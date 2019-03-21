
var calcTime = require('../../utils/calcTime.js'), resourcePath = getApp().resourcePath, API = getApp().API
Page({

  /**
   * 页面的初始数据
   */
  data: {
  shopid:'',
  pageSize:10,
  artList:[],
  articlesNum:0,
  resourcePath: resourcePath
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    wx.hideShareMenu();
    this.setData({
      shopid:options.shopid
    })
    this.getList();
  },
  getList() {
    API.getCompanyBlogArticle({
      limit: this.data.pageSize,
      page: 1,
      start: 0,
      shopId: this.data.shopid
    })
    .then(res => {
      for (let i = 0; i < res.datalist.length; i++) {
        res.datalist[i].showTime = calcTime.calcTime(res.datalist[i].releaseTimeStr)
      }
      this.setData({ artList: res.datalist, articlesNum: res.totalCount })
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
    this.setData({
      pageSize: this.data.pageSize+5
    })
    this.getList();
  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function () {
  
  },
  toArticles(e) {
    let id = e.currentTarget.dataset.id;
    console.log("1231231321", id)
    wx.navigateTo({
      url: '/pages/artDetail/artDetail?id=' + id,
    })
  }
})