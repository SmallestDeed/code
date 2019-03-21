let WxParse = require('../../utils/wxParse/wxParse.js'), API = getApp().API, $App = getApp(), myForEach = getApp().myForEach
Page({

  /**
   * 页面的初始数据
   */
  data: {
    caseDetails: {},
    resourcePath: getApp().resourcePath,
    sevenUrl: getApp().sevenUrl,
    favoriteRequest: true,
    collectRequest: true,
    decoratePriceBox: false,
    caseListheight: '',
    caseListOverflow: 'none',
    decoratePriceList: [],
    isWholeHouse: 1 // 2表示全屋
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    wx.hideShareMenu();
    this.setData({ isWholeHouse: options.type})
    this.getCaseDetails(options.id, options.type == 2?1:0) // 获取方案详情
  },
  getCaseDetails(id, type) {
    API.getCaseDetails({
      id: id,
      type
    })
    .then(res => {
      res.status ? this.setData({ caseDetails: res.obj[0]}) : this.setData({ caseDetails: {}})
      res.status ?WxParse.wxParse('article', 'html', res.obj[0].desc || '', this, 5): null      
    })
  },
  formToThreeD(e) {
    let item = e.detail.target.dataset.item, routerQueryType = e.detail.target.dataset.type,
      formId = e.detail.formId, webUrl = null, sevenObj = null
    item.fullHousePlanUUID ? (webUrl = $App.wholeHouseUrl, sevenObj = { uuid: item.fullHousePlanUUID, embedded: 1 }) :
      (webUrl = this.data.sevenUrl, sevenObj = {
        token: wx.getStorageSync('token'),
        platformCode: 'brand2c',
        operationSource: 1,
        planId: item.designPlanRecommendId || item.planRecommendedId,
        routerQueryType: routerQueryType,
        customReferer: $App.wxUrl,
        platformNewCode: 'miniProgram',
        formId: formId
      })
    for (let key in sevenObj) { webUrl += key + '=' + sevenObj[key] + '&' }
    getApp().data.webUrl = webUrl
    wx.navigateTo({ url: '/pages/web-720/web-720' })
    console.log(webUrl)
  },
  toThreeD(e) {
    let webUrl = null, sevenObj = {}
    API.getRecommendedVideoId({
      planRecommendedId: e.currentTarget.dataset.item.designPlanRecommendId,
      remark: e.currentTarget.dataset.type
    })
      .then(res => {
        if (res.success) { return res.datalist[0].id } else { return false }
      })
      .then(res => {
        if (res) {
          API.getRecommendedVideoMessage({ thumbId: res })
            .then(res => {
              res.success ? this.toVideo(res.obj.url) : wx.showToast({ title: '打开失败', icon: 'none' })
            })
        }
      })
  },
  toVideo(url) {
    wx.navigateTo({  url: '/pages/template/video/video?url=' + url })
  },
  collectCase() { // 方案收藏
    this.collectOrLikeCase({
      title: '收藏',
      flag: 'collectRequest',
      num: 'collectNum',
      status: 'isFavorite',
      api: 'collectCase',
      param: 'recommendId'
    })
  },
  likeCase() { // 方案点赞
    this.collectOrLikeCase({
      title: '点赞',
      flag: 'favoriteRequest',
      num: 'likeNum',
      status: 'isLike',
      api: 'likeCase',
      param: 'designId'      
    })
  },
  collectOrLikeCase(obj) {
    let that = this, status = null, title = null
    if (this.data[obj.flag] == true) {
      this.setData({ [obj.flag]: false })
      this.data.caseDetails[obj.status] ? (status = 0, title = '取消' + obj.title) : (status = 1, title = obj.title)
      API[obj.api]({ 
        status: status, 
        [obj.param]: this.data.caseDetails.designPlanRecommendId,
        designPlanType: this.data.isWholeHouse
       })
      .then(res => {
        if (res.success) {
          status == 0 ? this.data.caseDetails[obj.num] -= 1 : this.data.caseDetails[obj.num] += 1
          this.data.caseDetails[obj.status] = status
          this.setData({ caseDetails: this.data.caseDetails })
          wx.showToast({ title: title + '成功' })
        } else {
          wx.showToast({ title: title + '失败', icon: 'none' })
        }
        setTimeout(function () { that.setData({ [obj.flag]: true }) }, 500)
      })
    }
  },
  putInMyhouse() { // 装进我家
    // this.renderTypeShow() // 显示组件  
    let item = this.data.caseDetails
    wx.setStorageSync('caseItem', item)
    wx.navigateTo({
      url: '/pages/plan/case-house-type/case-house-type?type=plan'
    })
  },
  toShopDetail(e) {
    if (!this.data.caseDetails.shopId && this.data.caseDetails.shopId != 0) {
      wx.showToast({
        title: '暂无门店',
        icon: 'none'
      })
      return
    }
    wx.navigateTo({
      url: '/pages/decoration/companyDetail/companyDetail?id=' + this.data.caseDetails.shopId,
    })
  },
  showDecoratePriceBox(e) { // 显示装修报价弹框
    let item = e.currentTarget.dataset.item, height = wx.getSystemInfoSync().windowHeight
    if (item) {
      myForEach(item, (value) => {
        switch (value.decorateTypeName) {
          case "半包": value.text = '辅材+人工费+管理费'; break;
          case "清水": value.text = '人工费+管理费'; break;
          case "全包": value.text = '主材+辅材+人工费+管理费'; break;
          case "包软装": value.text = '主材+辅材+人工费+管理费+部分软装'; break;
        }
      })
      this.setData({ decoratePriceList: item, decoratePriceBox: true, caseListheight: height + 'px', caseListOverflow: 'hidden' })
    } else {
      this.setData({ decoratePriceBox: false, caseListheight: '', caseListOverflow: 'none' })
    }
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
  
  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function () {
  
  }
})