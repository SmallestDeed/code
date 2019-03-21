package com.sandu.api.base.output;/**
 * @ Author     ：weisheng.
 * @ Date       ：Created in PM 8:18 2019/1/8 0008
 * @ Description：${description}
 * @ Modified By：
 * @Version: $version$
 */

import com.sandu.api.base.model.UserCardAccessRecord;
import lombok.Data;

import java.io.Serializable;

/**
 * @Title:
 * @Package
 * @Description:
 * @author weisheng
 * @date 2019/1/8 0008PM 8:18
 */
@Data
public class UserCardAccessRecordMsg implements Serializable{
    private static final long serialVersionUID = 7907673532787698931L;

    private UserCardAccessRecord userCardAccessRecord;

    private int unReadMsgCount;

}
