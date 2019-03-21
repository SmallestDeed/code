let myForEach = getApp().myForEach,
    API = getApp().API,
    socket = null
let $App = getApp();
Page({
    data: {
        staticImageUrl: $App.staticImageUrl,
        num: 0,
        pageSize: 20,
        pageNum: 1,
        feedbackList:[],
    },
    onLoad: function (options) {
      wx.hideShareMenu();
    },
    onShow: function () {
        this.getFeedbackList();
    },
    toInfo(e) {
        wx.navigateTo({
            url: '/pages/feedbackInfo/feedbackInfo?id=' + e.currentTarget.dataset.id,
        })
    },
    getFeedbackList(){
        let data = {
            userId: wx.getStorageSync('id')
        }
        API.getFeedbackList(data).then(res=>{
            let flag = res.success && res.datalist
            this.setData({
                feedbackList: flag?res.datalist:[],
                totalCount: flag?res.totalCount:0,
            })
        })
    },
    onShareAppMessage: function () {

    }
})