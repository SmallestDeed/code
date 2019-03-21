// pages/bowen/bowenList.js
const app = getApp(), API = app.API //每个界面都要写上API，才能调
Page({

  /**
   * 页面的初始数据
   */
  data: {
    defaultImg: '/static/images/news_pic_nor.png',
    bowenList:[],
    resourcePath: app.data.resourcePath, // 这个是图片的前缀
    shopId: 0,
    start: 1,
    totalCount: 0,
    isShowEmpty: false
  },
  // 博文列表
  getBowenList(type) {
    API.getIndexBowenList({
      limit: 10,
      page: this.data.start,
      shopId: this.data.shopId || ''
    }).then((res) => {
      if (res.datalist) {
        let arr = type == 'pull' ? this.data.bowenList.concat(res.datalist) : res.datalist;
        arr.forEach((value) => {
          value.releaseTimeStr = app.changeTiem(value.releaseTimeStr)
        })
        this.setData({
          bowenList: arr,
          totalCount: res.totalCount,
          isShowEmpty: arr.totalCount > 0 ? false : true
        })    
      } else {
        this.setData({ isShowEmpty: true });
      }
    })
  },

  // 跳到博文详情
  bowenDetail(e) {
    this.data.start = 1
    let item = e.currentTarget.dataset.item;
    wx.navigateTo({
      url: `/pages/bowenDetail/bowenDetail?articleid=${item.articleId}`,
    })
  },
  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    this.data.shopId = options.shopId;
    this.data.companyId = options.companyId
    this.getBowenList()
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
    let timer = setInterval(() => {
      if (app.data.loginStatus === 1) {
        clearInterval(timer)
        this.getBowenList()
      }
    }, 100)
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
    if (this.data.start * 10 < this.data.totalCount) {
      this.data.start++;
      this.getBowenList('pull')
    }
  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function () {

  }
})