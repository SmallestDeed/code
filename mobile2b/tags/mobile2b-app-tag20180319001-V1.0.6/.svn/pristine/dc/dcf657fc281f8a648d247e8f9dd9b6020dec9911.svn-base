package app.test;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import com.nork.common.properties.ResProperties;
import com.nork.common.util.Utils;
import com.nork.design.controller.web.IntelligenceDecorationController;
import com.nork.design.controller.web.IntelligenceDecorationController.PosNameInfo;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class TestDemoCreatedByHuangsongbo {

	@Test
	public void test001() {
		//System.out.println(Utils.getValueByFileKey(ResProperties.RES, ResProperties.DESIGNPLAN_USEDCONFIG_FILEKEY, ""));
	}
	
	@Test
	public void test002() {
		System.out.println(StringUtils.equals(null, null));
	}
	
	@Test
	public void test003() {
		List<PosNameInfo> posNameInfoList = new ArrayList<IntelligenceDecorationController.PosNameInfo>();
		String posNameInfoJson = "[{\"posName\":\"dim0\",\"deignPlanProductId\":2070710},{\"posName\":\"dim1\",\"deignPlanProductId\":2070711},{\"posName\":\"dim2\",\"deignPlanProductId\":2070712},{\"posName\":\"qiangm0\",\"deignPlanProductId\":2070713},{\"posName\":\"qiangm1\",\"deignPlanProductId\":2070714},{\"posName\":\"qiangm2\",\"deignPlanProductId\":2070715},{\"posName\":\"qiangm3\",\"deignPlanProductId\":2070716},{\"posName\":\"qiangm4\",\"deignPlanProductId\":2070717},{\"posName\":\"qiangm5\",\"deignPlanProductId\":2070718},{\"posName\":\"meng0\",\"deignPlanProductId\":2070719},{\"posName\":\"bd0\",\"deignPlanProductId\":2070720},{\"posName\":\"bd20\",\"deignPlanProductId\":2070721},{\"posName\":\"bd30\",\"deignPlanProductId\":2070722},{\"posName\":\"ch0\",\"deignPlanProductId\":2070723},{\"posName\":\"ca0\",\"deignPlanProductId\":2070724},{\"posName\":\"li0\",\"deignPlanProductId\":2070725},{\"posName\":\"ca1\",\"deignPlanProductId\":2070726},{\"posName\":\"li1\",\"deignPlanProductId\":2070727},{\"posName\":\"ho0\",\"deignPlanProductId\":2070728},{\"posName\":\"ho1\",\"deignPlanProductId\":2070729},{\"posName\":\"pe0\",\"deignPlanProductId\":2070730}]";
		JSONArray jsonArray = JSONArray.fromObject(posNameInfoJson);
		for (int index = 0; index < jsonArray.size(); index++) {
			/*PosNameInfo posNameInfo = (PosNameInfo) JSONObject.toBean(jsonArray.getJSONObject(index), PosNameInfo.class);*/
			PosNameInfo posNameInfo = new IntelligenceDecorationController().new PosNameInfo();
			posNameInfo.setPosName(jsonArray.getJSONObject(index).getString("posName"));
			posNameInfo.setDeignPlanProductId(jsonArray.getJSONObject(index).getInt("deignPlanProductId"));
			posNameInfoList.add(posNameInfo);
		}
		System.out.println(posNameInfoList);
	}
	
	
	
}
