package com.sandu.api.pointmall.model.vo;

import java.math.BigDecimal;
import java.util.List;

import com.sandu.api.base.model.vo.BaseVo;

import io.swagger.annotations.ApiModel;

@ApiModel(value = "单一礼品对象", description = "单一礼品对象")
public class GiftSingleVo extends BaseVo<GiftSingleVo> {
    private static final long serialVersionUID = -20180810234044L;
    private String name;
    private int point;
    private String code;
    private String explain;
    private String introduce;
    private int inventory;
    private BigDecimal price;
    private Double expressFee;
    private List<String> filenames;


    public Double getExpressFee() {
        return expressFee;
    }

    public void setExpressFee(Double expressFee) {
        this.expressFee = expressFee;
    }

    /**
     * 获取礼品图片名称集合
     *
     * @return
     */
    public List<String> getFilenames() {
        return filenames;
    }

    /**
     * 设置礼品图片名称集合
     *
     * @param filenames
     */
    public void setFilenames(List<String> filenames) {
        this.filenames = filenames;
    }


    /**
     * 获取礼品名名称
     *
     * @return
     */
    public String getName() {
        return this.name;

    }

    /**
     * 设置礼品名称
     *
     * @param name 礼品名称
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取礼品的积分数
     *
     * @return
     */
    public int getPoint() {
        return point;
    }

    /**
     * 设置礼品的积分数
     *
     * @param point
     */
    public void setPoint(int point) {
        this.point = point;
    }

    /**
     * 获取礼品的代码
     *
     * @return
     */
    public String getCode() {
        return code;
    }

    /**
     * 设置礼品的代码
     *
     * @param code
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * 获取礼品的兑换说明
     *
     * @return
     */
    public String getExplain() {
        return explain;
    }

    /**
     * 设置礼品的兑换说明
     *
     * @param explain
     */
    public void setExplain(String explain) {
        this.explain = explain;
    }

    /**
     * 获取礼品的介绍
     *
     * @return
     */
    public String getIntroduce() {
        return introduce;
    }

    /**
     * 设置礼品的介绍
     *
     * @param introduce
     */
    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }

    /**
     * 获取礼品的库存数
     *
     * @return
     */
    public int getInventory() {
        return inventory;
    }

    /**
     * 设置礼品的库数
     *
     * @param inventory
     */
    public void setInventory(int inventory) {
        this.inventory = inventory;
    }

    /**
     * 获取礼品的价值
     *
     * @return
     */
    public BigDecimal getPrice() {
        return price;
    }

    /**
     * 设置礼品的价值
     *
     * @param price
     */
    public void setPrice(BigDecimal price) {
        this.price = price;
    }


}
