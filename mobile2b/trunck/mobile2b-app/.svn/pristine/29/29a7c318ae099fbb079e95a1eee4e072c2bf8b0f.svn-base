/**
 * 
 */
package app.test;

import java.io.*;

/**
 * @author Administrator
 *
 */
public class AESTest {
	private static String key="41e5c74dd46e4ddcb942dc8ce6224a2e";
    public static void main(String[] args) {  
//    	//System.out.println(UUID.randomUUID().toString().replace("-", ""));
    	
    	long startTime = System.currentTimeMillis();
        InputStream in=null;
		try {
			
			File file = new File("e:/test/101914_20170614165116846.assetbundle");  
			in = new FileInputStream(file);//读取留文件
			writeToLocal("e:/test/aes/a_101914_20170614165116846.assetbundle",in);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (null != in)
					in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		//System.out.println(" 耗时  "+(System.currentTimeMillis()-startTime)+" ms");
    }  
    /** 
     * 流写入本地文件 
     *  
     * @param destination  结果输出到目标文件
     * @param input  输入流
     * @throws IOException 
     */  
    public static void writeToLocal(String destination, InputStream input) {
        int index;  
        byte[] bytes = new byte[1024];  
        
        File outFile = new File(destination);
        if(!outFile.exists())//文件不存在则创建目录
        	outFile.getParentFile().mkdirs();
        
        FileOutputStream oupPut = null;
        try{
        	oupPut =new FileOutputStream(outFile);  //输出文件
        	
    		oupPut.write(key.getBytes(), 0, key.getBytes().length);
    		oupPut.flush();
    		
        	while ((index = input.read(bytes)) != -1) {  //每次读取1024byte，直到返回-1表示结束
        		/*for (int i = 0;!once && i<index;i++) {
        			bytes2[i] = (byte) (bytes[i]^key.charAt(i %keyLength));// 每个byte和key对应位置做疑惑， 由于是异或运算，所以，在明文运行为加密，在密文上运行为解密
        		} */
        		oupPut.write(bytes, 0, index);
        		oupPut.flush();  
        	}  
        	
        	
        }catch(Exception e){
        	e.printStackTrace();
		} finally {
			try {
				if (null != oupPut)
					oupPut.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
    }  
}  
