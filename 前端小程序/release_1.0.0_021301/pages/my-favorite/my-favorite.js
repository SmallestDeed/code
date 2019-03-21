//logs.js
const util = require('../../utils/util.js')
let API = getApp().API
let $App = getApp()
Page({
  data: {
    logs: [],
    planList:[],
    goodsList:[],
  },
  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function (res) {
    if (res.from === 'menu') {
      return $App.shareAppMessageObj
    }
  },
  onLoad: function () {
    wx.hideShareMenu();
    var that = this
    this.setData({
      logs: (wx.getStorageSync('logs') || []).map(log => {
        return util.formatTime(new Date(log))
      })
    })
    that.getPlan();
    that.getGoods();
    
  },
  onShow:function(){
    var that=this
    that.getPlan();
    that.getGoods();
  },
  getPlan: function () {
    API.getDesignplanfavorite({ spaceType: '', designPlanStyleId: '', spaceArea: '', isSort: 0})
    .then((res) => {
      this.setData({ planList: res.obj })
    })
  },
  getGoods:function(){
    API.getProductfavorite({ isSort: 0})
      .then((res) => {
        this.setData({ goodsList: res.obj })
      })
  },
  toMyGoods:function(e){
    var that=this
    wx.navigateTo({
      url: '/pages/my-favorite-goods/my-favorite-goods?goodslist=' + that.data.goodsList,
    })
  },
  toZero(){
    wx.switchTab({
      url: '/pages/zerounit-design/zerounit-design',
    })
  }
})
