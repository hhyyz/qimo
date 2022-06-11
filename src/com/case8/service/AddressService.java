package com.case8.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import com.case8.dao.AddressDao;
import com.case8.dao.KnowledgeDao;
import com.case8.dao.ShoppingDao;
import com.case8.dao.UserDao;
import com.case8.dao.WxIndexDao;
import com.case8.pojo.Address;
import com.case8.pojo.AddressAll;
import com.case8.pojo.BaseDataPojo;
import com.case8.pojo.BaseListPojo;
import com.case8.pojo.Knowledge;
import com.case8.pojo.KnowledgeAll;
import com.case8.pojo.Product;
import com.case8.pojo.ProductAll;
import com.case8.pojo.User;
import com.case8.pojo.WxIndex;
import com.case8.pojo.WxIndexAll;
import com.case8.util.DButil;
import com.case8.util.QiniuUtil;
import com.google.gson.Gson;

public class AddressService {
	// 管理端查询详情
		public BaseDataPojo<Address> selectById(int id) {
			Connection conn = DButil.getConnection();
			AddressDao appIndexDao = new AddressDao(conn);
			try {
				Address appIndex = new Address();
				appIndex = appIndexDao.selectById(id); // 返回记录集
				conn.commit();
				return new BaseDataPojo<Address>("获取成功", true, appIndex);
			} catch (Exception e) {
				try {
					conn.rollback();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				return new BaseDataPojo<Address>("获取失败", false, null);
			} finally {
				if (conn != null) {
					DButil.closeConnection(conn);
				}
			}
		}
	
	public BaseDataPojo<AddressAll> selectApp(Address address) {
		Connection conn = DButil.getConnection();
		AddressDao addressDao = new AddressDao(conn);
		ArrayList<Address> list = new ArrayList<Address>();
		
		try {
			list = addressDao.select(address);
			
			conn.commit();
			AddressAll indexAll = new AddressAll();
			indexAll.setaddress(list);
			return new BaseDataPojo<AddressAll>("获取成功", true,indexAll);
		} catch (SQLException e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			return new BaseDataPojo<AddressAll>("获取失败", false,null);
		} finally {
			if (conn != null) {
				DButil.closeConnection(conn);
			}
		}
	}
	
	/* 添加业务 */
	public BaseDataPojo<Address> insert(Address address) {
		Connection conn = DButil.getConnection();
		AddressDao addressDao = new AddressDao(conn);
		try {
			addressDao.insert(address);
			conn.commit();
			return new BaseDataPojo<Address>("添加成功", true, null);
		} catch (Exception e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			return new BaseDataPojo<Address>("添加成功", true, null);
		} finally {
			if (conn != null) {
				DButil.closeConnection(conn);
			}
		}
	}

	/* 更新业务 */
	public BaseDataPojo<Address> update(Address address) {
		Connection conn = DButil.getConnection();
		AddressDao addressDao = new AddressDao(conn);
		try {
			addressDao.update(address);
			conn.commit();
			return new BaseDataPojo<Address>("更新成功", true, null);
		} catch (Exception e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			return new BaseDataPojo<Address>("更新失败", false, null);
		} finally {
			if (conn != null) {
				DButil.closeConnection(conn);
			}
		}
	}

	/* 删除业务 */
	public BaseDataPojo<WxIndex> delete(WxIndex appIndex) {
		Connection conn = DButil.getConnection();
		WxIndexDao appIndexDao = new WxIndexDao(conn);
		try {
			appIndexDao.delete(appIndex);
			conn.commit();
			// 删除时根据实际情况，删除上传的七牛图片
			String imgUrl = appIndex.getImgUrl();
			String key = imgUrl.substring(imgUrl.lastIndexOf("/")+1);
			if(QiniuUtil.delFile(key)){
				return new BaseDataPojo<WxIndex>("删除成功", true, null);
			}else{
				return new BaseDataPojo<WxIndex>("空间图片删除失败", true, null);
			}
		} catch (Exception e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			return new BaseDataPojo<WxIndex>("删除失败", false, null);
		} finally {
			if (conn != null) {
				DButil.closeConnection(conn);
			}
		}
	}

}
