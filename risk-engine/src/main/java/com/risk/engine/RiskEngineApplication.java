package com.risk.engine;
import beans.Payment;
import beans.ProcessedPayment;
import com.google.gson.Gson;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import services.DBService;
import services.RabbitMQService;
import services.RiskEngineService;

import java.util.Arrays;

@SpringBootApplication(scanBasePackages={"services","com.risk.engine"})
public class RiskEngineApplication implements CommandLineRunner, InitializingBean {

	@Autowired
	DBService dataBaseService;

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
			dataBaseService.getJdbcTemplate().batchUpdate("INSERT INTO processed_payments(amount, currency, userId, payeeId, paymentMethodId, riskScore, approved) " +
					"VALUES ('" + processedPayment.getAmount() + "','" + processedPayment.getCurrency() + "','" + processedPayment.getUserId() +
					"','" + processedPayment.getPayeeId() + "','" + processedPayment.getPaymentMethodId() + "','"+processedPayment.getRiskScore()+"','"+processedPayment.isApproved()+"')");

			logger.info("Current Database:");
			dataBaseService.getAllTableRows().stream().forEach(row -> logger.info(((Object) row).toString()));
		});
	}

	@Override
	public void run(String... strings){
		logger.info("RiskEngineApplication is running...");
	}

}
