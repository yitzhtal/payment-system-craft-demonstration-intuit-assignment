package rabbitmq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import com.risk.engine.LoggingController;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class RabbitMQService implements InitializingBean, DisposableBean {

    @Autowired
    LoggingController logger;

    private Channel channel = null;
    private Connection connection = null;
    private ConnectionFactory factory = null;
    private final String queueName = "payments-queue";
    private final String userName = "guest";
    private final String password = "guest";
    private final String virtualHost = "/";
    private final String host = "localhost";
    private final int port = 5672;

    public void afterPropertiesSet() throws Exception {
        logger.info("init() -  initalizing RabbitMQService");
        try {
            if(factory == null && connection == null) { //no need for more then one connection
                factory = new ConnectionFactory();
                factory.setUsername(userName);
                factory.setPassword(password);
                factory.setVirtualHost(virtualHost);
                factory.setHost(host);
                factory.setPort(port);

                logger.info("init() -  setting up new connection");
                connection = factory.newConnection();

                logger.info("init() -  creating channel");
                channel = connection.createChannel();

                logger.info("init() -  declaring queue: " +queueName);
                channel.queueDeclare(queueName, false, false, false, null);
            }
        } catch (Exception e) {
            logger.info("init() -  failed initializing RabbitMQService bean");
        }
    }

    public void consumeMessage(DeliverCallback deliverCallback) {
        logger.info("consumeMessage() -  started");
        try {
            channel.basicConsume(queueName, true, deliverCallback, consumerTag -> { });
            logger.info("consumeMessage() -  ended successfully");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void destroy() throws Exception {
        connection.close();
        channel.close();
    }

}
