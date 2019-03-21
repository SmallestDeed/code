// pages/brandHall/storeCoupon/storeCoupon.js
import { resourcePath } from '../../utils/config.js'
import fetch from '../../utils/fetch.js';
let $App = getApp();
Page({
  data: {
    imgURL: getApp().data.imgURL,
    staticImageUrl: getApp().staticImageUrl,
    resourcePath: resourcePath,
    companyId: wx.getStorageSync("companyId"),
    pageNo: 1,
    pageSize: 10,
    goodsList: [],
    totalCount: 0,
  },
  onLoad: function () {
  },
  onShow: function() {
    this.getList();
  },
  getList:function(){
    let url = 'v1/sandu/mini/goodsrecommend/getTopList',
        that = this,
        data = {
          pageNo: this.data.pageNo,
          pageSize: this.data.pageSize,
          companyId: this.data.companyId,
        }
    fetch(url, 'get', data, 'shop').then(res => {
      that.setData({
        goodsList: res.data.list,
        totalCount: res.data.total,
      })
    })
  },
  goGoodsInfo: function (e) {
    let id = e.currentTarget.dataset.id;
    wx.navigateTo({
      url: '/pages/goods-details/goods-details?id=' + id,
    })
  },
  onReachBottom: function () {
    if (this.data.pageSize <= this.data.totalCount) {
      let pagenum = this.data.pageSize + 10
      this.setData({
        pageSize: pagenum
      })
      this.getList();
    } else {
      wx.showToast({
        title: '暂无更多数据',
        icon: 'none'
      })
      return
    }
  },
  onShareAppMessage: function (res) {
    if (res.from === 'menu') {
      return $App.shareAppMessageFn(true);
    }
  }
})