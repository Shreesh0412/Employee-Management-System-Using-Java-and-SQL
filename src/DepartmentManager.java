import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DepartmentManager {

    public boolean addDepartment(Department d) {
        String sql = "INSERT INTO departments (dept_name, location) VALUES (?, ?)";

        try (Connection con = Database.connect();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, d.getDeptName());
            ps.setString(2, d.getLocation());
            ps.executeUpdate();
            return true;
        } catch (SQLException ex) {
            System.out.println("Unable to add department: " + ex.getMessage());
            return false;
        }
    }

    public List<Department> getAllDepartments() {
        List<Department> departments = new ArrayList<>();
        String sql = "SELECT * FROM departments ORDER BY dept_id";

        try (Connection con = Database.connect();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                departments.add(new Department(
                    rs.getInt("dept_id"),
                    rs.getString("dept_name"),
                    rs.getString("location")
                ));
            }
        } catch (SQLException ex) {
            System.out.println("Unable to fetch departments: " + ex.getMessage());
        }
        return departments;
    }

    public Department findDepartment(int deptId) {
        String sql = "SELECT * FROM departments WHERE dept_id=?";

        try (Connection con = Database.connect();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, deptId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Department(
                        rs.getInt("dept_id"),
                        rs.getString("dept_name"),
                        rs.getString("location")
                    );
                }
            }
        } catch (SQLException ex) {
            System.out.println("Department search failed: " + ex.getMessage());
        }
        return null;
    }

    public void printDepartments() {
        List<Department> departments = getAllDepartments();

        System.out.println("\n--- Departments ---");
        if (departments.isEmpty()) {
            System.out.println("No departments found.");
            return;
        }

        for (Department d : departments) System.out.println(d);
    }

    public void printDepartmentEmployees(EmployeeManager employeeManager, int deptId) {
        Department department = findDepartment(deptId);

        if (department == null) {
            System.out.println("Department not found.");
            return;
        }

        System.out.println("\nDepartment: " + department.getDeptName());
        List<Employee> employees = employeeManager.getEmployeesByDepartment(deptId);

        if (employees.isEmpty()) {
            System.out.println("No employees assigned to this department.");
        } else {
            for (Employee e : employees) System.out.println(e);
        }
    }
}
