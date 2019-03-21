  // pages/web-720/web-720.js
let mySplitUrl = getApp().mySplitUrl, myCompoundUrl = getApp().myCompoundUrl, API = getApp().API, $App = getApp()
Page({

  /**
   * 页面的初始数据
   */
  data: {
    webUrl: '',
    webViewNum: 0,
    staticImageUrl: getApp().staticImageUrl,
    shareText: '我刚刚用随选网设计了一套房子，快来看看吧！',
    isGrass: 0, // 是否是通用版的720 是1不是0
    coverPath: getApp().staticImageUrl + "wechat_share_02.png"
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    if (options.scene) {
      this.data.isGrass = 1
      this.loginUponInitWebUrl(options)
    } else if (options.item) {
      this.data.isGrass = 1
      this.grassSecondShare(options)
    } else {   
      let page = getCurrentPages(), previousPath = page.length > 1 ? page[page.length - 2].route : ''
      let webUrl = (options.item ? myCompoundUrl(JSON.parse(options.item)) : getApp().data.webUrl) + '&lp=' + previousPath + '&uid=' + wx.getStorageSync('openId')
      this.data.isGrass = 0
      this.setData({ webUrl })
      console.log(webUrl, 'webUrl')
    }
    this.sellingPoints() // 设置埋点
  },
  grassSecondShare(options) {
    let item = JSON.parse(options.item)
    item.url = decodeURIComponent(item.url)
    getApp().data.webUrl = myCompoundUrl(item)
    this.setData({ webUrl: '' })
    $App.userLoginStatus.then(() => {
      console.log(getApp().data.webUrl + '&token=' + res.obj.token, '啊')
      this.setData({ webUrl: getApp().data.webUrl + '&token=' + wx.getStorageSync('token') })
    })
  },
  loginUponInitWebUrl(options) {
    let scene = decodeURIComponent(options.scene), sceneObj = mySplitUrl(scene), that = this
    console.log(scene)    
    getApp().data.webUrl = myCompoundUrl({
      url: getApp().grassSevenUrl,
      renderId: sceneObj.i,
      sceneType: sceneObj.t,
      planSourceType: sceneObj.s || '',
      hasChanged: sceneObj.b || '',
      qrCodeType: sceneObj.c,
      customReferer: getApp().wxUrl,
      platformCode: 'miniProgram',
      platformNewCode: 'miniProgram',
      isRender: 0
    })
    $App.userLoginStatus.then(() => {
      this.setData({ webUrl: getApp().data.webUrl + '&token=' + wx.getStorageSync('token') })
      console.log(this.data.webUrl)
    })
  },
  bindmessage(e) {
    console.log(e.details)
  },
  sendRenderIdParams(e) {
    let renderIdParams = e.detail.data[0].renderIdParams
    let prevPage = getCurrentPages()[getCurrentPages().length - 2]
    this.data.shareText = e.detail.data[0].shareText || this.data.shareText
    this.data.coverPath = e.detail.data[0].coverPath ? (getApp().resourcePath + e.detail.data[0].coverPath) : getApp().staticImageUrl + "wechat_share_02.png"
    console.log(this.data.shareText, this.data.coverPath, 'sqwqwq')
    prevPage.setData({ atPresentUuid: e.detail.data[0].atPresentUuid || '' })    
    if (renderIdParams && JSON.stringify(renderIdParams) !== '{}') {     
      prevPage.setData({ fullHousePlanId: renderIdParams.fullHousePlanId, mainTaskId: renderIdParams.mainTaskId, isOnShow: 1, atPresentUuid: e.detail.data[0].atPresentUuid || ''})  
    }
  },
  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function () {

  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function () {
    this.planPayCallback() // 方案收费回调
  },
  planPayCallback() {
    if (wx.getStorageSync('payIsSuccess') && wx.getStorageSync('payIsSuccess') != 3) {
      console.log(1)
      let webUrl = this.data.webUrl
      this.setData({ webUrl: this.data.webUrl + 'payIsSuccess=' + (wx.getStorageSync('payIsSuccess') || 0) + '&date=' + new Date().getTime() })
      this.data.webUrl = webUrl
      wx.setStorageSync('payIsSuccess', 3)
    }
  },
  sellingPoints() {
    let page = getCurrentPages(), previousPath = page.length > 1 ? page[page.length - 2].route : '', nowPath = page[page.length - 1].route
    API.sellingPoint({ 
      uid: wx.getStorageSync('openId'),
      cp: nowPath,
      lp: previousPath,
      // e: event ? event : '',
      pt: '3D全景'
      // data: [{ openId: wx.getStorageSync('openId'), currentPage: nowPath, lastPage: previousPath }]
    }).then(res => {
      // console.log(res, 'swqwqwqwqw')
    })
  },
  changeWebUrl() {
    this.setData({
      webUrl: ''
    })
    this.setData({
      webUrl: getApp().data.webUrl
    })
  },

  /**
   * 生命周期函数--监听页面隐藏
   */
  onHide: function () {

  },

  /**
   * 生命周期函数--监听页面卸载
   */
  onUnload: function () {

  },

  /**
   * 页面相关事件处理函数--监听用户下拉动作
   */
  onPullDownRefresh: function () {

  },

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom: function () {

  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function (res) {
    let that = this
    let item = mySplitUrl(res.webViewUrl)
    delete item.token
    item.isRender = 0
    item.url = encodeURIComponent(item.url)
    let path = (this.data.isGrass == 0 ? `/pages/plan/house-case/house-case?item=` : `/pages/web-720/web-720?item=`) + JSON.stringify(item)
    if (res.from === 'menu') {
      return {
        title: this.data.shareText,
        imageUrl: this.data.coverPath,
        path: path,
        success(res) {
          console.log(path, 'res')
        },
        fail(err) {
          console.log(err, 'err')
        }
      }
    }
  }
})  