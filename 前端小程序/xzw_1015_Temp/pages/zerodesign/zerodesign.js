let API = getApp().API,
    myForEach = getApp().myForEach,
    $App = getApp();
Page({
    data: {
      webUrl: getApp().sxwLangingPage + '/sxw/landingpage/mobilezerodesign.html'
    },
    onLoad: function (options) {
    },
    onShow: function () {

    },
    onShareAppMessage: function () {
        if (res.from === 'menu') {
            return $App.shareAppMessageFn(true);
        }
    }
})