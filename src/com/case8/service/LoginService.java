package com.case8.service;

import java.sql.Connection;
import java.sql.SQLException;

import com.case8.dao.LoginDao;
import com.case8.dao.UserDao;
import com.case8.pojo.BaseDataPojo;
import com.case8.pojo.LoginSession;
import com.case8.pojo.User;
import com.case8.pojo.LoginWeb;
import com.case8.util.DButil;
import com.google.gson.Gson;

public class LoginService {
	
	// main方法用来测试业务层的所有方法，保证数据正确性
	public static void main(String[] args) {
		LoginService loginService = new LoginService();
		System.out.println(new Gson().toJson
				(loginService.webLogin("aaaa","123")));
	}

	// 处理登录业务
	public BaseDataPojo<LoginSession> login(String openId, String token){
		// 将openId和token插入数据库表，先查询数据库表中是否已存在此openId
		LoginSession loginSession = selectByOpenId(openId);
		// 数据库中已存在此openId，这时只需要更新token
		if (loginSession != null) { 
			loginSession.setToken(token); // 更新token
			if (update(loginSession)) { // 更新token成功，将新的token返回到前端缓存
				return new BaseDataPojo<LoginSession>
					("登录成功", true, loginSession);
			} else { // 更新失败，返回错误提示信息
				return new BaseDataPojo<LoginSession>
					("登录失败，更新token时错误", false, null);
			}
		} else { //不存在此openId，则先添加一个新用户，然后将新用户的id与用户登录表关联
			User user = new User();
			user.setName(token); // 初始设置一个随机姓名，后期可改名
			user.setType(0); // 设置用户类型：0微信用户，1管理员
			int userId = addUser(user); // 添加新用户
			if (userId != -1) { // 将userId和openId、token一起插入到数据库表中
				LoginSession loginSession1 = new LoginSession();
				loginSession1.setOpenId(openId);
				loginSession1.setToken(token);
				loginSession1.setUserId(userId);
				if (insert(loginSession1)) { // 新用户添加成功后，插入一条登录信息，关联openId、token及userId
					return new BaseDataPojo<LoginSession>("登录成功", true, loginSession1);
				} else {
					return new BaseDataPojo<LoginSession>("登录失败，添加openId及token时错误", false, null);
				}
			} else {
				return new BaseDataPojo<LoginSession>("登录失败，添加新用户时错误", false, null);
			}
		}
	}
	
	public BaseDataPojo<LoginWeb> webLogin(String userName, String pwd){
		Connection conn = DButil.getConnection();
		UserDao userDao = new UserDao(conn);
		LoginDao loginDao = new LoginDao(conn);
		LoginWeb webLogin = new LoginWeb();
		User user = null;
		try {
			user = userDao.selectWebUser(userName, pwd);
			if(user != null){	// 用户登录成功后，查询token一并返回
				String token = loginDao.selectByUserId(user.getId());
				if(token != null){
					webLogin.setId(user.getId());
					webLogin.setName(user.getName());
					webLogin.setPhone(user.getPhone());
					webLogin.setType(user.getType());
					webLogin.setToken(token);
					return new BaseDataPojo<LoginWeb>("登录成功", true, webLogin);
				}else{
					return new BaseDataPojo<LoginWeb>("获取token失败", false, null);
				}
			}else{
				return new BaseDataPojo<LoginWeb>("登录失败，用户名或密码错误", false, null);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return new BaseDataPojo<LoginWeb>("登录失败", false, null);
		}finally {
			if (conn != null) {
				DButil.closeConnection(conn);
			}
		}
	}
	
	public boolean insert(LoginSession loginSession) {
		Connection conn = DButil.getConnection();
		LoginDao loginDao = new LoginDao(conn);
		try {
			loginDao.insert(loginSession);	// 添加用户并返回用户id
			conn.commit();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
			if (conn != null) {
				DButil.closeConnection(conn);
			}
		}
	}
	
	public int addUser(User user) {
		Connection conn = DButil.getConnection();
		UserDao userDao = new UserDao(conn);
		try {
			int id = userDao.insert(user);	// 添加用户并返回用户id
			conn.commit();
			return id;
		} catch (SQLException e) {
			e.printStackTrace();
			return -1;
		} finally {
			if (conn != null) {
				DButil.closeConnection(conn);
			}
		}
	}
	
	// 根据openId更新数据库表loginSession
	public boolean update(LoginSession loginSession){
		Connection conn = DButil.getConnection();
		LoginDao loginDao = new LoginDao(conn);
		try {
			loginDao.update(loginSession);
			conn.commit();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} finally {	// 关闭数据库连接
			if (conn != null) {
				DButil.closeConnection(conn);
			}
		}
	}
	
	// 根据openId查询数据库表loginSession
	public LoginSession selectByOpenId(String openId){
		Connection conn = DButil.getConnection();
		LoginDao loginDao = new LoginDao(conn);
		LoginSession loginSession = null;
		try {
			loginSession = loginDao.selectByOpenId(openId);
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			if (conn != null) {
				DButil.closeConnection(conn);
			}
		}
		return loginSession;
	}
	
	// 查验token是否有效
	public int checkToken(String token) {
		LoginSession loginSession = selectByToken(token);
		if (loginSession != null) { // 数据库查到此token，表示token有效并返回userId
			return loginSession.getUserId();
		} else { // 数据库中没查到此token，表示token过期或者无此记录
			return -1;
		}
	}
	
	public LoginSession selectByToken(String token) {
		Connection conn = DButil.getConnection();
		LoginDao loginDAO = new LoginDao(conn);
		LoginSession session = null;
		try {
			session = loginDAO.selectByToken(token);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (conn != null) {
				DButil.closeConnection(conn);
			}
	    
		}
		
		return session;
		
	}

}
