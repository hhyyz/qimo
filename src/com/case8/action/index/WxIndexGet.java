package com.case8.action.index;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.case8.pojo.BaseDataPojo;
import com.case8.pojo.BaseListPojo;
import com.case8.pojo.LoginSession;
import com.case8.pojo.WxIndex;
import com.case8.pojo.WxIndexAll;
import com.case8.service.LoginService;
import com.case8.service.WxIndexService;
import com.google.gson.Gson;

@WebServlet("/GetIndexList")
public class WxIndexGet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter(); // 用PrintWriter对象将返回结果写入服务器

		// 获取header信息
		String token = request.getHeader("token");
		
		// 获取前端参数
		// 查询条件，没有条件则不设置
		String condition = request.getParameter("condition");
		boolean isApp = false;	// 是否小程序发请求
		WxIndex appIndex = new WxIndex(); // 创建对象，封装参数、查询条件
		if (condition != null && condition.length() > 0) {
			appIndex.setCondition(condition);
		} else {
			appIndex.setCondition("");
			isApp = true;
		}
		// 分页条件，没有条件则不设置
		String pages = request.getParameter("pageNum");
		String rows = request.getParameter("pageSize");
		if (pages != null && pages.length() > 0 && rows != null && rows.length() > 0) {
			int page = Integer.parseInt(pages);
			int row = Integer.parseInt(rows);
			appIndex.setLimit(" limit " + (page - 1) * row + "," + row);
		} else {
			appIndex.setLimit("");
		}

		LoginService loginService = new LoginService();
		WxIndexService indexService = new WxIndexService();
		if(token != null){
			int id = loginService.checkToken(token);	// 验证token是否有效
			if(id != -1){
				if (!isApp) { // 获取管理端查询首页数据
					BaseListPojo<WxIndex> listPojo = indexService.select(appIndex);
					out.print(new Gson().toJson(listPojo));
				} else {		// 获取小程序首页的全部数据
					BaseDataPojo<WxIndexAll> dataPojo = indexService.selectApp(appIndex);
					out.print(new Gson().toJson(dataPojo));
				}
			}else{
				out.print(new Gson().toJson(new BaseDataPojo<LoginSession>("token已失效", false, null)));
			}
		}else{
			out.print(new Gson().toJson(new BaseDataPojo<LoginSession>("无token参数", false, null)));
		}
	}
}
