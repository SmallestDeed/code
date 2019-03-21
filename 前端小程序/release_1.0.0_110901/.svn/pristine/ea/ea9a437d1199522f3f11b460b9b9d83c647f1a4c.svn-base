//app.js
import utils from './utils/utils.js'
import API from '/api/api.js'
import { myPinYin } from '/utils/Convert_Pinyin.js'
// let io = require('./lib/weapp.socket.io.js')
const io = require('./lib/socket.io.slim.js')
import {
  staticImageUrl,
  resourcePath,
  sevenUrl,
  wholeHouseUrl,
  wholeHouse3dUrl,
  basePath,
  userChatUrl,
  grassSevenUrl,
  sxwLangingPage,
  squeezePageUrl
} from './utils/config.js'
import { quickNavigation } from './component/quick-navigation/quick-navigation'
import { bindingPhone } from './component/binding-phone/binding-phone'
import { newNav } from 'component/newNav/newNav'
App({
  // 原生组件
  grassSevenUrl,
  staticImageUrl,
  quickNavigation,
  bindingPhone,
  newNav,
  // 原生组件
  data: {
    imgURL: "http://open.rjhq.cn/xz/wx/images/",
      URL: "http://192.168.0.114:8080/",
      staticImage: "http://192.168.2.199:4444/static/image/",
    hidden: false,
    webUrl: '',
    cityData: [], // 城市数
    carCount: 0, // 购物车数量,
    loginStatus: '',
    loginFlag: false,
    cityList: []
  },
  wxUrl: 'https://servicewechat.com/wx0d37f598e1028825/devtools/page-frame.html',
  sevenUrl,
  wholeHouseUrl,
  wholeHouse3dUrl,
  sxwLangingPage,
  myFindIndex: utils.myFindIndex,
  myForEach: utils.myForEach,
  resourcePath: resourcePath,
  mySplitUrl: utils.mySplitUrl,
  myCompoundUrl: utils.myCompoundUrl,
  API: API,
  staticImageUrl,
  squeezePageUrl,
  mySocket: null,
  webSocketStatus: false,
  socket(params) {
    let url = basePath.userChatUrl + '?userSessionId=' + params.userSessionId + '&appId=16'
    return io(url)
  },
    shareAppMessageFn(bool, params) {
        let isBool = bool || false;
        let pages = getCurrentPages(),
            isIndex = false,
            opt = params ? params : '',
            url = '/' + pages[pages.length - 1].route + opt,
            str = [
                'pages/home/home',
                'pages/plan/house-case/house-case',
                'pages/brandHall/bHIndex/bHIndex',
                'pages/decoration/cityService/cityService',
                'pages/mine/myself/myself'
            ];
        for (let i = 0; i < str.length; i++) {
            if (pages[pages.length - 1].route == str[i] || !isBool) {
                isIndex = true;
                break;
            }
        }
        url = !bool ? str[0] : url;
        console.log(encodeURI(url), 'wqwq')
        let path = isIndex ? url : (str[0] + '?navToUrl=' + encodeURIComponent(url));
        console.log(path);
        return {
            title: '随选网',
            path: path,
            success(res) { },
            fail(err) { }
        }
    },
  onLaunch: function () {
    this.showStorage() // 展示存储能力
    this.userLogin() // 登录
    this.getAllArea() // 获取省市区
    // this.testLiveChat()
    // console.log(this.socket({ userSessionId: '63690911ad8c11e89ee9000c2978eed1', contactSessionId: '637ada4dad8c11e89ee9000c2978eed2'}))
  },
  testLiveChat() {
    const socket = io('http://im.ci.sanduspace.com:15001/im/chat?userSessionId=63690911ad8c11e89ee9000c2978eed1&contactSessionId=637ada4dad8c11e89ee9000c2978eed2')
    socket.on('chatevent', content => {
      console.log('received news: ', content)
    })
    socket.emit('chatevent', {
      fromUserSessionId: "63690911ad8c11e89ee9000c2978eed1",
      toUserSessionId: "637ada4dad8c11e89ee9000c2978eed2",
      msgBody: "还是轮询啊。。。，用户量一大会不会炸，有没有用到阻塞"
    })
  },
  userLogin() { // 用户登录
    let that = this
    wx.login({
      success: res => {
        API.getUserOpenId({ code: res.code, appid: 'wx42e6b214e6cdaed3' })
          .then(res => {
            if (res.success) {
              // 存储openId
              wx.setStorageSync('openId', res.obj)
              // 获取用户信息
              wx.getSetting({
                success(res) {
                  if (!res.authSetting['scope.userInfo']) {
                    wx.authorize({
                      scope: 'scope.userInfo',
                      success() {
                        // 已经授权，可以直接调用 getUserInfo 获取头像昵称，不会弹框
                        wx.getUserInfo({
                          success: res => {
                            // 可以将 res 发送给后台解码出 unionId
                            that.globalData.userInfo = res.userInfo
                            // 由于 getUserInfo 是网络请求，可能会在 Page.onLoad 之后才返回
                            // 所以此处加入 callback 以防止这种情况
                            if (that.userInfoReadyCallback) { that.userInfoReadyCallback(res) }
                            // 上传用户昵称
                            API.saveMinProNickName({ openId: wx.getStorageSync('openId'), nickName: res.userInfo.nickName })
                          }
                        })
                      }
                    })
                  } else {
                    // 已经授权，可以直接调用 getUserInfo 获取头像昵称，不会弹框
                    wx.getUserInfo({
                      success: res => {
                        // 可以将 res 发送给后台解码出 unionId
                        that.globalData.userInfo = res.userInfo
                        // 由于 getUserInfo 是网络请求，可能会在 Page.onLoad 之后才返回
                        // 所以此处加入 callback 以防止这种情况
                        if (that.userInfoReadyCallback) {
                          that.userInfoReadyCallback(res)
                        }
                        // 上传用户昵称
                        API.saveMinProNickName({ openId: wx.getStorageSync('openId'), nickName: res.userInfo.nickName })
                      }
                    })
                  }
                }
              })
              // 登录
              API.setUserLogin({ openid: res.obj, appid: 'wx42e6b214e6cdaed3' })
                .then(res => {
                  if (res.success) {
                    wx.setStorageSync('token', res.obj.token)
                    wx.setStorageSync('id', res.obj.id)
                    // 存储首次登陆状态
                    wx.setStorageSync('isLoginBefore', res.obj.isLoginBefore)
                    this.mySocket = this.socket({ userSessionId: res.obj.sessionId})
                    this.mySocket.on('connect', () => { console.log('链接成功'); this.webSocketStatus = true});
                    this.mySocket.on('disconnect', function () { console.log('断开连接') });
                    wx.setStorageSync('uuid', res.obj.sessionId || '')
                    that.data.loginFlag = true;
                    that.data.loginStatus = res.obj.isLoginBefore
                  }
                })

            }
          })

      },
      fail: (err) => {
        wx.showToast({
          title: '网络错误',
          icon: 'none'
        })
      }
    })
  },
  getAllArea() {
    API.getAllArea()
      .then(res => {
        res.status ? wx.setStorageSync('cityData', res.obj) : wx.setStorageSync('cityData', []);
        this.getCityList(res.obj)
      })
      .catch(() => { wx.setStorageSync('cityData', []) })
  },
  getCityList(cityData) { // 获取城市数组
    let provinces = [], allCity = []
    this.myForEach(cityData, (value) => { allCity = allCity.concat(value.baseAreaVos) })
    let cityList = [{ key: 'A', val: [] }, { key: 'B', val: [] }, { key: 'C', val: [] }, { key: 'D', val: [] }, { key: 'E', val: [] }, { key: 'F', val: [] }, { key: 'G', val: [] }, { key: 'H', val: [] }, { key: 'I', val: [] }, { key: 'J', val: [] }, { key: 'K', val: [] }, { key: 'L', val: [] }, { key: 'M', val: [] }, { key: 'N', val: [] }, { key: 'O', val: [] }, { key: 'P', val: [] }, { key: 'Q', val: [] }, { key: 'R', val: [] }, { key: 'S', val: [] }, { key: 'T', val: [] }, { key: 'U', val: [] }, { key: 'V', val: [] }, { key: 'W', val: [] }, { key: 'X', val: [] }, { key: 'Y', val: [] }, { key: 'Z', val: [] }]
    this.myForEach(allCity, (value, index) => {
      value.baseAreaVos = []
      let count = cityList.findIndex((n) => { return n.key == myPinYin.getFullChars(value.areaName).substr(0, 1) })
      count !== -1 ? cityList[count].val.push(value) : null
    })
    let addressHistotyList = wx.getStorageSync('addressHistoty') || [], location = wx.getStorageSync('cityLoctionItem')
    if (location) { addressHistotyList.unshift(location) } else {
      addressHistotyList.length > 0 ? null : addressHistotyList.unshift({ areaCode: '440300', areaName: "深圳市", baseAreaVos: [], id: 1976, levelId: 2, pid: '440000' })
    }
    let hotCityCode = ['110100', '310100', '440100', '440300', '510100', '320100', '320500', '330100'], hotCityList = [], arr = []
    this.myForEach(hotCityCode, (value) => { hotCityList.push(allCity.find((n) => { return n.areaCode == value })) })
    cityList.unshift({ key: 'rm', keyshow: '热门', val: hotCityList, show: '热门城市' })
    cityList.unshift({ key: 'zj', keyshow: '定位', val: addressHistotyList, show: '定位/最近访问' })
    this.myForEach(cityList, (value, index) => { value.val.length > 0 ? arr.push(value) : null })
    this.data.cityList = arr
  },
  showStorage() {
    let that = this, logs = wx.getStorageSync('logs') || []
    logs.unshift(Date.now())
    wx.setStorageSync('logs', logs)
  },
  cityDataFilter(cityData) { // 城市数据处理
    this.myForEach(cityData, (valOne) => {
      this.myForEach(valOne.baseAreaVos, (valTwo) => {
        valTwo.baseAreaVos.unshift({
          areaCode: '',
          areaName: '全市',
          baseAreaVos: null,
          id: null,
          levelId: 0,
          pid: valTwo.areaCode
        })
      })
    })
    return cityData
  },
  myNavigateBack(url, options) {
    console.log(url, 'wq')
    let pages = getCurrentPages(), optionUrl = '?'
    let flag = this.myFindIndex(pages, (page) => {
      return page.route === url
    })
    if (typeof options === 'object') {
      for (let key in options) {
        optionUrl += key + '=' + options[key] + '&'
      }
      optionUrl.slice(0, optionUrl.length - 1)
    } else {
      optionUrl = ''
    }
    console.log(pages, url, flag, 'wq')
    if (flag !== -1) {
      wx.navigateBack({
        delta: pages.length - 1- flag
      })
      // return getCurrentPages()[flag].route
    } else {
      wx.navigateTo({
        url: '/' + url + optionUrl,
      })
      return false
    }
  },
  globalData: {
    userInfo: null
  },
  onShareAppMessage() {

  }
})