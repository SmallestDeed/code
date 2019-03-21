// pages/upload-house/upload-house.js
let $App = getApp();
let API = getApp().API

Page({

  /**
   * 页面的初始数据
   */
  data: {
    staticImageUrl: getApp().staticImageUrl,
    value: [0, 0, 0],
    value2: [0,0,0],
    provinceList: [],
    cityList: [],
    districtList: [],
    province: '',
    city: '',
    district: '',
    threeLevelValue: [0, 0, 0],
    modelTypeValue: [0,0,0],
    provincialLinkageShow: false,
    modelTypeShow:false,
    hideMessage: true,
    showDelete: false,
    houseMainParams: {
      region: '',
      houseEstate: '',
      houseType: '',
      houseArea: '',
      houseDescribe: '',
    },
    contactNumber: '',
    bedRoomList: [ // 室列表
      {
        num: '1室'
      },
      {
        num: '2室'
      },
      {
        num: '3室'
      },
      {
        num: '4室'
      },
      {
        num: '5室'
      }
    ],
    drawingRoomList: [ //厅列表
      {
        num: '0厅'
      },
      {
        num: '1厅'
      },
      {
        num: '2厅'
      },
      {
        num: '3厅'
      },
      {
        num: '4厅'
      },
      {
        num: '5厅'
      },
    ],
    toiletRoomList: [ //卫列表
      {
        num: '0卫'
      },
      {
        num: '1卫'
      },
      {
        num: '2卫'
      },
      {
        num: '3卫'
      },
      {
        num: '4卫'
      },
      {
        num: '5卫'
      },
    ],
    uploadImage: '',

    bootAlertShow: false // 户型引导弹框
  },

  /**
   * 生命周期函数--监听页面加载
   */
  getRandomNum: function(n) {
    let num = 1;
    for (let i = 0; i < n - 1; i++) {
      num *= 10;
    }
    return parseInt(Math.random() * 9 * num + 1 * num)
  },
  onLoad: function(options) {
    wx.hideShareMenu();
    // let aaa =  this;
    // setInterval(function(res){
    //     console.log(aaa.getRandomNum('sss'), res);
    // },1000)
    if (options){
      let kongAreaHouseMain = this.data.houseMainParams;
      kongAreaHouseMain.region = options.region
      kongAreaHouseMain.houseEstate = options.livingName
      this.setData({
        houseMainParams: kongAreaHouseMain
      })
    }
    new $App.newNav() // 注册快速导航组件
    this.sellingPoint()
    this.getCityData()
  },
  getCityData() {
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
        provinceList: provinces, //省数据
        cityList: res.obj.cities, //市数据
        districtList: res.obj.areas //区数据
      })
    })
  },
  //埋点
  sellingPoint(event) {
    let page = getCurrentPages(),
      previousPath = page.length > 1 ? page[page.length - 2].route : '',
      nowPath = page[page.length - 1].route
    API.sellingPoint({
      uid: wx.getStorageSync('openId'),
      cp: nowPath,
      lp: previousPath,
      e: event ? event : '',
      pt: '提交户型资料'
    })
  },
  // 地区选择API开始
  houseProvincialShow() {
    this.setData({
      provincialLinkageShow: true
    })
  },
  // 户型类型选择开始
  houseTypeShow() {
    this.setData({
      modelTypeShow: true
    })
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
  bindChange1(e){
    const val = e.detail.value
    const temp = this.data.modelTypeValue
    this.setData({
      modelTypeValue: val
    })
  },
  provincialLinkageHide(e) { // 隐藏三级
    let val = this.data.threeLevelValue
    this.setData({
      provincialLinkageShow: false
    })
    if (e.currentTarget.dataset.type === 'confirm') {
      this.data.houseMainParams.region = this.data.provinceList[val[0]].areaName + this.data.cityList[val[1]].areaName + this.data.districtList[val[2]].areaName
      this.setData({
        value: this.data.threeLevelValue,
        province: this.data.provinceList[val[0]].areaName,
        city: this.data.cityList[val[1]].areaName,
        district: this.data.districtList[val[2]].areaName,
        houseMainParams: this.data.houseMainParams
      })
    }
  },
  modelTypeShowHide(e){
    let val = this.data.modelTypeValue
    this.setData({
      modelTypeShow:false
    })
    if(e.currentTarget.dataset.type === 'confirm1'){
      this.data.houseMainParams.houseType = this.data.bedRoomList[val[0]].num  +       this.data.drawingRoomList[val[1]].num  +  this.data.toiletRoomList[val[2]].num
      this.setData({
        value2: this.data.modelTypeValue,
        houseMainParams: this.data.houseMainParams
      })
    }
  },
  changeInput(e) {
    let type = e.target.dataset.type;
    let v = e.detail.value;
    let that = this;
    let data = function() {
      let key = type == 'contactNumber' ? type : 'houseMainParams.' + type
      let flag = ((type == 'houseArea' || type == 'contactNumber') && v <= 0) || v.trim().length <= 0;
      if (flag) {
        that.setData({
          [key]: ''
        })
        return;
      }
      that.setData({
        [key]: v
      })
    }
    if (type == 'houseArea') {
      if (/^\d+(\.\d+)?$/.test(v)) {
        if (String(v).indexOf(".") + 1 > 0) {
          if ([String(v).length - (String(v).indexOf(".") + 1)] > 2) {
            wx.showToast({
              title: '面积限制两位小数',
              icon: 'none'
            })
            let kongAreaHouseMain = this.data.houseMainParams;
            kongAreaHouseMain.houseArea = (kongAreaHouseMain.houseArea / 1).toFixed(2);
            this.setData({
              houseMainParams: kongAreaHouseMain
            })
          } else {
            data();
          }
        } else {
          data();
        }
      } else {
        if (!/^\d*\.{0,1}\d{0,1}$/.test(v)) {
          wx.showToast({
            title: '请输入正确数字',
            icon: 'none'
          })
          let kongAreaHouseMain = this.data.houseMainParams;
          kongAreaHouseMain.houseArea = '';
          this.setData({
            houseMainParams: kongAreaHouseMain
          })
        } else {
          data()
        }
      }
    } else if (type =='houseEstate'){
      let i = /[\uD83C|\uD83D|\uD83E][\uDC00-\uDFFF][\u200D|\uFE0F]|[\uD83C|\uD83D|\uD83E][\uDC00-\uDFFF]|[0-9|*|#]\uFE0F\u20E3|[0-9|#]\u20E3|[\u203C-\u3299]\uFE0F\u200D|[\u203C-\u3299]\uFE0F|[\u2122-\u2B55]|\u303D|[\A9|\AE]\u3030|\uA9|\uAE|\u3030/ig;
      if(i.test(v)){
        wx.showToast({
          title: '请输入正确小区名字',
          icon: 'none'
        })
        let kongAreaHouseMain = this.data.houseMainParams;
        kongAreaHouseMain.houseEstate = ''
        this.setData({
          houseMainParams: kongAreaHouseMain
        })
      }else{
        data()
      }
    }
  },
  // 地区选择API结束
  uploadHousePictrue() { // 上传户型图
    wx.chooseImage({
      count: 1,
      success: (res) => {
        this.setData({
          hideMessage:false,
          showDelete:true,
          uploadImage: res.tempFiles[0].path
        })
      }
    })
  },
  delete(){
    this.setData({
      uploadImage:'',
      hideMessage:true,
      showDelete:false
    })
  },
  submitHouseMessage() { // 提交户型信息
    let _this = this;
    let flag = false;
    for (let key in this.data.houseMainParams) {
      !this.data.houseMainParams[key] && key != 'houseDescribe' ? flag = true : null
      if (!this.data.houseMainParams[key] && key != 'houseDescribe') {
        flag = true;
        break;
      }
    }
    if (flag) {
      wx.showToast({
        title: '请填写完整数据',
        icon: 'none'
      });
      return;
    } else if (this.data.uploadImage === '') {
      if (this.data.contactNumber && !(/^1[3|4|5|8][0-9]\d{8}$/.test(this.data.contactNumber))) {
        wx.showToast({
          title: '请正确填写电话!',
          icon: 'none'
        })
        return;
      }
      API.uploadHouseType({
          'cityInfo': this.data.houseMainParams.region,
          'livingInfo': this.data.houseMainParams.houseEstate,
          'houseType': this.data.houseMainParams.houseType,
          'houseArea': parseInt(this.data.houseMainParams.houseArea),
          'description': this.data.houseMainParams.houseDescribe,
          'platform': 'brand2c',
          'module': 'house',
          'type': 'image',
          'contactNumber': this.data.contactNumber
        })
        .then(res => {
          if (res.success) {
            // wx.showToast({
            //     title: '上传户型成功'
            // })
            // setTimeout(function() {
            //     wx.navigateBack()
            // }, 2000)

            // rzd 户型引导弹框开始

            // rzd 户型引导弹框结束
            _this.setData({
              bootAlertShow: true
            })
            wx.pageScrollTo({
              scrollTop: 0,
              duration: 300
            })
            this.sellingPoint('submit-house-btn');
          } else {
            wx.showToast({
              title: '上传户型失败',
              icon: 'none'
            })
          }
        })
    } else {
      API.uploadFileHouseType({
          'cityInfo': this.data.houseMainParams.region,
          'livingInfo': this.data.houseMainParams.houseEstate,
          'houseType': this.data.houseMainParams.houseType,
          'houseArea': parseInt(this.data.houseMainParams.houseArea),
          'description': this.data.houseMainParams.houseDescribe,
          'contactNumber': this.data.contactNumber || '',
          'platform': 'brand2c',
          'module': 'house',
          'type': 'image',
          'path': this.data.uploadImage
        })
        .then(res => {
          if (res.status) {
            _this.setData({
              bootAlertShow: true
            });
            this.sellingPoint('submit-house-btn');
          } else {
            wx.showToast({
              title: '上传户型失败'
            })
          }
          // res.status ? _this.setData({
          //   bootAlertShow: true
          // }): wx.showToast({
          //       title: '上传户型失败'
          //   })
        })
        .catch(err => {
          wx.showToast({
            title: '上传户型失败'
          })
        })
    }
  },
  closeSucAlert() {
    this.setData({
      bootAlertShow: false,
      houseMainParams: {
        region: '',
        houseEstate: '',
        houseType: '',
        houseArea: '',
        houseDescribe: ''
      },
      uploadImage: '',
      contactNumber: '',
      hideMessage: true,
      showDelete: false
    })
  },
  backHome() {
    wx.switchTab({
      url: '/pages/home/home',
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
})