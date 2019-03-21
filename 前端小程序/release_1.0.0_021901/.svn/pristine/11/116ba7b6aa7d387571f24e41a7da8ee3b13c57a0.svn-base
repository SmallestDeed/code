let app = getApp(), API = getApp().API
Page({

  /**
   * 页面的初始数据
   */
  data: {
    resourcePath: app.resourcePath,
    sevenUrl: getApp().sevenUrl,
    isShowEmpty: false,
    handleIndex: -1,
    isShowRechristen: false,
    curPage: 1,
    designList: [],
    totalCount: 0,
    reanme: '',
    reanmeObj: {},
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    this.getMyDegian();
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

  /*阻止弹框界面拉动*/
  hmove() {
    return;
  },

  /*跳转720*/
  toThreeD(e) {
    console.log(e.currentTarget.dataset.item)
    let item = e.currentTarget.dataset.item,
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
        // url = '/mobile/renderPic/getPictureList.htm',
        API.getDesignPlanResourceId(data)
          .then(res => {
            if (res.success) {
              // url2 = '/mobile/design/designPlan/getPanoPicture.htm',
              API.getDesignPlanResource({
                thumbId: res.datalist[0].id
              }).then(res => {
                console.log(res)
                res.success ? (routerQueryType === 'video' ? that.toVideo(res.obj.url) : that.enlargeImage([res.obj.url])) : wx.showToast({
                  title: '打开失败',
                  icon: 'none'
                })
              })
            }
          })
      } else {
        item.fullHousePlanUUID ? (webUrl = app.wholeHouseUrl, sevenObj = {
          uuid: item.fullHousePlanUUID,
          embedded: 1
        }) :
          (webUrl = that.data.sevenUrl, sevenObj = {
            token: wx.getStorageSync('token'),
            platformCode: 'brand2c',
            operationSource: 0,
            planId: item.businessId,
            routerQueryType: routerQueryType,
            customReferer: app.wxUrl,
            platformNewCode: 'miniProgram'
          })
        for (let key in sevenObj) {
          webUrl += key + '=' + sevenObj[key] + '&'
        }
        getApp().webUrl = webUrl
        app.myNavigateBack('pages/web-720/web-720')
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

  /**
  * 确定:confirm，取消:cancel, 打开：open，重命名;
  * 打开右上角操作框：handle
  */
  operation(e) {
    let type = e.currentTarget.dataset.type;
    // this.data.index = e.currentTarget.dataset.index ? e.currentTarget.dataset.index : '';
    //打开右上角操作框
    if (type == 'handle') {
      let index = e.currentTarget.dataset.index;
      this.setData({
        handleIndex: this.data.handleIndex == index ? -1 : index
      });
    }
    //打开重命名
    if (type == 'open') {
      let item = e.currentTarget.dataset.item;
      let obj = {
        planType: item.planHouseType,
        planId: item.planHouseType == 1 ? item.businessId : item.newFullHousePlanId,
        taskId: item.planHouseType == 1 ? item.taskId : item.mainTaskId,
        renderState: item.renderState
      }
      this.setData({
        rename: item.designName,
        isShowRechristen: true,
        reanmeObj: obj
      });
    }
    //删除
    if (type == 'delete') {
      let that = this;
      let item = e.currentTarget.dataset.item;
      let obj = {
        planId: item.planHouseType == 1 ? (item.businessId ? item.businessId : 0) : (item.newFullHousePlanId ? item.newFullHousePlanId : 0),
        taskId: item.planHouseType == 1 ? item.taskId : item.mainTaskId,
        renderState: item.renderState,
        planType: item.planHouseType
      }
      API.deleteMyDesign(obj).then(res => {
        if (res.obj) {
          wx.showToast({
            title: '删除成功',
            duration: 2000,
            complete: function () {
              setTimeout(function () {
                // that.getMyDegian();
                let arr = that.data.designList;
                arr.splice(that.data.handleIndex, 1);
                that.setData({ handleIndex: -1, designList: arr });
              }, 2000) //延迟时间
            }
          })
        } else {
          wx.showToast({
            title: '删除失败',
            duration: 2000,
            complete: function () {
              setTimeout(function () {
                that.getMyDegian();
              }, 2000) //延迟时间
            }
          })
        }
      });
    }
    //取消重命名
    if (type == 'cancel') {
      this.setData({
        isShowRechristen: false
      });
    }
    //确定重命名
    if (type == 'confirm') {
      let that = this;
      this.data.reanmeObj.planName = this.data.rename;
      API.degianReanme(this.data.reanmeObj).then(res => {
        if (res.obj) {
          wx.showToast({
            title: '重命名成功',
            duration: 1500,
            complete: function () {
              setTimeout(function () {
                let arr = that.data.designList;
                arr[that.data.handleIndex].designName = that.data.rename
                that.setData({ isShowRechristen: false, handleIndex: -1, designList: arr });
              }, 1500) //延迟时间
            }
          })
        } else {
          wx.showToast({
            title: '重命名失败',
            duration: 1500,
            complete: function () {
            }
          })
        }
      });
    }
  },

  /*去免费报价*/
  goLanding(e) {
    let item = e.currentTarget.dataset.item;
    let id = item.planRenderType == 1 ? item.businessId : item.newFullHousePlanId
    let tys = item.planRenderType == 1 ? 0 : 1;
    let urls = 'https://720.sanduspace.com/static/sxw/landingpage/mobildecoration.html';
    let url = '/pages/landing/landing?info=1&id=' + id + '&type=' + tys + '&url=' + urls;
    wx.navigateTo({
      url: url,
    })
  },

  /*继续装修*/
  toHouseDeatil(e) {
    let item = e.currentTarget.dataset.item
    wx.navigateTo({
      url: '/pages/OnekeyFinish/OnekeyFinish?type=myPlan&houseId=' + item.houseId + '&templateId=' + item.templateId + '&fullHousePlanId=' + item.newFullHousePlanId + '&mainTaskId=' + item.mainTaskId
    })
    // console.log(item.mainTaskId, 'swq')
  },

  /*获取并过滤输入框表情*/
  getRename(e) {
    this.setData({
      rename: app.myEmoticon(e.detail.value)
    })
  },

  /*获取我的方案列表*/
  getMyDegian(type) {
    API.getMyDegian({
      pageSize: 10,
      curPage: this.data.curPage
    }).then(res => {
      if (res.datalist) {
        let arr = type == 'pull' ? this.data.designList.concat(res.datalist) : res.datalist;
        this.setData({
          designList: arr,
          totalCount: res.obj
        });
      } else {
        this.setData({
          isShowEmpty: type == 'pull' ? false : true
        })
      }
    });
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
    if (this.data.curPage * 10 < this.data.totalCount) {
      this.data.curPage += 1;
      this.getMyDegian('pull');
    }
  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function (res) {
    // let item;
    // if (res.from === 'button') {
    //   // 来自页面内转发按钮
    //   console.log(res.target.dataset.item)
    //   item = res.target.dataset.item;
    // }
    // return {
    //   title: item.designName,
    //   path: '/page/user?id=123'
    // }
    let that = this;
    if (res.from === 'menu') {
      return app.shareAppMessageObj
    }
    if (res.from === 'button') {
      console.log('999')
      let item = res.target.dataset.item, routerQueryType = res.target.dataset.type, webUrl = null, sevenObj = null
      let imgurl;
      item.planPicPath ? imgurl = this.data.resourcePath + item.planPicPath : imgurl = '/static/image/design_def.png'
      item.newFullHousePlanId ? (webUrl = app.wholeHouse3dUrl, sevenObj = {
        uuid: item.vrResourceUuid,
        token: wx.getStorageSync('token'),
        platformCode: 'brand2c',
        operationSource: 0,
        planId: item.newFullHousePlanId,
        routerQueryType: 'seven',
        customReferer: app.wxUrl,
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
          customReferer: app.wxUrl,
          platformNewCode: 'miniProgram',
          isTask: 1,
          taskType: item.planRenderType,
          houseId: item.houseId || 0
        })
      console.log(sevenObj)
      for (let key in sevenObj) { webUrl += key + '=' + sevenObj[key] + '&' }
      webUrl = encodeURIComponent(webUrl)
      console.log('123', webUrl)
      let shareObj = {
        title: item.designName,
        path: '/pages/house-case/house-case?shareType=720&url=' + webUrl,
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
  }
})