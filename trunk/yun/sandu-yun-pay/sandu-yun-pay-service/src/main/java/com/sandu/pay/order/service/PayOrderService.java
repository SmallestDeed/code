package com.sandu.pay.order.service;

import com.sandu.pay.base.model.BasePlatform;
import com.sandu.pay.order.metadata.RenderCheckVo;
import com.sandu.pay.order.metadata.RenderPriceInfoVo;
import com.sandu.pay.order.metadata.ScanPayReqData;
import com.sandu.pay.order.model.PayModelConfig;
import com.sandu.pay.order.model.PayOrder;
import com.sandu.pay.order.model.PayOrderModel;
import com.sandu.pay.order.model.ResultMessage;
import com.sandu.pay.order.model.search.PayOrderSearch;
import com.sandu.pay.order.vo.PayMobileLoginVo;
import com.sandu.pay.order.vo.PayModelConfigVo;
import com.sandu.product.model.MobileProductReplace;
import com.sandu.system.model.SysDictionary;
import com.sandu.system.model.task.SysTask;
import com.sandu.user.model.LoginUser;
import com.sandu.user.model.SysUser;

import java.net.UnknownHostException;
import java.util.List;


/**
 * Created by Administrator on 2017/9/19.
 */
public interface PayOrderService {

    /**
     * 通过主键查询
     * @param id
     * @return
     */
    PayOrder get(Integer id);

    /**
     * 更新订单信息你
     * @param payOrder 订单
     * @return
     */
    Integer update(PayOrder payOrder);

    /**
     * 创建订单
     * @param order
     * @return 订单ID
     */
    Integer addPayOrder(PayOrder order, LoginUser loginUser);

    /**
     * 更新订单状态
     * @param order
     * @return
     */
    Integer updatePayState(PayOrder order);

    /**
     * 通过订单号查询订单信息
     * @param orderNo
     * @return
     */
    PayOrder getOrderByOrderNo(String orderNo);

    /**
     *    获取数据数量
     *
     * @param  payOrderSearch
     * @return   int
     */
    int getCount(PayOrderSearch payOrderSearch);

    /**
     *    分页获取数据
     *
     * @param  payOrdertSearch
     * @return   List<PayOrder>
     */
    List<PayOrder> getPaginatedList(PayOrderSearch payOrdertSearch);

    /**
     *    获取所有数据
     *
     * @param  payOrder
     * @return   List<PayOrder>
     */
    List<PayOrder> getList(PayOrder payOrder);

    /**
     * 度币充值----2c移动端(APP支付)-----------------
     *
     * @param productId
     * @param totalFee
     * @param payType
     * @param loginUser
     * @param sysDictionary
     * @param platformCode
     * @return
     */
    ResultMessage rechargeIntegralByAppPay(Integer productId, int totalFee, String payType, LoginUser loginUser, SysDictionary sysDictionary, String platformCode);
    ResultMessage getRechargeQrCodePath(Integer productId, int totalFee, String payType, LoginUser loginUser, SysDictionary sysDictionary, String platformCode);


    /**
     * 获取支付二维码信息
     * @param loginUser
     * @param productDesc
     * @param productName
     * @param productType
     * @param productId
     * @param payType
     * @param totalFee
     * @param msgId
     * @return
     */
    ResultMessage getPayScanOrderUrlInfo(Integer totalFee, String payType, Integer productId, String productType, String productName, String productDesc, LoginUser loginUser, String msgId, String platformCode);

    /**
     * 微信支付回调
     * @param sbResult
     * @param success
     * @return
     */
    boolean wxPayCallbackUpdateInfo(String sbResult, boolean success);

    /**
     * 更新支付宝支付回调订单信息
     * @param order
     */
    boolean updateAliPayCallbackInfo(PayOrder order);

    /**
     * 渲染失败退款到账户余额
     * @param sysTask
     * @param renderErroMsg
     */
    void renderRefund(SysTask sysTask, String renderErroMsg);

    /**
     * 给客户端发送订单支付状态信息,更新任务状态，退款
     * @param orderNo
     * @return
     */
    boolean sendPayStateNew(String orderNo);

