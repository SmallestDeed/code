import request from './request.js'
const houseCaseAPI = {
  getDesignplanconditionmetadata(params = {}) { // 获取空间
    return request('baseUrl').get('/designplan/designplanconditionmetadata', params)
  },
  getRecommendCaseList(params = {}) { // 获取方案
    return request('baseUrl').get('/designplan/designplanrecommendedlist', params)
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
  cutPriceByDecorate(params = {}) { // 体验后砍价接口
    return request('systemUrl').formData('/v1/act/bargain/reg/cutPriceByDecorate', params)
  },
  getRegStatus(params = {}) { // 砍价活动获取活动regid
    return request('systemUrl').get('/v1/act/bargain/reg/getRegStatus', params)
  },
  collectMiniUserFormId(params) { // formid
    return request('systemUrl').post('/v1/notify/wx/collectMiniUserFormId', params)
  }
}
export default houseCaseAPI
