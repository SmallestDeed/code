// pages/putInMyHouse/putInMyHouse.js
let $App = getApp(), API = getApp().API
Page({

  /**
   * 页面的初始数据
   */
  data: {
    userInfo: '',
    pData: '',
    isShowIntegralInsufficient: false,
    issBindingMobile: false
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    wx.hideShareMenu();
    let pData = JSON.parse(options.planData)
    this.setData({
      pData: pData
    })
    this.getIsBindingMobile();
    new $App.bindingPhone();
  },
  getUserInfo: function () {
    API.getUserDuBiMessage()
      .then(res => {

        this.setData({ userInfo: res.obj })
        console.log(this.data.userInfo)
      })
  },
    submitInfo(e) { // 模板消息接口调用
        wx.setStorage({ key: 'formId', data: e.detail.formId })
      console.log(e.detail.formId, this.data.pData.planRecommendedId || this.data.pData.designPlanRecommendId, '亮眼=====');
        API.saveUserRenderFormId({
            designPlanId: this.data.pData.planRecommendedId || this.data.pData.designPlanRecommendId,//方案id
            formId: e.detail.formId,//表单ID
            forwardPage: 'pages/home/home?navToUrl=/pages/my-tasks/my-tasks',//跳转页
            renderType: 0,//渲染类型：0，装进我加；1，替换渲染
            renderLevel: 4 //渲染级别：1，照片；4：720；6，视频；8，720
        }).then(res => {
            console.log(res, '亮眼-----------')
        })
    },
  confirm(e) {
      let thay = this;
    API.addRenderTask(this.data.pData)
      .then(res => {
        if (res.success) {
          if (res.message === '创建渲染任务成功！') {
            this.getUserInfo()
            thay.submitInfo(e)
            wx.showModal({
              title: '正在渲染中...',
              content: '预计3分钟后完成',
              confirmText: '查看任务',
              cancelText: '返回',
              cancelColor: '#999999',
              confirmColor: '#ff6419',
              success: (res) => {
                res.confirm ? wx.navigateTo({ url: '/pages/my-tasks/my-tasks' }) : null
              }
            })
          } else {
            wx.showToast({ title: '111' + res.message, icon: 'none' })
          }
        } else {
          if (res.message === '账户度币不足，请充值.') {
            this.setData({
              isShowIntegralInsufficient: true
            });
            // wx.showModal({
            //   title: '提示',
            //   content: '度币余额不足，去充值？',
            //   confirmText: '去充值',
            //   cancelText: '暂不考虑',
            //   cancelColor: '#999999',
            //   confirmColor: '#ff6419',
            //   success: (res) => {
            //     res.confirm ? wx.navigateTo({ url: '/pages/buy-points/buy-points' }) : null
            //   }
            // })
          } 
          // else {
          //   wx.showModal({
          //     title: '提示',
          //     content: '户型数量已用完，购买更多？',
          //     confirmText: '去购买',
          //     cancelText: '暂不考虑',
          //     cancelColor: '#999999',
          //     confirmColor: '#ff6419',
          //     success: (res) => {
          //       res.confirm ? wx.navigateTo({ url: '/pages/purchase-house/purchase-house' }) : null
          //     }
          //   })
          // }
        }
      })
  },
  myevent(e) {
    this.setData({
      isShowIntegralInsufficient:  false
    });
    if (e.detail == '暂不考虑') {return}
    if (e.detail == '去充值') {
      wx.navigateTo({ url: '/pages/buy-points/buy-points' });
    }
    if (e.detail == '立即绑定') {
      this.bindingPhoneShow()
    }
  },
  getIsBindingMobile() {
    // 检验手机号是否存在
    let url = `/v2/user/center/isUserMobileBinded`
    API.getIsBindingMobile()
      .then(res => {
        if (res.success) {
          if (res.obj === 0) {
            this.setData({ issBindingMobile: false })
          } else if (res.obj === 1) {
            this.setData({ issBindingMobile: true })
          }
        } else {
          this.setData({ issBindingMobile: false })
        }
        console.log(this.data.issBindingMobile, 'eqeweqeq')
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
    this.getUserInfo()
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

  },
  putInMyHouse() {

  }
})