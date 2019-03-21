package app.test;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.JavaIdentifierTransformer;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import com.nork.common.util.CryptUtils;
import com.nork.common.util.IgnoreJsonPropertyFilter;
import com.nork.design.model.unity.RoomConfig;
import com.nork.design.model.unity.Root;
import com.nork.product.model.JsonProduct;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageDecoder;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

/**
 * Created by Administrator on 2015/7/6.
 */
public class TestClient {

    private static void HttpPostDataLogin() {
        try {

            HttpClient httpclient = new DefaultHttpClient();
//            String uri = "http://localhost:8081/onlineDecorate/jsp/product/proCategory/searchProCategory.htm?type=A";
           //String uri = "http://192.168.1.230:8080/onlineDecorate/small/design/usedProducts/saveInterface.htm";
           // String uri = "http://localhost:8080/onlineDecorate/small/product/userProductCollect/saveInterface.htm";
           // String uri = "http://localhost:8080/onlineDecorate/small/design/designPlanComment/getUdpcjsplist.htm";
//            String uri = "http://localhost:8081/onlineDecorate/small/design/usedProducts/usedProductList.htm";
          // String uri = "http://localhost:8080/onlineDecorate/small/product/userProductCollect/jsplistInterface.htm";
           // String uri = "http://localhost:8080/onlineDecorate/small/design/designPlanComment/saveInterface.htm";
//            String uri = "http://localhost:8081/onlineDecorate/small/design/designPlanComment/getCommentList.htm";
//            String uri = "http://localhost:8081/onlineDecorate/small/product/baseProduct/get.htm";
          //  String uri = "http://localhost:8080/onlineDecorate/small/design/designPlanCollect/saveSchemeFavorites.htm";
           // String uri = "http://localhost:8080/onlineDecorate/small/design/designPlanCollect/schemeFavoritesList.htm";
//            String uri = "http://localhost:8080/onlineDecorate/small/design/usedProducts/del.htm";
             String uri = "http://localhost:8081/onlineDecorate/small/design/designPlanCollect/del.htm";
            HttpPost httppost = new HttpPost(uri);

            // 添加http头信息
            String username = "{\"appKey\":\"1234\",\"token\":\"90636232048680698834131992195824\",\"msgId\":\"login\",\"deviceId\":\"1\"}";

            String author = CryptUtils.encryptBASE64((username).getBytes()).replace("\r\n","");
            // "your token"
            httppost.addHeader("Authorization", author); // 认证token

            httppost.addHeader("Content-Type", "application/json");

            httppost.addHeader("User-Agent", "imgfornote");

            // http post的json数据格式： {"longinname": "saadmin","passwd": "admin"}

//            JSONObject obj = new JSONObject();
//            obj.put("msgId","123");
//            obj.put("productId","63");
            //obj.put("content","乱码");
//            obj.put("id","1");
//            obj.put("designName","华润样板房A1");
//            obj.put("srcCode","B6556391828");
//            obj.put("srcType","6");
//            obj.put("userId","1");
//            obj.put("type", null);
//            obj.put("cid", null);


//            httppost.setEntity(new StringEntity(obj.toString()));

            HttpResponse response;

            response = httpclient.execute(httppost);

            // 检验状态码，如果成功接收数据
            int code = response.getStatusLine().getStatusCode();

            //System.out.println("============code:"+code);

            if (code == 200) {
                String rev = EntityUtils.toString(response.getEntity());
                //System.out.println(rev);
                // 返回json格式：
                // {"success":true,"message":"ok","data":null,"totalSize":0,"totalCount":0,"state":true,"list":null,"result":null,"obj":true}

                JSONObject obj2 = JSONObject.fromObject(rev);
                //System.out.println("return=" + obj2);

                String success = obj2.getString("success");
                //System.out.println("success=" + success);

                String message = obj2.getString("message");
                //System.out.println("message=" + message);
                Object o = (Object)obj2.getString("obj");
                //System.out.println("obj=" + obj2.getString("obj"));
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void json(){
        //读取配置文件中的内容
        StringBuffer sb = new StringBuffer();
        try {
            FileReader fr = new FileReader("F://1.txt");
            BufferedReader br = new BufferedReader(fr);
            String str = "";
            while( ( str = br.readLine() ) != null ){
                sb.append(str);
            }
            br.close();
            fr.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //将配置文件中的内容转换成json格式
        JSONObject jsonObject = JSONObject.fromObject(sb.toString());
        JSONArray jsonArray = (JSONArray)jsonObject.get("RoomConfig");
        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.setRootClass(JsonProduct.class);
        //处理json中key的首字母大写情况
        jsonConfig.setJavaIdentifierTransformer(new JavaIdentifierTransformer() {
            @Override
            public String transformToJavaIdentifier(String s) {
                char[] chars = s.toCharArray();
                chars[0] = Character.toLowerCase(chars[0]);
                return new String(chars);
            }
        });
        //过滤在json中Entity没有的属性
        jsonConfig.setJavaPropertyFilter(new IgnoreJsonPropertyFilter());
        List<JsonProduct> productCodes = (List<JsonProduct>) JSONArray.toCollection(jsonArray, jsonConfig);
        //System.out.println(productCodes.size());
//        JSONObject jsonObject1 = JSONObject.fromObject(jsonArray.get(0), jsonConfig);
//        JsonProduct jsonProduct = (JsonProduct) JSONObject.toBean(jsonObject1, JsonProduct.class);
//        //System.out.println(jsonProduct.getLinkPosition().getX());
    }

    public static void main(String[] args) {
    	
    	HttpPostDataLogin();
//        TestClient.test();
//        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
//        try {
//            Calendar c = Calendar.getInstance();
//            c.setTime(format.parse("2015-11-08"));
//            int monday = c.get(Calendar.DAY_OF_WEEK) - 1;
//            if (monday == 0)
//                monday = 7;
//            c.add(Calendar.DATE, -monday + 1);
//            //System.out.println(format.format(c.getTime()));
//
//            Calendar c1 = Calendar.getInstance();
//            c1.setTime(format.parse("2016-01-8"));
//            int sunday = c1.get(Calendar.DAY_OF_WEEK) - 1;
//            if (sunday == 0)
//                sunday = 7;
//            c1.add(Calendar.DATE, -sunday + 7);
//            //System.out.println(format.format(c1.getTime()));
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//        String imagePath = "f://1.jpg";
//        ImageUtils.pressText("嘿嘿嘿11111111",imagePath,ImageUtils.LEFT_DOWN);
//        TestClient.sy();
    }

    public static void sy(){
        try {
            String imgPath = "f://1.jpg";
            //1.jpg是你的 主图片的路径
            InputStream is = new FileInputStream(imgPath);


            //通过JPEG图象流创建JPEG数据流解码器
            JPEGImageDecoder jpegDecoder = JPEGCodec.createJPEGDecoder(is);
            //解码当前JPEG数据流，返回BufferedImage对象
            BufferedImage buffImg = jpegDecoder.decodeAsBufferedImage();
            //得到画笔对象
            Graphics g = buffImg.getGraphics();

            //创建你要附加的图象。
            //2.jpg是你的小图片的路径
//            ImageIcon imgIcon = new ImageIcon("2.jpg");

            //得到Image对象。
//            Image img = imgIcon.getImage();

            //将小图片绘到大图片上。
            //5,300 .表示你的小图片在大图片上的位置。
//            g.drawImage(img, 5, 330, null);


            //设置颜色。
            g.setColor(Color.BLACK);


            //最后一个参数用来设置字体的大小
            Font f = new Font("宋体", Font.BOLD, 30);

            g.setFont(f);
            int height_biao = g.getFontMetrics(g.getFont()).getHeight();
            //10,20 表示这段文字在图片上的位置(x,y) .第一个是你设置的内容。
            g.drawString("默哀555555。。。。。。。", 25, height_biao);

            g.dispose();


            OutputStream os = new FileOutputStream(imgPath);

            //创键编码器，用于编码内存中的图象数据。

            JPEGImageEncoder en = JPEGCodec.createJPEGEncoder(os);
            en.encode(buffImg);


            is.close();
//            os.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        //System.out.println ("合成结束。。。。。。。。");
    }

    public static void test(){
        StringBuffer sb = new StringBuffer();
        try {
            FileReader fr = new FileReader("f://225596_20160107220453533.txt");
            BufferedReader br = new BufferedReader(fr);
            String str = "";
            while( ( str = br.readLine() ) != null ){
                sb.append(str);
            }
            br.close();
            fr.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //将配置文件中的内容转换成json格式
//        JSONObject jsonObject = JSONObject.fromObject(sb.toString());
//        JSONArray jsonArray = (JSONArray)jsonObject.get("RoomConfig");
//        JsonConfig jsonConfig = new JsonConfig();
//        jsonConfig.setRootClass(RoomConfig.class);
//        //处理json中key的首字母大写情况
//        jsonConfig.setJavaIdentifierTransformer(new JavaIdentifierTransformer() {
//            @Override
//            public String transformToJavaIdentifier(String s) {
//                char[] chars = s.toCharArray();
//                chars[0] = Character.toLowerCase(chars[0]);
//                return new String(chars);
//            }
//        });
//        //过滤在json中Entity没有的属性
//        jsonConfig.setJavaPropertyFilter(new IgnoreJsonPropertyFilter());
//        List<RoomConfig> productCodes = (List<RoomConfig>) JSONArray.toCollection(jsonArray, jsonConfig);
//        //System.out.println(productCodes.size());
        JSONObject jsonObject = JSONObject.fromObject(sb.toString());
        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.setRootClass(RoomConfig.class);
        //处理json中key的首字母大写情况
        jsonConfig.setJavaIdentifierTransformer(new JavaIdentifierTransformer() {
            @Override
            public String transformToJavaIdentifier(String s) {
                char[] chars = s.toCharArray();
                chars[0] = Character.toLowerCase(chars[0]);
                return new String(chars);
            }
        });
        //过滤在json中Entity没有的属性
        jsonConfig.setJavaPropertyFilter(new IgnoreJsonPropertyFilter());
        Root root = new Root();
        Object mainCameraIndexPathObj = jsonObject.get("MainCameraIndexPath");
        if( mainCameraIndexPathObj != null ) {
            root.setMainCameraIndexPath(mainCameraIndexPathObj.toString());
        }
        JSONArray jsonArray = (JSONArray)jsonObject.get("RoomConfig");
        List<RoomConfig> roomConfigs = (List<RoomConfig>) JSONArray.toCollection(jsonArray, jsonConfig);
        root.setRoomConfig(roomConfigs);
        //System.out.println(JSONObject.fromObject(root).toString());
    }
}
