package app.nork.task.render;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.JavaIdentifierTransformer;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import app.nork.task.render.model.RenderTaskStatus;
import app.nork.task.render.model.RenderUserInfo;
import app.nork.task.render.model.RequestCreateTaskEntity;
import app.nork.task.render.model.RequestHeadEntity;
import app.nork.task.render.model.RequestOperateTaskEntity;
import app.nork.task.render.model.RequestQueryTaskEntity;
import app.nork.task.render.model.TaskResult;

import com.nork.common.util.FileUploadUtils;
import com.nork.common.util.HttpClientUtil;
import com.nork.common.util.IgnoreJsonPropertyFilter;
import com.nork.common.util.Utils;
import com.nork.common.util.collections.Lists;
import com.nork.task.model.SysTask;
import com.nork.task.model.SysTaskStatus;
import com.nork.task.model.search.SysTaskSearch;
import com.nork.task.service.SysTaskService;



public class CreateRenderTask {
	
	private SysTaskService sysTaskService;
	
	public CreateRenderTask(){
		String[] location = {"classpath:spring/spring-mybatis.xml"};
		ApplicationContext ct = new ClassPathXmlApplicationContext(location,true); 
		if(ct!=null){
			sysTaskService = ct.getBean(SysTaskService.class);
		}
	}
	