    /**
     * 余额支付
     * @param userId
     * @param totalFee
     * @param payType
     * @param productId
     * @param productType
     * @param productName
     * @param productDesc
     * @return
     */
    ResultMessage getAddPredepositPay(Integer userId, Integer totalFee, String payType, Integer productId, String productType, String productName, String productDesc, String platformCode);

    /**
     * 运维网站度币支付渲染任务
     * @param userId
     * @param renderType
     * @param planId
     * @param houseId
     * @return
     */
    ResultMessage addIntegralPayRenderTask(Integer userId, Integer renderType, Integer planId, Integer houseId, String platformCode);

    /**
     * 扩展户型数订单
     * @param userId
     * @param renderType
     * @param planId
     * @param houseId
     * @return
     */
    ResultMessage addExpandHouseOrder(Integer userId, Integer payType, String platformCode);

    /**
     * 通知退款，非官方退款
     * @param orderNo
     * @param userId
     * @return
     */
    ResultMessage notifyRefund(String orderNo, Integer userId);


    /**
     * 消费明细数量
     * @param payOrderSearch
     * @return
     */
    int getExpenseRecordCount(PayOrderSearch payOrderSearch);

    /**
     * 消费明细列表
     * @param payOrderSearch
     * @return
     */
    List<PayOrder> getExpenseRecordList(PayOrderSearch payOrderSearch);

    /**
     * 更新渲染订单状态
     * @param orderNo （渲染订单号）
     * @param falg （true渲染成功更新状态 false渲染失败 更新状态退款）
     * @return
     */
    ResultMessage updateRenderPayState(String orderNo, boolean falg);

	int updateTaskId(PayOrder payOrder);

	/**
	 * 渲染任务状态变更
	 * @param sysTask
	 * @param loginUser
	 * @param payType
	 * @param payState
	 * @param msgSendIsSuccess
	 * @return
	 */
    String updateNonPaymentTaskNew(SysTask sysTask, LoginUser loginUser, String payType, String payState, Boolean msgSendIsSuccess);

    /**
     * 回填
     *
     * */
    boolean updatePayoOrder(String orderNo, Integer taskId);

    /**
	 * 替换记录
	 * @param model
	 * @return
	 * @throws UnknownHostException
	 */
	public Object replaceRecord(MobileProductReplace model, String platformCode) throws UnknownHostException;

	/**
    *
    * 移动端获取微信app支付下单接口
    *
    * @param payMobileLoginVo
    * @return
    */

   public ResultMessage unifiedOrderForWxAppPay(PayMobileLoginVo payMobileLoginVo, String platformCode);


    /**
     *
     * 获取微信h5支付需要重定向的url
     *
     * @param payMobileLoginVo
     * @return
     */
    public ResultMessage getMobilePostponeWeChatPayUrl(PayMobileLoginVo payMobileLoginVo, String platformCode);

    /**
     *
     * 移动端度币支付开通移动端登录
     *
     * @param payMobileLoginVo
     * @return
     */
    public ResultMessage addMobileLoginIntegralPay(PayMobileLoginVo payMobileLoginVo, String platformCode);

    /**
     *
     * 移动端支付宝网页支付开通移动端登录
     *
     * @param payMobileLoginVo
     * @return
     */
    public ResultMessage addPostponeAlipay(PayMobileLoginVo payMobileLoginVo, String platformCode);

    public ResultMessage addPostponeAppAlipay(PayMobileLoginVo payMobileLoginVo, String platformCode);

    /**
     *
     * 移动端的开通移动端登录支付成功后更新用户信息
     *
     * @param orderNo 订单号
     */
    public void updateUserMobileInfo(String orderNo);

    /**
	 * 判断渲染免费时间段和返回免费时间段
	 * @return
	 */
	public RenderCheckVo renderFreeTimeInfo();
	/**
	 * 根据渲染类型查询价格列表和免费信息
	 * @return
	 */
	public List<RenderPriceInfoVo> findPriceInfo(Integer renderingType);

    /**
     *
     * 移动端的开通支付回调(微信h5支付)
     *
     * @param sbResult
     * @param success
     * @return
     */
    public boolean wxNotifyMobilePay(String sbResult, boolean success);

    /**
     *
     * 移动端登录支付成功的通知
     *
     * @param orderNo 订单号
     * @return
     */
    boolean wxNotifyMobilePaySend(String orderNo);

