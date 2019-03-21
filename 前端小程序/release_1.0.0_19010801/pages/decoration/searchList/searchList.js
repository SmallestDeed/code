// pages/decoration/searchList/searchList.js
// pages/decoration/supplyDetail/supplyDetail.js
import { resourcePath, defaultImg} from '../../../utils/config.js'
let $App = getApp(), API = getApp().API
Page({

  /**
   * 页面的初始数据
   */
  data: {
    defaultImg: defaultImg,
    id: '',
    supTypeId: 0,
    supType: 'new',
    cityItem:'',
    supcurPage: 1,
    supPageSize: 10,
    type: 1,
    supplyList: [],
    resourcePath: resourcePath,
    supplyIcon: $App.staticImageUrl+'home_label_supply.png',
    demandIcon: $App.staticImageUrl+'home_label_demand.png',
    
    businessType:'',
   
    sortFlag: false,
    keyWord:'',
    sortArr: [
      {
        id: 0,
        name: '最新'
      },
      {
        id: 1,
        name: '最热'
      }
    ],
    classtyFlag: false,
    classtyArr: [],
    classtyValue: 0,
    professionFlag: false,
    professionArr: [],
    professionValue: 0,
    
    
    sort: "",
    exampleList: [],
    thiSameCityPageSize: 5, // 搜索同城服务列表接口 
    thiSameCityTotal: 0
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    wx.hideShareMenu();
    console.log(options, 'wqwq')
    this.setData({
      type:options.type,
      id: options.id,
    })
    if(options.id){
      this.setData({
        id:options.id
      })
    }
    if(options.keyWord){
      this.setData({
        keyWord:options.keyWord
      })
    }
    
    if(options.cityItem){
      let item = JSON.parse(options.cityItem) 
    this.setData({
      cityItem: item
    })
    }else{
    this.getCityData();
    }
    console.log("1233", this.data.cityItem.areaName)
    if(this.data.type!="service"){
      this.getsupplyinfo();
      this.getOptions();
    }else{
      this.getList();
    }
  },
  getList() { // 同城服务搜索
    API.getCompanyShopList({
      pageNo: 1,
      pageSize: this.data.thiSameCityPageSize,
      businessType: this.data.businessType,
      shopName: this.data.keyWord,
      cityCode: this.data.cityItem.areaCode,
      orderBy: this.data.sort,
      platformType: 2
    })
    .then(res => {
      if (res.code == 200) {
        if (res.data.list) {
          this.setData({ exampleList: res.data.list, thiSameCityTotal: res.data.count })
        } else {
          this.setData({ exampleList: [] })
        }
      } else if (res.code == 400) {
        this.setData({ exampleList: [] })
      }
    })
  },
  getOptions() {
    API.getAllSupplyDemandCategory({ type: 1, categoryId: 2 })
    .then(res => {
      this.setData({ classtyArr: res.obj })
      
      let data={
        id:'',
        level:'',
        name:'不限',
        picPath:null,
        pid:'',
        supplyDemandCategoryVos:null
      }
      let arr = this.data.classtyArr;
      arr.splice(0, 0, data)
      this.setData({
        classtyArr:arr
      })
      for (let i = 0; i < res.obj.length; i++) {
        let a = res.obj[i].pid + "," + res.obj[i].id
        if (this.data.id == a) {
          this.setData({ classtyValue: i })
        }
      }
    })
    
    API.getAllSupplyDemandRoles({ type: 1 })
    .then(res => {
      let i = 0, arr = []
      for (var item in res.obj) {
        arr[i] = { name: item, id: res.obj[item]}
        i++;
      }
      this.setData({ professionArr: arr })
      console.log("1213",this.data.professionArr)
      let data = {
        id: '',
        name: '不限'
      }
      let arry = this.data.professionArr;
      arry.splice(0, 0, data)
      this.setData({
        professionArr: arry
      })
    })
  },
  getsupplyinfo() {
    let obj = null
    this.data.supType == 'new' ? (this.data.cityItem.areaCode!='' ? obj = { city: this.data.cityItem.areaCode, isSortByGmtModified: 'desc' } : obj = {
      isSortByGmtModified: 'desc'
    }) : (this.data.cityItem.areaCode != '' ? obj = { isSortByViewNum: 'desc', city: this.data.cityItem.areaCode } : obj = {
      isSortByViewNum: 'desc'
    })
    let data = {}
    if (this.data.id != null && this.data.id != ''){
      data={
        curPage: this.data.supcurPage,
        pageSize: this.data.supPageSize,
        supplyDemandCategoryId: this.data.id,
        type: this.data.type == 'service' ? 2 : 1,
        title: this.data.keyWord
      }
    }else{
      data={
      curPage: this.data.supcurPage,
      pageSize: this.data.supPageSize,
      type: this.data.type == 'service' ? 2 : 1,
      title: this.data.keyWord
      }
    }

    API.getallsupplydemandinfo(Object.assign(data, obj))
    .then(res => {
      if (res.obj != null) {
        for (let i = 0; i < res.obj.length; i++) {
          if (res.obj[i].supplyDemandPicList.length >= 2) {
            res.obj[i].supplyDemandPicList = res.obj[i].supplyDemandPicList.slice(0, 3)
          }
        }
        this.setData({ supplyList: res.obj })
      } else {
        this.setData({ supplyList: [] })
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
    if (this.data.type != "service") {
      this.getsupplyinfo();
      this.getOptions();
    } else {
      this.getList();
    }
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
    if (this.data.type != "service") {
      this.setData({
        supPageSize: this.data.supPageSize + 5
      })
      this.getsupplyinfo();
    } else {
      if (this.data.exampleList.length < this.data.thiSameCityTotal) {
        this.setData({
          thiSameCityPageSize: this.data.thiSameCityPageSize + 5
        })
        this.getList();
      }
    }
  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function () {

  },
  toDetail(e) {
    let id = e.currentTarget.dataset.id
    wx.navigateTo({
      url: '../supplyDetail/supplyDetail?id=' + id,
    })
  },
  changeType(e) {
    let type = e.currentTarget.dataset.type
    this.setData({
      type: type
    })
    this.getsupplyinfo();
  },
  sortChange(e) {
    const val = e.detail.value
    this.setData({
      sortValue: val
    })
  },
  sortCommit(e) {
    let id = e.currentTarget.dataset.id
    this.setData({
      supTypeId: id
    })

    if (this.data.supTypeId == 0) {
      this.setData({
        supType: 'new'
      })
    } else {
      this.setData({
        supType: 'hot'
      })
    }

    this.setData({
      sortFlag: false
    })
    this.getsupplyinfo();
  },
  
 
  getCityData() {
    
    let data={
      areaCode: '',
      areaName: '全部',
      baseAreaVos: [{
        areaCode: '',
        areaName: '全部',
        baseAreaVos: [{
          areaCode: '',
          areaName: '全部',
          baseAreaVos: null,
          id: null,
          leveId: 3,
          pid: ''
        }
        ],
        id: 9999,
        levelId: 2,
        pid: '0000000'
      }],
      id: '00000',
      levelId: 1,
      pid: 0,
    }
    this.setData({
      cityItem: data
    })
  },
  changeCtiy: function (e) {
    wx.navigateTo({
      url: '/pages/chooseAddress/chooseAddress?cityItem='+JSON.stringify(this.data.cityItem),
    })
  },
  changeSort() {
    this.setData({
      condition: false,
      classtyFlag: false,
      sortFlag: !this.data.sortFlag,
      professionFlag: false
    })
  },
  changeClassty() {
    this.setData({
      classtyFlag: !this.data.classtyFlag,
      sortFlag: false,
      professionFlag: false
    })
  },
  classtyCommit(e) {
    let id = e.currentTarget.dataset.id
    let pid = e.currentTarget.dataset.pid
    
    if(id!=''&&pid!=''){
      this.setData({
        id: pid + "," + id
      })
    }else{
      this.setData({
        id:''
      })
    }
    this.setData({
      classtyFlag: false,
      classtyValue: e.currentTarget.dataset.index
    })
    this.getsupplyinfo();
  },
  changeProfession() {
    this.setData({
      condition: false,
      classtyFlag: false,
      sortFlag: false,
      professionFlag: !this.data.professionFlag
    })
  },
  professionCommit(e) {
    let id = e.currentTarget.dataset.id
    this.setData({
      id: id,
      professionFlag: false,
      professionValue: e.currentTarget.dataset.index
    })
    this.getsupplyinfo();
    console.log("1111", this.data.uid)
  },
  input(e) {
    this.setData({
      keyWord: e.detail.value
    })
  },
  doSearch() {
    this.setData({
      supPageSize: 5,
      thiSameCityPageSize: 5
    })
    if (this.data.type == 'service') {
      this.getList();
    } else {
      this.getsupplyinfo();
    }
  },
  changeNewHot(e){
    let sort = e.currentTarget.dataset.sort;
    this.setData({
      sort:sort
    })
    this.getList()
  },
toCompany(e) {
    let id = e.currentTarget.dataset.id;
    wx.navigateTo({
      url: '/pages/decoration/companyDetail/companyDetail?id=' + id,
    })
  }
})