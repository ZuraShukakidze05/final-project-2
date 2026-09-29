package ge.tbc.testautomation.data;

import ge.tbc.testautomation.database.DataBaseConfig;
import ge.tbc.testautomation.database.mappers.LoanTestDataMapper;
import ge.tbc.testautomation.database.models.LoanTestData;
import org.apache.ibatis.session.SqlSession;
import org.testng.annotations.DataProvider;

import java.util.List;

public class LoanDataProvider {

    @DataProvider(name = "loanData")
    public static Object[][] getLoanData() {
        try (SqlSession session = DataBaseConfig.getSqlSessionFactory().openSession()) {
            LoanTestDataMapper mapper = session.getMapper(LoanTestDataMapper.class);
            List<LoanTestData> records = mapper.getAllLoanTestData();

            Object[][] data = new Object[records.size()][1];
            for (int i = 0; i < records.size(); i++) {
                data[i][0] = records.get(i);
            }
            return data;
        }
    }
}