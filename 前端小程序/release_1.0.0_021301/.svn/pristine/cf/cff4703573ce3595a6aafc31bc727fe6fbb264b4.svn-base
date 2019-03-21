// pages/my-user-news/my-user-news.js
let $App = getApp(),
  API = getApp().API,
  myForEach = getApp().myForEach,
  socket = null;
Page({
  /**
   * 页面的初始数据
   */
  data: {
    staticImageUrl: $App.staticImageUrl,
    message: "暂时还没有消息记录~"
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    wx.hideShareMenu({}) 
  },

  monitorMessage() {
    getApp().mySocket.emit('im_loc_msg_event', {
      userSessionId: wx.getStorageSync('uuid'),
      loc: 3,
      appId: 16,
    }) // 上报位置
    getApp().mySocket.on('im_unread_msg_event', content => {
      this.getUserChatList(content)
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
    this.init();
  },
  init() {
    this.monitorMessage()
    this.getUserChatList();
  },
  getUserChatList() {
    API.getUserChatList({
      userSessionId: wx.getStorageSync('uuid')
    }).then(res => {
      if (res.resultCode === 'SUCCESS') {
        this.setData({
          userInformation: res.data || []
        })
      }
    })
  },
  routerToChatFrame(e) {
    let item = JSON.stringify(e.currentTarget.dataset.item)
    wx.navigateTo({ url: `/pages/chat-frame/chat-frame?item=${item}` })
  },
  informationTouchStart(e) { // 开始滑动
    this.setData({ startX: e.touches[0].clientX, startY: e.touches[0].clientY, })
    myForEach(this.data.userInformation, (value) => { value.isMove = false })
    this.setData({ userInformation: this.data.userInformation })
  },
  informationTouchMove(e) { // 滑动过程
    let moveX = e.touches[0].clientX,
      moveY = e.touches[0].clientY
    if (this.computeAngle((moveX - this.data.startX), (moveY - this.data.startY))) {
      this.setData({
        ['userInformation[' + e.currentTarget.dataset.index + '].isMove']: (moveX <= this.data.startX)
      })
    }
  },
  computeAngle(x, y) { // 计算角度
    return !(Math.abs(Math.atan(y / x) * (180 / Math.PI)) > 30)
  },
  deleteUserContacts(e) { // 删除联系人
    let item = e.currentTarget.dataset.item, index = e.currentTarget.dataset.index
    console.log(item)
    API.deleteUserContacts({
      userSessionId: wx.getStorageSync('uuid'),
      contactSessionId: item.contactSessionId,
      relatedObjId: item.relatedObjId,
      relatedObjType: item.relatedObjType
    }).then(res => {
      wx.showToast({ title: '删除' + (res.resultCode === "SUCCESS" ? "成功" : "失败") })
      if (res.resultCode === "SUCCESS") {
        this.data.userInformation.splice(index, 1)
        this.setData({ userInformation: this.data.userInformation })
      }
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