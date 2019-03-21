// pages/planList/planList.js
let fetch = getApp().fetch
Page({

  /**
   * 页面的初始数据
   */
  data: {
    planPageSize:5,
    planList:[]
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    console.log(options.shopId,'123')
    this.getPlan(options.shopId);
  },
  getPlan(e) {
    let url = '/fullsearch-app/all/recommendationplan/search/mini/list';
    let recommendationPlanSearchVo = {
      companyId: wx.getStorageSync('companyId'),
      shopId: e,
      displayType: 'decorate',
      enterType: 'shop'
    }
    let pageVo = {
      pageSize: this.data.planPageSize,
      start: 1,
    }
    let parms = {
      "recommendationPlanSearchVo": recommendationPlanSearchVo,
      "pageVo": pageVo
    }
    fetch(url, 'post', parms, 'fullsearch')
      .then((res) => {
        if (res.success) {
          this.setData({
            planList: res.datalist
          })
        }
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
    this.getPlan()
    this.setData({
      planPageSize: this.data.planPageSize+5
    })
  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function () {

  }
})