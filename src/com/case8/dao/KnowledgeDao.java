package com.case8.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.case8.pojo.Knowledge;
import com.case8.pojo.User;
import com.mysql.jdbc.Statement;

public class KnowledgeDao {
	
	private Connection conn = null;
	private PreparedStatement pst = null;
	
	// 定义构造函数，实例化时完成连接的注入
	public KnowledgeDao(Connection conn){
		super();
		this.conn = conn;
	}
	
	// 查询用户登录信息
	
	
	// 获取知识列表
	public ArrayList<Knowledge> select(Knowledge knowledge) throws SQLException {
		String sql = "select * from knowledge where 1=1";
		String condition = knowledge.getCondition();
		
		String limit = knowledge.getLimit();
		if (condition != null && !condition.equals("")) {
			sql = "select * from knowledge where " + condition;
		}
		if (limit != null && !limit.equals("")) {
			sql += limit;
		}
		pst = conn.prepareStatement(sql);
		ResultSet rs = pst.executeQuery();
		ArrayList<Knowledge> list = new ArrayList<Knowledge>();
		if(rs.next()) {
			for (int i = 0; i <= rs.getRow(); i++) {
			Knowledge knowledge1 = new Knowledge();
			knowledge1.setPostId(rs.getInt("postId"));
			knowledge1.setTitle(rs.getString("title"));
			knowledge1.setImg(rs.getString("img"));
			knowledge1.setContent(rs.getString("content"));
			knowledge1.setEye(rs.getInt("eye"));
			knowledge1.setCollection(rs.getInt("collection"));
			knowledge1.setEyeimg(rs.getString("eyeimg"));
			knowledge1.setCollimg(rs.getString("collimg"));
			knowledge1.setType(rs.getString("type"));
			list.add(knowledge1);
			rs.next();
			}
		}
		return list;
	}

	
	public Knowledge selectById(int postId) throws SQLException {
		String sql = "select * from knowledge where postId=" + postId;
		pst = conn.prepareStatement(sql);
		pst.setInt(1, postId);
		ResultSet rs = pst.executeQuery();
		Knowledge knowledge=new Knowledge();
		if (rs.next()) {
			
			knowledge.setPostId(rs.getInt("postId"));
			knowledge.setTitle(rs.getString("title"));
			knowledge.setImg(rs.getString("img"));
			knowledge.setContent(rs.getString("content"));
			knowledge.setEye(rs.getInt("eye"));
			knowledge.setCollection(rs.getInt("collection"));
			knowledge.setEyeimg(rs.getString("eyeimg"));
			knowledge.setCollimg(rs.getString("collimg"));
			knowledge.setType(rs.getString("type"));
		}return knowledge;
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
	public boolean update(Knowledge knowledge) throws SQLException {
		try {
			String sql = "update knowledge set title=?,img=?,content=?,eye=?,collection=?,eyeimg=?,collimg=? where postId=?";
			pst = conn.prepareStatement(sql);
			pst.setString(1, knowledge.getTitle());
			pst.setString(2, knowledge.getImg());
			pst.setString(3, knowledge.getContent());
			pst.setInt(4, knowledge.getEye());
			pst.setInt(5, knowledge.getCollection());
			pst.setString(6, knowledge.getEyeimg());
			pst.setString(7, knowledge.getCollimg());
			pst.setInt(8, knowledge.getPostId());
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
