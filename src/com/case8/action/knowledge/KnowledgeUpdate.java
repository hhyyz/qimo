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

@WebServlet("/KnowledgeUpdate")
public class KnowledgeUpdate extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter(); // 用PrintWriter对象将返回结果写入服务器
		
	
		
		// 获取前端参数
		int postId = Integer.parseInt(request.getParameter("postId"));
		String title = request.getParameter("title");
		String img = request.getParameter("img");
		String content = request.getParameter("content");
		int eye = Integer.parseInt(request.getParameter("eye"));
		int collection = Integer.parseInt(request.getParameter("collection"));
		String eyeimg = request.getParameter("eyeimg");
		String collimg = request.getParameter("collimg");
		String type = request.getParameter("type");
		Knowledge knowledge = new Knowledge(postId, title, img, content, eye, collection, eyeimg,collimg,type);
		
		
		KnowledgeService knowledgeService = new KnowledgeService();
		
				BaseDataPojo<Knowledge> dataPojo = knowledgeService.update(knowledge);
				out.print(new Gson().toJson(dataPojo));
			
	}
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}
}
