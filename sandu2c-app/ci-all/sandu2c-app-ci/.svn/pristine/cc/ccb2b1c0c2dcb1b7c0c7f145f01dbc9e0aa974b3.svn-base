package com.sandu.initialize;/**
 * @ Author     ：weisheng.
 * @ Date       ：Created in AM 10:32 2018/7/3 0003
 * @ Description：${description}
 * @ Modified By：
 * @Version: $version$
 */

import com.sandu.common.util.BadWordUtil;
import com.sandu.common.util.BankWordUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Set;

/**
 * @Title: 初始化敏感词词典
 * @Package
 * @Description:
 * @author weisheng
 * @date 2018/7/3 0003AM 10:32
 */
@Slf4j
@Component
public class InitSensitiveWords {

    private final static String SENSITIVE_FILE_PATH = "config/sensitivewords.properties";

    private final static String BANK_FILE_PATH = "config/bankwords.properties";



    /*初始化敏感词数据到内存中*/
    @PostConstruct
    private void initSensitiveWords(){
        Set<String> words = BadWordUtil.readTxtByLine(SENSITIVE_FILE_PATH);
        BadWordUtil.addBadWordToHashMap(words);
        log.info("敏感词汇数据初始化完成,数据条数为:"+words.size()+"map集合size:"+BadWordUtil.wordMap.size());
    }


}
