// 公共模块接口
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
  getSystemRenderList(params = {}, loading = '') { // 获取系统消息
    return request('baseUrl').get('/sysUserMessage/getSystemList', params, loading)
  },
  getUserRenderList(params = {}, loading = '') { // 获取用户渲染消息
    return request('baseUrl').get('/sysUserMessage/getList.htm', params, loading)
  },
  getUserChatList(params = {}) { // 获取用户聊天列表
    return request('userMessageUrl').get('/msgCenter/userContact/list', params)
  },
  getIsBindingMobile(params = {}) { // 检验手机号是否存在 
    return request('userUrl').get('/v2/user/center/isUserMobileBinded', params)
  },
  bindUserMobile(params = {}) { // 绑定手机号码
    return request('userUrl').formData('/v2/user/center/bindUserMobile', params)
  },
  getBindPhoneCode(params = {}) { // 获取绑定手机验证码
    return request('userUrl').get('/v1/center/getSms', params)
  },
  saveMinProNickName(params = {}) { // 保存用户信息
    return request('userUrl').formData('/v2/user/center/modifyUserInfo', params)
  },
  getJsonData(params = {}) { // 获取二维码保存参数
    return request('unionUrl').get('/union/sxw/getJsonData', params)
  }
}
export default commonAPI
