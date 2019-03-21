// pages/plan/selection-scheme/selections-scheme.js
let API = getApp().API,
  myForEach = getApp().myForEach;
let $App = getApp()
Page({

  /**
   * 页面的初始数据
   */
  data: {
    type: 'myPlan',
    conditionActive: -1,
    childConditionActive: [0, -1, -1],
    staticImage: getApp().staticImageUrl,
    resourcePath: getApp().resourcePath,
    getCaseParams: {
      spaceType: '',
      designPlanStyleId: '',
      spaceArea: ''
    },
    pageSize: 10,
    totalCount: 0,
    favoriteRequest: true,
    collectRequest: true,
    emptyPageObj: {},
    recommendCaseList: [],
    spaceList: [],
    areaList: [],
    styleList: [],
    isCheck: -1,
    planList: [],
    planHouseType: '',
    spaceFuctionId: '',
    planType: '',
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function(options) {
    new $App.newNav() // 注册快速导航组件
    
    this.getPlanList()
  },

  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function() {

  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function() {},

  /**
   * 生命周期函数--监听页面隐藏
   */
  onHide: function() {

  },

  /**
   * 生命周期函数--监听页面卸载
   */
  onUnload: function() {

  },

  /**
   * 页面相关事件处理函数--监听用户下拉动作
   */
  onPullDownRefresh: function() {

  },

  /**
   * 页面上拉触底事件的处理函数
   */
  // onReachBottom: function() {
  //  this.setData({
  //    pageSize:this.data.pageSize+=10
  //  })
  //   console.log("987")
  //   if (this.data.type =='myPlan'){
  //     this.getPlanList();
  //     console.log("987")
  //   }else{
  //     this.getRecommendCaseList();
  //   }
  // },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function(res) {
    if (res.from === 'menu') {
      return $App.shareAppMessageObj
    }
  },
  // 顶部切换
  changeType(e) {
    this.setData({
      type: e.currentTarget.dataset.type
    })
    if(this.data.type == 'myPlan'){
      this.getPlanList()
    }else{
      this.getRecommendCaseList(this.data.getCaseParams)
      this.getConditionMetadata() // 获取方案筛选条件
    }
  },
  // 收藏tab切换
  switchCondition(e) {
    let index = e.currentTarget.dataset.index
    if (index === this.data.conditionActive) {
      index = -1
    }
    this.setData({
      conditionActive: index
    })
  },
  closeCaseCondition() {
    this.setData({
      conditionActive: -1
    })
  },
  // 点击筛选条件
  chooseCondition(e) {
    let indexP = e.currentTarget.dataset.indexp,
      indexC = e.currentTarget.dataset.indexc
    if (indexP === 0) {
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
    // if (indexP == 0) {
    //   this.setData({
    //     houseType: this.data.spaceList[indexC].houseType || '',
    //   })
    // } else if (indexP == 1) {
    //   this.setData({
    //     areaValue: this.data.areaList[indexC].areaId || ''
    //   })
    // } else if (indexP == 2) {
    //   this.setData({
    //     designStyleId: this.data.styleList[indexC].styleCode || '',
    //   })
    // }
    this.getRecommendCaseList(this.data.getCaseParams)
  },
  // 获取方案筛选条件
  getConditionMetadata() {
    API.getConditionMetadata()
      .then(res => {
        if (res.status) {
          this.setData({
            spaceList: res.obj,
            areaList: res.obj[0].designPlanAreaList,
            styleList: res.obj[0].designPlanStyleVoList,
            'getCaseParams.spaceType': res.obj[0].houseType
          })
          this.getRecommendCaseList(this.data.getCaseParams)
        } else {
          this.setData({
            spaceList: [],
            areaList: [],
            styleList: []
          })
        }
      })
  },
  // 获取收藏方案列表
  getRecommendCaseList(obj) {
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
          this.setData({
            planList: res.datalist,
            totalCount: res.totalCount
          })
        } else {
          this.setData({
            planList: [],
            totalCount: 0,
            emptyPageObj: {
              imgUrl: '/static/image/undefined.png',
              title: '您还没有收藏方案哦',
              // btnText: '',
              url: '/pages/plan/house-case/house-case',
              // switchTab: true
            }
          })
        }
      })
  },
  // 上拉加载
  onReachBottom() {
    
      this.setData({
        pageSize: this.data.pageSize + 10
      })

    this.data.type == 'myPlan' ? this.getPlanList():this.getRecommendCaseList(this.data.getCaseParams)
  },
  switchCheck(e) {
    let index = e.currentTarget.dataset.index
    if (index === this.data.isCheck) {
      index = -1
    }
    this.setData({
      isCheck: index
    })
  },
  confirmBtn() {
    let planType,
      flag=true,
      pages = getCurrentPages(), //当前页面 
      prevPage = pages[pages.length - 2]; //上一页面
    if (this.data.type == 'myPlan') {
      this.data.planList[this.data.isCheck].planHouseType == 1 ? planType = 3 : planType = 4
      prevPage.setData({
        addPlanId: this.data.planList[this.data.isCheck].newFullHousePlanId || this.data.planList[this.data.isCheck].businessId,
        addPlanImg: this.data.planList[this.data.isCheck].planPicPath,
        planType: planType
      })
    } else if (this.data.type == 'collectPlan') {
      this.data.planList[this.data.isCheck].spaceFuctionId == 13 ? planType = 2 : planType = 1
      prevPage.setData({
        addPlanId: this.data.planList[this.data.isCheck].planRecommendedId,
        addPlanImg: this.data.planList[this.data.isCheck].coverPath,
        planType: planType
      })
    }
    wx.navigateBack({
      delta: 1
    })
  },
  getPlanList() {
    API.getMyDegianPlan({
      pageSize: this.data.pageSize,
      curPage: 1
    })
      .then(res => {
        console.log(res)
        if (res.datalist) {
          let list = [];
          for(let i=0;i<res.datalist.length;i++){
            if (res.datalist[i].isSuccess==2){
              list=list.concat(res.datalist[i])
            }
          }
          console.log(list,'1111')
          this.setData({
            planList: list,
            planCount: res.datalist.length
          })
        } else {
          this.setData({
            planList: [],
            totalCount: 0,
            emptyPageObj: {
              imgUrl: '/static/image/undefined.png',
              title: '您还没有用过我们的产品呢',
              btnText: '去体验',
              url: '/pages/plan/house-case/house-case',
              switchTab: true
            }
          })
        }
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
          if (res.success) {
            return res.datalist[0].id
          } else {
            return false
          }
        })
        .then(res => {
          if (res) {
            API.getRecommendedVideoMessage({
                thumbId: res
              })
              .then(res => {
                res.success ? this.toVideo(res.obj.url) : wx.showToast({
                  title: '打开失败',
                  icon: 'none'
                })
              })
          }
        })
    } else {
      type === '720' ? routerQueryType = 'seven' : routerQueryType = 'roam'
      item.fullHousePlanUUID ? (webUrl = $App.wholeHouseUrl, sevenObj = {
          uuid: item.fullHousePlanUUID,
          embedded: 1
        }) :
        (webUrl = $App.sevenUrl, sevenObj = {
          token: wx.getStorageSync('token'),
          platformCode: 'brand2c',
          operationSource: 1,
          planId: item.designPlanRecommendId || item.planRecommendedId,
          routerQueryType: routerQueryType,
          customReferer: $App.wxUrl,
          platformNewCode: 'miniProgram'
        })
      for (let key in sevenObj) {
        webUrl += key + '=' + sevenObj[key] + '&'
      }
      getApp().data.webUrl = webUrl
      wx.navigateTo({
        url: '/pages/web-720/web-720'
      })
    }
  },
  toThreeD(e) {
    let routerQueryType = e.currentTarget.dataset.type,
      item = e.currentTarget.dataset.item,
      webUrl = null,
      sevenObj = null
    if (item.planHouseType == 3 && !item.vrResourceUuid) {
      wx.showModal({
        title: '提示',
        content: '该方案正在渲染中，请稍后~',
        confirmText: '确定',
        cancelText: '取消',
        cancelColor: '#999999',
        confirmColor: '#ff6419'
      })
      return;
    }
    item.newFullHousePlanId ? (webUrl = $App.wholeHouse3dUrl, sevenObj = {
        uuid: item.vrResourceUuid,
        token: wx.getStorageSync('token'),
        platformCode: 'brand2c',
        operationSource: 0,
        planId: item.newFullHousePlanId,
        routerQueryType: 'seven',
        customReferer: $App.wxUrl,
        platformNewCode: 'miniProgram',
        // formId: formId,
        isRender: 0,
        isTask: 1,
        houseId: item.houseId || 0,
        taskType: item.planRenderType
      }) :
      (webUrl = this.data.sevenUrl, sevenObj = {
        token: wx.getStorageSync('token'),
        platformCode: 'brand2c',
        operationSource: 0,
        planId: item.businessId,
        routerQueryType: routerQueryType,
        customReferer: $App.wxUrl,
        platformNewCode: 'miniProgram',
        isTask: 1,
        taskType: item.planRenderType,
        houseId: item.houseId || 0
      })
    for (let key in sevenObj) {
      webUrl += key + '=' + sevenObj[key] + '&'
    }
    getApp().data.webUrl = webUrl
    wx.navigateTo({
      url: '/pages/web-720/web-720'
    })
  },
})