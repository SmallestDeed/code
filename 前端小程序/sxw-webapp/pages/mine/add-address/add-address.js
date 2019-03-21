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
  bindChange: function(e) {
    const val = e.detail.value
    const temp = this.data.threeLevelValue
    if (temp[0] !== val[0]) { //省在滑动
      API.getAreaByPid({
        pid: this.data.provinceList[val[0]].areaCode,
        levelId: 2
      }).then(res => {
        this.setData({
          cityList: res.obj, //市
        })
        API.getAreaByPid({
          pid: this.data.cityList[val[1]].areaCode,
          levelId: 3
        }).then(res => {
          this.setData({
            districtList: res.obj
          })
        })
      })
      this.setData({
        value: [val[0], 0, 0]
      })
      val[1] = 0
      val[2] = 0
    } else if (temp[1] !== val[1]) {
      API.getAreaByPid({
        pid: this.data.cityList[val[1]].areaCode,
        levelId: 3
      }).then(res => {
        this.setData({
          districtList: res.obj
        })
      })
      this.setData({
        value: [val[0], val[1], 0]
      })
      val[2] = 0
    }
    this.setData({
      cityList: this.data.cityList,
      districtList: this.data.districtList,
      threeLevelValue: val
    })
  },
  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function(options) {
    wx.hideShareMenu();
    new $App.newNav() // 注册快速导航组件
    let item
    if (JSON.stringify(options) === '{}') {
      item = JSON.stringify({
        consignee: '',
        mobile: '',
        region: '',
        address: '',
        province: '',
        city: '',
        district: '',
        id: ''
      })
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
    API.getAreaByPid({
      pid: 0,
      levelId: 1
    }).then(res => {
      let provinces = []
      $App.myForEach(res.obj.provinces, (value) => {
        provinces.push({
          areaCode: value.areaCode,
          areaName: value.areaName,
          id: value.id,
          levelId: value.levelId,
          pid: value.pid
        })
      })
      this.setData({
        provinceList: res.obj.provinces, //省数据
        cityList: res.obj.cities, //默认深圳市数据
        districtList: res.obj.areas //默认深圳区数据
      })
      if (this.data.userInformation.province != '') {
        myForEach(this.data.provinceList, (value, index) => {
          if (value.areaCode === this.data.userInformation.province) {
            this.data.threeLevelValue[0] = index
            API.getAreaByPid({
              pid: this.data.userInformation.province,
              levelId: 2
            }).then(res => {
              myForEach(res.obj, (value, jndex) => {
                if (value.areaCode === this.data.userInformation.city) {
                  let jArr = this.data.threeLevelValue
                  jArr[1] = jndex
                  this.setData({
                    cityList: res.obj,
                    threeLevelValue: jArr,
                  })
                }
              })
              API.getAreaByPid({
                pid: this.data.userInformation.city,
                levelId: 3
              }).then(res => {
                myForEach(res.obj, (value, jndex2) => {
                  if (value.areaCode === this.data.userInformation.district) {
                    let jArr2 = this.data.threeLevelValue
                    jArr2[2] = jndex2
                    this.setData({
                      districtList: res.obj,
                      threeLevelValue: jArr2
                    })
                  }
                })
                this.setData({
                  threeLevelValue: this.data.threeLevelValue,
                  value: this.data.threeLevelValue,
                })
              })
            })
          }
        })
      }
    })
  },
  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function() {

  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function() {

  },

  /**
   * 生命周期函数--监听页面隐藏
   */
  onHide: function() {

  },

  /**
   * 生命周期函数--监听页面卸载
   */
  onUnload: function() {

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

  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function(res) {
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
    // 原版本 let val = this.data.threeLevelValue
    // this.setData({
    //   provincialLinkageShow: false
    // })
    // if (e.currentTarget.dataset.type === 'confirm') {
    //   this.data.userInformation.region = this.data.cityData[val[0]].areaName + this.data.cityData[val[0]].baseAreaVos[val[1]].areaName + this.data.cityData[val[0]].baseAreaVos[val[1]].baseAreaVos[val[2]].areaName
    //   this.setData({
    //     value: this.data.threeLevelValue,
    //     province: this.data.cityData[val[0]].areaName,
    //     city: this.data.cityData[val[0]].baseAreaVos[val[1]].areaName,
    //     district: this.data.cityData[val[0]].baseAreaVos[val[1]].baseAreaVos[val[2]].areaName,
    //     userInformation: this.data.userInformation
    //   })
    // }

    let val = this.data.threeLevelValue
    this.setData({
      provincialLinkageShow: false
    })
    if (e.currentTarget.dataset.type === 'confirm') {
      this.data.userInformation.region = this.data.provinceList[val[0]].areaName + this.data.cityList[val[1]].areaName + this.data.districtList[val[2]].areaName
      this.setData({
        value: this.data.threeLevelValue,
        province: this.data.provinceList[val[0]].areaName,
        city: this.data.cityList[val[1]].areaName,
        district: this.data.districtList[val[2]].areaName,
        userInformation: this.data.userInformation
      })
    }
  },
  confirmAddAddress() { // 确定添加地址
    let flag = false,
      val = this.data.threeLevelValue,
      userInformation = this.data.userInformation
    for (let key in userInformation) {
      if (!this.data.userInformation[key]) {
        if (key == 'consignee' || key == 'mobile' || key == 'region' || key == 'address') {
          flag = true
        }
      }
    }
    if (flag) {
      wx.showToast({
        title: '请填写完整数据',
        icon: 'none'
      })
      return
    }
    if (!/^1[3456789]\d{9}$/.test(userInformation.mobile) || !/^(\(\d{3,4}\)|\d{3,4}-|\s)?\d{7,14}$/.test(userInformation.mobile)) {
      wx.showToast({
        title: '请填写正确的手机格式',
        icon: 'none'
      })
      return
    }
    API.addUserAddress({
        consignee: this.data.userInformation.consignee,
        addressName: "公司",
        mobile: this.data.userInformation.mobile,
        province: this.data.provinceList[val[0]].areaCode,
        city: this.data.cityList[val[1]].areaCode,
        district: this.data.districtList[val[2]].areaCode,
        address: this.data.userInformation.address,
        isDefault: 0,
        isEdit: this.data.isEdit,
        id: this.data.userInformation.id
      })
      .then(res => {
        if (res.status) {
          wx.showToast({
            title: '新增地址成功',
            icon: 'success'
          })
          wx.navigateBack({})
        } else {
          wx.showToast({
            title: '新增地址失败',
            icon: 'success'
          })
        }
      })
  }
})