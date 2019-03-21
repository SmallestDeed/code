let fetch = getApp().fetch, $App = getApp()
let timer = null
Page({
  /**
   * 页面的初始数据
   */
  data: {
    staticImageUrl: getApp().staticImageUrl,
    resourcePath: getApp().resourcePath,
    sevenUrl: getApp().sevenUrl,    
    curPage: 0,
    pageSize: 10,
    contentlist: [],
    message:"加载中",
    operationUserId:"",
    totalCount: 0,
    flag: false,
    LoopFlag:true,
  },
  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function() {
    new $App.quickNavigation() // 注册组件
    this.setData({
      LoopFlag: true
    })
    // 用户数据
  },
  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function() {},
  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function() {
    this.getSearchResluts('加载数据');
    let $self = this
    timer = setInterval(() => {
      if ($self.data.LoopFlag){
      $self.getSearchResluts('加载数据', 'timer')
      }

    }, 8000)
  },
  /**
   * 生命周期函数--监听页面隐藏
   */
  onHide: function() {
    clearInterval(timer)
  },
  /**
   * 生命周期函数--监听页面卸载
   */
  onUnload: function() {
    var that=this
    this.setData({
      LoopFlag:false
    })
  },
  /**
   * 页面相关事件处理函数--监听用户下拉动作
   */
  onPullDownRefresh: function() {
    wx.stopPullDownRefresh() // 停止刷新写逻辑
    this.setData({
      curPage: 0
    })
    this.getSearchResluts('加载数据');
  },
  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom: function() {
    if (this.data.pageSize >= this.data.totalCount && this.data.flag) {
      return
    } else {
      this.setData({
        pageSize: this.data.pageSize + 10
      })
      this.getSearchResluts('加载数据')
    }
  },
  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function (res) {
    if (res.from === 'menu') {
      return $App.shareAppMessageFn(true);
    }
  },
  getSearchResluts: function(message, timer) {
    let timerText = null
    if (timer === 'timer') {
      timerText = timer
    }
    this.setData({
      flag: false
    })
    var that = this
    var url = "/mobile/pay/getMyReplaceRecord.htm"
    var data = {
      // operationUserId:that.data.operationUserId,
      limit: that.data.pageSize,
      start: that.data.curPage
    }
    fetch(url, 'post', data, 'render', timerText)
      .then((res) => {
        that.setData({
          flag: true
        })
        var contentlistTem = that.data.contentlist
        if (res.success) {
          
          if (res.datalist) {
            this.setData({
              contentlist: res.datalist,
              totalCount: res.totalCount
            })
          } else {
            this.setData({
              contentlist: [],
              message: '暂无数据',
              totalCount: 0
            })
          }
        } else {
          this.setData({
            contentlist: [],
            message: '暂无数据',
            totalCount: 0
          })
        }
      })
      .catch((res) => {
        that.setData({
          message: res.message,
          contentlist: [],
          totalCount: 0
        })
      })
  },
  toThreeD(e) {
      console.log(e.detail.target.dataset.item)
      let item =  e.detail.target.dataset.item,
          renderTypesStr = item.renderTypesStr,
          url = '/mobile/renderPic/getPictureList.htm',
          url2 = '/mobile/design/designPlan/getPanoPicture.htm',
          that = this,
          typeList = ['', 'photo', 'seven', 'roam', 'video'],
        routerQueryType = typeList[renderTypesStr],
          webUrl = null,
          sevenObj = null,
          data = {
              id: item.businessId,
              remark: routerQueryType
          }
      if (item.isSuccess === 2) {
          if (routerQueryType === 'video') {
              fetch(url, 'post', data, 'render')
                  .then(res => {
                      if (res.success) {
                          fetch(url2, 'post', {
                              thumbId: res.datalist[0].id
                          }, 'render').then(res => {
                              console.log(res)
                              res.success ? (routerQueryType === 'video' ? that.toVideo(res.obj.url) : that.enlargeImage([res.obj.url])) : wx.showToast({
                                  title: '打开失败',
                                  icon: 'none'
                              })
                          })
                      }
                  })
          } else {
              item.fullHousePlanUUID ? (webUrl = $App.wholeHouseUrl, sevenObj = {
                  uuid: item.fullHousePlanUUID,
                  embedded: 1
              }) :
                  (webUrl = that.data.sevenUrl, sevenObj = {
                      token: wx.getStorageSync('token'),
                      platformCode: 'brand2c',
                      operationSource: 0,
                      planId: item.businessId,
                      routerQueryType: routerQueryType,
                      customReferer: $App.wxUrl,
                      platformNewCode: 'miniProgram'
                  })
              for (let key in sevenObj) {
                  webUrl += key + '=' + sevenObj[key] + '&'
              }
              getApp().data.webUrl = webUrl
              $App.myNavigateBack('pages/web-720/web-720')
              console.log(webUrl)
          }
      } else if (item.isSuccess == 0 || item.isSuccess == 1) {
          wx.showToast({
              title: '渲染中',
              icon: 'none'
          })
      } else {
          wx.showToast({
              title: '渲染失败',
              icon: 'none'
          })
      }
  },
  enlargeImage(url) { // 查看大图
    wx.previewImage({
      current: url[0], // 当前显示图片的http链接
      urls: [url] // 需要预览的图片http链接列表
    })
  },
  toVideo(url) {
    wx.navigateTo({
      url: '../video/video?url=' + url
    })
  },
  tohousetype() {
    wx.switchTab({
      url: '/pages/house-type/house-type',
    })
  }
})