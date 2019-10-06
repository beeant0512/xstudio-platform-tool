package com.xstudio.srping.aop.log;

/**
 * @author xiaobiao
 * @date 2018/11/8
 */
public interface ILogUserActionService {
    /**
     * 日志记录
     * @param log 日志内容
     */
    void saveLog(Log log);
}
