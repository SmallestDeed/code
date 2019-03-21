// pages/bowenDetail/bowenDetail.js
const app = getApp(), API = app.API //每个界面都要写上API，才能调
Page({

  /**
   * 页面的初始数据
   */
  data: {
    defaultHeader: '/static/images/header.png',
    resourcePath: app.data.resourcePath, // 这个是图片的前缀
    articleId:'',
    articleDetail:{},
    shopDetail:{}
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    
    this.data.articleId = options.articleid;
    this.getArticleDetail()
  },
  goutong(e) {
    //let name = e.currentTarget.dataset.name;
    let items = this.data.articleDetail.shopDetail;
    items.shopIntroduced='';
    let item = JSON.stringify(items);
    wx.navigateTo({
      url: `/pages/chat/chat?item=${item}`,
    })
  },
  getArticleDetail(){
    API.getBowenDetail({
      articleId: this.data.articleId
    }).then((res) => {
      if(res.obj) {
        res.obj.content = res.obj.content ? res.obj.content.replace(/\<img/gi, '<img class="img"'):''
        res.obj.releaseTimeStr = app.changeTiem(res.obj.releaseTimeStr)
        this.setData({
          articleDetail: res.obj,
          shopDetail: res.obj.shopDetail
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
    let timer = setInterval(() => {
      if (app.data.loginStatus === 1) {
        clearInterval(timer)
      }
    },100)
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

  }
})