
let $App = getApp();
let fetch = getApp().fetch
Page({
    data: {
      staticImageUrl: getApp().staticImageUrl,
        value: [0, 0, 0],
        provinceList: [],
        cityList: [],
        districtList: [],
        province: '',
        city: '',
        district: '',
        threeLevelValue: [0, 0, 0],
        provincialLinkageShow: false,
        cityData: [],
        houseMainParams: {
            region: '',
            houseEstate: '',
            houseName: '',
            houseArea: '',
            houseDescribe: '',
            focus: false,
        },
        navObj:[
            {
                name: '首页',
                url: '/pages/index/index',
                icon: getApp().staticImageUrl+'pop_icon_home.png'
            },
            {
                name: '方案',
                url: '/pages/house-case/house-case',
                icon: getApp().staticImageUrl+'pop_icon_place.png'
            },
            {
                name: '商品',
                url: '/pages/house-goods/house-goods',
                icon: getApp().staticImageUrl+'pop_icon_goods.png'
            },
            {
                name: '户型',
                url: '/pages/house-type/house-type',
                icon: getApp().staticImageUrl+'pop_icon_type.png'
            },
            {
                name: '我的',
                url: '/pages/personal-center/personal-center',
                icon: getApp().staticImageUrl+'pop_icon_me.png'
            },
        ],
        showNav:false,
        bottom:0,
        contactNumber: '',
        placeholderText:'',
        uploadImage: getApp().staticImageUrl+'pic_add.png'
    },
    navTo:function(e){
        wx.switchTab({
            url: e.currentTarget.dataset.url
        })
    },
    openFun: function () {
        this.setData({
            showNav: true
        })
    },
    closeFun:function(){
        this.setData({
            showNav: false
        })
    },
    /**
     * 生命周期函数--监听页面加载
     */
    onLoad: function(options) {
        new $App.quickNavigation() // 注册组件
        let cityData = wx.getStorageSync('cityData')
        this.data.cityData = cityData
        let provinceList = []
        $App.myForEach(cityData, (value) => {
            provinceList.push({
                areaCode: value.areaCode,
                areaName: value.areaName,
                id: value.id,
                levelId: value.levelId,
                pid: value.pid
            })
        })
        this.setData({
            provinceList: provinceList,
            cityList: this.data.cityData[0].baseAreaVos,
            districtList: this.data.cityData[0].baseAreaVos[0].baseAreaVos
        })
    },
    // 地区选择API开始
    houseProvincialShow() {
        this.setData({
            provincialLinkageShow: true
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
            cityList: this.data.cityData[val[0]].baseAreaVos,
            districtList: this.data.cityData[val[0]].baseAreaVos[val[1]].baseAreaVos,
            threeLevelValue: val
        })
    },
    provincialLinkageHide(e) { // 隐藏三级
        let val = this.data.threeLevelValue
        this.setData({
            provincialLinkageShow: false
        })
        if (e.currentTarget.dataset.type === 'confirm') {
            this.data.houseMainParams.region = this.data.cityData[val[0]].areaName + this.data.cityData[val[0]].baseAreaVos[val[1]].areaName + this.data.cityData[val[0]].baseAreaVos[val[1]].baseAreaVos[val[2]].areaName
            this.setData({
                value: this.data.threeLevelValue,   
                province: this.data.cityData[val[0]].areaName,
                city: this.data.cityData[val[0]].baseAreaVos[val[1]].areaName,
                district: this.data.cityData[val[0]].baseAreaVos[val[1]].baseAreaVos[val[2]].areaName,
                houseMainParams: this.data.houseMainParams
            })
        }
    },
    changeInput(e) {
        let key = e.target.dataset.type == 'contactNumber' ? e.target.dataset.type : 'houseMainParams.' + e.target.dataset.type
        this.setData({
            [key]: e.detail.value,
        })
        e.target.dataset.type == 'houseDescribe' ? this.setData({ placeholderText: e.detail.value}):''

    },
    // 地区选择API结束
    uploadHousePictrue() { // 上传户型图
        wx.chooseImage({
            count: 1,
            success: (res) => {
                this.setData({
                    uploadImage: res.tempFiles[0].path
                })
            }
        })
    },
    submitHouseMessage() { // 提交户型信息
        let flag = false;
        let that = this;
        for (let key in this.data.houseMainParams) {
            if (!this.data.houseMainParams[key] && key != 'houseDescribe' && key != 'focus') {
                flag = true
            }
        }
        if (flag) {
            wx.showToast({
                title: '请填写完整数据',
                icon: 'none'
            })
            return
        } else if (this.data.uploadImage === getApp().staticImageUrl+'pic_add.png') {
            let url = '/home/basehouseapply/uploadhouse'
            let data = {
                'cityInfo': this.data.houseMainParams.region,
                'livingInfo': this.data.houseMainParams.houseEstate,
                'houseName': this.data.houseMainParams.houseName,
                'houseArea': parseInt(this.data.houseMainParams.houseArea),
                'description': this.data.houseMainParams.houseDescribe,
                'platform': 'brand2c',
                'module': 'house',
                'type': 'image',
                'contactNumber': this.data.contactNumber
            }

            let _phone = this.data.contactNumber;
            if (_phone && !(/^1[3|4|5|8][0-9]\d{8}$/.test(_phone))) {
                wx.showToast({
                    title: '请正确填写电话!',
                    icon: 'none'
                })
                return;
            }
            fetch(url, 'formData', data)
                .then((res) => {
                    if (res.success) {
                        wx.showToast({
                            title: '上传户型成功',
                        })
                        setTimeout(function(){
                            let obj = {};
                            obj.region = ''
                            obj.houseEstate = ''
                            obj.houseName = ''
                            obj.houseArea = ''
                            obj.houseDescribe = '';
                            that.setData({
                                uploadImage: getApp().staticImageUrl+'pic_add.png',
                                contactNumber:'',
                                houseMainParams:obj
                            })
                        },500)
                        //wx.navigateBack()
                    } else {
                        wx.showToast({
                            title: '上传户型失败',
                            icon: 'none'
                        })
                    }
                })
        } else {
            wx.uploadFile({
                url: $App.baseUrl + '/home/basehouseapply/uploadhouse',
                filePath: this.data.uploadImage,
                name: 'file',
                header: {
                    'token': wx.getStorageSync('token'),
                    "Content-Type": 'multipart/form-data',
                    'Authorization': wx.getStorageSync('token') || '',
                    'Platform-Code': 'brand2c'
                },
                formData: {
                    'cityInfo': this.data.houseMainParams.region,
                    'livingInfo': this.data.houseMainParams.houseEstate,
                    'houseName': this.data.houseMainParams.houseName,
                    'houseArea': parseInt(this.data.houseMainParams.houseArea),
                    'description': this.data.houseMainParams.houseDescribe,
                    'contactNumber': this.data.contactNumber || '',
                    'platform': 'brand2c',
                    'module': 'house',
                    'type': 'image'
                },
                success(res) {
                    wx.hideLoading()
                    let data = JSON.parse(res.data)
                    if (data.status) {
                        wx.showToast({
                            title: '上传户型成功',
                        })
                        setTimeout(function () {
                            let obj = {};
                            obj.region = ''
                            obj.houseEstate = ''
                            obj.houseName = ''
                            obj.houseArea = ''
                            obj.houseDescribe = '';
                            that.setData({
                                uploadImage: getApp().staticImageUrl+'pic_add.png',
                                contactNumber: '',
                                houseMainParams: obj
                            })
                        }, 500)
                    } else {
                        wx.showToast({
                            title: '上传户型失败',
                            icon: 'none'
                        })
                    }
                },
                fail(res) {
                    wx.showToast({
                        title: '上传户型失败',
                        icon: 'none'
                    })
                    wx.hideLoading()
                }
            })
        }
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
    },
    onShareAppMessage: function(res) {
        if (res.from === 'menu') {
            return $App.shareAppMessageFn(false);
        }
    },
})