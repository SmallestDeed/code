// 页面分享请调用此方法

function shareUrl(url,title,parms,image){
  let obj={},path='';
  //标题为空的默认为随选网
  if(title==''){
    title='随选网'
  }
  //有parms的组装url
  if(parms!='' && parms!=undefined){
    path = url + '?'+formateObjToParamStr(parms)
  }else{
    path=url
  }
  image == '' || image==undefined?obj={
    title:title,
    path:path,
    success:{},
    fail:{}
  }:obj={
    title:title,
    path:path,
    imageUrl:image,
    success:{},
    fail:{}
  }
  return obj
}
function filter(str) { // 特殊字符转义
  str += ''; // 隐式转换
  str = str.replace(/%/g, '%25');
  str = str.replace(/\+/g, '%2B');
  str = str.replace(/ /g, '%20');
  str = str.replace(/\//g, '%2F');
  str = str.replace(/\?/g, '%3F');
  str = str.replace(/&/g, '%26');
  str = str.replace(/\=/g, '%3D');
  str = str.replace(/#/g, '%23');
  return str;
}

function formateObjToParamStr(paramObj) {
  const sdata = [];
  for (let attr in paramObj) {
    sdata.push(`${attr}=${filter(paramObj[attr])}`);
  }
  return sdata.join('&');
};

module.exports = {
  shareUrl:shareUrl
}
