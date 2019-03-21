// component/case-list/case-list.js
let myForEach = getApp().myForEach, mySplitUrl = getApp().mySplitUrl,
staticImageUrl = getApp().staticImageUrl,
    myCompoundUrl = getApp().myCompoundUrl, $App = getApp(), API = getApp().API, ttt = ''
Component({
    /**
     * 组件的属性列表
     */
    properties: {
        recommendCaseList: {
            type: Array,
            value: [],
        },
        border: {
            type: Boolean,
            value: false
        },
        isOneKeyToDecorate: {
            type: Boolean,
            value: false
        },
        nowCase: {
            type: Boolean,
            value: false
        },
        caseItem: {
            type: Boolean,
            value: false
        },
    },
    /**
     * 组件的初始数据
     */
    data: {
        resourcePath: getApp().resourcePath,
        collectRequest: true,
        favoriteRequest: true,
        decoratePriceBox: false,
        decoratePriceList: [],
        secondRender: false,
        staticImageUrl: staticImageUrl,
        inputFlag:false,
        inputFlag2:false,
        getPhone: false,
        message:'获取验证码',
        item: '',
        getObj: {
            phone: '',
            code: ''
        },
        planCostShow: false,
        havePurchased: 0,
        planPrice: 0,
        getCodeTimer: null
    },
    
    /**
     * 组件的方法列表
     */
    methods: {
        closePlanCost() {
            this.setData({
                planCostShow: false
            })
        },
        ShowplanCost(e) {
            let item = e.currentTarget.dataset.item;
            console.log(item);
            this.setData({
                planCostShow: true,
                havePurchased: item.havePurchased,
                planPrice: item.planPrice
            })
        },
        formToThreeD(e) {
            let type = e.detail.target.dataset.type, item = e.detail.target.dataset.item, routerQueryType = '',
                formId = e.detail.formId, webUrl = null, sevenObj = null
            type === '720' ? routerQueryType = 'seven' : routerQueryType = 'roam'
            item.fullHousePlanUUID ? (webUrl = $App.wholeHouseUrl, sevenObj = { uuid: item.fullHousePlanUUID, embedded: 1 }) :
                (webUrl = $App.sevenUrl, sevenObj = {
                    token: wx.getStorageSync('token'),
                    platformCode: 'brand2c',
                    operationSource: 1,
                    planId: item.designPlanRecommendId || item.planRecommendedId,
                    routerQueryType: routerQueryType,
                    customReferer: $App.wxUrl,
                    platformNewCode: 'miniProgram',
                    formId: formId
                })
            for (let key in sevenObj) { webUrl += key + '=' + sevenObj[key] + '&' }
            getApp().data.webUrl = webUrl
            $App.myNavigateBack('pages/web-720/web-720')
            console.log(webUrl)
        },
        toThreeD(e) { // 调转到3D界面
            let type = e.currentTarget.dataset.type, item = e.currentTarget.dataset.item, routerQueryType = null, webUrl = null, sevenObj = null
            if (type === 'video') {
                API.getRecommendedVideoId({
                    planRecommendedId: item.designPlanRecommendId || item.planRecommendedId,
                    remark: type
                })
                    .then(res => {
                        if (res.success) { return res.datalist[0].id } else { return false }
                    })
                    .then(res => {
                        if (res) {
                            API.getRecommendedVideoMessage({ thumbId: res })
                                .then(res => {
                                    res.success ? this.toVideo(res.obj.url) : wx.showToast({ title: '打开失败', icon: 'none' })
                                })
                        }
                    })
            } else {
                type === '720' ? routerQueryType = 'seven' : routerQueryType = 'roam'
                item.fullHousePlanUUID ? (webUrl = $App.wholeHouseUrl, sevenObj = { uuid: item.fullHousePlanUUID, embedded: 1 }) :
                    (webUrl = $App.sevenUrl, sevenObj = {
                        token: wx.getStorageSync('token'),
                        platformCode: 'brand2c',
                        operationSource: 1,
                        planId: item.designPlanRecommendId || item.planRecommendedId,
                        routerQueryType: routerQueryType,
                        customReferer: $App.wxUrl,
                        platformNewCode: 'miniProgram'
                    })
                for (let key in sevenObj) { webUrl += key + '=' + sevenObj[key] + '&' }
                getApp().data.webUrl = webUrl
                $App.myNavigateBack('pages/web-720/web-720')
                console.log(webUrl)
            }
        },
        collectCase(e) { // 方案收藏
            let index = e.currentTarget.dataset.index
            this.collectOrLikeCase({
                title: '收藏',
                flag: 'collectRequest',
                num: 'collectNum',
                status: 'isFavorite',
                api: 'collectCase',
                param: 'recommendId',
                index,
                designPlanType: 'designPlanType'
            })
        },
        likeCase(e) { // 方案点赞
            let index = e.currentTarget.dataset.index
            this.collectOrLikeCase({
                title: '点赞',
                flag: 'favoriteRequest',
                num: 'likeNum',
                status: 'isLike',
                api: 'likeCase',
                param: 'designId',
                index,
                designPlanType: 'designPlanType'
            })
        },
        showIcon: function (e) {
            if (!e.currentTarget.dataset.icon){
                wx.showToast({
                    title: '暂无图片明细！',
                    icon:'none'
                })
                return;
            }
            let icon = this.data.resourcePath + e.currentTarget.dataset.icon;
            wx.previewImage({
                urls: [icon],
            })
        },
        collectOrLikeCase(obj) {
            let that = this, status = null, title = null
            if (this.data[obj.flag] == true) {
                this.setData({ [obj.flag]: false })
                this.data.recommendCaseList[obj.index][obj.status] ? (status = 0, title = '取消' + obj.title) : (status = 1, title = obj.title)
                API[obj.api]({ 
                    status: status, 
                    [obj.param]: this.data.recommendCaseList[obj.index].designPlanRecommendId || this.data.recommendCaseList[obj.index].planRecommendedId,
                    [obj.designPlanType]: this.data.recommendCaseList[obj.index].spaceType==13?2:1
                     })
                    .then(res => {
                        if (res.success) {
                            status == 0 ? this.data.recommendCaseList[obj.index][obj.num] -= 1 : this.data.recommendCaseList[obj.index][obj.num] += 1
                            this.data.recommendCaseList[obj.index][obj.status] = status
                            this.setData({ recommendCaseList: this.data.recommendCaseList })
                            wx.showToast({ title: title + '成功' })
                        } else {
                            wx.showToast({ title: title + '失败', icon: 'none' })
                        }
                        setTimeout(function () { that.setData({ [obj.flag]: true }) }, 500)
                    })
            }
        },
        routerToCaseDetails(e) {
            let id = e.currentTarget.dataset.id, type = e.currentTarget.dataset.type
            wx.navigateTo({ url: `/pages/case-details/case-details?id=${id}&type=${type || 0}` })
        },
        putInMyhouse(e) { // 装进我家
            let _that = this, index = e.currentTarget.dataset.index 
            this.setData({
                item: Object.assign(e.currentTarget.dataset.item, { index: index }, { isCaseItem: this.properties.caseItem}),
            })
            if (wx.getStorageSync('bindingPhone')){
                _that.toPutInMyhouse()
                return;
            }
                
            getApp().fetch(`/v2/user/center/checkUserSecondRender`, 'get', {},
            'user') .then((res) => {
                if (res.obj){
                    getApp().fetch(`/v2/user/center/isUserMobileBinded`, 'get', {}, 'user')
                    .then((e) => {
                        let flags = e.success ? !e.obj : true
                        _that.setData({ getPhone: flags })
                        wx.setStorage({
                            key: 'bindingPhone',
                            data: !flags,
                        })
                        !flags ? _that.toPutInMyhouse():'';
                    })
                    
                } else {
                    _that.toPutInMyhouse()
                }
            })
        },
        toPutInMyhouse(){
            let item = this.data.item;
            if (this.data.isOneKeyToDecorate) {
                this.data.nowCase ? item.type = 'seven' : 'item'
                this.triggerEvent('putInMyhouse', item, { bubbles: true })
            } else {
                wx.setStorageSync('caseItem', item)
                wx.navigateTo({
                    url: '/pages/case-house-type/case-house-type'
                })
            }
        },
        showDecoratePriceBox(e) {
            let item = e.currentTarget.dataset.item, height = wx.getSystemInfoSync().windowHeight
            myForEach(item, (value) => {
                switch (value.decorateTypeName) {
                    case "半包": value.text = '辅材+人工费+管理费'; break;
                    case "清水": value.text = '人工费+管理费'; break;
                    case "全包": value.text = '主材+辅材+人工费+管理费'; break;
                    case "包软装": value.text = '主材+辅材+人工费+管理费+部分软装'; break;
                }
            })
            this.setData({ decoratePriceList: item, decoratePriceBox: true })
            this.triggerEvent('showDecoratePriceBox', true, { bubbles: true })
        },
        hideDecoratePriceBox() {
            this.setData({ decoratePriceBox: false })
            this.triggerEvent('showDecoratePriceBox', false, { bubbles: true })
        },
        toVideo(url) {
            wx.navigateTo({
                url: '/pages/video/video?url=' + url
            })
        },
        closeFun() {
            this.setData({
                getPhone: !this.data.getPhone,
                click: true,
                message:'获取验证码',
                getObj:[],
                inputFlag: false,
            })
            clearInterval(this.data.getCodeTimer);
            //this.toPutInMyhouse()
        },
        changeInput(e) {
            let key = 'getObj.' + e.target.dataset.type
            this.setData({
                [key]: e.detail.value.trim(),
            })
            if (e.target.dataset.type == 'phone' && e.detail.value.length >= 11) {
                this.setData({
                    inputFlag: true,
                })
            }
        },
        getCodeFun() {
            let phone = this.data.getObj.phone,
                that = this;
            if (!phone && !(/^1[3|4|5|6|7|8|9][0-9]\d{8}$/.test(phone))) {
                wx.showToast({
                    title: '请正确填写电话!',
                    icon: 'none'
                })
                return;
            }
          let url = `/v1/center/getSmsCode`
          getApp().fetch(url, 'formData', { phoneNumber: phone, functionType: 2 }, 'user')
                .then((res) => {
                    if (res.success) {
                        let num = 60
                        that.setData({
                            inputFlag2: true,
                            message: num + 's',
                        })
                        this.data.getCodeTimer = setInterval(function () {
                            num--;
                            that.setData({
                                message: num + 's',
                            })
                            if (num <=0) {
                                clearInterval(that.data.getCodeTimer);
                                that.setData({
                                    message: '获取验证码',
                                })
                            }
                        }, 1000)
                    }else{
                        wx.showToast({
                            title: res.message,
                            icon: 'none'
                        })
                    }
                })
        },
        submitFun() {
            let that = this,
                code = this.data.getObj.code;
            if (!code) {
                wx.showToast({
                    title: '请填写验证码',
                    icon: 'none'
                })
                return
            }
            let url = `/v2/user/center/bindUserMobile`
            getApp().fetch(url, 'formData', {
                mobile: that.data.getObj.phone,
                authCode: code
            }, 'user').then((res) => {
                if (res.success) {
                    wx.showToast({
                        title: '绑定成功'
                    })
                    wx.setStorage({
                        key: 'bindingPhone',
                        data: 1,
                    })
                    that.setData({
                        getPhone: false
                    })
                    //that.toPutInMyhouse()
                } else {
                    wx.showToast({
                        title: res.message,
                        icon: 'none'
                    })
                }
            })
        },
        toImg(e) {
            let curr = e.currentTarget.dataset.item;
            let imgList = curr.designPlanCoverPath ? curr.designPlanCoverPath : (curr.coverPath ? curr.coverPath:'')
            let imgArr = [this.data.resourcePath + imgList];
            if (!imgList){
                wx.showToast({
                    title: '图片不存在!',
                    icon: 'none'
                })
                return;
            }
            wx.previewImage({
                //当前显示下表
                current: imgArr[0],
                //数据源
                urls: imgArr
            })
        }
    }
})
