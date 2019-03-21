import request from './request.js'
const cityServiceAPI = {
    getBasecompanyDetail(params = {}) {
        return request('shopUrl').get('/sandu/mini/basecompany/detail', params)
    },
    getSysdictionaryList(params = {}) {
        return request('shopUrl').get('/sandu/mini/sysdictionary/list', params)
    },
    publishSupplyAndDemand(params = {}) { // 发布供求信息
        return request('unionUrl').post('union/supplydemand/addallsupplydemandinfo', params)
    },
    editSupplyAndDemand(params = {}) { // 发布供求信息
        return request('unionUrl').post('union/supplydemand/updatemysupplydemandinfo', params)
    },
    getCompanyShopactivity(params = {}) { // 未知接口
        return request('shopUrl').post('/sandu/mini/shopactivity/list', params)
    },
    uploadFileIssuedImage(params = {}) { // 上传发布信息图片
        return request('unionUrl').uploadFile('union/supplydemandpic/uploadrespic', params)
    },
    getStyleName(params = {}) { // 获取风格列表
        return request('shopUrl').get('sandu/mini/companyshop/styleList', params)
    },
  getUserPhone(params = {}) { // 获取预约号码
    return request('shopUrl').get('sandu/mini/proprietorInfo/getUserInfo', params)
  },
  getReserveSave(params = {}) { //预约接口
    return request('shopUrl').post('sandu/mini/proprietorInfo/save', params)
  },
  getPlanDetail(params = {}) {
    return request('baseUrl').get('/designplan/getRecommendedDesignPlanDetail', params)
  },
  getHomeList(params = {}) {  // 获取家装清单
    return request('baseUrl').get('/designplan/products', params)
  },
  getSupplydemandinfoDetail(params = {}){//供求信息详情
    return request('unionUrl').get('supplydeman/demand/getsupplydemanddetail',params)
  },
  getSupplydemandComment(params = {}) {//供求信息评论
    return request('unionUrl').get('/union/userreviews/getuserreviews', params)
  },
  submitComment(params = {}) {//发布评论
    return request('unionUrl').post('union/userreviews/adduserreviews', params)
  },
  getTopMessage(params = {}){ //互动区置顶留言
    return request('unionUrl').get('union/userreviews/topreviews', params)
  },
  getDeleteComment(params = {} ){  //删除自己评论
    return request('unionUrl').get('union/userreviews/delreviewmsg',params)
  },
  getMeDeleteComment(params = {}){ //删除自己的帖子
    return request('unionUrl').get('supplydeman/demand/delsupplydemand',params)
  },
  collectPage(params = {}) {//收藏、点赞帖子
    return request('baseUrl').get('/system/nodeInfo/setStatus', params)
  },
  copyPlan(params = {}) {//复制方案
    return request('coreUrl').post('/core/fullHouse/copyFullHouseDesignPlan' , params )
  },
  copyMsgFullHouseExist(params = {}) { //校验用用户是否已经点击全屋方案,并拷贝生成了新的全屋
    return request('coreUrl').get('core/fullHouse/copyMsgFullHouseExist', params)
  },
}
export default cityServiceAPI