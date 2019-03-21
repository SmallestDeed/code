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
        comment: null,
        renderNewsText: '暂时没有渲染消息',
        isShowRenderNewsHint: false,
        renderNewsObj: {},
        systemNewObj:{
          isRead: 1,
          title: '暂无系统消息'
        },
    },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    // setInterval(() => { this.getSystemNewsList() }, 8000)
  },
  
  monitorMessage() {
    getApp().mySocket.emit('im_loc_msg_event', { 
      userSessionId: wx.getStorageSync('uuid'), loc: 3, appId: 16, 
    }) // 上报位置
    getApp().mySocket.on('im_unread_msg_event', content => { 
      this.getUserChatList(content);
      console.log(content, '消息');
    })    
  },


    /**
     * 生命周期函数--监听页面初次渲染完成
     */
    onReady: function() {},

  /**
   * 生命周期函数--监听页面显示
   */
  // onShow: function () {
  //   console.log(1)
  //   this.monitorMessage()    
  //   this.getRenderNewsList()
  //   this.getUserChatList()
  // },
  getcommentMessage(isRead) {
    API.commentMessage({
      curPage: 0,
      pageSize: 1,
      isRead: isRead
    }).then(res => {
      if (res.obj) {
        this.setData({
          comment: res.obj[0]
        });
      } else if (isRead == 0) {
        this.getcommentMessage(1);
      }
    });
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
      wx.showToast({ title: '删除' + (res.resultCode === "SUCCESS" ? "成功" : "失败")})
      if (res.resultCode === "SUCCESS") {
        this.data.userInformation.splice(index, 1)
        this.setData({ userInformation: this.data.userInformation })
      }
    })
  },
  getUserChatList(content) {
    console(content, 12132132)
    API.getUserChatList({ userSessionId: wx.getStorageSync('uuid')}).then(res => {
      res.resultCode === 'SUCCESS' ? this.setData({ userInformation: res.data || [] }) : null
    })
  },
  routerToChatFrame(e) {
    let item = JSON.stringify(e.currentTarget.dataset.item)
    wx.navigateTo({ url: `/pages/chat-frame/chat-frame?item=${item}` })
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
        this.setData({ renderNewsObj: res.obj[0] })
      } else {
        this.setData({ renderNewsObj: { title: '暂时没有渲染消息', isRead: 1, gmtCreate: '' }})        
      }
    })
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
      this.setData({ ['userInformation['+ e.currentTarget.dataset.index + '].isMove']: (moveX <= this.data.startX) })
    }
  },
  computeAngle(x, y) { // 计算角度
    return !(Math.abs(Math.atan(y / x) * (180 / Math.PI)) > 30)
  },
  routerToRenderNews() {
    wx.navigateTo({ url: '/pages/my-render-news/my-render-news' })
  },
  routerToCommentNews(e) {
    wx.navigateTo({ url: '/pages/my-render-comment/my-render-comment' })
  },
  /**
   * 生命周期函数--监听页面隐藏
   */
  onHide: function () {
    // socket.disconnect() // 断开连接
  },


    /**
     * 生命周期函数--监听页面显示
     */
    onShow: function() {
      this.init() // 初始化
    },
    init() {
      this.getcommentMessage(0)
      this.monitorMessage()
      this.getRenderNewsList()
      this.getUserChatList()
    },
    getUserChatList() {
        API.getUserChatList({
                userSessionId: wx.getStorageSync('uuid')
            })
            .then(res => {
                if (res.resultCode === 'SUCCESS') {
                    this.setData({
                        userInformation: res.data || []
                    })
                }
            })
    },
    routerToChatFrame(e) {
        let item = JSON.stringify(e.currentTarget.dataset.item)
        wx.navigateTo({
            url: `/pages/chat-frame/chat-frame?item=${item}`
        })
    },
    getRenderNewsList() { // 获取用户最后一次渲染的渲染消息
        API.getUserRenderList({
            order: "gmt_modified",
            orderNum: "desc",
            messageType: 1,
            limit: 1,
            start: 0
        })
        .then(res => {
            if (res.success && res.obj) {
                this.setData({
                    renderNewsObj: res.obj[0]
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
    getSystemNewsList() { // 获取系统消息轮询
        API.getSystemRenderList({
            order: "gmt_modified",
            orderNum: "desc",
            limit: 1,
            start: 0,
            isRead: 0
        }, 'noLoading').then(res => {
          this.setData({
            systemNewObj: res.success && res.obj ? { isRead: res.obj[0].isRead, title: res.obj[0].title } : { isRead: 1, title: '暂无系统消息' }
          })
          // console.log(this.data.systemNewObj)
        })
        
    },
    informationTouchStart(e) { // 开始滑动
        this.setData({
            startX: e.touches[0].clientX,
            startY: e.touches[0].clientY,
        })
        myForEach(this.data.userInformation, (value) => {
            value.isMove = false
        })
        this.setData({
            userInformation: this.data.userInformation
        })
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
    routerToRenderNews(e) {
        wx.navigateTo({
            url: '/pages/my-render-news/my-render-news?type=' + e.currentTarget.dataset.type
        })
    },
    routerToSystemNews(e){
        wx.navigateTo({
            url: '/pages/systemNews/systemNews'
        })
    },
    onReachBottom: function() {

    },
    onShareAppMessage: function() {

    }
})