	//通过taskCode关联瑞云任务ID号
    @SuppressWarnings({"unchecked"})
	private void HttpPostCreateTask(String taskCode,String sceneRootPath,String ProjectName) {
		try {
            String uri = "https://www5.renderbus.com/api/v1/task/";
            RequestHeadEntity headEntity = new RequestHeadEntity();
            headEntity.setAccess_key("MjAxNjEyMDgxNDIxMjE4OTA1Njg=");
            headEntity.setAccount("panda");
            headEntity.setMsg_locale("zh");
            headEntity.setAction("create_max_task");
            
    		RequestCreateTaskEntity task = new RequestCreateTaskEntity();
    		//String scenePath = "D/test/upload/C07_0022_000_385444_20161128170252422526/C07_0022_000.max";
    		String scenePath = sceneRootPath +"max/" +taskCode+".max";
    		task.setProject_name(ProjectName);
    		task.setInput_scene_path(scenePath);
    		task.setCg_soft_name("3ds Max 2014");
    		task.setFrames("1"); 
    		task.setOutput_file_name(taskCode);
    		
    		Map<String, Object> request = new HashMap<String, Object>();
    		request.put("head", headEntity);
    		request.put("body", task);
    		
    		CloseableHttpResponse response = HttpClientUtil.getResponse(uri, request);
    		 // 检验状态码，如果成功接收数据
            int code = response.getStatusLine().getStatusCode();
            //////System.out.println("============code:"+code);
            
            if (code == 200) {
	    		BufferedReader br = null;
	    		try {
	    			br = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
		            String result = br.readLine();
	                //////System.out.println("result:"+result);
	                JSONObject obj = JSONObject.fromObject(result);
	                JSONObject dataList = JSONObject.fromObject(obj.getString("body"));
	               if( dataList != null ){
	                	JSONArray jsonArray = (JSONArray) dataList.get("data");
	                	int task_total = dataList.getString("total_size")==null?0:Integer.parseInt(dataList.getString("total_size"));
	                	if( task_total > 0 ){
	                		JsonConfig jsonConfig = getJsonConfig("TaskResult");
		                	List<TaskResult> list = (List<TaskResult>)JSONArray.toCollection(jsonArray,jsonConfig);
		                	if( list!=null && list.size()>0 ){
		                		TaskResult taskResult = list.get(0);
			                	SysTask sysTask = new SysTask();
			                	sysTask.setBusinessCode(taskCode);
			                	sysTask.setIsDeleted(0);
			                	//sysTask.setState("")
			                	//渲染方式【云渲染】
			                	List<SysTask> taskList = sysTaskService.getList(sysTask);
			                	if (taskList!=null && taskList.size()==1) {
			                		SysTask st = taskList.get(0);
			                		//yun方式
			                		st.setRenderWay("ruiyun");
			                		st.setRenderTaskId(Integer.parseInt(taskResult.getTask_id()));
			                		st.setState(RenderTaskStatus.getTaskState(taskResult.getTask_status()));
			                		sysTaskService.update(st);
			                	}else{
			                	    //////System.out.println("本地任务与云渲染任务未对应！任务编码："+taskCode);
			                	}
		                	}
	                	}
	                }
	    		} finally {
	    			response.close();
	    			if(br != null) {
	    				br.close();
	    			}
	    		}
			}
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
	
	public static JsonConfig getJsonConfig(String type){
		JsonConfig jsonConfig = new JsonConfig();
		if("TaskResult".equals(type)){
			jsonConfig.setRootClass(TaskResult.class);
		}else{
			jsonConfig.setRootClass(RenderUserInfo.class);
		}
		//处理json中key的首字母大写情况
		jsonConfig.setJavaIdentifierTransformer(new JavaIdentifierTransformer() {
			@Override
			public String transformToJavaIdentifier(String s) {
				char[] chars = s.toCharArray();
				chars[0] = Character.toLowerCase(chars[0]);
				return new String(chars);
			}
		});
		//过滤在json中Entity没有的属性
		jsonConfig.setJavaPropertyFilter(new IgnoreJsonPropertyFilter());
		return jsonConfig;
	}
   
	//查询任务ID
	@SuppressWarnings({"unchecked"})
	private void HttpPostQueryTask(String taskId) {
		try {
            String uri = "https://www5.renderbus.com/api/v1/task/";
            RequestHeadEntity headEntity = new RequestHeadEntity();
            headEntity.setAccess_key("MjAxNjEyMDgxNDIxMjE4OTA1Njg=");
            headEntity.setAccount("panda");
            headEntity.setMsg_locale("zh");
            headEntity.setAction("query_task");
    		
            RequestQueryTaskEntity queryTask = new RequestQueryTaskEntity();
            queryTask.setTask_id(taskId);
    		
    		Map<String, Object> request = new HashMap<String, Object>();
    		request.put("head", headEntity);
    		request.put("body", queryTask);
    		
    		CloseableHttpResponse response = HttpClientUtil.getResponse(uri, request);
    		 // 检验状态码，如果成功接收数据
            int code = response.getStatusLine().getStatusCode();
            //////System.out.println("============code:"+code);
            
            if (code == 200) {
	    		BufferedReader br = null;
	    		try {
	    			br = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
		            String result = br.readLine();
	                //////System.out.println("result:"+result);
	                JSONObject obj = JSONObject.fromObject(result);
	                JSONObject dataList = JSONObject.fromObject(obj.getString("body"));
	                if( dataList != null ){
	                	JSONArray jsonArray = (JSONArray) dataList.get("data");
	                	JsonConfig jsonConfig = getJsonConfig("TaskResult");
	                	List<TaskResult> list = (List<TaskResult>)JSONArray.toCollection(jsonArray,jsonConfig);
	                	for(TaskResult taskResult : list){
	                		
	                		//////System.out.println("result TaskId=:"+taskResult.getTask_id()+" project_name:"+taskResult.getProject_name());
	                	}
	                }
	    		} finally {
	    			response.close();
	    			if(br != null) {
	    				br.close();
	    			}
	    		}
			}
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
	
	//操作任务状态 operateState【 0：暂停任务 1：重提任务 2：删除任务】
	@SuppressWarnings({"unchecked"})
	private void HttpPostOperateTask(String taskIds,String operateState) {
		try {
            String uri = "https://www5.renderbus.com/api/v1/task/";
            RequestHeadEntity headEntity = new RequestHeadEntity();
            headEntity.setAccess_key("MjAxNjEyMDgxNDIxMjE4OTA1Njg=");
            headEntity.setAccount("panda");
            headEntity.setMsg_locale("zh");
            headEntity.setAction("operate_task");
    		
            RequestOperateTaskEntity queryTask = new RequestOperateTaskEntity();
            queryTask.setTask_id(taskIds);
            queryTask.setOperate_order(operateState);
    		
    		Map<String, Object> request = new HashMap<String, Object>();
    		request.put("head", headEntity);
    		request.put("body", queryTask);
    		
    		CloseableHttpResponse response = HttpClientUtil.getResponse(uri, request);
    		 // 检验状态码，如果成功接收数据
            int code = response.getStatusLine().getStatusCode();
            //////System.out.println("============code:"+code);
            
            if (code == 200) {
	    		BufferedReader br = null;
	    		try {
	    			br = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
		            String result = br.readLine();
	                //////System.out.println("result:"+result);
	                JSONObject obj = JSONObject.fromObject(result);
	                JSONObject dataList = JSONObject.fromObject(obj.getString("body"));
	                if( dataList != null ){
	                	JSONArray jsonArray = (JSONArray) dataList.get("data");
	                	JsonConfig jsonConfig = getJsonConfig("TaskResult");
	                	List<TaskResult> list = (List<TaskResult>)JSONArray.toCollection(jsonArray,jsonConfig);
	                	for(TaskResult taskResult : list){
	                		if( taskResult.getOperate_result().endsWith("0") ){
	                			SysTaskSearch sysTaskSearch = new SysTaskSearch();
	                			sysTaskSearch.setRenderTaskId(Integer.parseInt(taskResult.getTask_id()));
	                			sysTaskSearch.setIsDeleted(0);
	                			sysTaskSearch.setStart(0);
	                			sysTaskSearch.setLimit(1);
	                			List<SysTask> taskList = sysTaskService.getPaginatedList(sysTaskSearch);
	                			SysTask sysTask = taskList.get(0);
	                			sysTask.setStart(Integer.parseInt(operateState));
	                			sysTaskService.update(sysTask);
	                		}else{
	                			//////System.out.println(taskResult.getDescription());
	                		}
	                		//////System.out.println("result TaskId=:"+taskResult.getTask_id()+" project_name:"+taskResult.getProject_name());
	                	}
	                }
	    		} finally {
	    			response.close();
	    			if(br != null) {
	    				br.close();
	    			}
	    		}
			}
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
	
	//查询用户信息
	@SuppressWarnings({"unchecked"})
	private void HttpPostqueryCustomer() {
		try {
            String uri = "https://www5.renderbus.com/api/v1/task/";
            RequestHeadEntity headEntity = new RequestHeadEntity();
            headEntity.setAccess_key("MjAxNjEyMDgxNDIxMjE4OTA1Njg=");
            headEntity.setAccount("panda");
            headEntity.setMsg_locale("zh");
            headEntity.setAction("query_customer");
            
            RequestQueryTaskEntity queryTask = new RequestQueryTaskEntity();
            queryTask.setLogin_name("panda");
    		Map<String, Object> request = new HashMap<String, Object>();
    		request.put("head", headEntity);
    		request.put("body", queryTask);
    		
    		CloseableHttpResponse response = HttpClientUtil.getResponse(uri, request);
    		 // 检验状态码，如果成功接收数据
            int code = response.getStatusLine().getStatusCode();
            //////System.out.println("============code:"+code);
            
            if (code == 200) {
	    		BufferedReader br = null;
	    		try {
	    			br = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
		            String result = br.readLine();
	                //////System.out.println("result:"+result);
	                JSONObject obj = JSONObject.fromObject(result);
	                JSONObject dataList = JSONObject.fromObject(obj.getString("body"));
	                if( dataList != null ){
	                	JSONArray jsonArray = (JSONArray) dataList.get("data");
	                	int task_total = dataList.getString("total_size")==null?0:Integer.parseInt(dataList.getString("total_size"));
	                	if( task_total > 0 ){
	                		JsonConfig jsonConfig = getJsonConfig("RenderUserInfo");
		                	List<RenderUserInfo> list = (List<RenderUserInfo>)JSONArray.toCollection(jsonArray,jsonConfig);
		                	RenderUserInfo user = list.get(0);
		                	//////System.out.println("用户名称："+user.getLogin_name()+";用户手机："+user.getPhone());
	                	}
	                }
	    		} finally {
	    			response.close();
	    			if(br != null) {
	    				br.close();
	    			}
	    		}
			}
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
	
	
	/**
	 * 更新渲染任务状态
	 * @return
	 * @throws Exception 
	 */
	public Object updateRenderTaskState() {
		SysTaskSearch sysTaskSearch = new SysTaskSearch();
		sysTaskSearch.setIsDeleted(0);
		sysTaskSearch.setState(SysTaskStatus.EXECUTING);
		//正在执行和等待执行
//		sysTaskSearch.setStateList(Arrays.asList(RenderTaskStatus.ANALYSING,RenderTaskStatus.ANALYSED,RenderTaskStatus.PREPROCESSING,RenderTaskStatus.START));
		//瑞云渲染方式
		sysTaskSearch.setRenderWay("ruiyun");
		sysTaskSearch.setOrders(" gmt_create asc ");
		sysTaskSearch.setLimit(1);
		List<SysTask> list = sysTaskService.getPaginatedList(sysTaskSearch);
		if( Lists.isNotEmpty(list) ){
			SysTask sysTask = list.get(0);
			if( sysTask != null && sysTask.getRenderTaskId() != null ){
				try {
		            String uri = "https://www5.renderbus.com/api/v1/task/";
		            RequestHeadEntity headEntity = new RequestHeadEntity();
		            headEntity.setAccess_key("MjAxNjEyMDgxNDIxMjE4OTA1Njg=");
		            headEntity.setAccount("panda");
		            headEntity.setMsg_locale("zh");
		            headEntity.setAction("query_task");
		    		
		            RequestQueryTaskEntity queryTask = new RequestQueryTaskEntity();
		            queryTask.setTask_id(sysTask.getRenderTaskId().toString());
		    		
		    		Map<String, Object> request = new HashMap<String, Object>();
		    		request.put("head", headEntity);
		    		request.put("body", queryTask);
		    		
		    		CloseableHttpResponse response = HttpClientUtil.getResponse(uri, request);
		    		 // 检验状态码，如果成功接收数据
		            int code = response.getStatusLine().getStatusCode();
		            //////System.out.println("============code:"+code);
		            
		            if (code == 200) {
			    		BufferedReader br = null;
			    		try {
			    			br = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
				            String result = br.readLine();
			                //////System.out.println("result:"+result);
			                JSONObject obj = JSONObject.fromObject(result);
			                JSONObject dataList = JSONObject.fromObject(obj.getString("body"));
			                if( dataList != null ){
			                	JSONArray jsonArray = (JSONArray) dataList.get("data");
			                	JsonConfig jsonConfig = getJsonConfig("TaskResult");
			                	List<TaskResult> taskList = (List<TaskResult>)JSONArray.toCollection(jsonArray,jsonConfig);
			                	for(TaskResult taskResult : taskList){
		                			sysTask.setState(RenderTaskStatus.getTaskState(taskResult.getTask_status()));
		                			sysTaskService.update(sysTask);
			                	}
			                }
			    		} finally {
			    			response.close();
			    			if(br != null) {
			    				br.close();
			    			}
			    		}
					}
		        } catch (ClientProtocolException e) {
		            e.printStackTrace();
		        } catch (IOException e) {
		            e.printStackTrace();
		        } catch (Exception e) {
		            e.printStackTrace();
		        }
			}
		}
		return null;
	}
	
	/**
	 * 下载ry任务渲染图
	 * @return
	 * @throws Exception 
	 */
	public Object downloadRenderPic() {
//		logger.info("开始检测云渲染任务状态并下载渲染图。。。");
		SysTaskSearch sysTaskSearch = new SysTaskSearch();
		sysTaskSearch.setIsDeleted(0);
		sysTaskSearch.setState(SysTaskStatus.END_OF_EXECUTION);
		//已完成
//		sysTaskSearch.setStateList(Arrays.asList(RenderTaskStatus.ANALYSED));
		//瑞云渲染方式
		sysTaskSearch.setRenderWay("ry");
		sysTaskSearch.setPicDownloadState(0);
		sysTaskSearch.setOrders(" gmt_create asc ");
		sysTaskSearch.setLimit(1);
		List<SysTask> list = sysTaskService.getPaginatedList(sysTaskSearch);
		if( Lists.isNotEmpty(list) ){
			SysTask sysTask = list.get(0);
			if( sysTask != null && sysTask.getRenderTaskId() != null ){
				String osType = FileUploadUtils.SYSTEM_FORMAT;
				String scriptPath = Utils.getPropertyName("render", "script.path", "/MaxScript/");
				String occu = Utils.getPropertyName("render", "design.designPlan.maxrender.designPlan.upload.path", "/MaxRender/");
//				RenderConfig renderConfig = renderServersMap.get(sysTask.getRenderServer());
				String renderPathPic = "D:/MaxRender" + occu + scriptPath + sysTask.getBusinessCode()+"/";
				//////System.out.println("云渲染文件路径："+renderPathPic);
				if( "windows".equals(osType) ){
					occu = occu.replace("/", "\\");
				}
				String batPath = renderPathPic+"/downloadRenderPic.bat";
//				String file = "F:/xxc/rayvision_transmitter/downloadPic.bat";
				File file = new File(batPath);
				if( file.exists() ){
		            // 读取模板内容
		            String downContext = FileUploadUtils.getFileContext(batPath);
		            // 生成脚本文件
		            String renderPicPath = "/"+sysTask.getRenderTaskId()+"_"+sysTask.getBusinessCode();
		            downContext = downContext.replaceAll("%RENDERPICPATH%", renderPicPath);
		            FileUploadUtils.writeTxtFile(batPath, downContext);
		            //////System.out.println("生成成功！");
		        }else{
		        	//////System.out.println("找不到这个目录文件："+batPath+"！");
		        	return null;
		        }
				//执行批处理
				boolean flag = HttpClientUtil.callCmd(batPath);
				/*if(!flag){
					//////System.out.println("任务"+sysTask.getId()+"渲染图下载失败！");
					sysTask.setPicDownloadState(2);
					sysTaskService.update(sysTask);
				}else{
					//////System.out.println("任务"+sysTask.getId()+"渲染图下载成功！");
					sysTask.setPicDownloadState(1);
					sysTaskService.update(sysTask);
				}*/
			}
		}
		return null;
	}
	public static void test(){
		String renderBat = "Render_ruiyun.bat";
		String renderBatTemplet = "D:/MaxRender/MaxTemplet/" + renderBat;
		String renderPath = "D:/MaxRender/test/Render.bat";
		File file = new File(renderBatTemplet);
		if( file.exists() ){
			FileUploadUtils.fileCopy(renderBatTemplet,renderPath);
			File f = new File(renderPath);
			if( f.exists() ){
				String renderContext = FileUploadUtils.getFileContext(renderPath);
				//////System.out.println("文件内容："+renderContext);
				// 读取模板内容
				renderContext = renderContext.replaceAll("\\{designPlanCode\\}", "Test");
		        // 生成脚本文件
				FileUploadUtils.writeTxtFile(renderPath, renderContext);
				//////System.out.println("文件生成成功");
			}else{
				//////System.out.println(renderPath+"文件不存在！");
			}
	    }else{
	    	//////System.out.println(renderBat+"文件不存在！");
	    }
	}
	
	
	//H01_0001_001_845579_20161129104708201804
	//D/MaxRender/MaxRender/MaxScript/H01_0001_001_845579_20161129104708201804/
	//test
	public static void main(String[] args) {
//		test();
//		String filePath = "D:/MaxRender/test/123.txt";
//		StringBuffer sbCode = new StringBuffer("11111111111111111").append("\r\n");
//		sbCode.append("22222222222222").append("\r\n").append("end");
//		boolean flag = FileUploadUtils.writeTxtFile(filePath, sbCode.toString());
//		//////System.out.println(flag);
//		Date currentTime = new Date();
//		 //////System.out.println(currentTime);
//		 Date afterDate = new Date(System.currentTimeMillis() + 60000);
//		 String endTime = Utils.formatDate(afterDate, Utils.DATE_TIME);
//		 //////System.out.print(afterDate);
//		test();
		/*String bussiness_code = "";
		String sceneRootPath = "";
		String projectName = "";
		
		if(args ==null ||args.length ==0 ){
			 bussiness_code = "F02_0031_002_810066_20161215202859832291";
			 sceneRootPath = "D/MaxRender/MaxRender/MaxScript/F02_0031_002_810066_20161215202859832291/";
			 projectName = "test";
		}else{
			if(args.length ==3){
				 bussiness_code = args[0];
				 sceneRootPath = args[1];
				 projectName = args[2];
			}else{
				//////System.out.print("参数异常!");
			}
		};*/
		CreateRenderTask renderTask = new CreateRenderTask();
//		renderTask.HttpPostCreateTask(bussiness_code,sceneRootPath,projectName);
		renderTask.HttpPostQueryTask("5196831");
//		renderTask.updateRenderTaskState();
//		renderTask.HttpPostOperateTask("5137975","0");
//		int i = renderTask.saveRanderTask(5137975);
//		renderTask.HttpPostqueryCustomer();
//		renderTask.downloadRenderPic();
		
//		try {
//			boolean b = RenderUtil.sendRequest("openbat:E01_0026_001_599781_20161219212455399326","192.168.1.224");
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
    }
		
}
