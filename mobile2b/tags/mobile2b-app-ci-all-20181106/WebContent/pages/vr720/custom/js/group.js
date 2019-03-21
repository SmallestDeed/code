var KRPANO;
function krpanoReady(krpano){
    KRPANO = krpano;
    var scenes = $("#sceneList").val();
    var sceneArray = decodeURIComponent(scenes).split(",");
    // krpano.call("showlog()");
    for( var i=0;i<sceneArray.length;i++ ){
        krpano.call("loadxml("+sceneArray[i]+")");
    }

    // 把第一个场景设置成启动场景
    if( krpano.get("startscene") == null ){
        krpano.call("copy('startscene','scene[0].name')");
    }

    // 添加背景音乐
    var musicName = randomMusic() + 1;
    krpano.call("bgsnd_action('backgroundMusic/"+musicName+".mp3')");

    // 加载第一个场景
    krpano.call("startup()");
    krpano.call("skin_startup()");
}

function randomMusic(){
    return Math.floor(Math.random()*10)%1
}

$(document).ready(function(){
    /**
     * 加载产品清单
     */
    $(".productList").click(function(){
        var planId = KRPANO.get("scene[get(xml.scene)].planId");
        var userId = $("#userId").val();
        var userType = $("#userType").val();
        // 打开的时候才查询
        if( $(".productList").attr('class') == 'productList' ){
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
    });
});