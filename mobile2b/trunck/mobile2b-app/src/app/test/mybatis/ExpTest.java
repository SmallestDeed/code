package app.test.mybatis;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import com.nork.base.service.BaseService;
import com.nork.base.service.impl.JsonDataServiceImpl;
import com.nork.demo.dao.ExpMapper;
import com.nork.demo.model.Exp;
import com.nork.demo.service.ExpService;
import com.nork.task.service.SysTaskService;


@RunWith(SpringJUnit4ClassRunner.class)
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class })
@ContextConfiguration(locations = { "classpath:spring/spring.xml",
		"classpath:spring/spring-mybatis.xml",
		"classpath:spring/spring-servlet.xml" })
public class ExpTest {
	private static ResourceBundle app = ResourceBundle.getBundle("app");
	
	private static Logger logger = Logger.getLogger(ExpTest.class);
	@Autowired
	private ExpService expService;

	@Autowired
	private ExpMapper expMapper;

	@Autowired
	private BaseService baseService;
	
	@Autowired
	private SysTaskService sysTaskService;

	private MockHttpServletRequest request;
	private MockHttpServletResponse response;
	private MockMultipartFile multipartFile;

	@Before
	public void setUp() {
		request = new MockHttpServletRequest();
		request.setCharacterEncoding("UTF-8");
		response = new MockHttpServletResponse();
	}

	@Test
	public void add() throws Exception{
		String str="{\"id\":10,\"sysCode\":null}";
		//{"id":10,"sysCode":null,"remark":null}
		Exp exp = new Exp();
		exp.setId(10);
		exp.setStrAtt("test");
		exp.setIntAtt(22);
		exp.setDoubleAtt(2.11);
		
		//String json= exp.encode();
		String s = new JsonDataServiceImpl().getBeanToJsonData(exp);
		Exp exp2 = (Exp)new JsonDataServiceImpl().getJsonToBean(s,Exp.class);
		
		//////System.out.println("id="+ exp2.getId());
		//////System.out.println("strAtt="+exp2.getStrAtt());
		//////System.out.println("intAtt="+exp2.getIntAtt());
		//////System.out.println("doubleAtt="+exp2.getDoubleAtt());
		//////System.out.println("remark="+ (exp2.getRemark()==null));
		//////System.out.println("BeanToJsonData=" + s);
		//exp.decode(str);
		//expService.add(exp);
	}

		
	@Test
	public void test() {
		//System.out.println(1111);
		Integer batchNum = sysTaskService.getRenderBatchSum();
		//System.out.println("---------"+batchNum);
/*		TreeMap map = new TreeMap();
		map.put("_table_or_view_", "view_position_devices");
		map.put("_where_", "1=1");
		map.put("orders", "device_id asc");
		map.put("start", "1");
		map.put("limit", "10");
		try {
			int count = expMapper.getDataCount(map);
			//////System.out.print("count=" + count);

			List<TreeMap> list = expMapper.getDataMap(map);
			//////System.out.print("list=" + list.size());
		} catch (Exception e) {
			e.printStackTrace();
		}*/
	}


	public void sort() {
		
		Exp exp1 = new Exp();
		Exp exp2 = new Exp();
		exp1.setId(5);
		exp2.setId(6);
		List<Exp> list = new ArrayList<Exp>();
		list.add(exp1);
		list.add(exp2);
		ComparatorT2 comparator = new ComparatorT2();
		Collections.sort(list, comparator);
		for(Exp e:list){
			//////System.out.print(e.getId());
		}

	}

	public class ComparatorT implements Comparator {
		public int compare(Object arg0, Object arg1) {
			Exp a0 = (Exp) arg0;
			Exp a1 = (Exp) arg1;
			int flag = a0.getId().compareTo(a1.getId());
			if (flag == 0) {
				return a0.getId().compareTo(a1.getId());
			} else {
				return flag;
			}
		}
	}
	
	public class ComparatorT2 implements Comparator {
		public int compare(Object arg0, Object arg1) {
			Exp a0 = (Exp) arg0;
			Exp a1 = (Exp) arg1;
			int flag = a1.getId().compareTo(a0.getId());
			if (flag == 0) {
				return a1.getId().compareTo(a0.getId());
			} else {
				return flag;
			}
		}
	}
	
	
}
