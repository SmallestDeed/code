
Page({

  /**
   * 页面的初始数据
   */
  data: {},

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    wx.hideShareMenu();
    this.requestPayment(options)
  },
  requestPayment: function (obj) {
    //调起微信支付
    wx.requestPayment({
      //相关支付参数
      'appId': obj.appId,
      'timeStamp': obj.timestamp,
      'nonceStr': obj.nonceStr,
      'package': 'prepay_id=' + obj.prepayId,
      'signType': obj.signType,
      'paySign': obj.paySign,
      'success': function (res) {
        wx.setStorageSync('payIsSuccess', 1)
        wx.showToast({ title: '支付成功', icon: 'none', success() {
          wx.navigateBack()
        } })
      },
      'fail': function (err) {
        wx.setStorageSync('payIsSuccess', 2)        
        wx.showToast({ title: '支付失败', icon: 'none', success() {
          wx.navigateBack()          
        }})
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
  onShareAppMessage: function () {
  
  }
})