/**
 * 图片遮罩效果
 * @author Pei Shaw @dao.com
 */
$.fn.extend({   
    maskimage:function(){
        var imgs = $('img',$(this));
        var slctr = this.selector;
        imgs.each(function(){
            $(this).css('position','absolute').css('z-index','8');
            $(this).after('<div mTag="mImg" class="img_alpha none" style="width: '+$(this).width()+'px; height: '+$(this).height()+'px;"/>')
        });
        
        imgs.mouseenter(function() {
            $('[mTag="mImg"]',$(this).parents(slctr)).show();
            $(this).next('[mTag="mImg"]').hide();
        });
                
        imgs.mouseleave(function() {
            $('[mTag="mImg"]',$(this).parents(slctr)).hide();
        });
    }
});   