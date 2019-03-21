"use strict";

function _interopRequireDefault(e) {
  return e && e.__esModule ? e : {
    default: e
  }
}
let App = getApp()
var _config = require("../../../utils/config.js"), API = getApp().API
Page({
  data: {
    staticImageUrl: getApp().staticImageUrl,
    shopid: "",
    casePageSize: 10,
    caseList: [],
    resourcePath: _config.resourcePath
  },
  onLoad: function(e) {
    wx.hideShareMenu();
      new App.newNav() // 注册快速导航组件
    this.setData({
      shopid: e.shopid
    }), this.getList()
  },
  getList: function() {
    var e = this;
    API.getProjectCaseList({
      pageNo: 1,
      pageSize: this.data.casePageSize,
      shopId: this.data.shopid
    }).then(res => {
      if(res.success){
        this.setData({
          caseList: res.datalist
        })
      }
    })
  },
  onReady: function() {},
  onShow: function() {},
  onHide: function() {},
  onUnload: function() {},
  onPullDownRefresh: function() {},
  onReachBottom: function() {
    this.setData({
      casePageSize: this.data.casePageSize+5
    })
    this.getList();
  },
  onShareAppMessage: function() {},
  toWorkCase(e) {
    let id = e.currentTarget.dataset.id
    wx.navigateTo({
      url: '/pages/caseDetail/caseDetail?id=' + id,
    })
  }
});