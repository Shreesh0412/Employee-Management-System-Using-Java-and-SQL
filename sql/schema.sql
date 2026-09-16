CREATE TABLE IF NOT EXISTS departments (
    dept_id INTEGER PRIMARY KEY AUTOINCREMENT,
    dept_name TEXT NOT NULL UNIQUE,
    location TEXT NOT NULL
);

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
);
