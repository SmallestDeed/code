/*!
 * jquery.fn.drag
 * @author ydr.me
 * @version 1.2
 * 2014�?7�?2�?17:27:42
 */



/**
 * v1.0
 * 2013�?9�?22�?13:59:33
 * 构� 
 *
 * v1.1
 * 2013-11-23 16:24:47
 * 改进
 *
 * v1.2
 * 优化，OOP
 * 增加对触屏长按拖拽的支持选项
 *
 */



(function(win, udf) {

    'use strict';

    var $ = win.$,
        doc = win.document,
        $doc = $(doc),
        $body = $(doc.body),
        datakey = 'jquery-drag',
        defaults = {
            // 鼠标操作区域选择器，默认为this
            // 参数为选择器字符串
            handle: null,

            // 鼠标拖拽时，移动的目标，即从handle开始查找最近匹配的祖先元素
            // 默认为this
            // 参数为选择器字符串
            drag: null,

            // 拖拽轴向，x：水平，y：垂直，xy：所�?
            axis: 'xy',

            // 鼠标形状，为空时将不会自动设�?
            cursor: 'move',

            // 拖拽对象的最小位置，格式为{left: 10, top: 10}
            min: null,

            // 拖拽对象的最大位置，格式为{left: 1000, top: 1000}
            max: null,

            // 拖拽时的层级�?
            zIndex: 9999,

            // 拖拽开始前回调
            // this: drag element
            // arg0: event
            // arg1: instance
            ondragbefore: $.noop,

 
            // 拖拽开始后回调
            // this: drag element
            // arg0: event
            // arg1: instance
            ondragstart: $.noop,

            // 拖拽中回�?
            // this: drag element
            // arg0: event
            // arg1: instance
            ondrag: $.noop,

            // 拖拽结束后回�?
            // this: drag element
            // arg0: event
            // arg1: instance
            ondragend: $.noop
        };

    $.fn.drag = function(settings) {
        // 当前�?1个参数为字符�?
        var run = $.type(settings) === 'string',
            // 获取运行方法时的其他参数
            args = [].slice.call(arguments, 1),
            // 复制默认配置
            options = $.extend({}, defaults),
            // 运行实例化方法的元素
            $element,
            // 实例化对�?
            instance;

        // 运行实例化方法，�?1个字符不能是“_�?
        // 下划线开始的方法皆为私有方法
        if (run && run[0] !== '_') {
            if (!this.length) return;

            // 只取集合中的�?1个元� 
            $element = $(this[0]);

            // 获取保存的实例化对象
            instance = $element.data(datakey);

            // 若未保存实例化对象，则先保存实例化对�?
            if (!instance) $element.data(datakey, instance = new Constructor($element[0], options)._init());

            // 防止与静态方法重合，只运行原型上的方�?
            // 返回原型方法结果，否则返回undefined
            return Constructor.prototype[settings] ? Constructor.prototype[settings].apply(instance, args) : udf;
        }
        // instantiation options
        else if (!run) {
            // 合并参数
            options = $.extend(options, settings);
        }

        return this.each(function() {
            var element = this,
                instance = $(element).data(datakey);

            // 如果没有保存实例
            if (!instance) {
                // 保存实例
                $(element).data(datakey, instance = new Constructor(element, options)._init());
            }
        });
    };
    $.fn.drag.defaults = defaults;


    function Constructor(element, options) {
        this.element = element;
        this.options = options;
    }

    Constructor.prototype = {
        /**
         * 初始�?
         * @return this
         * @version 1.0
         * 2014�?7�?3�?18:29:40
         */
        _init: function() {
            var the = this,
                options = the.options,
                $element = $(the.element);

            the.$element = $element;

            // 采用事件代理
            if (options.handle) {
                $element.on('mousedown taphold', options.handle, $.proxy(the._start, the));
            } else {
                $element.on('mousedown taphold', $.proxy(the._start, the));
            }

            $doc.mousemove($.proxy(the._move, the))
                .mouseup($.proxy(the._end, the))
                .bind('touchmove', $.proxy(the._move, the))
                .bind('touchend', $.proxy(the._end, the))
                .bind('touchcancel', $.proxy(the._end, the));

            return the;
        },



        /**
         * 拖拽开始回�?
         * @param {Object} e event
         * @return undefined
         * @version 1.0
         * 2014�?7�?3�?18:29:40
         */
        _start: function(e) {
            if (!this.is) {
                e = e.originalEvent;
                e.preventDefault();
                
                var the = this,
                    options = the.options,
                    $element = the.$element,
                    $handle = options.handle ? $(e.target).closest(options.handle) : $(e.target),
                    $drag = options.drag ? $handle.closest(options.drag) : $element,
                    cssPos,
                    offset,
                    te = e.touches ? e.touches[0] : e;

                if (!$element.has($drag).length) $drag = $element;

                the.$drag = $drag;
                options.ondragbefore.call($drag[0], e, the);

                the.zIndex = $drag.css('z-index');
                the.cursor = $body.css('cursor');
                the.$drag = $drag.css('z-index', options.zIndex);
                cssPos = $drag.css('position');
                offset = $drag.offset();

                if (cssPos === 'static') {
                    $drag.css('position', 'relative');
                }
                // 不是相对�? static �?
                else if (cssPos === 'fixed' || cssPos === 'absolute') {
                    $drag.css($drag.position());
                }


                the.pos = {
                    x: te.pageX,
                    y: te.pageY,
                    l: offset.left,
                    t: offset.top
                };
                the.is = !0;
                if (the.options.cursor) $body.css('cursor', options.cursor);

                options.ondragstart.call($drag[0], e, the);
            }
        },




        /**
         * 拖拽移动回调
         * @param {Object} e event
         * @return undefined
         * @version 1.0
         * 2014�?7�?3�?18:29:40
         */
        _move: function(e) {
            if (this.is) {
                e = e.originalEvent;
                e.preventDefault();

                var the = this,
                    options = the.options,
                    min = options.min,
                    max = options.max,
                    pos = the.pos,
                    $drag = the.$drag,
                    offset = $drag.parent(!0).offset(),
                    minLeft, minTop, maxLeft, maxTop,
                    to = {},
                    te = e.touches ? e.touches[0] : e;


                // axis
                if (~options.axis.indexOf('x')) to.left = te.pageX - pos.x + pos.l;
                if (~options.axis.indexOf('y')) to.top = te.pageY - pos.y + pos.t;

                // min
                if (min && min.left !== udf) {
                    if (to.left < (minLeft = min.left + offset.left)) to.left = minLeft;
                }
                if (min && min.top !== udf) {
                    if (to.top < (minTop = min.top + offset.top)) to.top = minTop;
                }

                // max
                if (max && max.left !== udf) {
                    if (to.left > (maxLeft = max.left + offset.left)) to.left = maxLeft;
                }
                if (max && max.top !== udf && to.top > max.top) {
                    if (to.top > (maxTop = max.top + offset.top)) to.top = maxTop;
                }

                $drag.offset(to);
                options.ondrag.call($drag[0], e, the);
            }
        },



        /**
         * 拖拽结束回调
         * @param {Object} e event
         * @return undefined
         * @version 1.0
         * 2014�?7�?3�?18:29:40
         */
        _end: function(e) {
            if (this.is) {
                var the = this,
                    $drag = the.$drag;

                e.preventDefault();
                the.is = !1;
                if (the.options.cursor) $body.css('cursor', the.cursor);
                $drag.css('z-index', the.zIndex);
                the.options.ondragend.call($drag[0], e, the);
            }
        },



        /**
         * 设置或获取选项
         * @param  {String/Object} key 键或键值对
         * @param  {*}             val �?
         * @return 获取时返回键值，否则返回this
         * @version 1.0
         * 2014�?7�?3�?20:08:16
         */
        options: function(key, val) {
            // get
            if ($.type(key) === 'string' && val === udf) return this.options[key];

            var map = {};
            if ($.type(key) === 'object') map = key;
            else map[key] = val;

            this.options = $.extend(this.options, map);
        }
    };
})(this);
