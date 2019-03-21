function krpanoReady(krpano){
    var code = $("#code").val();
    $.ajax({
        url : url + "/pages/vr720/getScene.htm?code="+code,
        error : function(){},
        success : function(data){
            var scene = eval("("+data+")");
            if( !scene.success ){
                alert('错误请求!');
            }else{
                // 加载场景
                krpano.call("loadxml("+scene.message+")");
                krpano.call("startup()");

                // 添加背景音乐
                var musicName = randomMusic() + 1;
                krpano.call("bgsnd_action('backgroundMusic/"+musicName+".mp3')");
            }
        }
    });
}

/**
 * 获取用户昵称、企业LOGO等基础信息
 */
function getPanoramaVo(planId){
    $.ajax({
        url : url + "/pages/vr720/getPanoramaVo.htm",
        data : {planId:planId},
        error : function(){},
        success : function(data){
            var result = eval("("+data+")");
            if( result.success ){
                var panoramaVo = result.panoramaVo;
                /*$("#userName").text(panoramaVo.userName);*/
                $("#phone").text(panoramaVo.phone);
                $("#companyName").text(panoramaVo.compnayName);
                $("#companyLogoUrl").attr("src",panoramaVo.companyLogoPath);

                // 获取设计方案产品费用清单
                getProductCost(panoramaVo.planId,panoramaVo.userId,panoramaVo.userType);
            }
        }
    });
}

/**
 * 获取设计方案产品费用清单
 */
function getProductCost(planId,userId,userType){
    $.ajax({
        url : url + "/pages/vr720/getProductsCost.htm",
        data : {planId:planId,userId:userId,userType:userType},
        error : function(){},
        success : function(data){
            $("#productCostDiv").html(data);
            // 显示产品费用清单列表按钮
            $(".productList").show();
        }
    });
}

function aaa(){
    var code = $("#code").val();
    $.ajax({
        url : url + "/pano/getScene.htm?code="+code,
        error : function(){},
        success : function(data){
            var scene = eval("("+data+")");
            if( !scene.success ){
                alert('错误请求!');
            }else{
                // 加载场景
                krpano.call("loadxml("+scene.message+")");
                krpano.call("startup()");

                // 添加背景音乐
                var musicName = randomMusic() + 1;
                krpano.call("bgsnd_action('backgroundMusic/"+musicName+".mp3')");

                // 获取用户昵称、企业LOGO等基础信息
                getPanoramaVo(krpano.get("scene[get(startscene)].planId"));
            }
        }
    });
}

function randomMusic(){
    return Math.floor(Math.random()*10)%1
}