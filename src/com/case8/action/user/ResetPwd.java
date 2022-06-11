package com.case8.action.user;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.case8.pojo.BaseDataPojo;
import com.case8.pojo.LoginSession;
import com.case8.pojo.User;
import com.case8.service.LoginService;
import com.case8.service.UserService;
import com.google.gson.Gson;

@WebServlet("/ResetPwd")
public class ResetPwd extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public ResetPwd() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		
		// 获取header信息
		String token = request.getHeader("token");
		
		// 获取前端参数
		String idStr = request.getParameter("id");
		if(token != null){
			if(idStr == null){
				out.print(new Gson().toJson(new BaseDataPojo<LoginSession>("无id参数", false, null)));
			}else{
				int id = Integer.parseInt(request.getParameter("id"));
				User user = new User();
				user.setId(id);
				LoginService loginService = new LoginService();
				UserService userService = new UserService();
				int userId = loginService.checkToken(token);	// 验证token是否有效
				if(userId != -1){
					BaseDataPojo<User> dataPojo = userService.resetPwd(user);
					out.print(new Gson().toJson(dataPojo));
				}else{
					out.print(new Gson().toJson(new BaseDataPojo<LoginSession>("token已失效", false, null)));
				}
				
			}
		}else{
			out.print(new Gson().toJson(new BaseDataPojo<LoginSession>("无token参数", false, null)));
		}
	}

}
