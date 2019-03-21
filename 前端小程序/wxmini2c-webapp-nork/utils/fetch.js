// let Authorization = 'eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzaWduZmxhdCI6ImJyYW5kXzJjX3VzZXJfdG9rZW46IiwiZXh0IjoxNTIxODAzMjM3OTUxLCJ1aWQiOjE1NjEsIm10eXBlIjoiMiIsInVuYW1lIjoiMTg2ODg3MjE3NjMiLCJ1dHlwZSI6MSwiYXBwS2V5IjoiZDgzMjUzYjM0YzJiNGYxZGJmYzA1YzExZThjYzE3MjciLCJzZXNzaW9uVGltZW91dCI6ODY0MDAsImlhdCI6MTUyMTc5MjQzNzk1MX0.2rJYFHJvFOoH7P-06Ebo_sX7s_sBFfUKxkA3Rtfa2Bs'
let Promise = require('../lib/es6-promise/es6-promise').Promise
let goodStorage = wx.getStorageSync('token')
import {basePath} from './config.js'
import {appid} from './config.js'
const fetch = (url, type, params, base, timer) => {
  let baseUrl, contentType = 'application/json', platformCode = 'miniProgram'
  if (base === 'pay') {
    baseUrl = basePath.payUrl
  } else if (base === 'render') {
    baseUrl = basePath.renderUrl    
    platformCode = 'miniProgram'    
  } else if (base === 'search') {
    baseUrl = basePath.productSearchUrl
  } else if (base === 'login') {
    baseUrl = basePath.limitUrl
  } else if (base==='order'){
    baseUrl = basePath.orderUrl
  }
  else if (base === 'user') {
    baseUrl = basePath.userUrl
  } else if (base === 'shop') {
    baseUrl = basePath.shopUrl
  } else if (base === 'system') {
     baseUrl = basePath.systemUrl  
    // baseUrl = basePath.devUrl
  } else if (base === 'else') {
      baseUrl = basePath.elseUrl
  } else if (base === 'fullsearch') {
      baseUrl = basePath.fullsearchUrl
  } else if (base === 'unionapi') {
    baseUrl = basePath.unionapi
  } else if(base=='core'){
    baseUrl=basePath.coreUrl
  } else{
    baseUrl = basePath.baseUrl
  }
  if (type === 'formData') {
    let str = [];
    for (var p in params) {
      str.push(encodeURIComponent(p) + "=" + encodeURIComponent(params[p]));
    }
    params = str.join("&")
    type = 'post'
    contentType = 'application/x-www-form-urlencoded'
    platformCode = 'miniProgram'
  }
   return new Promise((resolve, reject) => {
     if (!timer) {
       wx.showLoading({
         title: '加载中',
       })
     }
    wx.request({
      url: baseUrl + url,
      data: params,
      method: type,
      header: {
        "Content-Type": contentType,
        'Authorization': wx.getStorageSync('token') || '',
        'Platform-Code': platformCode
      },
      success: function (res) {
        resolve(res.data)
        if (!timer) { wx.hideLoading() }
        if (res.data.message === '请登录!' || res.data.message === '请登陆') {
          login()
        }
      },
      fail: function (err) {
        if (!timer) { wx.hideLoading()}
        wx.showToast({
          title: '加载失败',
          icon: 'none',
          duration: 2000
        })
        reject(err)
      }
    })
  })
}

const login = () => {
  wx.login({
    success: res => {
      let url = `/user/getOpenid`
      fetch(url, 'formData', {
        code: res.code,
        appid: appid
      }, 'login')
      .then(res => {
        if (res.success) {
          let url = `/user/login`
          return fetch(url, 'formData', {
            openid: res.obj,
            appid: appid
          }, 'login')
        }
      })
      .then(res => {
        if (res.success) {
          wx.setStorageSync('token', res.obj.token)
        }
      })
    },
    fail: () => {
      wx.showToast({
        title: '网络错误',
        icon: 'none'
      })
    }
  })
}
module.exports = fetch
