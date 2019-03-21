let API = getApp().API,
    myForEach = getApp().myForEach,
    $App = getApp();
Page({
    data: {
        staticImageUrl: $App.staticImageUrl,
        pageNo:1,
        pageSize:10,
        listObj:[],
        totalCount:0,
    },
    onLoad: function(options) {
      wx.hideShareMenu();
    },
    onShow: function() {
        this.getList();
    },
    onReachBottom: function(res) {
        if (this.data.pageSize<=this.data.totalCount){
            this.setData({
                pageSize: this.data.pageSize+10
            })
            this.getList();
        }else{
            wx.showToast({
                title: '暂无更多数据',
                icon: 'none'
            })
        }
    },
    onShareAppMessage: function(res) {
        if (res.from === 'menu') {
            return $App.shareAppMessageFn(true);
        }
    },
    getList(){
        let data = {
            pageNo: this.data.pageNo,
            pageSize: this.data.pageSize,
        };
        let that = this;
        API.getDecorateOrderList(data).then(res=>{
            if(res.status){
                console.log(res);
                that.setData({
                    listObj: res.obj,
                    totalCount: res.totalCount
                })
            }
        })
    },
    goAdmission: function() {
        wx.navigateTo({
            url: '/pages/admission/admission',
        })
    },
})