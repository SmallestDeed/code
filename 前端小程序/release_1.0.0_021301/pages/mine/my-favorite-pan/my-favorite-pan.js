// pages/house-case/house-case.js
let API = getApp().API, myForEach = getApp().myForEach;
let $App = getApp()
Page({

  /**
   * 页面的初始数据
   */
  data: {
    imageArray: ['https://homesiteres.zbjimg.com/homesite%2Fres%2F300X250.jpg%2Forigine%2F4ef2b90d-f4a3-4589-abef-ce2104fa65ea', 'https://homesiteres.zbjimg.com/homesite%2Fres%2F300X250.jpg%2Forigine%2F4ef2b90d-f4a3-4589-abef-ce2104fa65ea'],
    spaceList: [],
    areaList: [],
    styleList: [],
    staticImageUrl: getApp().staticImageUrl,
    conditionActive: -1,
    childConditionActive: [0, -1, -1],
    recommendCaseList: [],
    resourcePath: getApp().resourcePath,
    sevenUrl: getApp().sevenUrl,
    pageSize: 10,
    getCaseParams: {
      spaceType: '',
      designPlanStyleId: '',
      spaceArea: ''
    },
    totalCount: 0,
    favoriteRequest: true,
    collectRequest: true,
    emptyPageObj: {}
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    wx.hideShareMenu();
    new $App.newNav() // 注册快速导航组件
    this.sellingPoint()
    this.getConditionMetadata() // 获取方案筛选条件
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
  //埋点
  sellingPoint(event) {
    let page = getCurrentPages(), previousPath = page.length > 1 ? page[page.length - 2].route : '',
      nowPath = page[page.length - 1].route
    API.sellingPoint({
      uid: wx.getStorageSync('openId'),
      cp: nowPath,
      lp: previousPath,
      e: event ? event : '',
      pt: '收藏的方案'
    })
  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function (res) {
    if (res.from === 'menu') {
      return $App.shareAppMessageObj
    }
  },
  enlargeImage(url) { // 查看大图
    wx.previewImage({
      current: url, // 当前显示图片的http链接
      urls: [url] // 需要预览的图片http链接列表
    })
  },
  /*跳转720*/
  go720(e) {
    let type = e.currentTarget.dataset.type,
      item = e.currentTarget.dataset.item,
      routerQueryType = null,
      webUrl = null,
      sevenObj = null
    if (type === 'video') {
      API.getRecommendedVideoId({
        planRecommendedId: item.designPlanRecommendId || item.planRecommendedId,
        remark: type
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
    } else {
      type === '720' ? routerQueryType = 'seven' : routerQueryType = 'roam'
      item.fullHousePlanUUID ? (webUrl = $App.wholeHouseUrl, sevenObj = { uuid: item.fullHousePlanUUID, embedded: 1 }) :
        (webUrl = $App.sevenUrl, sevenObj = {
          token: wx.getStorageSync('token'),
          platformCode: 'brand2c',
          operationSource: 1,
          planId: item.designPlanRecommendId || item.planRecommendedId,
          routerQueryType: routerQueryType,
          customReferer: $App.wxUrl,
          platformNewCode: 'miniProgram'
        })
      for (let key in sevenObj) { webUrl += key + '=' + sevenObj[key] + '&' }
      getApp().data.webUrl = webUrl
      wx.navigateTo({ url: '/pages/web-720/web-720' })
      console.log(webUrl)
    }
  },
  getConditionMetadata() { // 获取方案筛选条件
    API.getConditionMetadata().then(res => {
      if (res.status) {
        let obj = {
          houseType: 0,
          houseName: '全部',
          designPlanAreaList: [],
          designPlanStyleVoList: []
        }
        res.obj.splice(0, 0, obj)
        this.setData({
          spaceList: res.obj,
          areaList: res.obj[0].designPlanAreaList,
          styleList: res.obj[0].designPlanStyleVoList,
          'getCaseParams.spaceType': res.obj[0].houseType
        })
        this.getRecommendCaseList(this.data.getCaseParams)
      } else {
        this.setData({ spaceList: [], areaList: [], styleList: [] })
      }
    })
  },
  switchCondition(e) { // 切换选择框\
    let index = e.currentTarget.dataset.index
    if (index === this.data.conditionActive) {
      index = -1
    }
    this.setData({
      conditionActive: index
    })
  },
  chooseCondition(e) { // 选择筛选条件
    let indexP = e.currentTarget.dataset.indexp, indexC = e.currentTarget.dataset.indexc
    if (indexP === 0) {
      // this.data.childConditionActive[0] = 0
      this.data.childConditionActive[1] = -1
      this.data.childConditionActive[2] = -1
      this.setData({
        areaList: this.data.spaceList[indexC].designPlanAreaList,
        styleList: this.data.spaceList[indexC].designPlanStyleVoList,
        childConditionActive: this.data.childConditionActive
      })
    }
    this.data.childConditionActive[indexP] = indexC
    this.setData({
      childConditionActive: this.data.childConditionActive,
      'getCaseParams.spaceType': this.data.spaceList[this.data.childConditionActive[0]].houseType,
      'getCaseParams.designPlanStyleId': this.data.childConditionActive[2] == -1 ? '' : this.data.styleList[this.data.childConditionActive[2]].styleCode,
      'getCaseParams.spaceArea': this.data.childConditionActive[1] == -1 ? '' : this.data.areaList[this.data.childConditionActive[1]].areaId
    })
    this.getRecommendCaseList(this.data.getCaseParams)
  },
  closeCaseCondition() {
    this.setData({
      conditionActive: -1
    })
  },
  getRecommendCaseList(obj) { // 获取方案列表

    API.getDesignplanfavorite({
      "recommendationPlanSearchVo": {
        "houseType": obj.spaceType || '',
        "spaceArea": obj.spaceArea || '',
        "designStyleId": obj.designPlanStyleId || '',
      },
      "pageVo": {
        "start": 0,
        "pageSize": this.data.pageSize
      }
    })
      .then(res => {
        if (res.datalist) {
          this.setData({ recommendCaseList: res.datalist, totalCount: res.totalCount })
        } else {
          this.setData({
            recommendCaseList: [],
            totalCount: 0,
            emptyPageObj: {
              imgUrl: this.data.staticImageUrl+'undefined.png',
              title: '您还没有收藏方案哦',
              btnText: '去逛一逛',
              url: '/pages/plan/house-case/house-case',
              switchTab: true
            }
          })
        }
      })
    // let url = `/designplanfavorite/list`
    // API.getDesignplanfavorite({
    //   curPage: 1,
    //   pageSize: this.data.pageSize,
    //   spaceType: obj.spaceType || '',
    //   designPlanStyleId: obj.designPlanStyleId || '',
    //   spaceArea: obj.spaceArea || '',
    //   displayType: 'decorate',
    //   isSortByReleaseTime: 0,
    //   platformCode: 'selectDecoration'
    // })
    // .then(res => {
    //   console.log(res);
    //   if (res.status) {
    //     if (res.obj) {
    //       this.setData({ recommendCaseList: res.obj, totalCount: res.totalCount })
    //     } else { 
    //       this.setData({ 
    //         recommendCaseList: [],
    //          totalCount: 0, 
    //          emptyPageObj: {
    //            imgUrl: 'undefined.png',
    //            title: '您还没有收藏方案哦',
    //            btnText: '去逛一逛',
    //            url: '/pages/plan/house-case/house-case',
    //            switchTab: true
    //          }
    //       })
    //     }
    //   } else {
    //     this.setData({ 
    //       recommendCaseList: [], 
    //       totalCount: 0,
    //       emptyPageObj: {
    //         imgUrl: 'undefined.png',
    //         title: res.message,
    //       }
    //       })
    //   }
    // })
  },
  onReachBottom() { // 底部
    if (this.data.pageSize >= this.data.totalCount) {
      return
    } else {
      this.setData({
        pageSize: this.data.pageSize + 10
      })
      this.getRecommendCaseList(this.data.getCaseParams)
    }
  },
  toVideo(url) {
    wx.navigateTo({
      url: '/pages/template/video/video?url=' + url
    })
  },
  putInMyhouse(e) { // 装进我家
    // this.renderTypeShow() // 显示组件
    let item = e.currentTarget.dataset.item
    wx.setStorageSync('caseItem', item)
    wx.navigateTo({
      url: '/pages/plan/case-house-type/case-house-type'
    })
  },
  collectOrLikeCase(obj) {
    console.log("123", this.data.recommendCaseList[obj.index].spaceType ? (this.data.recommendCaseList[obj.index].spaceType == 13 ? 2 : 1) : (this.data.recommendCaseList[obj.index].spaceFunctionId == 13 ? 2 : 1))
    let that = this, status = null, title = null
    if (this.data[obj.flag] == true) {
      this.setData({ [obj.flag]: false })
      this.data.recommendCaseList[obj.index][obj.status] ? (status = 0, title = '取消' + obj.title) : (status = 1, title = obj.title)
      API[obj.api]({ status: status, [obj.param]: this.data.recommendCaseList[obj.index].designPlanRecommendId || this.data.recommendCaseList[obj.index].planRecommendedId, designPlanType: this.data.recommendCaseList[obj.index].planHouseType || (this.data.recommendCaseList[obj.index].spaceType ? (this.data.recommendCaseList[obj.index].spaceType == 13 ? 2 : 1) : (this.data.recommendCaseList[obj.index].spaceFunctionId == 13 ? 2 : 1)) })
        .then(res => {
          if (res.success) {
            status == 0 ? this.data.recommendCaseList[obj.index][obj.num] -= 1 : this.data.recommendCaseList[obj.index][obj.num] += 1
            if (this.data.recommendCaseList[obj.index][obj.num] < 0) {
              this.data.recommendCaseList[obj.index][obj.num] = 0
            }

            this.data.recommendCaseList[obj.index][obj.status] = status
            this.setData({ recommendCaseList: this.data.recommendCaseList })
            wx.showToast({ title: title + '成功' })
          } else {
            wx.showToast({ title: title + '失败', icon: 'none' })
          }
          setTimeout(function () { that.setData({ [obj.flag]: true }) }, 500)
        })
    }
  },
  collectCase(e) { // 方案收藏
    let index = e.currentTarget.dataset.index
    this.collectOrLikeCase({
      title: '收藏',
      flag: 'collectRequest',
      num: 'collectNum',
      status: 'isFavorite',
      api: 'collectCase',
      param: 'recommendId',
      index
    })
  },
  likeCase(e) { // 方案点赞
    let index = e.currentTarget.dataset.index
    this.collectOrLikeCase({
      title: '点赞',
      flag: 'favoriteRequest',
      num: 'likeNum',
      status: 'isLike',
      api: 'likeCase',
      param: 'designId',
      index
    })
  },
  goRenderBtn() {
    wx.switchTab({
      url: '/pages/plan/house-case/house-case',
    })
  }
})
