// js规范
// 所有函数采用onshow写法，不要用onload function的写法
const app = getApp(),
  API = app.API,
  myCompoundUrl = getApp().myCompoundUrl //每个界面都要写上API，才能调
var pageData = {}
Page({
  data: {
    motto: 'Hello World',
    userInfo: {},
    hasUserInfo: false,
    canIUse: wx.canIUse('button.open-type.getUserInfo'),
    resourcePath: app.data.resourcePath, // 这个是图片的前缀
    viewHeight: 0,
    boxHeight: 0,
    contentHeight: 0,
    bowenCounter: 0,
    caseCounter: 0,
    isPull: false,
    defaultImg: '/static/images/news_pic_nor.png',
    defaultHeader: '/static/images/header.png',
    indexBanner: [], // banner
    designerList: [], // 设计师列表
    GongZhang: [], //工长团队
    IndexBrand: [], // 合用品牌
    serviceMessage: {}, // 服务信息
    filterProvince: '',
    projectCaseList: [], // 工程案例
    bowenList: [], // 博文列表
    indicatorDots: true,
    autoplay: true,
    interval: 3000,
    duration: 1000,
    circular: true,
    infoObj: {},
    wxInfo: false,
  },
  onLoad: function(options) {
    this.getUserDuBiMessage() // 需要token的实现
    this.userShare(options) // 用户分享
    // this.setTitles()
  },
  userShare(options) {
    if (options.item) { // 720
      let item = JSON.parse(options.item)
      item.url = decodeURIComponent(item.url)
      let shareTimer = setInterval(() => {
        if (wx.getStorageSync('token')) { // 获取token后再进行跳转       
          getApp().webUrl = myCompoundUrl(Object.assign(item, {
            token: wx.getStorageSync('token')
          }))
          wx.navigateTo({
            url: '/pages/web-720/web-720'
          })
          clearInterval(shareTimer)
        }
      }, 100)
    }
  },
  getUserDuBiMessage() {
    // 接口使用说明
    // 你在目录api/index-api.js中写的接口函数调用，在这里直接用
    let timer = setInterval(() => {
      if (app.data.loginStatus === 1) {
        clearInterval(timer)
        this.CompanyIdShopId();
        // this.setTitles()
      }
    }, 100)
  },

  CompanyIdShopId() {
    API.getIndexCompanyIdShopId().then((res) => {
      wx.setStorageSync('companyId', res.obj.companyId)
      wx.setStorageSync('shopId', res.obj.shopId) // res.obj.shopId
    })
  },

  //图片点击事件
  imgPreview(event) {
    var src = event.currentTarget.dataset.src; //获取data-src
    var index = event.currentTarget.dataset.index; //获取data-list
    let item = this.data.IndexQualification
    let arr = [];
    for (let i = 0; i < item.length; i++) {
      arr.push(this.data.resourcePath + item[i].picPath)
    }
    //图片预览
    wx.previewImage({
      current: src, // 当前显示图片的http链接
      urls: arr // 需要预览的图片http链接列表
    })
  },


  setTitles() {
    wx.setNavigationBarTitle({
      title: wx.getStorageSync('shopNames') || '店铺'
    })
  },

  // 首页banner
  getIndexBanner() {
    // 接口使用说明
    // 你在目录api/index-api.js中写的接口函数调用，在这里直接用
    API.getIndexBanner({
      positionCode: "shop:home:page:top"
    }).then(res => {
      if (res.datalist) {
        this.setData({
          indexBanner: res.datalist
        })
      }
    })
  },

  makePhoneCall() {
    wx.makePhoneCall({
      phoneNumber: this.data.infoObj.contactPhone
    })
  },

  goutong() {
    let items = this.data.infoObj;
    items.shopIntroduced = '';
    let item = JSON.stringify(items);
    wx.navigateTo({
      url: `/pages/chat/chat?item=${item}`,
    })
  },

  goTeamList(e) {
    let type = e.currentTarget.dataset.type
    wx.navigateTo({
      url: `/pages/teamList/teamList?type=${type}`,
    })
  },

  // 首页合作品牌
  getIndexBrand() {
    API.getIndexBanner({
      positionCode: 'shop:home:page:brandPic'
    }).then(res => {
      if (res.datalist) {
        this.setData({
          IndexBrand: res.datalist
        })
      } else {
        this.setData({
          IndexBrand: []
        })
      }
    })
  },
  // 企业资质
  getQualification() {
    API.getIndexBanner({
      positionCode: 'shop:home:page:qualification'
    }).then(res => {
      if (res.datalist) {
        this.setData({
          IndexQualification: res.datalist
        })
      } else {
        this.setData({
          IndexQualification: []
        })
      }
    })
  },
  getDesigner() {
    API.getIndexDesignerList({
      pageNo: 1,
      pageSize: 10,
      businessType: '',
      // shopName:'',
      // cityCode:'',
      // categoryIds:'',
      platformType: 4,
      userType: 5,
      orderBy: 'all',
      // decorationType: '',
      companyId: wx.getStorageSync('companyId'),
      shopType: 2
    }).then(res => {
      if (res.data) {
        this.setData({
          designerList: res.data.list
        })
      } else {
        this.setData({
          designerList: []
        })
      }
    })
  },
  getGongZhang() {
    API.getIndexDesignerList({
      pageNo: 1,
      pageSize: 10,
      businessType: '',
      // shopName:'',
      // cityCode:'',
      // categoryIds:'',
      platformType: 4,
      userType: 13,
      orderBy: 'all',
      // decorationType: '',
      companyId: wx.getStorageSync('companyId'),
      shopType: 2
    }).then(res => {
      if (res.data) {
        this.setData({
          GongZhang: res.data.list
        })
      } else {
        this.setData({
          GongZhang: []
        })
      }

    })
  },
  // 工程案例
  projectCase() {
    API.getIndexProjectList({
      pageNo: 1,
      pageSize: 10,
      shopId: wx.getStorageSync('shopId')
    }).then((res) => {
      if (res.datalist) {
        this.setData({
          projectCaseList: res.datalist,
          caseCounter: res.totalCount
        })
      } else {
        this.setData({
          projectCaseList: [],
          caseCounter: 0
        })
      }
    })
  },
  // 工程详情
  toDetail(e) {

    let item = e.currentTarget.dataset.item
    wx.navigateTo({
      url: `/pages/trulyCaseDetail/trulyCaseDetail?caseId=${item.caseId}`,
    })
  },
  // 博文列表
  getBowenList() {
    API.getIndexBowenList({
      limit: 10,
      page: 1,
      shopId: wx.getStorageSync('shopId')
    }).then((res) => {
      if (res.datalist) {
        let arr = res.datalist;
        arr.forEach((value) => {
          value.releaseTimeStr = app.changeTiem(value.releaseTimeStr)
        })
        this.setData({
          bowenList: arr,
          bowenCounter: res.totalCount
        })
      } else {
        this.setData({
          bowenList: [],
          bowenCounter: 0
        })
      }
    })
  },
  designer(e) {
    let item = e.currentTarget.dataset.item;
    wx.navigateTo({
      url: `/pages/designer/designer?shopId=${item.id}`
    })
  },
  onShow: function() {
    let timer = setInterval(() => {
      if (app.data.loginStatus === 1 && wx.getStorageSync('shopId')) {
        clearInterval(timer)
        this.getDesigner() // 设计师列表
        this.getGongZhang() // 工长团队
        this.getIndexBrand() //合作品牌    
        this.getQualification() // 企业资质
        this.serviceInfo() //获取服务信息
        this.projectCase() //工程案例
        this.getBowenList() // 博文列表
        this.getIndexBanner() // 不需要token的实现
        this.getLoginStatus()
      }
    }, 100)
  },
  // 博文列表
  toBowenList() {
    wx.navigateTo({
      url: `/pages/bowen/bowenList?shopId=${wx.getStorageSync('shopId')}`,
    })
  },
  // 跳到工长团队详情
  gongZhangDetail(e) {
    let item = e.currentTarget.dataset.item;
    wx.navigateTo({
      url: `/pages/gongZhangDetail/gongZhangDetail?shopId=${item.id}`,
    })
  },
  // 跳到博文详情
  bowenDetail(e) {
    let item = e.currentTarget.dataset.item;
    wx.navigateTo({
      url: `/pages/bowenDetail/bowenDetail?articleid=${item.articleId}`,
    })
  },
  // 到工程案例
  toCaseList() {
    wx.navigateTo({
      url: `/pages/trulyCase/trulyCase?shopId=${wx.getStorageSync('shopId')}`,
    })
  },

  viewHeight() {
    if (this.data.contentHeight > this.data.boxHeight) {
      this.setData({
        isPull: !this.data.isPull
      })
    }
  },
  // 获取富文本盒子高度
  getBoxHeigth() {
    let query = wx.createSelectorQuery();
    query.select('#rich-text').boundingClientRect(rect => {
      this.setData({
        contentHeight: rect.height
      });
    }).exec();
    query.select('#theShow').boundingClientRect(rect => {
      this.setData({
        boxHeight: rect.height
      });
    }).exec();
  },
  // 服务信息
  serviceInfo() {
    API.getIndexServiceInfo({
      shopId: wx.getStorageSync('shopId'),
      platformValue: 4
    }).then((res) => {
      if (res.data) {
        wx.setStorageSync('shopNames', res.data.shopName)
        // 缓存承接范围
        if (res.data.serviceArea) {
          wx.setStorageSync('serviceArea', res.data.serviceArea)
        }
        this.setData({
          infoObj: res.data
        })
        this.setTitles()
        res.data.shopIntroduced = res.data.shopIntroduced ? res.data.shopIntroduced.replace(/\<img/gi, '<img class="img" ') : '';
        // filterProvince

        if (res.data.provinceName == res.data.cityName) {
          this.setData({
            filterProvince: res.data.provinceName
          })
        } else {
          this.setData({
            filterProvince: res.data.provinceName + res.data.cityName
          })
        }
        if (res.data.shopIntroduced) {
          res.data.shopIntroduced = getApp().filtrationHtml(res.data.shopIntroduced);
        }
        this.setData({
          serviceMessage: res.data
        })
        if (res.data.shopIntroduced && res.data.shopIntroduced.length > 0) {
          setTimeout(() => {
            this.getBoxHeigth();
          }, 500)
        }
      }
    })
  },

  getLoginStatus() {
    let loginData = wx.getStorageSync('loginStatus')
    console.log(1111111, loginData)
    if (loginData.headPic || loginData.avatarUrl) {
      this.setData({
        wxInfo: false
      })
    } else {
      this.setData({
        wxInfo: true
      })
    }
  },

  /*获取用户信息*/
  onGotUserInfo(e) {
    if (e.detail.userInfo) {
      console.log(444444444, '已授权')
      // getApp().globalData.userInfo = e.detail.userInfo
      let loginData = wx.setStorageSync('loginStatus', e.detail.userInfo)
      this.setData({
        wxInfo: false
      })
    } else {
      this.setData({
        wxInfo: false
      })
    }
    console.log(this.data.wxInfo, getApp().globalData.userInfo)
    API.saveMinProNickName({
      nickName: e.detail.userInfo.nickName,
      headPic: e.detail.userInfo.avatarUrl
    })
  },
  closeBtn() {
    wx.removeStorage({
      key: 'loginStatus',
      success: function(res) {},
    })
    this.setData({
      wxInfo: false,
    })
  },

  /**
   * 生命周期函数--监听页面隐藏
   */
  onHide: function() {
    this.setData({
      isPull: false
    })
  },
  onShareAppMessage() {} // 分享
})