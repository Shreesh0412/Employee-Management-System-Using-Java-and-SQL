import java.util.List;
import java.util.Scanner;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static final EmployeeManager employeeManager = new EmployeeManager();
    private static final DepartmentManager departmentManager = new DepartmentManager();

    public static void main(String[] args) {
        Database.initialize();

        while (true) {
            printMainMenu();
            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1" -> employeeMenu();
                case "2" -> departmentMenu();
                case "3" -> salaryMenu();
                case "4" -> {
                    System.out.println("Exiting the program. Goodbye!");
                    return;
                }
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void printMainMenu() {
        System.out.println("\n========================================");
        System.out.println("       EMPLOYEE MANAGEMENT SYSTEM");
        System.out.println("========================================");
        System.out.println("1. Employee Management");
        System.out.println("2. Department Management");
        System.out.println("3. Salary Management");
        System.out.println("4. Exit");
        System.out.print("Enter your choice: ");
    }

    private static void employeeMenu() {
        while (true) {
            System.out.println("\n--------- EMPLOYEE MANAGEMENT ---------");
            System.out.println("1. Add Employee");
            System.out.println("2. View All Employees");
            System.out.println("3. Search Employee by ID");
            System.out.println("4. Search Employee by Name");
            System.out.println("5. Update Employee");
            System.out.println("6. Delete Employee");
            System.out.println("7. View Employees by Department");
            System.out.println("8. Back");
            System.out.print("Enter your choice: ");

            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1" -> addEmployee();
                case "2" -> viewAllEmployees();
                case "3" -> searchById();
                case "4" -> searchByName();
                case "5" -> updateEmployee();
                case "6" -> deleteEmployee();
                case "7" -> viewByDepartment();
                case "8" -> { return; }
                default -> System.out.println("Invalid choice.");
            }
        }
    }

    private static void departmentMenu() {
        while (true) {
            System.out.println("\n-------- DEPARTMENT MANAGEMENT --------");
            System.out.println("1. Add Department");
            System.out.println("2. View Departments");
            System.out.println("3. View Department Employees");
            System.out.println("4. Back");
            System.out.print("Enter your choice: ");

            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1" -> addDepartment();
                case "2" -> departmentManager.printDepartments();
                case "3" -> viewByDepartment();
                case "4" -> { return; }
                default -> System.out.println("Invalid choice.");
            }
        }
    }

    private static void salaryMenu() {
        while (true) {
            System.out.println("\n----------- SALARY MANAGEMENT ----------");
            System.out.println("1. Salary Report");
            System.out.println("2. Department-wise Average Salary");
            System.out.println("3. Back");
            System.out.print("Enter your choice: ");

            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1" -> employeeManager.printSalaryReport();
                case "2" -> employeeManager.printDepartmentWiseSalary();
                case "3" -> { return; }
                default -> System.out.println("Invalid choice.");
            }
        }
    }

    private static void addEmployee() {
        System.out.println("\n--- Add Employee ---");

        String id = readNonEmpty("Employee ID: ");
        if (employeeManager.searchById(id) != null) {
            System.out.println("Employee ID already exists.");
            return;
        }

        String name = readNonEmpty("Employee Name: ");
        double salary = readNonNegativeDouble("Salary: ");
        String post = readNonEmpty("Post: ");
        String email = readOptional("Email: ");
        String phone = readOptional("Phone: ");
        String joiningDate = readOptional("Joining Date (YYYY-MM-DD): ");

        departmentManager.printDepartments();
        int deptId = readDepartmentId("Department ID (0 for none): ");

        Employee employee = new Employee(
            id, name, salary, post, email, phone, joiningDate, deptId
        );

        if (employeeManager.addEmployee(employee)) {
            System.out.println("Employee added successfully.");
        }
    }

    private static void viewAllEmployees() {
        List<Employee> employees = employeeManager.getAllEmployees();

        System.out.println("\n--- All Employees ---");
        if (employees.isEmpty()) {
            System.out.println("No records found.");
            return;
        }

        for (Employee e : employees) System.out.println(e);
    }

    private static void searchById() {
        String id = readNonEmpty("Enter Employee ID: ");
        Employee employee = employeeManager.searchById(id);

        if (employee == null) System.out.println("Employee not found.");
        else System.out.println(employee);
    }

    private static void searchByName() {
        String name = readNonEmpty("Enter name to search: ");
        List<Employee> employees = employeeManager.searchByName(name);

        if (employees.isEmpty()) {
            System.out.println("No matching employees found.");
        } else {
            for (Employee e : employees) System.out.println(e);
        }
    }

    private static void updateEmployee() {
        String id = readNonEmpty("Enter Employee ID to update: ");
        Employee old = employeeManager.searchById(id);

        if (old == null) {
            System.out.println("Employee not found.");
            return;
        }

        System.out.println("Press Enter to keep the old value.");

        String name = readWithDefault("Name [" + old.getName() + "]: ", old.getName());
        double salary = readDoubleWithDefault("Salary [" + old.getSalary() + "]: ", old.getSalary());
        String post = readWithDefault("Post [" + old.getPost() + "]: ", old.getPost());
        String email = readWithDefault("Email [" + old.getEmail() + "]: ", old.getEmail());
        String phone = readWithDefault("Phone [" + old.getPhone() + "]: ", old.getPhone());
        String joiningDate = readWithDefault(
            "Joining Date [" + old.getJoiningDate() + "]: ", old.getJoiningDate()
        );

        int deptId = readDepartmentId(
            "Department ID [" + old.getDeptId() + "] (0 to remove): "
        );

        Employee updated = new Employee(
            id, name, salary, post, email, phone, joiningDate, deptId
        );

        if (employeeManager.updateEmployee(updated)) {
            System.out.println("Employee updated successfully.");
        }
    }

    private static void deleteEmployee() {
        String id = readNonEmpty("Enter Employee ID to delete: ");
        Employee employee = employeeManager.searchById(id);

        if (employee == null) {
            System.out.println("Employee not found.");
            return;
        }

        System.out.println(employee);
        System.out.print("Are you sure? (Y/N): ");
        String confirm = scanner.nextLine().trim();

        if (confirm.equalsIgnoreCase("Y") && employeeManager.deleteEmployee(id)) {
            System.out.println("Employee deleted successfully.");
        } else {
            System.out.println("Deletion cancelled.");
        }
    }

    private static void addDepartment() {
        System.out.println("\n--- Add Department ---");
        String name = readNonEmpty("Department Name: ");
        String location = readNonEmpty("Location: ");

        if (departmentManager.addDepartment(new Department(name, location))) {
            System.out.println("Department added successfully.");
        }
    }

    private static void viewByDepartment() {
        departmentManager.printDepartments();
        int deptId = readDepartmentId("Enter Department ID: ");

        if (deptId == 0) {
            System.out.println("Please select a valid department.");
            return;
        }

        departmentManager.printDepartmentEmployees(employeeManager, deptId);
    }

    private static String readNonEmpty(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            if (!input.isEmpty()) return input;
            System.out.println("This field cannot be empty.");
        }
    }

    private static String readOptional(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }

    private static double readNonNegativeDouble(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                double value = Double.parseDouble(scanner.nextLine().trim());
                if (value >= 0) return value;
            } catch (NumberFormatException ignored) {}
            System.out.println("Please enter a valid non-negative number.");
        }
    }

    private static double readDoubleWithDefault(String prompt, double defaultValue) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            if (input.isEmpty()) return defaultValue;

            try {
                double value = Double.parseDouble(input);
                if (value >= 0) return value;
            } catch (NumberFormatException ignored) {}
            System.out.println("Please enter a valid non-negative number.");
        }
    }

    private static int readDepartmentId(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();

            try {
                int value = Integer.parseInt(input);
                if (value < 0) {
                    System.out.println("Department ID cannot be negative.");
                    continue;
                }
                if (value != 0 && departmentManager.findDepartment(value) == null) {
                    System.out.println("Department does not exist.");
                    continue;
                }
                return value;
            } catch (NumberFormatException e) {
                System.out.println("Enter a valid department ID.");
            }
        }
    }

    private static String readWithDefault(String prompt, String defaultValue) {
        System.out.print(prompt);
        String input = scanner.nextLine().trim();
        return input.isEmpty() ? defaultValue : input;
    }
}
