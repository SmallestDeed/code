package com.sandu.gateway.pay.util;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.security.Security;

public class AESTool {

    /**
     * 密钥算法
     */
    private static final String ALGORITHM = "AES";
    /**
     * 加解密算法/工作模式/填充方式
     */
    private static final String ALGORITHM_MODE_PADDING = "AES/ECB/PKCS7Padding";
    
    //对商户key做md5，得到32位小写key*
    private SecretKeySpec key; 

    //微信支付API密钥设置路径：微信商户平台(pay.weixin.qq.com)-->账户设置-->API安全-->密钥设置
    public AESTool(String mchKey) {
    	key = new SecretKeySpec(MD5Util.MD5Encode(mchKey, "UTF-8").toLowerCase().getBytes(), ALGORITHM);
    }

    /**
     * AES加密
     *
     * @param data
     * @return
     * @throws Exception
     */
    public String encryptData(String data) throws Exception {
        Security.addProvider(new BouncyCastleProvider());
        // 创建密码器
        Cipher cipher = Cipher.getInstance(ALGORITHM_MODE_PADDING, "BC");
        // 初始化
        cipher.init(Cipher.ENCRYPT_MODE, key);
        return Base64Util.encode(cipher.doFinal(data.getBytes()));
    }

    /**
     * AES解密
     *
     *（1）对加密串A做base64解码，得到加密串B
     *（2）用key*对加密串B做AES-256-ECB解密（PKCS7Padding）
     * @param base64Data
     * @return
     * @throws Exception
     */
    public String decryptData(String base64Data) throws Exception {
        Security.addProvider(new BouncyCastleProvider());
        Cipher cipher = Cipher.getInstance(ALGORITHM_MODE_PADDING, "BC");
        cipher.init(Cipher.DECRYPT_MODE, key);
        return new String(cipher.doFinal(Base64Util.decode(base64Data)));
    }

    public static void main(String[] args) throws Exception {
        String A = "微信返回的加密信息req_info";
       // System.out.println(AESUtil.encryptData(A));
        System.out.println(new AESTool("wxb3048NorKad782765143df7NorKcb2").decryptData("9LXgjpa1UXD9Ii7FNGKV3ijHXDEOtLtEOE8bpVbqq1KqtI/0VBM2/aBBTfn+aGDB+UwDaZsNR9tNfnEwEZIXkSCfYpKVTbuibklkjm0VXC5yE4OcknQrn8jkUXFLrvEK03yYj4H7ysEX24dtNPXVttOln69Qf/6rkiWxPvT63wL5NYLxuURep7rMkxrCPBTxnBRwGMkM6pI5IJGWoEenhqkAHudnfMdBR/dOdWJPViHuCqBzct3o9qv0bh9c8xJlYL+5XbX/yU94zin3AMW6zMVxQZDmDDpRXslL5+LR7RGPUoHgupBediFD3C2GgOf1nc8jgMlehh1UZberzQmptlGrpf5WJSmRQ8/6efENIsWMNCFGC4acaZTklbrnPH5u9WWteNVHvJjpaZQ2u2y3E9R6otDgjoIKGLQWWNJrFV/VpFZyjHwjpPkYchSVFsAUoQjjL1rb5WX8eMMg0aYvR6ptOfUD2JYBnxHTqwpzu8tshmlLGd7pxb9Wtc+FdlA/AZdFwwJ+KDoNrpsO8Xq2FDy221IXPAGEONVS5RS7N4ISu9VS/y5gVkjIRA+OVYhQoY2gaubqfiP2gbmR4HI1vLbllXY4yfhinnvzGtDSjf3uV2Il886Jq5CufONwfe0CR8ZnEn+iWCFRFTJwYARsu3rtmdKYVb64bh+IVqs+ZGabT+Ol+Sim1vFi2EjbHFPtrj5tXN2RfxEWRyld2acUOTxQstG7Wv4nVgfmYzHTKI+U7a5w3RiX/AnuOuj6SqWMq7MRrcxWs0S+IWSdrOH7bsrdPeTlktAXe3d1cTyKcEXd12nVUB62cr2zgfFgUqOzxpOZRcRzMWJio408TUbz6jDz3Xp73iTDIxqGmWPf09cQ12lIN7DPnIYh6FD/tRDiaCTeThQDJt71d+AVg1E2FTrDa+6CIglQ1jxua/9V443go29lYmqAexyR5a+yoIs/3Z55eJsZ/tq8+IGNDMoT+mIY04nFIxBQhpkpJzQkvX2xkxiBFHC9G/30qkUKG71CAOQoD2wDKDsz9xKi1A/E2m5Opke9DgIklRzUObmALcV/Y9VfsFiqhPHlmsG045PL"));
    }        
    }