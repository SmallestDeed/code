// pages/chooseAddress/chooseAddress.js
import { myPinYin } from '../../utils/Convert_Pinyin.js'
let $App = getApp(), API = getApp().API, myForEach = getApp().myForEach
Page({

  /**
   * 页面的初始数据
   */
  data: {
    cityList: [],
    locationCity: '',
    ListId: "热门",
    cityItem: [],
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    wx.hideShareMenu();
    let item = JSON.parse(options.cityItem);
    this.setData({
      cityItem: item
    })
    // this.getLocation();
    this.getCitys();

  },
  getCitys: function () {
    this.setData({ cityList: $App.data.cityList })
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
  chooseCity(e) {
    //把选项存入缓存
      let cityItem = e.currentTarget.dataset.city ? e.currentTarget.dataset.city: { areaCode: '', areaName: "全部", baseAreaVos: [], id: 9999, levelId: 2, pid: '' }
    let addressHistotyList = wx.getStorageSync('addressHistoty') || [{ areaCode: '440300', areaName: "深圳市", baseAreaVos: [], id: 1976, levelId: 2, pid: '440000' }]
    let index = addressHistotyList.findIndex((n) => { return n.areaCode == cityItem.areaCode})
    index == -1 ? (addressHistotyList.push(cityItem) > 3 ? addressHistotyList.splice(1,1) : null) : null
    $App.data.cityList[0] = { key: 'zj', keyshow: '定位', val: addressHistotyList, show: '定位/最近访问' }
    wx.setStorageSync('addressHistoty', addressHistotyList)
    wx.setStorageSync('nowCity', cityItem)
    getCurrentPages()[getCurrentPages().length - 2].setData({ cityItem: cityItem });
    wx.navigateBack({ delta: 1 })
  },
  toWord(e) {
    let Item = e.currentTarget.dataset.item;
    if (Item == "最近") { Item = "zj" }
    if (Item == "热门") { Item = "rm" }
    this.setData({ ListId: Item });
  },
})