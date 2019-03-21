// pages/goods-evaluation/goods-evaluation.js
let API = getApp().API;
Page({
  /**
   * 页面的初始数据
   */
  data: {
    staticImageUrl: getApp().staticImageUrl,
    resourcePath: getApp().resourcePath,
    allEvaluationList: [],
    picEvaluationList: [],
    spuId: '',
    allTotal: 0,
    picTotal: 0,
    isPicComment: 0,
    allPageNo: 1,
    picPageNo: 1,
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    this.data.spuId = options.spuId
    this.getGoodsEvaluation('all')
    this.getGoodsEvaluation()
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

  //获取商品评价
  getGoodsEvaluation(type) {
    let obj = {
      userId: wx.getStorageSync('userId'), // 2501
      spuId: this.data.spuId, // 1
      pageSize: 10,
      pageNo: type == 'all' ? this.data.allPageNo : this.data.picPageNo
    }
    if (type != 'all') { obj.isPicComment = this.data.isPicComment}
    API.getGoodsEvaluation(obj).then(res => {
      if (res.obj) {
        if (type == 'all') {
          let allEvaluationList = this.data.allEvaluationList.concat(res.obj.data)
          this.setData({
            allEvaluationList: allEvaluationList,
            allTotal: res.obj.allTotal,
            picTotal: res.obj.picTotal
          })
        } else {
          let picEvaluationList = this.data.picEvaluationList.concat(res.obj.data)
          this.setData({
            picEvaluationList: picEvaluationList
          })
        }
      }
    });
  },

  examineImg(e) {
    wx.previewImage({
      urls: [this.data.resourcePath + e.currentTarget.dataset.item]
    });
  },

  switchover(e) {
    this.setData({
      isPicComment: e.currentTarget.dataset.type
    })
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
    if (this.data.isPicComment = 0 && this.data.pageNo * 10 < this.data.allTotal) {
      this.data.pageNo++;
      this.getGoodsEvaluation('all')
    }
    if (this.data.isPicComment = 1 && this.data.pageNo * 10 < this.data.picTotal) {
      this.data.pageNo++;
      this.getGoodsEvaluation()
    }
  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function () {

  }
})