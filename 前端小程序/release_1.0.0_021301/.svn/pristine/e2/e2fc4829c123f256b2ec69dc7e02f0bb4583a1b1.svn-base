import utils from '../../utils/utils.js'
let API = getApp().API,
  $APP = getApp()
Page({

  /**
   * 页面的初始数据
   */
  data: {
    staticImageUrl: getApp().staticImageUrl,
    resourcePath: getApp().resourcePath,
    chatContentList: [],
    // scrollId: '',
    chatContent: '',
    inputFocus: true,
    friendUuid: '',
    socket: '',
    myFriendMessageObj: {},
    toUserShopId: '',
    shopDetails: {}, // 店铺详情
    userChatMessage: '',
    top: 999999,
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function(options) {
    this.initLoad(options) // 初始化
    this.monitorMessage() // 监听实时消息      
  },
  initLoad(options) { // 初始化
    options.item = options.item.replace(/(^\s+)|(\s+$)/g, "")
    let item = JSON.parse(options.item.replace(/&/g, "$"))
    this.initializeChatBox(item) // 初始化聊天框信息  
    this.getShopDetails(item) // 获取店铺详情
    this.judgeHaveShop(item) // 校测当前聊天用户是否有店铺
    this.getChatingRecords() // 获取聊天记录
    this.refreshMessageRead() // 刷新消息为已读
  },
  initializeChatBox(item) { // 初始化聊天框信息
    this.data.friendUuid = item.contactSessionId || item.sessionId
    this.data.toUserShopId = item.id || item.relatedObjId
    wx.setNavigationBarTitle({
      title: item.contactName || item.shopName
    })
    this.setData({
      myFriendMessageObj: item,
    })
  },
  getShopDetails(item) { // 获取店铺详情
    API.getCompanyDetails({
      shopId: item.id || item.relatedObjId,
      platformValue: 2
    }).then(res => {
      if (res.code === 200 && res.data) {
        this.setData({
          shopDetails: res.data
        })
      }
    })
  },
  judgeHaveShop(item) { // 校测当前聊天用户是否有店铺
    API.judgeHaveShop({
      contactSessionId: item.sessionId,
    }).then(res => {
      this.setData({
        isHaveShop: true
      });
    })
  },
  refreshMessageRead() { // 重置消息未读
    API.refreshMessageRead({
        userSessionId: wx.getStorageSync('uuid') || '',
        contactSessionId: this.data.friendUuid,
        relatedObjId: this.data.toUserShopId,
        relatedObjType: 1
      })
      .then(res => {
        console.log(res, '成功刷新')
      })
  },
  getChatingRecords() { // 获取聊天记录
    let _this = this;
    API.getChatingRecords({
        userSessionId: wx.getStorageSync('uuid') || '',
        contactSessionId: this.data.friendUuid,
        relatedObjId: this.data.toUserShopId,
        relatedObjType: 1,
        pageNum: 1,
        pageSize: 10000,
        platformCode: "sxw"
      })
      .then(res => {
        console.log('获取聊天记录List', res)
        if (res.resultCode === 'SUCCESS') {
          let list = res.data.list ? res.data.list.reverse() : [];
          _this.setData({
            chatContentList: list,
            top: 99999,
            isShowPage: true
          })
        }
      })
  },
  monitorMessage() {
    const $self = this
    getApp().mySocket.emit('im_loc_msg_event', {
      userSessionId: wx.getStorageSync('uuid'),
      loc: 1,
      contactSessionId: this.data.friendUuid,
      appId: 16,
      relatedObjType: 1,
      relatedObjId: this.data.toUserShopId
    })
    getApp().mySocket.on('im_chat_msg_event', content => {
      console.log('接收')
      let key = 'chatContentList[' + $self.data.chatContentList.length + ']'
      $self.setData({
        [key]: {
          msgBody: content.msgBody,
          obj: content.obj,
          time: utils.getNowTime("--"),
          direction: 2,
          msgType: content.msgType
        },
      }, () => {
        $self.setData({
          top: 99999
        })
      })
    })
  },
  routerToUserDetails() {
    // this.setData({
    //   scrollId: 'scrollId'
    // })
  },
  sendUserMessage(e) { // 发送消息
    if (this.sendDate && Date.parse(new Date()) - this.sendDate < 1000) return;
    this.sendDate = Date.parse(new Date())

    if (this.data.userChatMessage.trim()) {
      let key = 'chatContentList[' + this.data.chatContentList.length + ']';
      this.setData({
        [key]: {
          msgBody: this.data.userChatMessage,
          time: utils.getNowTime("--"),
          direction: 1,
          msgType: 0
        },
      }, () => {
        this.setData({
          top: 99999,
          chatContent: '',
          userChatMessage: '',
          isShowSendBtn: false
        })
      })
      getApp().mySocket.emit('im_chat_msg_event', {
        fromUserSessionId: wx.getStorageSync('uuid'),
        toUserSessionId: this.data.friendUuid,
        msgBody: this.data.userChatMessage,
        relatedObjId: this.data.toUserShopId,
        relatedObjType: 1,
        fromAppId: 16,
        relatedObjOwnerSessionId: this.data.friendUuid,
        msgType: 0
      })
    }
  },
  changeUserChatMessage(e) { // 设置聊天内容
    this.setData({
      isShowSendBtn: e.detail.value.trim().length > 0,
      userChatMessage: e.detail.value
    })
  },
  // 输入框焦点丢失
  blur() {
    if (!this.data.userChatMessage || this.data.userChatMessage.trim().length == 0) {
      this.setData({
        isShowSendBtn: false
      })
    }
  },
  openUserWindow() { // 打开聊天下面的操作栏
    this.setData({
      isShowWindow: !this.data.isShowWindow,
      top: 999999,
    })
  },
  openAlbum() { // 打开相册
    let _this = this;
    wx.chooseImage({
      count: 9,
      sizeType: ['original', 'compressed'],
      sourceType: ['album'],
      success(res) {
        if (res.tempFiles.length > 10) {
          wx.showToast({
            title: '图片不得超过10张',
          })
          return;
        }
        // console.log(wx.getFileSystemManager().readFileSync(res.tempFilePaths[0]))

        for (let i = 0; i < res.tempFilePaths.length; i++) {
          API.uploadMsgImg({
              'path': res.tempFilePaths[i],
              'platform': 'mini',
              'module': 'demand',
              'type': "image"
            })
            .then(res => {
              if (res.resultCode == "SUCCESS") {
                getApp().mySocket.emit('im_chat_msg_event', {
                  fromUserSessionId: wx.getStorageSync('uuid'),
                  toUserSessionId: _this.data.friendUuid,
                  msgBody: res.data.picPath,
                  relatedObjId: _this.data.toUserShopId,
                  relatedObjType: 1,
                  fromAppId: 16,
                  relatedObjOwnerSessionId: _this.data.friendUuid,
                  msgType: 1
                })
                let key = 'chatContentList[' + _this.data.chatContentList.length + ']'
                _this.setData({
                  [key]: {
                    msgBody: res.data.picPath,
                    height: res.data.height,
                    width: res.data.width,
                    time: utils.getNowTime("--"),
                    direction: 1,
                    msgType: 1
                  },
                }, () => {
                  _this.setData({
                    top: 99999,
                  })
                })
              } else {
                wx.showToast({
                  title: '上传失败',
                  icon: 'none'
                });
              }
            })
        }
      }
    })
  },
  toShopDetail() { // 跳转到我的店铺
    wx.navigateTo({
      url: '/pages/decoration/companyDetail/companyDetail?id=' + this.data.shopDetails.id,
    })
  },
  imgLoad(e) {
    this.scalingImg(e.detail);
    let width = 'chatContentList[' + e.currentTarget.dataset.index + '].width',
      height = 'chatContentList[' + e.currentTarget.dataset.index + '].height',
      show = 'chatContentList[' + e.currentTarget.dataset.index + '].show';
    this.setData({
      [width]: e.detail.width,
      [height]: e.detail.height,
      [show]: true,
    })
  },
  showImg(e) {
    wx.previewImage({
      urls: [e.target.dataset.src]
    });
  },
  scalingImg(obj) {
    if (obj.width >= 440 || obj.height >= 440) {
      obj.width = obj.width / 2
      obj.height = obj.height / 2
      this.scalingImg(obj)
    } else if (obj.width < 150 && obj.height < 150) {
      obj.width = obj.width * 2
      obj.height = obj.height * 2
      this.scalingImg(obj)
    } else {
      return obj
    }
  },
  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function() {},

  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function() {
    this.getSelectData()
  },
  // 获取选中的数据
  getSelectData() {
    let $self = this;
    let key = 'chatContentList[' + $self.data.chatContentList.length + ']';
    if (this.data.addHouseId) {
      $self.setData({
        [key]: {
          obj: {
            id: this.data.addHouseId,
            thumbnailPath: this.data.addHouseImg,
            houseCommonCode: this.data.addHouseTitle,
          },
          time: utils.getNowTime("--"),
          direction: 1,
          msgType: 4
        },
      }, () => {
        $self.setData({
          top: 99999,
        })
      })

      getApp().mySocket.emit('im_chat_msg_event', {
        fromUserSessionId: wx.getStorageSync('uuid'),
        toUserSessionId: this.data.friendUuid,
        msgBody: this.data.addHouseId,
        relatedObjId: this.data.toUserShopId,
        relatedObjType: 1,
        fromAppId: 16,
        relatedObjOwnerSessionId: this.data.friendUuid,
        msgType: 4
      })
    }
    if (this.data.addPlanId) {
      console.log(this.data.addPlanItem);
      $self.setData({
        [key]: {
          obj: Object.assign(this.data.addPlanItem, {
            // planPicPath: this.data.addPlanImg,
            // designName: this.data.addPlanTitle,
            coverPath: this.data.addPlanImg,
            planName: this.data.addPlanTitle,
          }),
          time: utils.getNowTime("--"),
          direction: 1,
          msgType: this.data.addPlanItem.planHouseType == 1 ? 2 : 3
        },
      }, () => {
        $self.setData({
          top: 99999,
        })
      })

      getApp().mySocket.emit('im_chat_msg_event', {
        fromUserSessionId: wx.getStorageSync('uuid'),
        toUserSessionId: this.data.friendUuid,
        msgBody: this.data.addPlanItem.planHouseType == 1 ? this.data.addPlanItem.businessId : this.data.addPlanItem.newFullHousePlanId,
        relatedObjId: this.data.toUserShopId,
        relatedObjType: 1,
        fromAppId: 16,
        relatedObjOwnerSessionId: this.data.friendUuid,
        msgType: this.data.addPlanItem.planHouseType == 1 ? 2 : 3
      })
    }
    this.setData({
      addHouseId: null,
      addHouseImg: null,
      addHouseTitle: null,
      addPlanId: null,
      addPlanImg: null,
      addPlanTitle: null,
      addPlanItem: null,
      planType: null
    });
  },
  toUrl(e) {
    if (e.currentTarget.dataset.url) {
      wx.navigateTo(e.currentTarget.dataset);
    }
  },
  toHouseDetail(e) {
    let item = e.currentTarget.dataset.item.obj;
    API.copyMsgFullHouseExist({
        historyMsgId: e.currentTarget.dataset.id
      })
      .then(res => {
        console.log(res)
      });
    wx.navigateTo({
      url: '/pages/house-type/house-details/house-details?type=myPlan&houseId=' + item.houseId + '&templateId=' + item.templateId + '&fullHousePlanId=' + item.id + '&mainTaskId=' + item.mainTaskId
    })
    this.sellingPoint('continue-finish')
  },
  /*跳转720*/

  toThreeD(e) {
    let item = e.currentTarget.dataset.item;
    let obj = e.currentTarget.dataset.item.obj;
    let mainTaskId;
    if (item.direction == 2 && item.msgType == 3) {
      API.copyMsgFullHouseExist({
          historyMsgId: e.currentTarget.dataset.obj.id
        })
        .then(res => {
          if (res.obj.copyFlag == 1) {
            API.copyPlan({
                fullHouseDesignPlanId: obj.fullHouseId ? obj.fullHouseId : obj.businessId,
                mainTaskId: obj.mainTaskId,
                supplyDemandId: this.data.id,
                msgId: e.currentTarget.dataset.obj.id
              })
              .then(res => {
                let list = res.obj.split(',');
                obj.id = list[0];
                mainTaskId = list[1]

                let routerQueryType = 'seven',
                  webUrl = null,
                  sevenObj = null
                obj.fullHousePlanUUID ? (webUrl = $APP.wholeHouse3dUrl, sevenObj = {
                    uuid: obj.fullHousePlanUUID,
                    token: wx.getStorageSync('token'),
                    platformCode: 'brand2c',
                    operationSource: 0,
                    planId: obj.id,
                    routerQueryType: 'seven',
                    customReferer: $APP.wxUrl,
                    platformNewCode: 'miniProgram',
                    isTask: 1,
                    isRender: 0,
                    houseId: obj.houseId || 0,
                    taskType: 3,
                    sdId: this.data.id,
                    rc: 1,
                    mainTaskId: mainTaskId
                  }) :
                  (webUrl = $APP.sevenUrl, sevenObj = {
                    token: wx.getStorageSync('token'),
                    platformCode: 'brand2c',
                    operationSource: 0,
                    planId: obj.planRecommendedId || obj.businessId,
                    routerQueryType: routerQueryType,
                    customReferer: $APP.wxUrl,
                    platformNewCode: 'miniProgram',
                    isTask: 1,
                    taskType: 1,
                    houseId: obj.houseId || 0,
                    sdId: this.data.id,
                    rc: 0,
                    mainTaskId: obj.mainTaskId
                  })
                for (let key in sevenObj) {
                  webUrl += key + '=' + sevenObj[key] + '&'
                }

                getApp().data.webUrl = webUrl;
                wx.navigateTo({
                  url: '/pages/web-720/web-720'
                });
              })
          } else {
            obj.id = res.obj.fullHouseId;
            mainTaskId = res.obj.mainTaskId;

            let routerQueryType = 'seven',
              webUrl = null,
              sevenObj = null
            obj.fullHousePlanUUID ? (webUrl = $APP.wholeHouse3dUrl, sevenObj = {
                uuid: obj.fullHousePlanUUID,
                token: wx.getStorageSync('token'),
                platformCode: 'brand2c',
                operationSource: 0,
                planId: obj.id,
                routerQueryType: 'seven',
                customReferer: $APP.wxUrl,
                platformNewCode: 'miniProgram',
                isTask: 1,
                isRender: 0,
                houseId: obj.houseId || 0,
                taskType: 3,
                sdId: this.data.id,
                rc: 1,
                mainTaskId: mainTaskId
              }) :
              (webUrl = $APP.sevenUrl, sevenObj = {
                token: wx.getStorageSync('token'),
                platformCode: 'brand2c',
                operationSource: 0,
                planId: obj.planRecommendedId || obj.businessId,
                routerQueryType: routerQueryType,
                customReferer: $APP.wxUrl,
                platformNewCode: 'miniProgram',
                isTask: 1,
                taskType: 1,
                houseId: obj.houseId || 0,
                sdId: this.data.id,
                rc: 0,
                mainTaskId: obj.mainTaskId
              })
            for (let key in sevenObj) {
              webUrl += key + '=' + sevenObj[key] + '&'
            }

            getApp().data.webUrl = webUrl;
            wx.navigateTo({
              url: '/pages/web-720/web-720'
            });
          }
        })
    } else {
      obj.id = obj.businessId || obj.fullHouseId || obj.planRecommendedId;
      mainTaskId = obj.mainTaskId;

      let routerQueryType = 'seven',
        webUrl = null,
        sevenObj = null
      obj.fullHousePlanUUID ? (webUrl = $APP.wholeHouse3dUrl, sevenObj = {
          uuid: obj.fullHousePlanUUID,
          token: wx.getStorageSync('token'),
          platformCode: 'brand2c',
          operationSource: 0,
          planId: obj.id,
          routerQueryType: 'seven',
          customReferer: $APP.wxUrl,
          platformNewCode: 'miniProgram',
          isTask: 1,
          isRender: 0,
          houseId: obj.houseId || 0,
          taskType: 3,
          sdId: this.data.id || 0,
          rc: 1,
          mainTaskId: mainTaskId,
        }) :
        (webUrl = $APP.sevenUrl, sevenObj = {
          token: wx.getStorageSync('token'),
          platformCode: 'brand2c',
          operationSource: 0,
          planId: obj.planRecommendedId || obj.businessId,
          routerQueryType: routerQueryType,
          customReferer: $APP.wxUrl,
          platformNewCode: 'miniProgram',
          isTask: 1,
          taskType: 1,
          houseId: obj.houseId || 0,
          sdId: this.data.id || 0,
          rc: 0,
          mainTaskId: mainTaskId,
        })
      for (let key in sevenObj) {
        webUrl += key + '=' + sevenObj[key] + '&'
      }

      getApp().data.webUrl = webUrl;
      wx.navigateTo({
        url: '/pages/web-720/web-720'
      });
    }

  },
  /**
   * 生命周期函数--监听页面隐藏
   */
  onHide: function() {},

  /**
   * 生命周期函数--监听页面卸载
   */
  onUnload: function() {
    // socket.disconnect() // 断开连接  
  },

  /**
   * 页面相关事件处理函数--监听用户下拉动作
   */
  onPullDownRefresh: function() {
    console.log(1)
  },

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom: function() {

  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function() {

  }
})