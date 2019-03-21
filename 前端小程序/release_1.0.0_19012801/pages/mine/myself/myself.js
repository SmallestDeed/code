let $App = getApp(), API = getApp().API
Page({

  /**
   * 页面的初始数据
   */
  data: {
    staticImageUrl: $App.staticImageUrl,
    userInfo: '',
    wxInfo: '',
    notLoginImgUrl: '',
    isgetPhone: false,
    issBindingMobile: false,
    // renderMessageIsRead: 1, // Socket消息
    commentIsRead: 1, //评论消息
    userChatIsRead: 1, //聊天消息
    timer: null,
    getSystemNewsListTimer: null,
    planNum:''
  },
  //埋点
  sellingPoint(event) {
    let page = getCurrentPages(),
      previousPath = page.length > 1 ? page[page.length - 2].route : '',
      nowPath = page[page.length - 1].route
    API.sellingPoint({
      uid: wx.getStorageSync('openId'),
      cp: nowPath,
      lp: previousPath,
      e: event ? event : '',
      pt: '我的'
    })
  },
  goMyfavorite() {
    this.sellingPoint('my-favorite')
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    wx.hideShareMenu();
    this.init() // 初始化
    this.sellingPoint();
  },
  init() { // 初始化
    this.isRead()
    new $App.bindingPhone()
    this.setData({
      wxInfo: getApp().globalData
    })
    // setInterval(() => { this.getcommentMessage() }, 8000)
    getApp().mySocket.on('im_unread_msg_event', content => {
      this.setData({
        systemNewIsRead: 0
      })
    }) // 消息提醒
    // getApp().mySocket.on('im_push_msg_event', content => {
    //   this.setData({
    //     systemNewIsRead: 0
    //   }), console.log(content)
    // }) // 渲染消息
    this.getUserMessage() // 获取用户头像
  },
  getUserMessage() {
    getApp().globalData.userInfo && this.setData({
      wxInfo: getApp().globalData
    })
  },
  getInfo(e) {
    if (e.detail.userInfo) {
      getApp().globalData.userInfo = e.detail.userInfo;
    }
    this.setData({
      wxInfo: getApp().globalData
    })
    API.saveMinProNickName({
      nickName: e.detail.userInfo.nickName,
      headPic: e.detail.userInfo.avatarUrl
    })
  },
  routerToMyModule(e) { // 跳转至我的模块
    wx.navigateTo({
      url: e.currentTarget.dataset.url
    })
    // e.currentTarget.dataset.type == 'news' ? this.setData({ unreadMsg: 0, renderMessageIsRead: 1, ['systemNewObj.isRead']: 1 }) : null
  },
  // getUserChatList() { // 实时消息推送
  //   API.getUserChatList({ userSessionId: wx.getStorageSync('uuid') }).then(res => {
  //     res.resultCode === 'SUCCESS' ? this.setData({ unreadMsg: res.data[0].unreadMsg || 0 }) : null
  //   })
  // },
  isRead() {
    let arr = [
      this.getcommentMessage(), //评论消息
      this.getUserChatList(), //聊天消息
    ];
    Promise.all(arr)
      .then((results) => {
        {
          if (results[0].obj) {
            this.setData({
              commentIsRead: results[0].obj[0].isRead
            });
          } else {
            this.setData({
              commentIsRead: 1
            });
          }
        } {
          if (this.data) {
            for (let i = 0; i < results[1].data.length; i++) {
              if (results[1].data[i].unreadMsg > 0) {
                this.setData({
                  userChatIsRead: 0
                });
                break;
              }
            }
            this.setData({
              userChatIsRead: 1
            });
          }
        }
        this.setData({
          isRead: this.data.commentIsRead == this.data.userChatIsRead == 1
        })
      })
  },

  //方案数量
  getUserPlanCount() {
    API.getUserPlanCount().then(res => { if (res.status) { this.setData({ planNum: res.obj }) } })
  },

  //评论消息
  getcommentMessage(resolve, reject) {
    return API.commentMessage({
      curPage: 0,
      pageSize: 1,
      isRead: 0,
    })
  },
  // 聊天消息
  getUserChatList(resolve, reject) {
    return API.getUserChatList({
      userSessionId: wx.getStorageSync('uuid')
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
    this.getUserChatList()
    this.getUserPlanCount()
  },

  /**
   * 生命周期函数--监听页面隐藏
   */
  onHide: function () {
  },

  /**
   * 生命周期函数--监听页面卸载
   */
  onUnload: function () { },

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
        this.setData({
          userInfo: res.obj
        })
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
            this.setData({
              issBindingMobile: false
            })
          } else if (res.obj === 1) {
            this.setData({
              issBindingMobile: true
            })
          }
        } else {
          this.setData({
            issBindingMobile: false
          })
        }
      })
  },
  bindPhoneCallBack() {
    this.setData({
      issBindingMobile: true
    })
    this.getUserInfo()
  },
  toMyAccount() {
    wx.navigateTo({
      url: '/pages/mine/my-account/my-account',
    })
  }
})