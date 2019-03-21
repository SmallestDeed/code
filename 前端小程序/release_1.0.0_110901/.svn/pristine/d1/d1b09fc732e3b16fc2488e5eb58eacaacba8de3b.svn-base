
let resourcePath = getApp().resourcePath, myForEach = getApp().myForEach, API = getApp().API
let $App = getApp()
Page({

  /**
   * 页面的初始数据
   */
  data: {
    resourcePath: resourcePath,
    isShowSearchRecord: true,
    ifShowToTopLogo: false,
    noresult: true,
    isShowSearch: true,
    hotList: [],
    historyList: [],
    searchType: '',
    searchBrandList: [],
    firstCategoryIds: 1000,
    brandNavList: [{ name: '建材', code: 1000 }, { name: '家居', code: 2000}, {name: '电器', code: 4000}],
    brandNavIndex: 0,
    filtrateList: [
      {
        name: '品牌',
        list: ['诺克照明', '左右沙发', '雷士照明', '诺克萨斯之手']
      },
      {
        name: '品牌',
        list: ['诺克照明', '左右沙发', '雷士照明', '我是诺克萨斯之手']
      },
      {
        name: '品牌',
        list: ['诺克照明', '左右沙发', '雷士照明', '我是诺克萨斯之手']
      },
      {
        name: '品牌',
        list: ['诺克照明', '左右沙发', '雷士照明', '我是诺克萨斯之手']
      },
      {
        name: '品牌',
        list: ['诺克照明', '左右沙发', '雷士照明', '我是诺克萨斯之手']
      }
    ],
    filtrateActive: [false,false],
    secondFiltrateActive: [-1 ,-1],
    filtrateBoxShow: false,
    goodsList: [],
    childGoodsFiltrateList: [], // 第四级第五级筛选列表
    parentGoodsFiltrateList: [], // 第三级筛选列表
    searchName: '',
    getGoodsListPageSize: 10,
    getGoodsListTotal: 0,
    parentFiltrateBoxShow: false,
    allFiltrateList: [],
    parentFiltrateListActive: 0,
    threeCodeList: [],
    fiveCodeList: []
  },
  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
      new $App.newNav() // 注册快速导航组件
    wx.setNavigationBarTitle({
      title: wx.getStorageSync('searchType') == 1 ? '品牌搜索' : '产品搜索'
    });
    this.setData({ 
      // searchName: wx.getStorageSync('searchName'),
      searchType: wx.getStorageSync('searchType'),
      });
    // this.beginSearchGoodsList();
    this.searchTypeList();
  },
  inputName(e) {
    this.data.searchName = e.detail.value;
  },
  selectNav(e) {
    let index = e.target.dataset.id;
    this.setData({
      brandNavIndex: index,
      firstCategoryIds: this.data.brandNavList[index].code
    });
    this.beginSearchGoodsList();
  },
  // 删除历史记录
  historyDelete() {
    this.setData({ historyList: []});
    if (wx.getStorageSync('searchType') == 0) {
      wx.setStorageSync('productSearchRecord', []);
    }
    if (wx.getStorageSync('searchType') == 1) {
       wx.setStorageSync('brandSearchRecord', []);
    }
    if (wx.getStorageSync('searchType') == 2) {
      wx.setStorageSync('shopProductSearchRecord', []);
    }
    console.log(this.data.historyList);
  },
  // 配置搜索选项 arr: 热门搜索列表；searchRecordArr：搜索历史列表
  searchTypeList() {
    let arr, searchRecordArr;
    if (wx.getStorageSync('searchType') == 0) {
      arr = ['左右沙发', '布艺沙发', '餐桌', '木地板', '电视柜'];
      searchRecordArr = wx.getStorageSync('productSearchRecord');
    }
    if (wx.getStorageSync('searchType') == 1) {
      arr = ['左右', '欧派', '名禾', '生活家', '诺克'];
      searchRecordArr = wx.getStorageSync('brandSearchRecord');
    }
    if (wx.getStorageSync('searchType') == 2) {
      arr = ['左右沙发', '布艺沙发', '餐桌', '木地板', '电视柜'];
      searchRecordArr = wx.getStorageSync('shopProductSearchRecord');
    }
    this.setData({
      historyList: searchRecordArr ? searchRecordArr : [],
      searchType: wx.getStorageSync('searchType'),
      hotList: arr ? arr : []
    });
  },
  // onPageScroll(e) {
  //   e.scrollTop > 200 ? this.setData({ ifShowToTopLogo: true }) : this.setData({ ifShowToTopLogo: false })
  // },
  goSearch() {
    this.searchTypeList();
    this.setData({
      isShowSearchRecord: true,
    });
  },
  // goTop() {
  //   this.setData({ ifShowToTopLogo: false })
  //   wx.pageScrollTo({
  //     scrollTop: 0,
  //     duration: 300
  //   });
  // },
  changeSearName(e) {
    let name = e.detail.value
    this.setData({
      searchName: name
    })
  },
  search() { // 搜索调用
    this.setData({
      getGoodsListPageSize: 10,
      filtrateBoxShow: false,
      parentFiltrateBoxShow: false,
      isShowSearch: false,
      fiveCodeList: [],
      threeCodeList: []
    })
    wx.setStorageSync('searchName', this.data.searchName);
    let arr;
    if (this.data.searchType == 0) {
      this.getGoodsList('begin') // 传入参数，初始化筛选条件
      if (wx.getStorageSync('productSearchRecord')) {
        arr = wx.getStorageSync('productSearchRecord');
        for (let i = 0, len = arr.length; i < len; i++) {
          if (arr[i] == this.data.searchName) { return; }
        }
      } else {
        arr = [];
      }
      arr.push(this.data.searchName);
      wx.setStorageSync('productSearchRecord', arr);
    }
    if (this.data.searchType == 1) {
      this.getBrandList(this.data.searchName);
      if (wx.getStorageSync('brandSearchRecord')) {
        arr = wx.getStorageSync('brandSearchRecord');
        for (let i = 0, len = arr.length; i < len; i++) {
          if (arr[i] == this.data.searchName) { return; }
        }
      } else {
        arr = [];
      }
      arr.push(this.data.searchName);
      wx.setStorageSync('brandSearchRecord', arr);
    }
    if (this.data.searchType == 2) {
      this.getGoodsList('begin') // 传入参数，初始化筛选条件
      if (wx.getStorageSync('shopProductSearchRecord')) {
        arr = wx.getStorageSync('shopProductSearchRecord');
        for (let i = 0, len = arr.length; i < len; i++) {
          if (arr[i] == this.data.searchName) { return; }
        }
      } else {
        arr = [];
      }
      arr.push(this.data.searchName);
      wx.setStorageSync('shopProductSearchRecord', arr);
    }
  },
  beginSearchGoodsList(e) { // 开始搜索
    if (e && e.detail.value) {
      this.setData({
        searchName: e.detail.value
      });
    }
    if (e && e.target.dataset.id) {
      this.setData({ searchName: e.target.dataset.id });
    } else if (!this.data.searchName) {// 为空时不搜索
      return;
    }
    this.setData({ isShowSearchRecord: false });
    this.search();
  },
  getBrandList(type) { // 获取品牌列表
    API.getCompanyShopList({
      pageNo: 1,
      pageSize: 10,
      businessType: 1,
      shopName: this.data.shopName,
      cityCode: '',
      orderBy: '',
      platformType: 2,
      orderBySql: 'a.gmt_create',
      firstCategoryIds: this.data.firstCategoryIds,
      shopName: type
    }).then(res => {
      if (res.code == 200) {
        if (type != '') {
          this.setData({ noresult: true });
        }
        this.setData({ searchBrandList: res.data.list});
        console.log(this.data.searchBrandList);
      }
      if (res.code == 400) {
        if (type != '') {
          this.setData({ noresult: false });
          this.getBrandList('');
        }
      }
    });
  },
  getGoodsList(type) { // 获取商品列表
    let companyId = wx.getStorageSync('companyId');
    console.log(companyId);
    API.getGoodsFullsearch({
      "sxwProductBaseConditionVo": {
        "companyId": companyId != '' ? companyId : undefined,
        "searchKeyword": wx.getStorageSync('searchName'),
        productThreeCategoryLongCodes: this.data.threeCodeList,
        productFiveCategoryLongCodes: this.data.fiveCodeList,
      },
      "pageVo": {
        "pageSize": this.data.getGoodsListPageSize,
        "currentPage": 1,
      },
      isAggregationCategory: 1
    })
      .then(res => {
        if (res.returnCode === '000000') {
          if (res.obj) {
            let parentArr = [{
              categoryCode: '',
              categoryName: '全部',
              categoryOrder: 0,
              productSmallTypeValue: 0,
              productType: '',
              sortOrder: 0
            }]
            if (type == 'begin') {
              myForEach(res.obj.aggs, (value) => {
                parentArr.push({
                  categoryCode: value.categoryCode,
                  categoryName: value.categoryName,
                  categoryOrder: value.categoryOrder,
                  productSmallTypeValue: value.productSmallTypeValue,
                  productType: value.productType,
                  sortOrder: value.sortOrder
                })
              })
              this.setData({
                parentGoodsFiltrateList: parentArr,
                allFiltrateList: res.obj.aggs,
                childGoodsFiltrateList: []
              })
            }
            this.setData({
              goodsList: res.obj.obj,
              getGoodsListTotal: res.total,
              noresult: true
            })
          } else {
            this.setData({
              goodsList: [],
              noresult: true,
              getGoodsListTotal: 0,
              parentGoodsFiltrateList: []
            })
          }
        } else if (res.returnCode === '000010') { // 无数据时展示猜你喜欢
          this.setData({
            goodsList: res.obj.obj,
            childGoodsFiltrateList: [],
            noresult: false,
            getGoodsListTotal: 0,
            parentGoodsFiltrateList: []
          })
        } else {
          this.setData({
            goodsList: [],
            childGoodsFiltrateList: [],
            noresult: true,
            getGoodsListTotal: 0,
            parentGoodsFiltrateList: []
          })
        }
      })
  },
  switchChildrenFiltrate(e) { // 切换四五级筛选条件
    let index = e.currentTarget.dataset.index 
    if (index == 0) {
      this.setData({
        childGoodsFiltrateList: [],
        parentFiltrateListActive: index,
        parentFiltrateBoxShow: false,
        threeCodeList: [],
        fiveCodeList: [],
        getGoodsListPageSize: 10        
      })
    } else if (index > 0){
      let first = [], second = [], threeCodeList = [],fiveCodeList = [], childGoodsFiltrateList = []
      threeCodeList.push(this.data.allFiltrateList[index - 1].categoryCode)
      myForEach(this.data.allFiltrateList[index - 1].productCategoryVoList, (value) => {
        first.push(false)
        second.push(-1)
      })
      this.setData({
        childGoodsFiltrateList: this.data.allFiltrateList[index - 1].productCategoryVoList,
        filtrateActive: first,
        secondFiltrateActive: second,
        parentFiltrateListActive: index,
        parentFiltrateBoxShow: false,
        threeCodeList: threeCodeList,
        fiveCodeList: [],
        getGoodsListPageSize: 10
      })
    }
    this.getGoodsList() // 获取商品列表
  },
  routerToGoodsDetails(e) { // 跳转至产品详情·1
    let id = e.currentTarget.dataset.id;
    if (wx.getStorageSync('searchType') == 1) {
      wx.navigateTo({
        url: '/pages/brandHall/bHStore/bHStore?id=' + id
      })
    } else {
      wx.navigateTo({
        url: '/pages/goods-details/goods-details?id=' + id
      })
    }
  },
  upDownFristBox(e) { // first焦点
    console.log(e)
    let index = e.currentTarget.dataset.index
    console.log(index, this.data.filtrateActive)
    this.data.filtrateActive[index] = !this.data.filtrateActive[index] 
    this.setData({
      filtrateActive: this.data.filtrateActive
    })
  },
  changeSecondActive(e) { // second焦点
    let index = e.currentTarget.dataset.index,
     childIndex = e.currentTarget.dataset.child, name = e.currentTarget.dataset.name
    // 如果是品牌的话,跳转到字母排序框
    if (name == '更多品牌...') {
      let arr = this.data.childGoodsFiltrateList.find((n) => {return n.categoryName == '品牌'})
      wx.setStorageSync('moreBrandList', arr.productCategoryVoList) // 缓存品牌分类
      wx.navigateTo({
        url: '/pages/search-more-brand/search-more-brand?index='+ index
      })
      return
    }
    if (this.data.secondFiltrateActive[index] == childIndex) {
      this.data.secondFiltrateActive[index] = -1
    } else {
      this.data.secondFiltrateActive[index] = childIndex
    }
    this.setData({
      secondFiltrateActive: this.data.secondFiltrateActive
    })
  },
  showFiltrateBox(e) { // 显示隐藏筛选框
    let index = e.currentTarget.dataset.index
    if (index == 0) {
      this.setData({
        filtrateBoxShow: false,
        parentFiltrateBoxShow: !this.data.parentFiltrateBoxShow
      })
      console.log(index, 'wq')      
    } else if (index == 1){
      if (this.data.childGoodsFiltrateList.length == 0) {
        wx.showToast({
          title: '请选择分类',
          icon: 'none'
        })
        return 
      }
      this.setData({
        filtrateBoxShow: !this.data.filtrateBoxShow,
        parentFiltrateBoxShow: false
      })
    }
  },
  resetSecondFiltrateActive() { // 重置焦点样式
    let arr = [] 
    myForEach(this.data.secondFiltrateActive, (value, index) => {
      arr.push(-1)
    })
    this.setData({
      secondFiltrateActive: arr
    })
  },
  confirmChildFiltrate() { // 确认四五级焦点样式
    let arr = [], flag = false
    myForEach(this.data.secondFiltrateActive, (value, index) => {
      if (value != -1) {
        flag= true
        arr.push(this.data.childGoodsFiltrateList[index].productCategoryVoList[value].categoryCode)
      }
    })
    if (flag) {
      this.data.getGoodsListPageSize = 10       
      this.setData({
        fiveCodeList: arr,
        parentFiltrateBoxShow: false,
        filtrateBoxShow: false
      })
      this.getGoodsList() // 获取商品
    } else {
      wx.showToast({
        title: '请选择分类',
        icon:'none'
      })
    }
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
    console.log(this.data.secondFiltrateActive, 'wq')
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
    console.log(this.data.goodsList.length, this.data.getGoodsListTotal)
    if (this.data.goodsList.length < this.data.getGoodsListTotal) {
      this.setData({
        getGoodsListPageSize: this.data.getGoodsListPageSize + 10 
      })
      this.getGoodsList('end') // 获取商品列表
    }
  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function () {
  
  }
})