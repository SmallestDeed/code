// 首页模块接口
// 接口使用说明
// 1、getUserDuBiMessage为你自己得接口函数命名
// 2、payUrl为服务参数，具体看utils里面的config
// 3、分别有三种请求方式 get post formData
// 以下为get的示范
import request from './request.js'
const designerAPI = {
  getUserDuBiMessage(params = {}) { // 获取用户度币信息 
    return request('payUrl').get('/web/system/sysUser/getUserBalance', params)
  },
  getIndexBanner(params = {}) { // 獲取首页banner
    return request('systemUrl').get('/base/banner/web/shop/list', params)
  },
  getIndexDesignerList(params = {}) { // 虎丘同城服務公司列表 
    return request('shopUrl').get('/sandu/mini/companyshop/list', params)
  },
  getIndexCompanyIdShopId(params = {}) { // 获取companyId shopId
    return request('systemUrl').get('/base/banner/web/getId', params)
  },
  getIndexServiceInfo(params = {}) { // 获取服务信息 
    return request('shopUrl').get('sandu/mini/companyshop/detail', params)
  },
  getIndexProjectList(params = {}) { // 获取工程案例 /sandu/shop/article/list
    return request('shopUrl').get('sandu/mini/companyshop/projectCaseList', params)
  },
  getIndexBowenList(params = {}) { // 获取博文 
    return request('shopUrl').get('/sandu/shop/article/list', params)
  },
}
export default designerAPI