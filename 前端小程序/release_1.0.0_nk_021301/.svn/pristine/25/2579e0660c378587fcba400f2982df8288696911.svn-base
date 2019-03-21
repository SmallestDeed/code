// pages/goods-list/goods-list.js
const util = require('../../utils/util.js')
let fetch = getApp().fetch
let $App = getApp();
let op = '';
Page({

  /**
   * 页面的初始数据
   */
  data: {
    pictures: ['https://p0.meituan.net/movie/ea4ac75173a8273f3956e514a4c78018253143.jpeg',
      'https://p0.meituan.net/movie/5d4fa35c6d1215b5689257307c461dd2541448.jpeg',
      'https://p0.meituan.net/movie/0c49f98a93881b65b58c349eed219dba290900.jpg',
      'https://p1.meituan.net/movie/45f98822bd15082ae3932b6108b17a01265779.jpg',
      'https://p1.meituan.net/movie/722de9a7b0c1f9c262162d87eccaec7c451290.jpg',
      'https://p0.meituan.net/movie/cb9be5bbedb78ce2ef8e83c93f83caca474393.jpg',
      'https://p1.meituan.net/movie/a852b992cdec15319c717ba9fa9b7a35406466.jpg',
      'https://p1.meituan.net/movie/dc1f94811793e9c653170cba7b05bf3e484939.jpg'
    ],
    goodsList:[],
    resourcePath: getApp().resourcePath,
    staticImageUrl: getApp().staticImageUrl,
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function() {
    new $App.quickNavigation() // 注册组件
    var that=this
    that.getGoods();
   
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
    this.getGoods();
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

  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function (res) {
    if (res.from === 'menu') {
      return $App.shareAppMessageFn(true);
    }
  },
  previewImage: function(e) {
    var that = this,
      //获取当前图片的下表
      index = e.currentTarget.dataset.index,
      //数据源
      pictures = this.data.pictures;
    wx.previewImage({
      //当前显示下表
      
      current: pictures[index],
      //数据源
      urls: pictures
    })
  },
  getGoods: function () {

    var that = this
    let url = "/product/productfavorite/spuCollectionList?isSort=0"
    var that = this;
    fetch(url, 'get', )
      .then((res) => {
        if (res.success) {
          that.setData({
            goodsList: res.obj
          })
        }
      })
  },
  toGoods:function(e){
    var id = e.currentTarget.dataset.id
      wx.navigateTo({
        url: '/pages/goods-details/goods-details?id=' + id,
      })
  },
  toshoptype:function(){
    wx.switchTab({
      url: '/pages/house-goods/house-goods',
    })
  }
})