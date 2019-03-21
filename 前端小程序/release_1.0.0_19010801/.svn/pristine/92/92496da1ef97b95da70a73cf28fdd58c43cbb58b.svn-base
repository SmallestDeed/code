// pages/decoration/brandHall/bHIndex/bHIndex.js
import { resourcePath } from '../../../utils/config.js'
let myForEach = getApp().myForEach, API = getApp().API
Page({
  /**
   * 页面的初始数据
   */
  data: {
    staticImageUrl: getApp().staticImageUrl,
    imgURL:getApp().data.imgURL,
    isShowClassify: false,
    ifShowToTopLogo: false,
    resourcePath: resourcePath,
    isSortSlideTop:true,
    isSortSlideDown:false,
    currClassify: { id: "", name: "全部" },//当前分类的内容 默认是全部{
    currSort:{id:"",name:"销量"},
    brandType:"",
    pageNo:1,
    classifyList: [
      {
        name: "全部",
        id: "0"
      },
      {
        name: "设计师",
        id: "3"
      },
      {
        name: "装修公司",
        id: "5"
      },
      {
        name: "施工单位",
        id: "6"
      },
      {
        name: "建材家居",
        id: "2"
      },
      {
        name: "设计公司",
        id: "4"
      }
    ],
    sortList:[
      {
        id:"praiseRate",
        name:"评分最高"
      }
    ],
    banners: ["https://show.sanduspace.com/AA/bannerpic/upload/1539677047484.jpg"],
    exampleList: [],
    shopName:'',
    cityCode:'',
    headerActive: 0, // 0是产品，1是品牌馆
    parentClassificationAllList: [],
    twoClassificationAllList: [],
    threeClassificationAllList: [],
    recommendGoodsList: [],
    recommendGoodsPageSize: 10,
    recommendGoodsTotal: 0    
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    wx.hideShareMenu();
    this.getShopList();
    this.getBanner();
  },
  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function () {
    this.getShopList();
    this.getBanner();
    this.getRecommendGoodsList() // 获取产品首页的推荐产品列表    
  },
  // onPageScroll(e) {
  //   e.scrollTop > 200 ? this.setData({ ifShowToTopLogo: true }) : this.setData({ ifShowToTopLogo: false })
  // },
  // goTop() {
  //   this.setData({ ifShowToTopLogo: false })
  //   wx.pageScrollTo({
  //     scrollTop: 0,
  //     duration: 300
  //   });
  // },
  getRecommendGoodsList() { // 获取产品首页的推荐产品列表
    API.getGoodsFullsearch({
      "pageVo": {
        "pageSize": this.data.recommendGoodsPageSize,
        "currentPage": 1,
      },
      isAggregationCategory: 0
    })
    .then(res => {
      if (res.returnCode === '000000') {
        this.setData({
          recommendGoodsList: res.obj.obj,
          recommendGoodsTotal: res.total
        })
      } else {
        this.setData({
          recommendGoodsList: [],
          recommendGoodsTotal: 0
        })
      } 
    })
  },
  routerToGoodsDetails(e) { // 跳转至商品详情
    let id = e.currentTarget.dataset.id
    wx.navigateTo({
      url: '/pages/goods-details/goods-details?id=' + id
    })
  },
  switchHeaderActive(e) { // 切换焦点
    this.setData({ ifShowToTopLogo: false })
    wx.pageScrollTo({
      scrollTop: 0,
      duration: 0
    });
    let index = e.currentTarget.dataset.index;
    let imgArr = [["https://show.sanduspace.com/AA/bannerpic/upload/1539677047484.jpg"], ["https://show.sanduspace.com/AA/bannerpic/upload/1539676183095.jpg"]];
    this.setData({
      headerActive: index,
      banners: imgArr[index]
    })
  },
  routerToGoodsSearch() { // 跳转至搜索页面
    wx.setStorageSync('companyId', '');
    wx.setStorageSync('searchType', this.data.headerActive);
    console.log(wx.getStorageSync('searchType'));
    wx.navigateTo({
      url: '/pages/goods-search/goods-search',
    })
  },
  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function () {
  
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
    if (this.data.headerActive == 1) { // 品牌馆
      this.getShopList();
    } else { // 产品模块下拉加载
      if (this.data.recommendGoodsList.length < this.data.recommendGoodsTotal) {
        this.setData({
          recommendGoodsPageSize: this.data.recommendGoodsPageSize + 10
        })
        this.getRecommendGoodsList()
      }
    }
  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function () {
  
  },
  toggleSort:function(){
    var This = this;
    This.setData({
      isSortSlideTop: This.data.isSortSlideDown,
      isSortSlideDown: This.data.isSortSlideTop,
      isShowSort: !This.data.isShowSort,
      isShowClassify:false,
      isClassifySlideDown:false,
      isClassifySlideTop:true
    })
  },
  toggleClassify:function(){
    var This = this;
    This.setData({
      isClassifySlideTop: This.data.isClassifySlideDown,
      isClassifySlideDown: This.data.isClassifySlideTop,
      isShowClassify: !This.data.isShowClassify,
      isShowSort:false,
      isSortSlideDown:false,
      isSortSlideTop:true
    })
  },
  toHref: function (e) {
    wx.setStorageSync('shopType', '');
    var sHref = e.currentTarget.dataset.href;
    var id = e.currentTarget.dataset.id
    wx.setStorageSync('shopId', id);
    if (sHref == undefined) return;
    console.log(sHref);
    wx.navigateTo({
      url: sHref+'?id='+id,
    })
  },
  getShopList:function(){
    API.getCompanyShopList({
      pageNo: 1,
      pageSize: 20,
      businessType: 1,
      shopName: this.data.shopName,
      cityCode: this.data.cityCode,
      orderBy: '',
      platformType: 2,
      orderBySql: 'a.gmt_create'
    })
    .then(res => {
      if (res.code == 200) {
        this.setData({ exampleList: res.data.list })
      }
    })
  },
  getBanner:function(){
    // fetch('/base/banner/web/xzminiprogram/list?positionCode=xz:brand:index:top', 'get', {}, 'system').then(res => {
    //   if (res.success == true) {
    //     // if (!res.data)
    //     //   return;
    //     // This.setData({
    //     //   banner: res.data
    //     // })
    //   } else {
    //     wx.showToast({
    //       title: res.message || "",
    //       icon: 'none'
    //     })
    //   }

    // }).then(res => {
    // })
  },
  doPreview: function (e) {
    var imgPath = e.currentTarget.dataset.imgpath;
    if (imgPath == undefined)
      return;
    wx.previewImage({
      current: imgPath, // 当前显示图片的http链接
      urls: [imgPath] // 需要预览的图片http链接列表
    })
  },
  changeClassify: function (e) {
    var classifyId = e.currentTarget.dataset.classifyid;
    var classifyname = e.currentTarget.dataset.classifyname;
    if (classifyId == undefined || classifyname == undefined)
      return;
    this.setData({
      currClassify: { id: classifyId, name: classifyname }
    })
    this.toggleClassify();
  },
  changeSort:function(e){
    var orderBy = e.currentTarget.dataset.sort;
    var name = e.currentTarget.dataset.name;
    this.setData({
      currSort: { id: orderBy, name: name }
    })
    this.toggleSort();
  },
  getItem: function (key, val, arr) {
    //key对象的属性名 val对象的属性值 arr要搜索的数组
    if (key == undefined || val == undefined || arr == undefined)
      return;
    for (var i = 0; i < arr.length; i++) {
      if (arr[i][key] == val) {
        return arr[i];
      }
    }
    return;
  },
  doFilter:function () {
    this.setData({
      pageNo: 1,
      exampleList: []
    })
    this.getShopList();
  },
  toList(e){
    let id = e.currentTarget.dataset.id , url = ''
    if (this.data.headerActive == 0 ) {
      url = `/pages/goods-class/goods-class?id=`
    } else if (this.data.headerActive == 1) {
      url = `/pages/brandHall/bHSearchRes/bHSearchRes?id=`
    }
    wx.navigateTo({
      url: url + id,
    })
  }
})