package com.risk.engine;
import beans.Payment;
import beans.ProcessedPayment;
import com.google.gson.Gson;
import interfaces.DBServiceInf;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import services.RabbitMQService;
import services.RiskEngineService;

import javax.sql.DataSource;

@SpringBootApplication(scanBasePackages={"services","com.risk.engine"})
@EntityScan("beans")
@EnableJpaRepositories("interfaces")
public class RiskEngineApplication implements CommandLineRunner, InitializingBean {
	@Autowired
	DataSource dataSource;

	@Autowired
	DBServiceInf databaseService;

	@Autowired
	RiskEngineService riskEngineService;

	@Autowired
	RabbitMQService rabbitMQService;

	@Autowired
	LoggingController logger;

	@Autowired
	Gson gson;

	public static void main(String args[]) {
		SpringApplication.run(RiskEngineApplication.class, args);
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		logger.info("afterPropertiesSet() : registering call back for rabbitmq");
		rabbitMQService.registerCallbackToConsumeMessage((consumerTag, delivery) -> {
			String message = new String(delivery.getBody(), "UTF-8");

			logger.info("Consumed message: '" + message + "'");
			logger.info("Delivery tag: '" + delivery.getEnvelope().getDeliveryTag() + "'");
			logger.info("Delivery properties: '" + delivery.getProperties() + "'");

			Payment payment = gson.fromJson(message, Payment.class);

			//analyzing the risk for this payment, returns a ProcessedPayment object with risk score and approved/not decision
			logger.info("Analyzing risk...");
			ProcessedPayment processedPayment = riskEngineService.analyze(payment);

			logger.info("Inserting payment record after analysis");
			databaseService.save(processedPayment);

			logger.info("Current Database:");
			databaseService.findAll().forEach(row -> logger.info(((Object) row).toString()));
		});
		logger.info("afterPropertiesSet() : done registering call back for rabbitmq");
	}

	@Override
	public void run(String... strings){
		logger.info("RiskEngineApplication is running...");
		logger.info("Our DataSource is = " + dataSource.toString());
	}

}
