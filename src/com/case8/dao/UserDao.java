package com.case8.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.case8.pojo.User;
import com.mysql.jdbc.Statement;

public class UserDao {
	
	private Connection conn = null;
	private PreparedStatement pst = null;
	
	// 定义构造函数，实例化时完成连接的注入
	public UserDao(Connection conn){
		super();
		this.conn = conn;
	}
	
	// 查询用户登录信息
	public User selectWebUser(String userName, String pwd) throws SQLException {
		String sql = "select * from user where name=? and password=?";
		pst = conn.prepareStatement(sql);
		pst.setString(1, userName);
		pst.setString(2, pwd);
		ResultSet rs = pst.executeQuery();
		if (rs.next()) {
			User user = new User();
			user.setId(rs.getInt("id"));
			user.setName(rs.getString("name"));
			user.setType(rs.getInt("type"));
			user.setPhone(rs.getString("phone"));
			return user;
		} else {
			return null;
		}
	}
	
	// 获取用户列表
	public ArrayList<User> selectUsers(User user) throws SQLException {
		String sql = "select * from user where 1=1";
		String condition = user.getCondition();
		String limit = user.getLimit();
		if (condition != null && !condition.equals("")) {
			sql = "select * from user where " + condition;
		}
		if (limit != null && !limit.equals("")) {
			sql += limit;
		}
		pst = conn.prepareStatement(sql);
		ResultSet rs = pst.executeQuery();
		ArrayList<User> list = new ArrayList<User>();
		while(rs.next()) {
			User user1 = new User();
			user1.setId(rs.getInt("id"));
			user1.setName(rs.getString("name"));
			user1.setType(rs.getInt("type"));
			user1.setPhone(rs.getString("phone"));
			list.add(user1);
		}
		return list;
	}
	
	// 统计用户表总数
	public int countUsers() throws SQLException {
		String sql = "select * from user where 1=1";
		pst = conn.prepareStatement(sql);
		ResultSet rs = pst.executeQuery();
		int n = 0;
		while(rs.next()) {
			n++;
		}
		return n;
	}
	
	public User selectById(int id) throws SQLException {
		String sql = "select * from user where id=?";
		pst = conn.prepareStatement(sql);
		pst.setInt(1, id);
		ResultSet rs = pst.executeQuery();
		if (rs.next()) {
			User user = new User();
			user.setId(rs.getInt("id"));
			user.setName(rs.getString("name"));
			user.setType(rs.getInt("type"));
			return user;
		} else {
			return null;
		}
	}
	
	//添加用户
	public int insert(User user){
		try {
			String sql = "insert into user(id,name,phone,type) values(?,?,?,?)";
			pst = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);	// 返回数据表的主key
			pst.setInt(1, user.getId());
			pst.setString(2, user.getName());
			pst.setString(3, user.getPhone());
			pst.setInt(4, user.getType());
			pst.executeUpdate();
			ResultSet rs = pst.getGeneratedKeys();		// 获取主key
			if(rs.next()){
				return rs.getInt(1);	// 返回主key
			}else{
				return -1;		// 插入失败返回-1
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return -1;
		}
	}
	
	// 更新记录
	public boolean update(User user) throws SQLException {
		try {
			String sql = "update user set name=?,phone=?,type=? where id=?";
			pst = conn.prepareStatement(sql);
			pst.setString(1, user.getName());
			pst.setString(2, user.getPhone());
			pst.setInt(3, user.getType());
			pst.setInt(4, user.getId());
			pst.executeUpdate();
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	// 删除记录
	public boolean delete(User user) throws SQLException {
		try {
			String sql = "delete from user where id=?";
			pst = conn.prepareStatement(sql);
			pst.setInt(1, user.getId());
			pst.executeUpdate();
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	// 重置密码
	public boolean resetPwd(User user) throws SQLException {
		try {
			String sql = "update user set password=? where id=?";
			pst = conn.prepareStatement(sql);
			pst.setString(1, user.getPassword());
			pst.setInt(2, user.getId());
			pst.executeUpdate();
			return true;
		} catch (Exception e) {
			return false;
		}
	}
}
