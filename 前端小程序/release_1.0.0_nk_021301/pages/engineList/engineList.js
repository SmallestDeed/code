import fetch from '../../utils/fetch.js'
let $App = getApp();
Page({

    data: {
        resourcePath: getApp().resourcePath,
        casePageSize: 10,
        staticImageUrl: getApp().staticImageUrl,
        shopid:'',
        caseList:'',
    },

    onLoad: function(options) {
        this.setData({
            shopid: options.id
        })
        this.getCase();
    },

    onShow: function() {

    },
    getCase() {
        let url = '/v1/sandu/mini/companyshop/projectCaseList';
        fetch(url, 'get', {
                pageNo: 1,
                pageSize: this.data.casePageSize,
                shopId: this.data.shopid
            }, 'shop')
            .then((res) => {
              if (res.success) {
                this.setData({
                  caseList: res.datalist
                })
              }
            })
    },
    toWorkCase(e) {
        let id = e.currentTarget.dataset.id
        wx.navigateTo({
            url: '/pages/work-case/work-case?id=' + id,
        })
    },
    onReachBottom: function() {
      this.setData({
        
      })
    },
    onShareAppMessage: function() {

    }
})