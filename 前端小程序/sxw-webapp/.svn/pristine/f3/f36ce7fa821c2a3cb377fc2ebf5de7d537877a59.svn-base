// pages/mine/my-favorite-fitment/choose-designer/choose-designer.js
let API = getApp().API,
  $App = getApp()
Page({

  /**
   * 页面的初始数据
   */
  data: {
    staticImageUrl: getApp().staticImageUrl,
    resourcePath: getApp().resourcePath,
    searchWord: '',
    // 埋点数据
    previousPath: '',
    nowPath: ''
  },
  otherList: {
    index: 1,
    size: 10
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    // rzd 埋点 190214 start
    let page = getCurrentPages(), previousPath = page.length > 1 ? page[page.length - 2].route : '',
      nowPath = page[page.length - 1].route;
    this.setData({
      previousPath: previousPath,
      nowPath: nowPath
    })
    // rzd 埋点 190214 end
    this.setData(options)
    this.initializeChatBox(options)
    if (options.type == 6) {
      wx.setNavigationBarTitle({
        title: '选择装修公司',
      })
    }
    // this.getNearestContactShop()
    // this.getRecommendList()
    this.getContactShop()
  },
  initializeChatBox(item) { // 初始化聊天框信息
    this.data.friendUuid = item.contactSessionId || item.sessionId
    this.data.toUserShopId = item.id || item.relatedObjId
    this.setData({
      myFriendMessageObj: item
    })
  },
  focus() {
    this.setData({
      isActived: true
    })
  },
  blur(e) {
    if (e.detail.value.trim().length == 0) {
      this.setData({
        isActived: false
      })
    }
  },
  input(e) {
    console.log(e.detail.value)
    this.setData({
      searchWord: e.detail.value.trim(),
    })
    this.getContactShop()
  },
  clear() {
    this.setData({
      searchList: null,
      searchWord: '',
      isActived: false
    })
    this.getContactShop();
  },
  getContactShop(index = 1) { //获取列表
    this.otherList.index = index;
    API.getContactShop({
      shopName: this.data.searchWord,
      recommendShopIds: this.data.type == 5 ? "1512,1266,1241" : "1394,1393,1519",
      userId: wx.getStorageSync('id'),
      pageNo: this.otherList.index,
      pageSize: this.otherList.size,
      nearestContactShopLimit: 3,
      businessTypes: this.data.type == 5 ? "3,4" : "5",
    })
      .then(res => {
        if (this.data.result) this.data.result.others.list.push(...res.data.others.list)
        this.setData({
          result: this.data.result || res.data
        })
      })
  },
  toggleModel(e) {
    if (!this.data.isShowModel) {
      $App.sellingPoint(API, 'seekDesignertoggleModel', this.data.nowPath, this.data.previousPath, '选择设计师');
    } else {
      $App.sellingPoint(API, 'seekDesignercancle', this.data.nowPath, this.data.previousPath, '选择设计师');
    }
    if (e && e.currentTarget.dataset.item) {
      this.setData({
        modelItem: e.currentTarget.dataset.item
      })
    }
    this.setData({
      isShowModel: !this.data.isShowModel
    })
  },
  modelInputBlur(e) {
    console.log('modelInputBlur', e)
    this.data.modelItem.value = e.detail.value.trim();
  },
  confirm() {
    $App.sellingPoint(API, 'seekDesignerconfirm', this.data.nowPath, this.data.previousPath, '选择设计师');
    // 方案消息
    getApp().mySocket.emit('im_chat_msg_event', {
      fromUserSessionId: wx.getStorageSync('uuid'),
      toUserSessionId: this.data.modelItem.sessionId,
      msgBody: this.data.msgBody,
      relatedObjId: this.data.modelItem.id,
      relatedObjType: 1,
      fromAppId: 16,
      relatedObjOwnerSessionId: this.data.modelItem.sessionId,
      msgType: this.data.msgType
    })
    if (this.data.modelItem.value) {
      // 文字消息
      getApp().mySocket.emit('im_chat_msg_event', {
        fromUserSessionId: wx.getStorageSync('uuid'),
        toUserSessionId: this.data.modelItem.sessionId,
        msgBody: this.data.modelItem.value,
        relatedObjId: this.data.modelItem.id,
        relatedObjType: 1,
        fromAppId: 16,
        relatedObjOwnerSessionId: this.data.modelItem.sessionId,
        msgType: 0
      })
      this.setData({
        'modelItem.value': ''
      })
    }
    wx.showToast({
      title: '发送成功！',
      mask: true,
      duration: 2000,
      success() {
        setTimeout(() => {
          wx.navigateBack({
            delta: 1,
          })
        }, 2000)
      },
    })
    this.toggleModel()
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
    this.getContactShop(++this.otherList.index)
  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function () {

  }
})