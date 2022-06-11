package com.case8.action.user;

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
import com.case8.pojo.User;
import com.case8.service.LoginService;
import com.case8.service.UserService;
import com.google.gson.Gson;

@WebServlet("/GetUserList")
public class GetUserList extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
    
	public GetUserList() {
        super();
    }
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	 // 设置前后端交互参数的编码为utf-8		
    	request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		// 用PrintWriter对象返回数据
		PrintWriter out = response.getWriter();
		
		// 获取header信息
		String token = request.getHeader("token");
		// 获取前端参数
		// 查询条件，没有条件则不设置
		User user = new User();
		String condition = request.getParameter("condition");
		if (condition != null && condition.length() > 0) {
			user.setCondition(condition);
		} else {
			user.setCondition("");
		}
		// 分页条件，没有条件则不设置
		String pages = request.getParameter("pageNum");
		String rows = request.getParameter("pageSize");
		if (pages != null && pages.length() > 0 && rows != null && rows.length() > 0) {
			int page = Integer.parseInt(pages);
			int row = Integer.parseInt(rows);
			user.setLimit(" limit " + (page - 1) * row + "," + row);
		} else {
			user.setLimit("");
		}
		LoginService loginService = new LoginService();
		UserService userService = new UserService();
		if(token != null){
			int id = loginService.checkToken(token);	// 验证token是否有效
			if(id != -1){
				BaseListPojo<User> listPojo = userService.selectUsers(user);
				out.print(new Gson().toJson(listPojo));
			}else{
				out.print(new Gson().toJson(new BaseDataPojo<LoginSession>("token已失效", false, null)));
			}
		}else{
			out.print(new Gson().toJson(new BaseDataPojo<LoginSession>("无token参数", false, null)));
		}
	}

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	doPost(request, response);
	}


}
