// pages/add-address/add-address.js
// import cityData from '../../utils/citys.js'
let myForEach = getApp().myForEach
let API = getApp().API
let $App = getApp()
Page({
  /**
   * 页面的初始数据
   */
  data: {
    userInformation: {
      consignee: '',
      mobile: '',
      region: '',
      address: '',
      province: '',
      city: '',
      district: ''
    },
    value: [0, 0, 0],
    provinceList: [],
    cityList: [],
    districtList: [],
    province: '',
    city: '',
    district: '',
    threeLevelValue: [0, 0, 0],
    provincialLinkageShow: false,
    cityData: wx.getStorageSync('cityData'),
    isEdit: 0
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
      cityList: this.data.cityData[val[0]].baseAreaVos,
      districtList: this.data.cityData[val[0]].baseAreaVos[val[1]].baseAreaVos,
      threeLevelValue: val
    })
  },
  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
      new $App.newNav() // 注册快速导航组件
    let item
    if (JSON.stringify(options) === '{}') {
      item = JSON.stringify(
        {
          consignee: '',
          mobile: '',
          region: '',
          address: '',
          province: '',
          city: '',
          district: '',
          id: ''
        }
      )
      this.setData({
        isEdit: 0
      })
      wx.setNavigationBarTitle({
        title: '添加收货地址'
      })
    } else {
      this.setData({
        isEdit: 1
      })
      wx.setNavigationBarTitle({
        title: '编辑收货地址'
      })
      item = options.item
    }
    this.data.userInformation = {
      consignee: JSON.parse(item).consignee,
      mobile: JSON.parse(item).mobile,
      region: JSON.parse(item).region,
      address: JSON.parse(item).address,
      province: JSON.parse(item).province,
      city: JSON.parse(item).city,
      district: JSON.parse(item).district,
      id: JSON.parse(item).id
    }
    this.setData({
      userInformation: this.data.userInformation
    })
    // 省三级联动
    // cityData.init(this)
    this.data.cityData = wx.getStorageSync('cityData')
    let provinceList = [], cityList = []
    myForEach(this.data.cityData, (value, index) => {
      let temp = JSON.parse(JSON.stringify(value))
      temp.baseAreaVos = []+
      provinceList.push(temp)
      if (index === 0) {
        cityList = this.processingCityData(value.baseAreaVos)
      }
    })
    this.setData({
      provinceList: provinceList,
      cityList: cityList,
      districtList: this.data.cityData[0].baseAreaVos[0].baseAreaVos
    })
    if (this.data.userInformation.province !== '') {
      myForEach(this.data.cityData, (value, index) => {
        if (value.areaCode === this.data.userInformation.province) {
          this.data.threeLevelValue[0] = index
        }
      })
      myForEach(this.data.cityData[this.data.threeLevelValue[0]].baseAreaVos, (value, index) => {
        if (value.areaCode === this.data.userInformation.city) {
          this.data.threeLevelValue[1] = index
        }
      })
      myForEach(this.data.cityData[this.data.threeLevelValue[0]].baseAreaVos[this.data.threeLevelValue[1]].baseAreaVos, (value, index) => {
        if (value.areaCode === this.data.userInformation.district) {
          this.data.threeLevelValue[2] = index
        }
      })
      this.setData({
        threeLevelValue: this.data.threeLevelValue,
        value: this.data.threeLevelValue,
        cityList: this.processingCityData(this.data.cityData[this.data.threeLevelValue[0]].baseAreaVos),
        districtList: this.data.cityData[this.data.threeLevelValue[0]].baseAreaVos[this.data.threeLevelValue[1]].baseAreaVos
      })
    }
  },
  processingCityData(arr) {
    let cityList = []
    myForEach(arr, (val) => {
      let temp = JSON.parse(JSON.stringify(val))
      temp.baseAreaVos = []
      cityList.push(temp)
    })
    return cityList
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
      return $App.shareAppMessageObj
    }
  },
  changeInput(e) {
    let key = 'userInformation.' + e.target.dataset.type
    this.setData({
      [key]: e.detail.value
    })
  },
  provincialLinkageShow() { // 显示三级
    this.setData({
      provincialLinkageShow: true
    })
  },
  provincialLinkageHide(e) { // 隐藏三级
    let val = this.data.threeLevelValue
    this.setData({
      provincialLinkageShow: false
    })
    if (e.currentTarget.dataset.type === 'confirm') {
      this.data.userInformation.region = this.data.cityData[val[0]].areaName + this.data.cityData[val[0]].baseAreaVos[val[1]].areaName + this.data.cityData[val[0]].baseAreaVos[val[1]].baseAreaVos[val[2]].areaName
      this.setData({
        value: this.data.threeLevelValue,
        province: this.data.cityData[val[0]].areaName,
        city: this.data.cityData[val[0]].baseAreaVos[val[1]].areaName,
        district: this.data.cityData[val[0]].baseAreaVos[val[1]].baseAreaVos[val[2]].areaName,
        userInformation: this.data.userInformation
      })
    }
  },
  confirmAddAddress() { // 确定添加地址
    let flag = false, val = this.data.threeLevelValue, userInformation = this.data.userInformation
    for (let key in userInformation) {
      if (!this.data.userInformation[key]) {
        if (key == 'consignee' || key == 'mobile' || key == 'region' || key == 'address') {
          flag = true
        }
      }
    }
    if (flag) {
      wx.showToast({ title: '请填写完整数据', icon: 'none' })
      return
    }
    if (!/^1[3456789]\d{9}$/.test(userInformation.mobile) || !/^(\(\d{3,4}\)|\d{3,4}-|\s)?\d{7,14}$/.test(userInformation.mobile)) {
      wx.showToast({ title: '请填写正确的手机格式', icon: 'none' })
      return
    }
    API.addUserAddress({
      consignee: this.data.userInformation.consignee,
      addressName: "公司",
      mobile: this.data.userInformation.mobile,
      province: this.data.cityData[val[0]].areaCode,
      city: this.data.cityData[val[0]].baseAreaVos[val[1]].areaCode,
      district: this.data.cityData[val[0]].baseAreaVos[val[1]].baseAreaVos[val[2]].areaCode,
      address: this.data.userInformation.address,
      isDefault: 0,
      isEdit: this.data.isEdit,
      id: this.data.userInformation.id
    })
      .then(res => {
        if (res.status) {
          wx.showToast({ title: '新增地址成功', icon: 'success' })
          wx.navigateBack({})
        } else {
          wx.showToast({ title: '新增地址失败', icon: 'success' })
        }
      })
  }
})