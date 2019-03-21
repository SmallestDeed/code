let fetch = getApp().fetch
let myForEach = getApp().myForEach
let $App = getApp();
let urltType = 'system'
Page({
    data: {
        staticImageUrl: getApp().staticImageUrl,
        resourcePath: getApp().resourcePath,
        gifts: [],
        defaultAddress:'',
    },
    getMineList:function (){
        let url = '',
            that = this,
            data = {}
        fetch(url, 'get', data , urltType).then((res) => {
            if (res.code == 200) {
                that.setData({
                    gifts: res.data
                })
            }
        })
    },
    onLoad: function(options) {
        this.getMyOrder(options.id);
        
    },
    getMyOrder: function (id) {
        let url = '/v1/imallOrder/getImallOrder',
            that = this;
        fetch(url, 'get', {
            id: id
        }, urltType).then((res) => {
            if (res.code == 200) {
                that.setData({
                    gifts: res.data,
                    defaultAddress: res.data.addressList[0]
                })
            }
        })
    },
    onShow: function() {

    },
    onShareAppMessage: function() {

    }
})