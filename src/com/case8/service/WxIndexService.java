package com.case8.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import com.case8.dao.WxIndexDao;
import com.case8.pojo.BaseDataPojo;
import com.case8.pojo.BaseListPojo;
import com.case8.pojo.WxIndex;
import com.case8.pojo.WxIndexAll;
import com.case8.util.DButil;
import com.case8.util.QiniuUtil;
import com.google.gson.Gson;

public class WxIndexService {
	
	public static void main(String[] args) {
		WxIndexService indexService = new WxIndexService();
		// 测试查询
		WxIndex appIndex = new WxIndex();
		// appIndex.setCondition("title like '%测试%'");
//		appIndex.setPosition(1);
//		BaseListPojo<WxIndex> listPojo = indexService.select(appIndex);
//		System.out.println(new Gson().toJson(listPojo));
		
		BaseDataPojo<WxIndexAll> dataPojo = indexService.selectApp(appIndex);
		System.out.println(new Gson().toJson(dataPojo));
		
		// 测试添加
		//AppIndex appIndex = new AppIndex(0, "测试", "http://127.0.0.1/", "16", 1, 3, 1);
		//BaseDataPojo<AppIndex> dataPojo = indexService.insert(appIndex);
		//System.out.println(new Gson().toJson(dataPojo));
		
		// 测试更新
		//AppIndex appIndex = new AppIndex(2, "测试", "http://127.0.0.1/", "10", 0, 1, 2);
		//BaseDataPojo<AppIndex> dataPojo = indexService.update(appIndex);
		//System.out.println(new Gson().toJson(dataPojo));
		
		// 测试删除
		//AppIndex appIndex = new AppIndex(2, "测试", "http://127.0.0.1/", "10", 0, 1, 2);
		//BaseDataPojo<AppIndex> dataPojo = indexService.delete(appIndex);
		//System.out.println(new Gson().toJson(dataPojo));
	}

	/* 管理端查询业务 */
	public BaseListPojo<WxIndex> select(WxIndex appIndex) {
		Connection conn = DButil.getConnection();
		WxIndexDao appIndexDao = new WxIndexDao(conn);
		try {
			ArrayList<WxIndex> list = new ArrayList<WxIndex>();
			list = appIndexDao.select(appIndex);// 返回记录集
			conn.commit();
			return new BaseListPojo<WxIndex>("获取成功", true, list.size(), list);
		} catch (Exception e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			return new BaseListPojo<WxIndex>("获取失败", false, 0, null);
		} finally {
			if (conn != null) {
				DButil.closeConnection(conn);
			}
		}
	}
	
	// 管理端查询详情
	public BaseDataPojo<WxIndex> selectById(int id) {
		Connection conn = DButil.getConnection();
		WxIndexDao appIndexDao = new WxIndexDao(conn);
		try {
			WxIndex appIndex = new WxIndex();
			appIndex = appIndexDao.selectById(id); // 返回记录集
			conn.commit();
			return new BaseDataPojo<WxIndex>("获取成功", true, appIndex);
		} catch (Exception e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			return new BaseDataPojo<WxIndex>("获取失败", false, null);
		} finally {
			if (conn != null) {
				DButil.closeConnection(conn);
			}
		}
	}
	
	/* 小程序端查询业务 */
	public BaseDataPojo<WxIndexAll> selectApp(WxIndex appIndex) {
		Connection conn = DButil.getConnection();
		WxIndexDao appIndexDao = new WxIndexDao(conn);
		appIndex.setCondition("state=1");
		try {
			// 分别获取顶部轮播、中部广告、底部导航
			appIndex.setPosition(1);
			ArrayList<WxIndex> listTop = appIndexDao.select(appIndex);
			appIndex.setPosition(2);
			ArrayList<WxIndex> listMid = appIndexDao.select(appIndex);
			appIndex.setPosition(3);
			ArrayList<WxIndex> listBot = appIndexDao.select(appIndex);
			conn.commit();
			WxIndexAll indexAll = new WxIndexAll();
			indexAll.setImgUrls(listTop);
			indexAll.setImage_ad(listMid.get(0));
			indexAll.setImage_bottom(listBot);
			return new BaseDataPojo<WxIndexAll>("获取首页成功", true, indexAll);
		} catch (Exception e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			return new BaseDataPojo<WxIndexAll>("获取首页失败", false, null);
		} finally {
			if (conn != null) {
				DButil.closeConnection(conn);
			}
		}
	}
	
	/* 添加业务 */
	public BaseDataPojo<WxIndex> insert(WxIndex appIndex) {
		Connection conn = DButil.getConnection();
		WxIndexDao appIndexDao = new WxIndexDao(conn);
		try {
			appIndexDao.insert(appIndex);
			conn.commit();
			return new BaseDataPojo<WxIndex>("添加成功", true, null);
		} catch (Exception e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			return new BaseDataPojo<WxIndex>("添加成功", true, null);
		} finally {
			if (conn != null) {
				DButil.closeConnection(conn);
			}
		}
	}

	/* 更新业务 */
	public BaseDataPojo<WxIndex> update(WxIndex appIndex) {
		Connection conn = DButil.getConnection();
		WxIndexDao appIndexDao = new WxIndexDao(conn);
		try {
			appIndexDao.update(appIndex);
			conn.commit();
			return new BaseDataPojo<WxIndex>("更新成功", true, null);
		} catch (Exception e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			return new BaseDataPojo<WxIndex>("更新失败", false, null);
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
