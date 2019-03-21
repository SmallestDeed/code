/**
 * MINI-web端，礼品实体对象
 * @author SHENYUNFEI
 * @date 2018/8/2  01:02
 */

package com.sandu.api.pointmall.model.vo;

import com.sandu.api.base.model.vo.BaseVo;

import io.swagger.annotations.ApiModel;

@ApiModel(value = "礼品对象", description = "礼品对象")
public class GiftVo extends BaseVo<GiftVo> {

    private static final long serialVersionUID = -20180810234044L;
    private String name;
    private int point;
    private String code;
    private String explain;
    private String introduce;
    private int inventory;
    private int price;
    private Double express_fee;
    private String filename;

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
     * 获取快递费
     * @return
     */
    public Double getExpress_fee() {
        return express_fee;
    }

    /**
     * 设置快递费
     * @param express_fee
     */
    public void setExpress_fee(Double express_fee) {
        this.express_fee = express_fee;
    }

    /**
     * 获取礼品的积分数
     * @return
     */
    public int getPoint() {
        return point;
    }

    /**
     * 设置礼品的积分数
     * @param point
     */
    public void setPoint(int point) {
        this.point = point;
    }

    /**
     * 获取礼品的代码
     * @return
     */
    public String getCode() {
        return code;
    }

    /**
     * 设置礼品的代码
     * @param code
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * 获取礼品的兑换说明
     * @return
     */
    public String getExplain() {
        return explain;
    }

    /**
     * 设置礼品的兑换说明
     * @param explain
     */
    public void setExplain(String explain) {
        this.explain = explain;
    }

    /**
     * 获取礼品的介绍
     * @return
     */
    public String getIntroduce() {
        return introduce;
    }

    /**
     * 设置礼品的介绍
     * @param introduce
     */
    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }

    /**
     * 获取礼品的库存数
     * @return
     */
    public int getInventory() {
        return inventory;
    }

    /**
     * 设置礼品的库数
     * @param inventory
     */
    public void setInventory(int inventory) {
        this.inventory = inventory;
    }

    /**
     * 获取礼品的价值
     * @return
     */
    public int getPrice() {
        return price;
    }

    /**
     * 设置礼品的价值
     * @param price
     */
    public void setPrice(int price) {
        this.price = price;
    }


    /**
     * 获取礼品的封面图片名称
     * @return
     */
    public String getConverImage() {
        return filename;
    }

    /**
     * 设置礼品的封面图片名称
     * @param filename
     */
    public void setConverImage(String filename) {
        this.filename = filename;
    }
}
