let $App = getApp(), API = getApp().API
Page({

  /**
   * 页面的初始数据
   */
  data: {
    userInfo: '',
    wxInfo: '',
    notLoginImgUrl: '',
    isgetPhone: false,
    issBindingMobile: false,
    renderNewsObj: {},
      systemNewObj:{},
    staticImage: $App.staticImageUrl,
    timer: null,
    renderMessageIsRead: 1 // 渲染消息
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    this.init() // 初始化
  },
  init() { // 初始化
    new $App.bindingPhone()  
    this.setData({ wxInfo: getApp().globalData })
    setInterval(() => { this.getSystemNewsList() }, 8000)
    getApp().mySocket.on('im_unread_msg_event', content => { this.setData({ unreadMsg: 1 }) }) // 消息提醒
    getApp().mySocket.on('im_push_msg_event', content => { this.setData({ renderMessageIsRead: 0}), console.log(content)}) // 渲染消息    
  },
  routerToMyModule(e) { // 跳转至我的模块
    wx.navigateTo({ url: e.currentTarget.dataset.url })
    // e.currentTarget.dataset.type == 'news' ? this.setData({ unreadMsg: 0, renderMessageIsRead: 1, ['systemNewObj.isRead']: 1 }) : null
  },
  getUserChatList() { // 实时消息推送
    API.getUserChatList({ userSessionId: wx.getStorageSync('uuid') }).then(res => {
      res.resultCode === 'SUCCESS' ? this.setData({ unreadMsg: res.data[0].unreadMsg || 0 }) : null
    })
  },
  getSystemNewsList() { // 实时轮训系统消息
      API.getSystemRenderList({
          order: "gmt_modified",
          orderNum: "desc",
          limit: 1,
          start: 0,
          isRead: 0
      },'noLoading').then(res => {
            if (res.success && res.obj) {
              this.setData({ systemNewObj: res.obj[0] })
            } else {
              this.setData({
                  systemNewObj: {
                      title: '暂时没有系统消息',
                      isRead: 1,
                      gmtCreate: ''
                  }
              })
            }
        console.log(this.data.systemNewObj, 'systemNewObj')
          })

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
    this.onShowInit() // 初始化
  },
  onShowInit() { // onshow初始化
    this.getUserInfo()
    this.getIsBindingMobile()
  },

  /**
   * 生命周期函数--监听页面隐藏
   */
  onHide: function () {
    // clearTimeout(this.data.timer)
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
    if (res.from === 'menu') {
      return $App.shareAppMessageObj
    }
  },
  getUserInfo: function () {
    API.getUserDuBiMessage()
    .then(res => {
      this.setData({ userInfo: res.obj })
    })
  },
  showBIndingPhoneBox() { // 打开绑定手机
    this.bindingPhoneShow()
  },
  getIsBindingMobile() {
    // 检验手机号是否存在
    let url = `/v2/user/center/isUserMobileBinded`
    API.getIsBindingMobile()
    .then(res => {
      if (res.success) {
        if (res.obj === 0) {
          this.setData({ issBindingMobile: false })
        } else if (res.obj === 1) {
          this.setData({ issBindingMobile: true })
        }
      } else {
        this.setData({ issBindingMobile: false })
      }
    })
  },
  bindPhoneCallBack() {
    this.setData({
      issBindingMobile: true
    })
    this.getUserInfo()
  },
  toMyAccount(){
    wx.navigateTo({
      url: '/pages/mine/my-account/my-account',
    })
  }
})