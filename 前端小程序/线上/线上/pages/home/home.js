import regeneratorRuntime from '../../utils/runtime.js';
let API = getApp().API,
    myForEach = getApp().myForEach,
    mySplitUrl = getApp().mySplitUrl,
    myCompoundUrl = getApp().myCompoundUrl,
    $App = getApp(),
    ttt = '';
let bmap = require('../../lib/es6-promise/bmap-wx.min.js');
import {
    shareTitle
} from '../../utils/config.js';
import {
    emptyTemplate
} from '../../component/emptyTemplate/emptyTemplate'

Page({
    emptyTemplate,
    /**
     * 页面的初始数据
     */
    data: {
      conditionNameList: {
        'spaceTypeVList3': '客餐厅',
        'spaceTypeVList4': '卧室',
        'spaceTypeVList5': '厨房',
        'spaceTypeVList6': '卫生间',
        'spaceTypeVList7': '书房',
        'spaceTypeVList8': '衣帽间',
        'spaceTypeVList13': '全屋'        
      },
      staticImageUrl: getApp().staticImageUrl,
      popularList1: [],
      popularList2: [],
      popularList3: [],
      praiseList: [],
      resourcePath: getApp().resourcePath,
      // 方案
      scrollTop: 0,
      floorstatus: false,
      imageArray: ['https://homesiteres.zbjimg.com/homesite%2Fres%2F300X250.jpg%2Forigine%2F4ef2b90d-f4a3-4589-abef-ce2104fa65ea', 'https://homesiteres.zbjimg.com/homesite%2Fres%2F300X250.jpg%2Forigine%2F4ef2b90d-f4a3-4589-abef-ce2104fa65ea'],
      spaceList: [],
      areaList: [],
      styleList: [],
      types: 0,
      favoriteRequest: true,
      collectRequest: true,
      conditionActive: -1,
      childConditionActive: [0, -1, -1],
      recommendCaseList: [],
      areaId: '',
      styleCode: '',
      oneAreaId: '',
      resourcePath: getApp().resourcePath,
      sevenUrl: getApp().sevenUrl,
      pageSize: 5,
      showType:false,
      getCaseParams: {
          spaceType: '',
          designPlanStyleId: '',
          spaceArea: ''
      },
      caseObj: [],
      isShow: true,
      totalCount: 0,
      caseListheight: '',
      caseListOverflow: 'none',
      scroolLeft: 0,
      ifShowToTopLogo: false,
      caseListheight: '',
      caseListOverflow: 'none',
      newTypeName:[],
      decoratePriceBox: false,
      decoratePriceList: [],
    },

    /**
     * 生命周期函数--监听页面加载
     */
    onLoad: function(options) {
      this.init(options) // 初始化
    },
    init(options) { // 初始化
      if (options.navToUrl) {
        wx.showLoading({
          title: '跳转中...'
        })
        setTimeout(function () {
          wx.hideLoading();
          wx.navigateTo({
            url: decodeURIComponent(options.navToUrl)
          })
        }, 500)
      }
      var that = this
      //同城联盟回调
      if (options.userId) {
        if (options.type == '720') {
          this.callback(options.userId, options.shareType, options.time)
          let sevenObj = {
            platformCode: options.platformCode,
            operationSource: options.operationSource,
            planId: options.planId,
            routerQueryType: options.routerQueryType,
            customReferer: options.customReferer,
            platformNewCode: options.platformNewCode
          }
          let webUrl = this.data.sevenUrl
          for (let key in sevenObj) {
            webUrl += key + '=' + sevenObj[key] + '&'
          }
          getApp().data.webUrl = webUrl
          $App.myNavigateBack('pages/web-720/web-720')
        } else {
          this.callback(options.userId, options.shareType, options.time)
        }
      }
      //渠道分享回调
      var that = this
      Date.prototype.toLocaleString = function () {
        return this.getFullYear() + "-" + (this.getMonth() + 1) + "-" + this.getDate() + " " + that.addZero(this.getHours()) + ":" + that.addZero(this.getMinutes()) + ":" + that.addZero(this.getSeconds());
      };

      if (options.scene) {
        console.log("1", decodeURIComponent(options.scene))
        let list = decodeURIComponent(options.scene).split(',');
        console.log("2", list)
        //将时间戳String转int，String的话结果会有误
        let timeStamp = parseInt(list[2])
        let inviteId = list[0],
          shareType = list[1],
          // new Date（时间戳）方法将时间戳转为标准时间，toLocaleString方法将标准时间转为需要的时间
          inviteTime = new Date(timeStamp).toLocaleString() //10位乘以1000，十三位不用动
        console.log("inviteId:" + inviteId)
        console.log("shareType:" + shareType)
        console.log("inviteTime:" + inviteTime)
        this.placeCall(inviteId, shareType, inviteTime)
      }
      this.getPopular();
      this.getUserPosition() // 获取用户地理位置
      new this.emptyTemplate() // 注册组件
    },
    hideDecoratePriceBox() {
      this.setData({ decoratePriceBox: false })
    },
    showDecoratePriceBox(e) {
      let item = e.detail
      console.log(e.detail)
      this.setData({ decoratePriceList: item, decoratePriceBox: true })
    },
    routerToCaseDetails(e) { // 方案详情页
        let id = e.currentTarget.dataset.id,
            type = e.currentTarget.dataset.type
        wx.navigateTo({
            url: `/pages/case-details/case-details?id=${id}&type=${type || 0}`
        })
    },
    changeType:function(){
        if (this.data.showType) {
            wx.pageScrollTo({
                scrollTop: 670,
                duration: 0
            })
        }
        this.setData({
            showType: !this.data.showType,
            newTypeName: this.data.spaceList
        })
    },
    placeCall(e1,e2,e3){
      var that = this
      console.log("123", $App.data.loginStatus, $App.data.loginFlag)
      if ($App.data.loginStatus == '' & $App.data.loginFlag == false) {
        setTimeout(function () {
          that.placeCall(e1, e2, e3)
        }, 1000);
        return;
      }
      if (!$App.data.loginFlag) {
        return;
      } else {
        API.invitecallbackshare({
          userId: e1,
          shareType: e2,
          shareTime: e3,
          shareSign: 'sxw'
        })
          .then(res => {
            console.log("回调结果:" + JSON.stringify(res))
          })
      }
      
    },
    callback(e1, e2, e3) { //e1 userId,e2 shareType,e3 time 
        var that = this
        console.log("123", $App.data.loginStatus, $App.data.loginFlag)
        if ($App.data.loginStatus == '' & $App.data.loginFlag == false) {
            setTimeout(function() {
                that.callback(e1, e2, e3)
            }, 1000);
            return;
        }
        if (!$App.data.loginFlag) {
            return;
        } else {

            if ($App.data.loginStatus == 0) {
                var that = this;
                Date.prototype.toLocaleString = function() {
                    return this.getFullYear() + "-" + (this.getMonth() + 1) + "-" + this.getDate() + " " + that.addZero(this.getHours()) + ":" + that.addZero(this.getMinutes()) + ":" + that.addZero(this.getSeconds());
                };
                let inviteId = e1,
                    shareType = e2,
                    inviteTime = new Date(e3 * 1).toLocaleString()
                let url = '/union/invite/invitecallback';
                API.invitecallback({
                        inviteId: inviteId,
                        shareType: shareType,
                        inviteTime: inviteTime,
                        shareSign: 'union'
                    })
                    .then(res => {console.log("回调结果",res)})
            }
        }

    },

    addZero(num) {
        num = num.toString();
        if (num.length == 1) {
            num = '0' + num
        }
        return num
    },
    onPageScroll(e) {
        e.scrollTop > 200 ? this.setData({
            ifShowToTopLogo: true
        }) : this.setData({
            ifShowToTopLogo: false
        })
    },
    goToTop() { // 回到顶部
        this.setData({
            ifShowToTopLogo: false
        })
        wx.pageScrollTo({
            scrollTop: 0,
            duration: 300
        })
    },
    getUserPosition() { // 获取用户地理位置
        let BMap = new bmap.BMapWX({
                ak: 'eXo93TLB1GE2dnrcl1rcot18qvu2vMpG'
            }),
            cityMessage = null,
            $self = this
        BMap.regeocoding({
            success(data) { // 成功之后调用接口进行保存
                cityMessage = data.originalData.result.addressComponent // 这就是我们需要的东西 
                let cityData = $App.cityDataFilter(wx.getStorageSync('cityData')),
                    allCity = [];
                //把所有市一级拿出来    
                for (let i = 0; i < cityData.length; i++) {
                    allCity = allCity.concat(cityData[i].baseAreaVos)
                }
                let item;
                for (let i = 0; i < allCity.length; i++) {
                    if (allCity[i].areaName == cityMessage.city) {
                        item = allCity[i]
                    }
                }
                wx.setStorageSync('cityMessage', cityMessage)
                wx.setStorageSync('cityLoctionItem', item)
                wx.getStorage({
                    key: 'nowCity',
                    fail:function(){
                        wx.setStorageSync('nowCity', item)
                    }
                })
                
            },
            fail(e) {
                wx.showModal({
                    title: '获取地理位置',
                    content: '您已取消获取位置，再次获取还是继续取消？',
                    success(res) {
                        if (res.confirm) {
                            wx.openSetting({
                                success(res) {
                                    if (!res.authSetting["scope.userInfo"] || !res.authSetting["scope.userLocation"]) {
                                        $self.getUserPosition()
                                    } else {
                                        wx.showToast({
                                            title: '获取地理位置失败',
                                            icon: 'none'
                                        })
                                    }
                                }
                            })
                        } else if (res.cancel) {
                            wx.showToast({
                                title: '获取地理位置失败',
                                icon: 'none'
                            })
                        }
                    },
                    fail() {
                        wx.showToast({
                            title: '获取地理位置失败',
                            icon: 'none'
                        })
                    }
                })
            }
        });
    },
    async getPlanCondition() { // 获取筛选条件进而获取方案 
      let res = await API.getConditionMetadata()
      myForEach(res.obj[0].designPlanAreaList, (value) => {
        value.areaName = value.areaName.replace('~', '-')
      })
      this.setData({
        newTypeName: res.obj,
        spaceList: res.obj,
        areaList: res.obj[0].designPlanAreaList,
        oneAreaId: '',
        styleList: res.obj[0].designPlanStyleVoList,
        'getCaseParams.spaceType': res.obj[0].houseType
      })     
      for (let i = 0; i < res.obj.length; i++) {
        await this.getRecommendCaseList(res.obj[i].houseType, i)
      }
    },
    getPopular() {
        API.getShopPopularityList({
                businessType: 3,
                platformType: 2
            })
            .then(res => {
                if (res.code == 200) {
                    this.setData({
                      popularList1: res.data.list.reduce((before, next, index) => {
                        index % 3 ? before[before.length - 1].push(next) : before.push([next]);
                        return before;
                      }, [])
                    })
                } else {
                    this.setData({
                        popularList1: []
                    })
                }
            })
        API.getShopPopularityList({
                businessType: 4,
                platformType: 2
            })
            .then(res => {
                if (res.code == 200) {
                    this.setData({
                        popularList2: res.data.list
                    })
                } else {
                    this.setData({
                        popularList2: []
                    })
                }
            })
        API.getShopPopularityList({
                businessType: 5,
                platformType: 2
            })
            .then(res => {
              if (res.code == 200) {
                this.setData({
                  popularList3: res.data.list.slice(0, 1)
                })
                this.setData({
                  praiseList: res.data.list.slice(1).reduce((before, next, index) => {
                    index % 2 ? before[before.length - 1].push(next) : before.push([next]);
                    return before;
                  }, [])
                });
              } else {
                this.setData({
                  popularList3: [],
                  praiseList: []
                })
              }
            })
    },
    shouMoreFun: function() {
        this.data.isShow ? this.setData({
            isShow: false
        }) : this.setData({
            isShow: true
        });
    },
    areaIdFun: function(e) {
        this.setData({
            areaId: e.currentTarget.dataset.info || '',
            'getCaseParams.spaceArea': e.currentTarget.dataset.info || ''
        })

    },
    addZero(num) {
        num = num.toString();
        if (num.length == 1) {
            num = '0' + num
        }
        return num
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
      API.getHomeCaseList({
        curPage: 1,
        pageSize: 5,
        spaceType: '',
        designPlanStyleId: '',
        spaceArea: '',
        displayType: 'decorate',
        isSortByReleaseTime: 0,
        decoratePriceType: '',
        decoratePriceRange:'',
      }).then(res => {
        this.setData({ caseObj: res.success ? res.obj : []})
      })
      // this.getPlanCondition() // 获取筛选条件进而获取方案 
    },
    /**
     * 用户点击右上角分享
     */
    onShareAppMessage: function() {

    },
    navigatTo(e) {
        let url = e.currentTarget.dataset.url
        let tab = e.currentTarget.dataset.tab
        let name = e.currentTarget.dataset.name || '设计师'
        wx.navigateTo({
            url: url + '?tab=' + tab + '&name=' + name,
        })
    },
    toCase(){
        wx.switchTab({
            url: '/pages/plan/house-case/house-case',
        })
    },
    toDetail(e) {
        let id = e.currentTarget.dataset.id
        wx.navigateTo({
            url: '/pages/decoration/companyDetail/companyDetail?id=' + id,
        })
    },
    navigat(e) {
        let url = e.currentTarget.dataset.url
      if (e.currentTarget.dataset.type == "信息栏") {
        wx.switchTab({
          url: '/pages/decoration/cityService/cityService'
        })
      } else {
        wx.navigateTo({
          url: url,
        })
      }
    },
    switchT(e) {
        let url = e.currentTarget.dataset.url
        wx.switchTab({
            url: url,
        })
    },

    //方案
    enlargeImage(url) { // 查看大图
        wx.previewImage({
            current: url, // 当前显示图片的http链接
            urls: [url] // 需要预览的图片http链接列表
        })
    },
    toThreeD(e) { // 调转到3D界面
        let type = e.currentTarget.dataset.type,
            item = e.currentTarget.dataset.item,
            routerQueryType = null,
            webUrl = this.data.sevenUrl
        if (type === 'video') {
            API.getRecommendedVideoId({
                    planRecommendedId: item.designPlanRecommendId,
                    remark: type
                })
                .then(res => {
                    if (res.success) {
                        return res.datalist[0].id
                    } else {
                        return false
                    }
                })
                .then(res => {
                    if (res) {
                        API.getRecommendedVideoMessage({
                                thumbId: res
                            })
                            .then(res => {
                                res.success ? this.toVideo(res.obj.url) : wx.showToast({
                                    title: '打开失败',
                                    icon: 'none'
                                })
                            })
                    }
                })
        } else {
            type === '720' ? routerQueryType = 'seven' : routerQueryType = 'roam'
            let sevenObj = {
                token: wx.getStorageSync('token'),
                platformCode: 'brand2c',
                operationSource: 1,
                planId: item.designPlanRecommendId,
                routerQueryType: routerQueryType,
                customReferer: $App.wxUrl,
                platformNewCode: 'miniProgram'
            }
            for (let key in sevenObj) {
                webUrl += key + '=' + sevenObj[key] + '&'
            }
            getApp().data.webUrl = webUrl
            $App.myNavigateBack('pages/web-720/web-720')
        }
    },
    closeCaseCondition() {
        this.setData({
            conditionActive: -1
        })
        if (this.data.conditionActive === -1) {
            this.setData({
                caseListheight: '',
                caseListOverflow: 'none'
            })
        } else {
            let caseListheight = wx.getSystemInfoSync().windowHeight
            this.setData({
                caseListheight: caseListheight + 'px',
                caseListOverflow: 'hidden'
            })
        }
    },
    getRecommendCaseList(tpye, num) { // 获取方案列表
        API.getRecommendCaseList({
                curPage: 1,
                pageSize: 5,
                spaceType: tpye || 3,
                designPlanStyleId:  '',
                spaceArea:  '',
                displayType: 'decorate',
                isSortByReleaseTime: 0
            })
            .then(res => { 
                if (res.status) {
                    if (res.obj) {
                        this.setData({
                          recommendCaseList: res.obj,
                          totalCount: res.totalCount,
                          ['caseObj[' + num + ']']: res.obj
                        })
                    } else {
                        this.setData({
                          recommendCaseList: [],
                          totalCount: 0,
                          ['caseObj[' + num + ']']: []
                        })
                    }
                } else {
                    this.setData({
                        recommendCaseList: [],
                        caseObj: [],
                        totalCount: 0
                    })
                }
            })
    },

    toVideo(url) {
        wx.navigateTo({
            url: '/pages/template/video/video?url=' + url
        })
    },
    putInMyhouse(e) { // 装进我家
        // this.renderTypeShow() // 显示组件  
        let item = e.currentTarget.dataset.item
        wx.setStorageSync('caseItem', item)
        wx.navigateTo({
            url: '/pages/plan/case-house-type/case-house-type'
        })
    },
    toPlan() {
        wx.switchTab({
            url: '/pages/plan/house-case/house-case',
        })
    }
})