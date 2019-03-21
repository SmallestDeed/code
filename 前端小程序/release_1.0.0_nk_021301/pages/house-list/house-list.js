let fetch = getApp().fetch, myForEach = getApp().myForEach, $App = getApp()
let opt = {};
Page({

    /**
     * 页面的初始数据
     */
    data: {
        resourcePath: getApp().resourcePath,
        staticImageUrl:getApp().staticImageUrl,
        productListImgs: [],
        mername: "",
        curPage: 1,
        pageSize: 10,
        livingId: "",
        contentlist: []
    },

    /**
     * 生命周期函数--监听页面加载
     */
    onLoad: function (options) {
        console.log(options);
        new $App.quickNavigation() // 注册组件
        var that = this;
        opt = options;
        that.setData({
            mername: options.name,
            livingId: options.id

        })
        wx.setNavigationBarTitle({
            title: that.data.mername//页面标题为路由参数
        })
        that.getSearchResluts("加载数据中")

    },

    getSearchResluts: function (message) {
        var that = this
        var url = "/home/basehouse/getbasehouselist"
        var data = {
            livingId: that.data.livingId,
            pageSize: that.data.pageSize,
            curPage: that.data.curPage
        }
        fetch(url, 'get', data)
            .then((res) => {
                var contentlistTem = that.data.contentlist
                var productImageTem = that.data.productListImgs;
                if (res.success) {
                    if (that.data.curPage == 1) {
                        contentlistTem = []
                        productImageTem = []
                    }

                    var productListImgs = [];
                    var contentlist = res.obj;

                    for (let i = 0; i < contentlist.length; i++) {
                        productListImgs.push(that.data.resourcePath + "/" + contentlist[i].largeThumbnailPath)
                    }


                    if (contentlist.length < that.data.pageSize) {
                        that.setData({
                            contentlist: contentlistTem.concat(contentlist),
                            productListImgs: productImageTem.concat(productListImgs),
                            hasMoreData: false
                        })
                    } else {
                        that.setData({
                            contentlist: contentlistTem.concat(contentlist),
                            productListImgs: productImageTem.concat(productListImgs),
                            hasMoreData: true,
                            curPage: that.data.curPage + 1
                        })
                    }
                } else {
                    wx.showToast({
                        title: res.message,
                    })
                }
            })
            .catch((res) => {
                wx.showToast({
                    title: '加载数据失败',
                })
            })
    },

    /**
     * 生命周期函数--监听页面初次渲染完成
     */
    onReady: function () {

    },

    /**
     * 生命周期函数--监听页面显示
     */
    onShow: function () {

    },

    /**
     * 生命周期函数--监听页面隐藏
     */
    onHide: function () {

    },

    /**
     * 生命周期函数--监听页面卸载
     */
    onUnload: function () {

    },

    /**
     * 页面相关事件处理函数--监听用户下拉动作
     */
    onPullDownRefresh: function () {

    },

    /**
     * 页面上拉触底事件的处理函数
     */
    onReachBottom: function () {

        if (this.data.hasMoreData) {
            this.getSearchResluts('加载更多数据')
        } else {
            wx.showToast({
                title: '没有更多数据',
            })
        }

    },

    /**
     * 用户点击右上角分享
     */
    onShareAppMessage: function (res) {
        if (res.from === 'menu') {
            //let obj = '?id=' + opt.id + '&name=' + opt.name;
            let obj = (opt.name ? ('?name=' + opt.name) : '')
                + (opt.id ? ('&id=' + opt.id) : '')
            return $App.shareAppMessageFn(true, obj);
        }
    },
    previewImage: function (e) {
        var that = this,
            //获取当前图片的下表
            index = e.currentTarget.dataset.index,
            //数据源
            productListImgs = this.data.productListImgs;
        wx.previewImage({
            //当前显示下表
            current: productListImgs[index],
            //数据源
            urls: productListImgs
        })
    },
    toHouseDetail(e) { // 检验是否合适
        let item = e.currentTarget.dataset.item,
            url = `/home/spacecommon/housespacelist`,
            totalArea = item.totalArea,
            caseItem = wx.getStorageSync('caseItem');

        if (caseItem) { // 是否检验
            let spaceFunctionId = caseItem.spaceFunctionId || caseItem.spaceType
            console.log('spaceFunctionId',spaceFunctionId);
            let caseItemAreaValue = ''
            if (caseItem.applySpaceAreas) {
                caseItemAreaValue = caseItem.applySpaceAreas.split(',')
            }
            let data = {
                houseId: item.id,
                designPlanRecommendId: caseItem.planRecommendedId||caseItem.designPlanRecommendId,
                groupPrimaryId: caseItem.groupPrimaryId ? caseItem.groupPrimaryId : ''
            }
            fetch(url, 'get', data)
                .then((res) => {
                    if (res.status) {
                        console.log(res.obj);
                        if (JSON.stringify(res.obj) !== '[]') {
                            let arr = []
                            let flag = false
                            let setCaseItem = false
                            let groupFlag = false
                            let nowItems = {};
                            console.log(caseItem.groupPrimaryId, caseItem.planRecommendedId, caseItem.planRecommendedId);
                            if (caseItem.groupPrimaryId != 0 && caseItem.groupPrimaryId != null && caseItem.groupPrimaryId == data.designPlanRecommendId) {
                                myForEach(res.obj, (val) => {
                                    if (spaceFunctionId == 13) {//13时为全屋，不需判断
                                        flag = true
                                        setCaseItem = true
                                        nowItems = res.obj;
                                    } else if (val.spaceFunctionId === spaceFunctionId) {
                                        
                                        if (val.designTemplets != null) {
                                            console.log(val);
                                            console.log(1111,val.designTemplets[0].designPlanRecommended);
                                            if (val.designTemplets[0].designPlanRecommended != null) {
                                                arr.push(val)
                                                flag = true
                                                groupFlag = true
                                                nowItems = res.obj;
                                            }
                                        }
                                    }
                                })
                            } else {
                                
                                myForEach(res.obj, (val) => {
                                    console.log(val.spaceFunctionId, spaceFunctionId, caseItemAreaValue.indexOf(val.spaceAreas));
                                    if (spaceFunctionId == 13) {//13时为全屋，不需判断
                                        flag = true
                                        setCaseItem = true
                                    }else if (val.spaceFunctionId === spaceFunctionId && caseItemAreaValue.indexOf(val.spaceAreas) !== -1) {    
                                        flag = true
                                        setCaseItem = true
                                    }
                                })
                            }

                            console.log('ssss',setCaseItem);
                            setTimeout(function(){
                                if (flag) {
                                    let strs = groupFlag ? ('&groupItem=' + JSON.stringify(arr[0])) : '';
                                    wx.navigateTo({
                                        url: '../house-details/house-details?type=houseCaseTrue&setCaseItem=' + setCaseItem +'&item=' + JSON.stringify(item) + strs
                                    })
                                } else {
                                    wx.showModal({
                                        title: '提示',
                                        content: '该户型暂时不能匹配您选择的方案!是否需要为你推荐适合当前户型的其它方案',
                                        cancelText: '暂不考虑',
                                        cancelColor: '#8e8e8e',
                                        confirmText: '需要推荐',
                                        confirmColor: '#ff6419',
                                        success: (res) => {
                                            if (res.confirm) {
                                                wx.navigateTo({
                                                    url: '../house-details/house-details?type=houseCaseFalse&item=' + JSON.stringify(item) + '&type=' + 'houseCaseFalse',
                                                })
                                            }
                                        }
                                    })
                                }
                            },300)
                        }
                    }
                })
        } else {
            console.log(1);
            wx.navigateTo({
                url: '../house-details/house-details?type=houseType&totalArea=' + totalArea + '&item=' + JSON.stringify(item)
            })
        }
    }
})