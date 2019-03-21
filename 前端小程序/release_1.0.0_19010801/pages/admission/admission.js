let API = getApp().API,
    myForEach = getApp().myForEach,
    $App = getApp();
Page({
    data: {
        ifShowToTopLogo: false,
        cityData: [],
        staticImageUrl: $App.staticImageUrl,
        imgUp: $App.staticImageUrl + 'page_icon_up.png',
        imgDown: $App.staticImageUrl + 'page_icon_down.png',
        provinceList: [],
        cityList: [],
        threeLevelValue: [0, 0],
        provincialLinkageShow: false,
        cityVal: '广东省深圳市',
        checked: true,
        businessType: 2,
        cityCode: "440300",
        companyName: "装修公司",
        cName: "",
        companyType: 1,
        mobile: "",
        provinceCode: "440000",
        comType: 1,
        index: [1],
        sourceType: 0,
        userName: "",
        companyShow: false,
        comValue: '',
        showTost: false,
        showTost2: false
    },
    onLoad: function (options) {
        wx.hideShareMenu()
        API.getAreaByPid({
            pid: 0,
            levelId: 1
        }).then(res => {
            let provinces = []
            $App.myForEach(res.obj.provinces, (value) => {
                provinces.push({
                    areaCode: value.areaCode,
                    areaName: value.areaName,
                    id: value.id,
                    levelId: value.levelId,
                    pid: value.pid
                })
            })
            this.setData({
                provinceList: provinces, //省数据
                cityList: res.obj.cities,   //市数据
            })
            console.log(this.data.provinceList)
            console.log(this.data.cityList)

        })

        // let cityData = wx.getStorageSync('cityData')
        // this.data.cityData = cityData
        // let provinceList = []
        // $App.myForEach(cityData, (value) => {
        //     provinceList.push({
        //         areaCode: value.areaCode,
        //         areaName: value.areaName,
        //         id: value.id,
        //         levelId: value.levelId,
        //         pid: value.pid
        //     })
        // })
        // this.setData({
        //     provinceList: provinceList,
        //     cityList: this.data.cityData[0].baseAreaVos,
        // })
    },
    closeFun() {
        this.setData({
            showTost: false,
        })
    },
    goHome() {
        wx.switchTab({
            url: '/pages/home/home',
        })
    },
    onShow: function () {

    },
    onShareAppMessage: function () {
      wx.hideShareMenu();
    },
    goEntry() {
        wx.pageScrollTo({ scrollTop: 0, duration: 300 })
        this.setData({
            ifShowToTopLogo: false
        })
    },
    myRules(type, param) {
        let regOne = /[\ud800-\udbff][\udc00-\udfff]/, regTwo = /[\u4e00-\u9fa5]/, flag = true, title = ''
        if (!param && type != 'checked') { return { flag: false, title: '请正确输入信息' } }
        switch (type) {
            case 'userName': flag = !(regOne.test(param) || !regTwo.test(param)); title = '请正确输入联系人'; break;
            case 'mobile': flag = /^1[3|4|5|7|8][0-9]\d{8}$/.test(param); title = '请正确输入手机号'; break;
            case 'checked': flag = param; title = '请阅读并接受随选网协议'; break;
        }
        return { flag: flag, title: title }
    },
    enterpriseEntry() {
        let flag = true, title, objKey = '', obj = {
            companyName: this.data.cName,
            userName: this.data.userName,
            mobile: this.data.mobile,
            checked: this.data.checked
        }
        for (let key in obj) {
            let rules = this.myRules(key, obj[key])
            console.log(key, obj[key], rules)
            if (!rules.flag) { flag = false, objKey = key, title = rules.title; break; }
        }
        if (flag) {
            API.enterpriseEntry({
                businessType: 2,
                cityCode: this.data.cityCode,
                companyName: this.data.cName,
                companyType: this.data.companyType,
                mobile: this.data.mobile,
                provinceCode: this.data.provinceCode,
                sourceType: 0,
                userName: this.data.userName,
            }).then(res => {
                if (res.success) {
                    this.setData({ showTost: true })
                } else {
                    wx.showToast({ title: res.message, icon: 'none' })
                }
            })
        } else {
            console.log(title);
            wx.showToast({ title: title, icon: 'none' })
            if (objKey === 'mobile' || objKey === 'userName') { this.setData({ [objKey]: '' }) }
        }
    },
    changeInput(e) {
        let ty = e.currentTarget.dataset.type;
        let val = e.detail.value.trim();

        switch (ty) {
            case 'cName':
                this.setData({ cName: val })
                break;
            case 'userName':
                this.setData({ userName: val })
                break;
            case 'mobile':
                this.setData({ mobile: val })
                break;
        }
    },
    navTo() {
        wx.navigateTo({
            url: '/pages/logOn/logOn',
        })
    },
    checked() {
        this.setData({
            checked: !this.data.checked
        })
    },
    houseProvincialShow() {
        this.setData({
            provincialLinkageShow: true
        })
    },
    companyShow() {
        this.setData({
            companyShow: true
        })
    },
    bindChange: function (e) {
        const val = e.detail.value
        const temp = this.data.threeLevelValue
        if (temp[0] !== val[0]) {
            API.getAreaByPid({ pid: this.data.provinceList[val[0]].areaCode, levelId: 2 }).then(res => { this.setData({ cityList: res.obj }) })
            this.setData({
                value: [val[0], 0]
            })
            val[1] = 0
        } else if (temp[1] !== val[1]) {
            this.setData({
                value: [val[0], val[1]]
            })
        }
        this.setData({
            cityList: this.data.cityList,
            threeLevelValue: val
        })
    },
    provincialLinkageHide(e) { // 隐藏三级
        let val = this.data.threeLevelValue
        this.setData({
            provincialLinkageShow: false
        })
        if (e.currentTarget.dataset.type === 'confirm') {
            this.data.cityVal = this.data.provinceList[val[0]].areaName + this.data.cityList[val[1]].areaName
            this.setData({
                value: this.data.threeLevelValue,
                provinceCode: this.data.provinceList[val[0]].areaCode,
                cityCode: this.data.cityList[val[1]].areaCode,
                city: this.data.cityList[val[1]].areaName,
                cityVal: this.data.cityVal
            })
        }
    },
    comBindChange: function (e) {
        this.setData({
            comValue: e.detail.value[0] == 0 ? "设计公司" : "装修公司",
            comType: e.detail.value[0],
            index: e.detail.value
        })
    },
    companyHidenone(e) {
        this.setData({
            companyShow: false
        })
    },
    companyHideconfirm(e) {
        this.setData({
            companyShow: false,
            companyName: this.data.index[0] == 0 ? '设计公司' : '装修公司',
            companyType: this.data.index[0]
        })
    },
    toUrl: function () {
        this.setData({
            showTost2: true,
        })
    },
    checkedT: function () {
        this.setData({
            checked: true,
            showTost2: false,
        })
    },
    checkedF: function () {
        this.setData({
            checked: false,
            showTost2: false,
        })
    },
    onPageScroll(e) {
        e.scrollTop > 600 ? this.setData({
            ifShowToTopLogo: true
        }) : this.setData({
            ifShowToTopLogo: false
        })
    },
})