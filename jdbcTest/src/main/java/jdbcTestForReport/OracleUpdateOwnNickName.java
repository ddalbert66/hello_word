package jdbcTestForReport;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class OracleUpdateOwnNickName {


	private static String fileName = "D:\\temp\\GAME_NAME_OWN_NICK_NAME.csv";

	public static void main(String argv[]) {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver"); // 驅動程式-第四類

			// Class.forName("sun.jdbc.odbc.JdbcOdbcDriver"); //驅動程式-第一類:
			// JDBC-ODBC橋接器
			// 至 設定-控制台-系統管理工具-資料來源(ODBC)
			// -> 選擇系統資料來源名稱->新增->選Oracle...->完成
			// -> [Data Source Name(資料來源名稱)\輸入:dsn1] [TNS Service Name\選擇:ORCL2]
			// [User ID\輸入:scott] [OK] -> 確定
		} catch (java.lang.ClassNotFoundException e) {
			System.err.print("ClassNotFoundException: ");
			System.err.println(e.getMessage());
		}

		BufferedReader br = null;
		FileReader fr = null;
		int count = 0;
		try {
//			Connection con = DriverManager.getConnection("jdbc:oracle:thin:@172.18.8.61:1521:bpmesbdb2", "twmewpprod",
//					"twmewpprod");

			fr = new FileReader(fileName);
			br = new BufferedReader(fr);
//			Statement stmt = con.createStatement();
			StringBuilder sb = new StringBuilder("update pub_site_game set OWN_NICK_NAME = '%s' where game_id = '%s' \n");
			String sCurrentLine;
			while ((sCurrentLine = br.readLine()) != null) {
				String[] gameName = sCurrentLine.split(",");
				if(gameName[1].contains(gameName[2])){
					System.out.printf(sb.toString(),gameName[1],gameName[0]);
				}
				
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				fr.close();
				br.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}
}