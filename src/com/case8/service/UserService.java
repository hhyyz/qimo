package com.case8.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import com.case8.dao.UserDao;
import com.case8.pojo.BaseDataPojo;
import com.case8.pojo.BaseListPojo;
import com.case8.pojo.User;
import com.case8.util.DButil;

public class UserService {
	
	public BaseListPojo<User> selectUsers(User user) {
		Connection conn = DButil.getConnection();
		UserDao userDao = new UserDao(conn);
		ArrayList<User> list = new ArrayList<User>();
		int total = 0;
		try {
			list = userDao.selectUsers(user);
			total = userDao.countUsers();
			conn.commit();
			return new BaseListPojo<User>("获取成功", true, total, list);
		} catch (SQLException e) {
			e.printStackTrace();
			return new BaseListPojo<User>("获取失败", false, 0, null);
		} finally {
			if (conn != null) {
				DButil.closeConnection(conn);
			}
		}
	}
	
	// 通过id查询用户信息
	public User selectById(int id) {
		Connection conn = DButil.getConnection();
		UserDao userDao = new UserDao(conn);
		User user = null;
		try {
			user = userDao.selectById(id);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (conn != null) {
				DButil.closeConnection(conn);
			}
		}
		return user;
	}
	
	/* 添加业务 */
	public BaseDataPojo<User> insert(User user) {
		Connection conn = DButil.getConnection();
		UserDao userDao = new UserDao(conn);
		try {
			int id = userDao.insert(user);
			conn.commit();
			if(id != -1){
				return new BaseDataPojo<User>("添加成功", true, null);
			}else{
				return new BaseDataPojo<User>("添加失败", false, null);
			}
		} catch (Exception e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			return new BaseDataPojo<User>("添加失败", false, null);
		} finally {
			if (conn != null) {
				DButil.closeConnection(conn);
			}
		}
	}
	
	/* 更新业务 */
	public BaseDataPojo<User> update(User user) {
		Connection conn = DButil.getConnection();
		UserDao userDao = new UserDao(conn);
		try {
			userDao.update(user);
			conn.commit();
			return new BaseDataPojo<User>("更新成功", true, null);
		} catch (Exception e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			return new BaseDataPojo<User>("更新失败", false, null);
		} finally {
			if (conn != null) {
				DButil.closeConnection(conn);
			}
		}
	}

	/* 删除业务 */
	public BaseDataPojo<User> delete(User user) {
		Connection conn = DButil.getConnection();
		UserDao userDao = new UserDao(conn);
		try {
			userDao.delete(user);
			conn.commit();
			return new BaseDataPojo<User>("删除成功", true, null);
		} catch (Exception e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			return new BaseDataPojo<User>("删除失败", false, null);
		} finally {
			if (conn != null) {
				DButil.closeConnection(conn);
			}
		}
	}
	
	/* 重置密码业务 */
	public BaseDataPojo<User> resetPwd(User user) {
		Connection conn = DButil.getConnection();
		UserDao userDao = new UserDao(conn);
		try {
			user.setPassword("ea49cbd8494148c33755d7fb51cc2511");
			userDao.resetPwd(user);
			conn.commit();
			return new BaseDataPojo<User>("重置密码成功", true, null);
		} catch (Exception e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			return new BaseDataPojo<User>("重置密码失败", false, null);
		} finally {
			if (conn != null) {
				DButil.closeConnection(conn);
			}
		}
	}
}
