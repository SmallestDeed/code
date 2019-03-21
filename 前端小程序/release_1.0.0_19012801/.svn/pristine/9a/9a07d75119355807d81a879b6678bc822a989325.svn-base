//logs.js
const util = require('../../../utils/util.js')
let API = getApp().API, $App = getApp()
Page({
  data: {
    staticImageUrl: $App.staticImageUrl,
    logs: [],
    score:'',
    scoreDetail:[],
    isMember: false, // 是否包年包月
    // residueDay: 0, // 剩余天数,
    issBindingMobile: false
  },
  onLoad: function () {
    wx.hideShareMenu();
      new $App.newNav() // 注册快速导航组件
    new $App.bindingPhone() 
    var that=this
    this.setData({
      logs: (wx.getStorageSync('logs') || []).map(log => {
        return util.formatTime(new Date(log))
      })
    })
    this.getIsBindingMobile()
    that.getScore();
    that.getScoreDetail();
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
  },
  showBIndingPhoneBox() { // 打开绑定手机
    console.log('123,')
    this.bindingPhoneShow()
  },
  onShow: function () {
    this.getuserIsPackageInMonthly() // 套餐
  },
  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function (res) {
    if (res.from === 'menu') {
      return $App.shareAppMessageObj
    }
  },
  getScore:function(){
    API.getUserDuBiMessage()
    .then((res) => {
      if (res.status) {
        this.setData({ score: res.obj.balanceIntegral })
      } else {
        wx.showToast({ title: '获取度币信息失败', icon: 'none', duration: 3000 })
      }
    })
  },
  getScoreDetail:function(){
    var that = this
    var url = "/web/pay/payOrder/findExpenseRecordList"
    API.getUserDuBiList()
    .then(res => {
      if (res.success) {
        that.setData({ scoreDetail: res.obj })
      } else {
        wx.showToast({ title: '获取度币明细失败', icon: 'none', duration: 3000 })
      }
    })
  },
  toBuy:function(){
    var that=this
    wx.navigateTo({
      url: '/pages/buy-points/buy-points?score='+that.data.score,
    })
  },
  routerToDredgeCombo() {
    wx.navigateTo({
      url: '/pages/mine/dredge-combo/dredge-combo',
    })
  },
  getuserIsPackageInMonthly() { // 获取用户包年包月信息
    API.getuserIsPackageInMonthly()
    .then(res => {
      if (res.success) {
        if (res.obj.state === '0') {
          this.setData({ isMember: false, residueDay: 0 })
        } else if (res.obj.state === '2' || res.obj.state === '3') {
          this.setData({ isMember: true, residueDay: res.obj.leftTime })
        }
      }
    })
  }
})