    public PayOrder getOrder(int totalFee, String payType, int productId, String productType, String productName, String productDesc, String tradeType);

    public void sysSave(PayOrder order, LoginUser loginUser);
    /**
     * 新增订单
     * */
    public int add(PayOrder payOrder);
    /**
     * 更新度币
     * */
    public boolean updateUserFinance(String orderNo);
    /**
     *add by yanghz

     * processAfterConsume 方法描述：  根据消费金额判断是否需要对用户进行自动升级

     * @param orderNum  订单号

     * @since  CodingExample　Ver(编码范例查看) 1.1
     */
    public void processAfterConsume(String orderNum);
    /**
	 * 得到未支付订单的数量，自动过滤取最近5分钟
	 * @author huangsongbo
	 * @param id
	 * @param paying
	 * @return
	 */
	public int getCountByUserIdAndStatus(Integer userId, String status);

	public ResultMessage addScanpayOrderPc(PayOrder payOrder, ScanPayReqData reqData);

	public ResultMessage addScanpayOrderPc(PayOrder payOrder, String productName, double totalFee, LoginUser loginUser, Long userId, ScanPayReqData reqData)throws Exception;

	public ResultMessage sendMessageAndCreateOrderNewPc(Integer productId, String productType, String productName,
                                                        String productDesc, String payType, Long userId, String msgId,
                                                        ResultMessage message, String level, Integer isTurnOn, Integer planId, Integer priority,
                                                        Integer viewPoint, Integer scene, Integer renderingType, String temporaryPic, String type, Integer mode, LoginUser loginUser, String platformCode);


	/**
	 * 5.6版本 创建一个未付款状态的渲染任务
	 * @param isTurnOn
	 * @param planId
	 * @param params
	 * @param priority
	 * @param viewPoint
	 * @param scene
	 * @param renderingType
	 * @param renderChanne
	 * @param priceInfo
	 * @param request
	 * @return
	 */
	public SysTask createNonPaymentTaskNew(Integer isTurnOn, Integer planId, Integer priority,
                                           Integer viewPoint, Integer scene, Integer renderingType, String priceInfo, LoginUser loginUser);


    /**
     * 微信扫码支付成功插入数据到付款方式业务关联表
     *
     * @param sbResult
     * @param success
     * @return
     */
    public boolean wxNotifyConfigPay(String sbResult, boolean success);

    /**
     *  购买渲染包年包月订单
     *
     * @param payModelConfigVo
     * @param userId 用户id
     * @param platformId 平台id
     * @return
     */
    public ResultMessage addRenderPayModelConfig(PayModelConfigVo payModelConfigVo, Integer userId, Integer platformId);

    /**
     * 包年包月生成渲染订单
     *
     * @param platformId  平台id
     * @param userId 用户id
     * @param payModelConfigVo
     * @return
     */
    public ResultMessage addRenderPayOrder(Integer platformId, Integer userId, PayModelConfigVo payModelConfigVo);

    /**
     * 付款成功之后的websocket通知
     *
     * @param orderNo 订单号
     * @return
     */
    public boolean sendRenderPayStateNew(String orderNo);

    /**
     * 2c度币充值（微信h5支付，支付宝手机网页支付）
     *
     * @param userId
     * @param payOrderModel
     * @return
     */
    public ResultMessage rechargeIntegralByH5Pay(Integer userId, PayOrderModel payOrderModel, String platformCode);

    /**
     * 包年包月生成渲染订单(移动端专用)
     *
     * @param platformId  平台id
     * @param userId 用户id
     * @param payModelConfigVo
     * @return
     */
    public ResultMessage addRenderOrder(Integer platformId, Integer userId, PayModelConfigVo payModelConfigVo);

    /**
     * 使用度币共享生成渲染订单接口（移动2b端使用）
     *
     * @param platformId 平台id
     * @param userId 用户id
     * @param mobileProductReplace
     * @return
     */
    public ResultMessage addIntegralShareRenderOrder(Integer platformId, Integer userId, MobileProductReplace mobileProductReplace);

    /**
     * 使用度币共享生成渲染订单接口（pc端使用）
     *
     * @param platformId 平台id
     * @param userId 用户id
     * @param payModelConfigVo
     * @return
     */
    public ResultMessage addIntegralShareRenderOrderPc(Integer platformId, Integer userId, PayModelConfigVo payModelConfigVo);

