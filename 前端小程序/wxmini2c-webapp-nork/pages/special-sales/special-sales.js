//logs.js
const util = require('../../utils/util.js')
let fetch = getApp().fetch
let myForEach = getApp().myForEach
let $App = getApp();
Page({
  data: {
    staticImageUrl:getApp().staticImageUrl,
    logs: [],
    goodsList:[],
    resourcePath: getApp().resourcePath,
    bgImg:getApp().staticImageUrl+'goods_banner_02.jpg',
  },
  onLoad: function () {
    this.getGoodsList();
  },
  onShareAppMessage: function (res) {
    if (res.from === 'menu') {
      $App.shareAppMessageFn(true);
    }
  },
  getGoodsList:function(){
    let url = '/goods/basegoods/getSpecialSale';
    let that = this;
    let data = { type: 1 };
    fetch(url, 'get' ,data).then((res) => {
      if (res.obj) {
        that.setData({
          goodsList: res.obj,
        })
      }
    })
  },
  toPage(e) {
    wx.navigateTo({
      url: '../goods-details/goods-details?id=' + e.currentTarget.dataset.id
    })
  }
})
