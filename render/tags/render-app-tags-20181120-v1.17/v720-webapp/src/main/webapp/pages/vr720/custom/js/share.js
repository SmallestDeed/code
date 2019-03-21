$(document).ready(function(){
    var pageUrl = location.href.split('#').toString();
    $.ajax({
        type : "POST",
        url : url + "/online/share/getWXConfig.htm",
        async : false,
        data:{url : pageUrl},
        error: function(xhr, status, error) {
            alert(status);
            alert(xhr.responseText);
        },
        success : function(dataStr) {
            var data = $.parseJSON(dataStr);
            wx.config({
                debug: false,
                appId: data.appid,
                timestamp: data.timestamp,
                nonceStr: data.nonceStr,
                signature: data.signature,
                jsApiList: [
                    'checkJsApi',
                    'onMenuShareTimeline',
                    'onMenuShareAppMessage'
                ]
            });
        }
    });
});

wx.ready(function(){
    var title = $("#shareTitle").val();
    var desc = $("#shareDesc").val();
    var imgUrl = $("#shareImgUrl").val();

	// 获取“分享到朋友圈”按钮点击状态及自定义分享内容接口
	wx.onMenuShareTimeline({
		title : "三度云享家|"+title, // 分享标题
		link : location.href.split('#').toString(),
		imgUrl : imgUrl // 分享图标
	});

	// 获取“分享给朋友”按钮点击状态及自定义分享内容接口
	wx.onMenuShareAppMessage({
		title : "三度云享家|"+title, // 分享标题
		desc : desc, // 分享描述
		link : location.href.split('#').toString(),
		imgUrl : imgUrl, // 分享图标
		type : 'link' // 分享类型,music、video或link，不填默认为link
	});
});