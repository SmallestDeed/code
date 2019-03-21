//logs.js
const util = require('../../../utils/util.js')
let API = getApp().API
let $App = getApp()
Page({
  data: {
    staticImageUrl: getApp().staticImageUrl,
    logs: [],
    planListLength: 0,
    houseCount: 0,
    invitationCount: 0,
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
      new $App.newNav() // 注册快速导航组件
    var that = this
    this.setData({
      logs: (wx.getStorageSync('logs') || []).map(log => {
        return util.formatTime(new Date(log))
      })
    })
    that.getPlan();
    that.getGoods();
    this.getHouselist();
    this.getInvitationList();
    this.sellingPoint();
  },
  onShow:function(){
    var that=this
    that.getPlan();
    that.getGoods();
  },
  //埋点
  sellingPoint(event) {
    let page = getCurrentPages(), previousPath = page.length > 1 ? page[page.length - 2].route : '',
      nowPath = page[page.length - 1].route
    API.sellingPoint({
      uid: wx.getStorageSync('openId'),
      cp: nowPath,
      lp: previousPath,
      e: event ? event : '',
      pt: '我的收藏'
    })
  },
  getInvitationList() {
    API.collectInvitationList({
      start: 0,
      limit: 10,
      fromFavorite: 1,
    }).then(res => {
      if (res.obj) {
        this.setData({
          invitationCount: res.totalCount
        });
      } 
    });
  },
  getHouselist() {
    API.collectHouselist({
      start: 0,
      limit: 10
    }).then(res => {
      if (res.obj) {
        this.setData({
          houseCount: res.totalCount
        });
      }
    });
  },
  getPlan: function () {
      API.getDesignplanfavoriteNum()
    .then((res) => {
      this.setData({ planListLength: res.totalCount  })
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
  }
})
