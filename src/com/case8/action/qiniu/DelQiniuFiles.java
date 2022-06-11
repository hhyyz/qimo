package com.case8.action.qiniu;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections4.map.HashedMap;

import com.case8.pojo.BaseDataPojo;
import com.case8.pojo.LoginSession;
import com.case8.service.LoginService;
import com.case8.util.QiniuUtil;
import com.google.gson.Gson;

@WebServlet("/DelQnFiles")
public class DelQiniuFiles extends HttpServlet {
	
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 设置前后端交互参数的编码为utf-8		
		req.setCharacterEncoding("utf-8");
		resp.setContentType("text/html;charset=utf-8");
		// 用PrintWriter对象返回数据
		PrintWriter out = resp.getWriter();	
		
		// 获取header信息
		String token = req.getHeader("token");
		
		// 获取参数并解析
		String keyListStr = req.getParameter("keys");
		String[] keyList = new Gson().fromJson(keyListStr,String[].class);
		System.out.println(keyList);
		
		LoginService loginService = new LoginService();
		if(token != null){
			int id = loginService.checkToken(token);	// 验证token是否有效
			if(id != -1){
				boolean isOk = QiniuUtil.delFileList(keyList);
				Map<String, String> map = new HashedMap<>();
				if(isOk){
					out.print(new Gson().toJson(new BaseDataPojo<Map<String, String>>
					("删除成功", true, map)));
				}else{
					out.print(new Gson().toJson(new BaseDataPojo<Map<String, String>>
					("删除失败", false, map)));
				}
			}else{
				out.print(new Gson().toJson(new BaseDataPojo<LoginSession>("token已失效", false, null)));
			}
		}else{
			out.print(new Gson().toJson(new BaseDataPojo<LoginSession>("无token参数", false, null)));
		}
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// GET请求统一由POST处理
		doPost(req, resp);
	}
}
