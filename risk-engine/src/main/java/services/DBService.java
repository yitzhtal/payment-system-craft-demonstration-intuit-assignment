package services;

import beans.Payment;
import com.risk.engine.LoggingController;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DBService implements InitializingBean, DisposableBean {
    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    LoggingController logger;

    @Override
    public void afterPropertiesSet() throws Exception {
        logger.info("afterPropertiesSet() - start");

        jdbcTemplate.execute("DROP TABLE processed_payments IF EXISTS");
        jdbcTemplate.execute("CREATE TABLE processed_payments(" +
                "id SERIAL, amount VARCHAR(255), currency VARCHAR(255), userId VARCHAR(255), payeeId VARCHAR(255), paymentMethodId VARCHAR(255))");

        logger.info("afterPropertiesSet() - end");
    }

    public JdbcTemplate getJdbcTemplate() { return jdbcTemplate; }

    public List<?> getAllTableRows() {
        return jdbcTemplate.query(
                "SELECT * FROM processed_payments", new Object[] { },
                (rs, rowNum) -> new Payment(rs.getLong("amount"), rs.getString("currency"), rs.getString("userId"), rs.getString("payeeId"), rs.getString("paymentMethodId"))
        );
    }

    @Override
    public void destroy() throws Exception {

    }
}
