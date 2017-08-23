package com.xiaoyetan.common.mq.plugins;

import com.xiaoyetan.Const;
import com.xiaoyetan.common.mq.CallCrmUnsubListener;
import org.apache.log4j.Logger;

import javax.jms.JMSException;


/**
 * @Author xiaoyetan
 * @Date :created on 17:28 2017/8/23
 */
public class ActivemqService {

    private final static Logger log = Logger.getLogger(ActivemqService.class);

    private static  String brokerUrl = Const.getValue("mq.brokerURL");
    public static final String CrmUnsubQueueName=Const.getValue("crm.unsub.queue");

    private static ActivemqPlugin mq ;

    static{
        try {
            log.info("ActivemqService static >>>");
            mq= new ActivemqPlugin(brokerUrl);
            mq.addQueneMessageListener(CrmUnsubQueueName, new CallCrmUnsubListener());
            log.info("ActivemqService static end>>>");
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取MQ工具类
     * @return
     */
    public static ActivemqPlugin MQ(){
        return mq;
    }

    // 发送消息
    public static void sendMessage(String queueName, final String msgJSON) {
        try {
            mq.sendQueueMsg(queueName,msgJSON);
            log.info(msgJSON);
        }catch (Exception e){
            e.printStackTrace();
        }

    }

}
