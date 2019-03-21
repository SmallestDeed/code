
import request from './request.js'
const myselfAPI = {
  getUserDuBiMessage(params = {}) { // 获取用户度币信息 
    return request('payUrl').get('/web/system/sysUser/getUserBalance', params)
  },
  getIsBindingMobile(params = {}) { // 检验手机号是否存在
    return request('userUrl').get('/v2/user/center/isUserMobileBinded', params)
  },
  getUserOrderList(params = {}) { // 获取用户订单列表
    return request('baseUrl').get('/order/dynamicQueryOrder', params)
  },
  getUserOrderDetails(params = {}) { // 获取用户订单详情
    return request('baseUrl').get('/order/getOrderByOrderId', params)
  },
  getIsShowSearchPopup(params = {}) { // 获取搜索户型指引弹窗
    return request('baseUrl').get('/user/sysuser/guideStep',params)
  },
  getCloseSearchKnow(params = {}) { // 获取搜索户型点击知道指引
    return request('baseUrl').post('/user/sysuser/guideStep', params)
  },
  cancelUserOrder(params = {}) { // 取消用户代付款订单
    return request('baseUrl').get('/order/updateorderstatus', params)
  },
  getUserDuBiList(params = {}) { // 获取度币明细列表
    return request('payUrl').get('/web/pay/payOrder/findExpenseRecordList', params)
  },
  getuserIsPackageInMonthly(params = {}) { // 获取用户包年包月信息
    return request('payUrl').get('/web/pay/checkRenderGroopRef2C', params)
  },
  getPackagesParams(params = {}) { // 获取包年包月参数
    return request('payUrl').get('/web/pay/getPackages', params)
  },
  dredgePackageInMonthly(params = {}) { // 开通包年包月服务
    return request('payUrl').formData('/web/pay/miniProPayOrder/packagePay', params)
  },
  getRechargeIntegral(params = {}) { // 获取度币充值选择参数
    return request('payUrl').post('/web/pay/payOrder/getRechargeIntegral', params)
  },
    getPayment(params = {}){
        return request('renderUrl').post('/render/server/payDesignPlanCopyRight.htm', params)
    },
  getUserRechargeParams(params = {}) { // 获取用户充值度币参数
    return request('payUrl').formData('/web/pay/miniProPayOrder/recharge', params)
  },
  getHouseTypeSpacePatterning(params = {}) { // 获取空间图形筛选条件
    return request('baseUrl').get('/designplan/getspaceshape', params)
  },
  getHouseTypeSpaceList(params = {}) { // 搜索空间列表
    return request('baseUrl').get('/home/spacecommon/spacesearch', params)
  },
  getUserHouseTypeList(params = {}) { // 搜索用户户型列表
    return request('baseUrl').get('/userresources/finduseraccounts', params)
  },
  getBuyHouseChooseParams(params = {}) { // 获取购买户型选择参数
    return request('baseUrl').get('/home/basehouse/getexpandhousedict', params)
  },
  getUserHouseTypeMesasage(params = {}) { // 获取用户户型信息
    return request('payUrl').get('/web/system/sysUser/getUserBalance', params)
  },
  buyHOuseType(params = {}) { // 购买户型
    return request('payUrl').get('/web/pay/payOrder/expandhouse', params)
  },
  getMyDegianPlan(params = {}) { // 获取我的装修
    return request('baseUrl').get('/autorender/mydecorationplan', params)
  },
  getUserPlanCount(params = {}){  //我的方案数量
    return request('baseUrl').get('/autorender/planCount',params)
  },
  getAllHouse( params = {}){  //我的装修里获取户型小区
    return request('baseUrl').get('/autorender/getAllHouse',params)
  },
  getAskState(params ={}){  //我的方案里点击红点
    return request('baseUrl').get('/autorender/setAskState/' + params.askType + '/' + params.taskId)
  },
  getDesignPlanResourceId(params = {}) { // 获取设计方案视频资源ID
    return request('renderUrl').post('/mobile/renderPic/getPictureList.htm', params)
  },
  getDesignPlanResource(params = {}) { // 获取设计方案视频资源
    return request('renderUrl').post('/mobile/design/designPlan/getPanoPicture.htm', params)
  },
  deleteDesignPlanResource(params = {}) { // 删除设计方案视频资源
    return request('renderUrl').post('/mobile/design/designPlan/getPanoPicture.htm', params)
  },
  getUserRenderTasksList(params = {}) { // 渲染任务
    return request('renderUrl').post('/mobile/pay/getMyReplaceRecord.htm', params)
  },
  getDesignplanfavoriteNum(params = {}) { // 获取收藏的方案
      return request('fullsearchUrl').get('/fullsearch-app/collect/recommendationplan/search/count', params)
  },
    getDesignplanfavorite(params = {}) { // 获取收藏的方案
      return request('fullsearchUrl').post('/fullsearch-app/collect/recommendationplan/search/list', params)
    },
  getProductfavorite(params = {}) { // 获取收藏的产品
    return request('baseUrl').get('/product/productfavorite/spuCollectionList', params)
  },
  collectHouselist(params = {}) { // 获取收藏的户型列表
    return request('baseUrl').get('/home/basehouse/getFavoriteHouse', params)
  },
  collectInvitationList(params = {}) { // 获取收藏的帖子列表
    return request('unionUrl').post('/union/supplydemand/getallsupplydemandinfo', params)
  },
  getAllSupplyDemandInfo(params = {}) { // 获取发布或者下架列表
    return request('unionUrl').post('union/supplydemand/getallsupplydemandinfo', params)
  },
  setPutawayOrSoldOut(params = {}) { // 上架或者下架
    return request('unionUrl').post('union/supplydemand/updatemysupplydemandstatus', params)
  },
  getUserRenderList(params = {}, loading = '') { // 获取用户渲染消息
    return request('baseUrl').get('/sysUserMessage/getList.htm', params, loading)
  },
  deleteUserRenderNews(params = {}) { // 删除用户渲染消息
    return request('baseUrl').get('/sysUserMessage/delete', params)
  },
  deleteMyDesignPlanAndTask(params = {}) { // 删除我的装修
    return request('baseUrl').get('/autorender/delplan', params)
  },
  getUserChatList(params = {}) { // 获取用户聊天列表
    return request('userMessageUrl').get('/msgCenter/userContact/list', params)
  },
  getChatingRecords(params = {}) { // 获取对应的聊天记录
    return request('userMessageUrl').get('/msgCenter/historyMsg/list', params)
  },
  refreshMessageRead(params = {}) { // 刷新消息为已读
    return request('userMessageUrl').formData('/msgCenter/userContact/resetUnreadMsg', params)
  },
  planReanme(params = {}) { // 方案重命名
    return request('baseUrl').get('/autorender/updateplanname', params)
  },
  deleteMyPlan(params = {}) { // 我的方案中删除方案
    return request('baseUrl').get('/autorender/delplan', params)
  },
  deleteUserContacts(params = {}) { // 删除联系人
    return request('userMessageUrl').formData('/msgCenter/userContact/remove', params)

  } ,
  getPayment(params = {}) {
    return request('renderUrl').post('/render/server/payDesignPlanCopyRight.htm', params)
  },
  publishMessage(params = {}) { // 发布信息
    return request('unionUrl').post('supplydeman/demand/publishdemandinfo', params)
  },
  commentMessage(params = {}, loading = '') { // 评论消息
    return request('unionUrl').get('union/userreviews/getuserreviews', params, loading)
  },
  readMessage(params = {}) { // 阅读消息
    return request('unionUrl').get('union/userreviews/isread', params)
  },
  uploadImg(params = {}) { // 上传图片
    return request('unionUrl').get('/union/userreviews/getuserreviews', params)
  },
  judgeHaveShop(params = {}) {  // 校测当前聊天用户是否有店铺 contactSessionId =>当前聊天用户sessionId
    return request('userMessageUrl').get('/msgCenter/userContact/checkMsgReceiverShopInfo', params)
  },
  uploadMsgImg(params = {}) {   // 上传消息图片接口 file传参
    return request('userMessageUrl').uploadFile('/msgCenter/historyMsg/upload', params)
  },
  checkSD(params = '' ){//检查帖子是否存在
    return request('unionUrl').get('/supplydeman/demand/check/'+params)
  }
}
export default myselfAPI