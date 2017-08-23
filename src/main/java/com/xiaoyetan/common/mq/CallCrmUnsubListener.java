package com.xiaoyetan.common.mq;

import com.alibaba.fastjson.JSON;
import com.xiaoyetan.common.mq.domain.UnsubCrmParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Author xiaoyetan
 * @Date :created on 17:31 2017/8/23
 */
public class CallCrmUnsubListener  implements MessageListener{
    private ExecutorService threadPool = Executors.newFixedThreadPool(1);
    private final static Logger LOGGER = LoggerFactory.getLogger(CallCrmUnsubListener.class);

    @Override
    public void onMessage(final Message message) {
        threadPool.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    if (message instanceof TextMessage) {
                        TextMessage tMsg = (TextMessage) message;
                        UnsubCrmParam unsubCrmParam = JSON.parseObject(tMsg.getText(), UnsubCrmParam.class);
                        LOGGER.info("退订监听正在检索>>>>" + unsubCrmParam.getMobile()+ ",处理线程:" + Thread.currentThread().getName());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    LOGGER.error(e.getMessage());
                }
            }
        });
    }

}
