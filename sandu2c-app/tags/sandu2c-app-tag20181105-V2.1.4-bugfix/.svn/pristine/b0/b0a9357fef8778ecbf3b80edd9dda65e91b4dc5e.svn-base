package com.sandu.user;/**
 * @ Author     ：weisheng.
 * @ Date       ：Created in PM 7:59 2018/8/23 0023
 * @ Description：${description}
 * @ Modified By：
 * @Version: $version$
 */

import com.sandu.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @Title:
 * @Package
 * @Description:
 * @author weisheng
 * @date 2018/8/23 0023PM 7:59
 */
@Data
public class MediationAuthorizeAdd implements Serializable{

    @NotBlank()
    @Name("证件类型")
    private Integer cardType;//证件类型

    @NotBlank()
    @Length(min=1,max=100)
    @Name("证件类型")
    private String cardNumber;//证件ID

    @NotBlank()
    @Length(min=1,max=50)
    @Name("证件人名称")
    private String cardName;//证件人名称

    @NotBlank()
    @Name("证件照片ID")
    private Integer cardPicId;//证件照片ID




}
