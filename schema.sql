CREATE TABLE IF NOT EXISTS departments (
    dept_id INTEGER PRIMARY KEY AUTOINCREMENT,
    dept_name TEXT NOT NULL UNIQUE,
    location TEXT NOT NULL
);

CREATE TABLE IF NOT EXISTS employees (
    emp_id TEXT PRIMARY KEY,
    emp_name TEXT NOT NULL,
    salary REAL NOT NULL CHECK (salary >= 0),
    post TEXT NOT NULL,
    email TEXT NOT NULL UNIQUE,
    phone TEXT NOT NULL,
    joining_date TEXT NOT NULL,
    dept_id INTEGER NOT NULL,
    FOREIGN KEY (dept_id) REFERENCES departments(dept_id)
        ON UPDATE CASCADE
        ON DELETE RESTRICT
);
