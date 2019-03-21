// pages/decoration/publishDetail/publishDetail.js
let $App = getApp(), API = getApp().API
import { resourcePath } from '../../../utils/config.js'
Page({

  /**
   * 页面的初始数据
   */
  data: {
    checkIcon: '/static/image/list_icon_check_nor.png',
    checkedIcon: '/static/image/list_icon_check_sel.png',
    supplyType: 2,//供求
    condition: false,//城市弹窗flag
    cityData: [],
    provinces: [],
    province: "",
    provinceCode: "",
    citys: [],
    city: "",
    cityCode: "",
    countys: [],
    county: '',
    countyCode: '',
    value: [0, 0, 0],
    threeLevelValue: [0, 0, 0],
    region: '',
    supplyId: '',
    arr: {},
    classifyFlag: false,
    classifyValue: 0,
    classifyValue2: 0,
    img: '',
    imgidarr: [],
    imgarr: [],
    title: '',
    content: '',
    user: '',
    phone: '',
    address: '',
    decorationCompany: 1,//装修公司
    designer: 1,
    proprietor: 1,
    builder: 1,
    materialShop: 1,
    isShowPhotograph: false
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
      new $App.newNav() // 注册快速导航组件
    // if(options.type){
    //   let itme = wx.getStorage('editItem')
    //   this.setData({
    //     title: item.title,
    //     content: item.description,
    //     idarr: item.coverPicId.split(','),
    //     supplyType: item.type,
    //     // supplyDemandCategoryId: this.data.arr.supplyDemandCategoryVos[this.data.classifyValue].pid + ',' + this.data.arr.supplyDemandCategoryVos[this.data.classifyValue].id,
    //     provinceCode: item.province,
    //     cityCode: item.city,
    //     countyCode: item.district,
    //     address: item.address,
    //     user: item.contact,
    //     phone: item.phone,
    //     builder: item.builder,
    //     decorationCompany: item.decorationCompany,
    //     designer: item.designer,
    //     materialShop: item.materialShop,
    //     proprietor: item.proprietor
    //   })
    // }

    this.setData({
      supplyId: options.supplyId
    })

    this.getCityData();
    this.getSupply();

  },
  photograph() {

  },
  getCityData() {
    let cityData = wx.getStorageSync('cityData')
    let provinces = []
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
    this.data.cityData = cityData;

  },
  getSupply() {//获取类别选项
    API.getAllSupplyDemandCategory({ type: 1})
      .then(res => {
        for (let i = 0; i < res.obj[0].supplyDemandCategoryVos.length; i++) {
          if (res.obj[0].supplyDemandCategoryVos[i].id == this.data.supplyId) {
            this.setData({
              arr: res.obj[0].supplyDemandCategoryVos[i]
            })
          }
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
  onShareAppMessage: function () {

  },
  changType(e) {
    let type = e.currentTarget.dataset.type;
    this.setData({
      supplyType: type
    })
  },
  selectCity() {
    this.setData({
      condition: true
    })
  },
  bindChange: function (e) {
    const cityData = wx.getStorageSync('cityData')
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
    console.log(val)
    this.setData({
      threeLevelValue: val,
      citys: cityData[val[0]].baseAreaVos,
      countys: cityData[val[0]].baseAreaVos[val[1]].baseAreaVos
    })
  },
  provincialLinkageHide(e) { // 确认地址接口
    let flag = e.currentTarget.dataset.flag, val = this.data.threeLevelValue
    console.log("--->", val)
    this.setData({
      condition: false
    })
    if (flag) {

      let region = this.data.cityData[val[0]].areaName + this.data.cityData[val[0]].baseAreaVos[val[1]].areaName + this.data.cityData[val[0]].baseAreaVos[val[1]].baseAreaVos[val[2]].areaName
      console.log(this.data.cityData)
      this.setData({
        province: this.data.cityData[val[0]].areaName,
        provinceCode: this.data.cityData[val[0]].areaCode,
        city: this.data.cityData[val[0]].baseAreaVos[val[1]].areaName,
        cityCode: this.data.cityData[val[0]].baseAreaVos[val[1]].areaCode,
        county: this.data.cityData[val[0]].baseAreaVos[val[1]].baseAreaVos[val[2]].areaName,
        countyCode: this.data.cityData[val[0]].baseAreaVos[val[1]].baseAreaVos[val[2]].areaCode,
        region: region,
        value: this.data.threeLevelValue
      })
    }
  },
  classify() {
    console.log("123")
    this.setData({
      classifyFlag: true
    })
  },

  getTitle(e) {
    var val = e.detail.value;
    console.log(val)
    this.setData({
      title: val
    });
    console.log(this.data.title)
  },
  getContent(e) {
    var val = e.detail.value;
    this.setData({
      content: val
    });
  },
  getUser(e) {
    var val = e.detail.value;
    this.setData({
      user: val
    });
  },
  getPhone(e) {
    console.log(e.detail.value)
    var val = e.detail.value;
    this.setData({
      phone: val
    });
  },
  getAddress(e) {
    console.log("123")
    var val = e.detail.value;
    console.log(val)
    this.setData({
      address: val
    });
    console.log(this.data.address)
  },

  classifyChange(e) {
    const val = e.detail.value
    this.setData({
      classifyValue2: val
    })
  },
  classifyCommit(e) {
    let flag = e.currentTarget.dataset.flag
    if (flag) {
      this.setData({
        classifyValue: this.data.classifyValue2
      })
    }
    this.setData({
      classifyFlag: false
    })
  },
  uploadPictrue() { // 上传图片
    if (this.data.imgarr.length >= 9) {
      wx.showToast({
        icon: none,
        title: '最多上传9张图片',
      })
    } else {
      this.setData({
        isShowPhotograph: true
      });
    }
  },
  // 添加图片操作
  photograph(e) {
    if (e.currentTarget.dataset.type == '取消') {
      this.setData({ isShowPhotograph: false});
    } else {
      console.log(e.currentTarget.dataset.type);
      wx.chooseImage({
        count: 1,
        sourceType: [e.currentTarget.dataset.type],
        success: (res) => {
          console.log(res, 1);
          this.data.imgarr.push(res.tempFiles[0].path)
          this.setData({ imgarr: this.data.imgarr, img: res.tempFiles[0].path, isShowPhotograph: false })
          this.upload(this.data.imgarr.length);
        }
      })
    }
  },
  upload(index) {
    var that = this;
    API.uploadFileIssuedImage({
      'platform': 'brand2c',
      'module': 'supply',
      'type': 'image',
      'path': this.data.imgarr[index - 1] 
    })
    .then(res => {
      if (res.status) {
        this.data.imgidarr.push(res.obj.resId)
        this.setData({ imgidarr: this.data.imgidarr })
        wx.showToast({ title: '上传图片成功'})
      } else {
        wx.showToast({ title: '上传图片失败', icon: 'none'})
      }
    })
  },
  submit() {
    let content = ''
    if (this.data.title == '') {
      content = '请填标题'
    } else if (this.data.content == '' || this.data.content.length < 10) {
      content = '内容需要大于10'
    } else if (this.data.address == '') {
      content = '请填写地址'
    } else if (this.data.phone == '') {
      content = '请填写联系方式'
    }

    if (content) { wx.showModal({ title: '提示', content }); return; }

    API.publishSupplyAndDemand({
      title: this.data.title,
      description: this.data.content,
      coverPicId: this.data.imgidarr.join(","),
      type: this.data.supplyType,
      supplyDemandCategoryId: this.data.arr.supplyDemandCategoryVos[this.data.classifyValue].pid + ',' + this.data.arr.supplyDemandCategoryVos[this.data.classifyValue].id,
      province: this.data.provinceCode,
      city: this.data.cityCode,
      district: this.data.countyCode,
      address: this.data.address,
      contact: this.data.user,
      phone: this.data.phone,
      street: '',
      builder: this.data.builder,
      decorationCompany: this.data.decorationCompany,
      designer: this.data.designer,
      materialShop: this.data.materialShop,
      proprietor: this.data.proprietor
    })
      .then(res => {
        if (res.status) {
          wx.showToast({
            title: '发布成功',
            icon: 'success',
            duration: 3000,
            complete: function () {
              setTimeout(function () { wx.navigateBack({ delta: 2 }) }, 3000)
            }
          })
        } else {
          wx.showModal({
            title: '提示',
            content: res.message,
            confirmText: '确定',
            cancelText: '取消',
            cancelColor: '#999',
            confirmColor: '#ff6419'
          })
        }
      })
  },
  changerange(e) {
    let id = e.currentTarget.dataset.id
    if (id == 1) {
      if (this.data.decorationCompany == 1) {
        this.setData({
          decorationCompany: 0
        })
      } else {
        this.setData({
          decorationCompany: 1
        })
      }
    }
    if (id == 2) {
      if (this.data.designer == 1) {
        this.setData({
          designer: 0
        })
      } else {
        this.setData({
          designer: 1
        })
      }
    }
    if (id == 3) {
      if (this.data.proprietor == 1) {
        this.setData({
          proprietor: 0
        })
      } else {
        this.setData({
          proprietor: 1
        })
      }
    }
    if (id == 4) {
      if (this.data.builder == 1) {
        this.setData({
          builder: 0
        })
      } else {
        this.setData({
          builder: 1
        })
      }
    }
    if (id == 5) {
      if (this.data.materialShop == 1) {
        this.setData({
          materialShop: 0
        })
      } else {
        this.setData({
          materialShop: 1
        })
      }
    }
  },
  deleteImg(e) {
    let index = e.currentTarget.dataset.index;
    console.log(index)
    let arr = this.data.imgarr
    let idarr = this.data.imgidarr
    arr.splice(index, 1);
    idarr.splice(index, 1)

    this.setData({
      imgarr: arr,
      imgidarr: idarr
    })
  }
})