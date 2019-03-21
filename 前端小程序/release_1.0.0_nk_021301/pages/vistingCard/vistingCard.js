// pages/vistingCard/vistingCard.js
let $App = getApp(),
  fetch = getApp().fetch
Page({

  /**
   * 页面的初始数据
   */
  data: {
    detail: '',
    resourcePath: getApp().resourcePath,
    getObj: {
      phone: '',
      code: ''
    },
    inputFlag: false,
    inputFlag2: false,
    message: "获取验证码",
    callFlag: false,
    createUser: '',
    shareType: '',
    userCardId: '',
    userCardAccessId: '',
    staticImageUrl: $App.staticImageUrl,
    loginFlag: true,
    shareFlag: false,
    userCardAccessRecordId: '',
    shareParam: {},
    QRCode: ''
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    console.log(getApp().globalData.userInfo, 'info')
    this.setData({
      createUser: options.createUser,
      shareType: options.shareType,
      userCardId: options.userCardId
    })
    this.getDetail('logo');
    if (getApp().globalData.userInfo == null) {

    } else {
      this.getDetail('all');
      this.addaccessrecord();
      this.getQRCode();
      this.setData({
        loginFlag: false
      })
    }


  },
  getQRCode() {
    let url = '/core/userCard/getWXQRCode'
    let data = {
      companyId: wx.getStorageSync('companyId'),
      userCardId: this.data.userCardId
    }
    fetch(url, 'get', data, 'core').then((res) => {
      if (res.obj) {
        this.setData({
          QRCode: res.obj
        })
      }
    })
  },
  getUserInfo(e) {
    if (e.detail.rawData) {
      this.getDetail('all')
      this.addaccessrecord();
      this.getQRCode();
      this.setData({
        loginFlag: false
      })
    } else {
      wx.showToast({
        title: '登录失败，请重试',
        icon: 'none'
      })
    }

  },
  share() {
    this.setData({
      shareFlag: true
    })
  },

  shareCancle() {
    this.setData({
      shareFlag: false
    })
  },
  getDetail(e) {

    let url = '/core/userCard/getUserCard',
      data = {
        userId: this.data.createUser
      }
    fetch(url, 'get', data, 'core').then((res) => {
      if (res.obj) {
        if (e == 'logo') {
          this.setData({
            'detail.companyLogoPath': res.obj.companyLogoPath
          })
        } else if (e == 'all') {
          this.setData({
            detail: res.obj
          })
        }

      }
    })
  },
  addaccessrecord() {
    let url = '/core/usercardaccess/addaccessrecord',
      data = {
        userCardId: this.data.userCardId,
        accessType: this.data.shareType
      }
    fetch(url, 'post', data, 'core').then((res) => {
      if (res.obj) {
        this.setData({
          userCardAccessId: res.obj
        })
      }
    })
  },
  access(e) {//意向记录
    let url = '/core/usercardaccess/addaccessoperation';
    let optionsType = e;
    let data = {
      userCardId: this.data.userCardId,
      userCardAccessRecordId: this.data.userCardAccessId,
      operationType: e
    }
    fetch(url, 'post', data, 'core').then((res) => {
      console.log(res)
    })
  },
  changeInput(e) {
    let key = 'getObj.' + e.target.dataset.type
    this.setData({
      [key]: e.detail.value.trim(),
    })
    if (e.target.dataset.type == 'phone' && e.detail.value.length >= 11) {
      this.setData({
        inputFlag: true,
      })
    }
  },
  getCodeFun() {
    let phone = this.data.getObj.phone,
      that = this;
    console.log("987654321")
    if (!phone || !(/^1[3|4|5|8|9][0-9]\d{8}$/.test(phone))) {
      console.log("98765432123456")
      wx.showToast({
        title: '请正确填写电话!',
        icon: 'none'
      })
      return;
    }
    let url = `/v1/center/getSmsCode`
    fetch(url, 'formData', { phoneNumber: phone, functionType: 2 }, 'user')
      .then((res) => {
        if (res.success) {
          let num = 60
          that.setData({
            inputFlag2: true,
            message: num + 's',
          })
          let setFunc = setInterval(function () {
            num--;
            that.setData({
              message: num + 's',
            })
            if (num <= 0) {
              clearInterval(setFunc);
              that.setData({
                message: '获取验证码',
              })
            }
          }, 1000)
        }
      })
  },
  submitFun(e) {
    let that = this,
      code = this.data.getObj.code;
    if (!code) {
      wx.showToast({
        title: '请填写验证码',
        icon: 'none'
      })
      return
    }
    console.log(this.data.userCardAccessId, '123456')
    let num = e.currentTarget.dataset.num;
    let url = `/core/usercardaccess/addaccessoperation`
    let data = {
      userCardId: this.data.userCardId,
      userCardAccessRecordId: this.data.userCardAccessId,
      operationType: num,
      phone: this.data.getObj.phone,
      code: this.data.getObj.code
    }
    fetch(url, 'post', data, 'core').then((res) => {

      if (!res.success) {
        wx.showToast({
          title: '验证码不匹配，请重新输入',
          icon: 'none'
        })
      } else {
        wx.showToast({
          title: this.data.detail.userName + '会尽快与您联系，请保持手机通畅',
          icon: 'none'
        })
        this.setData({
          callFlag: false,
          "getObj.phone": "",
          "getObj.code": ""
        })
      }
    })
  },
  callMe() {
    this.setData({
      callFlag: true
    })
  },
  cancleCall() {
    this.setData({
      callFlag: false
    })
  },
  callCard(e) {
    let phone = e.currentTarget.dataset.phone;
    let num = e.currentTarget.dataset.num
    wx.makePhoneCall({
      phoneNumber: phone,
    })
    this.access(num)
  },
  copyText(e) {
    let num = e.currentTarget.dataset.num
    wx.setClipboardData({
      data: e.currentTarget.dataset.text || '',
      success: function (res) {
        wx.getClipboardData({
          success: function (res) {
            wx.showToast({
              title: '复制成功'
            })
          }
        })
      }
    })
    this.access(num)
  },
  // showImage(){
  //   let obj={
  //     backImg:'http://wximg.dev.sanduspace.com/card-bg.png',
  //     company: this.data.detail.companyName,
  //     name: this.data.detail.userName,
  //     job: this.data.detail.companyPost,
  //     phone: this.data.detail.phone,
  //     email:this.data.detail.email,
  //     address: this.data.detail.address,
  //     QRCode: this.data.QRCode,
  //     headPic: this.data.detail.userHeadPicPath
  //   }
  //   this.setData({
  //     shareParam:obj
  //   })
  //   console.log(this.data.shareParam)
  //   this.createPoster.toggleDialog(true);
  // },
  showImage() {
    this.setData({
      shareFlag: false
    })
    let obj = {
      backImg: 'https://show.sanduspace.com/mobile2b/supplyDemand/image/2019/01/17/21/1547732522731/111.png',
      company: this.data.detail.companyName,
      name: this.data.detail.userName,
      job: this.data.detail.companyPost,
      phone: this.data.detail.phone,
      email: this.data.detail.email,
      address: this.data.detail.address,
      QRCode: this.data.QRCode,
      headPic: this.data.detail.userHeadPicPath
    }
    wx.setStorageSync('vistingCard', obj)
    wx.navigateTo({
      url: '/pages/cardPoster/cardPoster',
    })
  },
  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function () {
    this.createPoster = this.selectComponent("#createPoster");
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
  onShareAppMessage: function (e) {
    this.setData({
      shareFlag: false
    })
    return {
      title: '我的名片',
      path: '/pages/index/index?scene=u_c_p,' + this.data.createUser + ',' + this.data.userCardId + ',' + this.data.shareType,

      success: function (res) {
        // 转发成功
        console.log("转发成功:" + JSON.stringify(res));
      },
      fail: function (res) {
        // 转发失败
        console.log("转发失败:" + JSON.stringify(res));
      }
    }
  },
  goIndex() {
    wx.switchTab({
      url: '/pages/index/index',
    })
  },
  toTest() {
    wx.navigateTo({
      url: '/pages/test/test',
    })
  }
})