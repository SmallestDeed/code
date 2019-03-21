  // pages/web-720/web-720.js
let mySplitUrl = getApp().mySplitUrl, myCompoundUrl = getApp().myCompoundUrl, API = getApp().API
Page({

  /**
   * 页面的初始数据
   */
  data: {
    webUrl: '',
    webViewNum: 0,
    staticImageUrl: getApp().staticImageUrl,
    shareText: '我刚刚用随选网设计了一套房子，快来看看吧！'
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    if (options.type === 'grass') {
      this.loginUponInitWebUrl()
    } else {
      this.setData({ webUrl: options.item ? myCompoundUrl(JSON.parse(options.item)) : getApp().data.webUrl })      
    }
  },
  loginUponInitWebUrl() {
    let that = this
    this.setData({ webUrl: ''})
    wx.login({
      success: res => {
        API.getUserOpenId({ code: res.code, appid: 'wx42e6b214e6cdaed3' })
          .then(res => {
            if (res.success) {
              wx.setStorageSync('openId', res.obj)
              // 登录
              API.setUserLogin({ openid: res.obj, appid: 'wx42e6b214e6cdaed3' })
                .then(res => {
                  if (res.success) {
                    wx.setStorageSync('token', res.obj.token)
                    wx.setStorageSync('id', res.obj.id)
                    wx.setStorageSync('uuid', res.obj.sessionId || '')
                    console.log(getApp().data.webUrl + '&token=' + res.obj.token, 'wq')
                    this.setData({ webUrl: getApp().data.webUrl + '&token=' + res.obj.token})
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
  bindmessage(e) {
    console.log(e.details)
  },
  sendRenderIdParams(e) {
    console.log(e, 'wqwq')
    let renderIdParams = e.detail.data[0].renderIdParams
    let prevPage = getCurrentPages()[getCurrentPages().length - 2]
    this.data.shareText = e.detail.data[0].shareText || this.data.shareText
    if (renderIdParams && JSON.stringify(renderIdParams) !== '{}') {
      prevPage.setData({ fullHousePlanId: renderIdParams.fullHousePlanId, mainTaskId: renderIdParams.mainTaskId, isOnShow: 1 })  
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
    console.log('进来吧1！！！')
    if (wx.getStorageSync('payIsSuccess') && wx.getStorageSync('payIsSuccess') != 3) {
      console.log(1)
      let webUrl = this.data.webUrl
      console.log(webUrl)
      this.setData({ webUrl: this.data.webUrl + 'payIsSuccess=' + (wx.getStorageSync('payIsSuccess') || 0) + '&date=' + new Date().getTime() }) 
      this.data.webUrl = webUrl
      wx.setStorageSync('payIsSuccess', 3) 
    } else {
      // this.setData({ webUrl: getApp().data.webUrl })                                                                                                                               
    }
    console.log(this.data.webUrl)
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
    console.log(item, 'swq')
    delete item.token
    item.url = encodeURIComponent(item.url)
    let count = Math.round(Math.random() * 2) + 1
    let imageUrl = getApp().staticImageUrl +  'wechat_share_0' + 2 + '.png'
    let path = `/pages/plan/house-case/house-case?item=` + JSON.stringify(item)
    if (res.from === 'menu') {
      return {
        title: this.data.shareText,
        imageUrl: imageUrl,
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