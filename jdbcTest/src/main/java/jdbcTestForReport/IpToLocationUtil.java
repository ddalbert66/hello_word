package com.lgame.app.business.util;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.lgame.app.business.domain.AuthAccountDetail;
import com.lgame.modules.utils.CoderUtil;

import io.netty.util.internal.StringUtil;

public class IpToLocationUtil {
	/**
	 * 取得 IP 位置。
	 * @return
	 */
	public static String toLocation(String ip){
		//依IP位置判斷地區
	    String location = "";
	    try {
    		if(ip.indexOf(",") != -1){
    			String[] tokens = ip.split(",");
    			ip = tokens[0];
    		}
//          InetAddress ipAddress = InetAddress.getByName("211.21.81.44"); //台湾
//    		InetAddress ipAddress = InetAddress.getByName("66.249.79.17"); //美國
//			InetAddress ipAddress = InetAddress.getByName("112.29.252.12"); //安徽-none
//    		InetAddress ipAddress = InetAddress.getByName("14.215.240.152"); //广东省东莞市
	    	InetAddress ipAddress = InetAddress.getByName(ip);
	    	//打百度Api
			try {
				Map<String, Object> tokenData = new HashMap<String, Object>();
				//換打新的百度 舊的註解
				String encoding ="gbk";
//				tokenData.put("ak", "F454f8a5efe5e577997931cc01de3974"); // 开发者密钥
//				tokenData.put("ip", ipAddress.toString().replace("/", ""));
//				tokenData.put("coor", "bd09ll"); // 输出的坐标格式
//				String tokenUrl = "http://api.map.baidu.com/location/ip"; 		
				//新的百度
				tokenData.put("query",ipAddress.toString().replace("/", "")); // 查詢IP
				tokenData.put("resource_id","6006" );
				tokenData.put("ie", encoding); // 要的格式
				String tokenUrl = "http://opendata.baidu.com/api.php"; 
				Map<String,Object> paramMap = new HashMap<>();
				paramMap.put("conTimeout", 1000);
				paramMap.put("readTimeout", 1000);
				location = urlget(tokenUrl, tokenData, encoding, paramMap);
				
				if("中国".equals(location)) {
					tokenUrl = "https://sp0.baidu.com/8aQDcjqpAAV3otqbppnN2DJv/api.php";
					location = urlget(tokenUrl, tokenData, encoding, paramMap);
				}				
				
			} catch (Exception e) {
				e.printStackTrace();
				LogUtil.error(e);
			}
		} catch (Exception e) {
			e.printStackTrace();
			LogUtil.error(e);
			location = "不明位置";
		} 
		return location;
	  }
	
	private static String urlget(String tokenUrl, Map<String, Object> tokenData, String encoding, Map<String,Object> param) throws IOException {
		String location = "";
		List responseStr = HttpUtils.URLGet(tokenUrl, tokenData,encoding, param);
		if (responseStr.size() > 0) {
			JSONObject apiResult = JSONObject.parseObject(responseStr.get(0).toString());
			String status = apiResult.getString("status");
			if (status.equals("0")) { // 回传0成功
				JSONArray addressResult = apiResult.getJSONArray("data");
				if (null != addressResult && addressResult.size()>0) {
					String address = addressResult.getJSONObject(0).get("location").toString();
					if (null != address) {
						String[] cityForBaiduArr =  address.split(" ");
						if(cityForBaiduArr.length>1){
							String[] province=  cityForBaiduArr[0].split("省");
							if(province.length>1){
								location = cityForBaiduArr[0].replace("省", "省-");
							}else{
								location = province[0];								
							}
						}else{
							location = cityForBaiduArr[0];
						}
					}
				}
			} 
		}
		return location;
	}

