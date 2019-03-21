package com.sandu.service.collect.dao;

import com.sandu.api.collect.model.RecordMachineInfoOperation;
import org.springframework.stereotype.Repository;

/**
 * @ Author     ：weisheng.
 * @ Date       ：Created in PM 4:30 2018/10/23 0023
 * @ Description：${description}
 * @ Modified By：
 * @Version: $version$
 */
@Repository
public interface RecordMachineInfoOperationDao {


    int insertRecordMachineInfoOperation(RecordMachineInfoOperation recordMachineInfoOperation);
}
