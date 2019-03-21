// pages/brandHall/bHStore/bHStore.js
import { resourcePath } from '../../../utils/config.js'
let API = getApp().API
let $App = getApp()
Page({

  /**
   * 页面的初始数据
   */
  data: {
    resourcePath: resourcePath,
    id:'',
    imgURL:getApp().data.imgURL,
    keyword:"",
    isShowClassify:false,
    isClassifySlideDown:true,
    isClassifySlideTop:false,
    detail:{},
    companyId:'',
    code: [],
    pageSize:6,
    total: 0,
    goodsList:[],
    goodsNavList: [],
    goodsNavIndex: 0,
    ifShowToTopLogo: false,
    isBack: false
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    wx.hideShareMenu();
      new $App.newNav() // 注册快速导航组件
    this.setData({
      id:options.id
    })
    this.getDetail();
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
  goGoodsDetails(e) { // 跳转至商品详情
    let id = e.currentTarget.dataset.id
    wx.navigateTo({
      url: '/pages/goods-details/goods-details?id=' + id
    })
  },
  goSearch() {
    wx.setStorageSync('companyId', this.data.companyId);
    wx.setStorageSync('searchType', 2);
    wx.navigateTo({
      url: '/pages/goods-search/goods-search'
    })
  },
  selectNav(e) {
    let index = e.target.dataset.index;
    this.setData({ 
      goodsNavIndex: index,
      pageSize: 6
    });
    if (index == 0) { this.getHotGoodsList();}
    if (index == 1) { this.getNewGoodsList();}
    if (index > 1) {
      let arr = [];
      arr.push(this.data.goodsNavList[index].categoryCode);
      this.setData({
        code: arr
      });
      this.getGoods(0);
    }
  },
  // 获取新品
  getNewGoodsList() {
    API.getNewGoodsList({ companyId: this.data.companyId }).then(res => {
      if (res.obj) {
        this.setData({ goodsList: res.obj });
      }
    });
  },
  // 获取热售商品
  getHotGoodsList() {
    API.getHotGoodsList({ companyId: this.data.companyId }).then(res => {
      if (res.obj) {
        this.setData({ goodsList: res.obj });
      }
    });
  },
  // 获取分类商品
  getGoods(number){
    API.getGoodsFullsearch({
      pageVo: { pageSize: this.data.pageSize, currentPage: 1},
      sxwProductBaseConditionVo: {
        productThreeCategoryLongCodes: this.data.code,
        companyId: this.data.companyId
      },
      isAggregationCategory: number
    }).then(res => {
      if (number == 1) {
        let arr = [{ categoryName: '本店热售' }, { categoryName: '新品上市' }]
        if (res.obj.aggs) {
          arr = arr.concat(res.obj.aggs);
          this.setData({ goodsNavList: arr });
        } else {
          this.setData({ goodsNavList: arr });
        }
      }
      if (number == 0) {
        if (res.obj.obj) {
          this.setData({ 
            goodsList: res.obj.obj,
            total: res.total
          });
        }
      }
    });
  },
  getDetail() {
    API.getCompanyDetails({ shopId: parseInt(this.data.id), platformValue: 2 })
    .then(res => {
      if (res.code == 200) {
        res.data.shopIntroduced ?  res.data.shopIntroduced.replace(/\<img/gi, '<img style="width:97%;height:auto" ') : ''
        this.setData({ detail: res.data, companyId: res.data.companyId })
        this.getGoods(1);
        this.getHotGoodsList();
      }
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
    this.data.isBack = true;
    console.log(this.data.isBack, "详情页面");
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
    // setTimeout(() => { 
    //   if (this.data.isBack) {
    //     this.data.isBack = false;
    //     if (wx.getStorageSync('shopType') == 1) {
    //       console.log("详情页面", wx.getStorageSync('shopType'))
    //       wx.navigateTo({ url: `/pages/brandHall/bHSearchRes/bHSearchRes` });
    //     } else {
    //       wx.switchTab({ url: '/pages/brandHall/bHIndex/bHIndex' })
    //     }
    //   }
    // }, 100)
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
    if (this.data.goodsNavIndex > 1) {
      if (this.data.total > this.data.pageSize) {
        let i = this.data.pageSize;
        i = i + 6;
        this.setData({ pageSize: i });
        this.getGoods(0);
      }
    }
  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function () {
  
  },
  setKeyword:function(e){
    this.setData({
      keyword: e.detail.value
    })
  },
  doSearch:function(){
    
  },
  toHref:function(e){
    var sHref = e.currentTarget.dataset.href;
    if(sHref==undefined)
      return;
    wx.navigateTo({
      url: sHref,
    })  
  },
  toggleClassify: function () {
    var This = this;
    This.setData({
      isClassifySlideTop: This.data.isClassifySlideDown,
      isClassifySlideDown: This.data.isClassifySlideTop,
      isShowClassify: !This.data.isShowClassify
    })
  },
  callService(e){
    let phone = e.currentTarget.dataset.phone;
    wx.makePhoneCall({
      phoneNumber: phone,
    })
  }
})