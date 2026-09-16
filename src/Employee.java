public class Employee {
    private String empId;
    private String name;
    private double salary;
    private String post;
    private String email;
    private String phone;
    private String joiningDate;
    private int deptId;

    public Employee(String empId, String name, double salary, String post,
                    String email, String phone, String joiningDate, int deptId) {
        this.empId = empId;
        this.name = name;
        this.salary = salary;
        this.post = post;
        this.email = email;
        this.phone = phone;
        this.joiningDate = joiningDate;
        this.deptId = deptId;
    }

    public Employee(String empId, String name, double salary, String post,
                    String email, String phone, String joiningDate) {
        this(empId, name, salary, post, email, phone, joiningDate, 0);
    }

    public String getEmpId() { return empId; }
    public String getName() { return name; }
    public double getSalary() { return salary; }
    public String getPost() { return post; }
    public String getEmail() { return email; }
    public String getPhone() { return phone; }
    public String getJoiningDate() { return joiningDate; }
    public int getDeptId() { return deptId; }

    public void setName(String name) { this.name = name; }
    public void setSalary(double salary) { this.salary = salary; }
    public void setPost(String post) { this.post = post; }
    public void setEmail(String email) { this.email = email; }
    public void setPhone(String phone) { this.phone = phone; }
    public void setJoiningDate(String joiningDate) { this.joiningDate = joiningDate; }
    public void setDeptId(int deptId) { this.deptId = deptId; }

    @Override
    public String toString() {
        return String.format(
            "ID: %s | Name: %s | Salary: %.2f | Post: %s | Email: %s | Phone: %s | Joining Date: %s | Dept ID: %d",
            empId, name, salary, post, email, phone, joiningDate, deptId
        );
    }
}
