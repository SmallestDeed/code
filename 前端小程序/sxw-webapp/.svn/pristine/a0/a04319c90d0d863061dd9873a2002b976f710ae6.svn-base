// pages/work-case/work-case.js
let $App = getApp(), API = getApp().API
var WxParse = require('../../utils/wxParse/wxParse.js')
Page({

  /**
   * 页面的初始数据
   */
  data: {
    List: {},
    id: '',
    caseDetail: [],
    resourcePath: getApp().resourcePath,
    richtxt: ''
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    wx.hideShareMenu();
      new $App.newNav() // 注册快速导航组件
    this.setData({
      id: options.id
    })
    this.getCase()

  },
  getCase() {
    let url = '/sandu/mini/companyshop/projectCaseList';
    let txt = ''
    API.getProjectCaseList({  pageType: 'detail', caseId: this.data.id })
    .then(res => {
      if (res.success) {
        this.setData({ caseDetail: res.datalist[0] })
        this.getRichTxt();
      }
    })
  },
  getRichTxt() {
    var that = this
    wx.request({
      url: that.data.resourcePath + that.data.caseDetail.filePath,
      success: function (res) {
        that.setData({
          richtxt: res.data
        })
        WxParse.wxParse('article', 'html', that.data.richtxt, that, 0);
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

  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function () {

  },
  preview(e) {

    let urls = [];
    for (let i = 0; i < this.data.List.content.length; i++) {
      if (this.data.List.content[i].type == 'image') {
        urls.push(this.data.List.content[i].content)
      }
    }
    wx.previewImage({
      current: e.currentTarget.dataset.url,
      urls: urls,
    })
  }
})