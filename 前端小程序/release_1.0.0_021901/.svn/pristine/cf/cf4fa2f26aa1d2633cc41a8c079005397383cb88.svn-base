// pages/myself/myself.js
let app = getApp(), API = app.API;
Page({

  /**
   * 页面的初始数据
   */
  data: {
    wxInfo: null,
    userInfo: {},
    isSystemMessages: 1, // 系统消息
    idNewRenderMessages: 1, // 渲染消息
    isUserChatIsRead: 1, // 聊天消息
    systemNewIsRead: 1, // Socket实时消息
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    this.init();
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
    this.getHint();
    this.getLoginStatus()
  },

  /*初始化*/
  init() {
    this.getUserDuBiMessage();
    // Socket实时消息提醒
    getApp().mySocket.on('im_unread_msg_event', content => {
      console.log('1')
      this.setData({ systemNewIsRead: 0 })
    })
    // Socket实时渲染消息
    getApp().mySocket.on('im_push_msg_event', content => {
      this.setData({ idNewRenderMessages: 0 }), console.log(content, 'Socket实时渲染消息')
    });
    // Socket实时聊天消息
    getApp().mySocket.on('im_chat_msg_event', content => {console.log('聊天')})
  },



  /*获取度币*/
  getUserDuBiMessage() {
    API.getUserDuBiMessage().then(res => {
      if (res.obj) {
        res.obj.balanceIntegral = parseInt(res.obj.balanceIntegral)
        this.setData({ userInfo: res.obj })
      }
    });
  },

  /*获取我的消息提示红点*/
  getHint() {
    //系统消息
    API.getSystemRenderList({
      order: "gmt_modified",
      orderNum: "desc",
      limit: 1,
      start: 0,
      isRead: 0
    }, 'noLoading').then(res => {
      if (res.success && res.obj) {
        let obj = res.obj[0];
        this.setData({ isSystemMessages: obj.isRead });
      } else {
        this.setData({ isSystemMessages: 1 });
      }
      console.log(this.data.isSystemMessages, '系统消息')
    });
    //获取用户最后一次渲染的渲染消息
    API.getUserRenderList({
      order: "gmt_modified",
      orderNum: "desc",
      limit: 1,
      start: 0
    }).then(res => {
      if (res.success && res.obj) {
        this.setData({ idNewRenderMessages: res.obj[0].isRead })
      } else {
        this.setData({ idNewRenderMessages: 1 })
      }
      console.log(this.data.idNewRenderMessages, '渲染消息')
    });
    // 获取聊天列表
    API.getUserChatList({ userSessionId: wx.getStorageSync('uuid') }).then(res => {
      if (res.resultCode === 'SUCCESS') {
        for (let i = 0; i < res.data.length; i++) {
          if (res.data[i].unreadMsg > 0) {
            this.setData({isUserChatIsRead: 0});
            console.log(this.data.isUserChatIsRead, '聊天消息')
            return;
          }
        }
        this.setData({isUserChatIsRead: 1});
        console.log(this.data.isUserChatIsRead, '聊天消息')
      }
    })
  },

  /*跳转*/
  skip(e) {
    if (e.currentTarget.dataset.type == 'seekHouse') {
      wx.setStorageSync('caseItem', '')
    }
    wx.navigateTo({
      url: e.currentTarget.dataset.url
    });
  },

  getLoginStatus(){
    let loginData =  wx.getStorageSync('loginStatus')
    console.log(loginData)
    if (loginData.avatarUrl || loginData.headPic) {
      console.log(2222)
      this.setData({
        wxInfo: loginData
      })
      console.log(this.data.wxInfo)
    } else {
      console.log(1111)
      this.setData({
        wxInfo: null
      })
    }
  },

  /*获取用户信息*/
  onGotUserInfo(e) {
    if (e.detail.userInfo) {
      // getApp().globalData.userInfo = e.detail.userInfo
      let loginData = wx.setStorageSync('loginStatus', e.detail.userInfo)
      // this.setData({ wxInfo: false })
    } else {
      this.setData({ wxInfo: false })
    }
    this.setData({
      wxInfo: e.detail.userInfo
    })
    API.saveMinProNickName({
      nickName: e.detail.userInfo.nickName,
      headPic: e.detail.userInfo.avatarUrl
    })
  },
  closeBtn() {
    wx.removeStorage({
      key: 'loginStatus',
      success: function (res) { },
    })
    this.setData({
      wxInfo: false,
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
  onShareAppMessage: function () {

  }
})