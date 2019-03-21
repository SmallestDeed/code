// pages/homeList/homeList.js
import {
  resourcePath,
  defaultImg
} from '../../utils/config.js'
import utils from '../../utils/utils.js';
let $App = getApp(),
  API = getApp().API
Page({

  /**
   * 页面的初始数据
   */
  data: {
    defaultImg: defaultImg,
    headerActive: 1, //1软装，0硬装
    resourcePath: resourcePath,
    softList: [],
    planId: '',
    productBatchType: 1,
    pageNo: 1,
    productList: [],
    tabIndex: 0,
    scroolLeft: 0,
    emptyType: false
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function(options) {
    wx.hideShareMenu();
      new $App.newNav() // 注册快速导航组件
    this.setData({
      planId:options.id
    })
    this.getList()
  },

  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function() {

  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function() {

  },

  /**
   * 生命周期函数--监听页面隐藏
   */
  onHide: function() {

  },

  /**
   * 生命周期函数--监听页面卸载
   */
  onUnload: function() {

  },

  /**
   * 页面相关事件处理函数--监听用户下拉动作
   */
  onPullDownRefresh: function() {

  },

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom: function() {
    if (this.data.headerActive == 0) {
      this.setData({
        pageNo: this.data.pageNo + 1
      })
      this.getList()
    } else {
      this.setData({
        pageNo: this.data.pageNo + 1
      })
      this.getList()
    }
  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function(res) {
    if (res.from === 'menu') {
      return $App.shareAppMessageObj
    }
  },
  changeType(e) { // 切换焦点
    let index = e.currentTarget.dataset.index
    this.setData({
      headerActive: index,
      productBatchType: index
    })
    this.getList()
  },
  cyhangeType: function(e) {
    let name = e.currentTarget.dataset.name,
      index = e.currentTarget.dataset.i
    let width = parseInt((wx.getSystemInfoSync().windowWidth || 750) / 5);
    if (index == this.data.tabIndex) //不可重复点击
      return
    this.setData({
      scroolLeft: index * width,
      productList: this.data.softList.filter(item => item.spaceName == name),
      tabIndex: index
    })
  },
  getList: function() {
    let obj = null
    API.getHomeList(Object.assign({
      planId: this.data.planId,
      productBatchType: this.data.productBatchType,
      pageNo: this.data.pageNo,
    }, obj)).then(res => {
      if (res.message) {
        this.setData({
          softList: res.obj,
          productList: res.obj.slice(0, 1)
        })
      } else {
        this.setData({
          softList: [],
          emptyType: true
        })
      }
    })
  }
})