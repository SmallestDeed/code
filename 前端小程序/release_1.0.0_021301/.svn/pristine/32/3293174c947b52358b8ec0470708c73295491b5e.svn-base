let API = getApp().API,
    myForEach = getApp().myForEach,
    $App = getApp();
Page({
    data: {
        staticImageUrl: $App.staticImageUrl,
        stars: 5,
        unStars: 0,
        starsObj: [],
        unStarsObj: [],
        chexd: false,
        imgList:[],
        imgWidth: 0,
        imgHeight: 0,
        imgId:[],
        remark:'',
        taskId: 0,
        id: 0,
        companyName:'',
        companyId:'',
    },

    onLoad: function(options) {
      wx.hideShareMenu();
        let arr1 = new Array(this.data.stars),
            arr2 = new Array(this.data.unStars);
        this.setData({
            starsObj: arr1,
            unStarsObj: arr2,
            taskId: options.taskId,
            id: options.id,
        })
        this.getConpanyInfo(options.taskId);
    },
    changeInput(e) {
        this.setData({
            remark: e.detail.value.trim(),
        })
    },
    deleteImg(e){
        let i = e.currentTarget.dataset.i;
        let imgList = this.data.imgList;
        imgList.splice(i, 1)
        this.setData({
            imgList: imgList
        })
    },
    upImg(e){
        let that = this;
        if (this.data.imgList.length<3){
            wx.chooseImage({
                count: 1,
                success: function (res) {
                    let imgList = that.data.imgList;
                    API.uploadEvaluate({
                        'path':  res.tempFilePaths[0],
                        'type':  'image',
                        'filekey':  'message.comment',
                        'platform':  'brand2c'
                    }).then(res => {
                        let obj = {
                            'path': res.obj.url,
                            'id': res.obj.resId
                        }
                        imgList.unshift(obj)
                        that.setData({
                            'imgList': imgList,
                        })
                    })
                },
            })
        }else{
            wx.showToast({
                title: '最多上传3张!!!',
                icon:'none'
            })
        }
    },
    changeNum(e){
        let i = e.currentTarget.dataset.i + 1,
            t = e.currentTarget.dataset.t,
            arr1 = new Array(t=='1' ? i : this.data.stars + i),
            arr2 = new Array(5 - arr1.length);
        this.setData({
            stars: arr1.length,
            unStars: arr2.length,
            starsObj: arr1,
            unStarsObj: arr2
        })
    },
    getConpanyInfo(id){
        let that = this;
        API.getCompanyEvaluate({
            'taskId': id
        }).then(res => {
            if(res.obj){
                that.setData({
                    companyName: res.obj.companyName,
                    companyId: res.obj.companyId,
                })
            }
        })
    },
    submitFun(){
        let idStr = '';
        let imgList = this.data.imgList;
        for (let i = 0; i < imgList.length;i++){
            idStr += i < (imgList.length - 1) ? this.data.imgList[i].id + ',' : this.data.imgList[i].id;
        }
        let data = {
            "companyId": this.data.companyId,
            "isAnonymous": this.data.chexd?0:1,
            "messageId": this.data.id,
            "orderId": this.data.taskId,
            "picIds": idStr,
            "remark": this.data.remark,
            "score": this.data.stars
        }
        if (!this.data.remark || this.data.remark === 'undefined'){
            wx.showToast({
                title: '请输入你的评价!',
                icon: 'none'
            })
            return;
        }
        API.commentEvaluate(data).then(res=> {
            if(res.status){
                wx.showToast({
                    title: '评论成功',
                    icon:'none'
                })
                setTimeout(function(){
                    wx.navigateBack({
                      delta: 2
                    })
                },300)
            }else{
                wx.showToast({
                    title: '请输入有效评价!!',
                    icon: 'none'
                })
            }
        })
    },
    chexd(e){
        this.setData({
            chexd: !this.data.chexd
        })
    },
    onShareAppMessage: function() {

    }
})