let $App = getApp(), API = getApp().API
let timer = null
Page({
  /**
   * 页面的初始数据
   */
  data: {
    staticImageUrl: $App.staticImageUrl,
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
    emptyPageObj: {}
  },
  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function(options) {
    wx.hideShareMenu();
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
      return $App.shareAppMessageObj
    }
  },
  getSearchResluts: function(message, timer) {
    let timerText = null
    if (timer === 'timer') { timerText = timer }
    this.setData({ flag: false })
    API.getUserRenderTasksList({ limit: this.data.pageSize, start: this.data.curPage})
    .then(res => {
      let contentlistTem = this.data.contentlist , obj = null
      if (res.datalist && res.datalist.length > 0) {
        obj = { contentlist: res.datalist, totalCount: res.totalCount }
      } else {
        obj = {
          contentlist: [],
          emptyPageObj: {
            imgUrl: this.data.staticImageUrl+'undefined.png',
            title: '您还没有渲染过呢',
            btnText: '去渲染',
            url: '/pages/house-type/house-type/house-type'
          }
        }
      }
      // res.success ? (res.datalist ? obj = { contentlist: res.datalist, totalCount: res.totalCount } : obj = {
      //   contentlist: [], message: '暂无数据', emptyPageObj}) : obj = { contentlist: [], message: '暂无数据', totalCount: 0 }
      this.setData(Object.assign({ flag: true}, obj))
    })
    .catch((res) => {
      this.setData({ message: res.message, contentlist: [], totalCount: 0 })
    })
  },
  toThreeD(e) {
    console.log(e.currentTarget.dataset.item)
    let item = e.currentTarget.dataset.item, renderTypesStr = item.renderTypesStr, typeList = ['', 'photo', 'seven', 'roam', 'video']
    let routerQueryType = typeList[renderTypesStr], webUrl = null, sevenObj = null
    if (item.isSuccess === 2) {
      if (routerQueryType === 'video') {
        API.getDesignPlanResourceId({
          id: item.businessId,
          remark: routerQueryType
        })
          .then(res => {
            if (res.success) { return routerQueryType === 'video' ? res.datalist[0].id : res.datalist[0].pid } else { return false }
          })
          .then(res => {
            if (res) {
              API.getDesignPlanResource({ thumbId: res })
                .then(res => {
                  res.success ? (routerQueryType === 'video' ? this.toVideo(res.obj.url) : this.enlargeImage([res.obj.url])) : wx.showToast({ title: '打开失败', icon: 'none' })
                })
            }
          })
      } else {
        item.fullHousePlanUUID ? (webUrl = $App.wholeHouseUrl, sevenObj = { uuid: item.fullHousePlanUUID, embedded: 1 }) :
          (webUrl = this.data.sevenUrl, sevenObj = {
            token: wx.getStorageSync('token'),
            platformCode: 'brand2c',
            operationSource: 0,
            planId: item.businessId,
            routerQueryType: routerQueryType,
            customReferer: $App.wxUrl,
            platformNewCode: 'miniProgram'
          })
        for (let key in sevenObj) { webUrl += key + '=' + sevenObj[key] + '&' }
        getApp().data.webUrl = webUrl
        // wx.navigateTo({ url: '/pages/web-720/web-720'})
        $App.myNavigateBack('pages/web-720/web-720')
        console.log(webUrl)
      }
    } else if (item.isSuccess == 0 || item.isSuccess == 1) {
      wx.showToast({ title: '渲染中', icon: 'none' })
    } else {
      wx.showToast({ title: '渲染失败', icon: 'none' })
    }
  },
  enlargeImage(url) { // 查看大图
  console.log(url)
    wx.previewImage({
      current: url[0], // 当前显示图片的http链接
      urls: [url] // 需要预览的图片http链接列表
    })
  },
  toVideo(url) {
    wx.navigateTo({
      url: '/pages/template/video/video?url=' + url
    })
  },
  tohousetype() {
    wx.switchTab({
      url: '/pages/house-type/house-type/house-type',
    })
  }
})