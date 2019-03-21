// pages/house-type/house-detail/house-detail.js
import regeneratorRuntime from '../../../utils/runtime.js';

const API = getApp().API;
let $App = getApp();
// 下拉条件方向图标
const conditionDownIcon = 'page_icon_down.png';
const conditionUpIcon = 'page_icon_up.png';

Page({

  /**
   * 页面的初始数据
   */
  data: {
    resourcePath: getApp().resourcePath,
    pageParams: {},
    selectSpaceName: '获取中...',
    selectTemplateId: '',
    wholeHousePicData: '',
    currentSpaceIndex: '',
    showWholeHouseBtn: true,
    wholeHouseSelectIcon: {
      active: false,
      icons: ['huxing_icon_all.png', 'home_msg_all.png']
    },
    showLargePic: false,
    recomType: [
      {
        name: '推荐方案',
        active: true
      },
      {
        name: '我的收藏',
        active: false
      }
    ],
    recomConditionTab: [
      {
        key: 'style',
        label: '装修风格'
      },
      {
        key: 'mode',
        label: '装修方式'

      },
      {
        key: 'price',
        label: '装修价格'
      }
    ].map(item => Object.assign(item, {
      active: false,
      directionIcon: [conditionDownIcon, conditionUpIcon]
    })),
    showWhichKey: '',
    showConditionContent: false,
    allSpaceAndStyleList: [],
    currentSpaceInStyleList: [],
    allModeAndPriceData: [],
    modeList: [],
    currentPriceList: [{
      name: '全部',
      value: '',
      active: true
    }],
    planList: [],
    showMask: false,
    showPutInLoading: false,
    showReplaceConfirm: false,
    showSelectAreaConfirm: false,
    showGuide: false,
    selectReplaceType: 2,
    selectAreaInfo: '',
    fullHousePlanId: '',
    mainTaskId: '',
    isOnShow: 0,
    pageSize:10,
    salcePicMultiple: 1,
    salcePicMultipleTemp: 1,
    isFingerScale: false
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
      new $App.newNav() // 注册快速导航组件
    this.setData({ pageParams: options });
  },

  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function () {

  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow: async function () {
    if (this.data.isOnShow === 1) {
      this.setData({
        ['pageParams.mainTaskId']: this.data.mainTaskId,
        ['pageParams.fullHousePlanId']: this.data.fullHousePlanId,
        ['pageParams.type']: 'myPlan'
      })
    }
    let handleList = {
      search: this.searchEntryHandle,
      plan: this.planEntryHandle,
      myPlan: this.myPlanEntryHandle
    }[this.data.pageParams.type]();
    let { obj } = await API.gethouseDetailsTypeList();
    this.setData({ allSpaceAndStyleList: obj })
    await this.queryWholeHousePic();
    await this.queryModeAndPrice();
    this.querySpaceAndStyle();
    this.coditionQuery();
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
    wx.removeStorageSync('taskResIdObj')
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
    this.setData({ pageSize: this.data.pageSize + 5 });
    this.coditionQuery();
  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function () {

  },
  catchTouchMove() { },
  /**
   * 业务逻辑
   */

  // 全屋图片及坐标
  async queryWholeHousePic() {
    let planInfo = wx.getStorageSync('caseItem');
    let firstNotRerderIndex;
    let selectedSpaceType;
    let selectedSpace;

    let { obj } = await API.queryWholeHousePic({ houseId: this.data.pageParams.houseId, fullHousePlanId: this.data.pageParams.fullHousePlanId || '' });

    if (this.data.pageParams.type === 'plan' && planInfo.spaceType === 13) {
      obj.guideInfoList.forEach(item => Object.assign(item, { active: true }))
      this.data.wholeHouseSelectIcon.icons = ['home_msg_all.png', 'huxing_icon_all.png'];
    }
    else {
      firstNotRerderIndex = obj.guideInfoList.findIndex(item => !item.renderStatus);
      selectedSpaceType = obj.guideInfoList.length && obj.guideInfoList[firstNotRerderIndex > -1 ? firstNotRerderIndex : 0];
      selectedSpace = this.data.allSpaceAndStyleList.filter(item => item.houseType === selectedSpaceType['spaceType']);
      obj.guideInfoList.length && obj.guideInfoList.forEach((item, index) => {
        if (firstNotRerderIndex > -1) {
          Object.assign(item, { active: index === firstNotRerderIndex });
          return;
        }
        Object.assign(item, { active: !index })
      });
    }

    let currentReplaceList = selectedSpaceType['renderStatus'] == 1 && obj.taskStates ? obj.taskStates.filter(item => item.templateId === selectedSpaceType['designTempletId']) : [];

    this.setData(this.data.pageParams.type === 'plan' && planInfo.spaceType === 13 ? {
      selectTemplateId: '',
      // pageParams: Object.assign(this.data.pageParams, { templateId: '' }),
      currentSpaceIndex: 0,
      selectSpaceName: '全屋',
      showWholeHouseBtn: !obj.guideInfoList.some(item => item.renderStatus),
      wholeHousePicData: obj,
      wholeHouseSelectIcon: this.data.wholeHouseSelectIcon
    } : {
        selectTemplateId: selectedSpaceType['designTempletId'],
        pageParams: Object.assign(this.data.pageParams, currentReplaceList.length ? {
          preRenderSceneId: currentReplaceList[0]['businessId']
        } : { preRenderSceneId: this.data.pageParams.preRenderSceneId ? this.data.pageParams.preRenderSceneId : ''}),
        currentSpaceIndex: firstNotRerderIndex > -1 ? firstNotRerderIndex : 0,
        selectSpaceName: selectedSpace.length ? selectedSpace[0]['houseName'] : '暂无',
        showWholeHouseBtn: !obj.guideInfoList.some(item => item.renderStatus),
        wholeHousePicData: obj
      });
  },
  // 全屋按钮事件
  selectWholeHouse(e) {
    let isSelected = this.data.wholeHouseSelectIcon.active;
    let isLarge = !!Number(e.currentTarget.dataset.large);
    let selectedSpaceType = this.data.wholeHousePicData.guideInfoList[this.data.currentSpaceIndex];
    let selectedSpace = this.data.allSpaceAndStyleList.filter(item => item.houseType === selectedSpaceType['spaceType']);
    this.data.wholeHousePicData.guideInfoList.forEach((item, index) => {
      if (isSelected) {
        Object.assign(item, { active: index === this.data.currentSpaceIndex });
        return;
      }
      Object.assign(item, { active: true })
    });

    this.setData({
      selectTemplateId: !isSelected ? '' : selectedSpaceType['designTempletId'],
      // pageParams: Object.assign(this.data.pageParams, { templateId: !isSelected ? '' : selectedSpaceType['designTempletId'] }),
      selectSpaceName: isSelected ? selectedSpace.length ? selectedSpace[0]['houseName'] : '暂无' : '全屋',
      wholeHouseSelectIcon: { active: !this.data.wholeHouseSelectIcon.active, icons: this.data.wholeHouseSelectIcon.icons.reverse() },
      wholeHousePicData: this.data.wholeHousePicData,
      salcePicMultiple: 1
    });

    isLarge && this.showAndCloseMask();

    this.coditionQuery();
  },
  // 选中空间事件
  selectSpace(e) {
    let index = e.currentTarget.dataset.index;
    let isLarge = !!Number(e.currentTarget.dataset.large);
    let currentData = this.data.wholeHousePicData.guideInfoList[index];
    let [selectedSpace] = this.data.allSpaceAndStyleList.filter(item => item.houseType === currentData.spaceType);
    this.data.wholeHousePicData.guideInfoList.forEach((item, i) => { Object.assign(item, { active: index === i }) });

    let styleList = [];
    if (!selectedSpace) {
      styleList = [{ styleCode: '', styleName: '不限', active: true }]
    }
    else {
      styleList = selectedSpace.designPlanStyleVoList;
      styleList.findIndex(item => !item.styleCode) < 0 && styleList.unshift({ styleCode: '', styleName: '不限', active: true });
    }

    let allModeList = this.data.allModeAndPriceData.map(item => Object.assign({}, { name: item.name, value: item.value, valueKey: item.valueKey, active: false }));
    allModeList.unshift({ name: '全部', value: '', active: true });

    let currentReplaceList = this.data.wholeHousePicData.taskStates && this.data.wholeHousePicData.taskStates.filter(item => item.templateId === currentData['designTempletId'])[0];

    this.setData({
      currentSpaceIndex: index,
      selectTemplateId: currentData['designTempletId'],
      pageParams: Object.assign(this.data.pageParams, {
        preRenderSceneId: currentReplaceList ? currentReplaceList['businessId'] : ''
      }),
      selectSpaceName: selectedSpace ? selectedSpace.houseName : '暂无',
      wholeHousePicData: this.data.wholeHousePicData,
      wholeHouseSelectIcon: { active: false, icons: ['huxing_icon_all.png', 'home_msg_all.png'] },
      currentSpaceInStyleList: styleList.map((item, index) => Object.assign(item, { active: !index })),
      modeList: allModeList,
      currentPriceList: [{
        name: '全部',
        value: '',
        active: true
      }],
      showConditionContent: false,
      recomConditionTab: this.data.recomConditionTab.map(item => Object.assign(item, { active: false, directionIcon: [conditionDownIcon, conditionUpIcon] }))
    });

    isLarge && this.showAndCloseMask();

    this.coditionQuery();
  },
  // 切换推荐或者收藏
  switchType(e) {
    let currentAreaData = this.data.wholeHousePicData.guideInfoList[this.data.currentSpaceIndex];
    let styleList = (this.data.allSpaceAndStyleList.filter(item => item.houseType === currentAreaData && currentAreaData.spaceType || 3))[0]['designPlanStyleVoList'];
    styleList.findIndex(item => !item.styleCode) < 0 && styleList.unshift({ styleCode: '', styleName: '不限', active: true });

    let allModeList = this.data.allModeAndPriceData.map(item => Object.assign({}, { name: item.name, value: item.value, valueKey: item.valueKey, active: false }));
    allModeList.unshift({ name: '全部', value: '', active: true });

    this.setData({
      pageSize: 10,
      showConditionContent: false,
      recomType: this.data.recomType.map((item, index) => Object.assign(item, { active: index === e.currentTarget.dataset.index })),
      recomConditionTab: this.data.recomConditionTab.map(item => Object.assign(item, { active: false, directionIcon: [conditionDownIcon, conditionUpIcon] })),
      currentSpaceInStyleList: styleList.map((item, index) => Object.assign(item, { active: !index })),
      modeList: allModeList,
      currentPriceList: [{
        name: '全部',
        value: '',
        active: true
      }]
    });

    this.coditionQuery();
  },
  // 显示条件筛选
  showCoditionPannel(e) {
    let index = e.currentTarget.dataset.index;
    this.setData({
      recomConditionTab: this.data.recomConditionTab.map((item, i) => {
        let isSelected = i === index;
        return Object.assign(item, {
          active: isSelected && !item.active,
          directionIcon: (item.active || isSelected) ? item.directionIcon.reverse() : item.directionIcon
        })
      })
    });

    this.setData({
      showWhichKey: this.data.recomConditionTab[index].active ? this.data.recomConditionTab[index].key : '',
      showConditionContent: !this.data.showConditionContent && this.data.recomConditionTab.some(item => item.active)
    })
  },
  // 关闭筛选条件pannel回调
  closePannel(e) {
    if (!this.data.showConditionContent) {
      this.setData({
        showConditionContent: this.data.recomConditionTab.some(item => item.active)
      })
    }
  },
  // 查询空间及类型
  async querySpaceAndStyle() {
    let currentAreaData = this.data.wholeHousePicData.guideInfoList[this.data.currentSpaceIndex];
    let styleList = (this.data.allSpaceAndStyleList.filter(item => item.houseType === currentAreaData && currentAreaData.spaceType || 3))[0]['designPlanStyleVoList'];
    styleList.unshift({ styleCode: '', styleName: '不限', active: true });

    this.setData({
      currentSpaceInStyleList: styleList
    });
  },
  // 查询装修方式和价格
  async queryModeAndPrice() {
    let { list } = await API.getFitmentQuote();
    let allModeList = list.map(item => Object.assign({}, { name: item.name, value: item.value, valueKey: item.valueKey, active: false }));
    allModeList.unshift({ name: '全部', value: '', active: true });
    this.setData({
      allModeAndPriceData: list,
      modeList: allModeList
    });
  },
  // 选择筛选条件查询
  selectConditionItem(e) {
    let index = e.currentTarget.dataset.index;
    let tabIndex = this.data.recomConditionTab.findIndex(item => item.active);
    let data = [this.data.currentSpaceInStyleList, this.data.modeList, this.data.currentPriceList][tabIndex];

    data.forEach((item, i) => Object.assign(item, { active: index === i }));

    if (tabIndex === 1) {
      let priceList = index ? [{ name: '全部', value: '', active: true }].concat(this.data.allModeAndPriceData[index - 1]['sonList'].map((item, i) => ({ name: item.name, value: item.value, active: false }))) : [{ name: '全部', value: '', active: true }];
      this.data.currentPriceList = priceList;
    }

    this.setData({
      pageSize: 10,
      showConditionContent: false,
      recomConditionTab: this.data.recomConditionTab.map(item => Object.assign(item, { active: false, directionIcon: [conditionDownIcon, conditionUpIcon] })),
      currentSpaceInStyleList: this.data.currentSpaceInStyleList,
      modeList: this.data.modeList,
      currentPriceList: this.data.currentPriceList
    });

    this.coditionQuery();
  },
  // 根据当前选中推荐方案和收藏选择
  coditionQuery() {
    let index = this.data.recomType.findIndex(item => item.active);
    [this.queryRecommandPlan, this.queryCollectPlan][index]();
  },
  // 查询推荐方案
  async queryRecommandPlan() {
    let selectedSpace = this.data.wholeHousePicData.guideInfoList[this.data.currentSpaceIndex];
    let isFullHouse = this.data.wholeHousePicData.guideInfoList.every(item => item.active);
    let [currentStyle] = this.data.currentSpaceInStyleList.filter(item => item.active);
    let [modeType] = this.data.modeList.filter(item => item.active);
    let [priceRange] = this.data.currentPriceList.filter(item => item.active);

    let { obj } = await API.getRecommendCaseList({
      curPage: 1,
      pageSize: this.data.pageSize,
      spaceType: isFullHouse ? 13 : selectedSpace.spaceType,
      designPlanStyleId: currentStyle && currentStyle.styleCode,
      spaceArea: isFullHouse ? '' : selectedSpace && selectedSpace.spaceArea,
      displayType: 'dragDecorate',
      decoratePriceType: modeType && modeType.value,
      decoratePriceRange: priceRange.value,
      designRecommendedStyleName: currentStyle && currentStyle.styleCode && currentStyle.styleName
    });

    this.setData({ planList: obj });
  },
  // 查询收藏方案
  async queryCollectPlan() {
    let selectedSpace = this.data.wholeHousePicData.guideInfoList[this.data.currentSpaceIndex];
    let [currentStyle] = this.data.currentSpaceInStyleList.filter(item => item.active);
    let isFullHouse = this.data.wholeHousePicData.guideInfoList.every(item => item.active);
    let { obj } = await API.getDesignplanfavorite({
      curPage: 1,
      pageSize: this.data.pageSize,
      spaceType: isFullHouse ? 13 : selectedSpace.spaceType,
      designPlanStyleId: currentStyle && currentStyle.styleCode,
      spaceArea: isFullHouse ? '' : selectedSpace && selectedSpace.spaceArea,
      displayType: 'decorate',
      isSortByReleaseTime: 0,
      platformCode: 'selectDecoration',
      designRecommendedStyleName: currentStyle && currentStyle.styleCode && currentStyle.styleName
    });

    this.setData({ planList: obj });
  },
  // 显示隐藏放大方案图选择空间
  showAndCloseMask() {
    this.setData({
      showLargePic: !this.data.showLargePic
    });
  },
  searchEntryHandle() {
    console.log('search', this.data.pageParams)
  },
  planEntryHandle() {
    switch (this.data.pageParams.matchState) {
      case 3:
      case '3':
        this.putInHouse();
        break;
      case 4:
      case '4':
        setTimeout(() => {
          this.setData({
            showMask: true,
            showReplaceConfirm: true
          });
        }, 250);
        break;
      case 5:
      case '5':
        if (!this.data.pageParams.mainTaskId) {
          let selectAreaInfo = wx.getStorageSync('matchData')['houseGuidePicInfo'];
          selectAreaInfo.guideInfoList.forEach((item, index) => {
            Object.assign(item, { active: !index });
          })
          setTimeout(() => {
            this.setData({
              showMask: true,
              showSelectAreaConfirm: true,
              selectAreaInfo: selectAreaInfo
            });
          }, 250);
          return;
        }
        setTimeout(() => {
          this.setData({
            showMask: true,
            showReplaceConfirm: true
          });
        }, 250);
        break;
    }
  },
  myPlanEntryHandle() {
    console.log('myPlan', this.data.pageParams)
  },
  /**
   * "houseId": 413485,
   * "operationSource": 1, // 1 方案进 0 我的进
   * "planHouseType": 3,
   * "planRecommendedId": 293555,
   * "renderTaskType": "panorama_render",
   * "taskSource": 1, // 0 C端
   * "taskType": 1,  // 0 自动渲染 1 产品替换
   * "totalFee": 10,
   * "fullHousePlanAction": 2,  // 1 第一次渲染 2 替换旧方案且不产生新的全屋方案 3 替换旧方案并且产生新的全屋方案
   * "templateId": 392023,
   * "groupPrimaryId": 0,
   * "houseGuidePicInfoId": 529,
   * "designTempletId": 392023,
   * "spaceFunctionId": 4,
   * "fullHousePlanId": 274,
   * "taskId": 52396,
   * "preRenderSceneId": 47560
   *  bizType
   */
  async putInHouse(templateId, spaceType, type) {
    let planInfo = wx.getStorageSync('caseItem');
    let item = wx.getStorageSync('matchData');

    let data = {
      taskSource: 0,
      taskType: 0,
      operationSource: 1,
      totalFee: 0,
      planHouseType: 3,
      fullHousePlanAction: type ? type : 1,
      renderTaskType: 'panorama_render', // only 720
      groupPrimaryId: planInfo.groupPrimaryId,
      houseId: this.data.pageParams.houseId,
      planRecommendedId: planInfo.designPlanRecommendId || planInfo.planRecommendedId,
      fullHousePlanId: this.data.pageParams.fullHousePlanId,
      taskId: type == 3 ? '' : this.data.pageParams.mainTaskId,
      preRenderSceneId: this.data.pageParams.preRenderSceneId
    }

    if (!templateId) {
      Object.assign(data, {
        templateId: item.templateId,
        designTempletId: item.templateId,
        spaceFunctionId: planInfo.spaceType || planInfo.spaceFunctionId
      }, this.data.pageParams.matchState == 3 && this.data.pageParams.fullHousePlanId ? {
        fullHousePlanAction: 2
        } : {}, planInfo.spaceType === 13 || planInfo.spaceFunctionId === 13 ? {
        fullHousePlanAction: 1,
        bizType: 2
      } : {})
    }
    else {
      Object.assign(data, {
        templateId: templateId,
        designTempletId: templateId,
        spaceFunctionId: spaceType
      }, spaceType == 13 ? {
        bizType: 2
        } : {}, this.data.pageParams.matchState == 5 && this.data.pageParams.mainTaskId ? {
          preRenderSceneId: item.taskStateList.filter(item => item.templateId === templateId)[0]['businessId']
      } : {})
    }

    let { success, obj, message } = await API.addRenderTask(data);

    if (success) {
      if (templateId) {
        this.spaceCancel();
        wx.hideLoading();

        if (type) {
          this.setData({
            showMask: false,
            showReplaceConfirm: false
          })
        }
      }

      this.setData({
        showMask: true,
        showPutInLoading: true,
        fullHousePlanId: obj.fullHousePlanId,
        mainTaskId: obj.mainTaskId
      });

      wx.setStorage({
        key: 'taskResIdObj',
        data: Object.assign(obj, data),
      });
    }

    if(message === '户型不足,请购买户型!') {
      wx.showModal({
        title: '提示',
        content: '户型数量已用完，购买更多？',
        confirmText: '去购买',
        cancelText: '暂不考虑',
        cancelColor: '#999999',
        confirmColor: '#ff6419',
        success: (res) => {
          res.confirm ? wx.navigateTo({ url: '/pages/purchase-house/purchase-house' }) : null
        }
      })
    }
  },
  selectArea(e) {
    let index = e.currentTarget.dataset.index;
    this.data.selectAreaInfo.guideInfoList.forEach((item, i) => {
      Object.assign(item, { active: index === i })
    });
    this.setData({
      selectAreaInfo: this.data.selectAreaInfo
    })
  },
  spaceCancel() {
    this.setData({
      showMask: false,
      showSelectAreaConfirm: false
    });
  },
  spaceRender() {
    let { designTempletId, spaceType } = this.data.selectAreaInfo.guideInfoList.filter(item => item.active)[0];
    this.data.pageParams.mainTaskId ? this.putInHouse(designTempletId, spaceType, this.data.selectReplaceType): this.putInHouse(designTempletId, spaceType);
    wx.showLoading({ title: '处理中...' });
  },
  replacePlanOrMakeNewPlan(e) {
    let planInfo = wx.getStorageSync('caseItem');
    let type = e.currentTarget.dataset.type;
    if (this.data.pageParams.mainTaskId && this.data.pageParams.matchState == 5) {
      let selectAreaInfo = wx.getStorageSync('matchData')['houseGuidePicInfo'];
      selectAreaInfo.guideInfoList.forEach((item, index) => {
        Object.assign(item, { active: !index });
      })
      setTimeout(() => {
        this.setData({
          selectReplaceType: type,
          showReplaceConfirm: false,
          showSelectAreaConfirm: true,
          selectAreaInfo: selectAreaInfo
        });
      }, 250);
      return;
    }
    this.putInHouse(this.data.pageParams.templateId, planInfo.spaceType, type);
  },
  keepDecorate() {
    wx.redirectTo({
      url: '/pages/house-type/house-details/house-details?type=myPlan&houseId=' + this.data.pageParams.houseId + '&fullHousePlanId=' + this.data.fullHousePlanId + '&mainTaskId=' + this.data.mainTaskId
    })
  },
  async formToThreeD(e) {
    if (!this.data.pageParams.fullHousePlanId) {
      wx.showModal({
        title: '提示',
        content: '您还没有装修完成的效果图，无法预览！'
      });
      return;
    }
    let $App = getApp();
    let planInfo = wx.getStorageSync('caseItem');
    let { obj } = await API.queryWholeHouseUUID({ fullHousePlanId: this.data.pageParams.fullHousePlanId });
    if (obj.vrResourceUuid) {
      let params = {
        uuid: obj.vrResourceUuid,
        houseId: this.data.pageParams.houseId,
        token: wx.getStorageSync('token'),
        platformCode: 'brand2c',
        operationSource: 1,
        routerQueryType: 'seven',
        customReferer: $App.wxUrl,
        platformNewCode: 'miniProgram',
        isRender: 3,
        formId: e.detail.formId,
        renderState: obj.renderState,
        planId: this.data.pageParams.fullHousePlanId
      }
      let url = $App.wholeHouse3dUrl + Object.keys(params).map(item => `${item}=${params[item]}`).join('&');
      getApp().data.webUrl = url;
      wx.navigateTo({ url: '/pages/web-720/web-720' });
    } else {
      wx.showModal({
        title: '提示',
        content: '您还没有装修完成的效果图，无法预览！'
      });
    }
  },
  scaleValue(e) {
    if (this.data.isFingerScale) {
      this.data.salcePicMultipleTemp = e.detail.scale;
    }
  },
  clickScaleValue(e) {
    if (e.currentTarget.dataset.type == 1) {
      this.setData({
        salcePicMultiple: this.data.salcePicMultiple >= 2 ? 2 : Number((this.data.salcePicMultiple + 0.2).toFixed(1))
      })
    }
    else {
      this.setData({
        salcePicMultiple: this.data.salcePicMultiple <= 0.5 ? 0.5 : Number((this.data.salcePicMultiple - 0.2).toFixed(1))
      })
    }
  },
  picTouchStart(e) {
    this.setData({
      isFingerScale: true
    });
  },
  picTouchEnd(e) {
    this.setData({
      salcePicMultiple: this.data.salcePicMultipleTemp
    });

    setTimeout(() => {
      this.setData({
        isFingerScale: false
      })
    })
  },
  showAndHideGuide() {
    this.setData({
      showMask: !this.data.showMask,
      showGuide: !this.data.showGuide
    })
  }
})