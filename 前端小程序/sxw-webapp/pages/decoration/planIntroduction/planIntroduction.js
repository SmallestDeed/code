
let $App = getApp(), API = getApp().API
let WxParse = require('../../../utils/wxParse/wxParse.js')
Page({
  /**
   * 页面的初始数据
   */
  data: {
    caseDetails: {},
    resourcePath: getApp().resourcePath,
    typeList: ['', '', ''],
    isCollect: false,
    isLike: false,
    id:'',
    type:'',
    Detail:'',
    isShowReservePop:false,
    bindedName: '',
    bindingPhoneMobile:'',
    bindingPhoneGetCodeText: '获取验证码',
    collectRequest: true,
    favoriteRequest: true,
    isbindedPhone: false,
    isNeededShowPhone: false,
    serviceType: ''
  },
  goTo720View(e){
    let type = '720', routerQueryType = 'seven', item = e.currentTarget.dataset.item, webUrl = null, sevenObj = null
    item.fullHousePlanUUID ? (webUrl = $App.wholeHouse3dUrl, sevenObj = { uuid: item.fullHousePlanUUID, embedded: 1 }) :
      (webUrl = $App.sevenUrl, sevenObj = {
        token: wx.getStorageSync('token'),
        platformCode: 'brand2c',
        operationSource: 1,
        planId: item.designPlanRecommendId || item.planRecommendedId,
        routerQueryType: routerQueryType,
        customReferer: $App.wxUrl,
        platformNewCode: 'miniProgram'
      })
    for (let key in sevenObj) { webUrl += key + '=' + sevenObj[key] + '&' }
    getApp().data.webUrl = webUrl
    wx.navigateTo({ url: '/pages/web-720/web-720' })
    console.log(webUrl)
  },
  goToCollect(){
    this.setData({
      isCollect: !this.data.isCollect
    })
  },
  goToLike(){
    this.setData({
      isLike: !this.data.isLike
    })
  },
  /**
   * 获取方案介绍
   */
  getRecommendedDesignPlanDetail(){
    let that = this
      
    API.getPlanDetail({
      id: this.data.id,
      type: this.data.type
    }).then(res => {
      if(res.obj){
        this.setData({
          Detail:res.obj[0]
        })
        WxParse.wxParse('article', 'html', that.data.Detail.desc || '', that, 30);
      }
    })
  },
  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    wx.hideShareMenu();
    this.init(options)
  },
  
  init(options) { // 初始化
    new $App.newNav() // 注册快速导航组件
      
    this.setData({
      id: options.id,
      type: options.type,
      targetId: wx.getStorageSync('targetId')
    })
    this.getUserPhone();
    this.getCaseDetails(options.id, options.type == 2 ? 1 : 0); // 获取方案详情
    this.getRecommendedDesignPlanDetail();
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

  onReachBottom: function () {
  },

  onPullDownRefresh: function () {
  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function (res) {
  },
  showBindPhone() {
    this.setData({
      isbindedPhone: false,
      bindingPhoneMobile: ''
    })
  },
  getCaseDetails(id, type) {
    API.getCaseDetails({
      id: id,
      type
    })
      .then(res => {
        console.log(res, '1')
        res.status ? this.setData({ caseDetails: res.obj[0] }) : this.setData({ caseDetails: {} })
        res.status ? WxParse.wxParse('article', 'html', res.obj[0].desc || '', this, 5) : null
      })
  },
  putInMyhouse() { // 装进我家
    // this.renderTypeShow() // 显示组件  
    let item = this.data.caseDetails
    wx.setStorageSync('caseItem', item)
    wx.navigateTo({
      url: '/pages/plan/case-house-type/case-house-type?type=plan'
    })
  },
  ReservePop(e){
    this.getUserPhone();
    this.setData({
      isShowReservePop:true,
      serviceType: e.target.dataset.type,
      bindingPhoneCode: ''
    })
  },
  changeName(e) {
    this.setData({
      bindedName: e.detail.value
    })
  },
  changeMobile(e) {
    this.setData({
      'bindingPhoneMobile': e.detail.value
    })
    let regMobile = /(^1[3|4|5|6|7|8|9]\d{9}$)|(^09\d{8}$)/
    let flag = regMobile.test(this.data.bindingPhoneMobile)
    this.setData({
      'bindingPhoneformaTure': flag
    })

  },
  changeCode(e) {
    this.setData({
      'bindingPhoneCode': e.detail.value
    })
  },
  getCode() {
    if (!this.data.bindingPhoneformaTure || this.data.bindingPhoneGetCodeText !== '获取验证码') {
      wx.showToast({ title: '请输入正确的手机号码', icon: 'none' })
      return
    }
    API.getBindPhoneCode({
      phoneNumber: this.data.bindingPhoneMobile,
      functionType: 2
    })
      .then(res => {
        if (res.success) {
          let count = 61, $self = this
          this.data.bindingPhoneTimer = setInterval(() => {
            count--
            $self.setData({
              'bindingPhoneGetCodeText': count + 's'
            })
            if (count <= 0) {
              clearInterval($self.data.bindingPhoneTimer)
              $self.setData({
                'bindingPhoneGetCodeText': '获取验证码'
              })
            }
          }, 1000)
        } else {
          this.setData({
            'bindingPhoneGetCodeText': '获取验证码'
          })
        }
      })
  },
  getUserPhone() {
    API.getUserPhone({
      id: wx.getStorageSync('id')
    }).then(res => {
      if (res.success) {
        this.setData({
          bindedName: res.obj.nickName,
          bindingPhoneMobile: res.obj.mobile
        })
        if (this.data.bindingPhoneMobile) {
          this.setData({
            isbindedPhone: true,
            isNeededShowPhone: true
          })
        }
      }
    })
  },
  closeReservePop() {
    this.setData({ isShowReservePop: false })
  },
  goToReserve() {
    if (!this.checkPhone()) {
      wx.showToast({
        title: '请输入正确的手机号',
        icon: 'none'
      })
      return
    }
    if (!this.data.isbindedPhone && this.data.isNeededShowPhone) {
      if (!this.checkCode()) {
        wx.showToast({
          title: '请输入正确的验证码',
          icon: 'none'
        })
        return
      }
    }
    if (this.data.bindedName == '') {
      wx.showToast({
        title: '请输入姓名',
        icon: 'none'
      })
      return
    }
    const reserveData = {
      "serviceType": this.data.serviceType,
      "shopId": wx.getStorageSync('targetId'),
      "userName": this.data.bindedName,
        "appointUserId": wx.getStorageSync('id'),
      "userPhone": this.data.bindingPhoneMobile
    }
    if (this.data.bindingPhoneCode) {
      reserveData.code = this.data.bindingPhoneCode
    }
    API.getReserveSave(reserveData).then(res => {
      if (res.success) {
        wx.showToast({
          title: '预约成功',
          duration: 3000,
          icon: 'success'
        })
        this.setData({
          isShowReservePop: false
        })
        // this.getUserPhone();
      } else {
        wx.showToast({
          title: res.message,
          icon: 'none'
        })
      }
    })
  },
  checkCode() {
    return /^\d{6}\b/.test(this.data.bindingPhoneCode)
  },
  toHomeList(){
    wx.navigateTo({
      url: `/pages/homeList/homeList?id=${this.data.id}&type=${this.data.type}`
    })
  },
  checkPhone() {
    console.log(this.data.bindingPhoneMobile)
    let regMobile = /(^1[3|4|5|7|8]\d{9}$)|(^09\d{8}$)/
    return regMobile.test(this.data.bindingPhoneMobile)
  },
  beginBinding() { // 开始绑定
    let codeFlag = /^\d{6}\b/.test(this.data.bindingPhoneCode)
    if (!this.data.bindingPhoneMobile || !this.data.bindingPhoneCode) {
      return
    }
    if (!this.checkPhone()) {
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
      mobile: this.data.bindingPhoneMobile,
      authCode: this.data.bindingPhoneCode
    })
      .then(res => {
        if (res.success) {
          wx.showToast({
            title: '绑定成功',
            duration: 2000,
          })
          setTimeout(() => {
            this.setData({
              'bindingPhoneShow': false
            })
            this.bindPhoneCallBack() // 绑定手机号回调
          }, 2000);
        } else {
          wx.showToast({
            title: res.message,
            icon: 'none'
          })
        }
      })
  },

  collectOrLikeCase(obj) {
    let that = this, status = null, title = null
    if (this.data[obj.flag] == true) {
      this.setData({ [obj.flag]: false })
      this.data.Detail[obj.status] ? (status = 0, title = '取消' + obj.title) : (status = 1, title = obj.title)
      API[obj.api]({ status: status, [obj.param]: this.data.Detail.designPlanRecommendId || this.data.Detail.planRecommendedId, designPlanType: this.data.Detail.planHouseType })
        .then(res => {
          if (res.success) {
            status == 0 ? this.data.Detail[obj.num] -= 1 : this.data.Detail[obj.num] += 1
            this.data.Detail[obj.status] = status
            this.setData({ Detail: this.data.Detail })
            wx.showToast({ title: title + '成功' })
          } else {
            wx.showToast({ title: title + '失败', icon: 'none' })
          }
          setTimeout(function () { that.setData({ [obj.flag]: true }) }, 500)
        })
    }
  },
  collectCase(e) { // 方案收藏
    let index = e.currentTarget.dataset.index
    this.collectOrLikeCase({
      title: '收藏',
      flag: 'collectRequest',
      num: 'collectNum',
      status: 'isFavorite',
      api: 'collectCase',
      param: 'recommendId',
      index
    })
  },
  likeCase(e) { // 方案点赞
    let index = e.currentTarget.dataset.index
    this.collectOrLikeCase({
      title: '点赞',
      flag: 'favoriteRequest',
      num: 'likeNum',
      status: 'isLike',
      api: 'likeCase',
      param: 'designId',
      index
    })
  }
}) 