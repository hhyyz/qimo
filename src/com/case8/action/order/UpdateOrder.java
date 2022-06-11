package com.case8.action.order;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.case8.pojo.BaseDataPojo;
import com.case8.pojo.LoginSession;
import com.case8.pojo.Order;
import com.case8.pojo.User;
import com.case8.service.LoginService;
import com.case8.service.OrderService;
import com.case8.service.UserService;
import com.google.gson.Gson;

@WebServlet("/UpdateOrder")
public class UpdateOrder extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public UpdateOrder() {
        super();
    }

	

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		
		// 获取前端参数	
		int id = Integer.parseInt(request.getParameter("id"));
		String name = request.getParameter("name");
		int type = Integer.parseInt(request.getParameter("type"));
		String pname = request.getParameter("pname");
		String dname = request.getParameter("dname");
		int number = Integer.parseInt(request.getParameter("number"));
		int price = Integer.parseInt(request.getParameter("price"));
		
		
			
				Order order = new Order(id,name,type,pname,dname,number,price);
				
				OrderService orderService = new OrderService();
			
			
					BaseDataPojo<Order> dataPojo = orderService.update(order);
					out.print(new Gson().toJson(dataPojo));
			
	}
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}
}
