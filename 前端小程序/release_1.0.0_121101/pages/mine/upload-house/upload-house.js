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
        provinceList: [],
        cityList: [],
        districtList: [],
        province: '',
        city: '',
        district: '',
        threeLevelValue: [0, 0, 0],
        provincialLinkageShow: false,
        cityData: [],
        houseMainParams: {
            region: '',
            houseEstate: '',
            houseName: '',
            houseArea: '',
            houseDescribe: '',
        },
        contactNumber: '',
        uploadImage: '/static/image/pic_add.png',

      bootAlertShow: false  // 户型引导弹框
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
        // let aaa =  this;
        // setInterval(function(res){
        //     console.log(aaa.getRandomNum('sss'), res);
        // },1000)
        new $App.newNav() // 注册快速导航组件
        this.sellingPoint()
        let cityData = wx.getStorageSync('cityData')
        this.data.cityData = cityData
        let provinceList = []
        $App.myForEach(cityData, (value) => {
            provinceList.push({
                areaCode: value.areaCode,
                areaName: value.areaName,
                id: value.id,
                levelId: value.levelId,
                pid: value.pid
            })
        })
        this.setData({
            provinceList: provinceList,
            cityList: this.data.cityData[0].baseAreaVos,
            districtList: this.data.cityData[0].baseAreaVos[0].baseAreaVos
        })
    },
    //埋点
    sellingPoint(event) {
      let page = getCurrentPages(), previousPath = page.length > 1 ? page[page.length - 2].route : '',
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
            cityList: this.data.cityData[val[0]].baseAreaVos,
            districtList: this.data.cityData[val[0]].baseAreaVos[val[1]].baseAreaVos,
            threeLevelValue: val
        })
    },
    provincialLinkageHide(e) { // 隐藏三级
        let val = this.data.threeLevelValue
        this.setData({
            provincialLinkageShow: false
        })
        if (e.currentTarget.dataset.type === 'confirm') {
            this.data.houseMainParams.region = this.data.cityData[val[0]].areaName + this.data.cityData[val[0]].baseAreaVos[val[1]].areaName + this.data.cityData[val[0]].baseAreaVos[val[1]].baseAreaVos[val[2]].areaName
            this.setData({
                value: this.data.threeLevelValue,
                province: this.data.cityData[val[0]].areaName,
                city: this.data.cityData[val[0]].baseAreaVos[val[1]].areaName,
                district: this.data.cityData[val[0]].baseAreaVos[val[1]].baseAreaVos[val[2]].areaName,
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
        }else {
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
          }else {
            console.log(v)
          }
        }
      }else {
        data()
      }
    },
    // 地区选择API结束
    uploadHousePictrue() { // 上传户型图
        wx.chooseImage({
            count: 1,
            success: (res) => {
                this.setData({
                    uploadImage: res.tempFiles[0].path
                })
            }
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
        } else if (this.data.uploadImage === '/static/image/pic_add.png') {
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
                    'houseName': this.data.houseMainParams.houseName,
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
                    'houseName': this.data.houseMainParams.houseName,
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
                    _this.setData({bootAlertShow: true});
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
        houseName: '',
        houseArea: '',
        houseDescribe: ''
      },
      uploadImage: '/static/image/pic_add.png',
      contactNumber: ''
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