    /**
     * 使用度币共享生成购买户型订单
     *
     * @param userId 用户id
     * @param expandType
     * @param platformId 平台id
     * @return
     */
    public ResultMessage addIntegralShareOrder(Integer userId, Integer expandType, Integer platformId);

    /**
     * 度币共享充值（pc端专用）
     *
     * @param userId 用户id
     * @param platformId 平台id
     * @param payOrderModel
     * @return
     */
    public ResultMessage addIntegralShareRechargeOrderPc(Integer userId, Integer platformId, PayOrderModel payOrderModel);

    /**
     * 微信扫码支付处理（度币共享的pc端充值）
     *
     * @param sbResult
     * @param success
     * @return
     */
    public boolean wxNotifySharePayPc(String sbResult, boolean success);

    /**
     * 度币共享开通移动端（移动2b专用）
     *
     * @param platformId 平台id
     * @param payMobileLoginVo
     * @return
     */
    public ResultMessage addIntegralShareLoginPay(Integer platformId, PayMobileLoginVo payMobileLoginVo);

    /**
     * 预付款支付购买产品（度币付款，包含生成渲染订单）
     *
     * @param productId 产品id
     * @param productType 产品类型
     * @param productName 产品名称
     * @param productDesc 产品详情
     * @param totalFee 总价钱
     * @param mode
     * @param type
     * @param isTurnOn
     * @param planId
     * @param viewPoint
     * @param scene
     * @param msgId
     * @param renderingType
     * @param platform 平台对象
     * @param level
     * @param priority
     * @param temporaryPic
     * @param orderNo 订单号
     * @param authorization
     * @param userId 用户id
     * @param PAYINGORDERMAX 未支付订单最大允许数
     * @return
     */
    public ResultMessage addpredepositpayPc(Integer productId,String productType, String productName,String productDesc,
                                            Integer totalFee,  Integer mode,String type,Integer isTurnOn,Integer planId,Integer viewPoint,Integer scene,
                                            String msgId,String renderingType,BasePlatform platform,
                                            String level,  Integer priority,String temporaryPic, String orderNo,String authorization,Integer userId,
                                          final Integer PAYINGORDERMAX);

    /**
     * pc端充值（微信和支付宝扫码支付）
     *
     * @param totalFee 总价钱
     * @param adjustFee 赠送金额
     * @param payType 支付类型
     * @param productId 产品id
     * @param msgId
     * @param userId 用户id
     * @param basePlatform 平台对象
     * @return
     */
    public ResultMessage addscanrechargePc(double totalFee,double adjustFee,String payType,Integer productId,String msgId,
                                           Integer userId, BasePlatform basePlatform) throws Exception;

    /**
     * 获取度币共享的主子账号的消费记录总条数
     *
     * @param payOrderSearch
     * @return
     */
    int getShareCount(PayOrderSearch payOrderSearch);

    /**
     * 获取度币共享的主子账号的消费记录列表
     *
     * @param payOrderSearch
     * @return
     */
    List<PayOrder> getSharePaginatedList(PayOrderSearch payOrderSearch);

    /**
     * 获取度币共享的主子账号的消费记录总条数
     *
     * @param payOrderSearch
     * @return
     */
    int getShareCountPc(PayOrderSearch payOrderSearch);

    /**
     * 获取度币共享的主子账号的消费记录列表
     *
     * @param payOrderSearch
     * @return
     */
    List<PayOrder> getSharePaginatedListPc(PayOrderSearch payOrderSearch);

    /**
     *  购买渲染包年包月订单（经销商用户专用）
     *
     * @param payModelConfigVo
     * @param userId 用户id
     * @param platformId 平台id
     * @return
     */
    public ResultMessage addRenderPayModelConfigFranchiser(PayModelConfigVo payModelConfigVo, Integer userId, Integer platformId);

    /**
     * 包年包月生成渲染订单（经销商用户专用）
     *
     * @param platformId  平台id
     * @param userId 用户id
     * @param payModelConfigVo
     * @return
     */
    public ResultMessage addRenderPayOrderFranchiser(Integer platformId, Integer userId, PayModelConfigVo payModelConfigVo);

