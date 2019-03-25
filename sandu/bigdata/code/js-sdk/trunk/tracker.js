(function() {
	var CookieUtil = {
		// get the cookie of the key is name
		get : function(name) {
			var cookieName = encodeURIComponent(name) + "=", cookieStart = document.cookie
					.indexOf(cookieName), cookieValue = null;
			if (cookieStart > -1) {
				var cookieEnd = document.cookie.indexOf(";", cookieStart);
				if (cookieEnd == -1) {
					cookieEnd = document.cookie.length;
				}
				cookieValue = decodeURIComponent(document.cookie.substring(
						cookieStart + cookieName.length, cookieEnd));
			}
			return cookieValue;
		},
		// set the name/value pair to browser cookie
		set : function(name, value, expires, path, domain, secure) {
			var cookieText = encodeURIComponent(name) + "="
					+ encodeURIComponent(value);

			if (expires) {
				// set the expires time
				var expiresTime = new Date();
				expiresTime.setTime(expires);
				cookieText += ";expires=" + expiresTime.toGMTString();
			}

			if (path) {
				cookieText += ";path=" + path;
			}

			if (domain) {
				cookieText += ";domain=" + domain;
			}

			if (secure) {
				cookieText += ";secure";
			}

			document.cookie = cookieText;
		},
		setExt : function(name, value) {
			this.set(name, value, new Date().getTime() + 315360000000, "/");
		}
	};

	var tracker = {
		// config
		clientConfig : {
			serverUrl : "http://bigdatadev111/log.gif",
			sessionTimeout : 360, // 360s -> 6min
			maxWaitTime : 3600, // 3600s -> 60min -> 1h
			ver : "1.0.0"
		},

		cookieExpiresTime : 315360000000, // cookie过期时间，10年

		//服务端字段对应名称
		columns : {
			userId : "uid",
			clientTime : "ct",
			eventName : "en",
			eventProperty : "ep",
			appId : "aid",
			sdk : "sdk",
			version : "ver"
		},

		cookieKeys : {
			userId : "userId",
			appId : "appId"

		},

		/**
		 * 获取userId
		 */
		getUserId : function() {
			return CookieUtil.get(this.cookieKeys.userId);
		},

		/**
		 * 设置userId
		 */
		setUserId : function(userId) {
			if (userId) {
				CookieUtil.setExt(this.cookieKeys.userId, userId);
			}
		},
		
		/**
		 * appid
		 */
		getAppId : function() {
			return CookieUtil.get(this.cookieKeys.appId);
		},

		/**
		 * 设置appid
		 */
		setAppId : function(appId) {
			if (appId) {
				CookieUtil.setExt(this.cookieKeys.appId, appId);
			}
		},
				
		
		track(eventName,eventPropertyJsonObject){
			if(!this.getUserId()){
				this.log("用户id不能为空!");
				return
			}
			if(!this.getAppId()){
				this.log("应用id不能为空!");
				return
			}
			var eventObj = {};
			var eventPropertyJsonStr = ''
			try{
				eventPropertyJsonStr = JSON.stringify(eventPropertyJsonObject)
			}catch(err){
				this.log(err);
				return;
			}
			eventObj[this.columns.eventName] = eventName;
			eventObj[this.columns.eventProperty] = eventPropertyJsonStr;
			
			this.setCommonColumns(eventObj);
			this.sendDataToServer(this.parseParam(eventObj));
			
		},

		sendDataToServer : function(data) {
			var that = this;
			var i2 = new Image(1, 1);
			i2.onerror = function() {
				
			};
			i2.src = this.clientConfig.serverUrl + "?" + data;
		},

		/**
		 * 往data中添加发送到日志收集服务器的公用部分
		 */
		setCommonColumns : function(data) {
			data[this.columns.userId] = this.getUserId(); // 设置用户id
			data[this.columns.clientTime] = new Date().getTime(); // 设置客户端时间
			data[this.columns.appId] = this.getAppId();
			data[this.columns.sdk] = "js";
			data[this.columns.version] = this.clientConfig.ver;
			
		},
		

		/**
		 * 参数编码返回字符串
		 */
		parseParam : function(data) {
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
		},

		/**
		 * 产生uuid<br/>
		 * UUID的产生逻辑，可以参考Java中UUID的生产代码
		 */
		generateId : function() {
			var chars = '0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz';
			var tmpid = [];
			var r;
			tmpid[8] = tmpid[13] = tmpid[18] = tmpid[23] = '-';
			tmpid[14] = '4';

			for (i = 0; i < 36; i++) {
				if (!tmpid[i]) {
					r = 0 | Math.random() * 16;
					tmpid[i] = chars[(i == 19) ? (r & 0x3) | 0x8 : r];
				}
			}
			return tmpid.join('');
		},

		/**
		 * 判断这个会话是否过期，查看当前时间和最近访问时间间隔时间是否小于this.clientConfig.sessionTimeout<br/>
		 * 如果是小于，返回false;否则返回true。
		 */
		isSessionTimeout : function() {
			var time = new Date().getTime();
			var preTime = CookieUtil.get(this.keys.preVisitTime);
			if (preTime) {
				// 最近访问时间存在,那么进行区间判断
				return time - preTime > this.clientConfig.sessionTimeout * 1000;
			}
			return true;
		},

		/**
		 * 更新最近访问时间
		 */
		updatePreVisitTime : function(time) {
			CookieUtil.setExt(this.keys.preVisitTime, time);
		},

		/**
		 * 打印日志
		 */
		log : function(msg) {
			console.log(msg);
		},

	};

	// 对外暴露的方法名称
	window.sd = {
		
		setUserId : function(uid) {
			tracker.setUserId(uid);
		},
		setAppId : function(appid) {
			tracker.setAppId(appid);
		},
		track : function(eventName,eventPropertyJson) {
			tracker.track(eventName,eventPropertyJson);
		}
	};

	// 自动加载方法
	var autoLoad = function() {
		sd.setUserId('u0001');
		sd.setAppId('a0001');
	};

	// 调用
	autoLoad();
})();