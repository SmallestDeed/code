import request from './request.js'
const goodsAPI = {
  getGoodsFullsearch(params = {}) { // 获取商品列表（全文搜索）
    return request('fullsearchUrl').post('/fullsearch-app/sxwmini/product/fuzzy/list', params)
  },
  getCompanyShopList(params = {}) { // 虎丘同城服務公司列表
    return request('shopUrl').get('/sandu/mini/companyshop/list', params)
  },
  joinPurchaseCar(params = {}) { // 加入购物车
    return request('baseUrl').post('/cart/add.htm', params)    
  },
  specificationsInfo(params = {}) { // 获取商品选择规格
    return request('baseUrl').get('/goods/basegoods/attr', params)
  },
  goodsSpecifications(params = {}) { // 获取商品规格的详细信息
    return request('baseUrl').get('/goods/basegoods/sku', params)    
  },
  getGoodsDetails(params = {}) { // 获取商品详情
    return request('baseUrl').get('/goods/basegoods/detail', params)
  },
  collectGood(params = {}) { // 收藏商品
    return request('baseUrl').get('/product/productfavorite/collectSpu', params)    
  },
  deleteCollectGood(params = {}) { // 取消收藏商品
    return request('baseUrl').get('/product/productfavorite/delCollectSpu', params)
  },
  // getGoodsRecommendPlan(params = {}) { // 获取商品匹配的方案列表
  //   return request('baseUrl').get('/goods/basegoods/design', params)    
  // },
  getGoodsRecommendPlan(params = {}) { // 获取商品匹配的方案列表
    return request('fullsearchUrl').get('/fullsearch-app/goods/recommendationplan/search/list', params)
  },
  getPurchasecarNumber(params = {}) { // 获取购物车列表
    return request('baseUrl').get('/cart/getDetail', params)
  },
  deletePurchasecar(params = {}) { // 删除某条购物车数据
    return request('baseUrl').post('/cart/del.htm', params)
  },
  getAddressList(params = {}) { // 获取用户地址列表
    return request('baseUrl').get('/order/getAddressByUserId', params)    
  },
  setUpGoodOrder(params = {}) { // 创建商品订单
    return request('baseUrl').post('/order/createorder', params)    
  },
  getGoodsPayParams(params = {}) { // 获取商品支付参数
    return request('payUrl').formData('/web/pay/miniProPayOrder/mallOrderPaying', params)    
  },
  deleteUserAddress(params = {}) { // 删除用户的地址
    return request('baseUrl').get('/order/deluseraddress', params)    
  },
  addUserAddress(params = {}) {
    return request('baseUrl').post('/order/adduseraddress', params)    
  },
  getProductCategory(params = {}) { // 获取产品筛选条件
    return request('baseUrl').get('/product/baseproduct/productcategory', params)
  },
  getCompanyDetailsGoodsList(params = {}) { // 获取品牌馆详情下面的产品列表
    return request('brandUrl').get('product/baseproduct/searchallproduct', params)
  },
  getCompanyDetails(params = {}) { // 获取公司详情
    return request('shopUrl').get('/sandu/mini/companyshop/detail', params)    
  },
  getNewGoodsList(params = {}) { // 新品商品
    return request('baseUrl').get('/mainpage/presellGoods', params)
  },
  getHotGoodsList(params = {}) { // 热售商品
    return request('baseUrl').get('/mainpage/specialOffer', params)
  }
}
export default goodsAPI
