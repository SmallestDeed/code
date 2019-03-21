let $App = getApp(), API = getApp().API, myForEach = getApp().myForEach
Page({
    data: {
        startX: 0, //开始坐标
        startY: 0,
        resourcePath: getApp().resourcePath,
        curPage: 0,
        staticImageUrl: $App.staticImageUrl,
        pageSize: 6,
        contentlist: [],
        message: "加载中",
        order: "gmt_modified",
        orderNum: "desc",
        getRenderListFlag: true,
        type: 1,
        renderListTotalCount: 0
    },
    /**
     * 用户点击右上角分享
     */
    onShareAppMessage: function (res) {
        if (res.from === 'menu') { return $App.shareAppMessageObj }
    },
    onShow: function(){
        this.getSearchResluts('加载数据');
    },
    onLoad: function (options) {
      wx.hideShareMenu();
        new $App.quickNavigation() // 注册组件
    },
    renderNewsTouchStart(e) { // 开始滑动
        this.setData({ startX: e.touches[0].clientX, startY: e.touches[0].clientY, })
        myForEach(this.data.contentlist, (value) => { value.isTouchMove = false })
        this.setData({ contentlist: this.data.contentlist })
    },
    renderNewsTouchMove(e) { // 滑动过程
        let moveX = e.touches[0].clientX,
            moveY = e.touches[0].clientY
        if (this.computeAngle((moveX - this.data.startX), (moveY - this.data.startY))) {
            this.setData({ ['contentlist[' + e.currentTarget.dataset.index + '].isTouchMove']: (moveX <= this.data.startX) })
        }
    },
    computeAngle(x, y) { // 计算角度
        return !(Math.abs(Math.atan(y / x) * (180 / Math.PI)) > 30)
    },
    //删除事件
    del: function (e) {
        API.deleteUserRenderNews({ id: e.currentTarget.dataset.id })
            .then(res => {
                if (res.success) {
                    this.data.contentlist.splice(e.currentTarget.dataset.index, 1)
                    this.setData({ contentlist: this.data.contentlist })
                } else {
                    wx.showToast({ title: '删除信息失败', icon: 'none' })
                }
            })
    },
    getSearchResluts: function (message) {
        // if (this.data.getRenderListFlag) {
        //     this.data.getRenderListFlag = false
            API.getSystemRenderList({
                order: this.data.order,
                orderNum: this.data.orderNum,
                limit: this.data.pageSize,
                start: this.data.curPage,
                isRead: 1
            })
                .then(res => {
                    //this.data.getRenderListFlag = true
                    if (res.success && res.obj) {
                        myForEach(res.obj, (value) => { value.isTouchMove = false })
                        this.setData({ contentlist: this.data.contentlist.concat(res.obj), renderListTotalCount: res.totalCount || 0 })
                    } else {
                        this.setData({ renderListTotalCount: 0 })
                        this.setData({ message: '暂时还没有消息记录~' })
                    }
                })
        //}
    },
    onReachBottom() {
        if (this.data.renderListTotalCount > this.data.contentlist.length) {
            this.data.curPage += 6
            this.getSearchResluts()
        }
    },
    toEvaluate(e) {
        let taskId = e.currentTarget.dataset.taskid;
        let id = e.currentTarget.dataset.id;
        console.log(e.currentTarget.dataset);
        wx.navigateTo({
            url: '/pages/evaluate/evaluate?taskId=' + taskId + '&id=' + id,
        })
    },
})