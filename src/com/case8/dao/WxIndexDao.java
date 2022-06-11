package com.case8.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.case8.pojo.WxIndex;


public class WxIndexDao {

	private Connection conn = null;
	private PreparedStatement pst = null;

	// 定义构造方法，实例化的时候完成连接的注入
	public WxIndexDao(Connection conn) {
		super();
		this.conn = conn;
	}

	// 查询记录
	public ArrayList<WxIndex> select(WxIndex appIndex) throws SQLException {
		int position = appIndex.getPosition();
		String condition = appIndex.getCondition();
		String sql = "";
		if(position==0){
			sql = "select * from wxindex where 1=1";
		}else{
			sql = "select * from wxindex where position=" + position;
		}
		if (condition != null && !condition.equals("")) {
			sql += " and " + condition;
		}
		if(position==0){
			sql += " order by position, rank";
		}else{
			sql += " order by rank";
		}
		pst = conn.prepareStatement(sql);
		ResultSet rs = pst.executeQuery();
		ArrayList<WxIndex> list = new ArrayList<WxIndex>();
		if (rs.next()) {
			for (int i = 0; i <= rs.getRow(); i++) {
				WxIndex appIndex1 = new WxIndex();
				appIndex1.setId(rs.getInt("id"));
				appIndex1.setTitle(rs.getString("title"));
				appIndex1.setImgUrl(rs.getString("imgurl"));
				appIndex1.setLink(rs.getString("link"));
				appIndex1.setState(rs.getInt("state"));
				appIndex1.setRank(rs.getInt("rank"));
				appIndex1.setPosition(rs.getInt("position"));
				list.add(appIndex1);
				rs.next();
			}
		}
		return list;
	}

	public WxIndex selectById(int id) throws SQLException {
		String sql = "select * from wxindex where id=" + id;
		pst = conn.prepareStatement(sql);
		ResultSet rs = pst.executeQuery();
		WxIndex appIndex = new WxIndex();
		if (rs.next()) {
			appIndex.setId(rs.getInt("id"));
			appIndex.setTitle(rs.getString("title"));
			appIndex.setImgUrl(rs.getString("imgurl"));
			appIndex.setLink(rs.getString("link"));
			appIndex.setState(rs.getInt("state"));
			appIndex.setRank(rs.getInt("rank"));
			appIndex.setPosition(rs.getInt("position"));
		}
		return appIndex;
	}

	// 插入记录
	public boolean insert(WxIndex appIndex) throws SQLException {
		try {
			String sql = "insert into wxindex(id,title,imgurl,link,state,rank,position) values(?,?,?,?,?,?,?) ";
			pst = conn.prepareStatement(sql);
			pst.setInt(1, appIndex.getId());
			pst.setString(2, appIndex.getTitle());
			pst.setString(3, appIndex.getImgUrl());
			pst.setString(4, appIndex.getLink());
			pst.setInt(5, appIndex.getState());
			pst.setInt(6, appIndex.getRank());
			pst.setInt(7, appIndex.getPosition());
			pst.executeUpdate();
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	// 更新记录
	public boolean update(WxIndex appIndex) throws SQLException {
		try {
			String sql = "update wxindex set title=?,imgurl=?,link=?,state=?,rank=?,position=? where id=?";
			pst = conn.prepareStatement(sql);
			pst.setString(1, appIndex.getTitle());
			pst.setString(2, appIndex.getImgUrl());
			pst.setString(3, appIndex.getLink());
			pst.setInt(4, appIndex.getState());
			pst.setInt(5, appIndex.getRank());
			pst.setInt(6, appIndex.getPosition());
			pst.setInt(7, appIndex.getId());
			pst.executeUpdate();
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	// 删除记录
	public boolean delete(WxIndex appIndex) throws SQLException {
		try {
			String sql = "delete from wxindex where id=?";
			pst = conn.prepareStatement(sql);
			pst.setInt(1, appIndex.getId());
			pst.executeUpdate();
			return true;
		} catch (Exception e) {
			return false;
		}
	}
}
