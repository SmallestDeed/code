// pages/decoration/supplyDetail/supplyDetail.js
import { resourcePath, staticImageUrl } from '../../../utils/config.js'
let API = getApp().API
let $App = getApp();
let share = require('../../../utils/shareConfig.js')
Page({

  /**
   * 页面的初始数据
   */
  data: {
    id: '',
    siShowDetail: false,
    detail: [],
    resourcePath: resourcePath,
    imgUrl: staticImageUrl,
    supplyIcon: $App.staticImageUrl + 'service_icon_gong.png',
    demandIcon: $App.staticImageUrl + 'service_icon_qiu.png',
    emptyPageObj: {},
    mold: '',
    bottomType: 'collect',
    addTypeFlag: false,
    imgList: [],
    imgIdList: [],
    addPlanId: '',
    addPlanImg: '',
    addHouseId: '',
    addHouseImg: '',
    pageSize: 5,
    commentTxt: '',
    planType: '',
    commentList: [],
    scrollId: 'title',
    inputFocus: false,
    windowHeight: '1200rpx',
    order: 'u.gmt_create',
    orderItemFlag: false,
    maskFlag: false,
    bigImgList: [],
    uId: '',//当前登录用户ID
    isShowDelete: 0,
    totalCount: '',
    topMessageState: '留言置顶',
    isTop: 1
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    if (options.addPlanId) {
      this.setData({
        addPlanId: options.addPlanId,
        addPlanImg: options.addPlanImg,
        inputFocus: true,
        maskFlag: false,
        planType: options.planType
      })
    }

    let uid = wx.getStorageSync('id')
    this.setData({
      uId: uid
    })
    new $App.newNav()
    this.setData({
      id: options.id,
      isShowDelete: options.isShowDelete,
      mold: options.mold ? options.mold : '',
      windowHeight: wx.getSystemInfoSync().windowHeight - 10
    })
    if (options.editFlag == 1) {
      wx.navigateTo({
        url: '/pages/publishComment/publishComment?addPlanImg=' + this.data.addPlanImg + '&addPlanId=' + this.data.addPlanId + '&businessId=' + this.data.id + '&userId=' + options.uId + '&planType=' + this.data.planType
      })
    }
  },
  goBack() {
    wx.navigateBack({
      delta: 1
    })
  },
  getComment() {
    API.getSupplydemandComment({ supplyDemandId: this.data.id, curPage: 0, pageSize: this.data.pageSize, order: this.data.order })
      .then(res => {
        if (res.obj) {
          this.setData({
            commentList: res.obj,
            totalCount: res.totalCount
          })
          for (let i = 0; i < res.obj.length; i++) {
            if (res.obj[i].topSign) {
              this.isTopMessage = true;
              break;
            }
            if (i == res.obj.length - 1) this.isTopMessage = false;
          }
        } else {
          this.setData({
            commentList: [],
            totalCount: ''
          })
        }
      })
  },
  changeTiem(tiem) {
    let dateTime = new Date(tiem.replace(/\-/g, '/'))
    let y = dateTime.getFullYear()
    let m = dateTime.getMonth() + 1
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
      date = '1天前';
    }
    if (leadTime / 1000 / 3600 / 48 > 1) {
      date = '2天前'
    }
    if (leadTime / 1000 / 3600 / 72 > 1) {
      date = '3天前'
    }
    if (leadTime / 1000 / 3600 / 96 > 1) {
      date = y + '-' + m + '-' + d
    }
    return date;
  },
  goEdit() {
    wx.navigateTo({
      url: '/pages/decoration/editPublish/editPublish',
    });
  },
  getDetail() {
    API.getSupplydemandinfoDetail({ supplyDemandId: this.data.id })
      .then(res => {
        if (res.obj) {
          let obj = res.obj;
          obj.gmtPublish = this.changeTiem(obj.gmtPublish);
          if (res.obj.baseHouse) {
            res.obj.baseHouse.houseTypeStr = res.obj.baseHouse.houseTypeStr.substr(0, 6)
          }
          this.setData({ detail: obj, siShowDetail: true });
        } else {
          this.setData({
            siShowDetail: false,
            emptyPageObj: {
              imgUrl: this.data.imgUrl + 'issue.png',
              title: res.message,
            }
          });
        }
      })
  },
  addViewNum() {
    let url = 'union/supplydemand/addsupplydemandviewnum'
    API.addSupplyDemandViewCount({ id: this.data.id })
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
    this.getDetail();
    this.addViewNum();
    this.getComment();
    this.setData({
      addPlanImg: this.data.addPlanImg,
      addHouseImg: this.data.addHouseImg
    })
    // let list = this.data.imgList
    // if(this.data.addPlanImg && this.data.addPlanId){
    //   list.splice(0, 0, this.data.resourcePath+this.data.addPlanImg)  
    // }
    // if (this.data.addHouseImg && this.data.addHouseId) {
    //   list.splice(0, 0, this.data.resourcePath + this.data.addHouseImg)
    // }
    this.setData({
      addTypeFlag: false,
      maskFlag: false
    })
    if (this.data.imgList.length > 0 || this.data.commentTxt != '' || this.data.addPlanId || this.data.addHouseId) {

      setTimeout(() => {
        this.setData({
          inputFocus: true,
          maskFlag: false
        })
      }, 500);
    }
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
  refuList() {
    let num = this.data.pageSize
    num += 5
    this.setData({
      pageSize: num
    })
    this.getComment();
  },
  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function (res) {
    if (res.from == 'menu') {

      let url = '/pages/decoration/supplyDetail/supplyDetail',
        parms = {
          id: this.data.id,
          mold: '供求'
        },
        title = this.data.detail.title;
      let obj = share.shareUrl(url, title, parms)
      return obj;
    }
  },
  bottomFocus(e) {
    let selectiveType = e.currentTarget.dataset.type
    wx.navigateTo({
      url: '/pages/publishComment/publishComment?userId=' + this.data.detail.userId + '&businessId=' + this.data.id + '&type=' + selectiveType,
    })
  },
  goStore(e) {
    let item = e.currentTarget.dataset.item
    let id = e.currentTarget.dataset.id;
    if (item.shopId > 0) {
    wx.navigateTo({
      url: '/pages/decoration/companyDetail/companyDetail?id=' + id
    })
    }
  },
  topMessage(e) {
    let that = this,
      status = e.currentTarget.dataset.item.topSign
    if (status) {
      that.data.isTop = 0
      this.getTopMessage(e)
    } else {
      that.data.isTop = 1
      if (!this.isTopMessage) {
        that.getTopMessage(e)
      } else {
      wx.showModal({
        title: '提示',
          content: '是否替换已置顶的评论',
        success(res) {
          if (res.confirm) {
            that.getTopMessage(e)
          } else if (res.cancel) {
            wx.showToast({
              title: '你已取消置顶',
            })
          }
        }
      })
    }
    }
  },
  getTopMessage(e) {
    let that = this
    API.getTopMessage({
      reviewsId: e.currentTarget.dataset.item.id,
      businessId: e.currentTarget.dataset.item.businessId,
      isTop: that.data.isTop
    }).then(res => {
      if (res.status) {
        that.getComment()
        that.getDetail()
      }
      if (e.currentTarget.dataset.item.topSign) {
        wx.showToast({
          title: '置顶已取消',
          icon: 'success',
          duration: 2000
        })
      } else {
        wx.showToast({
          title: '留言已置顶',
          icon: 'success',
          duration: 2000
        })
      }
    })
  },
  deleteBtn(e) {
    let that = this
    wx.showModal({
      title: '提示',
      content: '确认删除么？',
      success: function (sm) {
        if (sm.confirm) {
          API.getDeleteComment({
            reviewsId: e.currentTarget.dataset.id,
            businessId: e.currentTarget.dataset.item.businessId
          })
            .then(res => {
              if (res.status) {
                that.getComment()
                that.getDetail()
              } else {
                wx.showToast({
                  title: '删除失败',
                  icon: 'none'
                })
              }
            })
        }
      }
    })
  },
  deleteInfo(e) {
    let that = this
    wx.showModal({
      title: '提示',
      content: '确认删除么？',
      success: function (sm) {
        if (sm.confirm) {
          API.getMeDeleteComment({
            supplyDemandId: that.data.detail.id
          })
            .then(res => {
              if (res.status) {
                wx.switchTab({
                  url: '/pages/interaction/interactiveArea/interactiveArea',
                })
              } else {
                wx.showToast({
                  title: '删除失败',
                  icon: 'none'
                })
              }
            })
        }
      }
    })
  },
  addMore() {
    this.setData({
      addTypeFlag: !this.data.addTypeFlag,
      maskFlag: !this.data.maskFlag
    })
  },
  addPlan() {
    if (this.data.addPlanId != '' || this.data.addHouseId != '') {

      wx.showToast({
        icon: 'none',
        title: '方案、户型只能上传一个',
      })
      return;
    }
    wx.navigateTo({
      url: '/pages/plan/selection-scheme/selections-scheme',
    })
  },
  addHouse() {
    if (this.data.addPlanId != '' || this.data.addHouseId != '') {
      wx.showToast({
        icon: 'none',
        title: '方案、户型只能上传一个',
      })
      return;
    }
    wx.navigateTo({
      url: '/pages/plan/selection-house-type/selection-house-type',
    })
  },
  addImg() {
    let num = 10;
    this.data.addHouseImg == '' ? num = num : num += 1
    this.data.addPlanImg == '' ? num = num : num += 1
    if (this.data.imgList.length >= num) {
      wx.showToast({
        title: '图片不得超过10张',
      })
      return;
    }
    wx.chooseImage({
      count: 9,
      sourceType: ['album', 'camera'],
      sizeType: ['compressed'],
      success: (res) => {
        if (res.tempFiles.length + this.data.imgList.length > num) {
          wx.showToast({
            title: '图片不得超过10张',
          })
          return;
        }
        wx.navigateTo({
          url: '/pages/publishComment/publishComment?',
        })
        for (let i = 0; i < res.tempFilePaths.length; i++) {
          API.uploadFileIssuedImage({
            'platform': 'mini',
            'module': 'supply',
            'type': 'image',
            'path': res.tempFilePaths[i]
          }).then(res => {
            if (res.success) {
              let list = this.data.imgList,
                idList = this.data.imgIdList;
              res.obj.url = this.data.resourcePath + res.obj.url
              list.splice(-1, 0, res.obj.url)
              idList.splice(-1, 0, res.obj.resId)
              this.setData({
                imgList: list,
                imgIdList: idList
              });
            } else {

            }
          })
        }
        this.setData({
          inputFocus: true
        })
        // console.log(res.tempFilePaths)
        // let list = this.data.imgList
        // list.push.apply(list, res.tempFilePaths);
        // console.log(list)
        // this.setData({
        //   imgList: list
        // });
        // console.log(this.data.imgList)
      }
    });
  },
  deleteImg(e) {
    let index = e.currentTarget.dataset.index
    let list = this.data.imgList
    let idList = this.data.imgIdList
    list.splice(index, 1)
    idList.splice(index, 1)
    this.setData({
      imgList: list,
      imgIdList: idList
    })
  },
  deleteOther(e) {
    let type = e.currentTarget.dataset.type;
    if (type == 'plan') {
      this.setData({
        addPlanImg: '',
        addPlanId: ''
      })
    } else {
      this.setData({
        addHouseImg: '',
        addHouseId: ''
      })
    }
  },
  // textareaInput(e){
  //   let i = /[\uD83C|\uD83D|\uD83E][\uDC00-\uDFFF][\u200D|\uFE0F]|[\uD83C|\uD83D|\uD83E][\uDC00-\uDFFF]|[0-9|*|#]\uFE0F\u20E3|[0-9|#]\u20E3|[\u203C-\u3299]\uFE0F\u200D|[\u203C-\u3299]\uFE0F|[\u2122-\u2B55]|\u303D|[\A9|\AE]\u3030|\uA9|\uAE|\u3030/ig;
  //   this.setData({
  //     commentTxt: e.detail.value.replace(i, '').replace(/\s+/g, '')
  //   })
  // },
  // submitComment(e){
  //   let list = this.data.imgIdList;
  //   console.log(list)
  //   let commentTxt = this.data.commentTxt;
  //   this.data.addPlanId ? list.splice(0, 0, this.data.addPlanId):''  
  //   this.data.addHouseId ? list.splice(0, 0, this.data.addHouseId) : ''
  //   list = list.join(",")
  //   console.log(this.data.planType)
  //   if (commentTxt.replace(/^\s+|\s+$/g,'').length == 0){
  //     wx.showToast({
  //       title: '评论的内容不能为空',
  //       icon: 'none'
  //     })
  //     return
  //   }else{
  //     API.submitComment({
  //       reviewsMsg: this.data.commentTxt,
  //       businessId: this.data.id,
  //       coverPicIds: list,
  //       planId: this.data.addPlanId,
  //       planType: this.data.planType,
  //       houseId: this.data.addHouseId,
  //       supplyDemandPublisherId: this.data.detail.userId
  //     })
  //       .then(res => {
  //         if (res.status) {
  //           wx.showToast({
  //             title: '评论成功',
  //           })
  //           this.setData({
  //             commentTxt: '',
  //             imgIdList: [],
  //             imgList: [],
  //             addPlanId: '',
  //             planType: '',
  //             addHouseId: '',
  //             addPlanImg: '',
  //             addHouseImg: '',
  //             bottomType: 'collect',
  //             addTypeFlag:false
  //           })
  //           this.getComment();
  //           this.getDetail();
  //         } else {
  //           wx.showToast({
  //             title: '评论失败',
  //           })
  //         }
  //       })
  //   } 
  // },
  collectLike(e) {
    var that = this;
    let type = e.currentTarget.dataset.type,
      status = e.currentTarget.dataset.status;
    let detailType;
    if (status == null) {
      status == 0
    }
    type == 'collect' ? detailType = 1 : detailType = 2;
    status == 1 ? status = 0 : status = 1;
    API.collectPage({
      contentId: this.data.id,
      nodeType: 4,
      detailType: detailType,
      status: status
    })
      .then(res => {
        if (res.status) {
          if (type == 'collect') {
            let list = that.data.detail
            list.isFavorite = status
            status == 1 ? list.favoriteNum += 1 : list.favoriteNum -= 1;
            that.setData({
              detail: list
            })
          } else {
            let list = this.data.detail
            list.isLike = status
            status == 1 ? list.likeNum += 1 : list.likeNum -= 1
            that.setData({
              detail: list
            })
          }
        }
      })
  },
  commentLike(e) {
    var that = this;
    let id = e.currentTarget.dataset.id,
      status = e.currentTarget.dataset.status,
      index = e.currentTarget.dataset.index;
    if (status == null) {
      status = 0
    }
    status == 1 ? status = 0 : status = 1
    API.collectPage({
      contentId: id,
      nodeType: 5,
      detailType: 2,
      status: status
    }).then(res => {
      if (res.status) {
        let list = this.data.commentList;
        list[index].isLike = status;
        status == 1 ? list[index].likeNum += 1 : list[index].likeNum -= 1
        that.setData({
          commentList: list
        })
      }
    })
  },
  scroll() {
    if (this.data.scrollId == 'title') {
      this.setData({
        scrollId: 'comment'
      })
    } else {
      this.setData({
        scrollId: 'title'
      })
    }
  },
  scrollView() {
    if (this.data.bottomType != 'collect') {
      if (this.data.imgIdList == '' && this.data.commentTxt == '' && this.data.addPlanId == '' && this.data.addHouseId == '' && this.data.addTypeFlag == false) {
        this.setData({
          bottomType: 'collect'
        })
      }
    }
  },
  houseTodetail(e) {
    let item = e.currentTarget.dataset.item
    wx.navigateTo({
      url: '/pages/house-type/house-details/house-details?houseId=' + item.id + '&type=search&sdId=' + this.data.id,
    })
  },

  toHouseDetail(e) {
    let item = e.currentTarget.dataset.item
    let id, mainTaskId;
    API.copyPlan({
      fullHouseDesignPlanId: item.planRecommendedId,
      mainTaskId: item.mainTaskId,
      supplyDemandId: this.data.id
    }).then(res => {
      // API.copyPlan(item.planRecommendedId).then(res => {
      let list = res.obj.split(',');
      id = list[0];
      mainTaskId = list[1];
      wx.navigateTo({
        url: '/pages/house-type/house-details/house-details?type=myPlan&houseId=' + item.houseId + '&templateId=' + '' + '&fullHousePlanId=' + id + '&mainTaskId=' + mainTaskId + '&sdId=' + this.data.id

      })
    })
  },
  showOrder() {
    this.setData({
      showOrderFlag: !this.data.showOrderFlag
    })
  },
  changeOrder(e) {
    let order = e.currentTarget.dataset.order;
    this.setData({
      order: order
    })
    this.getComment();
    this.setData({
      showOrderFlag: false
    })
  },
  checkImg(e) {
    let url = e.currentTarget.dataset.url;
    wx.previewImage({
      urls: [url]
    })

  },
  // hiddenMaskBox(){
  //   if(this.data.imgIdList=='' && this.data.commentTxt=='' && this.data.addPlanId=='' && this.data.addHouseId=='' && this.data.addTypeFlag==false){
  //     this.setData({
  //       bottomType:'collect'
  //     })
  //   }
  //   this.setData({
  //     maskFlag : false
  //   })

  // },
  toThreeD(e) {
    let planType;
    if (e.currentTarget.dataset.type == 'master') {
      planType = this.data.detail.planType
    }
    if (e.currentTarget.dataset.type == 'salver') {

      planType = this.data.commentList[e.currentTarget.dataset.index].planType
    }
    if (planType == 1 || planType == 2) {//推荐方案原本逻辑
      let type = '720', item = e.currentTarget.dataset.item, routerQueryType = '',
        webUrl = null, sevenObj = null
      wx.setStorageSync('caseItem', item)
      type === '720' ? routerQueryType = 'seven' : routerQueryType = 'roam'
      let planHT;
      if (item.planHouseType) {
        planHT = item.planHouseType
      } else if (item.spaceType) {
        item.spaceType == 13 ? planHT = 2 : planHT = 1
      } else {
        item.spaceFunctionId == 13 ? planHT = 2 : planHT = 1
      }

      item.fullHousePlanUUID ? (webUrl = $App.wholeHouse3dUrl, sevenObj = {
        uuid: item.fullHousePlanUUID,
        token: wx.getStorageSync('token'),
        platformCode: 'brand2c',
        planId: item.designPlanRecommendId || item.planRecommendedId,
        customReferer: $App.wxUrl,
        platformNewCode: 'miniProgram',
        isRender: this.data.isRender,
        groupPrimaryId: item.groupPrimaryId || '',
        houseId: item.houseId || '',
        templateId: item.templateId,
        planHouseType: planHT,
        operationSource: 0,
        isGoods: this.data.isGoods,
        sdId: this.data.id,
        rc: 0
      }) :
        (webUrl = $App.sevenUrl, sevenObj = {
          token: wx.getStorageSync('token'),
          platformCode: 'brand2c',
          operationSource: 1,
          planId: item.designPlanRecommendId || item.planRecommendedId,
          routerQueryType: routerQueryType,
          customReferer: $App.wxUrl,
          platformNewCode: 'miniProgram',
          isRender: 0,
          shopId: this.data.shopId || '',
          isGoods: this.data.isGoods,
          sdId: this.data.id,
          rc: 0
        });

      for (let key in sevenObj) { webUrl += key + '=' + sevenObj[key] + '&' }

      getApp().data.webUrl = webUrl;
      wx.navigateTo({ url: '/pages/web-720/web-720' });

    } else {//我的装修逻辑
      let item = e.currentTarget.dataset.item
      let mainTaskId;
      if (planType == 4) {  //jyk 1130 因发布时发现bug 所以if else走的相同逻辑 之后重构
        API.copyPlan({
          fullHouseDesignPlanId: item.planRecommendedId,
          mainTaskId: item.mainTaskId,
          supplyDemandId: this.data.id
        }).then(res => {
          let list = res.obj.split(',');
          item.id = list[0];
          mainTaskId = list[1]

          let routerQueryType = 'seven',
            webUrl = null,
            sevenObj = null
          planType == 4 ? (webUrl = $App.wholeHouse3dUrl, sevenObj = {
            uuid: item.fullHousePlanUUID,
            token: wx.getStorageSync('token'),
            platformCode: 'brand2c',
            operationSource: 0,
            planId: item.id,
            routerQueryType: 'seven',
            customReferer: $App.wxUrl,
            platformNewCode: 'miniProgram',
            isTask: 1,
            isRender: 0,
            houseId: item.houseId || 0,
            taskType: 3,
            sdId: this.data.id,
            rc: 1,
            mainTaskId: mainTaskId
          }) :
            (webUrl = $App.sevenUrl, sevenObj = {
              token: wx.getStorageSync('token'),
              platformCode: 'brand2c',
              operationSource: 0,
              planId: item.planRecommendedId || item.businessId,
              routerQueryType: routerQueryType,
              customReferer: $App.wxUrl,
              platformNewCode: 'miniProgram',
              isTask: 1,
              taskType: 1,
              houseId: item.houseId || 0,
              sdId: this.data.id,
              rc: 0,
              mainTaskId: item.mainTaskId
            })
          for (let key in sevenObj) { webUrl += key + '=' + sevenObj[key] + '&' }

          getApp().data.webUrl = webUrl;
          wx.navigateTo({ url: '/pages/web-720/web-720' });
        })
      } else {
        let routerQueryType = 'seven',
          webUrl = null,
          sevenObj = null
        planType == 4 ? (webUrl = $App.wholeHouse3dUrl, sevenObj = {
          uuid: item.fullHousePlanUUID,
          token: wx.getStorageSync('token'),
          platformCode: 'brand2c',
          operationSource: 0,
          planId: item.id,
          routerQueryType: 'seven',
          customReferer: $App.wxUrl,
          platformNewCode: 'miniProgram',
          isTask: 1,
          isRender: 0,
          houseId: item.houseId || 0,
          taskType: 3,
          sdId: this.data.id,
          rc: 1,
          mainTaskId: mainTaskId
        }) :
          (webUrl = $App.sevenUrl, sevenObj = {
            token: wx.getStorageSync('token'),
            platformCode: 'brand2c',
            operationSource: 0,
            planId: item.planRecommendedId || item.businessId,
            routerQueryType: routerQueryType,
            customReferer: $App.wxUrl,
            platformNewCode: 'miniProgram',
            isTask: 1,
            taskType: 1,
            houseId: item.houseId || 0,
            sdId: this.data.id,
            rc: 0,
            mainTaskId: item.mainTaskId
          })
        for (let key in sevenObj) { webUrl += key + '=' + sevenObj[key] + '&' }
        getApp().data.webUrl = webUrl;
        wx.navigateTo({ url: '/pages/web-720/web-720' });
      }


    }

  },
  examineImg(e) {
    let imgArr = []
    for (let i = 0, len = this.data.detail.supplyDemandPicList.length; i < len; i++) {
      imgArr.push(this.data.resourcePath + this.data.detail.supplyDemandPicList[i].picPath);
    }
    this.setData({
      bigImgList: imgArr
    });
    wx.previewImage({
      //当前显示下表
      current: this.data.bigImgList[e.currentTarget.dataset.index],
      //数据源
      urls: this.data.bigImgList
    })
  },
  // cancelAdd(){
  //   this.setData({
  //     addTypeFlag:false
  //   })
  // },
  editInfo(e) {
    let type = 'edit';
    let id = this.data.detail.id
    wx.setStorage({
      key: 'editItem',
      data: this.data.detail,
    })
    wx.navigateTo({
      url: `/pages/publishMessage/publishMessage?id=${id}&type=${type}`
    })
  }
})