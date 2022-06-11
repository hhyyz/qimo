package com.case8.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import com.case8.dao.ProductDao;
import com.case8.dao.UserDao;
import com.case8.pojo.BaseDataPojo;
import com.case8.pojo.BaseListPojo;
import com.case8.pojo.Product;
import com.case8.pojo.User;
import com.case8.util.DButil;

public class ProductService {
	
	public BaseListPojo<Product> selectProducts(Product product) {
		Connection conn = DButil.getConnection();
		ProductDao productDao = new ProductDao(conn);
		ArrayList<Product> list = new ArrayList<Product>();
		int total = 0;
		try {
			list = productDao.selectProducts(product);
			total = productDao.countProducts();
			conn.commit();
			return new BaseListPojo<Product>("获取成功", true, total, list);
		} catch (SQLException e) {
			e.printStackTrace();
			return new BaseListPojo<Product>("获取失败", false, 0, null);
		} finally {
			if (conn != null) {
				DButil.closeConnection(conn);
			}
		}
	}
	
	// 通过id查询用户信息
	public Product selectById(int id) {
		Connection conn = DButil.getConnection();
		ProductDao productDao = new ProductDao(conn);
		Product product = null;
		try {
			product = productDao.selectById(id);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (conn != null) {
				DButil.closeConnection(conn);
			}
		}
		return product;
	}
	
	/* 添加业务 */
	public BaseDataPojo<Product> insert(Product product) {
		Connection conn = DButil.getConnection();
		ProductDao productDao = new ProductDao(conn);
		try {
			int id = productDao.insert(product);
			conn.commit();
			if(id != -1){
				return new BaseDataPojo<Product>("添加成功", true, null);
			}else{
				return new BaseDataPojo<Product>("添加失败", false, null);
			}
		} catch (Exception e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			return new BaseDataPojo<Product>("添加失败", false, null);
		} finally {
			if (conn != null) {
				DButil.closeConnection(conn);
			}
		}
	}
	
	/* 更新业务 */
	public BaseDataPojo<Product> update(Product product) {
		Connection conn = DButil.getConnection();
		ProductDao productDao = new ProductDao(conn);
		try {
			productDao.update(product);
			conn.commit();
			return new BaseDataPojo<Product>("更新成功", true, null);
		} catch (Exception e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			return new BaseDataPojo<Product>("更新失败", false, null);
		} finally {
			if (conn != null) {
				DButil.closeConnection(conn);
			}
		}
	}

	/* 删除业务 */
	public BaseDataPojo<Product> delete(Product product) {
		Connection conn = DButil.getConnection();
		ProductDao productDao = new ProductDao(conn);
		try {
			productDao.delete(product);
			conn.commit();
			return new BaseDataPojo<Product>("删除成功", true, null);
		} catch (Exception e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			return new BaseDataPojo<Product>("删除失败", false, null);
		} finally {
			if (conn != null) {
				DButil.closeConnection(conn);
			}
		}
	}
	
	/* 重置密码业务 */
	public BaseDataPojo<Product> resetPrice(Product product) {
		Connection conn = DButil.getConnection();
		ProductDao productDao = new ProductDao(conn);
		try {
			product.setPrice(100);
			productDao.resetPrice(product);
			conn.commit();
			return new BaseDataPojo<Product>("重置密码成功", true, null);
		} catch (Exception e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			return new BaseDataPojo<Product>("重置密码失败", false, null);
		} finally {
			if (conn != null) {
				DButil.closeConnection(conn);
			}
		}
	}

	
}
