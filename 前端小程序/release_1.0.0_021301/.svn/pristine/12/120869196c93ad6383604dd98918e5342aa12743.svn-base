// pages/my-news/my-news.js
let myForEach = getApp().myForEach,
  API = getApp().API,
  socket = null
let $App = getApp();
Page({

  /**
   * 页面的初始数据
   */
  data: {
    userInformation: [],
    staticImageUrl: $App.staticImageUrl,
    startX: 0,
    startY: 0,
    renderNewsText: '暂时没有渲染消息',
    isShowRenderNewsHint: false,
    renderNewsObj: {},
    systemNewObj: {
      isRead: 1,
      title: '暂无系统消息'
    },
    getSystemNewsListTimer: null
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function(options) {
    wx.hideShareMenu();
    this.getRenderNewsList()
    this.getSystemNewsList()
    this.getcommentMessage(0);
    this.getUserChatList()
    // setInterval(() => {  }, 8000)
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
  onReady: function() {},


  getcommentMessage(isRead) {
    API.commentMessage({
      curPage: 0,
      pageSize: 1,
      isRead: isRead,
    }).then(res => {
      if (res.obj) {
        this.setData({
          comment: res.obj[0],
        });
      } else if (isRead) {
        this.getcommentMessage(1);
      }
    });
  },
  getSystemNewsList() { // 获取系统消息轮询
    API.getSystemRenderList({
      order: "gmt_modified",
      orderNum: "desc",
      limit: 1,
      start: 0,
      isRead: 0
    }, 'noLoading').then(res => {
      this.setData({
        systemNewObj: res.success && res.obj ? {
          isRead: res.obj[0].isRead,
          title: res.obj[0].title,
          gmtCreate: res.obj[0].gmtCreate,
        } : {
          isRead: 1,
          title: '暂无系统消息'
        },
      })
    })


  },
  getUserChatList() {
    API.getUserChatList({
      userSessionId: wx.getStorageSync('uuid')
    }).then(res => {
      res.resultCode === 'SUCCESS' ? this.setData({
        userInformation: res.data || [],
      }) : null
    })
  },
  getRenderNewsList() { // 获取用户最后一次渲染的渲染消息
    API.getUserRenderList({
        order: "gmt_modified",
        orderNum: "desc",
        limit: 1,
        start: 0
      })
      .then(res => {
        if (res.success && res.obj) {
          this.setData({
            renderNewsObj: res.obj[0],
          })
        } else {
          this.setData({
            renderNewsObj: {
              title: '暂时没有渲染消息',
              isRead: 1,
              gmtCreate: ''
            }
          })
        }
      })
  },
  routerToRenderNews(e) {
    wx.navigateTo({
      url: '/pages/my-render-news/my-render-news?type=' + e.currentTarget.dataset.type
    })
  },
  routerToCommentNews(e) {
    wx.navigateTo({
      url: '/pages/my-render-comment/my-render-comment'
    })
    this.sellingPoint('renderNews')
  },
  routerToSystemNews(e) {
    wx.navigateTo({
      url: '/pages/systemNews/systemNews'
    })
    this.sellingPoint('systemNews')
  },
  routerToUserNews(e) {
    wx.navigateTo({
      url: '/pages/my-user-news/my-user-news'
    })
    this.sellingPoint('systemNews')
  },
  sellingPoint(event) {
    let page = getCurrentPages(),
      previousPath = page.length > 1 ? page[page.length - 2].route : '',
      nowPath = page[page.length - 1].route
    console.log(previousPath);
    API.sellingPoint({
      uid: wx.getStorageSync('openId'),
      cp: nowPath,
      lp: previousPath,
      e: event ? event : '',
      pt: '消息'
    })
  },
  /**
   * 生命周期函数--监听页面隐藏
   */
  onHide: function() {
    // socket.disconnect() // 断开连接
    clearInterval(this.data.getSystemNewsListTimer) // 设置定时器
  },
  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function() {
    this.init() // 初始化
  },
  init() {
    this.monitorMessage()
  },
  onReachBottom: function() {

  },
  onShareAppMessage: function() {

  }
})