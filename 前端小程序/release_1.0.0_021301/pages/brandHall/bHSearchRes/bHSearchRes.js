// pages/decoration/brandHall/bHIndex/bHIndex.js
import { resourcePath } from '../../../utils/config.js'
import { emptyTemplate } from '../../../component/emptyTemplate/emptyTemplate'
let API = getApp().API, $App = getApp()
Page({
  /**
   * 页面的初始数据
   */
  emptyTemplate,
  data: {
    ifShowToTopLogo: false,
    id:1000,
    imgURL: getApp().data.imgURL,
    resourcePath: resourcePath,
    isShowClassify: false,
    isShowSort: false,
    isClassifySlideDown: false,
    isClassifySlideTop: true,//Top开始都是true down都是false
    isSortSlideDown: false,
    isSortSlideTop: true,
    currClassify: { id: "", name: "全部" },//当前分类的内容 默认是全部{
    currSort: { id: "", name: "销量" },
    pageNo:1,
    noData:false,
    shopName:'',
    cityCode:'',
    isShowSaerch: false,
    hotList: ['左右', '欧派', '名禾', '生活家', '诺克'],
    historyList: [],
    searchValue: '',
    classifyList: [
      {
        name: "全部",
        id: ""
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
      }
    ],
    banners: [
      "http://open.rjhq.cn/xz/wx/images/spppg_banner01.jpg",
      "http://open.rjhq.cn/xz/wx/images/spppg_banner01.jpg",
      "http://open.rjhq.cn/xz/wx/images/spppg_banner01.jpg"
    ],
    isBack: true,
    searchResult:[{}],
    renderList:[
      //这里可以放搜索回来的数据和猜你喜欢的数据
    ]
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    wx.hideShareMenu();
      new $App.newNav() // 注册快速导航组件
    let searchType = '';
    if(options.id){
      this.setData({
        id:options.id
      })
      if (options.id == 1000) {
        searchType = '建材品牌';
      } else if (options.id == 2000) {
        searchType = '家居品牌';
      } else if (options.id == 4000) {
        searchType = '电器品牌';
      }
    }
    wx.setNavigationBarTitle({
      title: searchType
    })
    // var companyName = options.companyName;
    this.setData({
      // companyName: companyName,
      historyList: wx.getStorageSync('brandSearchRecord')
    })
    this.doSearch()
    new this.emptyTemplate()
  },
  onPageScroll(e) {
    e.scrollTop > 200 ? this.setData({ ifShowToTopLogo: true }) : this.setData({ ifShowToTopLogo: false })
  },
  goTop() {
    this.setData({ ifShowToTopLogo: false })
    wx.pageScrollTo({
      scrollTop: 0,
      duration: 300
    });
  },
  getValue(e) {
    this.setData({
      searchValue: e.detail.value
    });
  },
  goSaerch() {
    this.setData({
      isShowSaerch: true,
      historyList: wx.getStorageSync('brandSearchRecord')
    });
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
    this.data.isBack = true
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
    // console.log("品牌列表");
    // if (this.data.isBack) {
    //   this.data.isBack = false;
    //   setTimeout(() => {
    //     wx.switchTab({ url: '/pages/brandHall/bHIndex/bHIndex' });
    //   }, 500);
    // }
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
    this.doSearch();
  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function () {

  },
  toggleSort: function () {
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
  toggleClassify: function () {
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
    wx.setStorageSync('shopType', 1);
    wx.setStorageSync('shopId', e.currentTarget.dataset.id);
    var sHref = e.currentTarget.dataset.href;
    if (sHref == undefined)
      return;
    wx.navigateTo({
      url: sHref,
    })
  },
  setKeyword:function(e){
    var keyword = e.detail.value;
    this.setData({
      shopName: keyword
    })
  },
  historyDelete() {
    this.setData({
      historyList: []
    });
    wx.setStorageSync('brandSearchRecord', []);
  },
  saerchBrand(e) {
    if (e && e.detail.value) {
      this.setData({
        searchValue: e.detail.value
      });
    }
    if (e.target.dataset.id) {
      this.setData({
        searchValue: e.target.dataset.id
      });
    }
    if (this.data.searchValue) {
      this.setData({ isShowSaerch: false });
      this.doSearch(this.data.searchValue);
      let arr = [];
      if (wx.getStorageSync('brandSearchRecord')) {
        arr = wx.getStorageSync('brandSearchRecord');
        for (let i = 0, len = arr.length; i < len; i++) {
          if (arr[i] == this.data.searchValue) { return; }
        }
      } else {
        arr = [];
      }
      arr.push(this.data.searchValue);
      wx.setStorageSync('brandSearchRecord', arr);
    }
  },
  doSearch:function(value){
    API.getCompanyShopList({
      pageNo: 1,
      pageSize: 10,
      businessType: 1,
      shopName: this.data.shopName,
      cityCode: this.data.cityCode,
      orderBy: '',
      firstCategoryIds: this.data.id,
      platformType: 2,
      orderBySql: 'a.gmt_create',
      shopName: value? value : ''
    })
    .then(res => {
      if (res.code == 200) {
        this.setData({ searchResult: res.data.list })
        this.data.searchResult.length > 0 ? this.emptyTemplateShow('hide') : this.emptyTemplateShow('show')
      } else if (res.code == 400) {
        this.setData({ searchResult: [] })
        this.emptyTemplateShow('show')
      }
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
  changeSort: function (e) {
    var orderBy = e.currentTarget.dataset.sort;
    var name = e.currentTarget.dataset.name;
    this.setData({
      currSort: { id: orderBy, name: name }
    })
    this.toggleSort();
  },
  doFilter:function(){
    this.setData({
      pageNo:1,
      searchResult:[]
    })
    this.doSearch();
  },
  changeId(e){
    let id = e.currentTarget.dataset.id;
    this.setData({
      id:id
    })
    console.log(this.data.id)
    this.doSearch();
  }
})