import {
  resourcePath,
  defaultImg
} from '../../../utils/config.js';
import {
  emptyTemplate
} from '../../../component/emptyTemplate/emptyTemplate'
import {
  pinyin
} from '../../../utils/pinyin.js'

let $App = getApp(),
  API = getApp().API
Page({
  emptyTemplate, // 无数据组件
  data: {
    staticImageUrl: getApp().staticImageUrl,
    defaultImg: defaultImg,
    condition: false,
    imgURL: getApp().data.imgURL,
    resourcePath: resourcePath,
    isShowCitys: false,
    isCitySlideTop: true,
    isCitySlideDown: false,
    bannerAutoPlay: true,
    isShowClassify: false,
    isClassifySlideDown: false,
    showSel: false,
    messTxt: false,
    isShow: false,
    showStorage: true,
    isClassifySlideTop: true, //Top开始都是true down都是false
    currClassify: {
      id: "",
      name: "全部"
    }, //当前分类的内容 默认是全部
    recommendTitle: "设计师",
    brandType: "3", //3设计师 5装修公司 6施工单位 2建材家居默认是设计师,
    priceType:3,
    pageNo: 1, //请求数据的页码
    currCity: {},
    pageNo: 1,
    keyword: "",
    popularList: [],
    sortFlag: false,
    sortType: 'all',
    sortType2:'all',
    decorationType: '',
    sortName: '排序',
    pageSize: 10,
    sortFlag2: false,
    sortFlag3: false,
    sortFlag4: false,
    sortName2: '风格',
    sortName3: '费用',
    minValue: '',
    maxValue: '',
    sortName4: '方式',
    optionsType: '',
    history: [],
    sortFlag2Index: 0,
    classifyList: [{
        name: "全部",
        id: ""
      },
      {
        name: "现代简约1",
        id: "1"
      },
      {
        name: "现代简约2",
        id: "2"
      },
      {
        name: "现代简约3",
        id: "3"
      },
      {
        name: "现代简约4",
        id: "4"
      },
      {
        name: "现代简约5",
        id: "5"
      }
    ],
    materialClassify: [{
      "id": 4000,
      "childs": [{
          "id": 4001,
          "name": "大家电",
          "pid": 4000,
          "level": 2
        },
        {
          "id": 4007,
          "name": "厨房电器",
          "pid": 4000,
          "level": 2
        },
        {
          "id": 4015,
          "name": "小家电",
          "pid": 4000,
          "level": 2
        }
      ],
      "name": "电器",
      "pid": 1,
      "level": 1
    }],
    sortName2: '风格',
    styleName: [],
    serviceCitys: [],
    exampleList: [],
    cityData: [],
    provinces: [],
    province: "",
    citys: [],
    city: '全部',
    countys: [],
    county: '',
    value: [0, 0, 0],
    threeLevelValue: [0, 0, 0],
    cityCode: '',
    shareOptions: {},
    fixed: false,
    provinceObj: {},
    cityObj: {},
    showCityView: false,
    choosex: 0,
    showNoMore: false,
    lastX: 0,
    choosey: 0,
    lastY: 0,
    choosez: 0,
    choosep: 0,
    lastP: 0,
    typeList: {
      3: [{
        name: '梁景华'
      }, {
        name: '何宗宪'
      }, {
        name: '肖冰'
      }, {
        name: '宋阳'
      }, {
        name: '王立江'
      }, {
        name: '李志强'
      }],
      4: [{
        name: '上森'
      }, {
        name: '五行'
      }],
      2: [{
        name: '洋江行照明'
      }, {
        name: '圣鼎华光'
      }, {
        name: '典一照明'
      }, {
        name: '水晶宫'
      }, {
        name: '龙华皇家一号'
      }],
      5: [{
        name: '卓家'
      }, {
        name: '百创'
      }],
      6: [],
    },
    hotList: [],
    chooseBool: false,
    caseListheight: '',
    caseListOverflow: 'auto',
    showSel: true,
    ifShowToTopLogo: true,
  },
  // goToTop() { // 回到顶部
  //   //this.setData({ ifShowToTopLogo: false })
  //   wx.pageScrollTo({
  //     scrollTop: 0,
  //     duration: 300
  //   })
  // },
  changeFun: function() {
    let bool = this.data.showCityView
    if (bool) {
      this.closeFun();
    } else {
      this.setData({
        showCityView: true,
        sortFlag: false,
        sortFlag2: false,
        sortFlag3: false,
        caseListheight: wx.getSystemInfoSync().windowHeight * 2 - 88 + 'rpx',
        caseListOverflow: 'hidden',
        showSel: false,
        fixed: true,
      })
    }
  },
  changePro: function(e) {
    let index = e.currentTarget.dataset.idx;
    let indey = e.currentTarget.dataset.idy;
    this.setData({
      choosex: index,
      choosey: indey,
      choosep: (index == this.data.lastX && indey == this.data.lastY) ? this.data.lastY : -1,
      cityObj: this.data.provinceObj[index].objList[indey].baseAreaVos,
    })
  },
  submitFuc: function(e) {
    let index = e.currentTarget.dataset.idx,
      city = this.data.provinceObj[this.data.choosex].objList[this.data.choosey].baseAreaVos[index].areaName;
    city = city.split('').length > 3 ? city.split('')[0] + city.split('')[1] + city.split('')[2] + '...' : city;
    this.setData({
      showCityView: false,
      caseListheight: '',
      padTop: '0',
      sortFlag: false,
      sortFlag2: false,
      caseListOverflow: 'auto',
      lastX: this.data.choosex,
      lastY: this.data.choosey,
      lastP: this.data.choosep,
      city: city,
      fixed: false,
      choosep: index,
      cityCode: this.data.provinceObj[this.data.choosex].objList[this.data.choosey].baseAreaVos[index].areaCode
    })
    wx.setStorageSync('nowCity', this.data.provinceObj[this.data.choosex].objList[this.data.choosey].baseAreaVos[index])
    this.getList();
  },
  closeFun: function() {
    this.setData({
      showCityView: false,
      caseListheight: '',
      caseListOverflow: 'auto',
      fixed: false,
      sortFlag: false,
      sortFlag2: false,
      sortFlag3: false,
      sortFlag4: false,
      choosex: this.data.lastX,
      choosey: this.data.lastY,
      choosep: this.data.lastP,
    })
  },
  showStorageFun: function() {
    this.setData({
      showStorage: true,
    })
  },
  goSearch: function(e) {
    let n = this.data.brandType
    let val = e.currentTarget.dataset.val;
    this.setData({
      keyword: val,
      showStorage: false,
      caseListheight: '',
      caseListOverflow: 'auto',
    })
    this.setHistory(n, val);
    this.getList();
  },
  delHistory: function() {
    let that = this;
    wx.getStorage({
      key: 'hList',
      success: function(res) {
        let hList = res.data;
        hList[that.data.brandType] = [];
        wx.setStorage({
          key: "hList",
          data: hList
        })
        wx.showLoading({
          title: '正在删除...',
        })
        setTimeout(function() {
          wx.hideLoading()
          that.setData({
            history: hList[that.data.brandType]
          })
        }, 500)
      }
    })
  },
  setHistory: function(n, val) {
    let that = this;
    if (val == '' || val.split('').length <= 0)
      return;
    wx.getStorage({
      key: 'hList',
      success: function(res) {
        let hList = res.data;
        for (let i = 0; i < hList[n].length; i++) {
          if (val == hList[n][i].name)
            return;
        }
        hList[n].unshift({
          name: val
        });
        hList[n].length > 100 ? hList[n].pop() : ''
        wx.setStorage({
          key: "hList",
          data: hList
        })
        setTimeout(function() {
          that.setData({
            history: hList[n]
          })
        }, 200)
      },
      fail: function() {
        let hList = {
          2: [],
          3: [],
          4: [],
          5: [],
          6: []
        }
        hList[n].unshift({
          name: val
        });
        wx.setStorage({
          key: "hList",
          data: hList
        })
      }
    })
  },
  getPinyin: function(l1) {
    var l2 = l1.length;
    var I1 = "";
    var reg = new RegExp('[a-zA-Z0-9\- ]');
    for (var i = 0; i < l2; i++) {
      var val = l1.substr(i, 1);
      let name = this.arraySearch(val, pinyin);
      if (reg.test(val)) {
        I1 += val;
      } else if (name !== false) {
        I1 += name;
      }

    }
    I1 = I1.replace(/ /g, '-');
    while (I1.indexOf('--') > 0) {
      I1 = I1.replace('--', '-');
    }
    return I1;
  },
  arraySearch: function(l1) {
    for (let name in pinyin) {
      if (pinyin[name].indexOf(l1) != -1) {
        return name.toUpperCase().split('')[0];
        break;
      }
    }
    return false;
  },
  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function(options) {
    wx.hideShareMenu();
    new $App.newNav() // 注册快速导航组件
    let shareOptions = options
    this.setData({
      priceType:options.tab
    })
    // let arr = new Array('设计师', '设计师', '建材家居', '设计师', '设计师', '装修公司', '施工单位')
    let tab = options.tab || 3;
    // recommendTitle: arr[tab] || arr[0] 
    switch (tab + '') {
      case '2':
        this.setData({
          sortName2: '分类',
          optionsType:'1'
        })
        break;
      case '6':
        this.setData({
          sortName2: '工种',
          optionsType:'1'
        })
        break;
      case '5':
        this.setData({
          optionsType: '0'
        })
        break;
      case '3':
        this.setData({
          optionsType: '1'
        })
        break;
      case '4':
        this.setData({
          optionsType: "1"
        })
        break;
    }
    let provinceObj = [{
        letter: '',
        objList: []
      }, {
        letter: 'A',
        objList: []
      }, {
        letter: 'B',
        objList: []
      },
      {
        letter: 'C',
        objList: []
      }, {
        letter: 'F',
        objList: []
      }, {
        letter: 'G',
        objList: []
      },
      {
        letter: 'H',
        objList: []
      }, {
        letter: 'J',
        objList: []
      }, {
        letter: 'L',
        objList: []
      },
      {
        letter: 'N',
        objList: []
      }, {
        letter: 'Q',
        objList: []
      },
      {
        letter: 'S',
        objList: []
      }, {
        letter: 'T',
        objList: []
      }, {
        letter: 'L',
        objList: []
      },
      {
        letter: 'X',
        objList: []
      }, {
        letter: 'Y',
        objList: []
      }, {
        letter: 'Z',
        objList: []
      },
    ]
    let that = this;
    wx.getStorage({
      key: 'hList',
      success: function(res) {
        that.setData({
          history: res.data[tab],

        })
      }
    })
    let city = options.city || '全部'
    city = city.split('').length > 3 ? city.split('')[0] + city.split('')[1] + city.split('')[2] + '...' : city;
    this.setData({
      hotList: this.data.typeList[tab],
      isShow: true,
      brandType: tab,
      resourcePath: resourcePath,
      city: city,
      cityCode: options.code || '',
      shareOptions: shareOptions,
      recommendTitle: options.name ? options.name : '设计师',
      provinceObj: provinceObj,
      caseListheight: wx.getSystemInfoSync().windowHeight * 2 - 88 + 'rpx',
      caseListOverflow: 'hidden',
    })
    wx.setNavigationBarTitle({
      title: options.name ? '搜索' + options.name : '搜索设计师',
    })
    //this.getList(); // 获取列表接口
    this.getCitys(); // 获取城市接口
    this.getClassify();
    this.getPopularList();
    this.getCityData();
    new this.emptyTemplate() // 注册组件 
    this.getStyleName();
  },
  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function() {},



  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function() {
    let cityDatas = wx.getStorageSync('nowCity')
    let city = cityDatas.areaName || '全部';
    city = city.split('').length > 3 ? city.split('')[0] + city.split('')[1] + city.split('')[2] + '...' : city;
    this.setData({
      city: city,
      cityCode: cityDatas.areaCode || '',
    })
  },

  /**
   * 页面相关事件处理函数--监听用户下拉动作
   */
  onPullDownRefresh: function() {

  },

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom: function() {
    if (this.data.pageSize <= this.data.count) {
      this.setData({
        pageSize: this.data.pageSize + 5,
        messTxt: false,
        showNoMore: true,
      })
      this.getList();
    } else {
      this.setData({
        messTxt: true,
        showNoMore: false,
      })
    }
  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function() {

  },
  getList: function() {
    API.getCompanyShopList({
      pageNo: this.data.pageNo,
      pageSize: this.data.pageSize,
      businessType: this.data.brandType || "",
      shopName: this.data.keyword || "",
      cityCode: this.data.cityCode,
      categoryIds: this.data.currClassify.id,
      platformType: 2,
      orderBy: this.data.sortType
    }).then(res => {
      if (res.code == 200) {
        this.setData({
          exampleList: res.data.list,
          count: res.data.count,
        })
      } else if (res.code == 400) {
        this.setData({
          exampleList: [],
          count: 0,
        })
      }
      let flag = res.data ? res.data.list.length < 5 : res.data
      this.setData({
        showNoMore: !flag,
        messTxt: flag
      })
      this.data.exampleList.length > 0 ? this.emptyTemplateShow('hide') : this.emptyTemplateShow('show')
    })
  },
  getPopularList() {
    API.getShopPopularityList({
        businessType: this.data.brandType,
        platformType: 2
      })
      .then(res => {
        if (res.code == 200) {
          this.setData({
            popularList: res.data.list
          })
        } else {
          this.setData({
            popularList: []
          })
        }
      })
  },
  getCityData() {
    let cityData = $App.cityDataFilter(wx.getStorageSync('cityData'))
    let provinces = []
    let that = this
    let code = this.data.shareOptions.code;
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
    $App.myForEach(cityData, (v) => {
      v.areaNamePinyin = v.areaCode ? (v.areaName != "重庆市" ? that.getPinyin(v.areaName).split('')[0] : 'C') : ''
      provinces.push({
        areaCode: v.areaCode,
        areaName: v.areaName,
        id: v.id,
        levelId: v.levelId,
        pid: v.pid
      })

    })
    for (let i = 0; i < this.data.provinceObj.length; i++) {
      for (let j = 0; j < cityData.length; j++) {
        if (this.data.provinceObj[i].letter == cityData[j].areaNamePinyin) {
          this.data.provinceObj[i].objList.push(cityData[j])
        }
      }
    }
    setTimeout(function() {
      let obj = that.data.provinceObj
      for (let i = 0; i < obj.length; i++) {
        for (let j = 0; j < obj[i].objList.length; j++) {
          for (let p = 0; p < obj[i].objList[j].baseAreaVos.length; p++) {
            if (code == obj[i].objList[j].baseAreaVos[p].areaCode) {
              that.setData({
                choosex: i,
                lastX: i,
                choosey: j,
                lastY: i,
                choosep: p,
                lastP: i,
              })
            }
          }
        }
      }
    }, 200)
    setTimeout(function() {
      that.setData({
        provinces: provinces,
        provinceObj: that.data.provinceObj,
        citys: cityData[0].baseAreaVos,
        countys: cityData[0].baseAreaVos[0].baseAreaVos,
        cityObj: that.data.provinceObj[that.data.choosex].objList[that.data.choosey].baseAreaVos
      })
      that.data.cityData = cityData
    }, 400)
  },
  getCitys: function() {
    API.getCityWideXCityList()
      .then(res => {
        if (res.code == 200) {
          this.setData({
            serviceCitys: res.data || [],
            currCity: res.data[0]
          })
        } else {
          wx.showToast({
            title: res.message || "获取城市列表失败"
          })
        }
      })
  },

  getItem: function(key, val, arr) {
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
  getClassify: function() {},
  doFilter: function() {
    if (this.data.keyword.trim().length <= 0)
      return;
    this.setData({
      pageNo: 1,
      exampleList: [],
      isClassifySlideTop: true,
      isClassifySlideDown: false,
      isShowClassify: false,
      showStorage: false,
      caseListheight: '',
      showSel: true,
      caseListOverflow: 'auto',
    })
    this.setHistory(this.data.brandType, this.data.keyword);
    this.getList();
  },

  setKeyword: function(e) {
    var keyword = e.detail.value;
    this.setData({
      keyword: keyword,
    })
  },
  toHref: function(e) {
    var sHref = e.currentTarget.dataset.href;
    if (sHref == undefined)
      return;
    wx.navigateTo({
      url: sHref,
    })
  },
  toDetail(e) {
    let id = e.currentTarget.dataset.id
    wx.navigateTo({
      url: '/pages/decoration/companyDetail/companyDetail?id=' + id,
    })
  },
  changeCity: function(e) {
    this.setData({
      condition: !this.data.condition
    })
  },
  bindChange: function(e) {
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
  },
  provincialLinkageHide(e) { // 确认地址接口

    let flag = e.currentTarget.dataset.flag,
      val = this.data.threeLevelValue
    this.setData({
      condition: false
    })
    let city = this.data.cityData[val[0]].baseAreaVos[val[1]].areaName;
    city = city.split('').length > 3 ? city.split('')[0] + city.split('')[1] + city.split('')[2] + '...' : city;
    if (flag) {
      this.setData({
        province: this.data.cityData[val[0]].areaName,
        city: city,
        cityCode: this.data.cityData[val[0]].baseAreaVos[val[1]].areaCode,
        value: this.data.threeLevelValue
      })
      this.getList()
    }
  },
  changeSort() {
    if (!this.data.sortFlag) {
      this.setData({
        sortFlag: true,
        sortFlag2: false,
        sortFlag3: false,
        sortFlag4: false,
        showCityView: false,
        caseListheight: wx.getSystemInfoSync().windowHeight * 2 - 88 + 'rpx',
        caseListOverflow: 'hidden',
        showSel: false,
        fixed: true,
      })
    } else {
      this.closeFun();
    }
  },
  commitSort(e) {
    let type = e.currentTarget.dataset.type
    let name = e.currentTarget.dataset.name
    this.setData({
      sortType: type,
      sortName: name,
      showTopView: false
    })

    this.getList()
    this.closeFun()
  },
  commitSort3(e){
    let name = e.currentTarget.dataset.name
    let type = e.currentTarget.dataset.type
    let index = e.currentTarget.dataset.index
    this.setData({
      sortType2: type,
      sortName4: name,
      showTopView: false,
      decorationType: index
    })
    this.getList()
    this.closeFun()
  },
  changeSort2() {
    if (!this.data.sortFlag2) {
      this.setData({
        sortFlag2: true,
        sortFlag: false,
        sortFlag3: false,
        sortFlag4: false,
        showCityView: false,
        caseListheight: wx.getSystemInfoSync().windowHeight * 2 - 88 + 'rpx',
        caseListOverflow: 'hidden',
        showSel: false,
        fixed: true,
      })
    } else {
      this.closeFun();
    }
  },
  changeSort3() {
    if (!this.data.sortFlag3) {
      this.setData({
        sortFlag2: false,
        sortFlag: false,
        sortFlag3: true,
        sortFlag4: false,
        showCityView: false,
        caseListheight: wx.getSystemInfoSync().windowHeight * 2 - 88 + 'rpx',
        caseListOverflow: 'hidden',
        showSel: false,
        fixed: true,
      })
    } else {
      this.closeFun();
    }
  },
  changeSort4() {
    if (!this.data.sortFlag4) {
      this.setData({
        sortFlag2: false,
        sortFlag: false,
        sortFlag3: false,
        sortFlag4: true,
        showCityView: false,
        caseListheight: wx.getSystemInfoSync().windowHeight * 2 - 88 + 'rpx',
        caseListOverflow: 'hidden',
        showSel: false,
        fixed: true,
      })
    } else {
      this.closeFun();
    }
  },
  getStyleName: function() {
    let that = this;
    API.getStyleName({
        styleType: this.data.brandType
      })
      .then(res => {
        let data = res.code == 200 ? res.data : [];
        data.unshift({
          shopType: this.data.businessType,
          styleId: '',
          styleName: "全部"
        })
        that.setData({
          styleName: data
        })
      })
  },
  commitSort2(e) {
    let id = e.currentTarget.dataset.id
    let name = e.currentTarget.dataset.name
    let sortFlag2Index = e.currentTarget.dataset.index
    this.setData({
      'currClassify.id': id,
      sortName2: name,
      showTopView: false,
      sortFlag2Index: sortFlag2Index,
    })
    this.getList();
    this.closeFun();
  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function() {

  },
  changeType(e) {
    let type = e.currentTarget.dataset.type;
    this.setData({
      type: type
    })
  },
  input(e) {
    this.setData({
      keyWord: e.detail.value
    })
  },
  toList() {
    let paramUrl = '&cityItem=' + JSON.stringify(this.data.cityItem)
    if (this.data.keyWord == '')
      return;
    wx.navigateTo({
      url: '/pages/decoration/searchList/searchList?keyWord=' + this.data.keyWord + '&type=' + this.data.type + paramUrl,
    })
  },
  getMinValue: function (e) {
    this.setData({
      minValue: e.detail.value > 0 ? e.detail.value : ''
    })
  },
  getMaxValue: function (e) {
    this.setData({
      maxValue: e.detail.value > 0 ? e.detail.value : ''
    })
  },
  resetFun: function () {
    let obj = {}
    if (this.data.priceType == 3 || this.data.priceType == 4) {
      obj.designFeeStaring = ''
      obj.designFeeEnding = ''
    } else {
      obj.decorationPriceStart = ''
      obj.decorationPriceEnd = ''
    }
    this.setData({
      pageNo: 1,
      businessType: '',
      pageSize: 10,
      shopName: '',
      cityCode: '',
      categoryIds: this.data.currClassify.id,
      platformType: 2,
      orderBy: this.data.sortType,
      decorationType: this.data.decorationType,
      minValue: '',
      maxValue: ''
    })
    this.getList(obj);
  },
  submitFun: function (e) {
    let obj = {},
      minValue = this.data.minValue,
      maxValue = this.data.maxValue
    if (maxValue != '' && minValue != '' && minValue < maxValue) {
      if (this.data.priceType == 3 || this.data.priceType == 4) {
        obj.designFeeStarting = minValue
        obj.designFeeEnding = maxValue
      } else {
        obj.decorationPriceStart = minValue
        obj.decorationPriceEnd = maxValue
      }
      this.setData({ sortFlag3: false, showTopView: false })
      this.getList(obj)
      this.closeFun()
    } else {
      wx.showToast({ title: "请输入正确数值", icon: 'none' })
    }
  }
})