import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {
    private static final String DB_URL = "jdbc:sqlite:data/employees.db";

    public static Connection connect() throws SQLException {
        return DriverManager.getConnection(DB_URL);
    }

    public static void initialize() {
        String departmentTable = """
            CREATE TABLE IF NOT EXISTS departments (
                dept_id INTEGER PRIMARY KEY AUTOINCREMENT,
                dept_name TEXT NOT NULL UNIQUE,
                location TEXT NOT NULL
            )
            """;

        String employeeTable = """
            CREATE TABLE IF NOT EXISTS employees (
                emp_id TEXT PRIMARY KEY,
                name TEXT NOT NULL,
                salary REAL NOT NULL CHECK(salary >= 0),
                post TEXT NOT NULL,
                email TEXT,
                phone TEXT,
                joining_date TEXT,
                dept_id INTEGER,
                FOREIGN KEY (dept_id) REFERENCES departments(dept_id)
            )
            """;

        try (Connection con = connect();
             Statement stmt = con.createStatement()) {
            stmt.execute(departmentTable);
            stmt.execute(employeeTable);
        } catch (SQLException e) {
            System.out.println("Database initialization failed: " + e.getMessage());
        }
    }
}
