// pages/decoration/seek-service/seek-service.js
import {
  resourcePath,
  defaultImg
} from '../../../utils/config.js'
let $App = getApp(), API = getApp().API
Page({

  /**
   * 页面的初始数据
   */
  data: {
    sDDefaultImg:'',
    pageSize: 10,
    resourcePath: resourcePath,
    isShowempty: false,
    emptyText: '',
    isShowList: false,
    hotList: ['施工人员', '建材', '家具', '电器', '设计师', '装修设计'],
    historyList: [],
    seekText: '',
    sortList: [{ id: 1, name: '最热排序' },{id: 0, name: '最新排序'}],
    sortId: 1,
    showSort: false,
    showSite: false,
    showCategory: false,
    showScreen: false,
    provinceList: [],
    provinceCode: '',
    cityList: [],
    cityCode: '',
    cityName: '',
    decorationList: [],
    // decorationId: '',
    materialsList: [],
    // materialsId: '',
    manpowerList: [],
    // manpowerId: '',
    code: '',
    messageList: [{ name: '全部', id: '' }, { name: '供求', id: '1' }, { name: '需求', id: '2' }],
    messageId: 2,
    promulgatorList: [],
    promulgatorId: undefined,
    seekItemList: [],

    // rzd start
    supply: 0,
    staticImageUrl: $App.staticImageUrl,
    // rzd end
  },
  //rzd start
  changeSupply(e) {
    console.log(e)
    let type = e.currentTarget.dataset.supply;
    let msg = e.currentTarget.dataset.msgid
    console.log("11",msg)
    this.setData({
      supply: type,
      messageId: msg
    })
    this.seekList();
  },
  changeRzdSort() {
    this.setData({
      showSort: !this.data.showSort
    })
    
  },
  //rzd end

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    this.setData({
      sDDefaultImg: this.data.staticImageUrl + 'news_pic_nor.png'
    })
      new $App.newNav() // 注册快速导航组件
    this.setData({
      cityCode: options.cityCode,
      cityName: options.city,
      historyList: wx.getStorageSync('historyList') ? wx.getStorageSync('historyList'): []
    });
    // console.log(this.data.historyList);
    this.selectCode();
    this.getPromulgatorList();
    this.getAllSupply();
    setTimeout(() => {
      this.setData({
        provinceList: wx.getStorageSync('cityData'),
      });
    },2000);
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
  toDetail(e) {
    let id = e.currentTarget.dataset.id;
    let mold = e.currentTarget.dataset.type;
    wx.navigateTo({
      url: '../supplyDetail/supplyDetail?id=' + id + '&mold=' + mold,
    })
  },
  changeTiem(tiem) {
    let leadTime = new Date().getTime() - new Date(tiem.replace(/\-/g, '/')), date;
    if (leadTime / 1000 / 60 < 1) {
      date = '刚刚';
    }
    if (leadTime / 1000 / 60 > 1 && leadTime / 1000 / 60 < 60) {
      date = (leadTime / 1000 / 60).toFixed(0) + '分钟前';
    }
    if (leadTime / 1000 / 3600 > 1 && leadTime / 1000 / 3600 < 59) {
      date = (leadTime / 1000 / 3600).toFixed(0) + '小时前';
    }
    if (leadTime / 1000 / 3600 / 24 > 1) {
      date = '1天前';
    }
    return date;
  },
  // 请求数据列表
  seekList() {
    console.log(this.data.messageId)
    API.getallsupplydemandinfo({
      supplyDemandCategoryId: this.data.code != '' ? this.data.code : undefined, // 类别
      type: this.data.messageId != '' ? this.data.messageId : undefined, // 信息类型
      publisherType: this.data.promulgatorId != '' ? this.data.promulgatorId : undefined, // 发布者
      isSortByViewNum: this.data.sortId == 1 ? 'desc' : undefined, // 最热
      isSortByGmtModified: this.data.sortId == 0 ? 'desc' : undefined, // 最新
      title: this.data.seekText, // 搜索
      pageSize: this.data.pageSize,
      curPage: 1
    }).then(res => {
      if (res.obj) {
        let arr = res.obj;
        arr.forEach((value, index) => {
          value.gmtModified = this.changeTiem(value.gmtModified);
        });
        this.setData({
          seekItemList: arr,
          isShowempty: false
        });
        console.log(this.data.seekItemList)
      } else {
        this.setData({
          seekItemList: [],
          isShowempty: true
        });
      }
    });
  },
  getAllSupply() {
    API.getAllSupplyDemandCategory({
      type: 1,
      categoryId: 2
    }).then(res => {
      let fitmentList = [], buildingList = [], manpowerList = [];
      for (let i = 0; i < res.obj.length; i++) {
        if (res.obj[i].pid == 2) {
          fitmentList.push(res.obj[i]);
        }
        if (res.obj[i].pid == 3) {
          buildingList.push(res.obj[i]);
        }
        if (res.obj[i].pid == 4) {
          manpowerList.push(res.obj[i]);
        }
      }
      this.setData({
        decorationList: fitmentList,
        materialsList: buildingList,
        manpowerList: manpowerList
      });
    });
  },
  selectCode() {
    let cityData = wx.getStorageSync('cityData');
    for (let i = 0; i < cityData.length; i++) {
      for (let j = 0; j < cityData[i].baseAreaVos.length; j++) {
        if (cityData[i].baseAreaVos[j].areaCode == this.data.cityCode) {
          this.setData({
            provinceCode: cityData[i].areaCode,
            cityList: cityData[i].baseAreaVos
          });
          return;
        }
      }
    }
  },
  getPromulgatorList() {
    API.getAllSupplyDemandRoles({ type: 1 })
      .then(res => {
        let i = 0, arr = [{name: '全部', id: ''}]
        for (let item in res.obj) {
          arr[i+1] = {
            name: item,
            id: res.obj[item]
          }
          i++;
        }
        this.setData({ promulgatorList: arr })
      });
  },
  focus() {
    this.setData({
      showSort: false,
      showSite: false,
      showCategory: false,
      showScreen: false,
      isShowList: false,
      historyList: wx.getStorageSync('historyList') ? wx.getStorageSync('historyList') : []
    });
  },
  getText(e) {
    this.setData({
      seekText: e.detail.value
    });
  },
  seek(e) {
    let text = e.currentTarget.dataset.item ? e.currentTarget.dataset.item : this.data.seekText;
    if (text.replace(/\s/g, '') == '') {
      return;
    }
    let arr = [];
    this.setData({
      seekText: text,
      isShowList: true
    });
    // console.log(text);
    // console.log(1);
    this.seekList();
    if (wx.getStorageSync('historyList')) {
      arr = wx.getStorageSync('historyList');
      for (let i=0; i<arr.length; i++) {
        if (arr[i] == text) {
          return;
        }
      }
      arr.push(text);
    } else {
      arr = [text];
    }
    wx.setStorageSync('historyList', arr);
  },
  deleteHistory() {
    this.setData({
      historyList: []
    });
    wx.setStorageSync('historyList',[]);
    // console.log(this.data.historyList);
  },
  select(e) {
    if (e.currentTarget.dataset.type == "排序") {
      this.setData({
        showSort: !this.data.showSort,
        showSite: false,
        showCategory: false,
        showScreen: false
      });
    }
    if (e.currentTarget.dataset.type == "区域") {
      this.setData({
        showSort: false,
        showSite: !this.data.showSite,
        showCategory:false,
        showScreen: false
      });
    }
    if (e.currentTarget.dataset.type == "类别") {
      this.setData({
        showSort: false,
        showSite: false,
        showCategory: !this.data.showCategory,
        showScreen: false
      });
    }
    if (e.currentTarget.dataset.type == "筛选") {
      this.setData({
        showSort: false,
        showSite: false,
        showCategory: false,
        showScreen: !this.data.showScreen
      });
    }
  },
  // 选择排序
  selectSort(e) {
    this.setData({
      sortId: e.currentTarget.dataset.id,
      showSort: false
    });
    this.seekList();
  },
  // 选择省份城市
  selectArea(e) {
    if (e.currentTarget.dataset.type == 'province') {
      this.setData({
        provinceCode: e.currentTarget.dataset.item.areaCode,
        cityList: e.currentTarget.dataset.item.baseAreaVos,
      });
    }
    if (e.currentTarget.dataset.type == 'city') {
      this.setData({
        cityCode: e.currentTarget.dataset.item.areaCode,
        cityName: e.currentTarget.dataset.item.areaName,
        showSite: false
      });
      wx.setStorageSync('nowCity', e.currentTarget.dataset.item)
      // console.log(e.currentTarget.dataset.item);
    }
    this.seekList();
  },
  // 选择分类
  selectCategory(e) {
    // console.log(e.currentTarget.dataset.id);
    this.setData({
      code: e.currentTarget.dataset.id
    });
    // if (e.currentTarget.dataset.type == '装修服务') {
    //   this.setData({
    //     decorationId: e.currentTarget.dataset.id
    //   });
    // }
    // if (e.currentTarget.dataset.type == '建材家居') {
    //   this.setData({
    //     materialsId: e.currentTarget.dataset.id
    //   });
    //  }
    // if (e.currentTarget.dataset.type == '人力服务') { 
    //   this.setData({
    //     manpowerId: e.currentTarget.dataset.id
    //   });
    // }
  },
  categoryBtn(e) {
    if (e.currentTarget.dataset.type == '重置') {
      this.setData({
        decorationId: '',
        materialsId: '',
        manpowerId: '',
        code: ''
      });
    }
    if (e.currentTarget.dataset.type == '确定') {
      this.setData({
        showCategory: false
      });
      this.seekList();
    }
  },
  // 筛选
  selectScreen(e) {
    if (e.currentTarget.dataset.type == '信息类型') {
      this.setData({
        messageId: e.currentTarget.dataset.id
      });
    }
    if (e.currentTarget.dataset.type == '发布者') {
      this.setData({
        promulgatorId: e.currentTarget.dataset.id
      });
    }
  },
  screenBtn(e) {
    if (e.currentTarget.dataset.type == '重置') {
      this.setData({
        promulgatorId: '',
        messageId: ''
      });
    }
    if (e.currentTarget.dataset.type == '确定') {
      this.setData({
        showScreen: false
      });
    }
    this.seekList();
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
    let i = this.data.pageSize + 10;
    this.setData({
      pageSize: i
    });
    this.seekList();
  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function () {

  }
})