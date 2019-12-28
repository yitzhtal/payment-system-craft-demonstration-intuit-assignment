package services;

import beans.Payment;
import beans.ProcessedPayment;
import com.risk.engine.LoggingController;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class DBService implements InitializingBean {
    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    LoggingController logger;

    private AtomicInteger approvedPayments = new AtomicInteger(0);
    private AtomicInteger rejectedPayments = new AtomicInteger(0);

    @Override
    public void afterPropertiesSet() throws Exception {
        logger.info("afterPropertiesSet() - start");

        jdbcTemplate.execute("DROP TABLE processed_payments IF EXISTS");
        jdbcTemplate.execute("CREATE TABLE processed_payments(id SERIAL, amount VARCHAR(255), currency VARCHAR(255), userId VARCHAR(255), payeeId VARCHAR(255), paymentMethodId VARCHAR(255),riskScore VARCHAR(255),approved VARCHAR(255))");

        logger.info("afterPropertiesSet() - end");
    }

    public List<?> getAllTableRows() {
        return jdbcTemplate.query("SELECT * FROM processed_payments", new Object[] { },
                (rs, rowNum) -> new ProcessedPayment(
                        rs.getLong("amount"),
                        rs.getString("currency"),
                        rs.getString("userId"),
                        rs.getString("payeeId"),
                        rs.getString("paymentMethodId"),
                        rs.getInt("riskScore"),
                        rs.getBoolean("approved")));
    }

    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    public AtomicInteger getApprovedPayments() {
        return approvedPayments;
    }

    public void setApprovedPayments(AtomicInteger approvedPayments) {
        this.approvedPayments = approvedPayments;
    }

    public AtomicInteger getRejectedPayments() {
        return rejectedPayments;
    }

    public void setRejectedPayments(AtomicInteger rejectedPayments) {
        this.rejectedPayments = rejectedPayments;
    }
}
