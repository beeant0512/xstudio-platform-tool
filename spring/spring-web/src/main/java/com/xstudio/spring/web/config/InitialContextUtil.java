package com.xstudio.spring.web.config;

import com.xstudio.spring.core.ContextUtil;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

/**
 * @author xiaobiao
 * @version 2019/10/3
 */
@Component
public class InitialContextUtil implements ApplicationListener<ContextRefreshedEvent> {
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (null != event.getApplicationContext() && null == event.getApplicationContext().getParent()) {
            //只有root application context 没有父容器
            // start the executor
            ContextUtil.setContext(event.getApplicationContext());
        }
    }
}
