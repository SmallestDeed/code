// s设计模块接口
import request from './request.js'
const designAPI = {
  getNewRecommendCaseList(params = {}) {//获取方案（全文搜索）
    return request('fullsearchUrl').post('/fullsearch-app/all/recommendationplan/search/mini/list', params)
  },
  getMyDegian(params = {}) { // 获取我的方案
    return request('baseUrl').get('/autorender/mydecorationplan', params)
  },
  gethouseDetailsTypeList(params = {}) { // 获取户型详情中的空间列表
    return request('baseUrl').post('/designplan/designplanconditionmetadata', params)
  },
  selectMyPlan(params = {}) { //选择户型页查询我的方案
    return request('baseUrl').get('/autorender/myplan', params)
  },
  getCollectDesign(params = {}) { // 获取收藏的方案
    return request('fullsearchUrl').post('/fullsearch-app/collect/recommendationplan/search/list', params)
  },
  collectCase(params = {}) { // 方案收藏
    return request('baseUrl').post('/designplanfavorite/setFavoriteOrUnfavorite', params)
  },
  likeCase(params = {}) { // 方案点赞
    return request('baseUrl').post('/designPlanLike/setLikeOrDislike', params)
  },
  queryWholeHousePic(params = {}) {
    return request('baseUrl').get('/home/basehouse/getHouseGuidePicInfo', params)
  },
  getFitmentQuote(params = {}) { // 获取装修报价筛选条件
    return request('baseUrl').get('/decoratePrice/getDecoratePriceType', params)
  },
  getRecommendCaseList(params = {}) {//获取方案（全文搜索）
    return request('fullsearchUrl').post('/fullsearch-app/all/recommendationplan/search/mini/list', params)
  },
  addRenderTask(params = {}) { // 添加渲染任务
    return request('renderUrl').post('/render/server/addRenderTask.htm', params)
  },
  cutPriceByDecorate(params = {}) { // 体验后砍价接口
    return request('systemUrl').formData('act/bargain/reg/cutPriceByDecorate', params)
  },
  queryWholeHouseUUID(params = {}) {
    return request('baseUrl').get('/designplan/getFullHousePlanInfo', params)
  },
  saveUserRenderFormId(params = {}) { // 模板消息接口
    return request('systemUrl').formData('/notify/wx/saveUserRenderFormId', params)
  },
  matchPlanHouse(params = {}) { //匹配方案与户型是否能装进我家
    return request('baseUrl').get('/home/basehouse/getMatchResult', params)
  },
  examineHouseIsMatching(params = {}) { // 检验户型跟方案是否匹配
    return request('baseUrl').get('/home/spacecommon/housespacelist', params)
  },
  getRender(params = {}) { // 获取风格列表
    return request('renderUrl').formData('/render/server/checkReplaceDesignPlanPay.htm', params)
  },
  getIsBindingMobile(params = {}) { // 检验手机号是否存在
    return request('userUrl').get('/v2/user/center/isUserMobileBinded', params)
  },
  getPayment(params = {}) {
    return request('renderUrl').post('/render/server/payDesignPlanCopyRight.htm', params)
  },
  // cutPriceByDecorate(params = {}) { // 体验后砍价接口
  //   return request('systemUrl').formData('/v1/act/bargain/reg/cutPriceByDecorate', params)
  // },
  getSpaceNameInHouse(params = {}) {
    return request('baseUrl').post('/home/basehouse/getSpaceNameInHouse.htm', params)
  },
  getuserIsPackageInMonthly(params = {}) { // 获取用户包年包月信息
    return request('payUrl').get('/web/pay/checkRenderGroopRef2C', params)
  },
  getSpaceDetails(params = {}) { // 空间入口，获取空间详情
    return request('baseUrl').get('/design/designtemplet/spacedesign', params)
  },
  getRecommendedVideoId(params = {}) { // 获取视频资源id
    return request('renderUrl').post('/mobile/designPlanRecommended/getRecommendedPictureList.htm', params)
  },
  getRecommendedVideoMessage(params = {}) { // 获取视频资源
    return request('renderUrl').post('/mobile/design/designPlan/getPanoPicture.htm', params)
  },
}
export default designAPI