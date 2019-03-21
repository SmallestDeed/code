package com.sandu;

import com.google.gson.reflect.TypeToken;
import com.sandu.common.util.JsonUtil;
import lombok.Data;

import java.util.List;

public class Test2 {

    public static void main(String[] args) {
        /*String str = "[{'name':'wu_chuangt_6363_\\01','key':'1','textureRegionName':'1','textureIds':'33636,33635,33634,33633,33632,33631','defaultId':'33631'},{'name':'wu_chuangt_6363_02','key':'2','textureRegionName':'2','textureIds':'33642,33641,33640,33639,33638,33637','defaultId':'33637'}]";
        System.out.println(str);
        System.out.println(str.replaceAll("\\\\",""));*/
        Zewj b = JsonUtil.fromJson("{'name':'wu_chuangt_6363_\\01','key':'1','textureRegionName':'1','textureIds':'33636,33635,33634,33633,33632,33631','defaultId':'33631'}",Zewj.class);
        System.out.println(b.getName());
      /*  List<Zewj> a = JsonUtil.fromJson(str, new TypeToken<List<Zewj>>(){}.getType());
        System.out.println(a.size());*/
    }

    @Data
    public class Zewj{
        private String name;
        private String key;
        private String textureRegionName;
        private String textureIds;
        private String defaultId;
    }

}
