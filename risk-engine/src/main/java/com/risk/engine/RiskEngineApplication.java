package com.risk.engine;
import beans.Payment;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import services.DBService;
import services.RabbitMQService;

import java.util.Arrays;

@CrossOrigin(origins = "http://localhost:8080")
@SpringBootApplication(scanBasePackages={"services","com.risk.engine"})
public class RiskEngineApplication implements CommandLineRunner {

	@Autowired
	DBService dataBaseService;

	@Autowired
	RabbitMQService rabbitMQService;

	@Autowired
	LoggingController logger;

	Gson gson = new Gson();

	public static void main(String args[]) {
		SpringApplication.run(RiskEngineApplication.class, args);
	}

	@Override
	public void run(String... strings) throws Exception {
		rabbitMQService.consumeMessage((consumerTag, delivery) -> {
			String message = new String(delivery.getBody(), "UTF-8");
			Payment payment = gson.fromJson(message, Payment.class);
			logger.info("Consumed message: '" + message + "'");
			//analyze payment here!
			logger.info("Inserting payment record after analysis");

			Long amount = payment.getAmount();
			String currency = payment.getCurrency();
			String userId = payment.getUserId();
			String payeeId = payment.getPayeeId();
			String paymentMethodId = payment.getPaymentMethodId();
			SqlParameterSource[] batch = SqlParameterSourceUtils.createBatch(Arrays.asList(amount,currency,userId,payeeId,paymentMethodId));

			dataBaseService.getJdbcTemplate().batchUpdate("INSERT INTO processed_payments(amount, currency, userId, payeeId, paymentMethodId) " +
					"VALUES ('" + amount + "','" + currency + "','" + userId + "','" + payeeId + "','" + paymentMethodId + "')");

			dataBaseService.getAllTableRows().stream().forEach(row -> logger.info(((Object) row).toString()));
		});

	}

}
