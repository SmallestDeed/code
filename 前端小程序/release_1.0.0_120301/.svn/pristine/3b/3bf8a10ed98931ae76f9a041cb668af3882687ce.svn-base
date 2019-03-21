//taskType 0装进我家  1产品替换




let API = getApp().API
let $App = getApp()
Page({
  /**
   * 页面的初始数据
   */
  data: {
    resourcePath: getApp().resourcePath,
    staticImageUrl: getApp().staticImageUrl,
    sevenUrl: getApp().sevenUrl,
    curPage: 1,
    pageSize: 10,
    spaceFunctionId: "",
    houseId: "",
    isSort: 0,
    contentlist: [],
    listIndex: -1,
    isShowRechristen: false,
    message: "加载中",
    deleteFlag: false,
    deleteIndex: '',
    getContentListFlag: true,
    emptyPageObj: {},
    renamePlanType: '',
    renamePlanId: '',
    rename: '',
    reTaskId: '',
    reRenderState: '',
    loopFlag: true,
    planType: '',
    isPublish: '发布消息'
  },
  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    new $App.newNav() // 注册快速导航组件
    this.sellingPoint()
    // 用户数据
    this.setData({
      loopFlag: true
    })
    // this.getSearchResluts('加载数据');
  },
  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function () { },
  /**
   * 生命周期函数--监听页面显示
   */

  //埋点
  sellingPoint(event) {
    let page = getCurrentPages(), previousPath = page.length > 1 ? page[page.length - 2].route : '',
      nowPath = page[page.length - 1].route
    API.sellingPoint({
      uid: wx.getStorageSync('openId'),
      cp: nowPath,
      lp: previousPath,
      e: event ? event : '',
      pt: '我的方案'
    })
  },
  query: function () {
    if (this.data.loopFlag) {
      let _this = this;
      _this.getSearchResluts('加载数据')
      setTimeout(() => {
        _this.query();
      }, 45000);
      return;
    }
  },
  onShow: function () {
    this.query();
  },
  /**
   * 生命周期函数--监听页面隐藏
   */
  onHide: function () {
    this.setData({
      loopFlag: false
    })
  },
  /**
   * 生命周期函数--监听页面卸载
   */
  onUnload: function () {
    this.setData({
      loopFlag: false
    })
  },
  /**
   * 页面相关事件处理函数--监听用户下拉动作
   */
  onPullDownRefresh: function () { },
  /**
   * 页面上拉触底事件的处理函数
   */

  onReachBottom: function () {
    if (this.data.getContentListFlag) {
      if (this.data.totalCount > this.data.contentlist.length) {
        this.data.pageSize += 10
        this.getSearchResluts()
      }
    }
  },
  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function (res) {
    let that = this;
    if (res.from === 'menu') {
      return $App.shareAppMessageObj
    }
    if (res.from === 'button') {
      console.log('999')
      let item = res.target.dataset.item, routerQueryType = res.target.dataset.type, webUrl = null, sevenObj = null
      let imgurl;
      item.planPicPath ? imgurl = this.data.resourcePath + item.planPicPath : imgurl = '/static/image/design_def.png'
      // if (item.planHouseType == 3 && !item.vrResourceUuid) {
      //   wx.showModal({
      //     title: '提示',
      //     content: '该方案正在渲染中，请稍后分享~',
      //     confirmText: '确定',
      //     cancelText: '取消',
      //     cancelColor: '#999999',
      //     confirmColor: '#ff6419'
      //   })
      //   return;
      // }
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
      for (let key in sevenObj) { webUrl += key + '=' + sevenObj[key] + '&' }
      webUrl = encodeURIComponent(webUrl)
      console.log('123', webUrl)
      let shareObj = {
        title: item.designName,
        path: '/pages/home/home?shareType=720&url=' + webUrl,
        imageUrl: imgurl,
        success: function (res) {
          // 转发成功
        },
        fail: function (res) {
          // 转发失败
        }
      }
      return shareObj;
    }
  },
  goRechristen(e) {
    this.cancel();
    let item = e.currentTarget.dataset.item
    let pId, taskId;
    { { item.planHouseType == 1 ? pId = item.businessId : pId = item.newFullHousePlanId } }
    { { item.planHouseType == 1 ? taskId = item.taskId : taskId = item.mainTaskId } }
    { { pId == null ? pId = 0 : pId = pId } }
    this.setData({
      listIndex: -1,
      isShowRechristen: true,
      renamePlanType: item.planHouseType,
      renamePlanId: pId,
      rename: '',
      reTaskId: taskId,
      reRenderState: item.renderState
    });
  },
  cancel() {
    this.setData({
      isShowRechristen: false
    });
  },
  toLanding(e) {
    let curr = e.currentTarget.dataset
    let id = curr.type == 1 ? curr.id : curr.iid
    let tys = curr.type == 1 ? 0 : 1;
    let urls = 'https://720.sanduspace.com/static/sxw/landingpage/mobildecoration.html';
    let url = '/pages/landing/landing?info=1&id=' + id + '&type=' + tys + '&url=' + urls;
    wx.navigateTo({
      url: url,
    })
    this.sellingPoint({ name: 'free-offer' })
  },
  showOperateBox(e) {
    let index,
      item = e.currentTarget.dataset.item
    console.log(item)
    if (e.target.dataset.index == this.data.listIndex) {
      index = -1;
    } else {
      index = e.target.dataset.index;
    }
    this.setData({
      listIndex: index
    });
    if (item.supplyDemandId == null || item.supplyDemandId == 0) {
      this.setData({ isPublish: '发布消息' })
    } else {
      this.setData({ isPublish: '回复消息' })
    }
  },
  getSearchResluts: function (message) {
    this.data.getContentListFlag = false
    API.getMyDegianPlan({
      pageSize: this.data.pageSize,
      curPage: this.data.curPage
    })
      .then(res => {
        //designTemplateId!=-1 && houseId存在可以继续装修
        this.data.getContentListFlag = true
        if (res.datalist && res.datalist.length > 0) {
          this.setData({ contentlist: res.datalist, totalCount: res.obj })
        } else {
          this.setData({
            contentlist: [],
            totalCount: 0,
            emptyPageObj: {
              imgUrl: '/static/image/undefined.png',
              title: '您还没有用过我们的产品呢',
              btnText: '去体验',
              url: '/pages/plan/house-case/house-case',
              switchTab: 'switchTab'
            }
          })
        }
      })
  },
  cancelFlag(e) {
    let key = e.currentTarget.dataset.key
    if (key == -1) {
      return;
    } else {
      this.setData({
        listIndex: -1
      })
    }
  },
  toThreeD(e) {
    let routerQueryType = e.currentTarget.dataset.type, item = e.currentTarget.dataset.item, webUrl = null, sevenObj = null
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
      taskType: item.planRenderType,
      mainTaskId: item.mainTaskId
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
    for (let key in sevenObj) { webUrl += key + '=' + sevenObj[key] + '&' }
    getApp().data.webUrl = webUrl
    console.log(webUrl)
    wx.navigateTo({ url: '/pages/web-720/web-720' })
    this.sellingPoint({ name: 'toThreeD-btn' })
    // if (routerQueryType === 'video') {
    //   API.getDesignPlanResourceId({
    //     id: item.cpId,
    //     remark: routerQueryType
    //   })
    //     .then(res => {
    //       if (res.success) { return res.datalist[0].id } else { return false }
    //     })
    //     .then(res => {
    //       if (res) {
    //         API.getDesignPlanResource({ thumbId: res })
    //           .then(res => {
    //             res.success ? this.toVideo(res.obj.url) : wx.showToast({ title: '打开失败', icon: 'none' })
    //           })
    //       }
    //     })
    // } else {
    // }
  },
  enlargeImage(url) { // 查看大图
    wx.previewImage({
      current: url, // 当前显示图片的http链接
      urls: [url] // 需要预览的图片http链接列表
    })
  },
  toVideo(url) {
    wx.navigateTo({
      url: '/pages/template/video/video?url=' + url
    })
  },
  deletebtn(e) {//删除按钮
    var that = this;
    that.setData({
      deleteFlag: !that.data.deleteFlag,
      deleteIndex: e.currentTarget.dataset.index
    })

  },
  delete(e) {//删除装修
    let that = this
    let item = e.currentTarget.dataset.item
    let planId, taskId, renderState, planType, params;
    item.planHouseType == 1 ? taskId = item.taskId : taskId = item.mainTaskId
    { { item.planHouseType == 1 ? planId = item.businessId : planId = item.newFullHousePlanId } }
    { { planId == null ? planId = 0 : planId = planId } }
    taskId = taskId;
    renderState = item.renderState;
    planType = item.planHouseType;
    params = { 'planId': planId, 'taskId': taskId, 'renderState': renderState, 'planType': planType }
    API.deleteMyDesignPlanAndTask(params)
      .then(res => {
        this.setData({ deleteFlag: false, deleteIndex: '' })
        if (res.obj) {
          wx.showToast({
            title: '删除成功',
            duration: 2000,
            complete: function () {
              setTimeout(function () {
                that.getSearchResluts('加载数据');
                that.setData({ listIndex: -1 });
              }, 2000) //延迟时间
            }
          })
        } else {
          wx.showToast({
            title: '删除失败',
            duration: 2000,
            complete: function () {
              setTimeout(function () { that.getSearchResluts('加载数据') }, 2000) //延迟时间
            }
          })
        }
      })
  },
  tohousetype() { // 去装修
    wx.navigateTo({
      url: '/pages/house-type/house-type/house-type',
    })
  },
  getRename(e) {
    this.setData({
      rename: e.detail.value
    })
  },
  rename() {
    if (this.data.rename == '') {
      wx.showToast({
        title: '请输入方案名称',
        duration: 2000
      })
      return;
    }
    var that = this
    API.planReanme({
      planName: this.data.rename,
      planType: this.data.renamePlanType,
      planId: this.data.renamePlanId,
      taskId: this.data.reTaskId,
      renderState: this.data.reRenderState
    })
      .then(res => {
        if (res.obj) {
          wx.showToast({
            title: '重命名成功',
            duration: 2000,
            complete: function () {
              setTimeout(function () {
                that.setData({ isShowRechristen: false });
                that.getSearchResluts('加载数据');
              }, 2000) //延迟时间
            }
          })
        } else {
          wx.showToast({
            title: '重命名失败',
            duration: 2000,
            complete: function () {
            }
          })
        }
      })
  },
  deletePlan(e) {
    let id = e.currentTarget.dataset.id
    planType = e.currentTarget.dataset.planType
    API.deleteMyPlan({
      planType: planType,
      planId: id
    })
      .then(res => {
      })

  },
  toHouseDeatil(e) {
    let item = e.currentTarget.dataset.item
    wx.navigateTo({
      url: '/pages/house-type/house-details/house-details?type=myPlan&houseId=' + item.houseId + '&templateId=' + item.templateId + '&fullHousePlanId=' + item.newFullHousePlanId + '&mainTaskId=' + item.mainTaskId
    })
    this.sellingPoint({ name: 'continue-finish' })
    console.log(item.mainTaskId, 'swq')
  },
  sharePlan(item, type) {
    let webUrl = null, sevenObj = null
    if (item.planHouseType == 3 && !item.vrResourceUuid) {
      wx.showModal({
        title: '提示',
        content: '该方案正在渲染中，请稍后分享~',
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
    for (let key in sevenObj) { webUrl += key + '=' + sevenObj[key] + '&' }
    getApp().data.webUrl = webUrl
    console.log('123', item)
    let shareObj = {
      title: item.designName,
      path: '/pages/home/home?shareType=720&url=' + webUrl,
      success: function (res) {
        // 转发成功
      },
      fail: function (res) {
        // 转发失败
      }
    }
    return shareObj;
  },
  goPublishNews(e) {
    
    let item = e.currentTarget.dataset.item
    console.log(item)
    if (item.isSuccess == 0 || item.isSuccess==1){
      wx.showToast({
        icon:'none',
        title: '请等待方案渲染完成',
      })
      return;
    }
    if (item.isSuccess == 3) {
      wx.showToast({
        icon: 'none',
        title: '方案渲染失败,请重新渲染',
      })
      return;
    }
    let type = 'plan'
    item.planHouseType == 1 ? (this.setData({
      planType: 3
    })) : (this.setData({
      planType: 4
    }))
    if (!item.planPicPath) {
      item.planPicPath = ''
    }
    let planId;
    item.planHouseType == 1 ? planId = item.businessId : planId = item.newFullHousePlanId
    if (item.supplyDemandId == null || item.supplyDemandId == 0) {
      wx.navigateTo({
        url: '/pages/publishMessage/publishMessage?' + 'imgUrl=' + item.planPicPath + '&id=' + planId + '&planType=' + this.data.planType + '&type=' + type
      })
    } else {
      let planId;
      item.newFullHousePlanId ? planId = item.newFullHousePlanId : planId = item.businessId
      wx.navigateTo({
        url: '/pages/decoration/supplyDetail/supplyDetail?id=' + item.supplyDemandId + '&addPlanId=' + planId + '&addPlanImg=' + item.planPicPath + '&planType=' + this.data.planType

      })
    }
  }
})