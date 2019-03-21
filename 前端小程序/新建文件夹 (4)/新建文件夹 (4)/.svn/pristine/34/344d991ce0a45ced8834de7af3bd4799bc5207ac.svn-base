import request from './request.js'
const indexAPI = {
  invitecallback(params = {}) { // 同城联盟回调
    return request('unionUrl').post('union/invite/invitecallback', params)      
  },
  invitecallbackshare(params = {}) { // 渠道回调
    return request('unionUrl').post('union/invite/sharecallback', params)
  },
  getShopPopularityList(params = {}) { // 获取首页三个列表 
    return request('shopUrl').get('/sandu/mini/companyshop/shopPopularityList', params)
  },
  getCityWideXCityList(params = {}) { // 获取同城服务城市列表
    return request('shopUrl').get('/sandu/mini/basearea/listOpenService', params)
  },
  getallsupplydemandinfo(params = {}) { // 获取供求信息列表
    return request('unionUrl').post('union/supplydemand/getallsupplydemandinfo', params)
  },
  getAllSupplyDemandCategory(params = {}) { // 获取装修类型筛选条件
    return request('unionUrl').get('union/basedata/getallsupplydemandcategory', params)    
  },
  getAllSupplyDemandRoles(params = {}) { // 获取装修角色筛选条件
    return request('unionUrl').get('union/basedata/getallsupplydemandRoles', params)
  },
  addSupplyDemandViewCount(params = {}) { // 供求详情浏览量 
    return request('unionUrl').post('union/supplydemand/addsupplydemandviewnum', params)    
  },
  getCompanyDesignerList(params = {}) { // 获取店铺的设计师列表
    return request('shopUrl').get('/sandu/mini/CompanyDesigner/designerList', params)
  },
  getCompanyBlogArticle(params = {}) { // 获取博文列表
    return request('shopUrl').get('/sandu/shop/article/list', params)
  },
  getProjectCaseList(params = {}) { // 未知接口
    return request('shopUrl').get('/sandu/mini/companyshop/projectCaseList', params)
  },
  addCompanyVisitCount(params = {}) { // 店铺详情浏览量
    return request('shopUrl').get('/sandu/mini/companyshop/updateVisitCount', params)  
  },
  addRecommendedPlanVisitCount(params = {}) { // 店铺方案浏览量
    return request('coreUrl').get('/core/designplan/recommendedPlanCount', params)  
  },
  getCompanyPlan(params = {}) { // 获取店铺详情里面的方案
    return request('coreUrl').get('core/designplan/designplanrecommendedlist', params)  
  },
  getInteractioncomment(params = {}) { // 未知接口
    return request('shopUrl').get('/sandu/mini/interactioncomment/list', params)
  },
  getArticleDetails(params = {}) { // 獲取博文詳情
    return request('shopUrl').get('/sandu/shop/article/detail', params)    
  }
}
export default indexAPI