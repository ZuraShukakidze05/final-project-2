package ge.tbc.testautomation.database;

import ge.tbc.testautomation.constants.Constants;
import ge.tbc.testautomation.database.mappers.LoanTestDataMapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public final class DataBaseConfig {
    private static SqlSessionFactory factory;

    public static synchronized SqlSessionFactory getSqlSessionFactory() {
        if (factory == null) {
            initLoanSchema();

            try (InputStream inputStream = Resources.getResourceAsStream(Constants.MYBATIS_CONFIG_PATH)) {
                factory = new SqlSessionFactoryBuilder().build(inputStream);
            } catch (IOException e) {
                throw new RuntimeException(Constants.ERROR_MYBATIS_CONFIG_LOAD_FAILED, e);
            }

            seedLoanTestData();
        }
        return factory;
    }

    private static void initLoanSchema() {
        try (Connection conn = DriverManager.getConnection(
                Constants.DB_URL, Constants.DB_USER, Constants.DB_PASSWORD);
             Statement stmt = conn.createStatement()) {
            stmt.execute("""
                    CREATE TABLE IF NOT EXISTS loan_test_data (
                        id BIGINT PRIMARY KEY AUTO_INCREMENT,
                        amount DECIMAL(10,2) NOT NULL,
                        term_months INT NOT NULL,
                        expected_monthly_payment DECIMAL(10,2) NOT NULL
                    )
                    """);
        } catch (SQLException e) {
            throw new RuntimeException(Constants.ERROR_LOAN_SCHEMA_INIT_FAILED, e);
        }
    }

    private static void seedLoanTestData() {
        try (SqlSession session = factory.openSession(true)) {
            LoanTestDataMapper mapper = session.getMapper(LoanTestDataMapper.class);
            if (!mapper.getAllLoanTestData().isEmpty()) {
                return;
            }
        }
        executeSeedScript();
    }

    private static void executeSeedScript() {
        try (InputStream inputStream = Resources.getResourceAsStream(Constants.LOAN_SEED_SCRIPT_PATH);
             Connection conn = DriverManager.getConnection(
                     Constants.DB_URL, Constants.DB_USER, Constants.DB_PASSWORD);
             Statement stmt = conn.createStatement()) {

            String sql = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
            for (String statement : sql.split(";")) {
                if (!statement.isBlank()) {
                    stmt.execute(statement.trim());
                }
            }
        } catch (IOException | SQLException e) {
            throw new RuntimeException(Constants.ERROR_LOAN_SEED_FAILED, e);
        }
    }

    private DataBaseConfig() {
    }
}