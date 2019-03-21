// pages/artDetail/artDetail.js
let $App = getApp()
let WxParse = require('../../utils/wxParse/wxParse.js'), API =getApp().API
Page({

  /**
   * 页面的初始数据
   */
  data: {
    id:'',
    Detail:'',
    richtxt:''
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    wx.hideShareMenu();
      new $App.newNav() // 注册快速导航组件
    this.setData({
      id:options.id
    })
    this.getDetail();
  },
  
  getDetail(){
    var that=this
    API.getArticleDetails({ articleId: this.data.id })
      .then((res) => {
        this.setData({ Detail: res.obj, richtxt: res.obj.content })
        WxParse.wxParse('article', 'html', that.data.richtxt, that, 30);
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