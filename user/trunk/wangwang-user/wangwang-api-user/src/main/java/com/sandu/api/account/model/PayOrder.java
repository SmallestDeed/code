package com.sandu.api.account.model;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class PayOrder implements Serializable{

    private Integer id;

    /**  用户ID  **/
    private Integer userId;
    /**  产品类型  **/
    private String productType;
    /**  产品ID  **/
    private Integer productId;
    private String productName;
    private String productDesc;

    /**  订单号  **/
    private String orderNo;
    /**  支付金额，单位为分  **/
    private Integer totalFee;
    /***
     * 调节金额
     */
    private Integer  adjustFee=0;
    /**  支出类型  **/
    private String payType;
    /**  支出状态  **/
    private String payState;
    /**  预付id  **/
    private String prepayId;
    /**  编码路径  **/
    private String codeUrl;
    /**  参考id  **/
    private String refId;
    /**  公开Id  **/
    private String openId;
    /**  凭证时间  **/
    private Date orderDate;
    /**业务类别**/
    private String bizType;
    /**财务类别**/
    private String financeType;

    private String tradeType;

    private String storeId;
    /**  系统编码  **/
    private String sysCode;
    /**  创建者  **/
    private String creator;
    /**  创建时间  **/
    private Date gmtCreate;
    /**  修改人  **/
    private String modifier;
    /**  修改时间  **/
    private Date gmtModified;
    /**  是否删除  **/
    private Integer isDeleted;
    /**  字符备用1  **/
    private String att1;
    /**  字符备用2  **/
    private String att2;
    /**  整数备用1  **/
    private Integer numa1;
    /**  整数备用2  **/
    private Integer numa2;
    /**  备注  **/
    private String remark;
    /**渲染任务id*/
    private Integer taskId;
    /**
     *  超时时间
     *  订单失效时间，格式为yyyyMMddHHmmss，如2009年12月27日9点10分10秒表示为20091227091010。其他详见时间规则
     注意：最短失效时间间隔必须大于5分钟
     */
    private String timeoutExpress;

    //方案ID
    private Integer planId;

    /**方案名称*/
    private String planName;

    //户型ID
    private Integer houseId;
    //当前度币
    private Integer currentIntegral;
    //用户度币
    private Integer userTotalIntegral;

    // 平台id
    private Integer platformId;
    //平台名称
    private String platformName;
}
