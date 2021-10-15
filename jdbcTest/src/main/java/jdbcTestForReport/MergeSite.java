package jdbcTestForReport;

import java.io.FileWriter;
import java.io.IOException;
import java.net.InetAddress;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class MergeSite {

	/**
	 * 測試程式用於查詢IP地區用
	 */
	public static void main(String[] args) {
		// IO出來的位置
		List<String> ipList = new ArrayList<String>();
		
		try {	
			try {
				Class.forName("oracle.jdbc.driver.OracleDriver");
			} catch (ClassNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			Connection connection = null;
			connection = DriverManager.getConnection("jdbc:oracle:thin:@10.15.12.45:1521:LG", "requery",
					"tBpXAucUUSdMEWqX");
			Statement ps = connection.createStatement();
			// 你要下的SQL 記得給前墜

			// ResultSet result = ps.executeQuery(
			// " SELECT LOGIN_NAME,CREATE_TIME,LOGIN_IP FROM LG.AUTH_ACCOUNT
			// WHERE PLATFORM_ID = 960");
			ResultSet result;
			
			result = ps.executeQuery(
					"SELECT gpt.id,gpt.TEMPLATE_NAME,gpt.GAME_ID FROM GAME_PLAY_TEMPLATE gpt GROUP BY gpt.id,gpt.TEMPLATE_NAME,gpt.GAME_ID ORDER BY gpt.GAME_ID");
			

			int i = 1;
			while (result.next()) {
				// System.out.println(result.getString(3));
				ipList = new ArrayList<String>();
					String addres = "";
					if (addres.contains("菲律宾") || addres.contains("香港")) {
						StringBuffer sb = new StringBuffer();
						System.out.println(result.getString(1) + "||" + addres + "||" + result.getString(2));
						sb.append(result.getString(1) + ",");
						sb.append(result.getString(2) + ",");
						sb.append(addres + "\n");
					}
				

				i++;
				if (i % 1000 == 0) {
					System.out.println("第" + i + "笔");
				}
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}
}
