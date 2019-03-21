package com.sandu.decorate.output;

import com.sandu.system.model.SysDictionary;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class SysDictionaryVO {

    //分类
    private String type;
    //value值
    private Integer value;
    //name属性
    private String name;
    //valueKey
    private String valueKey;
    //子类集合
    private List<SysDictionaryVO> sonList = new ArrayList<>();


    /**
     * 把sysDictionary对象集合复制到sysDictionaryVO中
     *
     * @param sysDictionaryList
     * @return
     */
    public static List<SysDictionaryVO> copyToSysDictionaryVO(List<SysDictionary> sysDictionaryList) {
        List<SysDictionaryVO> sysDictionaryVOList = new ArrayList<>();
        SysDictionaryVO sysDictionaryVO;
        SysDictionaryVO sonVO;
        for (SysDictionary sysDictionary : sysDictionaryList) {
            sysDictionaryVO = new SysDictionaryVO();
            sysDictionaryVO.setType(sysDictionary.getType());
            sysDictionaryVO.setName(sysDictionary.getName());
            sysDictionaryVO.setValue(sysDictionary.getValue());
            sysDictionaryVO.setValueKey(sysDictionary.getValuekey());
            if (sysDictionary.getSonList() != null ) {
                for (SysDictionary dictionary : sysDictionary.getSonList()) {
                    sonVO = new SysDictionaryVO();
                    sonVO.setValueKey(dictionary.getValuekey());
                    sonVO.setValue(dictionary.getValue());
                    sonVO.setName(dictionary.getName());
                    sonVO.setType(dictionary.getType());

                    sysDictionaryVO.getSonList().add(sonVO);
                }
            }
            sysDictionaryVOList.add(sysDictionaryVO);
        }
        return sysDictionaryVOList;
    }

}
