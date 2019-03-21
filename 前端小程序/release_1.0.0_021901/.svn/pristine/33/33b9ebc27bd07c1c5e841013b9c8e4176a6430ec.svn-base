// pages/house/house.js
let app = getApp(), API = getApp().API, myForEach = getApp().myForEach
Page({
  /**
   * 页面的初始数据
   */
  data: {
    resourcePath: app.resourcePath,
    curPage: 1,
    livingId: '',
    totalCount: 0,
    houseList: [],
    status: 0,
    type:''
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    wx.setNavigationBarTitle({ title: options.name });
    this.data.livingId = options.id;
    this.data.type = options.type;
    this.getHouselist();
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

  /*查看大图*/
  examineImg(e) {
    if (e.currentTarget.dataset.img) {
      wx.previewImage({
        urls: [e.currentTarget.dataset.img],
        current: e.currentTarget.dataset.img
      })
    }
  },

  /*一键装修*/
  goOnekeyFinish(e) {
    let item = e.currentTarget.dataset.item
    let totalArea = item.totalArea;
    let caseItem = wx.getStorageSync('caseItem');
    if (caseItem) {
      let spaceFunctionId = caseItem.spaceFunctionId || caseItem.spaceType;
      let caseItemAreaValue = '';
      if (caseItem.applySpaceAreas) {
        caseItemAreaValue = caseItem.applySpaceAreas.split(',')
      }
      let data = {
        houseId: item.id,
        designPlanRecommendId: caseItem.planRecommendedId || caseItem.designPlanRecommendId,
        groupPrimaryId: caseItem.groupPrimaryId ? caseItem.groupPrimaryId : ''
      }
      API.examineHouseIsMatching(data).then(res => {
        if (res.status) {
          if (JSON.stringify(res.obj) !== '[]') {
            let arr = []
            let flag = false
            let setCaseItem = false
            let groupFlag = false
            let nowItems = {};
            if (caseItem.groupPrimaryId != 0 && caseItem.groupPrimaryId != null && caseItem.groupPrimaryId == data.designPlanRecommendId) {
              myForEach(res.obj, (val) => {
                // console.log(res.obj, 'res.obj')
                if (spaceFunctionId == 13) {//13时为全屋，不需判断
                  flag = true
                  setCaseItem = true
                  nowItems = res.obj;
                 
                } else if (val.spaceFunctionId === spaceFunctionId) {
                  
                  if (val.designTemplets != null) {
                    // console.log(val.designTemplets[0].designPlanRecommended);
                    if (val.designTemplets[0].designPlanRecommended != null) {
                      arr.push(val)
                      flag = true
                      groupFlag = true
                      nowItems = res.obj;
                    }
                  }
                }
              });
            } else {
              myForEach(res.obj, (val) => {
                if (spaceFunctionId == 13) {//13时为全屋，不需判断
                  flag = true
                  setCaseItem = true
                } else if (val.spaceFunctionId === spaceFunctionId && caseItemAreaValue.indexOf(val.spaceAreas) !== -1) {
                  flag = true
                  setCaseItem = true
                }
              });
            }
            setTimeout(function () {
              item.houseName = item.houseName.replace(/\?/g, "*")
              if (flag) {
                // console.log(arr[0])
                // arr[0] = arr[0].houseName.replace(/\?/g, "*")
                let strs = groupFlag ? ('&groupItem=' + JSON.stringify(arr[0])) : '';
                // console.log(strs)
                wx.navigateTo({
                  url: '/pages/OnekeyFinish/OnekeyFinish?type=houseCaseTrue&setCaseItem=' + setCaseItem + '&item=' + JSON.stringify(item) + strs
                })
              } else {
                wx.showModal({
                  title: '提示',
                  content: '该户型暂时不能匹配您选择的方案!是否需要为你推荐适合当前户型的其它方案',
                  cancelText: '暂不考虑',
                  cancelColor: '#8e8e8e',
                  confirmText: '需要推荐',
                  confirmColor: '#29cccc',
                  success: (res) => {
                    if (res.confirm) {
                      wx.navigateTo({
                        url: '/pages/OnekeyFinish/OnekeyFinish?type=houseCaseFalse&item=' + JSON.stringify(item) + '&type=' + 'houseCaseFalse',
                      })
                    }
                  }
                })
              }
            },300)
          }
        }
      });
    } else {
      wx.navigateTo({
        url: '/pages/OnekeyFinish/OnekeyFinish?type=houseType&totalArea=' + totalArea + '&item=' + JSON.stringify(item)
      })
    }
  },

  /*获取户型列表,type:pull为下拉拼接数据*/
  getHouselist(type) {
    API.getHouselist({
      livingId: this.data.livingId,
      pageSize: 10,
      curPage: this.data.curPage
    }).then(res => {
      if (res.obj) {
        let arr = type == 'pull' ? this.data.houseList.concat(res.obj) : res.obj;
        this.setData({
          houseList: arr,
          totalCount: res.totalCount
        });
      }
    });
  },

  /*防止遮罩层弹出后滑动滑动*/
  preventTouchMove() {},

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
    if (this.data.curPage*10 < this.data.totalCount) {
      this.data.curPage += 1;
      this.getHouselist('pull');
    }
  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function () {

  }
})