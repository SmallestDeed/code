var KRPANO;
function krpanoReady(krpano){
    KRPANO = krpano;
    var scenes = $("#sceneList").val();
    var code = $("#code").val();
    if( code == "9c0d708eb60747298c5b61f4e66ab456" ){
        var resourceUrl = "https://show.ci.sanduspace.com";
        scenes = '<scene planId="20436" name="scene_283857" title="" thumburl="'+resourceUrl+'/AA/d_userdesign/2018/04/08/16/design/designPlan/render/small/147151_20180408161937546.jpg">'
            + '<view hlookat="0" vlookat="0" fovtype="MFOV" fov="90" maxpixelzoom="0" fovmin="70" fovmax="100" limitview="range" vlookatmin="-90" vlookatmax="70"/>'
            + '<image><sphere url="'+resourceUrl+'/AA/d_userdesign/2018/04/08/16/design/designPlan/render/C09_0218_001_817669_324924.jpg"/></image>'
            + '<hotspot name="hotspot_283856" type="image" url="images/hotspot/locatingPoint.png" keep="false" ath="-81" atv="10" linkedscene="scene_283856" onclick="loadscene(\'scene_283856\')" text="卧室"/>'
            + '<hotspot name="hotspot_283854" type="image" url="images/hotspot/locatingPoint.png" keep="false" ath="98" atv="10" linkedscene="scene_283854" onclick="loadscene(\'scene_283855\')" text="厨房"/>'
            + '<hotspot name="hotspot_283855" type="image" url="images/hotspot/locatingPoint.png" keep="false" ath="-58" atv="10" linkedscene="scene_283855" onclick="loadscene(\'scene_283854\')" text="卫生间"/>'
            + '</scene>,'
            + '<scene planId="20435" name="scene_283856" title="" thumburl="'+resourceUrl+'/AA/d_userdesign/2018/04/08/14/design/designPlan/render/small/524920_20180408143649830.jpg">'
            + '<view hlookat="0" vlookat="0" fovtype="MFOV" fov="90" maxpixelzoom="0" fovmin="70" fovmax="100" limitview="range" vlookatmin="-90" vlookatmax="70"/>'
            + '<image><sphere url="'+resourceUrl+'/AA/d_userdesign/2018/04/08/14/design/designPlan/render/D04_0345_001_370057_527991.jpg"/></image>'
            + '<hotspot name="hotspot_283857" type="image" url="images/hotspot/locatingPoint.png" keep="false" ath="-115" atv="10" linkedscene="scene_283854" onclick="loadscene(\'scene_283857\')" text="客餐厅"/>'
            + '</scene>,'
            + '<scene planId="20434" name="scene_283855" title="" thumburl="'+resourceUrl+'/AA/d_userdesign/2018/04/08/14/design/designPlan/render/small/568501_20180408143602574.jpg">'
            + '<view hlookat="0" vlookat="0" fovtype="MFOV" fov="90" maxpixelzoom="0" fovmin="70" fovmax="100" limitview="range" vlookatmin="-90" vlookatmax="70"/>'
            + '<image><sphere url="'+resourceUrl+'/AA/d_userdesign/2018/04/08/14/design/designPlan/render/E03_0285_001_577126_325217.jpg"/></image>'
            + '<hotspot name="hotspot_283857" type="image" url="images/hotspot/locatingPoint.png" keep="false" ath="-123" atv="10" linkedscene="scene_283854" onclick="loadscene(\'scene_283857\')" text="客餐厅"/>'
            + '</scene>,'
            + '<scene planId="20433" name="scene_283854" title="" thumburl="'+resourceUrl+'/AA/d_userdesign/2018/04/08/14/design/designPlan/render/small/240908_20180408143522610.jpg">'
            + '<view hlookat="0" vlookat="0" fovtype="MFOV" fov="90" maxpixelzoom="0" fovmin="70" fovmax="100" limitview="range" vlookatmin="-90" vlookatmax="70"/>'
            + '<image><sphere url="'+resourceUrl+'/AA/d_userdesign/2018/04/08/14/design/designPlan/render/F02_0638_001_286325_809391.jpg"/></image>'
            + '<hotspot name="hotspot_283857" type="image" url="images/hotspot/locatingPoint.png" keep="false" ath="-55" atv="10" linkedscene="scene_283854" onclick="loadscene(\'scene_283857\')" text="客餐厅"/>'
            + '</scene>';
    }
    var sceneArray = decodeURIComponent(scenes).split(",");
    // alert(decodeURIComponent(scenes));
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