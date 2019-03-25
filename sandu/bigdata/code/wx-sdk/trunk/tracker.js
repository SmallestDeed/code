!function (func) {
  module.exports = func();
}

(function () {

	var b = "",Q = "",Y = {},
	clientConfig = {
			serverUrl : "http://bigdatadev111/log.gif",
			sessionTimeout : 360, // 360s -> 6min
			maxWaitTime : 3600, // 3600s -> 60min -> 1h
			sdk:"wx",
			ver : "1.0.0"
		},
		
	columns = {
		userId : "uid",
		clientTime : "ct",
		eventName : "en",
		eventProperty : "ep",
		appId : "aid",
		sdk : "sdk",
		version : "ver"
	},
	
	storageKeys = {
			userId : "userId",
			appId : "appId"
	};
		
	function tracker(app) {
		this.app = app
	}
	
	function constructFunc() {
		this.sd = new tracker(this)
	}
	
	
	
	function initFunc(myApp, eventMethod, constructFunc) {
		if (myApp[eventMethod]) {
			var eventFunc = myApp[eventMethod];
			myApp[eventMethod] = function (eventMethodParam) {
				constructFunc.call(this),
				eventFunc.call(this, eventMethodParam)				
			}
		} else
			myApp[eventMethod] = function (eventMethodParam) {
				constructFunc.call(this)
			}
	}
	
	/**
	 * 获取userId
	 */
	function getUserId() {
		return wx.getStorageSync(storageKeys.userId)
	}

	/**
	 * 设置userId
	 */
	function setUserId(userId) {
		if (userId) {
			wx.setStorageSync(storageKeys.userId, userId)
		}
	}
	
	/**
	 * appid
	 */
	function getAppId() {
		return wx.getStorageSync(storageKeys.appId)
	}

	/**
	 * 设置appid
	 */
	function setAppId(appId) {
		if (appId) {
			wx.setStorageSync(storageKeys.appId, appId)
		}
	}
	
	
	
	
	function setCommonColumns(data) {
		data[columns.userId] = getUserId(); // 设置用户id
		data[columns.clientTime] = new Date().getTime(); // 设置客户端时间
		data[columns.appId] = getAppId();
		data[columns.sdk] = clientConfig.sdk;
		data[columns.version] = clientConfig.ver;
	}
	
	
	
	function parseParam(data) {
		var params = "";
		for ( var e in data) {
			if (e && data[e]) {
				// 对key和value进行编码操作
				params += encodeURIComponent(e) + "="
						+ encodeURIComponent(data[e]) + "&";
			}
		}
		if (params) {
			return params.substring(0, params.length - 1);
		} else {
			return params;
		}
	}
	
	function sendDataToServer(params) {
		new Promise(function (t, e) {
			let reqUrl = clientConfig.serverUrl+'?'+params
			console.log(reqUrl);
			//console.log(clientConfig.serverUrl+'?'+params);
			wx.request({
				url: reqUrl,
				method: "GET",
				success: function (n) {
					//success
				},
				fail: function () {
					console.error("request log server fail!")
				}
			})
			
		})
	}
	

	tracker.prototype.track = function (eventName, eventPropertyJsonObject) {
		if(!getUserId()){
			console.log("用户id不能为空!");
			return
		}
		if(!getAppId()){
			console.log("应用id不能为空!");
			return
		}
		var eventObj = {}
		var eventPropertyJsonStr = ''
		try{
			eventPropertyJsonStr = JSON.stringify(eventPropertyJsonObject)
		}catch(err){
			console.log(err)
			return
		}
		eventObj[columns.eventName] = eventName
		eventObj[columns.eventProperty] = eventPropertyJsonStr
		
		setCommonColumns(eventObj)
		sendDataToServer(parseParam(eventObj))
	},
	tracker.prototype.init = function (userId,appId) {
		if(!userId){
			console.log("用户id不能为空!");
			return
		}
		if(!appId){
			console.log("应用id不能为空!");
			return
		}
		setUserId(userId);
		setAppId(appId);
    };

  return function (n) {
		!function () {
			var app = App;
			App = function (appParam) {
				console.log(appParam)
				initFunc(appParam, "onLaunch", constructFunc)
				return app(appParam)
			}
		}()
	}()

});