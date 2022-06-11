package com.case8.action.login;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.case8.pojo.BaseDataPojo;
import com.case8.pojo.LoginWeb;
import com.case8.service.LoginService;
import com.google.gson.Gson;

@WebServlet("/WebLogin")
public class WebLogin extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 设置前后端交互参数的编码为utf-8		
		req.setCharacterEncoding("utf-8");
		resp.setContentType("text/html;charset=utf-8");
		// 用PrintWriter对象返回数据
		PrintWriter out = resp.getWriter();		
		// 提取前端提交的数据
		String userName = req.getParameter("userName");
		String pwd = req.getParameter("pwd");
		if(userName != null && pwd != null){
			LoginService loginService = new LoginService();
			BaseDataPojo<LoginWeb> dataPojo = loginService.webLogin(userName, pwd);
			out.print(new Gson().toJson(dataPojo));
		}
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// GET请求统一由POST处理
		doPost(req, resp);
	}
}
