package com.xiaoyetan.common.mq.plugins;

import java.util.HashMap;
import java.util.Map;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.pool.PooledConnectionFactory;
import org.apache.activemq.thread.TaskRunnerFactory;
import com.jfinal.plugin.IPlugin;
import org.apache.log4j.Logger;

import javax.jms.*;

/**
 * @Author xiaoyetan
 * @Date :created on 17:26 2017/8/23
 */
public class ActivemqPlugin implements IPlugin{
    private final static Logger log = Logger.getLogger(ActivemqPlugin.class);
    private String brokerUrl;

    private ActiveMQConnectionFactory targetFactory;

    private PooledConnectionFactory pooledConFactory;

    private Connection connection;

    private Session session;

    private Map<String, Queue> queueMap = new HashMap<String, Queue>();
    private boolean isStarted = false;


    private Map<String, MessageProducer> queueProducer = new HashMap<String, MessageProducer>();

    public ActivemqPlugin(String brokerUrl) {
        this.brokerUrl = brokerUrl;
        try {
            init();
        } catch (JMSException e) {
            e.printStackTrace();
        }

    }


    public void init() throws JMSException {
        if (null == pooledConFactory) {
            targetFactory = new ActiveMQConnectionFactory(brokerUrl);
            targetFactory.setUseAsyncSend(true);
            TaskRunnerFactory taskRunnerFactory = new TaskRunnerFactory();
            taskRunnerFactory.setMaxIterationsPerRun(2);
            taskRunnerFactory.setMaxThreadPoolSize(10);
            taskRunnerFactory.setShutdownAwaitTermination(10);
            taskRunnerFactory.setDaemon(false);
            targetFactory.setSessionTaskRunner(taskRunnerFactory);
            //pooledConFactory = new PooledConnectionFactory(brokerUrl);  //都可以
            pooledConFactory = new PooledConnectionFactory((ActiveMQConnectionFactory) targetFactory);
            pooledConFactory.setMaxConnections(200);
        }
    }

    public Session createCon() throws JMSException {
        if (null == connection) {
            connection = pooledConFactory.createConnection();
            connection.start();
        }
        if (null == session) {
            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        }
        isStarted =true;
        return session;
    }

    public void addQueue(String queueName) {
        if (null == queueName || queueName.trim().equals("")) {
            throw new RuntimeException("Queue name parameter is blank!");
        }
        synchronized (queueMap) {
            if (null == queueMap) {
                queueMap = new HashMap<String, Queue>();
            }
            if (!queueMap.containsKey(queueName)) {
                queueMap.put(queueName, new ActiveMQQueue(queueName));
            }
        }
    }

    public void addQueneMessageListener(String queneName, MessageListener msgListenre) throws JMSException {
        addQueue(queneName);
        MessageConsumer comsumer = createCon().createConsumer(queueMap.get(queneName));
        comsumer.setMessageListener(msgListenre);
    }


    public void sendQueueMsg(String queueName, String msg) throws JMSException {
        addQueue(queueName);
        //创建一个生产者，然后发送多个消息。
        if (null == queueProducer) {
            queueProducer = new HashMap<String, MessageProducer>();
        }
        if (!queueProducer.containsKey(queueName)) {
            queueProducer.put(queueName, createCon().createProducer(queueMap.get(queueName)));
        }
        MessageProducer producer = queueProducer.get(queueName);
        //producer.setTimeToLive(time);
        producer.send(createCon().createTextMessage(msg));
    }

    public void setBrokerUrl(String brokerUrl) {
        this.brokerUrl = brokerUrl;
    }

    public String getBrokerUrl() {
        return brokerUrl;
    }

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    public boolean stop() {
        return false;
    }

    public boolean start() {
        if (null != session) {
            log.info("activemq isStarted>>>");
            return true;
        }else{
            new ActivemqService();
        }
        return true;
    }

}
