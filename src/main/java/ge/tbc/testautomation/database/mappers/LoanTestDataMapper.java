package ge.tbc.testautomation.database.mappers;

import ge.tbc.testautomation.database.models.LoanTestData;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface LoanTestDataMapper {

    @Insert("""
            INSERT INTO loan_test_data
                (amount, term_months, expected_monthly_payment)
            VALUES
                (#{amount}, #{termMonths}, #{expectedMonthlyPayment})
            """)
    int insertLoanTestData(LoanTestData loanTestData);

    @Select("""
            SELECT id, amount, term_months AS termMonths,
                   expected_monthly_payment AS expectedMonthlyPayment
            FROM loan_test_data
            """)
    List<LoanTestData> getAllLoanTestData();
}