	/**
	 * 測試程式用於查詢IP地區用
	 * */
	public static void main(String[] args){
		//IO出來的位置
		try (PrintWriter writer = new PrintWriter(new File("D://L085.csv"))) {
		      Class.forName("oracle.jdbc.driver.OracleDriver");
		      Connection connection = null;
		      connection = DriverManager.getConnection("jdbc:oracle:thin:@10.90.12.38:1521:LY", "requery",
		    		  "qXmHUhTMsK28QVe8");
		      Statement ps = connection.createStatement();
		      //你要下的SQL 記得給前墜
//		      ResultSet result = ps.executeQuery(
//		          "SELECT LOGIN_NAME,LAST_LOGIN_IP FROM LG.AUTH_ACCOUNT WHERE PLATFORM_ID  = 10983 AND LAST_LOGIN_IP IS NOT null");
		      ResultSet result = ps.executeQuery(
		    		  " SELECT acc.LOGIN_NAME AS \"账号\", CASE acc.\"TYPE\" WHEN '0' THEN '代理' WHEN  '1' THEN '会员'  ELSE '??' END  AS \"类型\"" + 
		    		  " ,acc.PARENT_NAME AS \"代理账号\", acc.VIP_EXPERIENCES AS \"成长积分\", acc.VIP_USER_LEVEL AS \"VIP等级\" " + 
		    		  " ,m.MONEY AS \"金额\", r9.REBATE AS \"时时彩返点\", r10.REBATE AS \"快3返点\"" + 
		    		  " , r11.REBATE AS \"11选5返点\", r13.REBATE AS \"北京赛车返点\", r5.REBATE AS \"六合彩返点\"" + 
		    		  " ,d.WECHAT AS \"微信\",d.QQ AS \"QQ\", d.EMAIL AS \"邮箱\", d.PHONE AS \"手机号\"" + 
//		    		  " -- , acc.CREATE_TIME AS \"注册时间\", acc.LAST_LOGIN_TIME \"最后登录时间\",d.REGISTER_IP AS \"注册IP\", acc.LAST_LOGIN_IP AS \"最后登录IP\"" + 
		    		  " FROM LY.AUTH_ACCOUNT acc" + 
		    		  " JOIN LY.AUTH_MONEY m ON" + 
		    		  " acc.id = m.ACCOUNT_ID" + 
		    		  " JOIN LY.AUTH_ACCOUNT_DETAIL d ON" + 
		    		  " acc.id = d.ACCOUNT_ID" + 
		    		  " LEFT JOIN LY.AUTH_AGENT_REBATE r9 ON" + 
		    		  " acc.id = r9.ACCOUNT_ID " + 
		    		  " AND r9.\"TYPE\" = 9" + 
		    		  " LEFT JOIN LY.AUTH_AGENT_REBATE r10 ON" + 
		    		  " acc.id = r10.ACCOUNT_ID " + 
		    		  " AND r10.\"TYPE\" = 10" + 
		    		  " LEFT JOIN LY.AUTH_AGENT_REBATE r11 ON" + 
		    		  " acc.id = r11.ACCOUNT_ID " + 
		    		  " AND r11.\"TYPE\" = 11" + 
		    		  " LEFT JOIN LY.AUTH_AGENT_REBATE r13 ON" + 
		    		  " acc.id = r13.ACCOUNT_ID " + 
		    		  " AND r13.\"TYPE\" = 13" + 
		    		  " LEFT JOIN LY.AUTH_AGENT_REBATE r5 ON" + 
		    		  " acc.id = r5.ACCOUNT_ID " + 
		    		  " AND r5.\"TYPE\" = 5" + 
		    		  " WHERE" + 
		    		  " 	acc.PLATFORM_ID = 304" + 
		    		  " AND acc.ACCOUNT_TYPE = 1" + 
		    		  " ORDER BY acc.LOGIN_NAME"
		    		  );
//		      ResultSet result = ps.executeQuery(
//		    		  " SELECT acc.LOGIN_NAME, acc.PARENT_NAME, m.MONEY " + 
//		    				  " , d.WECHAT, d.QQ, d.EMAIL, d.REMARK, d.PHONE, acc.CREATE_TIME" + 
//		    				  " , acc.LAST_LOGIN_TIME, d.REGISTER_IP, acc.LAST_LOGIN_IP " + 
//		    				  " FROM LY.AUTH_ACCOUNT acc" + 
//		    				  " JOIN LY.AUTH_MONEY m ON" + 
//		    				  " acc.id = m.ACCOUNT_ID" + 
//		    				  " JOIN LY.AUTH_ACCOUNT_DETAIL d ON" + 
//		    				  " acc.id = d.ACCOUNT_ID" + 
//		    				  " WHERE acc.PLATFORM_ID = 281" + 
//		    				  " AND acc.ACCOUNT_TYPE = 1" + 
//		    				  " ORDER BY acc.LOGIN_NAME"
//		    		  );
		      
		      
		      StringBuffer sb = new StringBuffer();
		      int index = 1;
		      while (result.next()) {
		    	  
		    	  if (index <= 50000 ) {
//		    	  if (index > 5000 && index <= 10000) {
//		    	  if (index > 10000 ) {
					AuthAccountDetail entity = new AuthAccountDetail();
					//,d.WECHAT AS "微信",d.QQ AS "QQ", d.EMAIL AS "邮箱", d.PHONE AS "手机号"
					if (StringUtil.isNotEmpty(result.getString(12))) {
						entity.setWechat(result.getString(12));
					}
					if (StringUtil.isNotEmpty(result.getString(13))) {
						entity.setQq(result.getString(13));
					}
					if (StringUtil.isNotEmpty(result.getString(14))) {
						entity.setEmail(result.getString(14));
					}
//					if (StringUtil.isNotEmpty(result.getString(15))) {
//						entity.setRemark(result.getString(15));
//					}
					if (StringUtil.isNotEmpty(result.getString(15))) {
						entity.setPhone(result.getString(15));
					}
					CoderUtil.annDecryption(entity);
					//, d.WECHAT, d.QQ, d.EMAIL, d.REMARK, d.PHONE,
					System.out.println(index + " : \t" + result.getString(1) 
							+ "\t" + result.getString(2) 
							+ "\t" + result.getString(3) 
							+ "\t" + result.getString(4) 
							+ "\t" + result.getString(5) 
							+ "\t" + result.getString(6) 
							+ "\t" + result.getString(7) 
							+ "\t" + result.getString(8) 
							+ "\t" + result.getString(9) 
							+ "\t" + result.getString(10) 
							+ "\t" + result.getString(11)
							+ "\t" + entity.getWechat() //
							+ "\t" + entity.getQq() 	//
							+ "\t" + entity.getEmail() 	//
//							+ "\t" + entity.getRemark() //
							+ "\t" + entity.getPhone() 	//
							);
				}
				index ++;
//		    	  System.out.println(result.getString(2));
//		    	  System.out.println(result.getString(3));
//		    	  System.out.println(result.getString(4));
//		    	  if(null != result.getString(2)){
//		    		  String addres =  "";
//		    		  try{
//		    			  addres = IpToLocationUtil.toLocation(result.getString(2));
//		    		  }catch(Exception e){
//		    			  e.printStackTrace();
//		    		  }
//			        if ("菲律宾".equals(addres)) {
//				    	  System.out.println(result.getString(1));
//				    	  System.out.println(result.getString(2));
//			        	sb.append(result.getString(1) + ",");
//			          sb.append(result.getString(2) + ",");
//			          sb.append(addres + "\n");
//			        }
//		    	  }
		      }
		      writer.write(sb.toString());
		    } catch (Exception e1) {
		      e1.printStackTrace();
		    }
	}
}
