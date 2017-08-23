package com.xiaoyetan;

import com.xiaoyetan.common.mq.plugins.ActivemqService;

/**
 * @Author xiaoyetan
 * @Date :created on 17:37 2017/8/23
 */
public class TestMain {
    public static void main(String[] args) {
        ActivemqService.sendMessage(Const.getValue("crm.unsub.queue"),"{\"mobile\":\"28027833333\"}");
    }

}
