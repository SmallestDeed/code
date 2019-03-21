function calcTime(t) {
  var str = '';
  if (t) {
    var t1 = Date.parse(new Date(t));
    var t2 = Date.parse(new Date());
    var s = (t2 - t1);
    if (s < (86400000 * 7)) {
      var day = Math.floor(s / 86400000);
      var hour = Math.floor((s % 86400000) / 3600000);
      var minute = Math.floor((s % 86400000 % 3600000) / 60000);
      var seconds = Math.floor(s % 86400000 % 3600000 % 60000) / 1000;
      if (day > 0) {
        str = day + "天前";
        return str;
      }
      if (hour > 0) {
        str = hour + "小时前";
        return str;
      }
      if (minute > 0) {
        str = minute + "分钟前";
        return str;
      }
      if (seconds < 60) {
        str = "刚刚";
        return str;
      }
    } else {
      return t.substring(0, 16);
    }
  } else {
    return str;
  }
}
module.exports = {
  calcTime: calcTime
}