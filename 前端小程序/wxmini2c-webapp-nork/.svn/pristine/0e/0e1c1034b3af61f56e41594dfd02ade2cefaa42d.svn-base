// pages/house-case/house-case.js
let $App = getApp(),
    fetch = getApp().fetch,
    myForEach = getApp().myForEach,
    mySplitUrl = getApp().mySplitUrl,
    myCompoundUrl = getApp().myCompoundUrl,
    API = getApp().API;
import {
    emptyTemplate
} from '../../component/emptyTemplate/emptyTemplate'
var WxParse = require('../../utils/wxParse/wxParse.js');
var {
    shareTitle
} = require('../../utils/config.js');
var {
    title
} = require('../../utils/config.js');
var shareTitle = {
    shareTitle
}.shareTitle;
var title = {
    title
}.title ? {
    title
}.title : false;
let bmap = require('../../lib/es6-promise/bmap-wx.min.js');
import {
    appid
} from '../../utils/config.js';
Page({
    emptyTemplate, // 无数据组件
    /**
     * 页面的初始数据
     */
    data: {
        typeName: '特卖商品',
        typeId: 1,
        typeImg: getApp().staticImageUrl + 'home_icon_sale.png',
        imageArray: [],
        issBindingMobile: wx.getStorageSync('bindingPhone'),
        staticImageUrl: $App.staticImageUrl,
        spaceList: [],
        areaList: [],
        styleList: [],
        skipPath: [],
        inputFlag: false,
        inputFlag2: false,
        isNull: true,
        presellList: [],
        click: false,
        getObj: {
            phone: '',
            code: ''
        },
        cid: '',
        favoriteRequest: true,
        collectRequest: true,
        conditionActive: -1,
        childConditionActive: [0, -1, -1],
        recommendCaseList: [],
        resourcePath: getApp().resourcePath,
        sevenUrl: getApp().sevenUrl,
        pageSize: 5,
        message: "获取验证码",
        getCaseParams: {
            spaceType: '',
            designPlanStyleId: '',
            spaceArea: ''
        },

        couponList: [],
        totalCount: 0,
        caseListheight: '',
        caseListOverflow: 'none',
        swiperCurrent: 0,
        brandImg: '',
        hideFlag: false,
        specialSaleList: [],
        specialOfferlList: [],
        options: '',
        article: '',
        isNk: false,
        getPhone: false,
        userId: wx.getStorageSync("userId"),
        thisFlag: false,
        companyId: '', //wx.getStorageSync("companyId")
        businessType: '',
        formId: [],
        formIndex: 0,
        newGoodsInfo: '',
        hotGoodsInfo: '',
        bannerInfo: '',
        appid: appid,
        planNum: 1,
        planCount: ''
    },

    getCompanyId: function () {
        let url = '/product/baseCompany/getCompanyId',
            that = this
        fetch(url, 'get').then((res) => {
            if (res.obj) {
                that.setData({
                    companyId: res.obj
                })
                that.showBrand();
                wx.setStorageSync('companyId', res.obj)

            }
        })
    },
    /**
     * 生命周期函数--监听页面加载
     */
    toUrl: function (e) {

        wx.navigateTo({
            url: e.currentTarget.dataset.url,
        })
    },
    unreceive: function () {
        wx.showToast({
            title: '已领取过',
            icon: 'none',
            duration: 800
        })
    },
    receive: function (e) {
        let url = '/v1/sandu/mini/activity/receive';
        let that = this;
        let data = {
            userId: this.data.userId,
            couponId: e.currentTarget.dataset.id,
        }
        fetch(url, 'get', data, 'shop').then((res) => {
            if (res.code == 200) {
                wx.showToast({
                    title: res.message,
                    icon: 'success',
                })
                that.getCouponList()
            } else {
                wx.showToast({
                    title: res.message,
                    icon: 'none'
                })
            }
        })
    },
    getCouponList: function () {
        let url = 'v1/sandu/mini/activity/getIndexCoupon'; //getWaitingReceiveList
        let that = this;
        let data = {
            companyId: this.data.companyId,
            userId: this.data.userId,
        }
        fetch(url, 'get', data, 'shop').then((e) => {
            let list = [];
            let maxL = e.data.length > 10 ? 10 : e.data.length;
            for (let i = 0; i < maxL; i++) {
                list[i] = e.data[i];
            }
            this.setData({
                couponList: list,
            })
        })

    },
    getConfig() {

        let url = '/core/company/dashboardconfig/' + this.data.appid,
            that = this
        fetch(url, 'get', '', 'core').then((res) => {
            if (!res.obj) {
                this.getRecommendCaseList();
            }
            if (res.obj) {
                if (res.obj.configMap.bana) {
                    that.setData({
                        bannerInfo: res.obj.configMap.bana
                    })
                }
                if (res.obj.configMap.baokuan) {
                    that.setData({
                        hotGoodsInfo: res.obj.configMap.baokuan
                    })
                }
                if (res.obj.configMap.newkuan) {
                    that.setData({
                        newGoodsInfo: res.obj.configMap.newkuan
                    })
                }

                if (res.obj.configMap.plan) {
                    console.log(this.data.recommendCaseList, '987654')
                    let list = []
                    for (let i = 0; i < res.obj.configMap.plan.configDetails.length; i++) {
                        list.push(res.obj.configMap.plan.configDetails[i].id)
                    }
                    let url = `fullsearch-app/all/recommendationplan/search/mini/queryByIds`
                    let data = {};
                    data = {
                        "recommendationPlanSearchVo": {
                            "displayType": "decorate",
                            "recommendedIds": list
                        },
                        "pageVo": {
                            "start": 0,
                            "pageSize": 20
                        }
                    }
                    fetch(url, 'post', data, 'fullsearch')
                        .then(res => {
                            if (res.success) {
                                if (res.datalist) {
                                    this.setData({ // setData底层实现的时候，会导致数据变化而页面渲染不出来，所以先置空 勿删
                                        recommendCaseList: []
                                    })
                                    this.setData({
                                        recommendCaseList: res.datalist,
                                        planCount: res.totalCount
                                    })
                                    console.log(this.data.recommendCaseList, '9876543')
                                } else {
                                    this.setData({
                                        recommendCaseList: [],
                                    })
                                }
                            } else {
                                this.setData({
                                    recommendCaseList: [],
                                })
                            }
                        })
                }

            }
        })
    },
    getBrand() {
        let url = '/core/company/branddesc/' + this.data.appid + '/N',
            that = this
        fetch(url, 'get', '', 'core').then((res) => {
            if (res.obj) {
                that.setData({
                    brandInfo: res.obj
                })
            }
        })

    },

    getRecommendCaseList() { // 获取方案列表
        let url = `fullsearch-app/all/recommendationplan/search/mini/list`
        let data = {};
        data = {
            "recommendationPlanSearchVo": {
                "displayType": "decorate",
            },
            "pageVo": {
                "start": 0,
                "pageSize": 5
            }
        }
        fetch(url, 'post', data, 'fullsearch')
            .then(res => {
                if (res.success) {
                    if (res.datalist) {
                        this.setData({
                            recommendCaseList: res.datalist,
                            totalCount: res.totalCount
                        })
                    } else {
                        this.setData({
                            recommendCaseList: [],
                            totalCount: 0
                        })
                    }
                } else {
                    this.setData({
                        recommendCaseList: [],
                        totalCount: 0
                    })
                }
            })
    },




    onLoad: function (e) {
        $App.userLoginStatus.then(() => {
            this.commonMs(e)
        })
    },
    isComeFromCode(options) { // 来源于二维码
        console.log(options, 'isComeFromCode')
        if (options.scene) {
            let scene = options.scene
            if (scene.includes('i') && scene.includes('t') && scene.includes('b') && scene.includes('s')) {
                this.comeFromGrass(options)
            } else {
                this.posterSharePanorama(options)
            }
        }
    },
    posterSharePanorama(options) {
        console.log(options.scene, "111111111")
        if (options.scene) {
            fetch('/union/sxw/getJsonData', 'get', {
                id: options.scene
            }, 'unionapi').then((res) => {
                getApp().data.cutpriceUrl = myCompoundUrl(JSON.parse(res.obj.jsonData))
                wx.navigateTo({
                    url: '/pages/cutprice/cutprice?isposter=1'
                })
            })
        }
    },
    comeFromGrass(options) { // 来源于通用版版本的二维码
        let scene = decodeURIComponent(options.scene),
            sceneObj = mySplitUrl(scene)
        getApp().data.webUrl = myCompoundUrl({
            url: getApp().grassSevenUrl,
            renderId: sceneObj.i,
            sceneType: sceneObj.t,
            planSourceType: sceneObj.s || '',
            hasChanged: sceneObj.b || '',
            qrCodeType: sceneObj.c || 0,
            customReferer: getApp().wxUrl,
            platformCode: 'miniProgram',
            platformNewCode: 'miniProgram',
            isRender: 0
        })
        wx.navigateTo({
            url: '/pages/web-720/web-720',
        })
    },

    commonMs(e) {
        if (e.navToUrl) {

            wx.showLoading({
                title: '跳转中...'
            })
            setTimeout(function () {
                wx.hideLoading();
                wx.navigateTo({
                    url: decodeURIComponent(e.navToUrl)
                })


            }, 1000)
        }
        // 砍价活动跳转
        if (e.actId) {
            this.toCutprice(e)
        }
        if (decodeURIComponent(e.scene).split(",")[0] == 'u_c_p') {
            this.toVistingCard(e)
        } else {
            this.isComeFromCode(e) // 获取海报分享到砍价活动
        }

        // ---------
        let that = this;
        wx.setNavigationBarTitle({
            title: shareTitle,
        })
        let mobileTimer = setInterval(() => {
            if (wx.getStorageSync('token')) {
                this.isBindMobile()
                clearInterval(mobileTimer)
            }
        }, 100)
        if (shareTitle == '诺克照明') {
            this.setData({
                typeName: '工厂直供',
                typeId: 0,
                typeImg: getApp().staticImageUrl + 'home_icon_factory.png',
                isNk: true,
            })
        }
        let options = e ? e : this.data.options;
        this.setData({
            options: options,
        })


        if (options.item) { // 720
            let item = Object.assign(JSON.parse(options.item), {
                token: wx.getStorageSync('token')
            })
            item.url = decodeURIComponent(item.url)
            getApp().data.webUrl = myCompoundUrl(item)
            wx.navigateTo({
                url: '/pages/web-720/web-720',
            })
        } else if (options.url) { // 视频
            wx.navigateTo({
                url: '/pages/video/video?url=' + options.url,
            })
        }
        wx.getSystemInfo({
            success: function (res) {
                that.setData({
                    windowHeight: res.windowHeight
                })
            },
        })



        //new this.emptyTemplate() // 注册组件
        setTimeout(function () {}, 800)

        //this.getCouponList();
        if (e.isAssemble) { //拼团跳到商品详情
            this.toGoodsDetail(e)
        }
    },
    toVistingCard(e) {
        console.log(e, '123')
        let array = decodeURIComponent(e.scene).split(",");
        console.log(array, '456')
        let userId = array[1],
            userCardId = array[2],
            type = array[3]
        wx.navigateTo({
            url: '/pages/vistingCard/vistingCard?createUser=' + userId + '&userCardId=' + userCardId + '&shareType=' + type
        })
    },
    linkTo(e) {
        let url = e.currentTarget.dataset.url;
        if (url == '/pages/house-goods/house-goods' || url == '/pages/index/index' || url == '/pages/purchase-car/purchase-car' || url == '/pages/personal-center/personal-center') {
            wx.switchTab({
                url: url,
            })
        } else {
            wx.navigateTo({
                url: url,
            })
        }

    },
    scroll(e) {
        this.setData({
            planNum: e.detail.current + 1
        })
    },
    toGoods(e) {
        wx.navigateTo({
            url: '/pages/goods-details/goods-details?id=' + e.currentTarget.dataset.id,
        })
    },

    toActive(e) {
        let type = e.currentTarget.dataset.type;
        if (type == '1') {
            wx.navigateTo({
                url: '/pages/activeKj/activeKj',
            })
        } else if (type == '2') {
            wx.navigateTo({
                url: '/pages/activePt/activePt',
            })
        }
    },
    isBindMobile() {
        fetch(`/v2/user/center/isUserMobileBinded`, 'get', {}, 'user')
            .then((res) => {
                this.setData({
                    thisFlag: res.success ? res.obj : false,
                    click: res.success ? res.obj : false,
                })
                wx.setStorage({
                    key: 'bindingPhone',
                    data: res.success ? res.obj : false,
                })
            })
    },
    posterSharePanorama(options) {
        if (options.scene) {
            fetch('/union/sxw/getJsonData', 'get', {
                id: options.scene
            }, 'unionapi').then((res) => {
                getApp().data.cutpriceUrl = myCompoundUrl(JSON.parse(res.obj.jsonData))
                wx.navigateTo({
                    url: '/pages/cutprice/cutprice'
                })
            })
        }
    },
    toCutprice(e) {
        wx.showLoading({
            title: '跳转中...'
        })
        setTimeout(function () {
            wx.hideLoading();
            wx.navigateTo({
                url: "/pages/cutprice/cutprice?actId=" + e.actId + "&regId=" + e.regId + "&isUser=" + e.isUser + "&cutPrice=&userId=" + e.userId,
            })
        }, 1000)
    },
    toGoodsDetail(e) {
        wx.navigateTo({
            url: `/pages/goods-details/goods-details?id=${e.id}&purchaseOpenId=${e.purchaseOpenId}&groupId=${e.groupId}&isAssemble=${e.isAssemble}`
        })
    },
    onPageScroll(e) {
        if (!wx.getStorageSync('bindingPhone')) {
            e.scrollTop > this.data.windowHeight && !this.data.click && !this.data.thisFlag ? this.setData({
                getPhone: true
            }) : "";
        }

    },
    getpresellList: function () {
        let url2 = '/mainpage/presellGoods',
            that = this
        fetch(url2, 'get', {
            companyId: that.data.companyId
        }).then((e) => {
            if (e.obj) {
                that.setData({
                    presellList: e.obj,
                })
            }
        })
    },
    getspecialOfferlList: function () {
        let url2 = '/mainpage/specialOffer',
            that = this
        fetch(url2, 'get', {
            companyId: that.data.companyId
        }).then((e) => {
            if (e.obj) {
                that.setData({
                    specialOfferlList: e.obj,
                })
            }
        })
    },
    getSpecialSale: function () {
        let url = shareTitle == '诺克照明' ? '/goods/basegoods/getSpecialSale' : '/mainpage/specialOffer'
        let that = this;
        let data = shareTitle == '诺克照明' ? {
            type: 0
        } : {
            companyId: that.data.companyId
        };
        fetch(url, 'get', data).then((res) => {
            if (res.obj) {
                that.setData({
                    specialSaleList: res.obj,
                })
            }
        })
    },
    showBannerImg() {
        let url2 = '/v1/base/banner/web/miniprogram/list',
            that = this,
            data = {
                companyId: this.data.companyId,
                positionCode: 'company:home:page:top'
            }
        fetch(url2, 'get', data, 'system').then((res) => {
            let imgArr = [],
                skipArr = [],
                img = res.datalist
            for (let i = 0; i < img.length; i++) {
                imgArr.push(img[i].picPath);
                skipArr.push(img[i].skipPath)
            }
            that.setData({
                imageArray: imgArr,
                skipPath: skipArr
            })
        })
    },
    showBrand: function () {
        let that = this,
            url2 = '/mainpage/company';
        fetch(url2, 'get', {
            companyId: that.data.companyId
        }).then((ress) => {
            if (ress.obj) {
                that.setData({
                    businessType: ress.obj.businessType,
                })
                wx.setStorage({
                    key: 'businessType',
                    data: ress.obj.businessType
                });
            }
        })
    },
    onShow: function () {
        this.setData({
            planNum: 1
        })
        this.getCouponList()

        let that = this;
        let caseListTimer = setInterval(() => {
            if (wx.getStorageSync('token')) {
                clearInterval(caseListTimer)

            }
        }, 100)
        // that.setData({
        //     // issBindingMobile: wx.getStorageSync('bindingPhone'),
        //     click: wx.getStorageSync('bindingPhone')?true:false,
        // })
        this.getConfig();
        this.getCompanyId();
        this.getBrand()
    },

    onShareAppMessage: function (res) {
        if (res.from === 'menu') {
            return $App.shareAppMessageFn();
        }
    },
    enlargeImage(url) { // 查看大图
        wx.previewImage({
            current: url, // 当前显示图片的http链接
            urls: [url] // 需要预览的图片http链接列表
        })
    },
    formToThreeD(e) { // 720
        let type = e.detail.target.dataset.type,
            item = e.detail.target.dataset.item,
            routerQueryType = '',
            formId = e.detail.formId
        if (type === '720' || type === 'roam') {
            let routerQueryType = ''
            if (type === '720') {
                routerQueryType = 'seven'
            } else {
                routerQueryType = 'roam'
            }
            let sevenObj = {
                token: wx.getStorageSync('token'),
                platformCode: 'brand2c',
                operationSource: 1,
                planId: item.designPlanRecommendId,
                routerQueryType: routerQueryType,
                customReferer: $App.wxUrl,
                platformNewCode: 'miniProgram',
                formId: formId
            }
            let webUrl = this.data.sevenUrl
            for (let key in sevenObj) {
                webUrl += key + '=' + sevenObj[key] + '&'
            }
            getApp().data.webUrl = webUrl
            $App.myNavigateBack('pages/web-720/web-720')
            return
        }
    },
    toThreeD(e) { // 调转到3D界面
        let type = e.currentTarget.dataset.type,
            item = e.currentTarget.dataset.item
        let url = `/mobile/designPlanRecommended/getRecommendedPictureList.htm`
        fetch(url, 'post', {
                planRecommendedId: item.designPlanRecommendId,
                remark: type
            }, 'render')
            .then(res => {
                if (res.success) {
                    if (type == 'photo') {
                        return res.datalist[0].pid
                    } else {
                        return res.datalist[0].id
                    }
                } else {
                    return false
                }
            })
            .then(res => {
                if (res) {
                    let url = `/mobile/design/designPlan/getPanoPicture.htm`
                    fetch(url, 'post', {
                            thumbId: res
                        }, 'render')
                        .then(res => {
                            if (res.success) {
                                if (type == 'photo') {
                                    this.enlargeImage(res.obj.url)
                                } else if (type == 'video') {
                                    this.toVideo(res.obj.url)
                                }
                            } else {
                                wx.showToast({
                                    title: '打开失败',
                                    icon: 'none'
                                })
                            }
                        })
                }
            })
        // getApp().data.webUrl = 'https://zuoyou.m.sanduspace.com'
        // wx.navigateTo({
        //   url: '../web-720/web-720',
        // })
    },


    closeCaseCondition() {
        this.setData({
            conditionActive: -1
        })
        if (this.data.conditionActive === -1) {
            this.setData({
                caseListheight: '',
                caseListOverflow: 'none'
            })
        } else {
            let caseListheight = wx.getSystemInfoSync().windowHeight
            this.setData({
                caseListheight: caseListheight + 'px',
                caseListOverflow: 'hidden'
            })
        }
    },

    toVideo(url) {
        wx.navigateTo({
            url: '../video/video?url=' + url
        })
    },
    goWeiGuang(e) {
        let num = e.currentTarget.dataset.num;
        let isIndex = false;
        let str = ['/pages/index/index', '/pages/house-case/house-case', '/pages/house-goods/house-goods', '/pages/house-type/house-type', '/pages/personal-center/personal-center'];
        for (let i = 0; i < str.length; i++) {
            if (this.data.skipPath[num] == str[i]) {
                isIndex = true;
            }
        }

        // wx.navigateTo({ url: '/pages/special-sales/special-sales' })
        // return;
        isIndex ? wx.switchTab({
            url: this.data.skipPath[num]
        }) : wx.navigateTo({
            url: this.data.skipPath[num]
        });
    },
    collectCase(e) { // 方案收藏
        var that = this
        if (that.data.collectRequest == true) {
            that.setData({
                collectRequest: false
            })
            let url = `/designplanfavorite/setFavoriteOrUnfavorite`,
                item = e.currentTarget.dataset.item,
                index = e.currentTarget.dataset.index,
                status = null,
                title = '收藏'
            if (item.isFavorite) {
                status = 0
            } else {
                status = 1
            }
            status == 0 ? title = '取消收藏' : '收藏'
            fetch(url, 'post', {
                    status: status,
                    recommendId: item.designPlanRecommendId
                })
                .then((res) => {

                    if (res.success) {
                        this.data.recommendCaseList[index].isFavorite = status
                        status == 0 ? this.data.recommendCaseList[index].collectNum -= 1 : this.data.recommendCaseList[index].collectNum += 1
                        this.setData({
                            recommendCaseList: this.data.recommendCaseList
                        })
                        wx.showToast({
                            title: title + '成功'
                        })
                        setTimeout(function () {
                            that.setData({
                                collectRequest: true
                            })
                        }, 500)

                    } else {
                        wx.showToast({
                            title: '收藏失败',
                            icon: 'none'
                        })
                        setTimeout(function () {
                            that.setData({
                                collectRequest: true
                            })
                        }, 500)
                    }
                })
        } else {
            return
        }

    },
    likeCase(e) { // 方案点赞
        var that = this;
        if (that.data.favoriteRequest == true) {
            that.setData({
                favoriteRequest: false
            })
            let url = `/designPlanLike/setLikeOrDislike`,
                item = e.currentTarget.dataset.item,
                index = e.currentTarget.dataset.index,
                status = null,
                title = '点赞'
            if (item.isLike) {
                status = 0
            } else {
                status = 1
            }
            status == 0 ? title = '取消点赞' : '点赞'
            fetch(url, 'post', {
                    status: status,
                    designId: item.designPlanRecommendId
                })
                .then((res) => {
                    if (res.success) {
                        this.data.recommendCaseList[index].isLike = status
                        status == 0 ? this.data.recommendCaseList[index].likeNum -= 1 : this.data.recommendCaseList[index].likeNum += 1
                        this.setData({
                            recommendCaseList: this.data.recommendCaseList
                        })
                        wx.showToast({
                            title: title + '成功'
                        })
                        setTimeout(function () {
                            that.setData({
                                favoriteRequest: true
                            })
                        }, 500)
                    } else {
                        wx.showToast({
                            title: title + '失败',
                            icon: 'none'
                        })
                        setTimeout(function () {
                            that.setData({
                                favoriteRequest: true
                            })
                        }, 500)
                    }
                })
        } else {
            return;
        }
    },
    getFormId(e) {
        this.data.formIndex++;
        this.data.formId.push(e.detail.formId);
        this.setData({
            formId: this.data.formId,
            formIndex: this.data.formIndex
        })
        if (this.data.formIndex >= 5) {
            setTimeout(() => {
                let num = e.detail.target.dataset.num;
                let isIndex = false;
                let str = ['/pages/index/index', '/pages/house-case/house-case', '/pages/house-goods/house-goods', '/pages/house-type/house-type', '/pages/personal-center/personal-center'];
                for (let i = 0; i < str.length; i++) {
                    if (this.data.skipPath[num] == str[i]) {
                        isIndex = true;
                    }
                }

                // wx.navigateTo({ url: '/pages/special-sales/special-sales' })
                // return;

                API.collectMiniUserFormId(this.data.formId).then(res => {
                    this.setData({
                        formIndex: 0,
                        formId: []
                    })
                })
                isIndex ? wx.switchTab({
                    url: this.data.skipPath[num]
                }) : wx.navigateTo({
                    url: this.data.skipPath[num]
                });
            }, 0)
        }
    },
    initFormId(e) {
        return new Promise((reslove, rejecr) => {
            this.data.formId.push(e.detail.formId);
            this.setData({
                formId: this.data.formId
            })
        })
    },
    collectCase(e) { // 方案收藏
        var that = this
        if (that.data.collectRequest == true) {
            that.setData({
                collectRequest: false
            })
            let url = `/designplanfavorite/setFavoriteOrUnfavorite`,
                item = e.currentTarget.dataset.item,
                index = e.currentTarget.dataset.index,
                status = null,
                title = '收藏'
            if (item.isFavorite) {
                status = 0
            } else {
                status = 1
            }
            status == 0 ? title = '取消收藏' : '收藏'
            fetch(url, 'post', {
                    status: status,
                    recommendId: item.designPlanRecommendId
                })
                .then((res) => {

                    if (res.success) {
                        this.data.recommendCaseList[index].isFavorite = status
                        status == 0 ? this.data.recommendCaseList[index].collectNum -= 1 : this.data.recommendCaseList[index].collectNum += 1
                        this.setData({
                            recommendCaseList: this.data.recommendCaseList
                        })
                        wx.showToast({
                            title: title + '成功'
                        })
                        setTimeout(function () {
                            that.setData({
                                collectRequest: true
                            })
                        }, 500)

                    } else {
                        wx.showToast({
                            title: '收藏失败',
                            icon: 'none'
                        })
                        setTimeout(function () {
                            that.setData({
                                collectRequest: true
                            })
                        }, 500)
                    }
                })
        } else {
            return
        }

    },
    likeCase(e) { // 方案点赞
        var that = this;
        if (that.data.favoriteRequest == true) {
            that.setData({
                favoriteRequest: false
            })
            let url = `/designPlanLike/setLikeOrDislike`,
                item = e.currentTarget.dataset.item,
                index = e.currentTarget.dataset.index,
                status = null,
                title = '点赞'
            if (item.isLike) {
                status = 0
            } else {
                status = 1
            }
            status == 0 ? title = '取消点赞' : '点赞'
            fetch(url, 'post', {
                    status: status,
                    designId: item.designPlanRecommendId
                })
                .then((res) => {
                    if (res.success) {
                        this.data.recommendCaseList[index].isLike = status
                        status == 0 ? this.data.recommendCaseList[index].likeNum -= 1 : this.data.recommendCaseList[index].likeNum += 1
                        this.setData({
                            recommendCaseList: this.data.recommendCaseList
                        })
                        wx.showToast({
                            title: title + '成功'
                        })
                        setTimeout(function () {
                            that.setData({
                                favoriteRequest: true
                            })
                        }, 500)
                    } else {
                        wx.showToast({
                            title: title + '失败',
                            icon: 'none'
                        })
                        setTimeout(function () {
                            that.setData({
                                favoriteRequest: true
                            })
                        }, 500)
                    }
                })
        } else {
            return;
        }
    },
    gotoPge(e) {
        var id = e.currentTarget.dataset.url,
            type = e.currentTarget.dataset.type

        if (id == 1) {
            wx.navigateTo({
                url: '/pages/goods-list/goods-list?type=' + type + '&code=root'
            })
        } else if (id == 0) {
            wx.navigateTo({
                url: '/pages/special-sales/special-sales',
            })
        } else {
            wx.switchTab({
                url: '../house-case/house-case'
            })
        }


    },
    toPage(e) {
        wx.navigateTo({
            url: '../goods-details/goods-details?id=' + e.currentTarget.dataset.id
        })
    },
    putInMyhouse(e) { // 装进我家
        // this.renderTypeShow() // 显示组件
        let item = e.currentTarget.dataset.item
        let _that = this;
        getApp().fetch(`/v2/user/center/checkUserSecondRender`, 'get', {},
            'user').then((res) => {
            if (res.obj) {
                getApp().fetch(`/v2/user/center/isUserMobileBinded`, 'get', {}, 'user')
                    .then((e) => {
                        let flags = e.success ? !e.obj : true
                        _that.setData({
                            getPhone: flags
                        })
                        wx.setStorage({
                            key: 'bindingPhone',
                            data: !flags,
                        });
                        !flags ? _that.toPutInMyhouse(item) : '';
                    })

            } else {
                _that.toPutInMyhouse(item)
            }
        })


    },
    toPutInMyhouse(item) {
        wx.setStorageSync('caseItem', item)
        wx.navigateTo({
            url: '../case-house-type/case-house-type'
        })
    },
    closeFun() {
        this.setData({
            getPhone: !this.data.getPhone,
            click: true,
        })
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
        if (!phone && !(/^1[3|4|5|8|9][0-9]\d{8}$/.test(phone))) {
            wx.showToast({
                title: '请正确填写电话!',
                icon: 'none'
            })
            return;
        }
        let url = `/v1/center/getSms`
        fetch(url, 'formData', {
                phoneNumber: phone,
                msgId: 12
            }, 'user')
            .then((res) => {
                if (res.success) {
                    let num = 60
                    that.setData({
                        inputFlag2: true,
                        message: num + 's',
                    })
                    let setFunc = setInterval(function () {
                        num--;
                        that.setData({
                            message: num + 's',
                        })
                        if (num <= 0) {
                            clearInterval(setFunc);
                            that.setData({
                                message: '获取验证码',
                            })
                        }
                    }, 1000)
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
        fetch(url, 'formData', {
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
                    getPhone: false,
                    click: true,
                })
                wx.setStorageSync('isGetPhone', that.data.getObj.phone)
            } else {
                wx.showToast({
                    title: res.message,
                    icon: 'none'
                })
            }
        })
    },

    routerToCaseDetails(e) {
        let id = e.currentTarget.dataset.id,
            type = e.currentTarget.dataset.type
        wx.navigateTo({
            url: `/pages/case-details/case-details?id=${id}&type=${type || 0}`
        })
    },
    onPullDownRefresh() {
        wx.stopPullDownRefresh();
    }
})