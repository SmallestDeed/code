// pages/dredge-combo/dredge-combo.js
let $App = getApp()
let fetch = $App.fetch
Page({

  /**
   * 页面的初始数据
   */
  data: {
    staticImageUrl:getApp().staticImageUrl,
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
  onLoad: function () {
    new $App.quickNavigation() // 注册组件
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
      return $App.shareAppMessageFn(true);
    }
  },
  // 获取包年包月信息
  getuserIsPackageInMonthly() { // 获取用户包年包月信息
    let url = `/web/pay/checkRenderGroopRef2C`
    fetch(url, 'get', {}, 'pay')
    .then((res) => {
      if (res.success) {
        if (res.obj.state === '0') {
          this.setData({
            comboState: res.obj.state
          })
          this.setData({
            isMember: false,
            expiryTime: 0
          })
        } else if (res.obj.state === '2' || res.obj.state === '3') {
          this.setData({
            isMember: true,
            expiryTime: res.obj.leftTime,
            comboState: res.obj.state
          })
        }
      } else {
        this.setData({
          isMember: false,
          expiryTime: 0,
          comboState: ''
        })
      }
    })
  },
  getPackageInMonthlyPamrams() { // 获取包年包月参数
    let url = `/web/pay/getPackages`
    fetch(url ,'get', {}, 'pay')
    .then((res) => {
      if (res.success) {
        // res.obj = res.obj.reverse()
        $App.myForEach(res.obj, (value, index) => {
          if (value.id == 18) {
            value.text = '1年'
          } else {
            value.text = '1月'
          }
          index == 0 ? this.setData({ payModelConfigId: value.id}): ''
        })
        this.setData({
          packageInMonthlyPamrams: res.obj
        })
      } else {
        this.setData({
          packageInMonthlyPamrams: [],
          payModelConfigId: ''
        })
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
    // if (this.data.payModelConfigId === 17 && this.data.comboState === '3') { // 包年不能包月
    //   wx.showToast({
    //     title: '购买失败',
    //     icon:'none'
    //   })
    //   return
    // }
    let url = `/web/pay/miniProPayOrder/packagePay`
    fetch(url, 'formData', {
      payModelConfigId: this.data.payModelConfigId,
      payMethod: 'miniPay'
    }, 'pay')
    .then((res) => {
      if (res.success) {
        wx.requestPayment({
          'timeStamp': res.obj.timeStamp,
          'nonceStr': res.obj.nonceStr,
          'package': res.obj.packageStr,
          'signType': 'MD5',
          'paySign': res.obj.paySign,
          'success': function (response) {
            wx.showToast({
              title: '支付成功',
            })
          },
          'fail': function (err) {
            wx.showToast({
              title: '支付失败',
              icon: 'none'
            })
          }
        })
      } else {
        wx.showToast({
          title: '支付失败',
          icon: 'none'
        })
      }
    })
  }
})