// pages/goods-list/goods-list.js
let fetch = getApp().fetch
let myForEach = getApp().myForEach
import { emptyTemplate } from '../../component/emptyTemplate/emptyTemplate'
let $App = getApp()
let codeG = '';
let opt = {};
Page({
  emptyTemplate,
  /**
   * 页面的初始数据
   */
  data: {
    staticImageUrl:getApp().staticImageUrl,
    goodsList: [],
    firstConditions: [],
    goodListheight:'', // 大盒子样式
    goodListOverflow: 'none', // 大盒子样式 
    goodListScreen: false,
    threeClassificationList: [],
    threeClassificationPid: '',
    cacheStatus: false,
    threeClassificationActive: [],
    resourcePath: getApp().resourcePath,
    pageSize: 30,
    totalCount: 0,
    code: '',
    styleList:[],
    optionsList:[],
    brandCode:'',
    brandName:'',
    brandType:'',
    lastTap:[],
    isPresell: '',
    isSpecialOffer: '',
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    opt = options;
    new $App.quickNavigation() // 注册组件
    new this.emptyTemplate() // 注册组件
    wx.setNavigationBarTitle({ title: options.name })
    this.setData({
      threeClassificationPid: options.id,
      threeClassificationCode: options.code,      
      code: options.code
    })
    codeG = options.code||'';
    if (options.type){
      if (options.type == 'presellList'){
        wx.setNavigationBarTitle({
          title: '新品预售',
        })
        this.setData({ isPresell: 1 })
      }else{
        wx.setNavigationBarTitle({
          title: '特卖商品',
        })
        this.setData({ isSpecialOffer: 1 })
      }
    }
    this.data.goodType
    this.getClassification()
    this.getGoodList(this.data.code, 1)
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
    
    let list = this.data.optionsList,
    slist = this.data.threeClassificationActive
    this.data.lastTap = slist;
    let a = { name: '品牌', show: this.data.brandName }
    let b ={name:'品牌',show:'全部'}
    if (this.data.brandCode != '' && this.data.brandType =='confirm') {
    for (let i = 0; i < this.data.threeClassificationList.length;i++){
      if (this.data.threeClassificationList[i].name=='品牌'){
        list[i]=a
      }
    }
    }else{
      for (let i = 0; i < this.data.threeClassificationList.length; i++) {
        if (this.data.threeClassificationList[i].name == '品牌') {
          list[i] = b;
          slist[i]=-1
        }
      }
    }
    this.setData({
      optionsList: list,
      threeClassificationActive: slist
    })
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
      let obj = (opt.code ? ('?code=' + opt.code) : '')
              + (opt.name ? ('&name=' + opt.name) : '') 
              + (opt.id ? ('&id=' + opt.id) :'')
              + (opt.type ? ('&type=' + opt.type) : '')
      return $App.shareAppMessageFn(true,obj);
    }
  },
  linkToGoodsDetails(e) { // 跳转至商品详情
    // console.log(e.currentTarget.dataset.item)
    let spuId = e.currentTarget.dataset.item.spuId
    let goodId = e.currentTarget.dataset.id
   let isAssem = '';
   let status = e.currentTarget.dataset.activitystatus
   let activeId = e.currentTarget.dataset.activityid
   if (status == 1 || status == 2){
     isAssem = true
   }else{
      isAssem = false
   }
    wx.navigateTo({
      url: '../goods-details/goods-details?id=' + goodId + '&isAssemble=' + isAssem + '&groupId=' + activeId + '&spuId=' + spuId
    })
  },
  showScreenBox() { // 筛选框显示
    this.setData({
      goodListScreen: !this.data.goodListScreen
    })
    if (this.data.goodListScreen) {
      let goodListheight = wx.getSystemInfoSync().windowHeight
      this.setData({
        goodListheight: goodListheight + 'px',
        goodListOverflow: 'hidden'
      })
    } else {
      this.setData({
        goodListheight: '',
        goodListOverflow: 'none'
      })
    }  
  },
  getClassification() {
    this.setData({
      threeClassificationList: wx.getStorageSync('threeClassificationList')
    })
    //处理品牌
    
    let brandShowList=[],
        moreObj = { id: 9999999, pid: 999999, name: "更多品牌...", level: 5, code: "toMore" },
        storageList = wx.getStorageSync('threeClassificationList')
    for (let i = 0; i < storageList.length;i++){
      if (storageList[i].name=='品牌'){
        brandShowList = storageList[i];
      }
    }   
    
    if (brandShowList.fourClassificationList&&brandShowList.fourClassificationList.length>12){
      brandShowList.fourClassificationList = brandShowList.fourClassificationList.slice(0, 11);
      brandShowList.fourClassificationList.push(moreObj)
    }
    let threeList = this.data.threeClassificationList;
    for (let i = 0; i < threeList.length;i++){
      if (threeList[i].name == '品牌') {
        threeList[i] = brandShowList
      }
    }
    this.setData({
      threeClassificationList: threeList
    })
    let list=[]
    let olist=[]
    for (let i = 0; i < this.data.threeClassificationList.length;i++){
      list.push(false)
    }
    for (let i = 0; i < this.data.threeClassificationList.length; i++) {
      let a ={name:this.data.threeClassificationList[i].name,show:'全部'}
      olist.push(a)
    }
    this.setData({
      styleList:list,
      optionsList:olist
    })
    // // 三级分类 
    // myForEach(threeClassificationList, (val) => {
    //   if (val.pid == this.data.threeClassificationPid && val.name !== '品牌') {
    //     this.data.threeClassificationList.push(val)
    //   }
    // })
    // // 四级分类
    // myForEach(this.data.threeClassificationList, (valP) => {
    //   valP.fourClassificationList = []
    //   myForEach(fourClassificationList, (valC) => {
    //     if (valP.id === valC.pid) {
    //       valP.fourClassificationList.push(valC)
    //     }
    //   })
    // })
    // 分类焦点
    this.data.threeClassificationActive = []
    myForEach(this.data.threeClassificationList, () => {
      this.data.threeClassificationActive.push(-1)
    })
  },
  fourActiveSwitch(e) {
    
    let indexP = e.currentTarget.dataset.indexp
    let indexC = e.currentTarget.dataset.indexc
    let list = this.data.optionsList
    let lastTap = this.data.lastTap
    let acList = this.data.threeClassificationList[indexP].fourClassificationList[indexC];
    let list3 = this.data.threeClassificationActive;
    let flag = (lastTap[indexP] == indexC && list3[indexP] != -1)
    
    if (acList.code=='toMore'){
      wx.navigateTo({
        url: '/pages/brandFilter/brandFilter',
      })
    }
    
    if (flag){
      this.data.threeClassificationActive[indexP] = -1
      list[indexP].show = '全部'
    }else{
      this.data.threeClassificationActive[indexP] = indexC
      lastTap[indexP] = indexC
      list[indexP].show = acList.name
    }
    this.setData({
      threeClassificationActive: list3,
      optionsList: list,
    })
  },
  confirmClassification() {
    let flag = false
    let code = []
    for (let i = 0; i < this.data.threeClassificationList.length; i++) {
      if (this.data.threeClassificationList[i].name == '品牌' && this.data.threeClassificationActive[i] == 11) {
        code.push(this.data.brandCode)
      }
    }
    myForEach(this.data.threeClassificationActive, (value, index) => {
      if (value !== -1) {
        flag = true
        code.push(this.data.threeClassificationList[index].fourClassificationList[value].code)
      }
    })
    
    for(let i=0;i<code.length;i++){
      if(code[i]=='toMore'){
        code.splice(i,1)
      }
    }
    if (!flag) {
      this.getGoodList(this.data.code,1)
      this.setData({
        goodListScreen:false,
        goodListOverflow: 'none'
      })
    } else {
      this.setData({
        pageSize: 30,
        goodListScreen: false,
        goodListheight: '',
        goodListOverflow: 'none'
      })
      this.setData({
        threeClassificationActive: [-1, -1, -1, -1]
      })
      this.getGoodList(code.join(','))
      let olist = [];
      for (let i = 0; i < this.data.threeClassificationList.length; i++) {
        let a = { name: this.data.threeClassificationList[i].name, show: '全部' }
        olist.push(a)
      }
      this.setData({
        optionsList: olist
      })
    }

  },
  getGoodList(code) { // 商品列表

    var that=this;
    let url = `/goods/basegoods/list`
    fetch(url, 'get',{
      categoryCode: code || '',
      curPage: 1,
      pageSize: this.data.pageSize,
      isPresell: this.data.isPresell,
      isSpecialOffer: this.data.isSpecialOffer
    })
    .then((res) => {
      
      if (res.status) {
        if (res.obj) {
          myForEach(res.obj, (value) => {
            let index = value.salePrice.indexOf('.')
            if (index != -1) {
              value.salePrice = value.salePrice.slice(0, index + 3) 
            }
          })
          
          for(let i=0;i<res.obj.length;i++){
            res.obj[i].picPath = res.obj[i].picPath?that.data.resourcePath + res.obj[i].picPath:'';
          }

          this.setData({
            goodsList: res.obj,
            totalCount: res.totalCount
          })
          this.emptyTemplateShow('hide')
        } else {
          this.setData({
            goodsList: [],
            totalCount: 0
          })
          
          this.emptyTemplateShow('show')
          this.setEmptyTemplateText('空空如也，没有找到相关产品~')
        }
      }
    })
  },
  // getGoodList(code) { // 商品列表
  //   var that = this;
  //   let url = `/product/baseproduct/searchallproduct`
  //   fetch(url, 'get', {
  //     categoryCode: code || '',
  //     curPage: 1,
  //     pageSize: this.data.pageSize
  //   })
  //     .then((res) => {
  //       if (res.status) {
  //         if (res.obj) {
  //           myForEach(res.obj, (value) => {
  //             let index = value.salePrice.indexOf('.')
  //             if (index != -1) {
  //               value.salePrice = value.salePrice.slice(0, index + 3)
  //             }
  //           })

  //           for (let i = 0; i < res.obj.length; i++) {
  //             res.obj[i].picPath = that.data.resourcePath + res.obj[i].picPath
  //           }

  //           this.setData({
  //             goodsList: res.obj,
  //             totalCount: res.totalCount
  //           })

  //           this.emptyTemplateShow('hide')
  //         } else {
  //           this.setData({
  //             goodsList: [],
  //             totalCount: 0
  //           })
  //           wx.showToast({
  //             title: '该分类下没有产品!',
  //             icon: 'none',
  //             duration: 1000
  //           })
  //           this.emptyTemplateShow('show')
  //           this.setEmptyTemplateText('空空如也，没有找到相关产品~')
  //         }
  //       }
  //     })
  // },
  getAllGoodList() {
    this.getGoodList(this.data.code)
  },
  scrollBottom() {
  },
  onReachBottom() { // 底部
    if (this.data.pageSize >= this.data.totalCount || this.data.goodListScreen) {
      return
    } else {
      this.setData({
        pageSize: this.data.pageSize + 30
      })
      this.getGoodList(this.data.code)
    }
  },
  resetActive() { // 重置筛选条件
   this.setData({
     threeClassificationActive: [-1, -1, -1, -1]
   })
   
   let olist=[];
   for (let i = 0; i < this.data.threeClassificationList.length; i++) {
     let a = { name: this.data.threeClassificationList[i].name, show: '全部' }
     olist.push(a)
   }
   this.setData({
     optionsList: olist
   })
  },
  toGoodsDetails() { // 调转到商品详情

  },
  closeGoodListScreen() {
    this.setData({
      goodListScreen: false
    })
  },
  preventBubbling() { // 防止冒泡

  },
  errorFunction: function (e) {
    var _errImg = e.target.dataset.img
    var _errObj = {}
    _errObj[_errImg] = "image/goods_pic_no.png"
    this.setData(_errObj) //注意这里的赋值方式,只是将数据列表中的此项图片路径值替换掉  
  },
  alloptions(e){
    let id = e.currentTarget.dataset.id
    let list = this.data.styleList
    list[id] = !list[id]
    this.setData({
      styleList: list
    })
  }
})