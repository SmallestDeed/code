
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
    return request('baseUrl').get('/autorender/mydesignplan', params)      
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
  getDesignplanfavorite(params = {}) { // 获取收藏的方案
    return request('baseUrl').get('/designplanfavorite/list', params)    
  },
  getProductfavorite(params = {}) { // 获取收藏的产品
    return request('baseUrl').get('/product/productfavorite/spuCollectionList', params)        
  },
  getAllSupplyDemandInfo(params = {}) { // 获取发布或者下架列表
    return request('unionUrl').post('union/supplydemand/getallsupplydemandinfo', params)        
  },
    getPayment(params = {}) {
        return request('renderUrl').post('/render/server/payDesignPlanCopyRight.htm', params)
    },
  setPutawayOrSoldOut(params = {}) { // 上架或者下架
    return request('unionUrl').post('union/supplydemand/updatemysupplydemandstatus', params)
  },
  // rzd 190104 评价功能
  addEval(params = {}) { // 提交评价
    return request('baseUrl').post('/comment/order/add', params)
  }
}
export default myselfAPI