    /**
     * 包年包月生成渲染订单(运营网站和移动端的经销商用户专用)
     *
     * @param platformId  平台id
     * @param userId 用户id
     * @param payModelConfigVo
     * @return
     */
    public ResultMessage addRenderOrderFranchiser(Integer platformId, Integer userId, PayModelConfigVo payModelConfigVo);

    /**
     * 2b购买包年包月接口（支付宝手机网页支付和微信h5支付）
     *
     * @param payModelConfigVo
     * @param userId 用户id
     * @param platformId 平台id
     * @return
     */
    public ResultMessage rechargePayModelByH5Pay(PayModelConfigVo payModelConfigVo, Integer userId, Integer platformId);

    /**
     * 2b购买包年包月接口（支付宝和微信APP支付）
     *
     * @param payModelConfigVo
     * @param userId 用户id
     * @param platformId 平台id
     * @return
     */
    public ResultMessage rechargePayModelByAppPay(PayModelConfigVo payModelConfigVo, Integer userId, Integer platformId);

    /**
     * 2b度币充值（微信h5支付，支付宝手机网页支付）
     *
     * @param userId 用户id
     * @param payOrderModel
     * @return
     */
    public ResultMessage rechargeIntegralByH5Pay2b(Integer userId, PayOrderModel payOrderModel, String platformCode);

    /**
     * 2b度币充值（微信APP支付，支付宝APP支付）
     *
     * @param userId 用户id
     * @param payOrderModel
     * @return
     */
    public ResultMessage rechargeIntegralByAppPay2b(Integer userId, PayOrderModel payOrderModel, String platformCode);

    /**
     *
     * 判断pc端渲染参数是否合法
     *
     * @param payModelConfigVo
     * @return
     */
    public ResultMessage checkRenderParameter(PayModelConfigVo payModelConfigVo);

    /**
     * pc端品牌商家免费渲染
     *
     * @param basePlatform
     * @param sysUser
     * @param payModelConfigVo
     * @return
     */
    public ResultMessage addRenderPayOrderFree(BasePlatform basePlatform, SysUser sysUser, PayModelConfigVo payModelConfigVo);

    /**
     * 移动端品牌商家免费渲染
     *
     * @param basePlatform
     * @param sysUser
     * @param payModelConfigVo
     * @return
     */
    public ResultMessage addRenderOrderFree(BasePlatform basePlatform, SysUser sysUser, PayModelConfigVo payModelConfigVo);

    /**
     * 厂商免费购买户型生成订单
     *
     * @param basePlatform
     * @param sysUser
     * @param expandType
     * @return
     */
    public ResultMessage addRenderOrderFree(BasePlatform basePlatform, SysUser sysUser, Integer expandType);

    /**
     * 运营网站渲染
     *
     * @param sysUser
     * @param renderType
     * @param planId
     * @param houseId
     * @param basePlatform
     * @return
     */
    public ResultMessage addCompanyPayRenderTask(SysUser sysUser, Integer renderType, Integer planId, Integer houseId, BasePlatform basePlatform);


    //小程序回调服务
    public boolean wxMiniPayCallbackUpdateInfo(String sbResult, boolean success);

    /**
     * 2c度币充值小程序支付
     *
     * @param sysUser
     * @param basePlatform
     * @param payOrderModel
     * @return
     */
    public ResultMessage rechargeIntegralByMiniPay2c(SysUser sysUser,BasePlatform basePlatform, PayOrderModel payOrderModel);

    /**
     * 通过订单号删除订单
     *
     * @param orderNo 订单号
     * @return
     */
    int deleteByOrderNo(String orderNo);

    List<PayModelConfig> getPayModelConfig2C();

    String add2CPackagePayOrder(Integer id, PayModelConfigVo payModelConfigVo, BasePlatform basePlatform);

    int getExpenseRecordCountV2(PayOrderSearch payOrderSearch);

    List<PayOrder> getExpenseRecordListV2(PayOrderSearch payOrderSearch);

    ResultMessage agencyPayModelByAppPay(PayModelConfigVo payModelConfigVo, Integer userId, Integer id);

    ResultMessage agencyRechargeIntegralByAppPay(Integer userId, PayOrderModel payOrderModel, String platformCode);
}
