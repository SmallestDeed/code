package app.test;

import com.nork.common.constant.util.SystemCommonUtil;
import com.nork.common.util.HttpClient;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;


public class Test {
    public static void main(String[] args){
        String a  = "26877,26876,26875,26874,26873,26872";
        Integer b = 26876;
        if (a.indexOf(b.toString()) != -1) {
            System.out.println(b);
        }

        //System.out.println(HttpClient.getInstance().sendHttpPost("http://localhost:8080/timeSpace/favorite/add.htm?did=747"));
    }
}
