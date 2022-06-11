package com.case8.action.product;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.case8.pojo.BaseDataPojo;
import com.case8.pojo.LoginSession;
import com.case8.pojo.Product;
import com.case8.pojo.User;
import com.case8.service.LoginService;
import com.case8.service.ProductService;
import com.case8.service.UserService;
import com.google.gson.Gson;

@WebServlet("/AddProduct")
public class AddProduct extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public AddProduct() {
        super();
    }
    
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter(); // 用PrintWriter对象将返回结果写入服务器
		
		
		
		// 获取前端参数
		String name = request.getParameter("name");
		int type = Integer.parseInt(request.getParameter("type"));
		int number = Integer.parseInt(request.getParameter("number"));
		int price = Integer.parseInt(request.getParameter("price"));
		int yprice = Integer.parseInt(request.getParameter("yprice"));
		String img = request.getParameter("img");
		Product product = new Product(0,name,number,type,price,yprice,img);
		
		
		ProductService productService = new ProductService();
		
				BaseDataPojo<Product> dataPojo = productService.insert(product);
				out.print(new Gson().toJson(dataPojo));
		
	}
protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

}
