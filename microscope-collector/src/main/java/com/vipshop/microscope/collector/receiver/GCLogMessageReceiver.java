package com.vipshop.microscope.collector.receiver;

import com.vipshop.microscope.collector.consumer.MessageConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

/**
 * Read Kafka message
 *
 * @author Xu Fei
 * @version 1.0
 */
public class GCLogMessageReceiver implements MessageReceiver {

    private static final Logger logger = LoggerFactory.getLogger(GCLogMessageReceiver.class);

    private final MessageConsumer consumer;

    public GCLogMessageReceiver(MessageConsumer consumer) {
        this.consumer = consumer;
    }

    /**
     * Read logs from kafka
     *
     */
    @Override
    public void start() {

        logger.info("start kafka message consumer");

        new Thread(new Runnable() {
            @Override
            public void run() {
                for (; ; ) {

                    String logs = "this is a test log in local model";
                    consumer.publish(logs);

                    try {
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();

    }

}