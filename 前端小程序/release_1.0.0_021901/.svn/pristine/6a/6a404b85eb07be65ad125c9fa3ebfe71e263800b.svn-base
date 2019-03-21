// pages/myCollect/myCollect.js
let app = getApp(), API = getApp().API
Page({

  /**
   * 页面的初始数据
   */
  data: {
    isShowEmpty: false,
    designList: [],
    start: 0,
    totalCount: 0
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    this.getCollectDesign();
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

  /*获取收藏的方案,type:pull为下拉拼接数据*/
  getCollectDesign(type) {
    API.getCollectDesign({
      pageVo: { pageSize: 5, start: this.data.start },
      recommendationPlanSearchVo: { designStyleId: '', houseType: '', spaceArea: '' }
    }).then(res => {
      if (res.datalist) {
        let arr = type == 'pull' ? this.data.designList.concat(res.datalist) : res.datalist
        this.setData({
          designList: arr,
          totalCount: res.totalCount
        });
      } else {
        this.setData({
          isShowEmpty: true
        })
      }
    });
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
    this.data.start += 5;
    if (this.data.start < this.data.totalCount) {
     this.getCollectDesign('pull');
    }
  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function () {

  }
})