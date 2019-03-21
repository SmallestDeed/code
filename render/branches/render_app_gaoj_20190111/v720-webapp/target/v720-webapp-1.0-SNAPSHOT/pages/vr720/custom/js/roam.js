function krpanoReady(krpano){
    var scenes = $("#sceneList").val();
    var sceneArray = decodeURIComponent(scenes).split(",");
   /* krpano.call("showlog()");
    alert(sceneArray);*/
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
}

function randomMusic(){
    return Math.floor(Math.random()*10)%1
}