public class Department {
    private int deptId;
    private String deptName;
    private String location;

    public Department(int deptId, String deptName, String location) {
        this.deptId = deptId;
        this.deptName = deptName;
        this.location = location;
    }

    public Department(String deptName, String location) {
        this(0, deptName, location);
    }

    public int getDeptId() { return deptId; }
    public String getDeptName() { return deptName; }
    public String getLocation() { return location; }

    @Override
    public String toString() {
        return String.format("ID: %d | Department: %s | Location: %s",
                deptId, deptName, location);
    }
}
