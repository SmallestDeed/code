// pages/newsList/newsList.js
let app = getApp(), API = app.API;
Page({

  /**
   * 页面的初始数据
   */
  data: {
    type: '',
    messagesList: [],
    start: 0,
    totalCount: 0,
    isShowEmpty: false
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    wx.setNavigationBarTitle({title: options.type});
    this.data.type = options.type;
    this.request({ 
      getName: this.data.type == '系统消息' ? 'getSystemRenderList' : 'getUserRenderList',
      type: 'init'
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

  /*获取数据*/
  request(obj) {
    API[obj.getName]({
      order: "gmt_modified",
      orderNum: "desc",
      limit: 10,
      start: this.data.start
    }).then(res => {
      if (res.success && res.obj) {
        let arr;
        if (obj.type == 'pull') {
          arr = this.data.messagesList.concat(res.obj);
        } else {
          arr = res.obj;
        }
        this.setData({ messagesList: arr, totalCount: res.totalCount, isShowEmpty: false });
      } else {
        this.setData({ isShowEmpty: true})
      }
    })
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
    if ((this.data.start + 1) * 10 < this.data.totalCount) {
      this.data.start += 1;
      this.request({ 
        getName: this.data.type == '系统消息' ? 'getSystemRenderList' : 'getUserRenderList',
        type: 'pull'
      })
    }
  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function () {

  }
})