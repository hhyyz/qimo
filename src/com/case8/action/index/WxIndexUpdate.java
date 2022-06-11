package com.case8.action.index;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.case8.pojo.BaseDataPojo;
import com.case8.pojo.LoginSession;
import com.case8.pojo.WxIndex;
import com.case8.service.LoginService;
import com.case8.service.WxIndexService;
import com.google.gson.Gson;

@WebServlet("/UpdateIndex")
public class WxIndexUpdate extends HttpServlet {

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
		int id = Integer.parseInt(request.getParameter("id"));
		String title = request.getParameter("title");
		String link = request.getParameter("link");
		String imgUrl = request.getParameter("imgUrl");
		int state = Integer.parseInt(request.getParameter("state"));
		int rank = Integer.parseInt(request.getParameter("rank"));
		int position = Integer.parseInt(request.getParameter("position"));
		WxIndex appIndex = new WxIndex(id, title, imgUrl, link, state, rank, position);
		
		LoginService loginService = new LoginService();
		WxIndexService indexService = new WxIndexService();
		if(token != null){
			int userId = loginService.checkToken(token);	// 验证token是否有效
			if(userId != -1){
				BaseDataPojo<WxIndex> dataPojo = indexService.update(appIndex);
				out.print(new Gson().toJson(dataPojo));
			}else{
				out.print(new Gson().toJson(new BaseDataPojo<LoginSession>("token已失效", false, null)));
			}
		}else{
			out.print(new Gson().toJson(new BaseDataPojo<LoginSession>("无token参数", false, null)));
		}
	}
}
