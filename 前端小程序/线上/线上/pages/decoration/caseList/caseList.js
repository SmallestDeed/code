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
    shopid: "",
    casePageSize: 5,
    caseList: [],
    resourcePath: _config.resourcePath
  },
  onLoad: function(e) {
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
    }).then(function (t) {
      200 == t.code && (console.log(t), e.setData({
        caseList: t.data
      }))
    })
  },
  onReady: function() {},
  onShow: function() {},
  onHide: function() {},
  onUnload: function() {},
  onPullDownRefresh: function() {},
  onReachBottom: function() {},
  onShareAppMessage: function() {},
  toWorkCase(e) {
    let id = e.currentTarget.dataset.id
    wx.navigateTo({
      url: '/pages/caseDetail/caseDetail?id=' + id,
    })
  }
});