// pages/myNews/myNews.js
let app = getApp(), API = app.API;
Page({

  /**
   * 页面的初始数据
   */
  data: {
    systemMessages: {}, // 系统消息
    renderMessages: {}, // 渲染消息
    idNewRender: false,
    chatMessagesList: [], // 聊天信息
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    
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
    this.init();
  },
  
  /*页面数据初始化*/
  init() {
    this.getSystemNewsList();
    this.getRenderNewsList();
    this.getUserChatList();
  },

  /*系统消息*/
  getSystemNewsList() {
    API.getSystemRenderList({
      order: "gmt_modified",
      orderNum: "desc",
      limit: 1,
      start: 0,
      isRead: 0
    }, 'noLoading').then(res => {
      if (res.success && res.obj) {
        console.log(12311)
        let obj = res.obj[0];
        obj.gmtModified = app.changeTiem(obj.gmtModified);
        this.setData({ systemMessages: obj });
      } else {
        this.setData({ 
          systemMessages: { content: '无系统消息', isRead: 1} 
        });
      }
    })
  },

  /*获取用户最后一次渲染的渲染消息*/
  getRenderNewsList() { 
    API.getUserRenderList({
      order: "gmt_modified",
      orderNum: "desc",
      messageType: 1,
      limit: 1,
      start: 0
    }).then(res => {
      if (res.success && res.obj) {
        let obj = res.obj[0];
        obj.gmtModified = app.changeTiem(obj.gmtModified);
        this.setData({ renderMessages: obj, idNewRender: obj.isRead })
      } else {
        this.setData({ renderMessages: { title: res.message}, idNewRender: 1 })
      }
    })
  },

  /*获取聊天列表*/
  getUserChatList() {
    API.getUserChatList({ userSessionId: wx.getStorageSync('uuid') }).then(res => {
      if (res.resultCode === 'SUCCESS') {
        this.setData({ chatMessagesList: res.data || [] })
      }
    })
  },

  /*去聊天页面*/
  goChat(e) {
    let item = JSON.stringify(e.currentTarget.dataset.item);
    wx.navigateTo({
      url: `/pages/chat/chat?item=${item}`
    });
  },

  /*去查看消息页面*/
  goNews(e) {
    let type = e.currentTarget.dataset.type;
    wx.navigateTo({
      url: `/pages/newsList/newsList?type=${type}`
    });
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