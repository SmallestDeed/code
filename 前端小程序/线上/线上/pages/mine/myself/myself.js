let $App = getApp(), API = getApp().API
Page({

  /**
   * 页面的初始数据
   */
  data: {
    userInfo: '',
    wxInfo: '',
    notLoginImgUrl: '',
    isgetPhone: false,
    issBindingMobile: false
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    let wxInfo = getApp().globalData;
    this.setData({ wxInfo: wxInfo })
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
    this.getUserInfo();
    this.getIsBindingMobile()
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
  getUserInfo: function () {
    API.getUserDuBiMessage()
    .then(res => {
      this.setData({ userInfo: res.obj })
    })
  },
  showBIndingPhoneBox() { // 打开绑定手机
    this.bindingPhoneShow()
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
    this.getUserInfo()
  },
  toMyAccount(){
    wx.navigateTo({
      url: '/pages/mine/my-account/my-account',
    })
  }
})