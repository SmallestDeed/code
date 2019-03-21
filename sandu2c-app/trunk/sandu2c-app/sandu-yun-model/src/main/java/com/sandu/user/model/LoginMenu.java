package com.sandu.user.model;

import com.sandu.common.model.Mapper;

import java.io.Serializable;


public class LoginMenu extends Mapper implements Serializable {

    private static final long serialVersionUID = 2712880546833875417L;
    private Integer id;// 数字主键
    private String longCode;
    private Integer parentId;
    private Integer levelId;
    private String name;
    private String url;
    private Integer sequence;
    private String keyWord;


    public Integer getSequence() {
        return sequence;
    }

    public void setSequence(Integer sequence) {
        this.sequence = sequence;
    }

    public String getKeyWord() {
        return keyWord;
    }

    public void setKeyWord(String keyWord) {
        this.keyWord = keyWord;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLongCode() {
        return longCode;
    }

    public void setLongCode(String longCode) {
        this.longCode = longCode;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public Integer getLevelId() {
        return levelId;
    }

    public void setLevelId(Integer levelId) {
        this.levelId = levelId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public LoginMenu copy() {
        LoginMenu obj = new LoginMenu();
        obj.setId(this.id);
        obj.setLongCode(this.longCode);
        obj.setParentId(this.parentId);
        obj.setLevelId(this.levelId);
        obj.setName(this.name);
        obj.setUrl(this.url);
        return obj;
    }

		/*public List<LoginMenu> getMenulist(){
			List<LoginMenu> menulist = new ArrayList<LoginMenu>();
			
			LoginMenu menu1 = new LoginMenu();
			LoginMenu menu2 = new LoginMenu();
			LoginMenu menu3 = new LoginMenu();
			LoginMenu menu4 = new LoginMenu();
			LoginMenu menu5 = new LoginMenu();
			LoginMenu menu6 = new LoginMenu();
			LoginMenu menu7 = new LoginMenu();
			LoginMenu menu8 = new LoginMenu();
			LoginMenu menu9 = new LoginMenu();
			LoginMenu menu10 = new LoginMenu();
			LoginMenu menu11 = new LoginMenu();
			LoginMenu menu12 = new LoginMenu();
			LoginMenu menu13 = new LoginMenu();
			LoginMenu menu14 = new LoginMenu();
			LoginMenu menu15 = new LoginMenu();
			LoginMenu menu16 = new LoginMenu();
			LoginMenu menu17 = new LoginMenu();
			LoginMenu menu18 = new LoginMenu();
			LoginMenu menu19 = new LoginMenu();
			LoginMenu menu20 = new LoginMenu();
			LoginMenu menu21 = new LoginMenu();
			LoginMenu menu22 = new LoginMenu();
			LoginMenu menu23 = new LoginMenu();
			LoginMenu menu24 = new LoginMenu();
			LoginMenu menu25 = new LoginMenu();
			LoginMenu menu26 = new LoginMenu();
			LoginMenu menu27 = new LoginMenu();
			LoginMenu menu28 = new LoginMenu();
			LoginMenu menu29 = new LoginMenu();
			LoginMenu menu30 = new LoginMenu();
			LoginMenu menu31 = new LoginMenu();
			LoginMenu menu32 = new LoginMenu();
			LoginMenu menu33 = new LoginMenu();
			LoginMenu menu34 = new LoginMenu();
			LoginMenu menu35 = new LoginMenu();
			LoginMenu menu36 = new LoginMenu();
			LoginMenu menu37 = new LoginMenu();
	
	 
			menu1.setId(1);menu1.setParentId(0);menu1.setLevelId(1);menu1.setName("首       页");menu1.setUrl("");
			menu2.setId(2);menu2.setParentId(1);menu2.setLevelId(2);menu2.setName("系统公告");menu2.setUrl("");
			menu3.setId(3);menu3.setParentId(2);menu3.setLevelId(3);menu3.setName("公告列表");menu3.setUrl("");
			menu4.setId(4);menu4.setParentId(1);menu4.setLevelId(2);menu4.setName("站内消息");menu4.setUrl("");
			menu5.setId(5);menu5.setParentId(4);menu5.setLevelId(3);menu5.setName("我的消息");menu5.setUrl("");
			menu6.setId(6);menu6.setParentId(0);menu6.setLevelId(1);menu6.setName("系统管理");menu6.setUrl("");
			menu7.setId(7);menu7.setParentId(6);menu7.setLevelId(2);menu7.setName("系统管理");menu7.setUrl("");
			menu8.setId(8);menu8.setParentId(7);menu8.setLevelId(3);menu8.setName("用户列表");menu8.setUrl("/system/sysUser/jsplist.htm");
			menu9.setId(9);menu9.setParentId(7);menu9.setLevelId(3);menu9.setName("角色列表");menu9.setUrl("/system/sysRole/jsplist.htm");
			menu10.setId(10);menu10.setParentId(7);menu10.setLevelId(3);menu10.setName("菜单列表");menu10.setUrl("/system/sysFunc/jspmain.htm");
			menu11.setId(11);menu11.setParentId(7);menu11.setLevelId(3);menu11.setName("组织列表");menu11.setUrl("/system/sysGroup/jspmain.htm");
			menu12.setId(12);menu12.setParentId(7);menu12.setLevelId(3);menu12.setName("数据字典");menu12.setUrl("/system/sysDictionary/jsplist.htm");
			menu13.setId(13);menu13.setParentId(7);menu13.setLevelId(3);menu13.setName("区域管理");menu13.setUrl("");
			menu14.setId(14);menu14.setParentId(0);menu14.setLevelId(1);menu14.setName("业务管理");menu14.setUrl("");
			menu15.setId(15);menu15.setParentId(14);menu15.setLevelId(2);menu15.setName("小区管理");menu15.setUrl("");
			menu16.setId(16);menu16.setParentId(15);menu16.setLevelId(3);menu16.setName("小区列表");menu16.setUrl("");
			menu17.setId(17);menu17.setParentId(14);menu17.setLevelId(2);menu17.setName("户型管理");menu17.setUrl("");
			menu18.setId(18);menu18.setParentId(17);menu18.setLevelId(3);menu18.setName("户型列表");menu18.setUrl("/system/resPicHouse/jsplist.htm");
			menu19.setId(19);menu19.setParentId(17);menu19.setLevelId(3);menu19.setName("通用户型");menu19.setUrl("/business/baseHouse/jsplist.htm");
			menu20.setId(20);menu20.setParentId(0);menu20.setLevelId(1);menu20.setName("商品管理");menu20.setUrl("");
			menu21.setId(21);menu21.setParentId(20);menu21.setLevelId(2);menu21.setName("商品分类");menu21.setUrl("");
			menu22.setId(22);menu22.setParentId(21);menu22.setLevelId(3);menu22.setName("企业管理");menu22.setUrl("");
			menu23.setId(23);menu23.setParentId(21);menu23.setLevelId(3);menu23.setName("品牌管理");menu23.setUrl("");
			menu24.setId(24);menu24.setParentId(21);menu24.setLevelId(3);menu24.setName("产品列表");menu24.setUrl("");
			menu25.setId(25);menu25.setParentId(0);menu25.setLevelId(1);menu25.setName("资源管理");menu25.setUrl("");
			menu26.setId(26);menu26.setParentId(25);menu26.setLevelId(2);menu26.setName("模型管理");menu26.setUrl("");
			menu27.setId(27);menu27.setParentId(26);menu27.setLevelId(3);menu27.setName("模型列表");menu27.setUrl("");
			menu28.setId(28);menu28.setParentId(25);menu28.setLevelId(2);menu28.setName("图片管理");menu28.setUrl("");
			menu29.setId(29);menu29.setParentId(28);menu29.setLevelId(3);menu29.setName("图片列表");menu29.setUrl("");
			menu30.setId(30);menu30.setParentId(25);menu30.setLevelId(2);menu30.setName("文件管理");menu30.setUrl("");
			menu31.setId(31);menu31.setParentId(30);menu31.setLevelId(3);menu31.setName("文件列表");menu31.setUrl("");
			menu32.setId(32);menu32.setParentId(0);menu32.setLevelId(1);menu32.setName("项目开发");menu32.setUrl("");
			menu33.setId(33);menu33.setParentId(32);menu33.setLevelId(2);menu33.setName("开发参考");menu33.setUrl("");
			menu34.setId(34);menu34.setParentId(33);menu34.setLevelId(3);menu34.setName("demo例子");menu34.setUrl("/demo/exp/jsplist.htm");
			menu35.setId(35);menu35.setParentId(33);menu35.setLevelId(3);menu35.setName("通用查询");menu35.setUrl("");
			menu36.setId(36);menu36.setParentId(32);menu36.setLevelId(2);menu36.setName("接口测试");menu36.setUrl("");
			menu37.setId(37);menu37.setParentId(36);menu37.setLevelId(3);menu37.setName("exp测试");menu37.setUrl("/client/routermain.htm?methods=demo.exp.list");
	 

	
			menulist.add(menu1);
			menulist.add(menu2);
			menulist.add(menu3);
			menulist.add(menu4);
			menulist.add(menu5);
			menulist.add(menu6);
			menulist.add(menu7);
			menulist.add(menu8);
			menulist.add(menu9);
			menulist.add(menu10);
			menulist.add(menu11);
			menulist.add(menu12);
			menulist.add(menu13);
			menulist.add(menu14);
			menulist.add(menu15);
			menulist.add(menu16);
			menulist.add(menu17);
			menulist.add(menu18);
			menulist.add(menu19);
			menulist.add(menu20);
			menulist.add(menu21);
			menulist.add(menu22);
			menulist.add(menu23);
			menulist.add(menu24);
			menulist.add(menu25);
			menulist.add(menu26);
			menulist.add(menu27);
			menulist.add(menu28);
			menulist.add(menu29);
			menulist.add(menu30);
			menulist.add(menu31);
			menulist.add(menu32);
			menulist.add(menu33);
			menulist.add(menu34);
			menulist.add(menu35);
			menulist.add(menu36);
			menulist.add(menu37);
			
			return menulist;
		}*/
}
