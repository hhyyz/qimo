package com.case8.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.case8.pojo.Order;
import com.case8.pojo.Product;

import com.mysql.jdbc.Statement;

public class OrderDao {
	
	private Connection conn = null;
	private PreparedStatement pst = null;
	
	// 定义构造函数，实例化时完成连接的注入
	public OrderDao(Connection conn){
		super();
		this.conn = conn;
	}
	
	
	
	// 获取商品列表
	public ArrayList<Order> selectOrders(Order order) throws SQLException {
		String sql = "select * from orderlist where 1=1";
		String condition = order.getCondition();
		String limit = order.getLimit();
		if (condition != null && !condition.equals("")) {
			sql = "select * from orderlist where" + condition;
		}
		if (limit != null && !limit.equals("")) {
			sql += limit;
		}
		pst = conn.prepareStatement(sql);
		ResultSet rs = pst.executeQuery();
		ArrayList<Order> list = new ArrayList<Order>();
		while(rs.next()) {
			Order order1 = new Order();
			order1.setId(rs.getInt("id"));
			order1.setName(rs.getString("name"));
			
			order1.setType(rs.getInt("type"));
			order1.setPname(rs.getString("pname"));
			order1.setDname(rs.getString("dname"));
			order1.setNumber(rs.getInt("number"));
			order1.setPrice(rs.getInt("price"));
			list.add(order1);
		}
	
		return list;
	}
	
	// 统计商品表总数
	public int countOrders() throws SQLException {
		String sql = "select * from orderlist where 1=1";
		pst = conn.prepareStatement(sql);
		ResultSet rs = pst.executeQuery();
		int n = 0;
		while(rs.next()) {
			n++;
		}
		return n;
	}
	
	public Order selectById(int id) throws SQLException {
		String sql = "select * from orderlist where id=?";
		pst = conn.prepareStatement(sql);
		pst.setInt(1, id);
		ResultSet rs = pst.executeQuery();
		if (rs.next()) {
			Order order = new Order();
			order.setId(rs.getInt("id"));
			order.setName(rs.getString("name"));
			
			order.setType(rs.getInt("type"));
			order.setPname(rs.getString("pname"));
			order.setDname(rs.getString("dname"));
			order.setNumber(rs.getInt("number"));
			order.setPrice(rs.getInt("price"));
			return order;
		} else {
			return null;
		}
	}
	
	
	
	// 更新记录
	public boolean update(Order order) throws SQLException {
		try {
			String sql = "update orderlist set name=?,type=?,pname=?,dname=?,price=?,number=? where id=?";
			pst = conn.prepareStatement(sql);
			pst.setString(1, order.getName());
			pst.setInt(2, order.getType());
			pst.setString(3, order.getPname());
			pst.setString(4, order.getDname());
			pst.setInt(5, order.getNumber());
			pst.setInt(6, order.getPrice());
			
			pst.setInt(7, order.getId());
			pst.executeUpdate();
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	


}
