let myForEach = getApp().myForEach,
    $App = getApp(),
    API = getApp().API
Page({
    data: {
        staticImageUrl: $App.staticImageUrl
    },

    onLoad: function(options) {
      wx.hideShareMenu();
    },
    copyFun() {
        wx.setClipboardData({
            data: 'suixuanwang001',
            success: function (res) {
                wx.getClipboardData({
                    success: function (res) {
                        console.log(res);
                        wx.showToast({
                            title: '复制 suixuanwang001 成功,快去加入吧！',
                            icon: 'none'
                        })
                    }
                })
            }
        })
    },
    onShow: function() {

    },
    onReachBottom: function() {

    },

    onShareAppMessage: function() {

    }
})