let WxParse = require('../../utils/wxParse/wxParse.js'),
  API = getApp().API,
  $App = getApp(),
  staticImageUrl = getApp().staticImageUrl,
  isEmojiCharacter = getApp().isEmojiCharacter,
  myForEach = getApp().myForEach;
let fetch = getApp().fetch
Page({

  /**
   * 页面的初始数据
   */
  data: {
    caseDetails: {},
    resourcePath: getApp().resourcePath,
    sevenUrl: getApp().sevenUrl,
    favoriteRequest: true,
    collectRequest: true,
    decoratePriceBox: false,
    caseListheight: '',
    caseListOverflow: 'none',
    decoratePriceList: [],
    isWholeHouse: 1, // 2表示全屋
    secondRender: false,
    staticImageUrl: staticImageUrl,
    inputFlag: false,
    inputFlag2: false,
    getPhone: false,
    message: '获取验证码',
    item: '',
    getObj: {
      phone: '',
      code: ''
    },
    planId: '',
    imgUrl: getApp().staticImageUrl,
    commentList: [],
    commentTxt: '',
    sort: 0,
    totalCount: '',
    textFocus: false,
    placeholderTxt: '说点什么吧...',
    cmtPname: '',
    cmtPid: '',
    windowHeight: '1200rpx',
    scrollId: 'banner',
    collectRequest: true,
    favoriteRequest: true,
    nowUserId: '',
    submitFlag: false
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {

    this.setData({
      isWholeHouse: options.type,
      planId: options.id,
      windowHeight: wx.getSystemInfoSync().windowHeight - 10,
      nowUserId: wx.getStorageSync('userId')
    })
    this.getCaseDetails(options.id, options.type == 2 ? 1 : 0) // 获取方案详情
  },
  getCaseDetails(id, type) {
    API.getCaseDetails({
      id: id,
      type
    })
      .then(res => {
        res.status ? this.setData({
          caseDetails: res.obj[0]
        }) : this.setData({
          caseDetails: {}
        })
        res.status ? WxParse.wxParse('article', 'html', res.obj[0].desc || '', this, 5) : null
        console.log(this.data.caseDetails, 'caseDetails')
      })
  },
  toThreeD(e) {
    let item = e.currentTarget.dataset.item,
      routerQueryType = e.currentTarget.dataset.type,
      webUrl = null,
      sevenObj = {}
    console.log(item.designPlanRecommendId, 'swq')
    if (routerQueryType === 'video') {
      API.getRecommendedVideoId({
        planRecommendedId: item.planRecommendedId || item.designPlanRecommendId,
        remark: routerQueryType
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
      item.fullHousePlanUUID ? (webUrl = $App.wholeHouseUrl, sevenObj = {
        uuid: item.fullHousePlanUUID,
        embedded: 1
      }) :
        (webUrl = this.data.sevenUrl, sevenObj = {
          token: wx.getStorageSync('token'),
          platformCode: 'brand2c',
          operationSource: 1,
          planId: item.planRecommendedId || item.designPlanRecommendId,
          routerQueryType: routerQueryType,
          customReferer: $App.wxUrl,
          platformNewCode: 'miniProgram'
        })
      for (let key in sevenObj) {
        webUrl += key + '=' + sevenObj[key] + '&'
      }
      getApp().data.webUrl = webUrl
      $App.myNavigateBack('pages/web-720/web-720')
      console.log(webUrl)
    }
  },
  toVideo(url) {
    wx.navigateTo({
      url: '/pages/video/video?url=' + url
    })
  },

  putInMyhouse() { // 装进我家
    // this.renderTypeShow() // 显示组件  
    let _that = this
    if (wx.getStorageSync('bindingPhone')) {
      _that.toPutInMyhouse();
      return;
    }
    getApp().fetch(`/v2/user/center/checkUserSecondRender`, 'get', {},
      'user').then((res) => {
        if (res.success ? res.obj : false) {
          _that.setData({ getPhone: true })
        } else {
          _that.toPutInMyhouse();
        }
      })
  },
  toPutInMyhouse() {
    let item = this.data.caseDetails
    wx.setStorageSync('caseItem', item)
    wx.navigateTo({
      url: '/pages/case-house-type/case-house-type'
    })
  },
  closeFun() {
    this.setData({
      getPhone: !this.data.getPhone,
      click: true,
    })
    this.toPutInMyhouse()
  },
  changeInput(e) {
    let key = 'getObj.' + e.target.dataset.type
    this.setData({
      [key]: e.detail.value.trim(),
    })
    if (e.target.dataset.type == 'phone' && e.detail.value.length >= 11) {
      this.setData({
        inputFlag: true,
      })
    }
  },
  getCodeFun() {
    let phone = this.data.getObj.phone,
      that = this;
    if (!phone && !(/^1[3|4|5|8|9][0-9]\d{8}$/.test(phone))) {
      wx.showToast({
        title: '请正确填写电话!',
        icon: 'none'
      })
      return;
    }
    let url = `/v1/center/getSmsCode`
    getApp().fetch(url, 'formData', { phoneNumber: phone, functionType: 2 }, 'user')
      .then((res) => {
        if (res.success) {
          let num = 60
          that.setData({
            inputFlag2: true,
            message: num + 's',
          })
          let setFunc = setInterval(function () {
            num--;
            that.setData({
              message: num + 's',
            })
            if (num <= 0) {
              clearInterval(setFunc);
              that.setData({
                message: '获取验证码',
              })
            }
          }, 1000)
        }
      })
  },
  submitFun() {
    let that = this,
      code = this.data.getObj.code;
    if (!code) {
      wx.showToast({
        title: '请填写验证码',
        icon: 'none'
      })
      return
    }
    let url = `/v2/user/center/bindUserMobile`
    getApp().fetch(url, 'formData', {
      mobile: that.data.getObj.phone,
      authCode: code
    }, 'user').then((res) => {
      if (res.success) {
        wx.showToast({
          title: '绑定成功'
        })
        wx.setStorage({
          key: 'bindingPhone',
          data: 1,
        })
        that.setData({
          getPhone: false
        })
        that.toPutInMyhouse()
      } else {
        wx.showToast({
          title: res.message,
          icon: 'none'
        })
      }
    })
  },
  toShopDetail(e) {
    if (!this.data.caseDetails.shopId && this.data.caseDetails.shopId != 0) {
      wx.showToast({
        title: '暂无门店',
        icon: 'none'
      })
      return
    }
    wx.navigateTo({
      url: '/pages/companyDetail/companyDetail?id=' + this.data.caseDetails.shopId,
    })
  },
  showDecoratePriceBox(e) { // 显示装修报价弹框
    let item = e.currentTarget.dataset.item,
      height = wx.getSystemInfoSync().windowHeight
    if (item) {
      myForEach(item, (value) => {
        switch (value.decorateTypeName) {
          case "半包":
            value.text = '辅材+人工费+管理费';
            break;
          case "清水":
            value.text = '人工费+管理费';
            break;
          case "全包":
            value.text = '主材+辅材+人工费+管理费';
            break;
          case "包软装":
            value.text = '主材+辅材+人工费+管理费+部分软装';
            break;
        }
      })
      this.setData({
        decoratePriceList: item,
        decoratePriceBox: true,
        caseListheight: height + 'px',
        caseListOverflow: 'hidden'
      })
    } else {
      this.setData({
        decoratePriceBox: false,
        caseListheight: '',
        caseListOverflow: 'none'
      })
    }
  },
  onShow: function () {
    this.getComment();
  },
  getComment() {
    let url = '/comment/list';
    let data = {
      userId: wx.getStorageSync('userId'),
      type: 1,
      planId: this.data.planId,
      sortType: this.data.sort,
      pageNo: 1,
      pageSize: 10
    }
    fetch(url, 'get', data, 'local')
      .then((res) => {
        if (res.obj) {

          this.setData({
            commentList: res.obj,
            totalCount: res.totalCount
          })
          //   console.log(res.obj[0].comment.indexOf('\n'), 'sasasaas')
          //   console.log(res.obj[0].comment.replace('\n', '\n'))
          //   console.log(res.obj)
        } else {
          this.setData({
            commentList: [],
            totalCount: 0
          })
        }

      })
  },
  onShareAppMessage: function () {

  },

  // commentLike(e) {
  //   var that = this;
  //   let id = e.currentTarget.dataset.id,
  //     status = e.currentTarget.dataset.status,
  //     index = e.currentTarget.dataset.index;
  //   if (status == null) {
  //     status = 0
  //   }
  //   status == 1 ? status = 0 : status = 1
  //   API.collectPage({
  //     contentId: id,
  //     nodeType: 5,
  //     detailType: 2,
  //     status: status
  //   }).then(res => {
  //     if (res.status) {
  //       let list = this.data.commentList;
  //       console.log(list)
  //       console.log(index)
  //       list[index].isLike = status;
  //       status == 1 ? list[index].likeNum += 1 : list[index].likeNum -= 1
  //       that.setData({
  //         commentList: list
  //       })
  //     }
  //   })
  // },
  textareaInput(e) {
    let i = /[\uD83C|\uD83D|\uD83E][\uDC00-\uDFFF][\u200D|\uFE0F]|[\uD83C|\uD83D|\uD83E][\uDC00-\uDFFF]|[0-9|*|#]\uFE0F\u20E3|[0-9|#]\u20E3|[\u203C-\u3299]\uFE0F\u200D|[\u203C-\u3299]\uFE0F|[\u2122-\u2B55]|\u303D|[\A9|\AE]\u3030|\uA9|\uAE|\u3030/ig;
    let flag = isEmojiCharacter(e.detail.value);
    this.setData({
      commentTxt: flag ? "" : e.detail.value.trim()
    })
    if (this.data.commentTxt != '') {
      this.setData({
        submitFlag: true
      })
    }
  },
  textF() {
    this.setData({
      submitFlag: true
    })
  },
  submitComment() {
    let that = this
    if (this.data.commentTxt.trim().length <= 0) {
      wx.showToast({
        title: '请输入评论内容',
        icon: 'none'
      })
      return;
    }
    let data = {
      planId: this.data.planId,
      comment: this.data.commentTxt,
      type: 1,
      isShowName: 0,
      userId: wx.getStorageSync('userId')
    }
    this.data.cmtPid == '' ? "" : data.pid = this.data.cmtPid
    let url = '/comment/plan/add'
    fetch(url, 'post', data, 'local')
      .then((res) => {
        if (res.status) {
          console.log("999999")

          wx.showToast({
            title: '评论成功',
            icon: 'none',
            duration: 2000
          })
          this.setData({
            textFocus: false,
            cmtPname: '',
            cmtPid: '',
            submitFlag: false,
            placeholderTxt: '说点什么吧...',
            commentTxt: ''
          })
          setTimeout(function () {
            that.getComment();
          }, 2000);
        } else {
          wx.showToast({
            title: '评论失败',
            icon: 'none',
            duration: 2000
          })
          this.setData({
            submitFlag: false,
          })
        }
      })
  },
  changeCmtSort(e) {
    let sort = e.currentTarget.dataset.sort;
    this.setData({
      sort: sort
    })
    this.getComment();
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
    let url = '/comment/changePraiseState?userId=' + wx.getStorageSync('userId') + '&commentId=' + id + '&type=' + status

    fetch(url, 'put', '', 'local')
      .then((res) => {
        if (res.message == '成功!') {
          console.log("pp")
          let list = this.data.commentList;
          list[index].praiseStatus = status;
          status == 1 ? list[index].praiseCount += 1 : list[index].praiseCount -= 1
          that.setData({
            commentList: list
          })
          console.log(that.data.commentList, '99')
        }
      })
  },
  deleteCmt(e) {
    let that = this;
    let id = e.currentTarget.dataset.id;
    let url = '/comment/delete?id=' + id

    fetch(url, 'delete', '', 'local')
      .then((res) => {
        if (res.status) {
          wx.showToast({
            title: '删除成功',
            icon: 'none',
            duration: 2000
          })
          setTimeout(function () {
            that.getComment();
          }, 2000);
        } else {
          wx.showToast({
            title: '删除失败',
            icon: 'none',
            duration: 2000
          })
        }
      })
  },
  answerCmt(e) {
    let cmtPid = e.currentTarget.dataset.id,
      cmtPname = e.currentTarget.dataset.name;
    let txt = '回复 ' + cmtPname + ':'
    this.setData({
      textFocus: true,
      cmtPname: cmtPname,
      cmtPid: cmtPid,
      placeholderTxt: txt
    })
  },
  textBlur() {
    this.setData({
      submitFlag: this.data.commentTxt.length <= 0 ? false : true
    })
    if (this.data.commentTxt == '') {
      this.setData({
        textFocus: false,
        cmtPname: '',
        cmtPid: '',
        placeholderTxt: '说点什么吧...',

      })
    } else {
      return
    }
  },
  scrollView() {
    this.setData({
      scrollId: 'comment'
    })
  },
  collectCase(e) { // 方案收藏
    var that = this
    if (that.data.collectRequest == true) {
      that.setData({
        collectRequest: false
      })
      let url = `/designplanfavorite/setFavoriteOrUnfavorite`,

        status = null,
        title = '收藏'
      console.log("987", this.data.caseDetails)
      if (this.data.caseDetails.isFavorite) {
        status = 0
      } else {
        status = 1
      }
      status == 0 ? title = '取消收藏' : '收藏'
      fetch(url, 'post', {
        status: status,
        recommendId: this.data.caseDetails.designPlanRecommendId,
        designPlanType: this.data.caseDetails.spaceType == 13 ? 2 : 1
      })
        .then((res) => {

          if (res.success) {
            let detail = this.data.caseDetails
            detail.isFavorite = status
            if (detail.collectNum < 10000) {
              detail.collectNumStr == null ? detail.collectNumStr = 0 : ''
              detail.collectNumStr = parseInt(detail.collectNumStr)
              console.log(detail.collectNumStr)
              status == 0 ? detail.collectNumStr -= 1 : detail.collectNumStr += 1
            }
            this.setData({
              caseDetails: detail
            })
            wx.showToast({
              title: title + '成功'
            })
            setTimeout(function () {
              that.setData({
                collectRequest: true
              })
            }, 500)

          } else {
            wx.showToast({
              title: '收藏失败',
              icon: 'none'
            })
            setTimeout(function () {
              that.setData({
                collectRequest: true
              })
            }, 500)
          }
        })
    } else {
      return
    }

  },
  likeCase(e) { // 方案点赞
    var that = this;
    if (that.data.favoriteRequest == true) {
      that.setData({
        favoriteRequest: false
      })
      let url = `/designPlanLike/setLikeOrDislike`,
        status = null,
        title = '点赞'
      if (this.data.caseDetails.isLike) {
        status = 0
      } else {
        status = 1
      }
      status == 0 ? title = '取消点赞' : '点赞'
      fetch(url, 'post', {
        status: status,
        designId: this.data.caseDetails.designPlanRecommendId,
        designPlanType: this.data.caseDetails.spaceType == 13 ? 2 : 1
      })
        .then((res) => {
          if (res.success) {
            let detail = this.data.caseDetails
            detail.isLike = status
            if (detail.likeNum < 10000) {
              detail.likeNumStr == null ? detail.likeNumStr = 0 : ''
              detail.likeNumStr = parseInt(detail.likeNumStr)
              status == 0 ? detail.likeNumStr -= 1 : detail.likeNumStr += 1
            }

            this.setData({
              caseDetails: detail
            })
            wx.showToast({
              title: title + '成功'
            })
            setTimeout(function () {
              that.setData({
                favoriteRequest: true
              })
            }, 500)
          } else {
            wx.showToast({
              title: title + '失败',
              icon: 'none'
            })
            setTimeout(function () {
              that.setData({
                favoriteRequest: true
              })
            }, 500)
          }
        })
    } else {
      return;
    }
  }
})