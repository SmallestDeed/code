package com.sandu.common.exception;


public class ExceptionCodes {
    public static final int CODE_10010001 = 10010001; //平台类型不存在
    public static final int CODE_10010002 = 10010002;//此用户已被限制登录，请联系客服!
    public static final int CODE_10010003 = 10010003;//userImei和usbTerminalImei不能同时为空!
    public static final int CODE_10010004 = 10010004;//数据初始化有问题,请联系客服!
    public static final int CODE_10010005 = 10010005; //设备登录异常,请联系客服!
    public static final int CODE_10010006 = 10010006; //无设备可登录,请联系客服!
    public static final int CODE_10010007 = 10010007; //设备限制类型异常，请联系客服!
    public static final int CODE_10010008 = 10010008; //设备类型不匹配，请重新登录!
    public static final int CODE_10010009 = 10010009;//网卡设备限制数据类型异常!
    public static final int CODE_10010010 = 10010010; //该帐号已绑定其他设备,只能使用初始设备登录!
    public static final int CODE_10010011 = 10010011; //尚未绑定序列号或序列号已过期，请在电脑端绑定后再登录！
    public static final int CODE_10010012 = 10010012; //您尚未开通移动版功能，请联系客服开通!
    public static final int CODE_10010013 = 10010013; //移动版已到期，请续费开通！
    public static final int CODE_10010014 = 10010014; //用户名或密码错误
    public static final int CODE_10010015 = 10010015; //企业不存在
    public static final int CODE_10010016 = 10010016; //企业合同未生效
    public static final int CODE_10010017 = 10010017; //企业合同到期
    public static final int CODE_10010018 = 10010018; //未开通此平台权限
    public static final int CODE_10010019 = 10010019; //平台权限未生效
    public static final int CODE_10010020 = 10010020; //平台权限已到期
    public static final int CODE_10010021 = 10010021;  //创建虚拟经销商企业失败   //未找到该经销商对应企业
    public static final int CODE_10010022 = 10010022; //平台权限已禁用
    public static final int CODE_10010023 = 10010023; //平台权限已结束
    public static final int CODE_10010024 = 10010024; //子账号不能登录PC端
    public static final int CODE_10010025 = 10010025; //主账号不存在,不能登录
    public static final int CODE_10010026 = 10010026; //主账号平台权限未开通或被禁用
    public static final int CODE_10010027 = 10010027; //不是企业用户
    public static final int CODE_10010028 = 10010028; //您不是厂商,无法登录!
    public static final int CODE_10010029 = 10010029; //未找到appId对应企业
    public static final int CODE_10010030 = 10010030; //appId不能为空
    public static final int CODE_10010031 = 10010031; //openId不能为空
    public static final int CODE_10010032 = 10010032; //小程序账号未绑定
    public static final int CODE_10010033 = 10010033; //appid错误或者服务器未配置secret
    public static final int CODE_10010034 = 10010034; //此手机号码已被其他非经销商用户占用
    public static final int CODE_10010035 = 10010035; //当前用户所在企业已存在此经销商用户
    public static final int CODE_10010036 = 10010036; //该手机号已绑定其他经销商账号,是否确认更新所有账号密码!
    public static final int CODE_10010037 = 10010037; //此手机号码已被其他经销商用户占用
    public static final int CODE_10010038 = 10010038; //更新失败:未找到相关记录
    public static final int CODE_10010039 = 10010039; //小程序登录首次赠送度币失败


    public static final int CODE_10010040 = 10010040; //用户失效时间已到期
    public static final int CODE_10010042 = 10010042; //套餐用户失效时间已到期

    public static final int CODE_10010043 = 10010043; //修改密码 =>旧密码错误

    public static final int CODE_10010041 = 10010041; //户型工具版本更新问题



    public static final int CODE_10010200 = 10010200; //该手机号已绑定其他账号!   obj==1 status==true
    public static final int CODE_10010201 = 10010201; //用户类型为空!   obj==0 status==false
    public static final int CODE_10010202 = 10010202; //修改成功!   //obj==0 status==true
    public static final int CODE_10010203 = 10010203; //修改失败!   //obj==0 status==false

    public static final int CODE_10010210 = 10010210; //此手机号已被其他用户占用.
    public static final int CODE_10010211 = 10010211; //该手机号已被其他企业用户(非经销商)占用
    public static final int CODE_10010212 = 10010212; //该手机号已绑定其他经销商账号,确认是否合并. 
    
    
    public static final int CODE_10010230 = 10010230; //更新失败:非经销商用户!


    //同城联盟中介版
    public static final int CODE_10010204 = 10010204; //验证码错误!
    public static final int CODE_10010205 = 10010205; //用户已被b端厂商绑定
    public static final int CODE_10010207 = 10010207; //用户信息为空
    public static final int CODE_10010208 = 10010208; //非中介用户不能登录
    public static final int CODE_10010209 = 10010209; //验证码超时
    public static final int CODE_10010400 = 10010400; //同城联盟注册用户觉得没有配置套餐
    public static final int CODE_10010401 = 10010401; //同城联盟注册用户觉得没有配置套餐
    public static final int CODE_10010402 = 10010402; //同城联盟注册用户邀请码不存在

    public static final int CODE_10010301 = 10010301; // 找不到用户

    //新套餐
    public static final int CODE_10010302 = 10010302; // 套餐过期
    public static final int CODE_10010303 = 10010303; // 科创,抢工长套餐过期



    public static final int CODE_10010206 = 10010206; //非同一企业的经销商手机号允许重复，其他账号手机号不允许重复!   obj==0 status==false

}
