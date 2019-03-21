// pages/purchase-house/purchase-house.js
let API = getApp().API
let myForEach = getApp().myForEach
let myFindIndex = getApp().myFindIndex 
let $App = getApp()

Page({

  /**
   * 页面的初始数据
   */
  data: {
    purchaseList: [],
    userMessage: {
      balanceIntegral: 0,
      usableHouseNumber: 0
    },
    issBindingMobile: false
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    wx.hideShareMenu();
    this.getIsBindingMobile()
    this.getHouseCount() // 获取户型参数
    new $App.bindingPhone()
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
    if (res.from === 'menu') {
      return $App.shareAppMessageObj
    }
  },
  showBIndingPhoneBox() { // 打开绑定手机
    this.bindingPhoneShow()
  },
  getHouseCount() { // 购买户型参数
    API.getBuyHouseChooseParams()
    .then(res => {
      if (res.status) {
        myForEach(res.obj, (value, index) => {
          index == 0 ? value.flag = true : value.flag = false
        })
        this.setData({ purchaseList: res.obj })
      } else {
        this.setData({ purchaseList: [] })
      }
    })
    API.getUserHouseTypeMesasage()
    .then(res => {
      if (res.status) {
        this.setData({ userMessage: res.obj })
      } else {
        this.setData({ userMessage: { balanceIntegral: 0, usableHouseNumber: 0 } })
      }
    })
  },
  routerToBuyPoints() {
    wx.navigateTo({
      url: '/pages/buy-points/buy-points?score=' + this.data.userMessage.balanceIntegral
    })
  },
  radioChange(e) {
    console.log(e.detail.value)
    let count = parseInt(e.detail.value)
    myForEach(this.data.purchaseList, (value ,index) => {
      if (count === index) {
        value.flag = true
      } else {
        value.flag = false        
      }
    })
    this.setData({
      purchaseList: this.data.purchaseList
    })
  },
  confirmPurchaseHouse() { // 确认购买户型
    let index = myFindIndex(this.data.purchaseList, (value) => { return value.flag })
    API.buyHOuseType({ expandType: this.data.purchaseList[index].value })
    .then(res => {
      let $self = this
      if (res.status) {
        wx.showToast({ title: '购买户型成功' })
        setTimeout(() => { $self.getHouseCount() }, 1000)
      } else {
        wx.showModal({
          title: '提示',
          content: '度币余额不足，去充值？',
          confirmText: '去充值',
          cancelText: '暂不考虑',
          cancelColor: '#999999',
          confirmColor: '#ff6419',
          success: (res) => {
            if (res.confirm) {
              wx.navigateTo({
                url: '/pages/buy-points/buy-points'
              })
            }
          }
        })
      }
    })
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
      })
  },
  bindPhoneCallBack() {
    this.setData({
      issBindingMobile: true
    })
  }
})