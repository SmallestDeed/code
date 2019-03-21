let myForEach = getApp().myForEach,
    API = getApp().API,
    socket = null
let $App = getApp();
Page({
    data: {
        staticImageUrl: $App.staticImageUrl,
        resourcePath: getApp().resourcePath,
        feedbackDetail:{},
        resPath: [],
        feedbackId: '',
        showPushPj:false,
    },
    onLoad: function (options) {
      wx.hideShareMenu();
        this.data.feedbackId = options.id;
        this.getFeedbackDetail(options.id);
    },
    getFeedbackDetail(id){
        API.getFeedbackDetail({ feedbackId: id}).then(res=>{
            let flag = res.success && res.obj
            let resPath = [];
            myForEach(res.obj.feedbackPicPath, (v) => {
                resPath.push(this.data.resourcePath + v.resPath);
            })
            let flag2 = res.obj.hasOwnProperty('estimate')?false:true;
            this.setData({
                feedbackDetail: flag ? res.obj:{},
                resPath: resPath,
                showPushPj: flag2,
            })
            
        })
    },
    showBigImg(e){
        let i = e.currentTarget.dataset.index;
        let urls = this.data.resPath
        wx.previewImage({
            urls: urls,
            current: urls[i]
        })
    },
    pushPj(e){
        let tys = parseInt(e.currentTarget.dataset.tys);
        let data = {
            id: this.data.feedbackId,
            estimate: tys,
        }
        API.feedbackEstimate(data).then((res)=>{
            if (res.success){
                this.setData({
                    showPushPj: false,
                    showToasts: true,
                })
                setTimeout((res=>{
                    this.setData({
                        showToasts: false,
                    })
                }),500)

            }
        })
    },
    onShareAppMessage: function () {

    }
})