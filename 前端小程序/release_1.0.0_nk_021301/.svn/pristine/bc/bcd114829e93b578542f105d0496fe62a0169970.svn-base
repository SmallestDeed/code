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
    getCompanyShopactivity(params = {}) { // 未知接口
        return request('shopUrl').post('/sandu/mini/shopactivity/list', params)
    },
    uploadFileIssuedImage(params = {}) { // 上传发布信息图片
        return request('unionUrl').uploadFile('union/supplydemandpic/uploadrespic', params)
    },
    getStyleName(params = {}) { // 获取风格列表
        return request('shopUrl').get('sandu/mini/companyshop/styleList', params)
    },
    getRender(params = {}) { // 获取风格列表
        return request('renderUrl').formData('/render/server/checkReplaceDesignPlanPay.htm', params)
    },
    
}
export default cityServiceAPI