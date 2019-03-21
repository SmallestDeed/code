let API = getApp().API,
    myForEach = getApp().myForEach,
    $App = getApp();
Page({
    data: {
      webUrl: getApp().sxwLangingPage + '/sxw/landingpage/mobildecoration.html?userId=' + wx.getStorageSync('id'),
    },
    onLoad: function(options) {
        
    },
    onShow: function() {

    },
    onShareAppMessage: function(res) {
        if (res.from === 'menu') {
            return $App.shareAppMessageFn(true);
        }
    }
})