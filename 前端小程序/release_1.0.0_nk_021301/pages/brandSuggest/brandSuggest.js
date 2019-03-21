// pages/brandSuggest/brandSuggest.js
let $App = getApp(),
  fetch = getApp().fetch
import { appid } from '../../utils/config.js';
const WxParse = require('../../utils/wxParse/wxParse.js');
Page({

  /**
   * 页面的初始数据
   */
  data: {
    brandInfo:'',
    appid:appid,
    resourcePath: getApp().resourcePath,
  },
  getRichTxt() {
    let url = '/core/company/branddesc/' + this.data.appid + '/Y',
      that = this
    fetch(url, 'get', '', 'core').then((res) => {
      if (res.obj) {
        that.setData({
          brandInfo: res.obj
        })
        WxParse.wxParse('article', 'html', this.data.brandInfo.richContext, this, 15);
      }
    })

   
  },
  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    this.getRichTxt()
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