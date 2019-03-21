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
    getBannerList(params = {}) { // 获取首页banner图
        return request('systemUrl').get('/base/banner/web/xzminiprogram/list', params)
    },
    commentEvaluate(params = {}) { // 我的消息评价
        return request('baseUrl').post('/decorateOrder/score', params)
    },
    
    getUserInfo(params = {}) { // 获取用户信息
        return request('baseUrl').get('/user/sysuser/userinfo', params)
    },
  getSystemRenderList(params = {}, loading = '' ) { // 获取系统消息
        return request('baseUrl').get('/sysUserMessage/getSystemList', params, loading)
    },
    enterpriseEntry(params = {}) { // 企业入驻
        return request('systemUrl').post('/proprietor', params)
    },
    getCompanyEvaluate(params = {}) { // 获取评论公司信息
        return request('baseUrl').get('/decorateOrder/detail', params)
    },
    uploadEvaluate(params = {}) { // 评论上传图片
        return request('baseUrl').uploadFile('/upload', params)
    },
    getDecorateOrderList(params = {}) { // 企业入驻列表
        return request('baseUrl').get('/decorateOrder/list', params)
    },
    getShopBannerList(params = {}) { //获取店铺列表banner
        return request('systemUrl').get('/base/banner/web/xzminiprogram/list', params)
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
    // getCompanyPlan(params = {}) { // 获取店铺详情里面的方案
    //   return request('coreUrl').get('core/designplan/designplanrecommendedlist', params)  
    // },
    getCompanyPlan(params = {}) { // 获取店铺详情里面的方案
        return request('fullsearchUrl').post('/fullsearch-app/all/recommendationplan/search/mini/list', params)
    },

    getInteractioncomment(params = {}) { // 未知接口
        return request('shopUrl').get('/sandu/mini/interactioncomment/list', params)
    },
    getArticleDetails(params = {}) { // 獲取博文詳情
        return request('shopUrl').get('/sandu/shop/article/detail', params)
    },
    getIndexBanner(params = {}) { // 獲取首页banner
      return request('systemUrl').get('base/banner/web/xzminiprogram/list', params)
    },
    feedback(params = {}) { // 反馈
        return request('systemUrl').post('/feedback/web/feedbackAdd', params)
    },
    feedbackEstimate(params = {}) { // 反馈点评
        return request('systemUrl').post('/feedback/web/feedbackEstimate', params)
    },
    getUserMobile(params = {}) { // 获取用户手机
        return request('systemUrl').get('/feedback/web/getUserMobile', params)
    },
    getFeedbackList(params = {}) { // 反馈列表
        return request('systemUrl').get('/feedback/web/feedbackList', params)
    },
    getFeedbackDetail(params = {}) { // 反馈详情
        return request('systemUrl').get('/feedback/web/feedbackDetail', params)
    },
  getContactShop(params = {}) { // 获取用户联系人接口
    return request('shopUrl').get('/sandu/mini/companyshop/getContactShop', params)
  },
}
export default indexAPI