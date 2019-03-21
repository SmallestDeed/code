// pages/goods-list/goods-list.js
let fetch = getApp().fetch, myForEach = getApp().myForEach
import { renderTypeExample } from '../../component/render-type/render-type'
let $App = getApp()
let opt = {};
Page({
    renderTypeExample,

    /**
     * 页面的初始数据
     */
    data: {
        drop: true,
        houseId: 0,
        houseTypelist: [],
        spaceInHouse: [],
        space: [],
        spaceInedxName: "",
        spaceInedxType: "",
        spaceList: [],
        resourcePath: getApp().resourcePath,
        sevenUrl: getApp().sevenUrl,
        information: [],
        style: [],
        designPlanStyleId: '',
        caseItem: {},
        isHouseCaseOrHouseType: false,
        caseIsSuitable: false,
        caseStyleActive: -1, // 风格焦点
        caseSpaceActive: 3, // 空间筛选焦点样式
        templateId: '', // 空间ID
        tapComeFromType: '',// 从哪里进
        pageSize: 20,
        totalCount: 0,
        spaceAreas: '',
        favoriteRequest: true,
        collectRequest: true,
        groupFlag: false,
        groupItem: {},
        nowItems: {},
        typeShow: false,
        nowFlag: false,
    },

    /**
     * 生命周期函数--监听页面加载
     */
    onLoad: function (options) {
        opt = options;
        new $App.quickNavigation() // 注册组件
        new this.renderTypeExample() // 注册组件
        new $App.bindingPhone() // 注册组件
        let item = JSON.parse(options.item)
        let flag = false
        let caseIsSuitable = null
        let thaaat = this;
        let totalArea = opt.totalArea;
        if (options.groupItem) {
            this.setData({
                groupItem: JSON.parse(options.groupItem),
                groupFlag: true
            })
        }
        if (options.type === 'houseType' || options.type === 'houseSpace') { // 从哪进
            flag = false
        } else if (options.type === 'houseCaseFalse' || options.type === 'houseCaseTrue') {
            flag = true
            caseIsSuitable = options.type === 'houseCaseFalse' ? true : false
        }
        this.setData({
            caseIsSuitable: caseIsSuitable
        })
        this.gethousetypelist()

        // if (this.data.groupFlag){
        //   let Item = this.data.caseItem;
        //   this.setData({
        //     caseItem: this.data.groupItem.designTemplets[0].designPlanRecommended
        //   })
        // }
        // 空间还是户型进
        if (options.type != 'houseSpace') {
            this.setData({
                houseId: item.id,
                isHouseCaseOrHouseType: flag,
                caseIsSuitable: caseIsSuitable,
                tapComeFromType: options.type
            })
            this.getinformation(totalArea) // 获取方案
        } else {
            this.setData({
                spaceInedxType: item.spaceFunctionId,
                isHouseCaseOrHouseType: flag,
                tapComeFromType: options.type,
                spaceList: [item],
                spaceAreas: item.spaceAreas
            })
            this.getinformation(item.spaceAreas) // 获取方案
            this.getSpaceDetails(item.spaceCommonId) // 获取空间详细信息
        }

    },
    //获取所有空间类型
    gethousetypelist: function () {
        var that = this,
            url = '/designplan/designplanconditionmetadata',
            data = {};
        fetch(url, 'POST', data).then((res) => {
            if (res.obj) {
                that.setData({ houseTypelist: res.obj })
                if (this.data.tapComeFromType === 'houseSpace') { // 如果是空间入口
                    myForEach(this.data.houseTypelist, (val) => {
                        if (val.houseType === this.data.spaceInedxType) {
                            this.setData({ spaceInedxName: val.houseName, space: val })
                        }
                    })
                } else { // 不是空间入口需要匹配相应空间
                    that.getSpaceInHouse()
                }
            }
        })
    },
    getCaseItem: function (obj) {
        let that = this;
        if (wx.getStorageSync('caseItem')) {
            let name = '';
            for (let i = 0; i < obj.length; i++) {
                if (wx.getStorageSync('caseItem').spaceType == obj[i].houseType) {
                    name = obj[i].houseName;
                }
            }
            if (name) {
                setTimeout(function () {
                    that.setData({
                        caseSpaceActive: wx.getStorageSync('caseItem').spaceType,
                        spaceInedxType: wx.getStorageSync('caseItem').spaceType,
                        spaceInedxName: name,
                    })
                    //that.getinformation(that.data.spaceAreas)
                    that.getspace();
                }, 200)
            } else {
                setTimeout(function () {
                    that.setData({
                        caseSpaceActive: obj[0] ? obj[0].houseType : 3,
                        spaceInedxType: obj[0] ? obj[0].houseType : 3,
                        spaceInedxName: obj[0] ? obj[0].name : '客餐厅',
                    })
                    //that.getinformation(that.data.spaceAreas)
                    that.getspace();
                }, 200)
            }
        }
    },
    //根据houseid匹配空间列表
    getSpaceInHouse: function () {
        var that = this,
            url = '/home/basehouse/getSpaceNameInHouse.htm',
            data = { houseId: that.data.houseId };
        fetch(url, 'POST', data)
            .then((res) => {
                if (res.status) {
                    that.setData({ spaceInHouse: res.obj })
                    var space2 = []
                    for (let i = 0; i < that.data.houseTypelist.length; i++) {
                        for (let j = 0; j < that.data.spaceInHouse.length; j++) {
                            if (that.data.houseTypelist[i].houseType == that.data.spaceInHouse[j]) {
                                space2.push(that.data.houseTypelist[i])
                            }
                        }
                    }
                    that.setData({
                        space: space2,
                        spaceInedxName: space2[0].houseName,
                        spaceInedxType: space2[0].houseType
                    })
                    that.getspace()
                    that.getCaseItem(space2);
                }
            })
    },
    //获取顶部空间图
    getspace: function () {
        var that = this,
            url = '/home/spacecommon/housespacelist',
            data = {
                houseId: that.data.houseId,
                spaceFunctionId: that.data.spaceInedxType,
                designPlanRecommendId: wx.getStorageSync('caseItem').designPlanRecommendId || '',
                groupPrimaryId: wx.getStorageSync('caseItem').designPlanRecommendId || '',
            };
        fetch(url, 'GET', data).then((res) => {

            if (res.status) {
                that.setData({
                    spaceList: res.obj,
                    spaceAreas: res.obj[0].spaceAreas || '',
                    templateId: res.obj[0].designTemplets[0].id
                })
                let nowCaseItem = res.obj[0].designTemplets[0].designPlanRecommended || '';
                for (let i = 0; i < nowCaseItem.length; i++) {
                    if (that.data.templateId == nowCaseItem[i].designTemplets[0].id) {
                        nowCaseItem = nowCaseItem[i].designTemplets[0].designPlanRecommended
                    }
                }
                setTimeout(function () {
                    that.setData({
                        caseItem: nowCaseItem,
                        nowFlag: nowCaseItem ? true : false,
                    })
                    that.getinformation(that.data.spaceAreas) // 获取方案
                    that.caseIsMatching(that.data.spaceInedxType, that.data.spaceAreas) // 校验方案是否合适
                }, 200)

            } else {
                that.setData({
                    spaceList: [],
                    spaceAreas: ''
                })
                that.caseIsMatching()
            }
        })
            .catch((res) => {
                that.setData({
                    caseItem: '',
                    nowFlag: false,
                })
            })
    },
    //获取推荐方案列表
    getinformation: function (spaceAreas) {
        var that = this,
            url = '/designplan/designplanrecommendedlist',
            data = {
                curPage: 1,
                pageSize: this.data.pageSize,
                spaceType: that.data.spaceInedxType || 3,
                designPlanStyleId: that.data.designPlanStyleId,
                spaceArea: spaceAreas || this.data.spaceArea,
                displayType: 'dragDecorate'
            };
        fetch(url, 'GET', data).then((res) => {
            if (res.status) {
                that.setData({
                    information: res.obj,
                    totalCount: res.totalCount
                })
            }
        })
    },
    /**
     * 页面上拉触底事件的处理函数
     */
    onReachBottom: function () {
        if (this.data.pageSize >= this.data.totalCount)
            return;

        this.setData({
            pageSize: this.data.pageSize + 20
        })
        this.getinformation(this.data.spaceAreas)
    },

    /**
     * 用户点击右上角分享
     */
    onShareAppMessage: function (res) {
        if (res.from === 'menu') {
            let obj = (opt.item ? ('?item=' + JSON.stringify(opt.item)) : '')
                + (opt.name ? ('&name=' + opt.name) : '')
                + (opt.groupItem ? ('&groupItem=' + JSON.stringify(opt.groupItem)) : '');
            return $App.shareAppMessageFn(false, obj);
        }
    },
    isDropMenuShow: function () {
        if (this.data.tapComeFromType === 'houseSpace') {
            return
        }
        this.setData({ 'drop': !this.data.drop })
    },
    spaceChange: function (event) {
        this.setData({
            caseStyleActive: -1,
            designPlanStyleId: ''
        })
        let that = this,
            spacetype = event.currentTarget.dataset.spacetype
        that.setData({
            spaceInedxType: event.currentTarget.dataset['spacetype'],
            spaceInedxName: event.currentTarget.dataset['spacename'],
            'drop': !this.data.drop,
            caseSpaceActive: spacetype
        })

        let flag01 = (this.data.spaceInedxType == wx.getStorageSync('caseItem').spaceType);
        flag01 ? this.setData({ caseIsSuitable: false }) : this.setData({ caseIsSuitable: true });
        that.getspace();
    },
    styleChange: function (event) {
        let that = this,
            index = event.currentTarget.dataset.index,
            flag02 = event.currentTarget.dataset['styleid'] == undefined;

        flag02 ? that.setData({ designPlanStyleId: '', caseStyleActive: -1 }) : that.setData({ designPlanStyleId: event.currentTarget.dataset['styleid'], caseStyleActive: index });

        that.getinformation(this.data.spaceAreas)
    },
    putInMyhouse(e) { // 装进我家
        let caseItem = e.currentTarget.dataset.item, type = e.currentTarget.dataset.type,
            flag03 = this.data.caseIsSuitable && type == 'caseItem';
        flag03 ? wx.showToast({ title: '该方案不适合您选择的空间', icon: "none" }) : this.renderTypeShow(caseItem, this.data.houseId, this.data.templateId)
    },
    getrecommedCaseList(e) { // 根据空间获取推荐方案列表
        if (this.data.tapComeFromType === 'houseSpace') {
            return
        }
        let spaceAreas = e.currentTarget.dataset.item.spaceAreas,
            id = e.currentTarget.dataset.item.designTemplets[0].id;
        this.setData({ spaceAreas: spaceAreas, templateId: id })
        this.caseIsMatching(this.data.spaceInedxType, this.data.spaceAreas) // 匹配方案是否合适
        this.getinformation(spaceAreas)
    },
    caseIsMatching(id, spaceAreas) { // 方案是否匹配
        if (this.data.caseItem) {
            let caseItem = this.data.caseItem || wx.getStorageSync('caseItem'),
                spaceFunctionId = caseItem.spaceType || caseItem.spaceFunctionId,
                caseItemAreaValue = caseItem.applySpaceAreas.split(','),
                flag04 = (id === spaceFunctionId && caseItemAreaValue.indexOf(spaceAreas) !== -1);
            flag04 ? this.setData({ caseIsSuitable: false }) : this.setData({ caseIsSuitable: true });
            if (caseItem.designPlanRecommendId != 0 && caseItem.designPlanRecommendId != null) {
                this.setData({
                    caseIsSuitable: false,
                })
            }
        }
    },
    getSpaceDetails(spaceCommonId) { // 空间入口，获取空间详情
        let url = `/design/designtemplet/spacedesign`
        fetch(url, 'get', { spaceCommonId: spaceCommonId }).then((res) => {
            res.status ? this.setData({ templateId: res.obj[0].designTempletId }) : this.setData({ templateId: '' });
        })
    },
    formToThreeD(e) { // 720
        let type = e.detail.target.dataset.type, item = e.detail.target.dataset.item, routerQueryType = '',
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
    formToThreeD(e) { // 720
        let type = e.detail.target.dataset.type, item = e.detail.target.dataset.item, routerQueryType = '',
            formId = e.detail.formId
        if (type === '720' || type === 'roam') {
            let routerQueryType = type === '720' ? 'seven' : 'roam'
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
        let url = `/mobile/designPlanRecommended/getRecommendedPictureList.htm`,
            item = e.currentTarget.dataset.item,
            type = e.currentTarget.dataset.type;
        fetch(url, 'post', {
            planRecommendedId: item.designPlanRecommendId,
            remark: type
        }, 'render')
            .then(res => {
                return res.success ? (type == 'photo' ? res.datalist[0].pid : res.datalist[0].id) : false
            })
            .then(res => {
                if (res) {
                    let url = `/mobile/design/designPlan/getPanoPicture.htm`
                    fetch(url, 'post', {
                        thumbId: res
                    }, 'render')
                        .then(res => {
                            res.success ? (type == 'photo' ? this.enlargeImage(res.obj.url) : this.toVideo(res.obj.url)) : wx.showToast({ title: '打开失败', icon: 'none' })
                        })
                }
            })
    },
    toVideo(url) {
        wx.navigateTo({
            url: '../video/video?url=' + url
        })
    },
    collectCase2(e) {
        let that = this,
            url = `/designplanfavorite/setFavoriteOrUnfavorite`,
            item = e.currentTarget.dataset.item,
            status = item.isFavorite ? 0 : 1,
            title = status == 0 ? '取消收藏' : '收藏';
        fetch(url, 'post', {
            status: status,
            recommendId: item.designPlanRecommendId
        }).then((res) => {
            if (res.success) {
                that.data.caseItem.isFavorite = status;
                that.data.caseItem.collectNum = status ? that.data.caseItem.collectNum + 1 : that.data.caseItem.collectNum - 1;
                that.data.caseItem.collectNum = that.data.caseItem.collectNum > 0 ? that.data.caseItem.collectNum : 0;
                for (let i = 0; i < that.data.information.length; i++) {
                    if (item.designPlanName == that.data.information[i].designPlanName) {
                        that.data.information[i] = that.data.caseItem
                    }
                }
                wx.showToast({
                    title: title + '成功'
                })
                setTimeout(function () {
                    that.setData({
                        caseItem: that.data.caseItem,
                        information: that.data.information
                    })
                }, 500)
            } else {
                wx.showToast({
                    title: '收藏失败',
                    icon: 'none'
                })
            }
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
                status = item.isFavorite ? 0 : 1,
                title = status == 0 ? '取消收藏' : '收藏';
            fetch(url, 'post', {
                status: status,
                recommendId: item.designPlanRecommendId
            })
                .then((res) => {
                    if (res.success) {
                        that.data.information[index].isFavorite = that.data.information[index].isFavorite ? 0 : 1;
                        that.data.information[index].collectNum = status ? that.data.information[index].collectNum + 1 : that.data.information[index].collectNum - 1
                        that.data.information[index].collectNum = that.data.information[index].collectNum > 0 ? that.data.information[index].collectNum : 0;
                        wx.showToast({
                            title: title + '成功'
                        })
                        if (that.data.information[index].designPlanName == that.data.caseItem.designPlanName) {
                            that.data.caseItem = that.data.information[index]
                        }
                        setTimeout(function () {
                            that.setData({
                                information: that.data.information,
                                caseItem: that.data.caseItem,
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
            return;
        }
    },
    likeCase2(e) { // 当前方案点赞2
        var that = this;
        if (that.data.favoriteRequest) {
            that.setData({
                favoriteRequest: false
            })
            let url = `/designPlanLike/setLikeOrDislike`,
                item = e.currentTarget.dataset.item,
                status = item.isLike ? 0 : 1,
                title = status == 0 ? '取消点赞' : '点赞';
            fetch(url, 'post', {
                status: status,
                designId: item.designPlanRecommendId
            }).then((res) => {
                if (res.success) {
                    that.data.caseItem.isLike = status
                    that.data.caseItem.likeNum = status ? that.data.caseItem.likeNum + 1 : that.data.caseItem.likeNum - 1;
                    that.data.caseItem.likeNum = that.data.caseItem.likeNum > 0 ? that.data.caseItem.likeNum : 0;
                    wx.showToast({
                        title: title + '成功'
                    })
                    for (let i = 0; i < that.data.information.length; i++) {
                        if (item.designPlanName == that.data.information[i].designPlanName) {
                            that.data.information[i] = that.data.caseItem
                        }
                    }
                    setTimeout(function () {
                        that.setData({
                            caseItem: that.data.caseItem,
                            information: that.data.information,
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
                status = item.isLike ? 0 : 1,
                title = status == 0 ? '取消点赞' : '点赞';
            fetch(url, 'post', {
                status: status,
                designId: item.designPlanRecommendId
            })
                .then((res) => {
                    if (res.success) {
                        that.data.information[index].isLike = that.data.information[index].isLike ? 0 : 1;
                        status == 0 ? that.data.information[index].likeNum -= 1 : that.data.information[index].likeNum += 1
                        that.data.information[index].likeNum = that.data.information[index].likeNum > 0 ? that.data.information[index].likeNum : 0;
                        wx.showToast({
                            title: title + '成功'
                        })
                        if (that.data.information[index].designPlanName == that.data.caseItem.designPlanName) {
                            that.data.caseItem = that.data.information[index]
                        }
                        setTimeout(function () {
                            that.setData({
                                information: that.data.information,
                                caseItem: that.data.caseItem,
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
            return
        }
    },
    bindPhoneCallBack() { // 绑定手机号回调
        this.hideBindingShow()
    }
})