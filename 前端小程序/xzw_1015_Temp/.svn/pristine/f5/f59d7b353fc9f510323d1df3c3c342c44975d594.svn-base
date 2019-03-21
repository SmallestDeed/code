import utils from '../../utils/utils.js'
let API = getApp().API, $APP = getApp()
Page({

  /**
   * 页面的初始数据
   */
  data: {
    resourcePath: getApp().resourcePath,
    chatContentList: [],
    scrollId: '',
    chatContent: '',
    inputFocus: true,
    friendUuid: '',
    socket: '',
    myFriendMessageObj: {},
    toUserShopId: '',
    shopDetails: {}, // 店铺详情
    userChatMessage: ''
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    this.initLoad(options) // 初始化
  },
  initLoad(options) { // 初始化
    options.item = options.item.replace(/(^\s+)|(\s+$)/g, "")
    let item = JSON.parse(options.item.replace(/&/g, "$"))
    this.initializeChatBox(item) // 初始化聊天框信息  
    this.getShopDetails(item) // 获取店铺详情
    this.getChatingRecords() // 获取聊天记录
    this.refreshMessageRead() // 刷新消息为已读
  },
  initializeChatBox(item) { // 初始化聊天框信息
    this.data.friendUuid = item.contactSessionId || item.sessionId
    this.data.toUserShopId = item.id || item.relatedObjId
    wx.setNavigationBarTitle({ title: item.contactName || item.shopName })
    this.setData({ myFriendMessageObj: item})
  },
  getShopDetails(item) { // 获取店铺详情
    API.getCompanyDetails({ id: item.id || item.relatedObjId}).then(res => {
      if (res.code === 200 && res.data) {
        this.setData({ shopDetails: res.data })
      }
    })
  },  
  refreshMessageRead() { // 重置消息未读
    API.refreshMessageRead({ 
      userSessionId: wx.getStorageSync('uuid') || '', 
      contactSessionId: this.data.friendUuid, 
      relatedObjId: this.data.toUserShopId,
      relatedObjType: 1
    })
    .then(res => {
      console.log(res, '成功刷新')
    })
  },
  getChatingRecords() { // 获取聊天记录
    API.getChatingRecords({
      userSessionId: wx.getStorageSync('uuid') || '',
      contactSessionId: this.data.friendUuid,
      relatedObjId: this.data.toUserShopId,
      relatedObjType: 1,
      pageNum: 1,
      pageSize: 10000
    })
    .then(res => {
      if (res.resultCode === 'SUCCESS') {
        let list = res.data.list ? res.data.list.reverse() : []
        list.length > 0?res.data.list[list.length - 1]['scrollId'] = `scrollId${list.length}` : ''
        this.setData({ chatContentList: list || []})
        this.setData({ scrollId: `scrollId${list.length}` })
      }
    })
  },
  monitorMessage() {
    const $self = this
    getApp().mySocket.emit('im_loc_msg_event', { userSessionId: wx.getStorageSync('uuid'), loc: 1, contactSessionId: this.data.friendUuid, appId: 16, relatedObjType: 1, relatedObjId: this.data.toUserShopId})
    getApp().mySocket.on('im_chat_msg_event', content => {
      let key = 'chatContentList[' + $self.data.chatContentList.length + ']', id = `scrollId${$self.data.chatContentList.length}`
      $self.setData({
        [key]: { msgBody: content.msgBody, time: utils.getNowTime(), direction: 2, scrollId: id },
        scrollId: id
      })
    })
  },
  routerToUserDetails() {
    this.setData({ scrollId: 'scrollId' })    
  },
  sendUserMessage(e) { // 发送消息
    if (this.data.userChatMessage) {
      let key = 'chatContentList[' + this.data.chatContentList.length + ']', id = `scrollId${this.data.chatContentList.length}`
      this.setData({ 
        [key]: { msgBody: this.data.userChatMessage, time: utils.getNowTime(), direction: 1, scrollId: id },
      })
      console.log(utils.getNowTime(), 'wqwq')
      this.setData({ scrollId: id, chatContent: ''})
      getApp().mySocket.emit('im_chat_msg_event', {
        fromUserSessionId: wx.getStorageSync('uuid'),
        toUserSessionId: this.data.friendUuid,
        msgBody: this.data.userChatMessage,
        relatedObjId: this.data.toUserShopId,
        relatedObjType: 1,        
        fromAppId: 16,
        relatedObjOwnerSessionId: this.data.friendUuid
      })
      this.setData({ userChatMessage: ''})
    }
  },
  changeUserChatMessage(e) { // 设置聊天内容
    this.setData({ userChatMessage: e.detail.value})
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
    this.monitorMessage() // 监听实时消息    
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
    // socket.disconnect() // 断开连接  
  },

  /**
   * 页面相关事件处理函数--监听用户下拉动作
   */
  onPullDownRefresh: function () {
    console.log(1)
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