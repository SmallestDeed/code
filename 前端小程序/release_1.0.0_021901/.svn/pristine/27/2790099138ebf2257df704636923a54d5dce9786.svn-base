// pages/myAccount/myAccount.js
let app = getApp(), API = app.API, time;
Page({

  /**
   * 页面的初始数据
   */
  data: {
    userInfo: {},
    userInfoList: [],
    isShowEmpty: false,

    phone:'',
    code:'',
    toastShow:false,
    codeTit: '获取验证码',
    i: 60,
    phoneBool:false,
    codeBool: false,
    issBindingMobile: false
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    this.init();
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

  /*初始化*/
  init() {
    API.getUserDuBiMessage().then(res => {
      if (res.obj) {
        res.obj.balanceIntegral = parseInt(res.obj.balanceIntegral)
        this.setData({ userInfo: res.obj })
      }
      this.setData({ userInfo: res.obj })
    });
    API.getUserDuBiList().then(res => {
      if (res.obj && res.obj.length > 0) {
        this.setData({ userInfoList: res.obj, isShowEmpty: false })
      } else {
        this.setData({ isShowEmpty: true })
      }
    });
    API.getIsBindingMobile().then(res=>{
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
  showToast() {
    this.setData({
      toastShow: true
    })
  },
  closeToast() {
    clearInterval(time);
    this.setData({
      toastShow: false,
      i: 60,
      codeTit: '获取验证码',
      phoneBool: false,
      codeBool: false,
      phone:'',
      code:''
    })
  },
  getCode() {
    var i = this.data.i;
    if (this.data.phoneBool) {
      var that = this;
      time = setInterval(function () {
        i--;
        that.setData({
          codeTit: i + "s"
        })
        if (i < 1) {
          clearInterval(time);
          that.setData({
            codeTit: '获取验证码',
            i: 60
          })
        }
      }, 1000);
      var data = {
        phoneNumber: this.data.phone,
        functionType: 2
      };
      API.getBindPhoneCode(data).then(res => {
        if (res.success) {
          wx.showToast({
            title: '验证码发送成功',
            icon:null
          })
        }
      });
    }
  },
  getCodeValue(e) {
    this.setData({
      code: e.detail.value
    })
    if (this.data.code.length == 6 && /^\d{6}\b/.test(this.data.code)) {
      this.setData({
        codeBool: true
      })
    }else {
      this.setData({
        codeBool: false
      })
    }
  },
  getPhone(e) {
    this.setData({
      phone: e.detail.value
    })
    if (this.data.phone.length == 11 && app.regMobile.test(this.data.phone)) {
      this.setData({
        phoneBool: true
      })
    }else {
      this.setData({
        phoneBool: false
      })
    }
  },
  beginBinding() { // 开始绑定
    let flag = app.regMobile.test(this.data.phone)
    let codeFlag = /^\d{6}\b/.test(this.data.code)
    if (!this.data.phone || !this.data.code) {
      return
    }
    if (!flag) {
      wx.showToast({
        title: '请输入正确的手机号',
        icon: 'none'
      })
      return
    } else if (!codeFlag) {
      wx.showToast({
        title: '请输入正确的验证码',
        icon: 'none'
      })
      return
    }
    API.bindUserMobile({
      mobile: this.data.phone,
      authCode: this.data.code
    })
      .then(res => {
        if (res.success) {
          wx.showToast({
            title: '绑定成功',
            duration: 2000,
          })
          this.closeToast();
          this.setData({
            issBindingMobile: true
          })
        } else {
          wx.showToast({
            title: res.message,
            icon: 'none'
          })
        }
      })
  },
  /*去充值*/
  goAddMoney() {
    wx.navigateTo({
      url: '/pages/goAddMoney/goAddMoney?score=' + this.data.userInfo.balanceIntegral,
    })
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