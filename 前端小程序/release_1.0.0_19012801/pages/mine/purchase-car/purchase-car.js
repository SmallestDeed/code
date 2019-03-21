// pages/purchase-car/purchase-car.js
let myForEach = getApp().myForEach, API = getApp().API
let $App = getApp()
Page({
    /**
     * 页面的初始数据
     */
    data: {
        staticImageUrl: getApp().staticImageUrl,
        carList: [],
        singleGoodsCount: [],
        totalPrice: 0,
        resourcePath: getApp().resourcePath,
        btnIscheckAll: false,
        startX: 0,
        startY: 0,
        emptyPageObj: {}
    },

    /**
     * 生命周期函数--监听页面加载
     */
    onLoad: function (options) {
      wx.hideShareMenu();
        new $App.newNav() // 注册快速导航组件
        this.getPurchaseCarList() // 获取购物车列表
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

    },

    /**
     * 用户点击右上角分享
     */
    onShareAppMessage: function (res) {
        if (res.from === 'menu') {
            return $App.shareAppMessageObj
        }
    },
    checkboxChange(e) { // 多选框选择
        let productPrice = 0
        let flag = true
        myForEach(this.data.carList, (valC, index) => {
            let judge = false
            myForEach(e.detail.value, (valP) => {
                if (valP == index) {
                    judge = true
                    productPrice = productPrice + Number(valC.productPrice) * this.data.singleGoodsCount[index]
                }
            })
            if (judge) {
                valC.checked = true
            } else {
                valC.checked = false
            }
        })
        if (e.detail.value.length !== this.data.carList.length) {
            flag = false
        }
        this.setData({
            totalPrice: productPrice,
            btnIscheckAll: flag,
            carList: this.data.carList
        })
    },
    isCheckAll(e) { // 是否全选
        let productPrice = 0
        if (e.detail.value.length !== 0) {
            myForEach(this.data.carList, (value, index) => {
                value.checked = true
                productPrice = productPrice + Number(value.productPrice) * this.data.singleGoodsCount[index]
            })
            this.setData({
                totalPrice: productPrice,
                carList: this.data.carList
            })
        } else {
            myForEach(this.data.carList, (value) => {
                value.checked = false
            })
            this.setData({
                totalPrice: 0,
                carList: this.data.carList
            })
        }
    },
    getPurchaseCarList() { // 购物车列表
        let url = `/cart/getDetail.htm`, price = 0, singleGoodsCount = []
        API.getPurchasecarNumber()
            .then((res) => {
                if (res.status && res.obj) {
                    myForEach(res.obj.cartProductList, (value) => {
                        value.checked = false
                        value.isMove = false
                    })
                    res.obj.cartProductList.forEach((value, index) => { singleGoodsCount.push(value.productNumber) })
                    this.setData({ singleGoodsCount: singleGoodsCount, carList: res.obj.cartProductList })
                } else {
                    this.setData({
                        carList: [],
                        emptyPageObj: {
                          imgUrl: this.data.staticImageUrl + 'carList.png',
                            title: '购物车空空如也~',
                            btnText: '去逛逛',
                            url: '/pages/brandHall/bHIndex/bHIndex',
                            switchTab: true
                        }
                    })
                }
            })
            .catch((err) => {
                this.setData({ carList: [], totalPrice: 0 })
            })
    },
    changeGoodsCount(e) { // 改变单个产品数量
        let index = e.currentTarget.dataset.index
        if (e.currentTarget.dataset.type === 'add') {
            this.data.singleGoodsCount[index] += 1
            this.data.carList[index].productNumber += 1
        } else {
            if (this.data.singleGoodsCount[index] > 1) {
                this.data.singleGoodsCount[index] -= 1
                this.data.carList[index].productNumber -= 1
            }
        }
        this.setData({
            singleGoodsCount: this.data.singleGoodsCount,
            carList: this.data.carList
        })
        this.calculationTotal() // 计算总价
    },
    settlement() { // 购物车结算
        let needCarList = []
        myForEach(this.data.carList, (value) => {
            if (value.checked) {
                needCarList.push(value)
            }
        })
        if (needCarList.length === 0) {
            wx.showToast({
                title: '请选择',
                icon: 'none'
            })
            return
        }
        let basegoodsSku = JSON.stringify(needCarList)
        basegoodsSku = this.myReplaceReg(basegoodsSku, '$')
        wx.navigateTo({
            url: '/pages/confirm-car-order/confirm-car-order?item=' + basegoodsSku
        })
    },
    myReplaceReg(str, replaceStr) {
        let reg = new RegExp('&', 'g')
        return str.replace(reg, replaceStr)
    },
    calculationTotal() { // 计算总价
        let totalPrice = 0
        myForEach(this.data.carList, (valC, index) => {
            if (valC.checked) {
                totalPrice += Number(valC.productPrice) * valC.productNumber
            }
        })
        this.setData({
            totalPrice: totalPrice
        })
    },
    carTouchStart(e) { // 购物车开始滑动
        let index = e.currentTarget.dataset.index
        this.setData({
            startX: e.touches[0].clientX,
            startY: e.touches[0].clientY,
        })
        // 物归原位
        myForEach(this.data.carList, (value) => {
            value.isMove = false
        })
        this.setData({
            carList: this.data.carList
        })
    },
    carTouchMove(e) { // 购物车滑动过程 
        let index = e.currentTarget.dataset.index,
            moveX = e.touches[0].clientX,
            moveY = e.touches[0].clientY
        let actualDistanceX = moveX - this.data.startX,
            actualDistanceY = moveY - this.data.startY
        if (this.computeAngle(actualDistanceX, actualDistanceY)) {
            if (moveX > this.data.startX) {
                this.data.carList[index].isMove = false
            } else {
                this.data.carList[index].isMove = true
            }
            this.setData({
                carList: this.data.carList
            })
        }
    },
    computeAngle(x, y) { // 计算角度
        let angle = Math.atan(y / x) * (180 / Math.PI)
        if (Math.abs(angle) > 30) {
            return false
        } else {
            return true
        }
    },
    deleteAloneCar(e) { // 删除单个购物车
        let item = e.currentTarget.dataset.item,
            index = e.currentTarget.dataset.index
        wx.showModal({
            title: '提示',
            content: '确定删除此商品',
            confirmText: '确定',
            cancelText: '取消',
            cancelColor: '#999999',
            confirmColor: '#ff6419',
            success: (res) => {
                if (res.confirm) {
                    API.deletePurchasecar({ cartProductIdList: [item.id] })
                        .then(res => {
                            if (res.status) {
                                wx.showToast({ title: '删除成功' })
                                this.data.carList.splice(index, 1)
                                this.setData({ carList: this.data.carList })
                            } else {
                                wx.showToast({ title: '删除失败' })
                            }
                        })
                }
            }
        })
    }
})