// pages/festivalActivity/festivalActivity.js
Page({

  /**
   * 页面的初始数据
   */
  data: {
    url: '',
    options: ''
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function(options) {
    this.options = options;
    if (options.activityId) {
      // 默认进入主页  分享进来是进好友助力  
      let loadUrl = `${getApp().sxwSqueezePage}festivalActivity`;
      let shareUrl = `${getApp().sxwSqueezePage}inviteFriend`;
      let url = `${options.isShare == 1 ? shareUrl : loadUrl}?activityId=${options.activityId}&wheelId=${options.wheelId}&token=${wx.getStorageSync('token')}&inviteUserId=${options.inviteUserId || wx.getStorageSync('id')}`;
      this.data.url = url;
    }else{
      this.data.url = getApp().data.festivalUrl
      // console.log(options.url)
      // this.data.url = decodeURIComponent(options.url)
    }
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
    this.setData({
      url: `${this.data.url}&load=1`
    })
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
  onShareAppMessage: function() {
    //isShare=1  是否是分享
    let url = `${getApp().sxwSqueezePage}inviteFriend?activityId=${this.options.activityId}&wheelId=${this.options.wheelId}&inviteUserId=${wx.getStorageSync('id')}`; 
    console.log(url)
    return {
      title: '随选闹元宵，请你看电影',
      path: `/pages/home/home?url=${encodeURIComponent(url)}`,
      imageUrl: getApp().staticImageUrl + "spring_share_bg.png",
      success: function(res) {
        // 转发成功
        console.log(res)
      },
      fail: function(res) {
        // 转发失败
      }
    }
  }
})