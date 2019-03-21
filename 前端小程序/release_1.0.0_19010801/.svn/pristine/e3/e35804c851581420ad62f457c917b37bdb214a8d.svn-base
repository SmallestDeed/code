let API = getApp().API,
    myForEach = getApp().myForEach,
    $App = getApp();
Page({
    data: {
      webUrl: getApp().squeezePageUrl + '/landingpage/mobilezerodesign.html'
    },
    onLoad: function (options) {
      wx.hideShareMenu();
    },
    onShow: function () {

    },
    onShareAppMessage: function (res) {
      if (res.from === 'menu') {
        let shareObj = {
          title: '0元设计',
          path: '/pages/home/home?shareType=active&pageType=zero',
          success: function (res) {
            // 转发成功
          },
          fail: function (res) {
            // 转发失败
          }
        }
        return shareObj;
      }
    }
})