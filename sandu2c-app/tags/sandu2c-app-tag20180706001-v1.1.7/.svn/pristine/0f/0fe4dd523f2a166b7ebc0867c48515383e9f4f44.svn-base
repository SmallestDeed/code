package com.sandu.web.task.model;

/**
 * Created by Administrator on 2016/6/6.
 */
public class SysTaskStatus {

    public static final Integer WAIT_EXECUTE = 0;// 未执行
    public static final Integer EXECUTING = 1;// 正在执行
    public static final Integer END_OF_EXECUTION = 2;// 执行结束
    public static final Integer END_OF_EXCEPTION = 3;// 异常结束
    public static final Integer SUSPEND = 4;// 手动暂停
    public static final Integer SUSPEND_BY_SYSTEM = 7;// 系统暂停
    public static final Integer TASK_TERMINATION = 5;// 任务终止
    public static final Integer TASK_DESTROYED = 6;// 任务资源已销毁
    public static final Integer READY_RENDER = 8;// ry解压完成状态
    public static final Integer RY_EXECUTING = 9;// ry正在执行    
    public static final Integer RENDER_END = 10;// ry渲染完成状态
    
    public static final Integer WAITING_EXECUTE = 11;// 等待执行中
    /**未付款任务*/
    public static final Integer NON_PAYMENT = 12;
    /**异常结束——渲染失败*/
    public static final Integer END_OF_EXCEPTION_RENDERFAILD = 13;
    /**创建渲染任务中（准备参数）*/
    public static final Integer CREATING_TASK = 99;// 创建渲染任务中（准备参数）

    
    /**
     * 待渲染：websocket通知APP渲染之前状态
     */
    public static final Integer WAITING_RENDER = 14;
    /**
     * 渲染中:websocket已通知APP渲染，到保存渲染图之前。如果没图片回调，则30分钟内都是这个状态
     */
    public static final Integer RENDERING = 15;
    /**
     * 渲染失败:1、超过30分钟未回调保存渲染图接口；2、保存渲染图接口异常；3、websocket通知APP渲染通知失败；
     */
    public static final Integer RENDER_FAILD = 16;
    /**
     * 渲染结束:1、图片保存成功。
     */
    public static final Integer END_OF_RENDER = 17;
    
//    WAIT_EXECUTE(0,"等待执行"),EXECUTING(1,"正在执行"),END_OF_EXECUTION(2,"执行结束"),END_OF_EXCEPTION(3,"异常结束"),SUSPEND(4,"暂停"),TASK_TERMINATION(5,"任务终止");
//
//    private String name;
//    private Integer value;
//
//    SysTaskStatus(Integer value,String name){
//        this.name = name;
//        this.value = value;
//    }
//
//    public static String getName(Integer value){
//        for( SysTaskStatus status : SysTaskStatus.values() ){
//            if( status.value == value ){
//                return status.getName();
//            }
//        }
//        return null;
//    }
//	
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public Integer getValue() {
//        return value;
//    }
//
//    public void setValue(Integer value) {
//        this.value = value;
//    }
}
