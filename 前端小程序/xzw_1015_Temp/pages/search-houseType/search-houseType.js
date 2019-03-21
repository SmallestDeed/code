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
    num: 1,
    num2: 1,
    id: 'a0',
    scrollLeft: 0,
    planList: '',
    myPlanFlag: true,
    type: '',
    promptFlag: false,
    status: ''
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    new $App.newNav() // 注册快速导航组件
    this.setData({
      type: options.type
    })
    console.log()
    this.getSearchResluts("加载数据");
    this.getPlanList();
    let caseItem = wx.getStorageSync('caseItem');

    if (caseItem.spaceType == 13 || this.data.type == "search" || caseItem.spaceFunctionId == 13) {

      this.setData({
        myPlanFlag: false
      })
    }
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
          console.log(res)
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
    let item = res.target.dataset.item, imgUrl = this.data.resourcePath + item.thumbnailPath
    console.log(imgUrl)
    let shareObj = {
      title: item.houseCommonCode,
      path: '/pages/home/home?shareType=houseDetail&houseId=' + item.id,
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
    wx.navigateTo({
      url: '/pages/plan/case-house-type/case-house-type?type=' + this.data.type,
    })
  },
  // routerToFitment(e) { // 去装修
  //   let houseItem = e.currentTarget.dataset.item
  //   wx.navigateTo({
  //     url: '/pages/house-type/house-details/house-details?type=plan&houseId=' + houseItem.id
  //   })
  // },
  scroll(e) {
    console.log(e.detail.current)
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

    let item = e.currentTarget.dataset.item,
      eType = e.currentTarget.dataset.type,
      caseItem;
      console.log('123',item)
    caseItem = wx.getStorageSync('caseItem')
    wx.setStorageSync('houseId', item.id)
    if (caseItem.spaceType == 13 || caseItem.spaceFunctionId == 13) {
      console.log("1")
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
      console.log("2")
      wx.navigateTo({
        url: '/pages/house-type/house-details/house-details?type=' + this.data.type + '&houseId=' + item.id
      })
      return;
    }
    if (eType == 'plan') {
      console.log("3")
      console.log(item)
      API.matchPlanHouse({ houseId: item.houseId, fullHousePlanId: item.newFullHousePlanId, recommendedPlanId: caseItem.designPlanRecommendId || caseItem.planRecommendedId })
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
            wx.setStorageSync('matchData', res.obj);
            wx.navigateTo({
              url: '/pages/house-type/house-details/house-details?type=' + this.data.type + '&matchState=' + res.obj.status + '&houseId=' + item.houseId + '&templateId=' + res.obj.templateId + '&fullHousePlanId=' + item.newFullHousePlanId + '&mainTaskId=' + item.mainTaskId + '&preRenderSceneId=' + (res.obj.status == 4 ? res.obj.taskStateList[0]['businessId'] : '')
            })
          }
        })
    } else {
      console.log("4")
      console.log("222222222222")
      console.log(item)
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
              url: '/pages/house-type/house-details/house-details?type=' + this.data.type + '&matchState=' + res.obj.status + '&houseId=' + item.id
            })
          }
        })
    }

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