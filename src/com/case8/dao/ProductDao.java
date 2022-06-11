package com.case8.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.case8.pojo.Product;
import com.case8.pojo.User;
import com.mysql.jdbc.Statement;

public class ProductDao {
	
	private Connection conn = null;
	private PreparedStatement pst = null;
	
	// 定义构造函数，实例化时完成连接的注入
	public ProductDao(Connection conn){
		super();
		this.conn = conn;
	}
	
	
	
	// 获取商品列表
	public ArrayList<Product> selectProducts(Product product) throws SQLException {
		String sql = "select * from product where 1=1";
		String condition = product.getCondition();
		String limit = product.getLimit();
		if (condition != null && !condition.equals("")) {
			sql = "select * from product where " + condition;
		}
		if (limit != null && !limit.equals("")) {
			sql += limit;
		}
		pst = conn.prepareStatement(sql);
		ResultSet rs = pst.executeQuery();
		ArrayList<Product> list = new ArrayList<Product>();
		while(rs.next()) {
			Product product1 = new Product();
			product1.setPostId(rs.getInt("postId"));
			product1.setName(rs.getString("name"));
			product1.setType(rs.getInt("type"));
			product1.setNumber(rs.getInt("number"));
			product1.setPrice(rs.getInt("price"));
			product1.setPrice(rs.getInt("yprice"));
			list.add(product1);
		}
		return list;
	}
	
	// 统计商品表总数
	public int countProducts() throws SQLException {
		String sql = "select * from product where 1=1";
		pst = conn.prepareStatement(sql);
		ResultSet rs = pst.executeQuery();
		int n = 0;
		while(rs.next()) {
			n++;
		}
		return n;
	}
	
	public Product selectById(int postId) throws SQLException {
		String sql = "select * from product where postId=?";
		pst = conn.prepareStatement(sql);
		pst.setInt(1, postId);
		ResultSet rs = pst.executeQuery();
		if (rs.next()) {
			Product product = new Product();
			product.setPostId(rs.getInt("postId"));
			product.setName(rs.getString("name"));
			product.setType(rs.getInt("type"));
			product.setNumber(rs.getInt("number"));
			product.setPrice(rs.getInt("price"));
			product.setYprice(rs.getInt("yprice"));
			return product;
		} else {
			return null;
		}
	}
	
	//添加商品
	public int insert(Product product){
		try {
			String sql = "insert into product(postId,name,number,type,price,yprice) values(?,?,?,?,?,?)";
			pst = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);	// 返回数据表的主key
			pst.setInt(1, product.getPostId());
			pst.setString(2, product.getName());
			pst.setInt(3, product.getNumber());
			pst.setInt(4, product.getPrice());
			pst.setInt(5, product.getType());
			pst.setInt(6, product.getYprice());
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
	public boolean update(Product product) throws SQLException {
		try {
			String sql = "update product set name=?,price=?,type=?,number=?,price=? where postId=?";
			pst = conn.prepareStatement(sql);
			pst.setString(1, product.getName());
			pst.setInt(2, product.getType());
			pst.setInt(3, product.getNumber());
			pst.setInt(4, product.getPrice());
			
			pst.setInt(5, product.getPostId());
			pst.setInt(6, product.getYprice());
			pst.executeUpdate();
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	// 删除记录
		public boolean delete(Product product) throws SQLException {
			try {
				String sql = "delete from product where postId=?";
				pst = conn.prepareStatement(sql);
				pst.setInt(1, product.getPostId());
				pst.executeUpdate();
				return true;
			} catch (Exception e) {
				return false;
			}
		}
	
	// 重置密码
	public boolean resetPrice(Product product) throws SQLException {
		try {
			String sql = "update product set price=? where postId=?";
			pst = conn.prepareStatement(sql);
			pst.setInt(1, product.getPrice());
			pst.setInt(2, product.getPostId());
			pst.executeUpdate();
			return true;
		} catch (Exception e) {
			return false;
		}
	}



}
