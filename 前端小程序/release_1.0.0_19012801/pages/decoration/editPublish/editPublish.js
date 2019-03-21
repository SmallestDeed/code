// pages/decoration/publishDetail/publishDetail.js
let $App = getApp(),
    API = getApp().API
import {
    resourcePath
} from '../../../utils/config.js'
Page({

    /**
     * 页面的初始数据
     */
    data: {
        checkIcon: $App.staticImageUrl+'list_icon_check_nor.png',
        checkedIcon: $App.staticImageUrl+'list_icon_check_sel.png',
        supplyType: 2, //供求
        condition: false, //城市弹窗flag
        cityData: [],
        provinces: [],
        province: "",
        provinceCode: "",
        citys: [],
        city: "",
        cityCode: "",
        countys: [],
        county: '',
        countyCode: '',
        value: [0, 0, 0],
        threeLevelValue: [0, 0, 0],
        region: '',
        supplyId: '',
        arr: {},
        classifyFlag: false,
        classifyValue: 0,
        classifyValue2: 0,
        img: '',
        imgidarr: [],
        imgarr: [],
        title: '',
        content: '',
        user: '',
        phone: '',
        address: '',
        decorationCompany: 1, //装修公司
        designer: 1,
        proprietor: 1,
        builder: 1,
        resourcePath: resourcePath,
        materialShop: 1
    },

    /**
     * 生命周期函数--监听页面加载
     */
    onLoad: function (options) {
        wx.hideShareMenu();
        new $App.newNav() // 注册快速导航组件
        let item = wx.getStorageSync('editItem')
        console.log("123123", item)
        let imgStr = '', imgList = [];
        for (let i = 0; i < item.supplyDemandPicList.length; i++) {
            imgList.push(this.data.resourcePath + item.supplyDemandPicList[i].picPath);
            imgStr += i < item.supplyDemandPicList.length - 1 ? item.supplyDemandPicList[i].id + ',' : item.supplyDemandPicList[i].id
        }
        console.log(imgStr);
        this.setData({
            supplyId: item.supplyDemandCategoryId,
            title: item.title,
            supplyDemandId: item.supplyDemandId,
            content: item.description,
            supplyType: item.typeName == '需求' ? 2 : 1,
            address: item.address,
            user: item.contact,
            imgarr: imgList,
            coverPicId: imgStr,
            phone: item.phone,
            region: item.provinceAddress + item.cityAddress + item.districtAddress,
            provinceCode: item.province,
            cityCode: item.city,
            countyCode: item.district,
            decorationCompany: item.decorationCompany,
            designer: item.designer,
            proprietor: item.proprietor,
            builder: item.builder,
            materialShop: item.materialShop
        })
        console.log(this.data.title)
        this.getCityData();
        this.getSupply();

    },
    getCityData() {
        let cityData = wx.getStorageSync('cityData')
        let provinces = []
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
        this.data.cityData = cityData;

    },
    getSupply() { //获取类别选项
        API.getAllSupplyDemandCategory({
            type: 1
        })
            .then(res => {
                for (let i = 0; i < res.obj[0].supplyDemandCategoryVos.length; i++) {
                    if (res.obj[0].supplyDemandCategoryVos[i].id == this.data.supplyId) {
                        this.setData({
                            arr: res.obj[0].supplyDemandCategoryVos[i]
                        })
                    }
                }
            })
    },
    /**
     * 生命周期函数--监听页面显示
     */
    onShow: function () {

    },
    /**
     * 用户点击右上角分享
     */
    onShareAppMessage: function () {

    },
    changType(e) {
        let type = e.currentTarget.dataset.type;
        this.setData({
            supplyType: type
        })
    },
    selectCity() {
        this.setData({
            condition: true
        })
    },
    // bindChange: function (e) {
    //   const val = e.detail.value
    //   const temp = this.data.threeLevelValue
    //   if (temp[0] !== val[0]) {
    //     this.setData({
    //       value: [val[0], 0, 0]
    //     })
    //     val[1] = 0
    //   } else if (temp[1] !== val[1]) {
    //     this.setData({
    //       value: [val[0], val[1], 0]
    //     })
    //     val[2] = 0
    //   }
    //   this.setData({
    //     threeLevelValue: val
    //   })
    // },
    bindChange: function (e) {
        const cityData = wx.getStorageSync('cityData')
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
        console.log(val)
        this.setData({
            threeLevelValue: val,
            citys: cityData[val[0]].baseAreaVos,
            countys: cityData[val[0]].baseAreaVos[val[1]].baseAreaVos
        })
    },
    provincialLinkageHide(e) { // 确认地址接口
        let flag = e.currentTarget.dataset.flag,
            val = this.data.threeLevelValue
        console.log("--->", val)
        this.setData({
            condition: false
        })
        if (flag) {

            let region = this.data.cityData[val[0]].areaName + this.data.cityData[val[0]].baseAreaVos[val[1]].areaName + this.data.cityData[val[0]].baseAreaVos[val[1]].baseAreaVos[val[2]].areaName
            console.log(this.data.cityData)
            this.setData({
                province: this.data.cityData[val[0]].areaName,
                provinceCode: this.data.cityData[val[0]].areaCode,
                city: this.data.cityData[val[0]].baseAreaVos[val[1]].areaName,
                cityCode: this.data.cityData[val[0]].baseAreaVos[val[1]].areaCode,
                county: this.data.cityData[val[0]].baseAreaVos[val[1]].baseAreaVos[val[2]].areaName,
                countyCode: this.data.cityData[val[0]].baseAreaVos[val[1]].baseAreaVos[val[2]].areaCode,
                region: region,
                value: this.data.threeLevelValue
            })
        }
    },
    classify() {
        this.setData({
            classifyFlag: true
        })
    },

    getTitle(e) {
        var val = e.detail.value;
        this.setData({
            title: val
        });
    },
    getContent(e) {
        var val = e.detail.value;
        this.setData({
            content: val
        });
    },
    getUser(e) {
        var val = e.detail.value;
        this.setData({
            user: val
        });
    },
    getPhone(e) {
        var val = e.detail.value;
        this.setData({
            phone: val
        });
    },
    getAddress(e) {
        var val = e.detail.value;
        this.setData({
            address: val
        });
    },

    classifyChange(e) {
        const val = e.detail.value
        this.setData({
            classifyValue2: val
        })
    },
    classifyCommit(e) {
        let flag = e.currentTarget.dataset.flag
        if (flag) {
            this.setData({
                classifyValue: this.data.classifyValue2
            })
        }
        this.setData({
            classifyFlag: false
        })
    },
    uploadPictrue() { // 上传图片
        if (this.data.imgarr.length >= 9) {
            wx.showToast({
                icon: none,
                title: '最多上传9张图片',
            })
        } else {
            this.setData({
                isShowPhotograph: true
            });
        }
    },
    // 添加图片操作
    photograph(e) {
        if (e.currentTarget.dataset.type == '取消') {
            this.setData({
                isShowPhotograph: false
            });
        } else {
            console.log(e.currentTarget.dataset.type);
            wx.chooseImage({
                count: 1,
                sourceType: [e.currentTarget.dataset.type],
                success: (res) => {
                    console.log(res);
                    this.data.imgarr.push(res.tempFiles[0].path)
                    this.setData({
                        imgarr: this.data.imgarr,
                        img: res.tempFiles[0].path,
                        isShowPhotograph: false
                    })
                    this.upload(this.data.imgarr.length);
                }
            })
        }
    },
    upload() {
        var that = this;
        console.log(this.data.imgarr[0]);
        wx.uploadFile({
            url: 'https://unionapi.sanduspace.com/v1/union/supplydemandpic/uploadrespic',
            filePath: this.data.imgarr[0],
            name: 'file',
            header: {
                'token': wx.getStorageSync('token'),
                "Content-Type": 'multipart/form-data',
                'Authorization': wx.getStorageSync('token') || '',
                'Platform-Code': 'brand2c'
            },
            formData: {
                'platform': 'brand2c',
                'module': 'supply',
                'type': 'image'
            },
            success(res) {
                wx.hideLoading()
                let data = JSON.parse(res.data)
                console.log(data)
                if (data.status) {
                    let id = data.obj.resId;
                    console.log(id)
                    let arr = that.data.imgidarr
                    arr.push(id)
                    that.setData({
                        imgidarr: arr
                    })
                    wx.showToast({
                        title: '上传图片成功',
                    })
                } else {
                    wx.showToast({
                        title: '上传图片失败',
                    })
                }
            },
            fail(res) {
                wx.showToast({
                    title: '上传图片失败',
                })
                wx.hideLoading()
            }
        })
    },
    submit() {
        let content = '';
        let flag = !this.data.decorationCompany && !this.data.designer && !this.data.proprietor && !this.data.builder && !this.data.materialShop
        if (this.data.title == '') {
            content = '请填标题'
        } else if (this.data.content == '' || this.data.content.length < 10) {
            content = '内容需要大于10'
        } else if (this.data.address == '') {
            content = '请填写地址'
        } else if (this.data.phone == '') {
            content = '请填写联系方式'
        } else if (flag) {
            content = '必须选择一个范围'
        }

        if (content) {
            wx.showModal({
                title: '提示',
                content
            });
            return;
        }
        let that = this;
        let datas = {
            title: this.data.title,
            description: this.data.content,
            coverPicId: this.data.coverPicId ? this.data.coverPicId : this.data.imgidarr.join(","),
            type: this.data.supplyType,
            id: this.data.supplyDemandId,
            supplyDemandCategoryId: this.data.arr.supplyDemandCategoryVos[this.data.classifyValue].pid + ',' + this.data.arr.supplyDemandCategoryVos[this.data.classifyValue].id,
            province: this.data.provinceCode,
            city: this.data.cityCode,
            district: this.data.countyCode,
            address: this.data.address,
            contact: this.data.user.length < 10 ? this.data.user : this.data.user.substring(0, 9),
            phone: this.data.phone,
            street: '',
            builder: this.data.builder,
            decorationCompany: this.data.decorationCompany,
            designer: this.data.designer,
            materialShop: this.data.materialShop,
            proprietor: this.data.proprietor
        }
        API.editSupplyAndDemand(datas).then(res => {
            that.statusFun(res.status);
        })
    },
    statusFun: function (bool) {
        if (bool) {
            wx.showToast({
                title: '修改成功',
                icon: 'success',
                duration: 1500,
                complete: function () {
                    setTimeout(function () {
                        wx.navigateBack({
                            delta: 1
                        })
                    }, 1500)
                }
            })
        } else {
            wx.showModal({
                title: '提示',
                content: res.message,
                confirmText: '确定',
                cancelText: '取消',
                cancelColor: '#999',
                confirmColor: '#ff6419'
            })
        }
    },
    changerange(e) {
        let id = e.currentTarget.dataset.id
        if (id == 1) {
            this.setData({
                decorationCompany: this.data.decorationCompany == 1 ? 0 : 1
            })
        } else if (id == 2) {
            this.setData({
                designer: this.data.designer == 1 ? 0 : 1
            })
        } else if (id == 3) {
            this.setData({
                proprietor: this.data.proprietor == 1 ? 0 : 1
            })
        } else if (id == 4) {
            this.setData({
                builder: this.data.builder == 1 ? 0 : 1
            })
        } else if (id == 5) {
            this.setData({
                materialShop: this.data.materialShop == 1 ? 0 : 1
            })
        }
    },
    deleteImg(e) {
        let index = e.currentTarget.dataset.index;
        console.log(index)
        let arr = this.data.imgarr
        let idarr = this.data.imgidarr
        arr.splice(index, 1);
        idarr.splice(index, 1);
        this.setData({
            imgarr: arr,
            imgidarr: idarr
        })
    }
})