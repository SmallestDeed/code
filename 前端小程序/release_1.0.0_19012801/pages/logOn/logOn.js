let API = getApp().API,
    myForEach = getApp().myForEach,
    $App = getApp();
Page({
    data: {
        staticImageUrl: $App.staticImageUrl,
    },
    onLoad: function (options) {
      wx.hideShareMenu();
    },
    onShow: function () {

    },
    onPullDownRefresh: function () {

    },
    onReachBottom: function () {

    },
    onShareAppMessage: function () {

    },
    launchAppError(e) {
        // wx.navigateToMiniProgram({
        //     appId: 'wxe24ed743feb9c17f',
        //     path: 'page/index/index',
        //     extraData: {
        //         foo: 'bar'
        //     },
        //     envVersion: 'develop',
        //     success(res) {
        //         // 打开成功
        //     }
        // })
    }
})