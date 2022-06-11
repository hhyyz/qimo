package com.case8.action.login;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.case8.pojo.BaseDataPojo;
import com.case8.pojo.LoginSession;
import com.case8.service.LoginService;
import com.case8.util.HttpUtil;
import com.google.gson.Gson;

@WebServlet("/Login")
public class Login extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	private static final String AppId = "wxab9cd6487866e93d";
	private static final String AppSecret = "eaa0a1463707491dbb416d42d9c7f48c";

	@SuppressWarnings("unchecked")
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 设置前后端交互参数的编码为utf-8		
		req.setCharacterEncoding("utf-8");
		resp.setContentType("text/html;charset=utf-8");
		// 用PrintWriter对象返回数据
		PrintWriter out = resp.getWriter();		
		// 提取前端提交的数据
		String code = req.getParameter("code");
		System.out.println("code=" + code);
		// 发送请求到微信API，获取用户的openId
		String url = "https://api.weixin.qq.com/sns/jscode2session" + "?appid=" + AppId + "&secret=" + AppSecret + "&js_code=" + code +
				"&grant_type=authorization_code";
		HttpUtil httpUtil = new HttpUtil();
		Map<String, String> map = new HashMap<>();
		String jsonStr = httpUtil.doGet(url, map);
		map = new Gson().fromJson(jsonStr, Map.class);
		// 获取用的openId
		String openId = map.get("openid");
		System.out.println("openId=" + openId);
		if(openId != null){
			// 生成登录凭证token，这里使用时间戳，实际开发中推荐用更成熟的机制生成token
			String token = "token_" + new Date().getTime();
			// 将openId和token传递到服务层，做下一步业务处理
			LoginService loginService = new LoginService();
			// 获取用户登录信息
			BaseDataPojo<LoginSession> dataPojo = loginService.login(openId, token);
			out.print(new Gson().toJson(dataPojo));
		}else{
			out.print(new Gson().toJson(new BaseDataPojo<LoginSession>("无法获取用户信息", false, null)));
		}
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// GET请求统一由POST处理
		doPost(req, resp);
	}
}
