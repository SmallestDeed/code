// 我的模块接口
import request from './request.js'
const myselfAPI = {
  getUserDuBiList(params = {}) { // 获取度币明细列表
    return request('payUrl').get('/web/pay/payOrder/findExpenseRecordList', params)
  },
  getAreaList(params = {}, noLoading){ // 区域列表
    return request('unionUrl').get('/union/base/basearea/getAreaByPid', params, noLoading)
  },
  getRegionList(params = {}) { // 户型搜索获取小区
    return request('baseUrl').get('/base/baseliving/getlvinglist', params)
  },
  getHouselist(params = {}) { // 获取户型列表
    return request('baseUrl').get('/home/basehouse/getbasehouselist', params)
  },
  getUserHouseTypeList(params = {}) { // 我的用户户型列表
    return request('baseUrl').get('/userresources/finduseraccounts', params)
  },
  degianReanme(params = {}) { // 方案重命名
    return request('baseUrl').get('/autorender/updateplanname', params)
  },
  deleteMyDesign(params = {}) { // 删除我的装修
    return request('baseUrl').get('/autorender/delplan', params)
  },
  getCompanyDetails(params = {}) { // 获取公司详情
    return request('shopUrl').get('/sandu/mini/companyshop/detail', params)
  },
  getChatingRecords(params = {}) { // 获取对应的聊天记录
    return request('userMessageUrl').get('/msgCenter/historyMsg/list', params)
  },
  refreshMessageRead(params = {}) { // 刷新消息为已读
    return request('userMessageUrl').formData('/msgCenter/userContact/resetUnreadMsg', params)
  },
  getRechargeIntegral(params = {}) { // 获取度币充值选择参数
    return request('payUrl').post('/web/pay/payOrder/getRechargeIntegral', params) 
  },
  getUserRechargeParams(params = {}) { // 获取用户充值度币参数
    return request('payUrl').formData('/web/pay/miniProPayOrder/recharge', params)
  },
  getPayment(params = {}) {
    return request('renderUrl').post('/render/server/payDesignPlanCopyRight.htm', params)
  },
  getBuyHouseChooseParams(params = {}) { // 获取购买户型选择参数
    return request('baseUrl').get('/home/basehouse/getexpandhousedict', params)
  },
  buyHOuseType(params = {}) { // 购买户型
    return request('payUrl').get('/web/pay/payOrder/expandhouse', params)
  },
  getDesignPlanResourceId(params = {}) { // 获取设计方案视频资源ID
    return request('renderUrl').post('/mobile/renderPic/getPictureList.htm', params)
  },
  getDesignPlanResource(params = {}) { // 获取设计方案视频资源
    return request('renderUrl').post('/mobile/design/designPlan/getPanoPicture.htm', params)
  },
}
export default myselfAPI