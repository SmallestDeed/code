function TimeStyle(type,value){
    if (type == 0) {
        let ts = Math.floor(value / 60 / 60 / 24).toString();
        if (ts.length <= 1) {
            ts = "0" + ts; //天
        }
        return ts
    } else if (type == 1) {
        let ss = Math.floor(value / 60 / 60 % 24).toString();
        if (ss.length <= 1) {
            ss = "0" + ss; //时
        }
        return ss
    } else if (type == 2) {
        let fs = Math.floor(value / 60 % 60).toString();
        if (fs.length <= 1) {
            fs = "0" + fs; //分
        }
        return fs
    }else if(type ==3){
        let ms = Math.floor(value % 60).toString();
        if (ms.length <= 1) {
            ms = "0" + ms; //秒
        }
          return ms
    }
}
module.exports = {
    TimeStyle
};
