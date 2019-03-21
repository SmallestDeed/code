// pages/interaction/interactiveArea/interactiveArea.js
import {
    resourcePath,
    defaultImg
} from '../../../utils/config.js'
import utils from '../../../utils/utils.js';
let $App = getApp(),
    API = getApp().API
Page({
    /**
     * 页面的初始数据
     */
    data: {
        staticImageUrl: $App.staticImageUrl,
        sDDefaultImg: '',
        defaultImg: defaultImg,
        condition: false,
        imgURL: getApp().data.imgURL,
        isShowCitys: false,
        isShowClassify: false,
        isCitySlideDown: false,
        isCitySlideTop: true,
        resourcePath: resourcePath,
        currClassify: {
            id: "",
            name: "全部"
        },
        currCity: {
            id: "",
            areaCode: ""
        },
        unreadMsg: 0,
        renderMessageIsRead: 0,
        systemNewObj: { isRead: 1 },
        pageNo: 1,
        brandType: "",
        keyword: "",
        sort: "newest",
        RecommendText: "大牌设计师、装修公司，好评单位，随你挑选。",
        serviceCitys: [],
        pageHeight: 2000,
        isFixedTab: false,
        fixedOffsetTop: 1000,
        scrollDistance: 0,
        fixedTabH: 0,
        supplyType:2,
        supplyIcon: $App.staticImageUrl+'service_icon_gong.png',
        demandIcon: $App.staticImageUrl+'service_icon_qiu.png',
        supplyTopFlag: false,
        ifShowToTopLogo: false,
        banners: [
            "https://shopapi.ci.sanduspace.com/xz/wx/images/zxfw_banner01.jpg",
            "https://shopapi.ci.sanduspace.com/xz/wx/images/zxfw_banner01.jpg",
            "https://shopapi.ci.sanduspace.com/xz/wx/images/zxfw_banner01.jpg"
        ],
        decorationCate: [{
                name: "设计师",
                href: "../brandList/brandList?tab=3", //3设计师 5装修公司 6施工单位 1建材家居
                imgPath: $App.staticImageUrl+"brandList4.png"
            },
            {
                name: "设计公司",
                href: "../brandList/brandList?tab=4",
                imgPath: $App.staticImageUrl+"brandList3.png"
            },
            {
                name: "装修公司",
                href: "../brandList/brandList?tab=5",
                imgPath: $App.staticImageUrl+"brandList5.png"
            },
            {
                name: "工长",
                href: "../brandList/brandList?tab=6",
                imgPath: $App.staticImageUrl+"brandList1.png"
            },
            {
                name: "建材家居",
                href: "../brandList/brandList?tab=2",
                imgPath: $App.staticImageUrl+"brandList2.png"
            }
        ],
        classifyList: [{
                name: "设计师",
                id: "3",
                href: "../brandList/brandList?tab=3"
            },
            {
                name: "装修公司",
                id: "5",
                href: "../brandList/brandList?tab=5"
            },
            {
                name: "施工单位",
                id: "6",
                href: "../brandList/brandList?tab=6"
            },
            {
                name: "建材家居",
                id: "1",
                href: "../brandList/brandList?tab=1"
            }
        ],
        type: 'service',
        supplyList: [],
        supPageSize: 5,
        supcurPage: 1,
        supType: 'new',
        cityData: [],
        provinces: [],
        province: "",
        citys: [],
        city: '全部',
        countys: [],
        county: '',
        value: [0, 0, 0],
        threeLevelValue: [0, 0, 0],
        condition: false,
        cityCode: '',
        pageSize: 5,
        cityItem: '',
        scrollY: 0,
        praiseRecommended: 0,
        praiseRecommendedFixed: false,
        fitmentList: [],
        fitmentIconList: [
          $App.staticImageUrl+'renovation.png', $App.staticImageUrl+'service_icon_07.png',
          $App.staticImageUrl+'supply_gz.png', $App.staticImageUrl+'service_icon_09.png',
          $App.staticImageUrl+'service_icon_04.png', $App.staticImageUrl+'brandList5.png'
        ],
        buildingList: [],
        buildingIconList: [
          $App.staticImageUrl+'jiancai.png', $App.staticImageUrl+'jiaju.png',
          $App.staticImageUrl+'service_icon_12.png'
        ],
        manpowerList: [],
      manpowerIconList: [$App.staticImageUrl+'service_icon_01.png', $App.staticImageUrl+'service_icon_13.png'],

      // rzd_data_satrt
      supply: 0, // 0代表需求，1供应；默认0
      // rzd_data_end

      suppHead: false, // 界面滚动
      rzd_sort: false, // 最新最热三角号
      sel_sortShow: false,
      selSort: 'new',
      sortTit: '最新',
      sortTit2: '最新',

      // 埋点数据
      previousPath: '',
      nowPath: ''
    },
    // rzd_js_start

    //埋点
    sellingPoint(event) {
      let page = getCurrentPages(), previousPath = page.length > 1 ? page[page.length - 2].route : '',
        nowPath = page[page.length - 1].route
      API.sellingPoint({
        uid: wx.getStorageSync('openId'),
        cp: nowPath,
        lp: previousPath,
        e: event ? event : '',
        pt: '互动区'
      })
    },
    //供求按钮切换
    /* 
    changeSupply(e) {
      let type = e.currentTarget.dataset.supply;
      let supplyType = e.currentTarget.dataset.type;
      this.setData({
        supply: type,
        supplyType: supplyType
      })
      this.getsupplyinfo();
    },
    // 展开最新最热弹框
    changeRzdSort() {
      this.setData({
        rzd_sort: !this.data.rzd_sort,
        sel_sortShow: !this.data.sel_sortShow
      })
    },
    // 关闭最新最热选择弹框
    closeSelSort() {
      this.setData({
        sel_sortShow: false,
        rzd_sort: false
      })
    }, 
    */
    // 选择最新与最热
    setSelSort(e) {
      let type = e.currentTarget.dataset.setsort;
      this.setData({
        selSort: type
      })
      if(type =='hot') {
        this.setData({
          sortTit: '最热'
        })
        $App.sellingPoint(API, 'huDongsetSelSortHot', this.data.nowPath, this.data.previousPath, '互动区');
      }else {
        this.setData({
          sortTit: '最新'
        })
        $App.sellingPoint(API, 'huDongsetSelSortNew', this.data.nowPath, this.data.previousPath, '互动区');
      }
      this.getsupplyinfo();
      // this.setData({
      //   sel_sortShow:false,
      //   rzd_sort:false
      // })
    },
    //rzd_js_end
    /**
     * 生命周期函数--监听页面加载
     */
     onPageScroll(e) {
      //  e.scrollTop > 200 ? this.setData({ ifShowToTopLogo: true }) : this.setData({ ifShowToTopLogo: false })
      //  if (this.data.type == 'service') {
      //    if (this.data.sel_sortShow == true) {
      //      this.setData({
      //        sel_sortShow: false,
      //        rzd_sort: false
      //      })
      //    }
      //    if (e.scrollTop > 480) {
      //      this.setData({
      //        supplyTopFlag: true
      //      })
      //    } else {
      //      this.setData({
      //        supplyTopFlag: false
      //      })
      //    }
      //  } else {
      //    if (this.data.sel_sortShow == true) {
      //      this.setData({
      //        sel_sortShow: false,
      //        rzd_sort: false
      //      })
      //    }
      //    if (e.scrollTop > 200) {
      //      this.setData({
      //        supplyTopFlag: true
      //      })
      //    } else {
      //      this.setData({
      //        supplyTopFlag: false
      //      })
      //    }
      //  }
     },
    goSeek() {
      $App.sellingPoint(API, 'huDonggoSeek', this.data.nowPath, this.data.previousPath, '互动区');
      wx.navigateTo({
        url: `/pages/decoration/seek-service/seek-service?city=${this.data.city}&cityCode=${ this.data.cityCode}`,
      })
    },
    // goToTop() { // 回到顶部
    //     this.setData({
    //         ifShowToTopLogo: false
    //     })
    //     wx.pageScrollTo({
    //         scrollTop: 0,
    //         duration: 300
    //     })
    // },
  onLoad: function (options) {
    // rzd 埋点 190214 start
    let page = getCurrentPages(), previousPath = page.length > 1 ? page[page.length - 2].route : '',
      nowPath = page[page.length - 1].route;
    this.setData({
      previousPath: previousPath,
      nowPath: nowPath
    })
    // rzd 埋点 190214 end
      wx.hideShareMenu();
      this.sellingPoint();
      let type = wx.getStorageSync('cityServiceType')
      if (type) {
        this.setData({
          type: type
        })
        wx.setStorageSync('cityServiceType', '')
      }
        this.setData({
            resourcePath: resourcePath,
          sDDefaultImg:this.data.staticImageUrl + 'news_pic_nor.png'
        })
        this.setScroll();
        this.getBanner();
    },
    getsupplyinfo() {
        let obj = null
        // this.data.cityCode == '' ? obj = {
        //     isSortByGmtModified: 'desc'
        // } : (this.data.selSort == 'new' ? obj = {
        //     isSortByGmtModified: 'desc'
        // } : obj = {
        //     isSortByViewNum: 'desc'
        // })
      this.data.selSort == 'new' ? obj = {
            isSortByGmtModified: 'desc'
        } : obj = {
            isSortByViewNum: 'desc'
        }
        API.getallsupplydemandinfo(Object.assign({
          curPage: this.data.supcurPage,
          pageSize: this.data.supPageSize,
          type: this.data.supplyType
            }, obj))
            .then(res => {
                let arr = [];
                if (res.obj != null) {
                    for (let i = 0; i < res.obj.length; i++) {
                        if (res.obj[i].supplyDemandPicList) {
                            res.obj[i].onePicture = res.obj[i].supplyDemandPicList[0].picPath
                        } else if (res.obj[i].supplyDemandCoverPic){
                          res.obj[i].onePicture = res.obj[i].supplyDemandCoverPic.picPath
                        }
                    }
                  arr = res.obj;
                  arr.forEach((value, index) => {
                    value.gmtModified = this.changeTiem(value.gmtModified);
                  });
                } else {
                  arr =[];  
                }
                this.setData({
                    supplyList: arr
                })
            })
    },
    changeTiem(tiem) {
      let dateTime = new Date(tiem.replace(/\-/g, '/'))
      let y = dateTime.getFullYear()
      let m = dateTime.getMonth()+1
      let d = dateTime.getDate()
      let leadTime = new Date().getTime() - dateTime, date;
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
        date =  '1天前';
      }
      if (leadTime / 1000 / 3600 / 48 > 1){
        date = '2天前'
      }
      if (leadTime / 1000 / 3600 / 72 > 1){
        date = '3天前'
      }
      if (leadTime / 1000 / 3600 / 96 > 1){
        date = y + '-' + m + '-' + d
      }
      return date;
    },
    getBanner() {
        // let url = '/base/banner/web/union/list?positionCode=union:service:supply:top'
        // fetch(url, 'get', {}, 'system').then(res => {
        //   if (res.success == 'true' && res.dataList) {
        //     this.setData({
        //       bannerList: res.dataList
        //     })
        //   }
        // })
    },
    getCityData() {
        let nowCity = wx.getStorageSync('nowCity')
        if (!nowCity) {
            let item = wx.getStorageSync('cityLoctionItem')
            if (item) {
                this.setData({
                    cityItem: item,
                    city: item.areaName,
                    cityCode: item.cityCode
                })
            } else {
                let data = {
                    areaCode: '',
                    areaName: "全部",
                    baseAreaVos: [],
                    id: 0,
                    levelId: 1,
                    pid: 0
                }
                this.setData({
                    cityItem: data
                })
                wx.setStorageSync('nowCity', data)
            }
        } else {
            this.setData({
                cityItem: nowCity
            })
        }

        // 根据获取用户地址，再进行请求
        // this.getList();
        this.getsupplyinfo();
    },
    changeCtiy: function(e) {
        this.setData({
            condition: !this.data.condition
        })
    },
    professionCommit(e) {
        let id = e.currentTarget.dataset.id
        this.setData({
            id: id,
            professionFlag: false,
            professionValue: e.currentTarget.dataset.index
        })
        this.getsupplyinfo();
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
        // let cityDatas = wx.getStorageSync('nowCity');
        // if (!cityDatas){
        //   let city = cityDatas.areaName || '深圳';
        //   city = city.split('').length > 3 ? city.split('')[0] + city.split('')[1] + city.split('')[2] + '...' : city
        //   this.setData({
        //     city: city,
        //     cityCode: cityDatas.areaCode || '440000',
        //   })
        // }
        // this.getList();
        // this.webSocketInit() // 初始化webSocket中的事件
        this.getsupplyinfo();
        this.getCityData();
        this.getAllSupply();
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
          fitmentList: fitmentList,
          buildingList: buildingList,
          manpowerList: manpowerList
        });
      });
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
      let size = this.data.supPageSize;
      size = size + 5
      this.setData({
        supPageSize: size
      })
      this.getsupplyinfo();
    },
    /**
     * 用户点击右上角分享
     */
    getSystemNewsList() { // 实时轮训系统消息
        API.getSystemRenderList({
            order: "gmt_modified",
            orderNum: "desc",
            limit: 1,
            start: 0
        }, 'noLoading')
            .then(res => {
                if (res.success && res.obj) {
                    this.setData({
                        systemNewObj: res.obj[0]
                    })
                } else {
                    this.setData({
                        systemNewObj: {
                            title: '暂时没有系统消息',
                            isRead: 1,
                            gmtCreate: ''
                        }
                    })
                }
            })
    },
    onShareAppMessage: function() {

    },
    setScroll: function() {
        var This = this;
        wx.getSystemInfo({
            success: function(res) {
                // 可使用窗口宽度、高度
                This.setData({
                    pageHeight: res.windowHeight
                })
            }
        })
        // setTimeout(function () {
        //   This.queryMultipleNodes("#fixed_tab", function (res) {
        //     This.setData({
        //       fixedOffsetTop: res[0].top + This.data.scrollDistance,
        //       fixedTabH: res[0].height
        //     })
        //     //因为需要加载图片等数据 所以要延迟一会才能获取到最新的数据、
        //   })
        // }, 800)
    },
    scroll: function(e) {
        var This = this;
        This.setData({
            scrollDistance: e.detail.scrollTop
        })
        if (e.detail.scrollTop >= this.data.fixedOffsetTop) {
            This.setData({
                isFixedTab: true
            })
        } else {
            This.setData({
                isFixedTab: false
            })
        }
    },
    queryMultipleNodes: function(selector, cb) {
        var This = this;
        if (!selector)
            return;
        var query = wx.createSelectorQuery()
        query.select(selector).boundingClientRect()
        query.selectViewport().scrollOffset()
        query.exec(function(res) {
            cb && typeof cb == "function" && cb(res)
        })
    },
    scrollLower: function() {
        this.getList();
    },
    concatService: function(path) {
        return getApp().URL + path;
    },
    toggleClassify: function() {
        var This = this;
        if (this.data.isShowClassify) {
            This.setData({
                isClassifySlideTop: true,
                isClassifySlideDown: false
            })
            setTimeout(function() {
                This.setData({
                    isClassifySlideTop: false,
                })
            }, 1000)
        } else {
            This.setData({
                isClassifySlideTop: false,
                isClassifySlideDown: true
            })
            setTimeout(function() {
                This.setData({
                    isClassifySlideDown: false,
                })
            }, 1000)
        }
        this.setData({
            isShowClassify: !this.data.isShowClassify
        })
    },
    toggleCitys: function() {
        // this.setData({
        //     condition: !this.data.condition
        // })
        wx.navigateTo({
            url: '/pages/chooseAddress/chooseAddress?cityItem=' + JSON.stringify(this.data.cityItem),
        })
    },
    changeClassify: function(e) {
        var classifyId = e.currentTarget.dataset.classifyid;
        var classifyname = e.currentTarget.dataset.classifyname;
        if (classifyId == undefined || classifyname == undefined)
            return;
        this.setData({
            currClassify: {
                id: classifyId,
                name: classifyname
            },
            pageNo: 1
        })
        this.toggleClassify();
        this.getList();
    },
    resort: function(e) {
        var orderBy = e.currentTarget.dataset.sort;
        this.setData({
            sort: orderBy,
            pageNo: 1
        })
        // this.getList();
        this.setData({
            pageScrollTop: 0
        })
    },
    webSocketInit() { // 初始化webSorket中的事件
        setInterval(() => { this.getSystemNewsList() }, 20000)
        let timer = setInterval(() => {
            if (getApp().webSocketStatus) {
                getApp().mySocket.on('im_unread_msg_event', content => { this.setData({ unreadMsg: 1 }) }) // 消息提醒
                getApp().mySocket.on('im_push_msg_event', content => { this.setData({ renderMessageIsRead: 1 }) }) // 渲染消息
                clearInterval(timer)
            }
        }, 500)
    },
    toHref: function(e) {
        var sHref = e.currentTarget.dataset.href,
            param = '',
            name = e.currentTarget.dataset.name;
        if (sHref.slice(0, 22) == '../brandList/brandList') {
            param = '&code=' + this.data.cityCode + '&city=' + this.data.city + '&name=' + name
        }
        if (sHref == undefined)
            return;
        wx.navigateTo({
            url: sHref + param,
        })
    },
    toNew(e){
        var sHref = e.currentTarget.dataset.href;
        this.setData({
            unreadMsg: 0,
            renderMessageIsRead: 0,
            ['systemNewObj.isRead']: 1
        })
        wx.navigateTo({
            url: sHref,
        })
        
    },
    toClassify: function(e) {
        var sHref = e.currentTarget.dataset.href;
        if (sHref == undefined)
            return;
        wx.navigateTo({
            url: sHref,
        })
        this.toggleClassify();
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
    changeCity: function(e) {
        var cityId = e.currentTarget.dataset.cityid;
        if (cityId == undefined)
            return;
        var currCity = this.getItem("areaCode", cityId, this.data.serviceCitys);
        if (!currCity)
            return;
        this.setData({
            currCity: currCity
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
    /* getList: function() {
        let query = wx.createSelectorQuery().in(this),
            that = this
        this.setData({
            cityCode: this.data.cityItem.areaCode,
            city: this.data.cityItem.areaName,
        })
        API.getCompanyShopList({
                pageNo: 1,
                pageSize: this.data.pageSize,
                businessType: this.data.currClassify.id,
                shopName: this.data.keyword,
                cityCode: this.data.cityItem.areaCode,
                orderBy: this.data.sort,
                platformType: 2
            })
            .then(res => {
                let exampleList = []
                this.data.pageNo == 1 ? null : exampleList = this.data.exampleList
                if (res.code == 200) {
                    let arr = [];
                    for (let i = 0; i < res.data.list.length; i++) {
                        if (res.data.list[i].businessType != 1) {
                            arr.push(res.data.list[i])
                        }
                    }
                    this.setData({
                        exampleList: arr
                    })
                    // 获取好评的距离
                    // if (this.data.praiseRecommended == 0) {
                    //     setTimeout(() => {
                    //         wx.createSelectorQuery().in(this).select('.hptj').boundingClientRect(function(res) {
                    //             that.data.praiseRecommended = res.top - res.height
                    //         }).exec()
                    //     }, 300)
                    // }
                } else if (res.code == 400) {
                    this.setData({
                        exampleList: []
                    })
                }
            })
    },*/
    setKeyword: function(e) {
        var keyword = e.detail.value;
        this.setData({
            keyword: keyword
        })
    },
    searchList: function() {
        this.setData({
            pageNo: 1,
            currClassify: {
                id: "",
                name: "全部"
            },
            brandType: "",
            sort: ""
        })
        // this.getList();
    },
    doPreview: function(e) {
        var imgPath = e.currentTarget.dataset.imgpath;
        if (imgPath == undefined)
            return;
        wx.previewImage({
            current: imgPath, // 当前显示图片的http链接
            urls: [imgPath] // 需要预览的图片http链接列表
        })
    },
    clearIpt: function() {
        this.setData({
            keyword: ""
        })
    },
    focusIpt: function() {
        this.setData({
            isShowIptClose: true
        })
    },
    changeType(e) {
        this.setData({
            type: e.currentTarget.dataset.type
        })
    },
    toDeatil(e) {
        let id = e.currentTarget.dataset.id;
        let cityObj = {
          id: id,
          city: this.data.city,
          cityCode: this.data.cityCode,
          title: e.currentTarget.dataset.title
        }
        wx.setStorageSync('cityObj', cityObj);
        let url = '../supplyList/supplyList';
        wx.navigateTo({
            url: url
        })
    },
    changeSupType(e) {
        let type = e.currentTarget.dataset.suptype
        this.setData({
            supType: type
        })
        this.getsupplyinfo();
    },
    toPublish() {
      $App.sellingPoint(API, 'huDongtoPublish', this.data.nowPath, this.data.previousPath, '互动区');
        wx.navigateTo({
          url: '/pages/publishMessage/publishMessage',
        })
    },
    toDetail(e) {
      let id = e.currentTarget.dataset.id; 
      let mold = e.currentTarget.dataset.type;
      $App.sellingPoint(API, 'huDongtoDetail', this.data.nowPath, this.data.previousPath, '互动区');
      wx.navigateTo({
        url: '/pages/decoration/supplyDetail/supplyDetail?id=' + id + '&mold=' + mold,
      })
    },
    toSearch() {
        wx.navigateTo({
            url: '/pages/decoration/search/search?type=' + this.data.type + '&code=' + this.data.cityCode + '&city=' + this.data.city,
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
        if (flag) {
            this.setData({
                province: this.data.cityData[val[0]].areaName,
                city: this.data.cityData[val[0]].baseAreaVos[val[1]].areaName,
                cityCode: this.data.cityData[val[0]].baseAreaVos[val[1]].areaCode,
                value: this.data.threeLevelValue
            })
            // this.getList()
            this.getsupplyinfo();
        }
    },
    toCompany(e) {
        let id = e.currentTarget.dataset.id;
        wx.navigateTo({
            url: '/pages/decoration/companyDetail/companyDetail?id=' + id,
        })
    },
    serviceSort(e) {
      let type = e.currentTarget.dataset.sort;
      this.setData({
        sort: type
      })
      if (type == 'hottest') {
        this.setData({
          sortTit2: '最热'
        })
      } else {
        this.setData({
          sortTit2: '最新'
        })
      }
      // this.getList()
      this.setData({
        sel_sortShow: false,
        rzd_sort: false
      })
    }
})