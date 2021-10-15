package jdbcTestForReport;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import org.apache.commons.lang.StringUtils;
import org.json.JSONObject;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;



public class xxx {

	public static void main(String[] args) {
		Random rand = new Random();

		// Obtain a number between [0 - 49].
		
		
		ArrayList<String> actUseUrlList = new ArrayList<String>();
		
		//actUseUrlList.add("宗毅");
		actUseUrlList.add("Stella");
		//actUseUrlList.add("崇理");
		//actUseUrlList.add("勝傑");
		//actUseUrlList.add("昱辰");
		//actUseUrlList.add("舜璋");
		actUseUrlList.add("資文");
		actUseUrlList.add("子宸");
		for (int i = 0; i < 10; i++) {
			int n = rand.nextInt(actUseUrlList.size());
			System.out.println("2/28號中獎名單:" + actUseUrlList.get(n));
		}
	}
}
