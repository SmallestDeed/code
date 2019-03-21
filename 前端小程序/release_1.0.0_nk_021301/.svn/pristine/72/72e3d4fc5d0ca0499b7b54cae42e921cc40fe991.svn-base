let fetch = getApp().fetch, $App = getApp(), API = getApp().API 
var { shareTitle } = require('../../utils/config.js');
var shareTitle = { shareTitle }.shareTitle;
let flagTitle = (shareTitle == '诺克照明');
Page({

  /**
   * 页面的初始数据
   */
  data: {
    staticImageUrl: getApp().staticImageUrl,
    userInfo: {},
    wxInfo:'',
    notLoginImgUrl:'',
    isgetPhone: false,
    issBindingMobile: false,
    flagTitle: flagTitle,
    businessType:''
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function(options) {
    var that=this;
    new $App.bindingPhone() // 注册组件
    wx.getStorage({
      key: 'businessType',
      success: function(res) {
        that.setData({
          businessType: res.data
        })
      },
    })
    
  },

  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function() {

  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function() {
    this.getUserInfo();
    this.getIsBindingMobile()
    let userInfo = wx.getStorageSync('userInfo')
    if (userInfo) {
      console.log(11111111, getApp().globalData.userInfo)
      this.setData({
        wxInfo: getApp().globalData.userInfo
      })
    } else {
      console.log(2222222, getApp().globalData.userInfo)
      this.setData({
        wxInfo: null
      })
    }
  },

  /**
   * 生命周期函数--监听页面隐藏
   */
  onHide: function() {

  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function (res) {
    if (res.from === 'menu') {
      return $App.shareAppMessageFn(true);
    }
  },

  getInfo(e) {
    if(e.detail.userInfo){
      wx.setStorage({
        key: 'userInfo',
        data: e.detail.userInfo,
      })
      getApp().globalData.userInfo = e.detail.userInfo
    }
    this.setData({
      wxInfo: e.detail.userInfo
    })
    API.saveMinProNickName({
      nickName: e.detail.userInfo.nickName,
      headPic: e.detail.userInfo.avatarUrl
    })
  },

  getUserInfo: function() {
    let url = "/web/system/sysUser/getUserBalance"
    var that = this;
    fetch(url, 'get', '', 'pay')
      .then((res) => {
        var userInfo = res.obj;
        that.setData({
          userInfo: userInfo
        })
      })
  },
  showBIndingPhoneBox() { // 打开绑定手机挂
    this.bindingPhoneShow()
  },
  getIsBindingMobile() {
    // 检验手机号是否存在
    let url = `/v2/user/center/isUserMobileBinded`
    fetch(url, 'get', {}, 'user')
      .then((res) => {
        if (res.success) {
          if (res.obj === 0) {
            this.setData({
              issBindingMobile: false
            })  
          } else if (res.obj === 1) {
            this.setData({
              issBindingMobile: true
            })
          }
        } else {
          this.setData({
            issBindingMobile: false
          })
        }
      })
  },
  bindPhoneCallBack() {
    this.setData({
      issBindingMobile: true
    })
    this.getUserInfo()
  },
  toPayDuCoin(){
    wx.navigateTo({
      url: '/pages/dredge-combo/dredge-combo',
    })
  },
  toBuyCoin(){
    wx.navigateTo({
      url: '/pages/my-account/my-account',
    })
  }
})
















