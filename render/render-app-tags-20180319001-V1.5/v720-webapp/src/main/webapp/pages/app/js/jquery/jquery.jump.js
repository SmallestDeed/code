/**
 * Improve By Pei Shaw
 */
function JumpObj(elem, range, direction,timeOff, startFunc, endFunc) {
    var curMax = range = range || 6;
    if (direction == void 0) {
        direction = 'top';
    }else if(direction == 'horizontal'){
        direction = 'left';
    }else if(direction == 'vertical'){
        direction = 'top';
    }
    startFunc = startFunc || function(){};
    endFunc = endFunc || function(){};
    var drct = 0;
    var step = 1;

    init();

    function init() {
        $(elem).css('position','relative');
        active();        
        if (timeOff != void 0) {    
            setTimeout(function(){
                if(!drct)jump();
            },timeOff);
        }
    }
    function active() {
        elem.onmouseover = function(e) {
            if(!drct)jump()
        }            
    }

    function deactive() {
        elem.onmouseover = null
    }

    function jump() {
        var t = parseInt($(elem).css(direction));
        if (!drct) motionStart();
        else {
            var nextTop = t - step * drct;
            if (nextTop >= -curMax && nextTop <= 0) $(elem).css(direction, nextTop + 'px');
            else if(nextTop < -curMax) drct = -1;
            else {
                var nextMax = curMax / 2;
                if (nextMax < 1) {
                    motionOver();
                    return;
                }
                curMax = nextMax;
                drct = 1;
            }
        }
        setTimeout(function(){
            jump()
        }, 200 / (curMax+3) + drct * 3);
    }
    function motionStart() {
        startFunc.apply(this);
        $(elem).css(direction, '0px');   
        drct = 1;
    }
    function motionOver() {
        endFunc.apply(this);
        curMax = range;
        drct = 0;
        $(elem).css(direction,'0px');
    }

    this.jump = jump;
    this.active = active;
    this.deactive = deactive;
}