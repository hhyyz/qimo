package com.case8.action.login;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.case8.pojo.BaseDataPojo;
import com.case8.pojo.LoginSession;
import com.case8.service.LoginService;
import com.google.gson.Gson;

@WebServlet("/CheckLogin")
public class CheckLogin extends HttpServlet {
	
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 设置前后端交互参数的编码为utf-8		
		req.setCharacterEncoding("utf-8");
		resp.setContentType("text/html;charset=utf-8");
		// 用PrintWriter对象返回数据
		PrintWriter out = resp.getWriter();		
		// 提取前端提交的数据
		String token = req.getParameter("token");
		System.out.println(token);
		// 将openId和token传递到服务层，做下一步业务处理
		LoginService loginService = new LoginService();
		int id = loginService.checkToken(token);
		if(id != -1){		// token有效
			out.print(new Gson().toJson(new BaseDataPojo<LoginSession>("token有效", true, null)));
		}else{			// token无效
			out.print(new Gson().toJson(new BaseDataPojo<LoginSession>("token无效或错误", false, null)));
		};
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// GET请求统一由POST处理
		doPost(req, resp);
	}
}
