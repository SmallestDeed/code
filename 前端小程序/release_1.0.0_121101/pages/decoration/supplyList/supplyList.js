// pages/decoration/supplyDetail/supplyDetail.js
import { resourcePath } from '../../../utils/config.js'
let $App = getApp(), API = getApp().API
Page({

  /**
   * 页面的初始数据
   */
  data: {
    isLoad: true,
    isShowUndefined: false,
    promulgatorId: '',
    isShowRankBox: false,
    isShowPromulgatorBox: false,
    isShowSiteBox: false,
    isProvince: false,
    id:'',
    supTypeId:0,
    supType:'new',
    supcurPage:1,
    supPageSize:10,
    type:1,
    supplyList:[],
    resourcePath: resourcePath,
    supplyIcon: '/static/image/home_label_supply.png',
    demandIcon: '/static/image/home_label_demand.png',
    commonCityList: [{ code: "", name: "深圳" }, { code: "", name: "中山" }],
    hotCityList: [
      { code: '', name: '全国' }, { code: '', name: '北京' }, { code: '', name: '上海' },
      { code: '', name: '杭州' }, { code: '', name: '深圳' }, { code: '', name: '广州' },
      { code: '', name: '成都' }, { code: '', name: '南京' }, { code: '', name: '武汉' },
      { code: '', name: '天津' }, { code: '', name: '西安' }, { code: '', name: '苏州' }
    ],
    districtCode: '',
    cityCode: '',
    cityData: [],
    provinces: [],
    province: "",
    provinceCode: '',
    citys: [],
    city: '深圳市',
    countys: [],
    county: '',
    value: [0, 0, 0],
    threeLevelValue: [0, 0, 0],
    condition: false,
    cityCode: 440300,
    sortFlag:false,
    sortArr:[
      {id:0, 
        name:'最新排序'
      },
      {
        id:1,
        name:'最热排序'
      }
    ],
    classtyFlag:false,
    classtyArr:[
      {
        id:'2,5',
        name:'装修报价'
      },
      {
        id: '2,6',
        name: '房屋测绘'
      },
      {
        id: '2,7',
        name: '房屋改造'
      },
      {
        id: '2,8',
        name: '现场施工'
      },
      {
        id: '2,9',
        name: '施工监理'
      },
    ],
    classtyValue:0,
    professionFlag:false,
    professionArr:[
      {
        id: '',
        name: '设计师'
      },
      {
        id: '6',
        name: '设计师'
      },
      {
        id: '4,14',
        name: '装修人员'
      }
    ],
    professionValue:0
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function () {
      new $App.newNav() // 注册快速导航组件
    this.setData({ 
      id: wx.getStorageSync('cityObj').id, 
      city: wx.getStorageSync('cityObj').city, 
      cityCode: wx.getStorageSync('cityObj').cityCode,
      // provinceCode: this.selectCode().areaCode,
      // citys: this.selectCode().baseAreaVos
    })
    // this.selectCode(wx.getStorageSync('cityData'));return;
    this.getsupplyinfo(wx.getStorageSync('cityObj').id);
    this.getCityData();
    this.getOptions();
    // this.selectCode();

    setTimeout(() => {
     this.selectCode();
    }, 3000);
    wx.setNavigationBarTitle({
      title: wx.getStorageSync('cityObj').title
    });
  },
  selectCode() {
    let cityData = wx.getStorageSync('cityData');
      cityData[34] = {
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
              }],
              id: 9999,
              levelId: 2,
              pid: '0000000'
          }],
          id: '00000',
          levelId: 1,
          pid: 0,
          areaNamePinyin: ''
      }
      let j = cityData[34]
      for (let i = 34; i > 0; i--) {
          cityData[i] = cityData[i - 1]
      }
      cityData[0] = j
      this.setData({
          provinces: cityData
      })
    for (let i = 0; i < cityData.length; i++) {
      for (let j = 0; j < cityData[i].baseAreaVos.length; j++) {
        if (cityData[i].baseAreaVos[j].areaCode == this.data.cityCode) {
          this.setData({

            provinceCode: cityData[i].areaCode,
            citys: cityData[i].baseAreaVos
          })
          return;
        }
      }
    }
  },
  select(e) {
    let option = e.currentTarget.dataset.type;
    console.log(e.currentTarget);
    if (option == "排序") {
      this.setData({ 
        isShowRankBox: !this.data.isShowRankBox,
        isShowSiteBox:false,
        isShowPromulgatorBox:false
      });
    }
    if (option == "地区") {
      this.setData({
        isShowRankBox: false,
        isShowSiteBox: !this.data.isShowSiteBox,
        isShowPromulgatorBox: false
      });
    }
    if (option == "发布者") {
      this.setData({
        isShowRankBox: false,
        isShowSiteBox: false,
        isShowPromulgatorBox: !this.data.isShowPromulgatorBox
      });
    }
  },
  // 选择排序
  selectRank(e) {
    let id = e.currentTarget.dataset.id;

  },
  // 选择省
  selectProvince(e) {
    console.log(e.target.dataset.item);
    let item = e.target.dataset.item;
    this.setData({
      provinceCode: item.areaCode,
      citys: item.baseAreaVos,
      province: item.areaName
    });
  },
  // 选择市
  selectCity(e) {
    let item = e.target.dataset.item;
    this.setData({
      province: this.data.province,
      city: item.areaName,
      cityCode: item.areaCode,
      isShowSiteBox: false
    })
    wx.setStorageSync('nowCity', item);
    this.getsupplyinfo();
  },
  getOptions() {
    API.getAllSupplyDemandCategory({ type: 1, categoryId: 2})
    .then(res => {
      this.setData({ classtyArr: res.obj })
      for (let i = 0; i < res.obj.length; i++) {
        let a = res.obj[i].pid + "," + res.obj[i].id
        if (this.data.id == a) {
          this.setData({
            classtyValue: i
          })
        }
      }
    })
    API.getAllSupplyDemandRoles({ type: 1})
    .then(res => {
      let i = 0, arr = [{name: '全部', id: ''}]
      for (let item in res.obj) {
          arr[i+1] = {
            name: item,
            id: res.obj[item]
          }
        i++;
      }
      this.setData({ professionArr: arr })
    })    
  },
  getsupplyinfo(id) {
    let obj = null;
    // let obj = null, pid = this.data.classtyArr[this.data.classtyValue].pid + ',' + this.data.classtyArr[this.data.classtyValue].id
    // console.log(this.data.classtyValue, this.data.classtyArr, 'wq')    
    if (this.data.supType == 'new') {
      this.data.cityCode == '' ? obj = {} : obj = { city: this.data.cityCode}
    } else {
      this.data.cityCode == '' ? obj = {} : obj = { city: this.data.cityCode}
    }
    API.getallsupplydemandinfo(
      Object.assign({
        curPage: this.data.supcurPage,
        pageSize: this.data.supPageSize,
        isSortByViewNum: this.data.supTypeId == 1 ? 'desc' : undefined, // 最热
        isSortByGmtModified: this.data.supTypeId == 0 ? 'desc' : undefined, // 最新
        supplyDemandCategoryId: this.data.id,
        type: this.data.type,
        publisherType: this.data.promulgatorId != '' ? this.data.promulgatorId : undefined // 发布者
        // publisherType: this.data.professionArr[this.data.professionValue].id // 发布者
      }, obj)
    )
    .then(res => {
      if (res.obj != null) {
        for (let i = 0; i < res.obj.length; i++) {
          if (res.obj[i].supplyDemandPicList.length >= 0) {
            res.obj[i].onePicture = res.obj[i].supplyDemandPicList[0].picPath
          }
        }
        this.setData({ 
          supplyList: res.obj,
          isShowUndefined: false,
        })
      } else {
        this.setData({ 
          supplyList: [],
          isShowUndefined: true
        });
      }
      // if (this.data.isLoad) {
      //   setTimeout(() => {
      //     this.setData({ 
      //       provinces: wx.getStorageSync('cityData'),
      //       isLoad: false
      //     })
      //   }, 3000);
      // }
    })
  
  },
  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function () {
    // setTimeout(() => {
    //   this.setData({ provinces: wx.getStorageSync('cityData') })
    // }, 3000);
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

  onReachBottom: function () {

      this.setData({
        supPageSize: this.data.supPageSize+5
      })
      this.getsupplyinfo();
  },

  onShareAppMessage: function () {
  
  },
  toDetail(e){
    let id = e.currentTarget.dataset.id
    wx.navigateTo({
      url: '../supplyDetail/supplyDetail?id='+id,
    })
  },
  changeType(e){
    let type = e.currentTarget.dataset.type
    this.setData({
      type:type
    })
    this.getsupplyinfo();
  },
  sortChange(e){
    const val=e.detail.value
    this.setData({
      sortValue:val
    })
  },
  sortCommit(e){
    let id=e.currentTarget.dataset.id
    this.setData({
      supTypeId:id
    })
    if (this.data.supTypeId==0){
      this.setData({
        supType:'new'
      }) 
    }else{
      this.setData({
        supType:'hot'
      }) 
    }
    this.setData({
      isShowRankBox: false
    })
    this.getsupplyinfo();
  },
  bindChange: function (e) {

    const val = e.detail.value

    const temp = this.data.threeLevelValue
    if (temp[0] !== val[0]) {
      this.setData({
        value: [val[0], 0, 0]
      })
      val[1] = 0
    } else if (temp[1] !== val[1]) {
      this.setData({
        value: [val[0], val[1], 0]
      })
      val[2] = 0
    }
    this.setData({
      citys: this.data.cityData[val[0]].baseAreaVos,
      cityCode: this.data.cityData[val[0]].baseAreaVos[val[1]].areaCode,
      countys: this.data.cityData[val[0]].baseAreaVos[val[1]].baseAreaVos,
      threeLevelValue: val
    })
    console.log(this.data.cityCode)
  },
  provincialLinkageHide(e) { // 确认地址接口
    let flag = e.currentTarget.dataset.flag, val = this.data.threeLevelValue
    this.setData({
      condition: false
    })
    if (flag) {
      this.setData({
        province: this.data.cityData[val[0]].areaName,
        city: this.data.cityData[val[0]].baseAreaVos[val[1]].areaName,
        cityCode: this.data.cityData[val[0]].baseAreaVos[val[1]].areaCode,
        value: this.data.threeLevelValue
      })
      console.log(this.data.cityCode)
      this.getsupplyinfo();
    }

  },
  getCityData() {
    let cityData = $App.cityDataFilter(wx.getStorageSync('cityData'))
    let provinces = []
    cityData[34] = {
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
    let j = cityData[34]
    for (let i = 34; i > 0; i--) {
      cityData[i] = cityData[i - 1]
    }
    cityData[0] = j
    console.log('123132-----', cityData)
    $App.myForEach(cityData, (value) => {
      provinces.push({
        areaCode: value.areaCode,
        areaName: value.areaName,
        id: value.id,
        levelId: value.levelId,
        pid: value.pid
      })
    })
    this.setData({
      provinces: provinces,
      citys: cityData[0].baseAreaVos,
      countys: cityData[0].baseAreaVos[0].baseAreaVos
    })
    this.data.cityData = cityData
  },
  changeCtiy: function (e) {
    this.setData({
      condition: !this.data.condition,
      sortFlag:false,
      classtyFlag:false,
      professionFlag:false
    })
  },
  changeSort(){
    this.setData({
      sortFlag:!this.data.sortFlag,
      classtyFlag: false,
      professionFlag: false,
      condition: false,
    })
  },
  changeClassty(){
    this.setData({
      classtyFlag: !this.data.classtyFlag,
      professionFlag: false,
      condition: false,
      sortFlag: false,
    })
  },
  classtyCommit(e){
    let id = e.currentTarget.dataset.id
    let pid = e.currentTarget.dataset.pid
    this.setData({
      id:pid+","+id,
      classtyFlag:false,
      classtyValue: e.currentTarget.dataset.index
    })
    this.getsupplyinfo();
  },
  changeProfession(){
    this.setData({
      professionFlag: !this.data.professionFlag,
      condition: false,
      sortFlag: false,
      classtyFlag:false
    })
  },
  professionCommit(e){
    let item = e.currentTarget.dataset.item
    this.setData({
      promulgatorId : item.id,
      isShowPromulgatorBox: false,
      professionValue: e.currentTarget.dataset.index
    })
    this.getsupplyinfo();
  },


})