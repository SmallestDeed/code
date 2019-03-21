import axios from 'axios';
import store from '../../store';
import router from '../../router';

// axios 配置
axios.defaults.timeout = 60000;
// axios.defaults.headers.post['Content-Type'] = 'application/x-www-form-urlencoded;charset=UTF-8';
axios.defaults.headers['Content-Type'] = 'application/json';

// POST传参序列化
axios.interceptors.request.use((config) => {
  config.headers['MediaType'] = 'MOBILE';  // 设备类型
  if (localStorage.token) {
    config.headers['Authorization'] = localStorage.token;
  }
  // console.log(store.state.isLoader);
  if (store.state.isLoader) {
    store.dispatch('showLoading');
  }
  return config;
}, (error) => {
  return Promise.reject(error);
});
// 返回状态判断
axios.interceptors.response.use((res) => {
  if (res.data.success) {
    // setTimeout(() => {
    //   store.dispatch('hideLoading');
    // }, 500);
    store.dispatch('hideLoading');
    return res;
  } else {
    if (res.data.CheckOneLogin) {
      let msg = res.data.message;
      store.commit('toastMsg', msg);
      store.commit('showToast');
      localStorage.setItem('token', '');
      sessionStorage.clear();
      return;
    }
    let errorMsg = res.data.message;
    if (errorMsg === '登录超时，请重新登录') { // 认证失败，返回登录页
      store.state.timeOut = true; // 登录超时
      router.replace('/');
      store.commit('showComComponents', false);
      localStorage.setItem('token', '');
      sessionStorage.clear();
      return false;
    } else if (errorMsg === '账号或密码错误!') { // 账户密码错误次数判断，跳转到忘记密码
      store.state.errorCount++;
      if (store.state.errorCount > 3) {
        store.commit('showComComponents', false);
        store.commit('popupMsg', '账号或密码错误超过3次，请修改密码！');
        store.commit('showPopup');
        setTimeout(() => {
          router.push('/reset');
        }, 2500);
        return false;
      }
    } else if (errorMsg === '您尚未开通移动版功能，请联系客服开通!') {
      store.state.isRenew = false;
      router.push('/payopen');
      return;
    } else if (errorMsg === '移动版已到期，请续费开通！') {
      store.state.isRenew = true;
      router.push('/payopen');
      return;
    }
    store.commit('popupMsg', errorMsg);
    store.commit('showPopup');
    return false;
  }
}, (error) => {
  store.commit('popupMsg', '系统繁忙，请稍后再试！');
  // store.commit('showPopup');
  return Promise.reject(error);
});

export function fetch (url, params, type) {
  // 动态添加baseUrl
  // axios.defaults.baseURL = (localStorage.getItem('serverUrl') || 'http://mobileapp.sanduspace.cn/') + 'app';
  axios.defaults.baseURL = (localStorage.getItem('serverUrl') || 'http://testmobileappnew.sanduspace.cn/') + 'app';
  // axios.defaults.baseURL = (localStorage.getItem('serverUrl') || 'http://192.168.1.107:49080/') + 'app';
  // axios.defaults.baseURL = (localStorage.getItem('serverUrl') || 'http://192.168.1.107:39080/') + 'app';
  // axios.defaults.baseURL = (localStorage.getItem('serverUrl') || 'http://192.168.1.107:29080/') + 'app';
  // axios.defaults.baseURL = (localStorage.getItem('serverUrl') || 'http://tests.sanduspace.cn:8889/') + 'app';
  // axios.defaults.baseURL = (localStorage.getItem('serverUrl') || 'http://192.168.3.5:8080/sandu-yun-web-mobile');
  return new Promise((resolve, reject) => {
    axios({
      method: type || 'post',
      url: url,
      data: params
    }).then(response => {
        if (response == undefined) {
          return;
        }
        resolve(response.data);
      }, err => {
        reject(err);
      })
      .catch((error) => {
        reject(error);
      });
  });
};

