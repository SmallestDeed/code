import {basePath} from './config.js'
const utils = {
  filtrationHtml: (str) => { // 过滤富文本空的HTML标签，用于判断返回html标签内是否有内容
    if (!str) {
      str = ''
    } else {
      let string = str.replace(/<[^>]+>/g, "").replace(/\s+/g, "");
      let index = str.indexOf('img')
      if (string.length == 0 && index == -1) {
        str = ''
      }
    }
    return str
  },
  myEmoticon: (str) => { // 禁止输入表情
    let i = /[\uD83C|\uD83D|\uD83E][\uDC00-\uDFFF][\u200D|\uFE0F]|[\uD83C|\uD83D|\uD83E][\uDC00-\uDFFF]|[0-9|*|#]\uFE0F\u20E3|[0-9|#]\u20E3|[\u203C-\u3299]\uFE0F\u200D|[\u203C-\u3299]\uFE0F|[\u2122-\u2B55]|\u303D|[\A9|\AE]\u3030|\uA9|\uAE|\u3030/ig;
    let String = str.replace(i, '').replace(/\s+/g, '')
    return String
  },
  myVerifyEmpty: (str) => { // 验证字符串是否为空,如果为空返回true，否则返回false
    // .replace(/\s+/g, "");
    let String = str.replace(/\s+/g, "");
    if (String == '') {
      return true;
    } else {
      return false;
    }
  },
  changeTiem(time) { // 转换时间
    let leadTime = new Date().getTime() - new Date(time.replace(/\-/g, '/')).getTime(),
      date;
    if (leadTime / 1000 / 60 < 1) {
      date = '刚刚';
    }
    if (leadTime / 1000 / 60 > 1 && leadTime / 1000 / 60 < 60) {
      date = (leadTime / 1000 / 60).toFixed(0) + '分钟前';
    }
    if (leadTime / 1000 / 3600 > 1 && leadTime / 1000 / 3600 < 59) {
      date = (leadTime / 1000 / 3600).toFixed(0) + '小时前';
    }
    if (leadTime / 1000 / 3600 / 24 > 1) {
      date = (leadTime / 1000 / 3600 / 24).toFixed(0) + '天前';
    }
    return date;
  },
  myForEach: (arr, callback) => { // forEach封装
    if (arr instanceof Array) {
      arr.forEach(callback)
    } else {
      return
    }
  },
  myFindIndex: (arr, callback) => { // 寻找对应的index
    let flag = -1
    utils.myForEach(arr, (value, index) => {
      if (callback(value)) {
        flag = index
        return
      }
    })
    return flag
  },
  mySplitUrl: (url) => { // 字符串裁切
    if (url.indexOf('?') === -1) {
      return
    } else {
      let index = url.indexOf('?')
      let newUrl = url.slice(index + 1).split('&')
      let urlObj = {}
      utils.myForEach(newUrl, (value) => {
        let index = value.indexOf('=')
        urlObj[value.slice(0, index)] = value.slice(index + 1)
      })
      urlObj.url = url.slice(0,index)
      return urlObj
    }
  },
  myCompoundUrl: (obj) => { // 把地址对象变为地址字符串
    if (!obj.url) {
      return ''
    } else {
      let url = obj.url + '?'
      for (var key in obj) {
        if (key !== 'url') {
         url += key + '=' + obj[key] + '&' 
        }
      }
      return url = url.slice(0,url.length - 1)   
    }
  },
  myFindIndex: (arr, callBack) => {
    let flag = -1
    if (arr instanceof Array) {
      arr.forEach((value, index) => {
        if (callBack(value)) {
          flag = index
          return
        }
      })
      return flag
    } else {
      return false
    }
  },
  requestFn(params) {
    params.loading !== 'noLoading'? wx.showLoading({ title: '加载中' }) : null
    return new Promise((resolve, reject) => {
      wx.request({
        url: basePath[params.base] + params.url,
        data: params.params,
        method: params.type,
        header: {
          "Content-Type": params.contentType,
          'Authorization': wx.getStorageSync('token') || '',
          'Platform-Code': params.platformCode
        },
        success(res) {
          resolve(res.data)
          params.loading !== 'noLoading' ? wx.hideLoading() : null
        },
        fail(err) {
          reject(err)
          wx.hideLoading()
          params.loading !== 'noLoading' ? wx.showToast({ title: '加载失败', icon: 'none', duration: 2000 }) : null
        }
      })
    })
  },
  uploadFileFn(params) {
    wx.showLoading({ title: '加载中' })
    return new Promise((resolve, reject) => {
      let path = params.params.path
      delete params.params.path
      wx.uploadFile({
        url: basePath[params.base] + params.url,
        filePath: path,
        name: 'file',
        header: {
          'token': wx.getStorageSync('token'),
          "Content-Type": params.contentType,
          'Authorization': wx.getStorageSync('token') || '',
          'Platform-Code': params.platformCode
        },
        formData: params.params,
        success(res) {
          let data = typeof res.data === 'string' ? JSON.parse(res.data) : res.data 
          resolve(data)
          wx.hideLoading()
        },
        fail(res) {
          reject(err)
          wx.hideLoading()
          wx.showToast({ title: '加载失败', icon: 'none', duration: 2000 })
        }
      })
    })
  },
  getNowTime() {
    let now = new Date()
    let year = now.getFullYear(), month = now.getMonth() + 1, day = now.getDate(), hours = now.getHours(), minutes = now.getMinutes()
    hours = hours < 10 ? '0' + hours : hours
    minutes = minutes < 10 ? '0' + minutes : minutes
    return year + '年' + month + '月' + day + '日' + ' ' + hours + ':' + minutes
  }
}

module.exports = utils