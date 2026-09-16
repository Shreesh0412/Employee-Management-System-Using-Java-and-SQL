import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmployeeManager {

    public boolean addEmployee(Employee e) {
        String sql = """
            INSERT INTO employees
            (emp_id, name, salary, post, email, phone, joining_date, dept_id)
            VALUES (?, ?, ?, ?, ?, ?, ?, ?)
            """;

        try (Connection con = Database.connect();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, e.getEmpId());
            ps.setString(2, e.getName());
            ps.setDouble(3, e.getSalary());
            ps.setString(4, e.getPost());
            ps.setString(5, e.getEmail());
            ps.setString(6, e.getPhone());
            ps.setString(7, e.getJoiningDate());

            if (e.getDeptId() == 0) ps.setNull(8, Types.INTEGER);
            else ps.setInt(8, e.getDeptId());

            ps.executeUpdate();
            return true;
        } catch (SQLException ex) {
            System.out.println("Unable to add employee: " + ex.getMessage());
            return false;
        }
    }

    public List<Employee> getAllEmployees() {
        List<Employee> employees = new ArrayList<>();
        String sql = "SELECT * FROM employees ORDER BY emp_id";

        try (Connection con = Database.connect();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) employees.add(readEmployee(rs));
        } catch (SQLException ex) {
            System.out.println("Unable to fetch employees: " + ex.getMessage());
        }
        return employees;
    }

    public Employee searchById(String empId) {
        String sql = "SELECT * FROM employees WHERE emp_id = ?";

        try (Connection con = Database.connect();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, empId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return readEmployee(rs);
            }
        } catch (SQLException ex) {
            System.out.println("Search failed: " + ex.getMessage());
        }
        return null;
    }

    public List<Employee> searchByName(String name) {
        List<Employee> employees = new ArrayList<>();
        String sql = "SELECT * FROM employees WHERE LOWER(name) LIKE LOWER(?) ORDER BY name";

        try (Connection con = Database.connect();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, "%" + name + "%");
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) employees.add(readEmployee(rs));
            }
        } catch (SQLException ex) {
            System.out.println("Search failed: " + ex.getMessage());
        }
        return employees;
    }

    public boolean updateEmployee(Employee e) {
        String sql = """
            UPDATE employees
            SET name=?, salary=?, post=?, email=?, phone=?, joining_date=?, dept_id=?
            WHERE emp_id=?
            """;

        try (Connection con = Database.connect();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, e.getName());
            ps.setDouble(2, e.getSalary());
            ps.setString(3, e.getPost());
            ps.setString(4, e.getEmail());
            ps.setString(5, e.getPhone());
            ps.setString(6, e.getJoiningDate());
            if (e.getDeptId() == 0) ps.setNull(7, Types.INTEGER);
            else ps.setInt(7, e.getDeptId());
            ps.setString(8, e.getEmpId());

            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            System.out.println("Update failed: " + ex.getMessage());
            return false;
        }
    }

    public boolean deleteEmployee(String empId) {
        String sql = "DELETE FROM employees WHERE emp_id=?";

        try (Connection con = Database.connect();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, empId);
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            System.out.println("Delete failed: " + ex.getMessage());
            return false;
        }
    }

    public List<Employee> getEmployeesByDepartment(int deptId) {
        List<Employee> employees = new ArrayList<>();
        String sql = "SELECT * FROM employees WHERE dept_id=? ORDER BY name";

        try (Connection con = Database.connect();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, deptId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) employees.add(readEmployee(rs));
            }
        } catch (SQLException ex) {
            System.out.println("Unable to fetch department employees: " + ex.getMessage());
        }
        return employees;
    }

    public double getAverageSalary() { return getSalaryAggregate("AVG"); }
    public double getHighestSalary() { return getSalaryAggregate("MAX"); }
    public double getLowestSalary() { return getSalaryAggregate("MIN"); }

    public void printSalaryReport() {
        System.out.println("\n--- Salary Report ---");
        System.out.printf("Average Salary : %.2f%n", getAverageSalary());
        System.out.printf("Highest Salary : %.2f%n", getHighestSalary());
        System.out.printf("Lowest Salary  : %.2f%n", getLowestSalary());
    }

    public void printDepartmentWiseSalary() {
        String sql = """
            SELECT d.dept_name, AVG(e.salary) AS avg_salary
            FROM departments d
            LEFT JOIN employees e ON d.dept_id = e.dept_id
            GROUP BY d.dept_id, d.dept_name
            ORDER BY d.dept_name
            """;

        try (Connection con = Database.connect();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            System.out.println("\n--- Department-wise Average Salary ---");
            while (rs.next()) {
                String dept = rs.getString("dept_name");
                double avg = rs.getDouble("avg_salary");
                if (rs.wasNull()) System.out.printf("%s : No employee data%n", dept);
                else System.out.printf("%s : %.2f%n", dept, avg);
            }
        } catch (SQLException ex) {
            System.out.println("Report failed: " + ex.getMessage());
        }
    }

    private double getSalaryAggregate(String function) {
        String sql = "SELECT " + function + "(salary) FROM employees";

        try (Connection con = Database.connect();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next() && !rs.wasNull()) return rs.getDouble(1);
        } catch (SQLException ex) {
            System.out.println("Salary calculation failed: " + ex.getMessage());
        }
        return 0.0;
    }

    private Employee readEmployee(ResultSet rs) throws SQLException {
        int deptId = rs.getObject("dept_id") == null ? 0 : rs.getInt("dept_id");

        return new Employee(
            rs.getString("emp_id"),
            rs.getString("name"),
            rs.getDouble("salary"),
            rs.getString("post"),
            rs.getString("email"),
            rs.getString("phone"),
            rs.getString("joining_date"),
            deptId
        );
    }
}
