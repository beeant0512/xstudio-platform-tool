package com.xstudio.srping.aop.log;

import com.alibaba.fastjson.JSON;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

/**
 * @author xiaobiao
 * @date 2018/11/8
 */
@Component
public class LogUserActionServiceImpl implements ILogUserActionService {
    public static Logger logger = LogManager.getLogger(LogUserActionServiceImpl.class);

    @Override
    public void saveLog(Log log) {
        logger.info("{}", JSON.toJSONString(log));
    }
}