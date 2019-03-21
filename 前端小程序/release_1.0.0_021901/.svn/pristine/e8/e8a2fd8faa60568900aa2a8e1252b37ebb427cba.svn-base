// pages/seekHouse/seekHouse.js
let app = getApp(), API = getApp().API
Page({

  /**
   * 页面的初始数据
   */
  data: {
    isShowSelector: false,
    isHistory: true,
    isEmpty: false,
    historyList: [],
    province: [],
    city: [],
    district: [],
    code: {},
    indexArr: [0,0,0],
    site: '',
    livingName: '',
    curPage: 1,
    totalCount: 0,
    regionList: []
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    this.getAreaList(0, 1);
    this.setData({
      historyList: wx.getStorageSync('historyList') ? wx.getStorageSync('historyList') : []
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

  },

  /*获取小区名称*/
  getLivingName(e) {
    this.setData({
      livingName: e.detail.value
    });
  },

  /*删除历史记录*/
  deleteHistory() {
    wx.setStorageSync('historyList', [])
    this.setData({ 
      historyList: [],
      isHistory: false
    });
  },

  /*缓存搜索历史*/
  setStorageHistory() {
    let arr = wx.getStorageSync('historyList');
    if (arr) {
      for (let i = 0, len = arr.length; i < len; i++) {
        if (
            arr[i].livingName == this.data.livingName 
            && arr[i].cityCode == this.data.code.cityCode 
            && arr[i].districtCode == this.data.code.districtCode
           ) {
          return;
        }
      }
    } else {
      arr = [];
    }
    let obj = {
      livingName: this.data.livingName,
      cityCode: this.data.code.cityCode,
      districtCode: this.data.code.districtCode,
      site: this.data.site
    }
    arr.push(obj);
    wx.setStorageSync('historyList', arr);
  },

  /*搜索小区*/
  seekRegion() {
    if (app.myVerifyEmpty(this.data.site)) {
      wx.showToast({ title: '请选择所在地区', icon: 'none' })
      return
    }
    if (app.myVerifyEmpty(this.data.livingName)) {
      wx.showToast({ title: '请填写小区名称', icon: 'none'})
      return
    }
    // this.data.operationType = 'seek';
    this.data.curPage = 1;
    this.setStorageHistory();
    this.getRegionList('seek');
  },

  /*历史搜索*/
  historySeek(e) {
    let item = e.currentTarget.dataset.item;
    // this.data.operationType = 'seek';
    this.data.livingName = item.livingName;
    this.data.code.cityCode = item.cityCode;
    this.data.code.districtCode = item.districtCode;
    this.data.curPage = 1;
    this.setData({ livingName: item.livingName, site: item.site});
    this.getRegionList('seek');
  },

  /*获取小区列表*/
  getRegionList(type) {
    let obj = {
      livingName: this.data.livingName,
      cityCode: this.data.code.cityCode,
      pageSize: 10,
      curPage: this.data.curPage,
      districtCode: this.data.code.districtCode,
    }
    API.getRegionList(obj).then(res => {
      if (res.obj) {
        let arr;
        let isEmpty = this.data.isEmpty;
        if (type == 'pull') {
          arr = this.data.regionList.concat(res.obj);
        } else {
          arr = res.obj;
          isEmpty = res.obj.length > 0 ? false : true
        }
        this.setData({
          regionList: arr,
          totalCount: res.totalCount,
          isEmpty: isEmpty,
          isHistory: false
        })
      }
    });
  },

  /*跳转户型页面*/
  goHouse(e) {
    let item = e.currentTarget.dataset.item;
    wx.navigateTo({
      url: `/pages/house/house?name=${item.livingName}&id=${item.id}`
    });
  },

  /*确定选择：confirm，取消选择：cancel, 打开：open，三级联动*/
  operation(e) {
    let type = e.currentTarget.dataset.type
    if (type == 'open') {
      this.setData({
        isShowSelector: true
      });
    }
    if (type == 'cancel') {
      this.setData({
        isShowSelector: false
      });
    }
    if (type == 'confirm') {
      let provinc = this.data.province[this.data.indexArr[0]];
      let city = this.data.city[this.data.indexArr[1]];
      let district = this.data.district[this.data.indexArr[2]];
      this.setData({
        site: provinc.areaName + '&nbsp;&nbsp;' + city.areaName + '&nbsp;&nbsp;' + district.areaName,
        code: { provincCode: provinc.areaCode, cityCode: city.areaCode, districtCode: district.areaCode },
        isShowSelector: false
      });
    }
  },
  
  /*三级联动变化事件*/
  bindChange(e) {
    let value = e.detail.value
    let indexArrs = this.data.indexArr
    if (value[0] != indexArrs[0]) {
      this.getAreaList(this.data.province[value[0]].areaCode, 2)
    }
    if (value[1] != indexArrs[1]) {
      this.getAreaList(this.data.city[value[1]].areaCode, 3)
    }
    this.data.indexArr = e.detail.value
  },

  /**
   * 请求区域 pid: 0,     levelId: 1，请求省及首个省下的市和区
   * 请求区域 pid: 省code,levelId: 2，请求市及以下区
   * 请求区域 pid: 市code,levelId: 3，请求区及
   */
  getAreaList(pid, levelId) {
    API.getAreaList({ pid: pid, levelId: levelId }, 'noLoading').then(res => {
      if (res.obj) {
        if (levelId == 1) {
          res.obj.areas.unshift({ areaName: '全市', areaCode: '' })
          this.setData({ 
            province: res.obj.provinces,
            city: res.obj.cities,
            district: res.obj.areas
          })
        }
        if (levelId == 2) {
          let arr = this.data.indexArr;
          arr[1] = 0; arr[2] = 0;
          res.obj[0].lowerArea.unshift({ areaName: '全市', areaCode: '' })
          this.setData({ 
            city: res.obj, 
            district: res.obj[0].lowerArea,
            indexArr: arr
          })
        }
        if (levelId == 3) {
          let arr = this.data.indexArr;
          arr[2] = 0;
          res.obj.unshift({ areaName: '全市', areaCode: ''})
          this.setData({ district: res.obj, indexArr: arr })
        }
      }
    });
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
    if (this.data.curPage * 10 < this.data.totalCount) {
      this.data.curPage += 1;
      // this.data.operationType = 'pull'
      this.getRegionList('pull');
    }
  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function () {

  }
})