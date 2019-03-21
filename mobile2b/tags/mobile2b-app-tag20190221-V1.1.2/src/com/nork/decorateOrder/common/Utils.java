package com.nork.decorateOrder.common;


import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author WangHaiLin
 * @date 2018/10/20  17:30
 */
public class Utils {

    /**
     * 处理电话号码：取电话号码前三位 + “****” + 电话号码后一位
     * @param mobile
     * @return
     */
    public static String  delMobile(String mobile){
    	if(mobile == null) {
    		return null;
    	}
        StringBuffer sb=new StringBuffer();
        String prefix = mobile.substring(0,3);
        String suffix = mobile.substring(mobile.length() - 1);
        sb.append(prefix);
        sb.append("****");
        sb.append(suffix);
        return sb.toString();
    }

    /**
     * 计算某个时间后多少天的时间
     * @param date 某个时间
     * @param days 后多少天
     * @return 时间
     */
    public static Date dateAfterDay(Date date , Integer days){
        Calendar calendar=Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, days);
        Date time = calendar.getTime();
        return time;
    }



    /**
     * 非抢单计算倒计时时间
     * @param create
     * @param day
     * @return
     */
    public static long delLostTime(Date create,Integer day){
        //create后多少day的时间
        Date lostDate = dateAfterDay(create ,day);
        //报价过期时间毫秒数
        long lostTime = lostDate.getTime();
        //当前时间毫秒数
        long nowTime = new Date().getTime();
        //倒计时毫秒数
        return lostTime-nowTime;
    }

    /**
     * 抢单计算倒计时时间
     * @param create
     * @return
     */
    public static long delLostTime2(Date create){
        //create后多少day的时间
        long date  = create.getTime();
        //报价过期时间毫秒数
        long lostTime = date+(15*60*1000);
        //当前时间毫秒数
        long nowTime = new Date().getTime();
        //倒计时毫秒数
        return lostTime-nowTime;
    }


    public static Long delLostTime(Date endTime){
        //当前时间
        Date nowTime=new Date(System.currentTimeMillis());
        //报价结束时间毫秒数
        long end = endTime.getTime();
        //当前时间毫秒数
        long now = nowTime.getTime();
        //倒计时毫秒数
        if (end>now){
            return end-now;
        }
        return null;
    }



    /**
     * 计算当前时间（currentDate）后几个工作日（days）的时间
     * @param currentDate 当前时间
     * @param days 工作日
     * @return
     */
    public static String getDate(Date currentDate, int days){
        Calendar calendar= Calendar.getInstance();
        calendar.setTime(currentDate);
        int i=0;
        while(i<days){
            calendar.add(Calendar.DATE,1);//整数往后推日期,负数往前推日期
            i++;
            if(calendar.get(Calendar.DAY_OF_WEEK)==Calendar.SATURDAY ||
                    calendar.get(Calendar.DAY_OF_WEEK)==Calendar.SUNDAY){
                i--;
            }
        }
        SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return dateFormat.format(calendar.getTime());
        //return calendar.getTime();
    }


}
