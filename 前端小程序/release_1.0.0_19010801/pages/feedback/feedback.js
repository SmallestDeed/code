let myForEach = getApp().myForEach,
    API = getApp().API,
    socket = null
let $App = getApp();
Page({
    data: {
        staticImageUrl: $App.staticImageUrl,
        num: 0,
        conts: '',
        phone: '',
        imgList: [],
        showToasts:false,
        havePhone: true,
    },

    onLoad: function(options) {
      wx.hideShareMenu();
    },
    deleteImg(e) {
        let i = e.currentTarget.dataset.i;
        let imgList = this.data.imgList;
        imgList.splice(i, 1)
        this.setData({
            imgList: imgList
        })
    },
    uploadEvaluate(e) {
        let imgList = this.data.imgList;
        API.uploadEvaluate({
            'path': e,
            'type': 'image',
            'filekey': 'message.comment',
            'platform': 'brand2c'
        }).then(res => {
            let obj = {
                'path': res.obj.url,
                'id': res.obj.resId
            }
            imgList.unshift(obj)
            this.setData({
                imgList: imgList,
            })
        })
    },
    upImg(e) {
        let that = this;
        if (this.data.imgList.length < 4) {
            wx.chooseImage({
                count: 4 - this.data.imgList.length,
                success: function(res) {
                    myForEach(res.tempFilePaths, (v) => {
                        that.uploadEvaluate(v)
                    })
                },
            })
        } else {
            wx.showToast({
                title: '最多上传4张!!!',
                icon: 'none'
            })
        }
    },
    onShow: function() {
        this.getUserpHone();
    },
    contsInput(e) {
        let val = e.detail.value.trim();
        if ($App.isEmojiCharacter(val)){
            wx.showToast({
                title: '请勿输入表情！！',
                icon: 'none'
            })
            this.setData({
                num: this.data.num,
                conts: this.data.conts
            })
            return;
        }
        this.setData({
            num: val.length,
            conts: val
        })
    },
    getUserpHone(){
        let that = this;
        API.getUserMobile({ userId:wx.getStorageSync('id')}).then((res)=>{
            that.setData({
                phone: res.obj||'',
                havePhone: (res.success && res.obj)||false,
            })
        })
    },
    phoneInput(e) {
        let val = e.detail.value.trim()
        this.setData({
            phone: val
        })
    },
    submit(e) {
        console.log(e.detail.formId);
        let conts = this.data.conts,
            phone = this.data.phone
        if (!conts) {
            wx.showToast({
                title: '请输入反馈内容！',
                icon: 'none'
            })
            return;
        }
        if (phone && !(/^1[3|4|5|8|9][0-9]\d{8}$/.test(phone))) {
            wx.showToast({
                title: '请正确填写电话!',
                icon: 'none'
            })
            return;
        } else {
            this.successFun();
        }
    },
    feedback(data){
        API.feedback(data).then(res=>{
            console.log(res);
            if (res.success){
                this.setData({
                    showToasts: true,
                })
                setTimeout(() => {
                    this.setData({  showToasts: false, })
                    this.removeFuc();
                    this.toLisst();
                }, 2000)
            }
        })
    },
    removeFuc() {
        this.setData({
            num: 0,
            conts: '',
            phone: '',
            imgList: []
        })
    },
    successFun() {
        let that = this;
        let idStr = '';
        let imgList = this.data.imgList;
        for (let i = 0; i < imgList.length; i++) {
            idStr += i < (imgList.length - 1) ? this.data.imgList[i].id + ',' : this.data.imgList[i].id;
        }
       let data = {
           userId: wx.getStorageSync('id'),
           phone: this.data.phone||'',
           feedbackContent: this.data.conts,
           feedbackPics: idStr||''
       };
        this.feedback(data);
    },
    toLisst() {
        wx.navigateTo({
            url: '/pages/feedbackList/feedbackList',
        })
    },
    showImg(e) {
        let urls = [];
        myForEach(this.data.imgList, (v) => {
            urls.push(v.path)
        })
        let current = this.data.imgList[e.currentTarget.dataset.index].path
        wx.previewImage({
            urls: urls,
            current: current
        })
    },
    onReachBottom: function() {

    },

    onShareAppMessage: function() {

    }

})