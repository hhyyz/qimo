package com.case8.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.case8.pojo.Address;
import com.case8.pojo.Knowledge;
import com.case8.pojo.Product;
import com.case8.pojo.User;
import com.case8.pojo.WxIndex;
import com.mysql.jdbc.Statement;

public class AddressDao {
	
	private Connection conn = null;
	private PreparedStatement pst = null;
	
	// 定义构造函数，实例化时完成连接的注入
	public AddressDao(Connection conn){
		super();
		this.conn = conn;
	}
	
	// 查询用户登录信息
	
	
	// 获取商品列表
	public ArrayList<Address> select(Address address) throws SQLException {
		String sql = "select * from addresslist where 1=1";
		String condition = address.getCondition();
		
		String limit = address.getLimit();
		if (condition != null && !condition.equals("")) {
			sql = "select * from addresslist where " + condition;
		}
		if (limit != null && !limit.equals("")) {
			sql += limit;
		}
		pst = conn.prepareStatement(sql);
		ResultSet rs = pst.executeQuery();
		ArrayList<Address> list = new ArrayList<Address>();
		if(rs.next()) {
			for (int i = 0; i <= rs.getRow(); i++) {
				Address address1 = new Address();
				address1.setPostId(rs.getInt("postId"));
				address1.setAddresslist(rs.getString("addresslist"));
				address1.setName(rs.getString("name"));
				address1.setPhone(rs.getString("phone"));
				
				
				list.add(address1);
			rs.next();
			}
		}
		return list;
	}

	
	public Address selectById(int postId) throws SQLException {
		String sql = "select * from addresslist where postId=" + postId;
		pst = conn.prepareStatement(sql);
		pst.setInt(1, postId);
		ResultSet rs = pst.executeQuery();
		Address address=new Address();
		if (rs.next()) {
			
			address.setPostId(rs.getInt("postId"));
			address.setAddresslist(rs.getString("addresslist"));
			address.setName(rs.getString("name"));
			address.setPhone(rs.getString("phone"));
		}return address;
	}
	
	// 插入记录
		public boolean insert(Address appIndex) throws SQLException {
			try {
				String sql = "insert into addresslist(postId,addresslist,name,phone) values(?,?,?,?) ";
				pst = conn.prepareStatement(sql);
				pst.setInt(1, appIndex.getPostId());
				pst.setString(2, appIndex.getName());
				pst.setString(3, appIndex.getAddresslist());
				pst.setString(4, appIndex.getPhone());
				
				pst.executeUpdate();
				return true;
			} catch (Exception e) {
				return false;
			}
		}
	
	// 更新记录
	public boolean update(Address address) throws SQLException {
		try {
			String sql = "update addresslist set addresslist=?,name=?,phone=? where postId=?";
			pst = conn.prepareStatement(sql);
			pst.setString(1, address.getAddresslist());
			pst.setString(2, address.getName());
			pst.setString(3, address.getPhone());
			pst.setInt(4, address.getPostId());
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
