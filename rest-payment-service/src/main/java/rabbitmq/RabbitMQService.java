package rabbitmq;

import com.paymentservice.LoggingController;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
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

    public RabbitMQService() { }

    public RabbitMQService(Channel channel, Connection connection, ConnectionFactory factory) {
        this.channel = channel;
        this.connection = connection;
        this.factory = factory;
    }

    public void afterPropertiesSet() throws Exception {
        logger.info("afterPropertiesSet() -  initalizing RabbitMQService");
        try {
            factory = new ConnectionFactory();
            factory.setUsername(userName);
            factory.setPassword(password);
            factory.setVirtualHost(virtualHost);
            factory.setHost(host);
            factory.setPort(port);

            logger.info("afterPropertiesSet() -  setting up new connection");
            connection = factory.newConnection();

            logger.info("afterPropertiesSet() -  creating channel");
            channel = connection.createChannel();
            channel.confirmSelect(); //enable publishing messages with confirmation

            logger.info("afterPropertiesSet() -  declaring queue: " +queueName);
            channel.queueDeclare(queueName, false, false, false, null);
        } catch (Exception e) {
            logger.info("afterPropertiesSet() -  failed initializing RabbitMQService bean");
        }
    }

    public void publishMessage(String message) {
        logger.info("publishMessage() -  publishing message");
        try {
            channel.basicPublish("", queueName, null, message.getBytes());
        } catch (Exception e) {
            logger.info("publishMessage() -  failed publishing message");
        }
        logger.info("publishMessage() -  message sent to queue: " + message + ".");
    }

    public void destroy() throws Exception {
        connection.close();
        channel.close();
    }

}
