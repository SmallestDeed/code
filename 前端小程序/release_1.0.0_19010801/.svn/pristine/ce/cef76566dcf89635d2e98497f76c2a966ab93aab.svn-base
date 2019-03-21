let API = getApp().API,
    myForEach = getApp().myForEach,
    $App = getApp();
Page({
    data: {
        staticImageUrl: $App.staticImageUrl,
        resourcePath: getApp().resourcePath
    },
    onLoad: function(options) {
      wx.hideShareMenu();
    },
    toPages:function(){
        wx.switchTab({
            url: '/pages/plan/house-case/house-case',
        })
    },
    onShow: function() {

    },
    onShareAppMessage: function(res) {
        if (res.from === 'menu') {
            return $App.shareAppMessageFn(true);
        }
    }
})