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

@WebServlet("/DeleteProduct")
public class DeleteProduct extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public DeleteProduct() {
        super();
    }

	

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		
		
		// 获取前端参数
		String idStr = request.getParameter("postId");
		
		ProductService productService = new ProductService();
		if(idStr != null){
			int postId = Integer.parseInt(idStr);
			Product product = new Product();
			product.setPostId(postId);
					BaseDataPojo<Product> dataPojo = productService.delete(product);
					out.print(new Gson().toJson(dataPojo));}
		else{
			out.print(new Gson().toJson(new BaseDataPojo<Product>("无id参数", false, null)));
		}
	}
				
       protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}
}
