  // pages/web-720/web-720.js
let mySplitUrl = getApp().mySplitUrl, myCompoundUrl = getApp().myCompoundUrl
Page({

  /**
   * 页面的初始数据
   */
  data: {
    webUrl: ''
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    let webUrl = null
    if (options.item) {
      webUrl = myCompoundUrl(JSON.parse(options.item))
    } else {
      webUrl = getApp().data.webUrl
    }
    this.setData({
      webUrl: webUrl
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
  onShareAppMessage: function (res) {
    let that = this
    let item = mySplitUrl(res.webViewUrl)
    delete item.token
    let count = Math.round(Math.random() * 2) + 1
    let imageUrl = './image/wechat_share_0' + count + '.png'
    let path = `/pages/web-720/web-720?item=` + JSON.stringify(item)
    if (res.from === 'menu') {
      return {
        title: '720 3D全景图',
        imageUrl: imageUrl,
        path: path,
        success(res) {
          console.log(path, 'res')
        },
        fail(err) {
          console.log(err, 'err')
        }
      }
    }
  }
})  