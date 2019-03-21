
import request from './request.js'
const commonAPI = {
  getUserOpenId(params = {}) { // 获取用户openid
    return request('limitUrl').formData('/user/getOpenid', params)
  },
  saveMinProNickName(params = {}) { // 保存用户信息
    return request('limitUrl').formData('/user/saveMinProNickName', params)    
  },
  setUserLogin(params = {}) { // 用户登录
    return request('limitUrl').formData('/user/login', params)    
  },
  getAllArea(params = {}) { // 获取省市区
    return request('baseUrl').get('/base/basearea/getAllArea', params)        
  },
  bindUserMobile(params = {}) { // 绑定手机号码
    return request('userUrl').formData('/v2/user/center/bindUserMobile', params)
  },
  getBindPhoneCode(params = {}) { // 获取绑定手机验证码
    return request('userUrl').formData('/v1/center/getSms', params)
  },
  addRenderTask(params = {}) { // 添加渲染任务
    return request('renderUrl').post('/render/server/addRenderTask.htm', params)
  },
  getRenderType(params = {}) { // 获取渲染选择参数
    return request('payUrl').get('/web/pay/payOrder/getRenderType', params)
  },
  isUserMobileBinded() { // 检验手机号
  },
  getJsonData(params = {}) { // 获取二维码保存参数
    return request('unionapi').get('/union/sxw/getJsonData', params)
  }
}
export default commonAPI