export default {
  login (params) {
    return fetch('/app/mobile/user/login.htm', params); // 登录
  },
  getCode (params) {
    return fetch('/app/mobile/user/app/sms.htm', params); // 获取验证码
  },
  reset (params) {
    return fetch('/app/mobile/user/app/findPassword.htm', params); // 找回密码
  },
  myDesign (params) {
    return fetch('/app/mobile/design/designPlan/myDesignPlanMobile.htm', params); // 我的设计
  },
  recom (params) {
    return fetch('/app/mobile/designPlanRecommended/planRecommendedList.htm', params); // 推荐
  },
  collect (params) {
    return fetch('/app/mobile/designPlanCollect/getCollectList.htm', params); // 获取所有收藏方案
  },
  space (params) {
    return fetch('/app/mobile/design/designPlan/getSpace.htm', params); // nav的空间列表
  },
  style (params) {
    return fetch('/app/mobile/spaceCommon/getDesignStyleList.htm', params); // nav的风格列表
  },
  getShape (params) {
    return fetch('/app/mobile/spaceCommon/getSpaceShape.htm', params); // nav的形状列表
  },
  fullView (params) {
    return fetch('/app/mobile/design/designPlan/getPanoPicture.htm', params); // 我的设计720°全景
  },
  getPictureList(params) {
    return fetch('/app/mobile/renderPic/getPictureList.htm', params); // 我的设计缩略图
  },
  recomGetPictureList(params) {
    return fetch('app/mobile/designPlanRecommended/getRecommendedPictureList.htm', params); // 推荐缩略图
  },
  detailsSee(params) {
    return fetch('app/mobile/design/designPlan/detailsSee.htm', params); // 获取720多点与漫游视频
  },
  getDesignPlanProductList(params) {
    return fetch('app/mobile/design/designPlan/getDesignPlanProductList.htm', params); // 获取720详情列表
  },
  getFavoritesList (params) {
    return fetch('/app/mobile/designPlanCollect/getFavoritesList.htm', params); // 获取收藏夹列表
  },
  addDesingPlanCollect (params) {
    return fetch('/app/mobile/designPlanCollect/addDesingPlanCollect.htm', params); // 添加收藏
  },
  subDesingPlanCollect (params) {
    return fetch('/app/mobile/designPlanCollect/delDesingPlanCollect.htm', params); // 取消收藏
  },
  delDesingPlanCollect(params) {
    return fetch('/app/mobile/designPlanCollect/delDesingPlanCollect.htm', params); // 取消收藏
  },
  houseType (params) {
    return fetch('/app/mobile/houseSearch/newHouseSearchWeb.htm', params); // 根据省市区搜索户型
  },
  typeArea (params) {
    return fetch('/app/mobile/spaceCommon/getSpaceAndarea.htm', params); // 获取房间类型及对应的面积范围
  },
  spaceType (params) {
    return fetch('/app/mobile/spaceCommon/newSpaceSearchWeb.htm', params); // 根据空间类型、面积搜索户型
  },
  detailType (params) {
    return fetch('/app/mobile/houseSearch/newHouseListWeb.htm', params); // 点击小区名字搜索户型
  },
  spaceLayout (params) {
    return fetch('/app/mobile/houseSearch/newHouseSpaceListWeb.htm', params); // 通过户型过滤空间布局图
  },
  newSpaceDesign (params) {
    return fetch('/app/mobile/spaceCommon/newSpaceDesignWeb.htm', params); // 通过空间搜索过滤空间布局图
  },
  getResRenderPicWeb (params) {
    return fetch('/app/mobile/renderPic/getResRenderPicWeb.htm', params); // 一键装修
  },
  submitHouse (params) {
    return fetch('/app/mobile/uploadHouse/create.htm', params); // 上传户型
  },
  getSpaceNameInHouse (params) {
    return fetch('/app/mobile/houseSearch/getSpaceNameInHouse.htm', params); // 一键装修头nav
  },
  transformAndCopyPlan (params) {
    return fetch('/app/mobile/autoRenderAndOneKeyCopy/transformAndCopyPlan.htm', params); // 渲染720接口
  },
  deductionOfPoints (params) {
    return fetch('/app/mobile/pay/deductionOfPoints.htm', params); // 720扣费接口
  },
  whetherRender (params) {
    return fetch('/app/mobile/pay/whetherRender.htm', params); // 判断是否渲染过720
  },
  findExpenseRecordList(params) {
    return fetch('/app/mobile/user/findExpenseRecordList.htm', params); // 账户消费记录
  },
  getQRCodePicUrl(params) {
    return fetch('app/mobile/renderPic/getQRCodePicUrl.htm', params); // 获取720的二维码
  },
  searchProduct(params) {
    return fetch('app/mobile/searchProduct/searchProduct.htm', params); // 查询产品库
  },
  replaceRecord(params) {
    return fetch('app/mobile/pay/replaceRecord.htm', params); // 一键替换生成订单
  },
  getMyReplaceRecord(params) {
    return fetch('app/mobile/pay/getMyReplaceRecord.htm', params); // 获取个人中心我的替换渲染任务
  },
  getAutoRender(params) {
    return fetch('app/mobile/renderPic/getAutoRender.htm', params); // 获取个人中心我的替换渲染任务
  },
  getMyMessageList(params) {
    return fetch('app/mobile/pay/getMyMessageList.htm', params); // 获取个人中心消息
  },
  getAllSysDictionary(params) {
    return fetch('app/mobile/sysDictionary/getAllSysDictionary.htm', params); // 获取所有的系统文件字典
  },
  searchProductGroup(params) {
    return fetch('app/mobile/searchProductGroup/searchProductGroup.htm', params); // 搜索组合替换的列表
  },
  findSameTypeProductList(params) {
    return fetch('app/mobile/searchProductGroup/findSameTypeProductList.htm', params); // 搜索材质的接口
  },
  searchProCategory(params) {
    return fetch('app/mobile/searchProduct/searchProCategory.htm', params); // 搜索筛选条件
  },
  deteleMyTaskAndDesign(params) {
    return fetch('app/mobile/pay/deteleMyTaskAndDesign.htm', params); // 我的任务删除我的设计实时删除
  },
  getDesignPlanHasBeCollected(params) {
    return fetch('app/mobile/designPlanCollect/getDesignPlanHasBeCollected.htm', params); // 获取该方案是否被收藏
  },
  unlockingByIntegral(params) {
    return fetch('app/mobile/unlocking/unlockingByIntegral.htm', params); // 通过积分开通移动端功能的接口
  },
  selectTextureById(params) {
    return fetch('app/mobile/searchProductGroup/selectTextureById.htm', params); // 移动端查询材质详情的接口
  },
  getWXSettings(params) {
    return fetch('app/mobile/unlocking/getWXSettings.htm', params); // 移动端获取微信支付配置的接口
  },
  selectProductById(params) {
    return fetch('app/mobile/searchProductGroup/selectProductById.htm', params); // 移动端获取原来产品材质的接口
  }
};
