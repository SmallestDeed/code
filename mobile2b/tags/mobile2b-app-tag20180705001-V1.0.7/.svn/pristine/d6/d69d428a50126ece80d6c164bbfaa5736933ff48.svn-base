package app.nork.task.render.model;

import com.nork.common.util.Utils;
import com.nork.task.model.SysTaskStatus;

public class RenderTaskStatus {

    public static final String Analysing = "Analysing";// 分析中
    public static final String AnalyseFailed = "AnalyseFailed";// 分析失败
    public static final String Analysed = "Analysed";// 分析完成
    public static final String Preprocessing = "Preprocessing";// 预处理
    public static final String Start = "Start";// 开始渲染
    public static final String System_Done = "System_Done";// 渲染完成
    public static final String User_Stop = "User_Stop";// 停止
    public static final String Failed = "Failed";// 失败
    public static final String SubFailed = "SubFailed";// 提交失败
    
//    public static final Integer ANALYSING = 1;// 分析中
//    public static final Integer ANALYSED = 2;// 分析完成
//    public static final Integer ANALYSEFAILED = 3;// 分析失败
//    public static final Integer PREPROCESSING = 4;// 预处理
//    public static final Integer START = 5;// 开始渲染
//    public static final Integer SYSTEM_DONE = 6;// 渲染完成
//    public static final Integer USER_STOP = 7;// 停止
//    public static final Integer FAILED = 8;// 失败
//    public static final Integer SUBFAILED = 9;// 提交失败
    
    	
    public static Integer getTaskState(String stateName){
    	if(stateName.equals(Failed) || stateName.equals(SubFailed) || stateName.equals(AnalyseFailed)){
    		return SysTaskStatus.END_OF_EXCEPTION;// 3异常结束
    	}else if(stateName.equals(User_Stop)){
    		return SysTaskStatus.SUSPEND;//4手动暂停
    	}else if(stateName.equals(System_Done)){
    		return SysTaskStatus.RENDER_END;//10ry渲染完成
    	}else{
    		return SysTaskStatus.RY_EXECUTING;// 9RY执行中
    	}
    }
    
    public static String getTaskRemark(String stateName){
    	if(stateName.equals(User_Stop)){
    		return "[" + Utils.getCurrentDateTime(Utils.DATE_TIME) + "]:执行失败！ry任务被暂停.";
    	}else if(stateName.equals(Failed)){
    		return "[" + Utils.getCurrentDateTime(Utils.DATE_TIME) + "]:执行失败！ry任务渲染失败.";
    	}else if(stateName.equals(SubFailed)){
    		return "[" + Utils.getCurrentDateTime(Utils.DATE_TIME) + "]:执行失败！ry任务提交失败.";
    	}else if(stateName.equals(AnalyseFailed)){
    		return "[" + Utils.getCurrentDateTime(Utils.DATE_TIME) + "]:执行失败！ry渲染分析失败.";
    	}else if(stateName.equals(System_Done)){
    		return "[" + Utils.getCurrentDateTime(Utils.DATE_TIME) + "]:ry渲染成功！";
    	}else if(stateName.equals(Analysing) || stateName.equals(Analysed) || stateName.equals(Preprocessing)){
    		return "[" + Utils.getCurrentDateTime(Utils.DATE_TIME) + "]:ry渲染等待中.";
    	}else if(stateName.equals(Start)){
    		return "[" + Utils.getCurrentDateTime(Utils.DATE_TIME) + "]:ry渲染执行中.";
    	}else{
    		return "";
    	}
    }
	
}
