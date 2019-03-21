package app.test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class RenderFreeTest {

	public static void main(String[] args) {
		test();
		// TODO Auto-generated method stub

	}
		public static void test(){
			String attr1="19:43:00,23:59:59";
			String attr2="00:00:00,08:00:00";
			
			SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
			String timeStartStr ="";
			String timeEndStr ="";
			
			String dayStartStr = "";
			String dayEndStr = "";
			
			if(attr1 == null || attr1.length() < 1){
				
			}else{
				String[] attr1Split = attr1.split(",");
				timeStartStr = attr1Split[0];
				dayEndStr = attr1Split[1];
			}
			if(attr2 == null || attr2.length() < 1){
				
			}else{
				String[] attr2Split = attr2.split(",");
				dayStartStr = attr2Split[0];
				timeEndStr = attr2Split[1];
			}
			
			try {
				Date dateNow = sdf.parse(sdf.format(new Date()));
				
				Date dataStart = sdf.parse(timeStartStr);
				Date dataEnd = sdf.parse(timeEndStr);
				
				long startTimeLong = dataStart.getTime();
				long endTimeLong = dataEnd.getTime();
				
				long nowTimeLong = dateNow.getTime();
				
				long dayStartLong = sdf.parse(dayStartStr).getTime();
				long dayEndLong = sdf.parse(dayEndStr).getTime();
				//判断当前时间是否在渲染免费时间段内
				if ((startTimeLong < nowTimeLong && nowTimeLong < dayEndLong) || (dayStartLong < nowTimeLong && nowTimeLong < endTimeLong)){
					//System.out.println("true");
				}else{
					//System.out.println("false");
				}
			} catch (ParseException e) {
				//System.out.println(e);
			}
	}
}
