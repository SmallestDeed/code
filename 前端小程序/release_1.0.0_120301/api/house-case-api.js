import request from './request.js'
const houseCaseAPI = {
  getDesignplanconditionmetadata(params = {}) { // 获取空间
    return request('baseUrl').get('/designplan/designplanconditionmetadata', params)
  },
  // getRecommendCaseList(params = {}) { // 获取方案
  //   return request('baseUrl').get('/designplan/designplanrecommendedlist', Object.assign({ platformCode: 'selectDecoration' }, params))
  // },
    getRecommendCaseList(params = {}) {//获取方案（全文搜索）
        return request('fullsearchUrl').post('/fullsearch-app/all/recommendationplan/search/mini/list', params)
    },
    getNewRecommendCaseList(params = {}) {//获取方案（全文搜索）
        return request('fullsearchUrl').post('/fullsearch-app/sxwmini/recommendationplan/search/list', params)
    },
  getConditionMetadata(params = {}) { // 获取方案筛选条件
    return request('baseUrl').get('/designplan/designplanconditionmetadata', params)    
  },
  getFitmentQuote(params = {}) { // 获取装修报价筛选条件
    return request('baseUrl').get('/decoratePrice/getDecoratePriceType', params)    
  },
  getRecommendedVideoId(params = {}) { // 获取视频资源id
    return request('renderUrl').post('/mobile/designPlanRecommended/getRecommendedPictureList.htm', params)
  },
  getRecommendedVideoMessage(params = {}) { // 获取视频资源
    return request('renderUrl').post('/mobile/design/designPlan/getPanoPicture.htm', params)
  },
  collectCase(params = {}) { // 收藏方案
    return request('baseUrl').post('/designplanfavorite/setFavoriteOrUnfavorite', params)
  },
  likeCase(params = {}) { // 方案点赞
    return request('baseUrl').post('/designPlanLike/setLikeOrDislike', params)    
  },
  getSearchResluts(params = {}) { // 户型搜索获取小区
    return request('baseUrl').get('/base/baseliving/getlvinglist', params)
  },
  getbasehouselist(params = {}) { // 获取户型列表
    return request('baseUrl').get('/home/basehouse/getbasehouselist', params)
  },
  examineHouseIsMatching(params = {}) { // 检验户型跟方案是否匹配
    return request('baseUrl').get('/home/spacecommon/housespacelist', params)
  },
  gethouseDetailsTypeList(params = {}) { // 获取户型详情中的空间列表
    return request('baseUrl').post('/designplan/designplanconditionmetadata', params)
  },
  getSpaceNameInHouse(params = {}) { // 户型入口匹配空间列表
    return request('baseUrl').post('/home/basehouse/getSpaceNameInHouse', params)
  },
  getHouseDetailsSpaceImage(params = {}) { // 获取户型详情里，顶部的空间图
    return request('baseUrl').get('/home/spacecommon/housespacelist', params)
  },
  getSpaceDetails(params = {}) { // 空间入口，获取空间详情
    return request('baseUrl').get('/design/designtemplet/spacedesign', params)    
  },
  uploadHouseType(params = {}) { // 上传户型
    return request('baseUrl').formData('/home/basehouseapply/uploadhouse', params)        
  },
  uploadFileHouseType(params = {}) { // 带图片的上传户型
    return request('baseUrl').uploadFile('/home/basehouseapply/uploadhouse', params)        
  },
  getCaseDetails(params = {}) { // 获取方案详情
    return request('baseUrl').get('/designplan/getRecommendedDesignPlanDetail', params)            
  },
  saveUserRenderFormId(params = {}) { // 模板消息接口
    return request('systemUrl').formData('/notify/wx/saveUserRenderFormId', params)                
  },
  addRenderTask(params = {}) { // 添加渲染任务
    return request('renderUrl').post('/render/server/addRenderTask.htm', params)
  },
  getRenderValue(params = {}) { // 获取渲染条件
    return request('payUrl').get('/web/pay/payOrder/getRenderType', params)
  },
  payDesignPlanCopyRight(params = {}) { // 获取方案收费支付参数
    return request('renderUrl').post('/render/server/payDesignPlanCopyRight.htm',  params)
  },
  selectMyPlan(params = {}) { //选择户型页查询我的方案
    return request('baseUrl').get('/autorender/myplan', params)
  },
  matchPlanHouse(params = {}) { //匹配方案与户型是否能装进我家
    return request('baseUrl').get('/home/basehouse/getMatchResult', params)
  },
  queryWholeHousePic(params = {}) {
    return request('baseUrl').get('/home/basehouse/getHouseGuidePicInfo', params)
  },
  queryWholeHouseUUID(params = {}) {
    return request('baseUrl').get('/designplan/getFullHousePlanInfo', params)
  },
   getHomeCaseList(params = {}) { // 获取首页方案列表
    return request('baseUrl').get('/designplan/homeOfPlanInfo', Object.assign({ platformCode: 'selectDecoration' }, params))

  },
  getSearchHouseCollect(params = {}) { //搜索户型收藏
    return request('baseUrl').get('/system/nodeInfo/setStatus',params)
  },
  cutPriceByDecorate(params = {}) { // 体验后砍价接口
    return request('systemUrl').formData('/act/bargain/reg/cutPriceByDecorate', params)
  },
  getRegStatus(params = {}) { // 砍价活动获取活动regid
    return request('systemUrl').get('/act/bargain/reg/getRegStatus', params)
  },
  collectMiniUserFormId(params) { // formid 接收模板消息
    return request('systemUrl').post('/notify/wx/collectMiniUserFormId', params)
  }
}

export default houseCaseAPI
