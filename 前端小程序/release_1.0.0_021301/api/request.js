import { basePath } from '../utils/config.js'
import utils from '../utils/utils.js'
let mobileTobArr = ['systemUrl', 'coreUrl', 'shopUrl'], platformCode = 'miniProgram'

 const request = (base) => {
   utils.myFindIndex(mobileTobArr, (n) => { return n == base }) !== -1 ? platformCode = 'mobile2b' : platformCode = 'miniProgram'
   return {
     get(url, params, loading) {
       return utils.requestFn({
         base: base,
         url: url, 
         params: params,
         type: 'get',
         contentType: 'application/json',
         platformCode: platformCode,
         loading
       })
     },
     post(url, params) {
       return utils.requestFn({
         base: base,
         url: url,
         params: params,
         type: 'post',
         contentType: 'application/json',
         platformCode: platformCode
       })
     },
     formData(url, params) {
       let ret = ''
       for (let key in params) { ret += encodeURIComponent(key) + '=' + encodeURIComponent(params[key]) + '&' }
       return utils.requestFn({
         base: base,
         url: url,
         params: ret,
         type: 'post',
         contentType: 'application/x-www-form-urlencoded',
         platformCode: platformCode
       })
     },
     uploadFile(url, params) {
       return utils.uploadFileFn({
         base: base,
         url: url,
         params: params,
         contentType: 'multipart/form-data',
         platformCode: platformCode
       })
     },
   }
 }
module.exports = request