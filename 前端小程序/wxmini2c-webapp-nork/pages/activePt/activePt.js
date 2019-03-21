// pages/activePt/activePt.js
let $App = getApp(),
  fetch = getApp().fetch
import { appid } from '../../utils/config.js';
Page({

  /**
   * 页面的初始数据
   */
  data: {
    appid: appid,
    resourcePath: getApp().resourcePath,
    staticImageUrl: getApp().staticImageUrl,
    activeList: '',
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    this.getList();
  },
  getList() {
    let url = '/core/company/groupPurchaseActivity'
    let data={
      appId: this.data.appid,
      companyId:wx.getStorageSync("companyId"),
      status:2
    }
    fetch(url, 'post', data, 'core')
      .then(res => {
        if(res.datalist){
          this.setData({
            activeList:res.datalist
          })
        }
      })
  },
  toActive(e){
    let id = e.currentTarget.dataset.id,
      spuid = e.currentTarget.dataset.spuid
    wx.navigateTo({
      url: '/pages/goods-details/goods-details?isAssemble=true&id=' + spuid + '&groupId=' + id + '&spuId=' + spuid,
    })
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