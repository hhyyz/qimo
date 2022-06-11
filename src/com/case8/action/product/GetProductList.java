package com.case8.action.product;

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
import com.case8.pojo.Product;
import com.case8.pojo.User;
import com.case8.service.LoginService;
import com.case8.service.ProductService;
import com.case8.service.UserService;
import com.google.gson.Gson;

@WebServlet("/GetProductList")
public class GetProductList extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
    
	public GetProductList() {
        super();
    }
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	 // 设置前后端交互参数的编码为utf-8		
    	request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		// 用PrintWriter对象返回数据
		PrintWriter out = response.getWriter();
		
		
		// 获取前端参数
		// 查询条件，没有条件则不设置
		Product product = new Product();
		String condition = request.getParameter("condition");
		if (condition != null && condition.length() > 0) {
			product.setCondition(condition);
		} else {
			product.setCondition("");
		}
		// 分页条件，没有条件则不设置
		String pages = request.getParameter("pageNum");
		String rows = request.getParameter("pageSize");
		if (pages != null && pages.length() > 0 && rows != null && rows.length() > 0) {
			int page = Integer.parseInt(pages);
			int row = Integer.parseInt(rows);
			product.setLimit(" limit " + (page - 1) * row + "," + row);
		} else {
			product.setLimit("");
		}
		ProductService productService = new ProductService();
		BaseListPojo<Product> listPojo = productService.selectProducts(product);
		out.print(new Gson().toJson(listPojo));
	}

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	doPost(request, response);
	}


}
