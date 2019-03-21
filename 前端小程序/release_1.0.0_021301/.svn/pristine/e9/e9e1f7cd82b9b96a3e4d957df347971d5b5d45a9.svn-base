//app.js
import utils from './utils/utils.js'
import API from '/api/api.js'
import {
  myPinYin
} from '/utils/Convert_Pinyin.js'
const io = require('./lib/socket.io.slim.js')
const ald = require('./utils/ald-stat.js')
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
  squeezePageUrl,
  sxwSqueezePage
} from './utils/config.js'
import {
  quickNavigation
} from './component/quick-navigation/quick-navigation'
import {
  bindingPhone
} from './component/binding-phone/binding-phone'
import {
  newNav
} from 'component/newNav/newNav'
import {
  state,
  mutations
} from './store/index.js'
App(Object.assign({
  // 原生组件
  grassSevenUrl,
  staticImageUrl,
  quickNavigation,
  bindingPhone,
  newNav,
  equipmentMessage: wx.getSystemInfoSync(),
  // 原生组件
  data: Object.assign({
    imgURL: "http://open.rjhq.cn/xz/wx/images/",
    URL: "http://192.168.0.114:8080/",
    staticImageUrl: "http://192.168.2.199:4444/static/image/",
    hidden: false,
    webUrl: '',
    cityData: [], // 城市数
    carCount: 0, // 购物车数量,
    loginStatus: '',
    loginFlag: false,
    cityList: [],
    cutpriceUrl: '',
    festivalUrl: '', //分享海报春节活动url
  }, state),
  wxUrl: 'https://servicewechat.com/wx42e6b214e6cdaed3/devtools/page-frame.html',
  sevenUrl,
  wholeHouseUrl,
  wholeHouse3dUrl,
  sxwLangingPage,
  myFindIndex: utils.myFindIndex,
  myForEach: utils.myForEach,
  resourcePath: resourcePath,
  mySplitUrl: utils.mySplitUrl,
  myCompoundUrl: utils.myCompoundUrl,
  sellingPoint: utils.sellingPoint,
  API: API,
  staticImageUrl,
  squeezePageUrl,
  sxwSqueezePage,
  mySocket: null,
  webSocketStatus: false,
  basePages: [
    'pages/home/home',
    'pages/plan/house-case/house-case',
    'pages/brandHall/bHIndex/bHIndex',
    'pages/decoration/cityService/cityService',
    'pages/mine/myself/myself'
  ],
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
      str = this.basePages;
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
      success(res) {},
      fail(err) {}
    }
  },
  onLaunch: function() {
    this.userLoginStatus = this.userLogin() // 登录    
    this.showStorage() // 展示存储能力
    this.getAllArea() // 获取省市区
    this.forcedUpdating() // 强制更新
    this.getUserMessage() // 获取用户
  },
  getUserMessage() {
    console.log('getUserMessage')
    let that = this
    wx.getUserInfo({
      success(res) {
        that.globalData.userInfo = res.userInfo
      }
    })
  },
  userLoginStatus: null,
  userLogin() { // 用户登录
    return new Promise((resolve, reject) => {
      let that = this
      wx.login({
        success: res => {
          API.getUserOpenId({
              code: res.code,
              appid: 'wx42e6b214e6cdaed3'
            })
            .then(res => {
              if (res.success) {
                // 存储openId
                wx.setStorageSync('openId', res.obj)
                // 登录
                API.setUserLogin({
                    openid: res.obj,
                    appid: 'wx42e6b214e6cdaed3'
                  })
                  .then(res => {
                    if (res.success) {
                      wx.setStorageSync('token', res.obj.token)
                      wx.setStorageSync('id', res.obj.id)
                      // 存储首次登陆状态
                      wx.setStorageSync('isLoginBefore', res.obj.isLoginBefore)
                      this.mySocket = this.socket({
                        userSessionId: res.obj.sessionId
                      })
                      this.mySocket.on('connect', () => {
                        console.log('链接成功');
                        this.webSocketStatus = true
                      });
                      this.mySocket.on('disconnect', function() {
                        console.log('断开连接')
                      });
                      wx.setStorageSync('uuid', res.obj.sessionId || '')
                      this.data.loginFlag = true // 后期可把这个去掉，用app.state.loginStatus === 1判断是登录与否， 1为登录                   
                      this.data.loginStatus = res.obj.isLoginBefore
                      if (this.userInfoReadyCallback) {
                        this.userInfoReadyCallback(res)
                      }
                      resolve(true)
                    } else {
                      reject(false)
                    }
                  })
              }
            })
        },
        fail: (err) => {
          reject(false)
          wx.showToast({
            title: '网络错误',
            icon: 'none'
          })
        }
      })
    })
  },
  forcedUpdating() {
    if (wx.canIUse('getUpdateManager')) {
      const updateManager = wx.getUpdateManager()
      updateManager.onCheckForUpdate((res) => {
        if (res.hasUpdate) {
          updateManager.onUpdateReady(function() {
            wx.showModal({
              title: '更新提示',
              content: '新版本已经准备好，是否重启应用？',
              success: function(res) {
                res.confirm && updateManager.applyUpdate()
              }
            })
          })
          updateManager.onUpdateFailed(() => {
            wx.showModal({
              title: '更新提示',
              content: '新版本下载失败',
              showCancel: false
            })
          })
        }
      })
    }
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
  getAllArea() {
    API.getAllArea()
      .then(res => {
        res.status ? wx.setStorageSync('cityData', res.obj) : wx.setStorageSync('cityData', []);
        this.getCityList(res.obj)
      })
      .catch(() => {
        wx.setStorageSync('cityData', [])
      })
  },
  getCityList(cityData) { // 获取城市数组
    let provinces = [],
      allCity = []
    this.myForEach(cityData, (value) => {
      allCity = allCity.concat(value.baseAreaVos)
    })
    let cityList = [{
      key: 'A',
      val: []
    }, {
      key: 'B',
      val: []
    }, {
      key: 'C',
      val: []
    }, {
      key: 'D',
      val: []
    }, {
      key: 'E',
      val: []
    }, {
      key: 'F',
      val: []
    }, {
      key: 'G',
      val: []
    }, {
      key: 'H',
      val: []
    }, {
      key: 'I',
      val: []
    }, {
      key: 'J',
      val: []
    }, {
      key: 'K',
      val: []
    }, {
      key: 'L',
      val: []
    }, {
      key: 'M',
      val: []
    }, {
      key: 'N',
      val: []
    }, {
      key: 'O',
      val: []
    }, {
      key: 'P',
      val: []
    }, {
      key: 'Q',
      val: []
    }, {
      key: 'R',
      val: []
    }, {
      key: 'S',
      val: []
    }, {
      key: 'T',
      val: []
    }, {
      key: 'U',
      val: []
    }, {
      key: 'V',
      val: []
    }, {
      key: 'W',
      val: []
    }, {
      key: 'X',
      val: []
    }, {
      key: 'Y',
      val: []
    }, {
      key: 'Z',
      val: []
    }]
    this.myForEach(allCity, (value, index) => {
      value.baseAreaVos = []
      let count = cityList.findIndex((n) => {
        return n.key == myPinYin.getFullChars(value.areaName).substr(0, 1)
      })
      count !== -1 ? cityList[count].val.push(value) : null
    })
    let addressHistotyList = wx.getStorageSync('addressHistoty') || [],
      location = wx.getStorageSync('cityLoctionItem')
    if (location) {
      addressHistotyList.unshift(location)
    } else {
      addressHistotyList.length > 0 ? null : addressHistotyList.unshift({
        areaCode: '440300',
        areaName: "深圳市",
        baseAreaVos: [],
        id: 1976,
        levelId: 2,
        pid: '440000'
      })
    }
    let hotCityCode = ['110100', '310100', '440100', '440300', '510100', '320100', '320500', '330100'],
      hotCityList = [],
      arr = []
    this.myForEach(hotCityCode, (value) => {
      hotCityList.push(allCity.find((n) => {
        return n.areaCode == value
      }))
    })
    cityList.unshift({
      key: 'rm',
      keyshow: '热门',
      val: hotCityList,
      show: '热门城市'
    })
    cityList.unshift({
      key: 'zj',
      keyshow: '定位',
      val: addressHistotyList,
      show: '定位/最近访问'
    })
    this.myForEach(cityList, (value, index) => {
      value.val.length > 0 ? arr.push(value) : null
    })
    this.data.cityList = arr
  },
  showStorage() {
    let that = this,
      logs = wx.getStorageSync('logs') || []
    logs.unshift(Date.now())
    wx.setStorageSync('logs', logs)
    if (!wx.getStorageSync('uniqueidentifier')) {
      wx.setStorageSync('uniqueidentifier', this.getUniqueidentifier(9))
    }
  },
  getUniqueidentifier(length) {
    return Number(Math.random().toString().substr(3, length) + Date.now()).toString(36)
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
    let pages = getCurrentPages(),
      optionUrl = '?'
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
        delta: pages.length - 1 - flag
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

  },
  isEmojiCharacter(substring) {
    for (var i = 0; i < substring.length; i++) {
      var hs = substring.charCodeAt(i);
      if (0xd800 <= hs && hs <= 0xdbff) {
        if (substring.length > 1) {
          var ls = substring.charCodeAt(i + 1);
          var uc = ((hs - 0xd800) * 0x400) + (ls - 0xdc00) + 0x10000;
          if (0x1d000 <= uc && uc <= 0x1f77f) {
            return true;
          }
        }
      } else if (substring.length > 1) {
        var ls = substring.charCodeAt(i + 1);
        if (ls == 0x20e3) {
          return true;
        }
      } else {
        if (0x2100 <= hs && hs <= 0x27ff) {
          return true;
        } else if (0x2B05 <= hs && hs <= 0x2b07) {
          return true;
        } else if (0x2934 <= hs && hs <= 0x2935) {
          return true;
        } else if (0x3297 <= hs && hs <= 0x3299) {
          return true;
        } else if (hs == 0xa9 || hs == 0xae || hs == 0x303d || hs == 0x3030 ||
          hs == 0x2b55 || hs == 0x2b1c || hs == 0x2b1b ||
          hs == 0x2b50) {
          return true;
        }
      }
    }
  },
  onError(err) {
    console.log(err, 'err')
  }
}, mutations))