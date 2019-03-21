import designAPI from './design-api.js'
import myselfAPI from './myself-api.js'
import commonAPI from './common-api.js'
import indexAPI from './index-api.js'
import orderAPI from './order-api.js'
const API = Object.assign(designAPI, myselfAPI, commonAPI, indexAPI, orderAPI)
export default API
