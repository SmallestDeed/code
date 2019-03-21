// pages/dredge-combo/dredge-combo.js
let $App = getApp()
let API = getApp().API
Page({

  /**
   * 页面的初始数据
   */
  data: {
    isMonthOrYears: 0,
    expiryTime: 0,
    packageInMonthlyPamrams: [],
    isMember: false,
    payModelConfigId: '',
    comboState: ''
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    wx.hideShareMenu();
      new $App.newNav() // 注册快速导航组件
    this.getuserIsPackageInMonthly() // 获取用户包年包月信息
    this.getPackageInMonthlyPamrams() // 获取包年包月的参数
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
  // 获取包年包月信息
  getuserIsPackageInMonthly() { // 获取用户包年包月信息
    API.getuserIsPackageInMonthly()
    .then(res => {
      if (res.success) {
        if (res.obj.state === '0') {
          this.setData({ comboState: res.obj.state, isMember: false, expiryTime: 0 })
        } else if (res.obj.state === '2' || res.obj.state === '3') {
          this.setData({ isMember: true, expiryTime: res.obj.leftTime, comboState: res.obj.state })
        }
      } else {
        this.setData({ isMember: false, expiryTime: 0, comboState: '' })
      }
    })
  },
  getPackageInMonthlyPamrams() { // 获取包年包月参数
    API.getPackagesParams()
    .then(res => {
      if (res.success) {
        $App.myForEach(res.obj, (value, index) => {
          value.id == 18 ? value.text = '1年' : value.text = '1月'
          index == 0 ? this.setData({ payModelConfigId: value.id }) : ''
        })
        this.setData({ packageInMonthlyPamrams: res.obj })
      } else {
        this.setData({ packageInMonthlyPamrams: [],  payModelConfigId: '' })
      }
    })
  },
  chooseCombo(e) {
    let item = e.currentTarget.dataset.item
    let index = e.currentTarget.dataset.index    
    this.setData({
      payModelConfigId: item.id,
      isMonthOrYears: index
    })
  },
  instantlyDredgeCombo() { // 立即开通
    let url = `/web/pay/miniProPayOrder/packagePay`
    API.dredgePackageInMonthly({ payModelConfigId: this.data.payModelConfigId, payMethod: 'miniPay' })
    .then(res => {
      if (res.success) {
        wx.requestPayment({
          'timeStamp': res.obj.timeStamp,
          'nonceStr': res.obj.nonceStr,
          'package': res.obj.packageStr,
          'signType': 'MD5',
          'paySign': res.obj.paySign,
          'success': function (response) {
            wx.showToast({ title: '支付成功' })
          },
          'fail': function (err) {
            wx.showToast({ title: '支付失败', icon: 'none' })
          }
        })
      } else {
        wx.showToast({ title: '支付失败', icon: 'none' })
      }
    })
  }
})