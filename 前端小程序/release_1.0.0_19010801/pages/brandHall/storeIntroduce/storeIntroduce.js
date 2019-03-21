// pages/brandHall/bHStore/bHStore.js
import { resourcePath } from '../../../utils/config.js'
let API = getApp().API
Page({

  /**
   * 页面的初始数据
   */
  data: {
    imgURL:getApp().data.imgURL,
    resourcePath: resourcePath,
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    wx.hideShareMenu();
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
  
  
  },
  getDetail:function(){
    API.getBasecompanyDetail({ companyId: 0 })
    .then(res => {
      if (res.code == 200) {
        if (!res.data) return;
        this.setData({
          exampleList: res.data
        })
      } else {
        wx.showToast({
          title: res.message || "网络错误 请稍后再试",
          icon: 'none'
        })
      }
    })
  }
})