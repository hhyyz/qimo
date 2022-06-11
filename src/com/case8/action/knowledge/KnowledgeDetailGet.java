package com.case8.action.knowledge;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.case8.pojo.BaseDataPojo;
import com.case8.pojo.Knowledge;
import com.case8.pojo.LoginSession;
import com.case8.pojo.WxIndex;
import com.case8.service.KnowledgeService;
import com.case8.service.LoginService;
import com.case8.service.WxIndexService;
import com.google.gson.Gson;

@WebServlet("/KnowledgeDetailGet")
public class KnowledgeDetailGet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter(); // 用PrintWriter对象将返回结果写入服务器
		
		
		
		// 获取前端参数
		int id = Integer.parseInt(request.getParameter("id"));
		
		
		KnowledgeService knowledgeService = new KnowledgeService();
		
				BaseDataPojo<Knowledge> dataPojo = knowledgeService.selectById(id);
				out.print(new Gson().toJson(dataPojo));
			
	}
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

}
