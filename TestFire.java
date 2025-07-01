import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class TestFire {
    public void vulnerableWithdraw(String accountId, String amount) throws Exception {
        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/testfire", "user", "pass");
        Statement stmt = conn.createStatement();

        // ruleid: tf-sqli
        String query = "UPDATE accounts SET balance = balance - " + amount +
                       " WHERE id = " + accountId;
        stmt.execute(query);
    }

    public void safeWithdraw(String accountId, String amount) throws Exception {
        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/testfire", "user", "pass");
        java.sql.PreparedStatement ps = conn.prepareStatement(
            "UPDATE accounts SET balance = balance - ? WHERE id = ?");
        ps.setString(1, amount);
        ps.setString(2, accountId);
        ps.execute();
        // ok: tf-sqli
    }
}
