package com.sandu.service.restexture.impl;

import com.sandu.api.platform.model.Platform;
import com.sandu.api.product.model.bo.ProductTextureInfo;
import com.sandu.api.restexture.service.ResTextureService;
import com.sandu.constant.Punctuation;
import com.sandu.service.platform.dao.PlatformDao;
import com.sandu.util.JsonParser;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class ResTextureServiceTest {

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public class Bean {
        private String name;
        private String key;
        private String textureIds;
        private String defaultId;
        private Object affectTextures;
    }

    @Autowired
    private ResTextureService resTextureService;
    @Autowired
    private PlatformDao platformDao;

    @Data
    @Builder
    public static class Permission {
        private String name;
        private String key;
        private String num;
        private Integer id;
        private String type;
        private Integer parentId;
        private Integer isLeaf = 1;
    }

    @Test
    public void page() {
        String str = "[{\"name\":\"材质区域1\",\"affectTextures\":[{\"id\":37232,\"affectPrice\":0}],\"defaultId\":37232,\"textureIds\":\"37232\",\"key\":\"0\"}]";
        List<Bean> beans = JsonParser.fromJson2List(str, Bean.class);
        System.out.println(beans);
    }


    @Test
    public void initPermission() throws IOException {
//        String filePath = "C:\\Users\\Sandu\\Desktop\\permission.text";
        String filePath = "C:\\Users\\Sandu\\Desktop\\permission.txt";
        FileReader fileReader = new FileReader(filePath);
        BufferedReader br = new BufferedReader(fileReader);
        List<Permission> list = new ArrayList<>();
        Map<Integer, Integer> map = new HashMap<>();
        map.put(1, 100);
        map.put(2, 10);
        map.put(3, 1);
        Integer id = 8000;
        Platform platform = platformDao.getByPlatformCode("merchantManage");
        while (true) {
            String s = br.readLine();
            if (s == null) {
                break;
            }
            if (StringUtils.isNoneBlank(s)) {
                String[] split = s.split(Punctuation.COMMA);
                Permission per = Permission.builder().key(split[1].trim())
                        .isLeaf(1)
                        .name(split[0].trim()).build();
                per.setType("menu");
                switch (split[2].trim()) {
                    case "0"://商家后台
                        per.setParentId(0);
                        break;
                    case "1":
                        id = id / 100 * 100;
                        id += map.get(1);
                        per.setParentId(id / 1000 * 1000);
                        break;
                    case "2":
                        id = id / 10 * 10;
                        id += map.get(2);
                        per.setParentId(id / 100 * 100);
                        break;
                    case "3":
                        id += map.get(3);
                        per.setParentId(id / 10 * 10);
                        per.setType("func");
                        per.setIsLeaf(0);
                        break;
                }
                per.setId(id);
                list.add(per);
            }
        }
        list.forEach(item -> {
            InitSysFunc initPermission = InitSysFunc.builder()
                    .id(item.getId())
                    .name(item.getName())
                    .platformId(platform.getId().intValue())
                    .type(item.getType())
                    .isDeleted(0)
                    .isLeaf(item.getIsLeaf())
                    .code(String.valueOf(System.currentTimeMillis()).substring(6))
                    .creator("initPermission")
                    .parentId(item.getParentId())
                    .keyword(item.getKey())
                    .build();
            sqlHead += setSql(initPermission);
        });
//        String substring = sqlHead.substring(sqlHead.lastIndexOf());
//        String result = substring + ";";
        log.info(sqlHead);
    }

    private String sqlHead = "INSERT INTO `sys_func` (`id`, `code`, `parentid`, `name`, `keyword`,`is_leaf`, `creator`, `is_deleted`, `type`, `platform_id`) ";

    String setSql(InitSysFunc initSysFunc) {
        String sql =
                "(select " +
                        initSysFunc.getId() + ", '" +
                        initSysFunc.getCode() + "', " +
                        initSysFunc.getParentId() + ",  '" +
                        initSysFunc.getName() + "', '" +
                        initSysFunc.getKeyword() + "', '" +
                        initSysFunc.getIsLeaf() + "', '" +
                        initSysFunc.getCreator() + "', '" +
                        initSysFunc.getIsDeleted() + "', '" +
                        initSysFunc.getType() + "', " +
                        "id as platform_id from base_platform where platform_code = 'merchantManage') UNION";
        return sql;
    }


    @Test
    public void Test() {

        String json = "[{'name':'zuoysf_dide_0054','key':'1','textureRegionName':'','textureIds':'35409,35413,35414','defaultId':'35409'},{'name':'zuoysf_dide_0054','key':'2','textureRegionName':'','textureIds':'35416,35418','defaultId':'35416'}]";
        String replace = json.replace("'", "\"");
        List<ProductTextureInfo> productTextureInfos = JsonParser.fromJson2List(replace, ProductTextureInfo.class);
        String s = JsonParser.toJson(productTextureInfos);
        System.out.println(productTextureInfos);
    }
}