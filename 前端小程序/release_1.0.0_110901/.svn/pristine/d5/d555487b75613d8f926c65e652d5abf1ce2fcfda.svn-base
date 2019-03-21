
// pages/goods-details/goods-details.js
let myForEach = getApp().myForEach, API = getApp().API
import { renderTypeExample } from '../../component/render-type/render-type'
import { emptyTemplate } from '../../component/emptyTemplate/emptyTemplate'
let WxParse = require('../../utils/wxParse/wxParse.js');
let $App = getApp()

Page({
  renderTypeExample,
  emptyTemplate,
  /**
   * 页面的初始数据
   */
  data: {
    //swiper配置
    autoplay: false,
    interval: 1000,
    duration: 200,
    favoriteRequest:true,
    collectRequest:true,
    prodetailsObj:{},
    resourcePath: getApp().resourcePath,
    sevenUrl: getApp().sevenUrl,
    imageArray: ['https://homesiteres.zbjimg.com/homesite%2Fres%2F300X250.jpg%2Forigine%2F4ef2b90d-f4a3-4589-abef-ce2104fa65ea', 'https://homesiteres.zbjimg.com/homesite%2Fres%2F300X250.jpg%2Forigine%2F4ef2b90d-f4a3-4589-abef-ce2104fa65ea'],
    textActive: 0 ,
    purchaseNumber: 1, // 用户要购买的数量
    specificationListShow: false, // 规格列表的展示
    confirmOrFooter: true,
    isCarOrBuy: 'buy', // 识别加入购物车或者购买
    goodDetailsHeight: '', // 大盒子样式
    goodDetailsOverflow: 'none', // 大盒子样式
    iconObj:{
      collect: {
        start: '/static/image/goods_icon_collect_nor@2x.png',
        active: '/static/image/goods_icon_collect_sct@2x.png'
      },
    },
    planNum:0,
    carCount: 0,
    curPage: 1,
    pageSize: 20,
    toatlCount: 0,
    productId:'',
  
    bflist: [],
    goodsType: [], //商品规格列表
    // 选择规格信息
    chooseText: '',//已选择样式
    attrValueId: [],//选择类型的id
    basegoodsSku: '',
    spuid:''
  },

  /**
   * 生命周期函数--监听页面加载
   */
  showImg:function(e){
    let src = e.currentTarget.dataset.url;
    if (src =='image/goods_pic_no.png'||!src){
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
  onLoad: function (options) {
    new this.renderTypeExample() // 注册组件
    new this.emptyTemplate() // 注册组件
      new $App.newNav() // 注册快速导航组件
    this.getGoodsDetails(options.id)
    this.getRecommendPlan(options.id)
    this.getPurchasecarNumber() // 初始化购物车数量    
    this.setData({
      carCount: getApp().data.carCount, // 加入购物车数量
      productId: options.id
    })
    this.setData({
      prodetailsObj:{
        id: options.id 
      } ,
    })
    
    this.specificationsInfo(options.id)//获取规格信息
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
    this.getPurchasecarNumber() // 刷新购物车数量
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
  // switchGoodsAndCase(e) { // 商品及案例的切換
  //   this.setData({
  //     textActive: e.target.dataset.index,
  //     specificationListShow:false
  //   })
  // },
  toThreeD(e) { // 调转到3D界面
    let type = e.currentTarget.dataset.type, item = e.currentTarget.dataset.item, routerQueryType = null, webUrl = this.data.sevenUrl
    if (type === 'video') {
      API.getRecommendedVideoId({
        planRecommendedId: item.designPlanRecommendId,
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
      let sevenObj = {
        token: wx.getStorageSync('token'),
        platformCode: 'brand2c',
        operationSource: 1,
        planId: item.designPlanRecommendId,
        routerQueryType: routerQueryType,
        customReferer: $App.wxUrl,
        platformNewCode: 'miniProgram'
      }
      for (let key in sevenObj) { webUrl += key + '=' + sevenObj[key] + '&' }
      getApp().data.webUrl = webUrl
      $App.myNavigateBack('pages/web-720/web-720')
      console.log(webUrl)
    }
  },
  enlargeImage(url) { // 查看大图
    wx.previewImage({
      current: url, // 当前显示图片的http链接
      urls: [url] // 需要预览的图片http链接列表
    })
  },
  toVideo(url) {
    wx.navigateTo({
      url: '/pages/template/video/video?url=' + url
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
      if (this.data.purchaseNumber < this.data.basegoodsSku.inventory) { // basegoodsSku.inventory (库存)
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
  purchaseImmediately() { // ；立即购买
    if (this.data.specificationListShow) {
      this.setData({
        goodDetailsheight: '',
        goodDetailsOverflow: 'none',
        specificationListShow: false,
        confirmOrFooter: true
      })
      this.data.basegoodsSku.purchaseNumber = this.data.purchaseNumber
      wx.navigateTo({
        url: '../confirm-order/confirm-order?item=' + JSON.stringify(this.data.basegoodsSku).replace(/&/g, '$')
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
      API.joinPurchaseCar({
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
            this.setData({ carCount: this.data.carCount })
            wx.showToast({ title: '加入购物车成功', icon: 'success', duration: 2000 })
          } else {
            wx.showToast({ title: '加入购物车失败', icon: 'none', duration: 2000 })
          }
        })
        .catch(res => {
          wx.showToast({ title: '加入购物车失败', icon: 'none', duration: 2000 })
        })
    } else {
      let goodListheight = wx.getSystemInfoSync().windowHeight
      this.setData({
        confirmOrFooter: false,
        specificationListShow: true,
        isCarOrBuy: 'car',
        goodDetailsheight: goodListheight + 'px',
        goodDetailsOverflow: 'hidden'
      })
    }
  },
  specificationsInfo(id) {
    API.specificationsInfo({
      id: id      
    })
    .then(res => {
      if (res.status) {
        this.setData({ goodsType: res.obj.attr })
        let chooseText = [];
        let total = [];//选择样式拼接
        let attrValueIds = [];//选择样式id拼接
        res.obj.attr.map(item => {
          chooseText = [...chooseText, ...item.attrValue.filter(item1 => {
            return item1.isSelected == 1
          })]
        })
        chooseText.map(item => {
          total.push(item.attrValueName)
          attrValueIds.push(item.attrValueId)
        })
        this.setData({ chooseText: total.join(','), attrValueId: attrValueIds })
        this.goodsSpecifications(attrValueIds.join(','));
      }
    })
  },
  choseSpecifications() { // 选择规格
    let goodListheight = wx.getSystemInfoSync().windowHeight

    this.setData({
      goodDetailsheight: goodListheight + 'px',
      goodDetailsOverflow: 'hidden',
      specificationListShow: true,
      confirmOrFooter: true
    })

  },
  goodsSpecifications(attrValueIds) { //选择规格类型
    API.goodsSpecifications({
      spuId: this.data.prodetailsObj.id,
      attrValueIds: attrValueIds
    })
    .then(res => {
      if (res.status) {
        this.setData({
          ['prodetailsObj.price']: res.obj.price,
          ['prodetailsObj.salePrice']: res.obj.salePrice,
          chooseText: res.obj.attribute,
          basegoodsSku: res.obj
        })
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
    this.data.goodsType[e.target.dataset.index].attrValue[e.target.dataset.number].isSelected = 1;//选择当前的类型 
    this.data.attrValueId[e.target.dataset.index] = e.target.dataset.items.attrValueId;//替换id
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
    if (this.data.isCarOrBuy === 'car') {
      API.joinPurchaseCar({
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
          wx.showToast({ title: '加入购物车成功', icon: 'success', duration: 2000 })
          this.data.carCount += this.data.purchaseNumber
          this.setData({ carCount: this.data.carCount })
        } else {
          wx.showToast({ title: '加入购物车失败', icon: 'none', duration: 2000 })
        }
      })
    } else if (this.data.isCarOrBuy === 'buy') {
      this.data.basegoodsSku.purchaseNumber = this.data.purchaseNumber
      let item = JSON.stringify(this.data.basegoodsSku).replace(/&/g, '$')
      console.log(item, 'wqdgrsf')
      wx.navigateTo({
        url: '../confirm-order/confirm-order?item=' + item
      })
    }
    this.setData({
      specificationListShow: false,
      confirmOrFooter: true,
      goodDetailsheight: '',
      goodDetailsOverflow: 'none'
    })
  },
  toPurchasecar() { // 跳转到购物车页面
    this.setData({
      goodDetailsheight: '',
      goodDetailsOverflow: 'none',
      specificationListShow: false,
      confirmOrFooter: true
    })
    wx.navigateTo({
      url: '/pages/mine/purchase-car/purchase-car',
    })
  },
  getGoodsDetails(id) { // 获取商品详情
    let that = this;
    API.getGoodsDetails({ id: id })
    .then(res => {
      if (res.status) {
        this.setData({ bflist: res.obj, spuid: res.obj })
        if (res.obj.smallPiclist == null || res.obj.smallPiclist.length == 0) {
          res.obj.smallPiclist = ['image/goods_pic_no.png']
        } else {
          for (let i = 0; i < res.obj.smallPiclist.length; i++) {
            res.obj.smallPiclist[i] = that.data.resourcePath + res.obj.smallPiclist[i]
          }
        }
        this.setData({ prodetailsObj: res.obj, productName: res.obj.productName })
        WxParse.wxParse('article', 'html', res.obj.productDesc || '', that, 5);
      }
    })
  },
  goodCollect(e) { // 收藏与取消收藏
    let id = e.currentTarget.dataset.id, requestType = null
    this.data.prodetailsObj.isFavorite === 0 ? requestType = 'collectGood' : requestType = 'deleteCollectGood'
    API[requestType]({ spuId: id })
    .then(res => {
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
    API.getGoodsRecommendPlan({ spuId: productId,platformCode:'selectDecoration' })
    .then(res => {
        if (res.datalist) {
          let recommendPlanList = [];
          recommendPlanList.push(res.datalist[0]);
          this.setData({ recommendPlanList: recommendPlanList, planNum: res.totalCount })
          this.emptyTemplateShow('hide')
        } else {
          this.setData({ recommendPlanList: [], planNum: 0 })
          this.emptyTemplateShow('show')
        }
      
    })
    .catch(res => {
      this.emptyTemplateShow('show')
      this.setData({ recommendPlanList: [], planNum: 0 })
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
      url: '/pages/plan/case-house-type/case-house-type'
    })
  },
  collectCase(e) { // 方案收藏
    let that = this,
      item = e.currentTarget.dataset.item,
      index = e.currentTarget.dataset.index,
      status = null,
      title = '收藏'
    if (that.data.collectRequest == true) {
      that.setData({ collectRequest: false })
      item.isFavorite ? (status = 0, title = '取消收藏') : (status = 1, title = '收藏')
      API.collectCase({ status: status, recommendId: item.designPlanRecommendId })
        .then(res => {
          if (res.success) {
            status == 0 ? this.data.recommendPlanList[index].collectNum -= 1 : this.data.recommendPlanList[index].collectNum += 1
            this.data.recommendPlanList[index].isFavorite = status
            this.setData({ recommendPlanList: this.data.recommendPlanList })
            wx.showToast({ title: title + '成功' })
          } else {
            wx.showToast({ title: '收藏失败', icon: 'none' })
          }
          setTimeout(function () { that.setData({ collectRequest: true }) }, 500)
        })
    }
  },
  likeCase(e) { // 方案点赞
    let that = this,
      item = e.currentTarget.dataset.item,
      index = e.currentTarget.dataset.index,
      status = null,
      title = '点赞'
    if (that.data.favoriteRequest == true) {
      that.setData({ favoriteRequest: false })
      item.isLike ? (status = 0, title = '取消点赞') : (status = 1, title = '点赞')
      API.likeCase({ status: status, designId: item.designPlanRecommendId })
        .then(res => {
          if (res.success) {
            status == 0 ? this.data.recommendPlanList[index].likeNum -= 1 : this.data.recommendPlanList[index].likeNum += 1
            this.data.recommendPlanList[index].isLike = status
            this.setData({ recommendPlanList: this.data.recommendPlanList })
            wx.showToast({ title: title + '成功' })
          } else {
            wx.showToast({ title: title + '失败', icon: 'none' })
          }
          setTimeout(function () { that.setData({ favoriteRequest: true }) }, 500)
        })
    }
  },
  getPurchasecarNumber() { // 获取购物车数量
    let count = 0
    API.getPurchasecarNumber()
    .then(res => {
      if (res.status) {
        if (res.obj) {
          myForEach(res.obj.cartProductList, (value) => { count += value.productNumber })
        }
      }
      this.setData({ carCount: count })  
    })
  }, 
  leaveAMessage() { // 未开放此功能
    wx.showToast({
      title: '未开放此功能',
      icon: 'none'
    })
  },
  toOfficalWeb(){
    wx.navigateTo({
      url: '/pages/mini-offcial-web/mini-offcial-web',
    })
  },
  bannerError: function (e) {//缺省图
    var _errImg = e.target.dataset.img
    var _errObj = {}
    _errObj[_errImg] = "image/goods_pic_no.png"
    this.setData(_errObj) //注意这里的赋值方式,只是将数据列表中的此项图片路径值替换掉  
  },
  toRecommendPlan(){//跳转到推荐方案页
    
    wx.navigateTo({
      url: '/pages/goods-details-plan/goods-details-plan?id=' + this.data.productId,
    })
  },
  callService(){
    let phone = '18928178107'
    wx.makePhoneCall({
      phoneNumber: phone,
    })
  },
 
})