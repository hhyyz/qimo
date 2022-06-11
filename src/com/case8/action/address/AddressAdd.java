package com.case8.action.address;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.case8.pojo.Address;
import com.case8.pojo.BaseDataPojo;
import com.case8.pojo.LoginSession;
import com.case8.pojo.WxIndex;
import com.case8.service.AddressService;
import com.case8.service.LoginService;
import com.case8.service.WxIndexService;
import com.google.gson.Gson;

@WebServlet("/AddressAdd")
public class AddressAdd extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter(); // 用PrintWriter对象将返回结果写入服务器
		
		
		
		// 获取前端参数
		String name = request.getParameter("name");
		String addresslist = request.getParameter("addresslist");
		String phone = request.getParameter("phone");
		Address address = new Address(0, name, addresslist, phone);
		
		
		AddressService addressService = new AddressService();
		
				BaseDataPojo<Address> dataPojo = addressService.insert(address);
				out.print(new Gson().toJson(dataPojo));
			
	}
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}
}
