package com.case8.action.address;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.case8.pojo.Address;
import com.case8.pojo.AddressAll;
import com.case8.pojo.BaseDataPojo;
import com.case8.pojo.BaseListPojo;
import com.case8.pojo.Knowledge;
import com.case8.pojo.KnowledgeAll;
import com.case8.pojo.LoginSession;
import com.case8.pojo.Order;
import com.case8.pojo.Product;
import com.case8.pojo.ProductAll;
import com.case8.pojo.User;
import com.case8.service.AddressService;
import com.case8.service.KnowledgeService;
import com.case8.service.LoginService;
import com.case8.service.OrderService;
import com.case8.service.ProductService;
import com.case8.service.ShoppingService;
import com.case8.service.UserService;
import com.google.gson.Gson;

@WebServlet("/GetAddressList")
public class GetAddressList extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
    
	
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	 // 设置前后端交互参数的编码为utf-8		
    	request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		// 用PrintWriter对象返回数据
		PrintWriter out = response.getWriter();
		
		
		// 获取前端参数
		// 查询条件，没有条件则不设置
		
		String condition = request.getParameter("condition");
		boolean isApp = false;	// 是否小程序发请求
		Address address = new Address();
		if (condition != null && condition.length() > 0) {
			address.setCondition(condition);
		} else {
			address.setCondition("");
			isApp = true;
		}
		// 分页条件，没有条件则不设置
		String pages = request.getParameter("pageNum");
		String rows = request.getParameter("pageSize");
		if (pages != null && pages.length() > 0 && rows != null && rows.length() > 0) {
			int page = Integer.parseInt(pages);
			int row = Integer.parseInt(rows);
			address.setLimit(" limit " + (page - 1) * row + "," + row);
		} else {
			address.setLimit("");
		}
		AddressService addressService = new AddressService();
		BaseDataPojo<AddressAll> dataPojo =addressService.selectApp(address);
		out.print(new Gson().toJson(dataPojo));
	}

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	doPost(request, response);
	}


}
