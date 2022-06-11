package com.case8.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import com.case8.dao.OrderDao;
import com.case8.dao.ProductDao;
import com.case8.dao.UserDao;
import com.case8.pojo.BaseDataPojo;
import com.case8.pojo.BaseListPojo;
import com.case8.pojo.Order;
import com.case8.pojo.Product;
import com.case8.pojo.User;
import com.case8.util.DButil;

public class OrderService {
	
	public BaseListPojo<Order> selectOrders(Order order) {
		Connection conn = DButil.getConnection();
		OrderDao orderDao = new OrderDao(conn);
		ArrayList<Order> list = new ArrayList<Order>();
		int total = 0;
		try {
			list = orderDao.selectOrders(order);
			total = orderDao.countOrders();
			conn.commit();
			return new BaseListPojo<Order>("获取成功", true, total, list);
		} catch (SQLException e) {
			e.printStackTrace();
			return new BaseListPojo<Order>("获取失败", false, 0, null);
		} finally {
			if (conn != null) {
				DButil.closeConnection(conn);
			}
		}
	}
	
	// 通过id查询用户信息
	public Order selectById(int id) {
		Connection conn = DButil.getConnection();
		OrderDao orderDao = new OrderDao(conn);
		Order order = null;
		try {
			order = orderDao.selectById(id);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (conn != null) {
				DButil.closeConnection(conn);
			}
		}
		return order;
	}
	
	
	
	/* 更新业务 */
	public BaseDataPojo<Order> update(Order order) {
		Connection conn = DButil.getConnection();
		OrderDao orderDao = new OrderDao(conn);
		try {
			orderDao.update(order);
			conn.commit();
			return new BaseDataPojo<Order>("更新成功", true, null);
		} catch (Exception e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			return new BaseDataPojo<Order>("更新失败", false, null);
		} finally {
			if (conn != null) {
				DButil.closeConnection(conn);
			}
		}
	}

	
}
