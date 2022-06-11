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

@WebServlet("/GetNoKeyToken")
public class GetToken extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 设置前后端交互参数的编码为utf-8		
		req.setCharacterEncoding("utf-8");
		resp.setContentType("text/html;charset=utf-8");
		// 用PrintWriter对象返回数据
		PrintWriter out = resp.getWriter();
		
		// 获取header信息
		String token = req.getHeader("token");
		
		// 获取小程序端传过来的参数，key：用于图片命名的参数
		LoginService loginService = new LoginService();
		if(token != null){
			int id = loginService.checkToken(token);	// 验证token是否有效
			if(id != -1){
				// 获取上传凭证
				String upToken = null;
				upToken = QiniuUtil.getUploadToken();
				Map<String, String> map = new HashedMap<>();
				map.put("token", upToken);
				if(upToken != null){
					out.print(new Gson().toJson(new BaseDataPojo<Map<String, String>>
					("获取七牛上传凭证token成功", true, map)));
				}else{
					out.print(new Gson().toJson(new BaseDataPojo<Map<String, String>>
					("获取七牛上传凭证token失败", false, map)));
				}
			}else{
				out.print(new Gson().toJson(new BaseDataPojo<LoginSession>("token已失效", false, null)));
			}
		}else{
			out.print(new Gson().toJson(new BaseDataPojo<LoginSession>("无token参数", false, null)));
		}
	}
}
