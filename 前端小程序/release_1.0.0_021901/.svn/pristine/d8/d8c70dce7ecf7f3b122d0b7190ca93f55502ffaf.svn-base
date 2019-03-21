// pages/myHouse/myHouse.js
let app = getApp(), API = app.API;
Page({

  /**
   * 页面的初始数据
   */
  data: {
    resourcePath: app.resourcePath,
    isShowEmpty: false,
    houseList: [],
    curPage: 1,
    totalCount: 0
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    this.getUserHouseTypeList();
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

  /*查看大图*/
  examineImg(e) {
    if (e.currentTarget.dataset.img) {
      wx.previewImage({
        urls: [e.currentTarget.dataset.img],
        current: e.currentTarget.dataset.img
      })
    }
  },

  /*获取用户户型列表*/
  getUserHouseTypeList(type) {
    API.getUserHouseTypeList({ pageSize: 5, curPage: this.data.curPage}).then(res => {
      if (res.obj && res.obj.houselist && res.obj.houselist.length > 0) {
        let arr = type == 'pull' ? this.data.houseList.concat(res.obj.houselist) : res.obj.houselist;
        this.setData({
          houseList: arr,
          totalCount: res.totalCount
        });
      }
      else {
        this.setData({
          isShowEmpty: true
        });
      }
    });
  },

  /*去装修*/
  goOnekeyFinish(e) {
    wx.removeStorageSync('caseItem')
    let item = e.currentTarget.dataset.item;
    wx.navigateTo({
      url: '/pages/OnekeyFinish/OnekeyFinish?type=houseType&item=' + JSON.stringify(item)
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
    if (this.data.curPage * 5 < this.data.totalCount) {
      this.data.curPage++;
      this.getUserHouseTypeList('pull');
    }
  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function () {

  }
})