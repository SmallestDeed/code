// pages/mini-offcial-web/mini-offcial-web.js
import fetch from '../../utils/fetch.js';
let $App = getApp()
let bmap = require('../../lib/es6-promise/bmap-wx.min.js');
var { shareTitle } = require('../../utils/config.js');
var shareTitle = { shareTitle }.shareTitle;
import { appid } from '../../utils/config.js';
Page({

    /**
     * 页面的初始数据
     */
    data: {
        resourcePath: getApp().resourcePath,
        triangleicon: $App.staticImageUrl+'wei_icon_down.png',
        staticImageUrl: $App.staticImageUrl,
        searchicon: $App.staticImageUrl+'search.png',
        shopList: [],
        companyId: '',
        pageSize: 10,
        totalCount: '',
        imageUrl: '.image/wei_pic_nor.png',
        cityData: [],
        provinces: [],
        province: "",
        citys: [],
        city: '全部',
        isNk: false,
        countys: [],
        county: '',
        resourcePath: getApp().resourcePath,
        value: [0, 0, 0],
        threeLevelValue: [0, 0, 0],
        condition: false,
        cityCode: "",
        shopCity: '',
        imgUrls: [],
        showMore: false,
        userLocation: {},
        userMessage: {},
        noMore:false,
        appid: appid,
        bannerImg:''
    },

    /**
     * 生命周期函数--监听页面加载
     */
    onLoad: function () {
        this.getCityData();
        let that = this;
        if (shareTitle == '诺克照明') {
            that.getUserLocation();
            that.setData({
                isNk: true,
            })
        }
        this.getCompanyId();
        this.getImgUrls();
        this.getBrand();
    },
  getBrand() {
    let url = '/core/company/branddesc/' + this.data.appid + '/N',
      that = this
    fetch(url, 'get', '', 'core').then((res) => {
      if (res.obj) {
        that.setData({
          bannerImg: res.obj.picPath
        })
        
      }
    })

  },
    goLocation: function (e) {
        let latitude = parseFloat(e.currentTarget.dataset.latitude),
            longitude = parseFloat(e.currentTarget.dataset.longitude),
            name = e.currentTarget.dataset.name,
            address = e.currentTarget.dataset.address;
        wx.openLocation({
            latitude: latitude,
            longitude: longitude,
            name: name,
            address: address
        })
    },
    getUserStorage: function () {
        let that = this;
        wx.getStorage({
            key: 'userMessage',
            success: function (res) {
                that.setData({
                    userMessage: res.data,
                })
                for (let i = 0; i < that.data.cityData.length; i++) {
                    if (res.data.province == that.data.cityData[i].areaName) {
                        for (let j = 0; j < that.data.cityData[i].baseAreaVos.length; j++) {
                            if (res.data.city == that.data.cityData[i].baseAreaVos[j].areaName) {
                                that.setData({
                                    citys: that.data.cityData[i].baseAreaVos,
                                    city: res.data.city,
                                    cityCode: that.data.cityData[i].baseAreaVos[j].areaCode,
                                    countys: that.data.cityData[i].baseAreaVos[j].baseAreaVos,
                                    threeLevelValue: [i, j, 0],
                                    value: [i, j, 0],
                                })
                            }
                        }
                    }
                }
            }
        })
    },
    getUserLocation: function () {
        let that = this;
        wx.getStorage({
            key: 'userLocation',
            success: function (res) {
                that.setData({
                    userLocation: res.data,
                })
            },
            fail: function (res) {
                that.getCityMessage();
            }
        })
        that.getUserStorage();
    },
    getCityMessage: function () {
        let cityMessage = null,
            $self = this,
            that = this;
        // 获取所处的地理位置
        let BMap = new bmap.BMapWX({
            ak: 'fKNgchMcIyhzxZDfPpdkEHPAHNVNBN62'
        });
        BMap.regeocoding({
            success(data) { // 成功之后调用接口进行保存
                cityMessage = data.originalData.result.addressComponent // 这就是我们需要的东西
                if (cityMessage) {
                    let url = '/user/sysuser/updateAddress'
                    let data = {
                        province: cityMessage.province,
                        city: cityMessage.city,
                        area: cityMessage.district,
                        street: cityMessage.street,
                        address: cityMessage.province + cityMessage.city + cityMessage.district + cityMessage.street + cityMessage.street_number
                    }
                    fetch(url, 'post', data).then((res) => {
                        if (res.status) {
                            wx.showToast({
                                title: '储存位置成功',
                                icon: 'success'
                            })
                            setTimeout(function () {
                                $self.getUserStorage();
                                $self.getCompanyId();
                                wx.getStorage({
                                    key: 'userLocation',
                                    success: function (res) {
                                        that.setData({
                                            userLocation: res.data,
                                        })
                                    }
                                })
                            }, 100)
                            wx.setStorage({
                                key: "userMessage",
                                data: cityMessage
                            })
                        }
                    })
                }
            },
            fail: function (error) {
                console.log(error)
                wx.getSetting({
                    success(res) {
                        console.log(res)
                    }
                })
            }
        });
    },
    goWeiGuang(e) {
        let num = e.currentTarget.dataset.num;
        let isIndex = false;
        let str = ['/pages/index/index', '/pages/house-case/house-case', '/pages/house-goods/house-goods', '/pages/house-type/house-type', '/pages/personal-center/personal-center'];
        for (let i = 0; i < str.length; i++) {
            if (this.data.skipPath[num] == str[i]) {
                isIndex = true;
            }
        }
        // wx.navigateTo({ url: '/pages/special-sales/special-sales' })
        // return;
        isIndex ? wx.switchTab({
            url: this.data.skipPath[num]
        }) : wx.navigateTo({
            url: this.data.skipPath[num]
        });
    },
    getImgUrls: function () {
        let url = '/product/baseCompany/getCompanyId',
            that = this

        fetch(url, 'get').then((res) => {
            if (res.obj) {
                let url2 = '/v1/base/banner/web/miniprogram/list'
                fetch(url2, 'get', {
                    positionCode: "company:weiguanwang:home:top",
                    companyId: res.obj
                }, 'system').then((res) => {
                    let imgArr = [],
                        skipArr = [],
                        img = res.datalist
                    for (let i = 0; i < img.length; i++) {
                        imgArr.push(img[i].picPath);
                        skipArr.push(img[i].skipPath)
                    }
                    that.setData({
                        imgUrls: imgArr,
                        skipPath: skipArr
                    })
                })
            }
        })
    },
    /**
     * 生命周期函数--监听页面初次渲染完成
     */
    onReady: function () {

    },
    getCompanyId() {
        let url = '/product/baseCompany/getCompanyId',
            data = {}
        fetch(url, 'get')
            .then((res) => {
                if (res.obj) {

                    this.setData({
                        companyId: res.obj
                    })
                    this.getList()
                }
            })
    },
    getList() { //请求列表
        let url = 'v1/sandu/mini/companyshop/offline/list',
            pageSize = this.data.pageSize,
            pageNo = 1,
            companyId = this.data.companyId,
            data = {
                companyId: companyId,
                pageSize: pageSize,
                pageNo: pageNo,
                businessType: 2,
                platformType: 1,
                cityCode: this.data.cityCode,
                longitude: this.data.userLocation.longitude ? this.data.userLocation.longitude : '',
                latitude: this.data.userLocation.latitude ? this.data.userLocation.latitude : '',
            }
        fetch(url, 'get', data, 'shop')
            .then((res) => {
                if (res.code == 200) {
                    for (let j = 0; j < res.data.list.length; j++) {
                        if (res.data.list[j].provinceName == '北京市' || res.data.list[j].provinceName == '天津市' || res.data.list[j].provinceName == '上海市' || res.data.list[j].provinceName == '重庆市') {
                            res.data.list[j].provinceName = ''
                        }
                    }
                    if (res.data.list.length < 10) {
                        this.setData({
                            showMore: false
                        })
                    }
                    this.setData({
                        shopList: res.data.list,
                        totalCount: res.data.count,
                        showMore: true,
                        noMore: this.data.pageSize > res.data.count ? true : false
                    })

                }
                if (res.code == 400) {
                    this.setData({
                        shopList: [],
                        noMore: true,
                    })
                }
            })
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
        this.getCompanyId();
        wx.stopPullDownRefresh();
    },

    /**
     * 页面上拉触底事件的处理函数
     */
    onReachBottom: function () {
        if (this.data.pageSize <= this.data.totalCount) {
            let pagenum = this.data.pageSize + 10
            this.setData({
                pageSize: pagenum
            })
            this.getList();
        } else {
            this.setData({
                noMore: true,
            })
            return
        }
    },

    /**
     * 用户点击右上角分享
     */
    onShareAppMessage: function (res) {
        if (res.from === 'menu') {
            return $App.shareAppMessageFn(true);
        }
    },
    todetail(e) {
        let curr = e.currentTarget.dataset
        let id = curr.id,
            city = curr.city,
            name = curr.name,
            longitude = curr.longitude,
            latitude = curr.latitude,
            distance = curr.distance,
            shoptype = curr.shoptype,
            data = '?id=' + id + '&shoptype=' + shoptype + '&city=' + city + '&name=' + name + '&longitude=' + longitude + '&latitude=' + latitude + '&distance=' + distance;
        wx.navigateTo({
            url: '/pages/web-detail-complicated/web-detail-complicated' + data,
        })
    },
    toSearch() {
        wx.navigateTo({
            url: '/pages/allshop/allshop',
        })
    },
    errorFunction: function (e) {
        var _errImg = e.target.dataset.img
        var _errObj = {}
        _errObj[_errImg] = "image/wei_pic_nor.png"
        this.setData(_errObj) //注意这里的赋值方式,只是将数据列表中的此项图片路径值替换掉  
    },
    bindChange: function (e) {
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
            this.getList();
        }

    },
    getCityData() {
        let cityData = $App.cityDataFilter(wx.getStorageSync('cityData'))
        let provinces = []
        cityData[34] = {
            areaCode: '',
            areaName: '全部',
            baseAreaVos: [{
                areaCode: '',
                areaName: '全部',
                baseAreaVos: [{
                    areaCode: '',
                    areaName: '全部',
                    baseAreaVos: null,
                    id: null,
                    leveId: 3,
                    pid: ''
                }],
                id: 9999,
                levelId: 2,
                pid: '0000000'
            }],
            id: '00000',
            levelId: 1,
            pid: 0,
        }
        let j = cityData[34]
        for (let i = 34; i > 0; i--) {
            cityData[i] = cityData[i - 1]
        }
        cityData[0] = j
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
        this.data.cityData = cityData
    },

    changeCtiy: function (e) {
        this.setData({
            condition: true
        })
    },
})