package com.case8.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.case8.pojo.Comment;
import com.case8.pojo.Knowledge;
import com.case8.pojo.Product;
import com.case8.pojo.User;
import com.mysql.jdbc.Statement;

public class CommentDao {
	
	private Connection conn = null;
	private PreparedStatement pst = null;
	
	// 定义构造函数，实例化时完成连接的注入
	public CommentDao(Connection conn){
		super();
		this.conn = conn;
	}
	
	// 查询用户登录信息
	
	
	// 获取评论列表
	public ArrayList<Comment> select(Comment comment) throws SQLException {
		String sql = "select * from comment where 1=1";
		String condition = comment.getCondition();
		
		String limit = comment.getLimit();
		if (condition != null && !condition.equals("")) {
			sql = "select * from comment where " + condition;
		}
		if (limit != null && !limit.equals("")) {
			sql += limit;
		}
		pst = conn.prepareStatement(sql);
		ResultSet rs = pst.executeQuery();
		ArrayList<Comment> list = new ArrayList<Comment>();
		if(rs.next()) {
			for (int i = 0; i <= rs.getRow(); i++) {
				Comment comment1 = new Comment();
				comment1.setPostId(rs.getInt("postId"));
				comment1.setName(rs.getString("name"));
				comment1.setImg(rs.getString("img"));
				comment1.setComment(rs.getString("comment"));
				comment1.setShoppingid(rs.getInt("shoppingid"));
				
				
				list.add(comment1);
			rs.next();
			}
		}
		return list;
	}

	
	public Comment selectById(int postId) throws SQLException {
		String sql = "select * from comment where shoppingid=" + postId;
		pst = conn.prepareStatement(sql);
		pst.setInt(1, postId);
		ResultSet rs = pst.executeQuery();
		Comment comment=new Comment();
		if (rs.next()) {
			
			comment.setPostId(rs.getInt("postId"));
			comment.setName(rs.getString("name"));
			comment.setImg(rs.getString("img"));
			comment.setComment(rs.getString("comment"));
			comment.setShoppingid(rs.getInt("shoppingid"));
			
		}return comment;
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
