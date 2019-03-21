// pages/web-720/web-720.js
let mySplitUrl = getApp().mySplitUrl, myCompoundUrl = getApp().myCompoundUrl, $App = getApp()
Page({

  /**
   * 页面的初始数据
   */
  data: {
    webUrl: '',
    webViewNum: 0,
    isGrass: 0,
    shareText: '720 3D全景图',
    coverPath: getApp().staticImageUrl + "wechat_share_02.png"
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    console.log(options, 'wqwq')
    if (options.scene) {
      this.data.isGrass = 1
      this.loginUponInitWebUrl(options)
    } else if (options.item) {
      this.data.isGrass = 1
      this.setData({ webUrl: myCompoundUrl(JSON.parse(options.item)) })
    } else {
      console.log(getApp().data.webUrl, 'getApp().data.webUrl')
      this.data.isGrass = 0
      this.setData({ webUrl: getApp().data.webUrl })
      console.log(this.data.webUrl, 'webUrl')
    }
  },
  loginUponInitWebUrl(options) {
    let scene = decodeURIComponent(options.scene), sceneObj = mySplitUrl(scene), that = this
    getApp().data.webUrl = myCompoundUrl({
      url: getApp().grassSevenUrl,
      renderId: sceneObj.i,
      sceneType: sceneObj.t,
      planSourceType: sceneObj.s || '',
      hasChanged: sceneObj.b || '',
      qrCodeType: sceneObj.c,
      customReferer: getApp().wxUrl,
      platformCode: 'miniProgram',
      platformNewCode: 'miniProgram'
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
    this.data.shareText = e.detail.data[0].shareText || this.data.shareText
    this.data.coverPath = e.detail.data[0].coverPath ? (getApp().resourcePath + e.detail.data[0].coverPath) : getApp().staticImageUrl + "wechat_share_02.png"
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
    console.log('进来吧1！！！')
    if (wx.getStorageSync('payIsSuccess') && wx.getStorageSync('payIsSuccess') != 3) {
      console.log(1)
      let webUrl = this.data.webUrl
      this.setData({ webUrl: this.data.webUrl + 'payIsSuccess=' + (wx.getStorageSync('payIsSuccess') || 0) + '&date=' + new Date().getTime() })
      this.data.webUrl = webUrl
      wx.setStorageSync('payIsSuccess', 3)
    }
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
    item.url = encodeURIComponent(item.url)
    let count = Math.round(Math.random() * 2) + 1
    let imageUrl = getApp().staticImageUrl + "wechat_share_02.png"
    let path = `/pages/index/index?item=` + JSON.stringify(item)
    console.log(path)
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