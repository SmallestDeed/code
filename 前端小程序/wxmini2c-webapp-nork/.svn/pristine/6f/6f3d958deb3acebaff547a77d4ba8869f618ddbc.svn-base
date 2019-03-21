// pages/goods-details/goods-details.js
let fetch = getApp().fetch,
API = getApp().API,
myForEach = getApp().myForEach
import {
    renderTypeExample
} from '../../component/render-type/render-type'
import {
    emptyTemplate
} from '../../component/emptyTemplate/emptyTemplate'
import {
    TimeStyle
} from '../../lib/data/data'
let $App = getApp()
var {
    shareTitle
} = require('../../utils/config.js');
const WxParse = require('../../utils/wxParse/wxParse.js');
var shareTitle = {
    shareTitle
}.shareTitle;
let flagTitle = (shareTitle == '诺克照明');
let optionsId = '';
Page({
    renderTypeExample,
    emptyTemplate,
    /**
     * 页面的初始数据
     */
    data: {
        evaluationImgList: ['', '', '', '', '', ''],
        showFailDialog:false,
        hiddenList: [],
        isHidden: false,
        refreshData:'',
        assembleSalePrice:'',
        groupId:'',
        purchaseOpenId:'',//扫码进来的 ID
        timerSatus:'',//拼团 状态 定时器
        assembleDayNumStatus: '',
        assembleHourNumStatus: '',
        assembleMinNumStatus:'',
        assembleSecNumStatus: '',
        timeValueStatus:'',
        assembleDetail: {},//拼团状态详情
        friendList:'', //拼团好友列表
        //assembleStatus:'',//拼团状态
        isMather:1,//是否拼团 发起人 0 不是 1是
        isShare: false, //外面扫码进来的页面
        isFail: false, //是否满团
        isAssemble: false, //是否拼团参数
        perListTotal:'',
        perListTimer:[],
        personListTime:[],
        isOneBuy:true,//单页面购买
        activeId:'',//拼团活动id
        personTimeValue:'',
        personTimeValueTwo:'',
        personPage:1,
        assembleChangePerson:[],
        timer:'',
        timeValue:'',
        assembleDayNum:'',
        assembleHourNum:'',
        assembleMinNum:'',
        assembleSecNum:'',
        //swiper配置
        autoplay: false,
        interval: 1000,
        duration: 200,
        favoriteRequest: true,
        collectRequest: true,
        prodetailsObj: {},
        resourcePath: getApp().resourcePath,
      staticImageUrl: getApp().staticImageUrl,
        sevenUrl: getApp().sevenUrl,
        imageArray: ['https://homesiteres.zbjimg.com/homesite%2Fres%2F300X250.jpg%2Forigine%2F4ef2b90d-f4a3-4589-abef-ce2104fa65ea', 'https://homesiteres.zbjimg.com/homesite%2Fres%2F300X250.jpg%2Forigine%2F4ef2b90d-f4a3-4589-abef-ce2104fa65ea'],
        textActive: 0,
        purchaseNumber: 1, // 用户要购买的数量
        specificationListShow: false, // 规格列表的展示
        confirmOrFooter: true,
        isCarOrBuy: 'buy', // 识别加入购物车或者购买
        goodDetailsHeight: '', // 大盒子样式
        goodDetailsOverflow: 'none', // 大盒子样式
        detailName: "",
        planNum: 0,
        carCount: 0,
        curPage: 1,
        pageSize: 20,
        toatlCount: 0,
        productId: '',
        flagTitle: flagTitle,
        bflist: [],
        goodsType: [], //商品规格列表
        // 选择规格信息
        chooseText: '', //已选择样式
        attrValueId: [], //选择类型的id
        basegoodsSku: '',
        spuid: '',
        businessType:'',
        couponList: [],
        optionsId: '',
        evaluation: null,
    },
    //获取商品评价
  getGoodsEvaluation() {
      API.getGoodsEvaluation({
        userId: wx.getStorageSync('userId'), // 2501 
        spuId: this.data.spuId, // spuId
        pageSize: 1,
        pageNo: 1
      }).then(res => {
        if (res.obj) {
          this.setData({
            evaluation: res.obj.data[0],
            allTotal: res.obj.allTotal
          })
        }
      });
    },
    //查看全部评价
    goEvaluation() {
      wx.navigateTo({
        url: `/pages/goods-evaluation/goods-evaluation?spuId=${this.data.spuId}`,
      })
    },
    //查看放大图片
    examineImg(e) {
      wx.previewImage({
        urls: [this.data.resourcePath + e.currentTarget.dataset.item]
      });
    },
    cloesDialog: function () {
        this.setData({
            showFailDialog: false
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
            userId: wx.getStorageSync("userId"),
            couponId: e.currentTarget.dataset.id,
        }
        fetch(url, 'get', data, 'shop').then((res) => {
            if (res.code == 200) {
                wx.showToast({
                    title: res.message,
                    icon: 'success',
                })
               this.data.isAssemble ? that.getAssembleDetail(optionsId) : that.getGoodsDetails(optionsId)
            } else if (res.code == 10000102) {
                wx.showToast({
                    title: res.message,
                    icon: 'none'
                })
            }
        })
    },
    getActivity: function (id, free) {
        let url = '/product/baseCompany/getCompanyId';
        let url2 = 'v1/sandu/mini/activity/getGoodsCouponList';
        let that = this;
        fetch(url, 'get').then((res) => {
            if (res.obj) {
                let data = {
                    userId: wx.getStorageSync("userId"),
                    companyId: res.obj,
                    productId: id,
                    totalFree: free
                }
                fetch(url2, 'get', data, 'shop').then((e) => {
                    let list = [];
                    let maxL = e.data.length > 10 ? 10 : e.data.length;
                    for (let i = 0; i < maxL; i++) {
                        list[i] = e.data[i];
                    }
                    this.setData({
                        couponList: list,
                    })
                })
            }
        })

    },

    showImg: function (e) {
        let src = e.currentTarget.dataset.url;
        if (src == 'image/goods_pic_no.png' || !src) {
            wx.showToast({
                title: '暂无图片预览',
                icon: 'none'
            })
            return;
        }
        wx.previewImage({
            current: src,
            urls: this.data.prodetailsObj.smallPiclist
        })
    },
    /**
     * 生命周期函数--监听页面加载
     */
    onLoad: function (options) {
        this.data.spuId = options.spuId || options.id
        this.getGoodsEvaluation()
        let that=this;
        let optionsId = options.id;
        this.data.optionsId = optionsId
        new this.renderTypeExample() // 注册组件
        new this.emptyTemplate() // 注册组件
        new $App.quickNavigation() // 注册组件
        //如果是拼团 商品 跑拼团接口 如果不是 跑原来的接口
        let paramsIsAssemble = '';
        options.isAssemble == 'true' ? paramsIsAssemble = true : paramsIsAssemble = false
        this.getPurchasecarNumber() // 初始化购物车数量   
        this.setData({
            carCount: getApp().data.carCount, // 加入购物车数量
            productId: optionsId,
            groupId: options.groupId,
            refreshData:options
        })
        this.setData({
            prodetailsObj: {
                id: optionsId
            },
            isAssemble: paramsIsAssemble
        })
        if (options.purchaseOpenId) {
           this.setData({
              purchaseOpenId: options.purchaseOpenId,
              isShare:true
           })
        } else {
            isShare: false
        }
         this.getAssembleStauts()//获取拼团状态接口
         if (that.data.isAssemble) {
             this.getAssembleDetail(optionsId)
         } else {
             this.getGoodsDetails(optionsId)
         }
        wx.getStorage({
          key: 'businessType',
          success: function (res) {
            that.setData({
              businessType: res.data
            })
          },
        })
        wx.setStorageSync('isAssemble',this.data.isAssemble)
       // this.specificationsInfo(optionsId) //获取规格信息

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
        let pages = getCurrentPages();
        this.getPurchasecarNumber() // 刷新购物车数量
        this.getRecommendPlan(this.data.optionsId) // 获取方案
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
       clearInterval(this.data.timer)
       clearInterval(this.data.timerSatus);
       clearInterval(this.data.perListTimer[0], this.data.perListTimer[1])
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
        // if (res.from === 'menu') {
        //     return $App.shareAppMessageFn(true, '?id=' + optionsId);
        // }
        let sharetitle = ''
        this.data.isAssemble ? sharetitle = `【来跟我们一起拼团吧】${this.data.basegoodsSku.productName}` : sharetitle = this.data.basegoodsSku.productName
         return {
             title: sharetitle,
             path: `/pages/index/index?id=${this.data.optionsId}&isAssemble=${this.data.isAssemble}&groupId=${this.data.groupId}&purchaseOpenId=${this.data.purchaseOpenId}`,
             imageUrl: this.data.resourcePath + this.data.basegoodsSku.productPicPath,
             success: function (res) {
                 // 转发成功
                 console.log(res)
             },
             fail: function (res) {
                 // 转发失败
             }
         }
    },
    // switchGoodsAndCase(e) { // 商品及案例的切換
    //   this.setData({
    //     textActive: e.target.dataset.index,
    //     specificationListShow:false
    //   })
    // },
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
            console.log(webUrl)
            $App.myNavigateBack('pages/web-720/web-720')
            return
        }
    },
    toThreeD(e) { // 调转到3D界面
        let url = `/mobile/designPlanRecommended/getRecommendedPictureList.htm`,
            item = e.currentTarget.dataset.item,
            type = e.currentTarget.dataset.type
        fetch(url, 'post', {
            planRecommendedId: item.designPlanRecommendId,
            remark: type
        }, 'render')
            .then(res => {
                let ret = res.success ? (type == 'photo' ? res.datalist[0].pid : res.datalist[0].id) : false;
                return ret;
            })
            .then(res => {
                if (res) {
                    let url = `/mobile/design/designPlan/getPanoPicture.htm`
                    fetch(url, 'post', {
                        thumbId: res
                    }, 'render')
                        .then(res => {
                            if (res.success) {
                                type == 'photo' ? this.enlargeImage(res.obj.url) : this.toVideo(res.obj.url)
                            } else {
                                wx.showToast({
                                    title: '打开失败',
                                    icon: 'none'
                                })
                            }
                        })
                }
            })
    },
    enlargeImage(url) { // 查看大图
        wx.previewImage({
            current: url, // 当前显示图片的http链接
            urls: [url] // 需要预览的图片http链接列表
        })
    },
    toVideo(url) {
        wx.navigateTo({
            url: '../video/video?url=' + url
        })
    },
    changePurchaseNumber(e) { // 增加购买数量
        if (e.target.dataset.index === 0) {
            if (this.data.purchaseNumber === 1) {
                return
            }
            this.setData({
                purchaseNumber: this.data.purchaseNumber - 1
            })
        } else {
            if (this.data.purchaseNumber < this.data.basegoodsSku.inventory) {
                this.setData({
                    purchaseNumber: this.data.purchaseNumber + 1
                })
            } else {
                wx.showToast({
                    title: '已达上限',
                    icon: 'none',
                    duration: 2000
                })
            }

        }
    },
    purchaseImmediately(e) { // ；立即购买
       if(e.currentTarget.dataset.type=='once'){
            this.setData({
                isOneBuy: true,
                purchaseNumber: 1
            })
       } else if (e.currentTarget.dataset.type == 'join') {
          this.setData({
              isOneBuy: false,
              isMather:0,
              purchaseNumber:1
          })
       } else if (e.currentTarget.dataset.type == 'couyicou') {
         this.setData({
             isOneBuy: false,
             isMather: 0,
             activeId: e.currentTarget.dataset.activityid,
             purchaseOpenId: e.currentTarget.dataset.purchaseopenid,
             purchaseNumber: 1
         })
       } 
       else {
            this.setData({
                isOneBuy: false,
                 isMather: 1,
                purchaseNumber: 1
            })
       }
       if (this.data.isAssemble == true && this.data.isOneBuy == false) {
           wx.setStorageSync('isAssemble', true)
       }else{
             wx.setStorageSync('isAssemble', false)
       }
          this.specificationsInfo(this.data.prodetailsObj.id);
        if (this.data.specificationListShow) {
            this.setData({
                goodDetailsheight: '',
                goodDetailsOverflow: 'none',
                specificationListShow: false,
                confirmOrFooter: true
            })
            this.data.basegoodsSku.purchaseNumber = this.data.purchaseNumber
            return;
            wx.navigateTo({
                url: '../confirm-order/confirm-order?item=' + JSON.stringify(this.data.basegoodsSku)
                // url: '../confirm-order/confirm-order?item=' + JSON.stringify(this.data.prodetailsObj)
            })
            return
        }
        let goodListheight = wx.getSystemInfoSync().windowHeight
        this.setData({
            goodDetailsheight: goodListheight + 'px',
            goodDetailsOverflow: 'hidden',
            confirmOrFooter: false,
            specificationListShow: true,
            isCarOrBuy: 'buy'
        })
    },
    joinPurchaseCar() { // 加入购物车
        if (this.data.specificationListShow) {
            let url = `/cart/add.htm`
            fetch(url, 'post', {
                productId: this.data.basegoodsSku.productId,
                productNumber: this.data.purchaseNumber,
                productPrice: this.data.basegoodsSku.price,
                productCode: this.data.prodetailsObj.productCode,
                productName: this.data.basegoodsSku.productName,
                productStyleName: this.data.prodetailsObj.productStyleName,
                productColorName: this.data.prodetailsObj.productColorName,
                productPicPath: this.data.basegoodsSku.productPicPath
            })
                .then(res => {
                    if (res.status) {
                        this.data.carCount += this.data.purchaseNumber
                        this.setData({
                            carCount: this.data.carCount
                        })
                        wx.showToast({
                            title: '加入成功',
                            icon: 'success',
                            duration: 2000
                        })
                    } else {
                        wx.showToast({
                            title: '加入失败',
                            icon: 'none',
                            duration: 2000
                        })
                    }
                })
                .catch(res => {
                    wx.showToast({
                        title: '加入失败',
                        icon: 'none',
                        duration: 2000
                    })
                })
            return
        }
        let goodListheight = wx.getSystemInfoSync().windowHeight
        this.setData({
            confirmOrFooter: false,
            specificationListShow: true,
            isCarOrBuy: 'car',
            goodDetailsheight: goodListheight + 'px',
            goodDetailsOverflow: 'hidden'
        })

    },
    specificationsInfo(id) {
        let req = '';
        if(!this.data.isOneBuy){
         let url = `/v1/group/purchase/goods/attr`;
           req = fetch(url, 'get', {
                 id: id,
                 activityId:this.data.activeId
             },'system')
        }else{
          let url = `/goods/basegoods/attr`;
          req = fetch(url, 'get', {
              id: id
          })
        }
      req.then(res => {
            if (res.status || res.success) {
                this.setData({
                    goodsType: res.obj.attr
                })
                let chooseText = [];
                res.obj.attr.map(item => {
                    chooseText = [...chooseText, ...item.attrValue.filter(item1 => {
                        return item1.isSelected == 1
                    })]
                })
                let total = []; //选择样式拼接
                let attrValueIds = []; //选择样式id拼接
                chooseText.map(item => {
                    total.push(item.attrValueName)
                    attrValueIds.push(item.attrValueId)
                })
                this.setData({
                    chooseText: total.join(','),
                    attrValueId: attrValueIds
                })
                this.goodsSpecifications(attrValueIds.join(','));
            }
        })
    },
    choseSpecifications() { // 选择规格
        let goodListheight = wx.getSystemInfoSync().windowHeight
        if(this.data.isAssemble){
            this.setData({
              isOneBuy:false,
              purchaseNumber: 1
            })
        }else{
             this.setData({
                 isOneBuy: true
             })
        }
        this.specificationsInfo(this.data.productId) //获取规格信息
        this.setData({
            goodDetailsheight: goodListheight + 'px',
            goodDetailsOverflow: 'hidden',
            specificationListShow: true,
            confirmOrFooter: true
        })

    },
    goodsSpecifications(attrValueIds) { //选择规格类型
        let req = '';
         if (this.data.isOneBuy) {
            let url = `/goods/basegoods/sku`;
           req =  fetch(url, 'get', {
                spuId: this.data.prodetailsObj.id,
                attrValueIds: attrValueIds
            })
         }else{
        let url = '/v1/group/purchase/goods/sku'
          req = fetch(url, 'get', {
              spuId: this.data.prodetailsObj.id,
              attrValueIds: attrValueIds,
              activityId: this.data.activeId,
          },'system')
         }
       req.then(res => {
            if (res.success) {
                this.setData({

                })
               if (this.data.isOneBuy){
                   if(this.data.isAssemble){
                       this.setData({
                           ['prodetailsObj.productName']: res.obj.productName,
                           ['prodetailsObj.price']: res.obj.price,
                           ['prodetailsObj.salePrice']: res.obj.salePrice,
                           chooseText: res.obj.attribute,
                           basegoodsSku: res.obj,
                       })
                   }else{
                        this.setData({
                            ['prodetailsObj.productName']: res.obj.productName,
                            ['prodetailsObj.price']: res.obj.price,
                            ['prodetailsObj.salePrice']: res.obj.salePrice,
                            chooseText: res.obj.attribute,
                            basegoodsSku: res.obj,
                        })
                   }
                   
               }else{
                    this.setData({
                        ['prodetailsObj.productName']: res.obj.productName,
                        //['prodetailsObj.salePrice']: res.obj.salePrice,
                         ['prodetailsObj.salePrice']: res.obj.price,
                        ['prodetailsObj.activityPrice']: res.obj.activityPrice,
                        chooseText: res.obj.attribute,
                        basegoodsSku: res.obj,
                        assembleSalePrice: res.obj.price
                    })
                    this.data.isOneBuy ? '' : res.obj.price = res.obj.activityPrice;
               }
            }
        })
    },
    chooseGoodsType(e) {
        //切换类型
        this.data.goodsType.map(res => {
            res.attrValue.map(item => {
                res.attrId == e.target.dataset.item.attrId ? item.isSelected = 0 : ''
            })
        })
        this.data.goodsType[e.target.dataset.index].attrValue[e.target.dataset.number].isSelected = 1; //选择当前的类型 
        this.data.attrValueId[e.target.dataset.index] = e.target.dataset.items.attrValueId; //替换id
        this.setData({
            goodsType: this.data.goodsType,
            attrValueId: this.data.attrValueId
        })
        this.goodsSpecifications(this.data.attrValueId.join(','))
    },
    closeSpecificationsTable() { // 关闭规格
        this.setData({
            goodDetailsheight: '',
            goodDetailsOverflow: 'none',
            specificationListShow: false,
            confirmOrFooter: true
        })
    },

    purchaseConfirm() { // 确定购买或者加入购物车
       if(this.data.isAssemble&&!this.data.isOneBuy){
          if(this.data.prodetailsObj.activity.purchaseLimitAmount<this.data.purchaseNumber){
               wx.showToast({
                   title: `该商品限购${this.data.prodetailsObj.activity.purchaseLimitAmount}件`,
                   icon: 'none',
                   duration: 1000
               })
               return
          }
       }
       if(this.data.isAssemble==true&&this.data.isOneBuy==false){
          wx.setStorageSync('isAssemble', true)
       }else{
            wx.setStorageSync('isAssemble', false)
       }
        if (this.data.isCarOrBuy === 'car') {
            let url = `/cart/add.htm`
            fetch(url, 'post', {
                productId: this.data.basegoodsSku.productId,
                productNumber: this.data.purchaseNumber,
                productPrice: this.data.basegoodsSku.price,
                productCode: this.data.prodetailsObj.productCode,
                productName: this.data.basegoodsSku.productName,
                productStyleName: this.data.prodetailsObj.productStyleName,
                productColorName: this.data.prodetailsObj.productColorName,
                productPicPath: this.data.basegoodsSku.productPicPath
            })
                .then(res => {
                    if (res.status) {
                        wx.showToast({
                            title: '加入购物车成功',
                            icon: 'success',
                            duration: 2000
                        })
                        this.data.carCount += this.data.purchaseNumber
                        this.setData({
                            carCount: this.data.carCount
                        })
                    } else {
                        wx.showToast({
                            title: '加入购物车失败',
                            icon: 'none',
                            duration: 2000
                        })
                    }
                })
                .catch(res => { })
        } else if (this.data.isCarOrBuy === 'buy') {
            this.data.basegoodsSku.purchaseNumber = this.data.purchaseNumber
            let basegoodsSku = JSON.stringify(this.data.basegoodsSku)
            basegoodsSku = this.myReplaceReg(basegoodsSku, '$')
            wx.navigateTo({
                url: '../confirm-order/confirm-order?item=' + basegoodsSku + '&activeId=' + this.data.activeId + '&isMather=' + this.data.isMather + '&spuId=' + this.data.prodetailsObj.id + '&skuId=' + this.data.basegoodsSku.skuId + '&purchaseOpenId=' + this.data.purchaseOpenId
            })
        }
        this.setData({
            specificationListShow: false,
            confirmOrFooter: true,
            goodDetailsheight: '',
            goodDetailsOverflow: 'none'
        })
    },
    myReplaceReg(str, replaceStr) {
        let reg = new RegExp('&', 'g')
        return str.replace(reg, replaceStr)
    },
    toPurchasecar() { // 跳转到购物车页面
        this.setData({
            goodDetailsheight: '',
            goodDetailsOverflow: 'none',
            specificationListShow: false,
            confirmOrFooter: true
        })
        
        wx.switchTab({
          url: '../purchase-car/purchase-car',
        })
    },
    getGoodsDetails(id) { // 获取商品详情
        var that = this;
        let url = `/goods/basegoods/detail`
        fetch(url, 'get', {
            id: id
        })
            .then(res => {
                if (res.status) {
                    //this.data.flagTitle?this.getActivity(id, parseFloat(res.obj.price)):'';
                    this.getActivity(id, parseFloat(res.obj.price))
                    this.setData({
                        bflist: res.obj,
                        spuid: res.obj,
                        detailName: res.obj.productName
                    })
                    if (res.obj.smallPiclist == null || res.obj.smallPiclist.length == 0) {
                        res.obj.smallPiclist = ['image/goods_pic_no.png']
                    } else {
                        for (let i = 0; i < res.obj.smallPiclist.length; i++) {
                            res.obj.smallPiclist[i] = that.data.resourcePath + res.obj.smallPiclist[i]
                        }
                    }

                    this.setData({
                        prodetailsObj: res.obj
                    })
                    WxParse.wxParse('article', 'html', this.data.prodetailsObj.productDesc || '无', this, 1);
                }
                this.setData({
                    isOneBuy: true
                })
                this.specificationsInfo(this.data.productId) //获取规格信息
            })
    },
    goodCollect(e) {
        let id = e.currentTarget.dataset.id
        let url = ''
        if (this.data.prodetailsObj.isFavorite === 0) {
            url = `/product/productfavorite/collectSpu`
        } else {
            url = `/product/productfavorite/delCollectSpu`
        }
        fetch(url, 'get', {
            spuId: id
        })
            .then((res) => {
                if (res.status) {
                    if (this.data.prodetailsObj.isFavorite === 0) {
                        this.data.prodetailsObj.isFavorite = 1
                        this.setData({
                            prodetailsObj: this.data.prodetailsObj
                        })
                    } else {
                        this.data.prodetailsObj.isFavorite = 0
                        this.setData({
                            prodetailsObj: this.data.prodetailsObj
                        })
                    }
                }
            })
    },
    getRecommendPlan(productId) { // 方案列表
        let url = 'fullsearch-app/goods/recommendationplan/search/list';

        fetch(url, 'get', {
            "spuId": parseInt(productId),
            "start": 0,
            "limit": 1
        }, 'fullsearch').
            then(res => {
                let flag = res.success && res.datalist ? true : false;
                this.setData({
                    recommendPlanList: flag ? res.datalist[0] : [],
                    planNum: flag ? res.datalist.length : 0
                })
                this.emptyTemplateShow(flag ? 'hide' : 'show')
            })
    },
    // toVideo(e) {
    //   wx.navigateTo({
    //     url: '../video/video?url=' + this.data.resourcePath + e.currentTarget.dataset.item[0]
    //   })
    // },
    putInMyhouse(e) { // 装进我家
        // this.renderTypeShow() // 显示组件
        let item = e.currentTarget.dataset.item
        wx.setStorageSync('caseItem', item)
        wx.navigateTo({
            url: '../case-house-type/case-house-type'
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
                        this.data.recommendPlanList[index].isFavorite = status
                        status == 0 ? this.data.recommendPlanList[index].collectNum -= 1 : this.data.recommendPlanList[index].collectNum += 1
                        this.setData({
                            recommendPlanList: this.data.recommendPlanList
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
            return;
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
                        this.data.recommendPlanList[index].isLike = status
                        status == 0 ? this.data.recommendPlanList[index].likeNum -= 1 : this.data.recommendPlanList[index].likeNum += 1
                        this.setData({
                            recommendPlanList: this.data.recommendPlanList
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
            return
        }
    },
    goPay:function(){
        wx.navigateTo({
            url: '/pages/mini-offcial-web/mini-offcial-web',
        })
    },
    getPurchasecarNumber() { // 获取购物车数量
        let url = `/cart/getDetail`,
            count = 0
        fetch(url, 'get')
            .then((res) => {
                if (res.status) {
                    if (res.obj) {
                        myForEach(res.obj.cartProductList, (value) => {
                            count += value.productNumber
                        })
                        this.setData({
                            carCount: count
                        })
                    } else {
                        this.setData({
                            carCount: 0
                        })
                    }
                } else {
                    this.setData({
                        carCount: 0
                    })
                }
            })
    },
    leaveAMessage() { // 未开放此功能
        wx.showToast({
            title: '未开放此功能',
            icon: 'none'
        })
    },
    toOfficalWeb() {
        wx.navigateTo({
            url: '/pages/mini-offcial-web/mini-offcial-web',
        })
    },
    bannerError: function (e) { //缺省图
        var _errImg = e.target.dataset.img
        var _errObj = {}
        _errObj[_errImg] = "image/goods_pic_no.png"
        this.setData(_errObj) //注意这里的赋值方式,只是将数据列表中的此项图片路径值替换掉  
    },
    toRecommendPlan() { //跳转到推荐方案页

        wx.navigateTo({
            url: '/pages/goods-details-plan/goods-details-plan?id=' + this.data.productId,
        })
    },
    callService() {
        let phone = '18928178107'
        wx.makePhoneCall({
            phoneNumber: phone,
        })
    },
   // 拼团模块接口  
   //获取拼团商品详情    
        getAssembleDetail(id) {
             var that = this;
            API.getAssembleGoodsDetails({
                id: id,
                activityId: this.data.groupId,
                purchaseOpenId: this.data.purchaseOpenId=='' ? '' : this.data.purchaseOpenId
            }).then(res => {
                if (res.success) {
                    res.obj.id = res.obj.spuId
                    this.getActivity(id, parseFloat(res.obj.price))
                    res.obj.salePrice = res.obj.price
                    this.setData({
                        bflist: res.obj,
                        spuid: res.obj,
                        detailName: res.obj.productName,
                        activeId: res.obj.activity.activityId,
                        assembleSalePrice: res.obj.price
                    })
                    if (res.obj.smallPiclist == null || res.obj.smallPiclist.length == 0) {
                        res.obj.smallPiclist = ['image/goods_pic_no.png']
                    } else {
                        for (let i = 0; i < res.obj.smallPiclist.length; i++) {
                            res.obj.smallPiclist[i] = that.data.resourcePath + res.obj.smallPiclist[i]
                        }
                    }
                    let timeValue = ''
                    if (res.obj.activity.howLongStartTime<0) {
                        timeValue = (res.obj.activity.howLongEndTime)/1000
                    } else {
                        timeValue = (res.obj.activity.howLongStartTime)/1000
                    }
                    this.setData({
                        prodetailsObj: res.obj,
                        timeValue: timeValue,
                        assembleDayNum: TimeStyle(0, timeValue),
                        assembleHourNum: TimeStyle(1, timeValue),
                        assembleMinNum: TimeStyle(2, timeValue),
                        assembleSecNum: TimeStyle(3, timeValue),
                        isFail: res.obj.activity.isGroupOverflow, //是否满团弹框
                        showFailDialog: res.obj.activity.isGroupOverflow
                    })
                    this.TimeCacl() //拼团倒计时方法
                    WxParse.wxParse('article', 'html', this.data.prodetailsObj.productDesc || '无', this, 0);
                }
                 this.getAssemblePersonList(this.data.personPage) //拼团  换一批 列表接口
                 //如果该活动已结束
                 if (res.obj.activity.activityStatus == 3 || res.obj.activity.activityStatus == 4) {
                     clearInterval(this.data.timer)
                     clearInterval(this.data.perListTimer[0], this.data.perListTimer[1])
                     this.setData({
                         isOneBuy:true
                     })
                 }
                 this.setData({
                     isOneBuy:false
                 })
                 this.specificationsInfo(this.data.productId) //获取规格信息
            })
        },
        //倒计时方法
        TimeCacl:function(){
            clearInterval(this.data.timer)
            var _this = this;
            _this.setData({
                timer: setInterval(() => {
                  if (this.data.prodetailsObj.activity) {
                      if (this.data.timeValue < 1){
                         clearInterval(this.data.timer)
                         this.getAssembleDetail(this.data.productId)
                      }else{
                         let newTime = this.data.timeValue - 1
                         this.setData({
                             timeValue: newTime,
                             assembleDayNum: TimeStyle(0, newTime),
                             assembleHourNum: TimeStyle(1, newTime),
                             assembleMinNum: TimeStyle(2, newTime),
                             assembleSecNum: TimeStyle(3, newTime),
                         })
                      }
                  }else{
                      clearInterval(this.data.timer)
                  }

                    if (this.data.timeValue <= 1 ) {
                        if (this.data.prodetailsObj.activity) {
                            this.getAssembleDetail(this.data.productId)
                           
                        }else{
                            clearInterval(this.data.timer)
                        }
                    }
                }, 1000)
            })
        },
        //换一批 拼团接口 
        getAssemblePersonList:function(personPage){
             this.setData({
                 personListTime: []
             })
             API.getAssemblePersonList({
                   activityId: this.data.activeId,
                   offset: personPage,
                   limit:2,
                 }).then(res =>{
                    if (res.datalist==null) {
                       return
                    }
                    if (res.success && res.datalist) {
                        res.datalist.map((item,index)=>{
                             if (item.howLongOpenDate < 0) {
                                 let perTime = item.howLongExpireDate/1000
                                let timeList = {
                                    timelist:perTime
                                }
                                this.data.personListTime.push(timeList)
                                 let hours = parseInt(TimeStyle(1, perTime)) + parseInt((TimeStyle(0, perTime) * 24))
                                 item.personTime = `${hours}:${TimeStyle(2, perTime)}:${TimeStyle(3, perTime)}`
                             } else {
                                let perTime = item.howLongOpenDate/1000
                                item.personTime = `${TimeStyle(1, perTime)}:${TimeStyle(2, perTime)}:${TimeStyle(3, perTime)}`
                                 let timeList = {
                                     timelist: perTime
                                 }
                                 this.data.personListTime.push(timeList)
                             }
                            return item
                        })
                        this.setData({
                            assembleChangePerson: res.datalist,
                            personListTime: this.data.personListTime,
                            perListTotal: (res.totalCount % 2 == 0) ? (res.totalCount / 2) : (res.totalCount / 2 + 1)
                        })
                         this.changePersonTime() //换一批倒计时方法
                    }
                 })
        },
        //拼团  换一批接口
        changePerson: function () {
           this.data.personListTime = []
           let page = this.data.personPage
           if (this.data.perListTotal-1 > page) {
              page++
           }else{
               page = 1
           }
          
           this.setData({
               personPage:page,
               personListTime: this.data.personListTime
           })
           this.getAssemblePersonList(page)
        },
        //拼团 换一批 数据 的倒计时
        changePersonTime(){
             if (this.data.assembleChangePerson==null) {
                 return
             }
            clearInterval(this.data.perListTimer[0], this.data.perListTimer[1])
            var _this = this;
            this.data.assembleChangePerson.map((item, index) => {
                this.data.perListTimer[index] = setInterval(() => {
                        if (this.data.personListTime[index].timelist < 1) {
                            clearInterval(this.data.perListTimer[index])
                            // this.getAssemblePersonList(1)
                            // this.getAssembleDetail(this.data.productId)
                             item.personTime = `该团已失效!`
                             this.setData({
                                 assembleChangePerson: this.data.assembleChangePerson
                             })
                        }else{
                            let newTime = this.data.personListTime[index].timelist - 1;
                            this.data.personListTime[index].timelist = newTime
                            let hours = parseInt(TimeStyle(1, newTime)) + parseInt((TimeStyle(0, newTime) * 24))
                            item.personTime = `${hours}:${TimeStyle(2, newTime)}:${TimeStyle(3, newTime)}`

                            this.setData({
                                assembleChangePerson: this.data.assembleChangePerson,
                                personListTime: this.data.personListTime,
                                perListTimer: this.data.perListTimer
                            })
                          
                        }
                },1000)
            })
          



            
            // _this.setData({
            //     perListTimer: setInterval(() => {
            //      this.data.assembleChangePerson.map((item,index)=>{
            //          let newTime = this.data.personListTime[index].timelist - 1
            //          if (newTime<1) {
            //              clearInterval(this.data.perListTimer)
            //              this.getAssemblePersonList(1)
            //               this.getAssembleDetail(this.data.productId)
            //          }else{
            //             this.data.personListTime[index].timelist = newTime
            //             let hours = parseInt(TimeStyle(1, newTime)) + parseInt((TimeStyle(0, newTime) * 24))
            //             item.personTime = `${hours}:${TimeStyle(2, newTime)}:${TimeStyle(3, newTime)}`
            //             return item
            //          }
            //      })
            //         this.setData({
            //            assembleChangePerson: this.data.assembleChangePerson,
            //            personListTime: this.data.personListTime
            //         })
            //     }, 1000)
            // })
        },

         getAssembleStauts() {
            //  if (!this.data.isShare){
            //      return
            //  }
            if (!this.data.purchaseOpenId){
                return
            }
             API.getAssembleDetail({
                 activeId: this.data.purchaseOpenId
             }).then(res => {
                 if (res.success && res.obj) {
                     let timeValueStatus = ''
                     if (res.obj.startTime < 0) {
                         timeValueStatus = res.obj.expireTime / 1000
                     } else {
                         timeValueStatus = res.obj.startTime / 1000
                     }
                     if (res.obj.innerUserInfoList) {
                         if (res.obj.innerUserInfoList.length < 5) {
                             this.setData({
                                 hiddenList: res.obj.innerUserInfoList,
                                 isHidden: false
                             })
                         } else if (res.obj.innerUserInfoList.length == 5) {
                             this.setData({
                                 hiddenList: res.obj.innerUserInfoList,
                                 isHidden: false
                             })
                         } else if (res.obj.innerUserInfoList.length > 5) {
                             let arr = []
                             res.obj.innerUserInfoList.forEach((element, index) => {
                                 if (index < 4) {
                                     arr.push(element)
                                 }
                             });
                             this.setData({
                                 hiddenList: arr,
                                 isHidden: true
                             })
                         }
                     }
                     //机器人默认头像
                     res.obj.innerUserInfoList.map(item => {
                         if (item.userId == -1) {
                             item.picPath = `${this.data.resourcePath}/AA/group_purchase/roberPic/rober_0${item.picId+1}.png`
                             item.mark = '我是机器人'
                         }
                         return item
                     })
                     this.setData({
                         friendList: res.obj.innerUserInfoList,
                         assembleDetail: res.obj,
                         timeValueStatus: timeValueStatus,
                         assembleDayNumStatus: TimeStyle(0, timeValueStatus),
                         assembleHourNumStatus: TimeStyle(1, timeValueStatus),
                         assembleMinNumStatus: TimeStyle(2, timeValueStatus),
                         assembleSecNumStatus: TimeStyle(3, timeValueStatus),
                     })
                     this.TimeCaclSataus()//拼团 状态倒计时方法
                 }
             })
         },
         TimeCaclSataus: function () {
             var _this = this;
             if (this.data.assembleDetail.openStatus != 0) {
                 clearInterval(_this.data.timerSatus);
                 return
             }
             _this.setData({
                 timerSatus: setInterval(() => {
                     if (this.data.timeValueStatus < 1) {
                        clearInterval(_this.data.timerSatus);
                        this.data.assembleDetail.openStatus = 2;
                        this.setData({
                            assembleDetail: this.data.assembleDetail
                        })
                     }
                     let newTime = this.data.timeValueStatus - 1
                     this.setData({
                         timeValueStatus: newTime,
                         assembleDayNumStatus: TimeStyle(0, newTime),
                         assembleHourNumStatus: TimeStyle(1, newTime),
                         assembleMinNumStatus: TimeStyle(2, newTime),
                         assembleSecNumStatus: TimeStyle(3, newTime),
                     })
                 }, 1000)
             })
         },
})