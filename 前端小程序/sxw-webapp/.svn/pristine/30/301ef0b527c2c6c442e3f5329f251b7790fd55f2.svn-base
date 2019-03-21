// pages/search-houseType/search-houseType.js
let API = getApp().API
let $App = getApp()
Page({

  /**
   * 页面的初始数据
   */
  data: {
    pageSize: 20,
    curPage: 1,
    usedCount: -1,
    planCount: '',
    useList: [],
    resourcePath: getApp().resourcePath,
    staticImageUrl: getApp().staticImageUrl,
    num: 1,
    num2: 1,
    id: 'a0',
    scrollLeft: 0,
    planList: '',
    myPlanFlag: true,
    type: '',
    promptFlag: false,
    status: '',
    id2:'',
    isFirstShow: false, // 控制户型引导页显示
    coverPath: '', // 已选方案封面图
    // 埋点数据
    previousPath: '',
    nowPath: ''
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
    $App.sellingPoint(API, '', this.data.nowPath, this.data.previousPath, '选择户型');
    // rzd 埋点 190214 end
    wx.hideShareMenu();
    this.rootInWebH5(options) // 来源于webH5
    this.sellingPoint() // 埋点    
    this.getIsShowPopup()
    new $App.newNav() // 注册快速导航组件
    this.setData({ type: options.type })
    this.getSearchResluts("加载数据");
    this.getPlanList();
    let caseItem = wx.getStorageSync('caseItem');
    this.setData({ coverPath: caseItem.coverPath || '' })
    if (caseItem.spaceType == 13 || this.data.type == "search" || caseItem.spaceFunctionId == 13) {
      this.setData({ myPlanFlag: false })
    }
  },
  rootInWebH5(options) { // 来源于H5
    if (options.source === 'webH5') {
      console.log(options)
      wx.setStorageSync('caseItem', options)
      this.sellingPoint('decorate-house', 'webH5')
    }
  },
  // 关闭户型引导提示
  closeBoot() {
    API.getCloseSearchKnow({id:this.data.id2,chooseHouseType:1}).then(res=>{
      if (res.status) { this.setData({ isFirstShow: false })}
    })
  },
  getIsShowPopup:function(){
    API.getIsShowSearchPopup().then(res=>{
      if(res.obj.chooseHouseType == '0'){
        this.setData({
          isFirstShow:true,
          id2:res.obj.id
        })
      }else{
        this.setData({
          isFirstShow:false
        })
      }
    })
  },
  getSearchResluts: function (message) {
    let that = this
    API.getUserHouseTypeList({ pageSize: that.data.pageSize, curPage: that.data.curPage })
      .then(res => {
        if (res.obj) {
          for (let i = 0; i < res.obj.houselist.length; i++) {
            res.obj.houselist[i].houseTypeStr = res.obj.houselist[i].houseTypeStr.substr(0, 6)
            if (res.obj.houselist[i].totalArea != null) {
              res.obj.houselist[i].totalArea = res.obj.houselist[i].totalArea + 'm²'
            }
          }
          this.setData({
            allCount: res.obj.userAlreadyBoughtHouseCount,
            usedCount: res.obj.userUsedHouseCount
          })
          if (res.obj.houselist.length > 0) {
            this.setData({
              useList: res.obj.houselist
            })
          }
        } else {
          that.setData({ message: res.message })
        }
      })
  },
  getPlanList() {
    API.selectMyPlan()
      .then(res => {
        if (res.datalist) {
          this.setData({
            planList: res.datalist,
            planCount: res.datalist.length
          })
        }
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
  onShareAppMessage: function (res) {
    $App.sellingPoint(API, 'shareHouseType', this.data.nowPath, this.data.previousPath, '选择户型');
    let item = res.target.dataset.item, imgUrl = this.data.resourcePath + item.thumbnailPath
    let shareObj = {
      title: item.houseCommonCode,
      // path: '/pages/home/home?shareType=houseDetail&houseId=' + item.id,
      path: '/pages/home/home?shareType=active&url=' + encodeURIComponent(`/pages/house-type/house-details/house-details?houseId=${item.id}&type=search`),
      imageUrl: imgUrl,
      success: function (res) {
        // 转发成功
      },
      fail: function (res) {
        // 转发失败
      }

    }
    return shareObj;
  },
  toHouseType() {
    this.sellingPoint('ppg_search')
    wx.navigateTo({
      url: '/pages/plan/case-house-type/case-house-type?type=' + this.data.type,
    })
  },
  sellingPoint(event, type) {
    let page = getCurrentPages(), previousPath = page.length > (type == 'webH5' ? 2 : 1) ? page[page.length - (type == 'webH5' ? 3 : 2)].route : '',
      nowPath = page[page.length - (type == 'webH5' ? 2 : 1)].route
    API.sellingPoint({
      uid: wx.getStorageSync('openId'),
      cp: nowPath,
      lp: previousPath,
      e: event ? event : '',
      pt: '选择户型'
    })
  },

  // routerToFitment(e) { // 去装修
  //   let houseItem = e.currentTarget.dataset.item
  //   wx.navigateTo({
  //     url: '/pages/house-type/house-details/house-details?type=plan&houseId=' + houseItem.id
  //   })
  // },
  scroll(e) {
    this.setData({
      num: e.detail.current + 1
    })
  },
  scroll2(e) {
    this.setData({
      num2: e.detail.current + 1
    })
  },
  match(e) {
    $App.sellingPoint(API, 'house-one-key', this.data.nowPath, this.data.previousPath, '选择户型');
    let item = e.currentTarget.dataset.item,
      eType = e.currentTarget.dataset.type,
      caseItem;
    this.sellingPoint('house-one-key')
    caseItem = wx.getStorageSync('caseItem')
    wx.setStorageSync('houseId', item.id || item.houseId)
    if (caseItem.spaceType == 13 || caseItem.spaceFunctionId == 13) {
      let obj = {
        templateId: ''
      }
      wx.setStorageSync('matchData', obj)
      wx.navigateTo({
        url: '/pages/house-type/house-details/house-details?type=' + this.data.type + '&matchState=3&houseId=' + item.id
      })
      return;
    }
    if (this.data.type == "search") {
      wx.navigateTo({
        url: '/pages/house-type/house-details/house-details?type=' + this.data.type + '&houseId=' + item.id
      })
      return;
    }
    if (eType == 'plan') {
      API.matchPlanHouse({ houseId: item.houseId, fullHousePlanId: item.newFullHousePlanId, recommendedPlanId: caseItem.designPlanRecommendId || caseItem.planRecommendedId })
        .then(res => {
          console.log(res, 'res')
          if (res.obj.status == 1) {
            this.setData({
              status: 1
            })
          } else if (res.obj.status == 2) {
            this.setData({
              status: 2
            })
          } else {
            wx.setStorageSync('matchData', res.obj);
            wx.navigateTo({
              url: '/pages/house-type/house-details/house-details?type=' + this.data.type + '&matchState=' + res.obj.status + '&houseId=' + item.houseId + '&templateId=' + res.obj.templateId + '&fullHousePlanId=' + item.newFullHousePlanId + '&mainTaskId=' + item.mainTaskId + '&preRenderSceneId=' + (res.obj.status == 4 ? res.obj.taskStateList[0]['businessId'] : '')
            })
          }
        })
    } else {
      API.matchPlanHouse({ houseId: item.id, recommendedPlanId: caseItem.designPlanRecommendId || caseItem.planRecommendedId })
        .then(res => {
          if (res.obj.status == 1) {
            this.setData({
              status: 1
            })
          } else if (res.obj.status == 2) {
            this.setData({
              status: 2
            })
          } else {
            wx.setStorageSync('matchData', res.obj)
            wx.navigateTo({
              url: '/pages/house-type/house-details/house-details?type=' + this.data.type + '&matchState=' + res.obj.status + '&houseId=' + item.id + '&houseName=' + encodeURIComponent(item.houseCommonCode)
            })
          }
        })
    }
    //砍价活动是否体验720
    
  },
  planImproper(e) {
    let options = e.currentTarget.dataset.options
    if (options == 1) {
      this.setData({
        status: ''
      })
    } else {
      // wx.switchTab({
      //   url: '/pages/plan/house-case/house-case',
      // })
      let houseId = wx.getStorageSync('houseId')
      wx.navigateTo({
        url: '/pages/house-type/house-details/house-details?houseId=' + houseId + '&type=search',
      })
      this.setData({
        status: ''
      })
    }
  },
  previewImage: function (e) {
    var that = this,
      src = e.currentTarget.dataset.src
    wx.previewImage({
      current: src,
      urls: [src]
    })
  },
})