
# DigiCorp REST API – Endpoint Guide

This guide describes the four main REST endpoints in the DigiCorp HR Management System.

---

## Endpoint 1: List All Departments

**Endpoint:** GET /departments

**Description:** Returns all departments in the system.

**Response Example:**
```json
    [
      {
        "deptNo": "d001",
        "deptName": "Sales"
      },
      {
        "deptNo": "d002",
        "deptName": "Engineering"
      }
    ]
```
---



## Endpoint 2: Get Employee by Employee Number

**Endpoint:** GET /employees/{empNo}

**Description:** Retrieves a single employee along with their department, title, salary, and managerial history.

**Path Parameter:**
- empNo: Employee number (positive integer)

**Response Example:**
```json
    {
      "empNo": 10004,
      "firstName": "John",
      "lastName": "Doe",
      "hireDate": "2020-01-15",
      "departments": [...],
      "titles": [...],
      "salaries": [...],
      "managedDepartments": [...]
    }
```

**Error Responses:**
- 400 Bad Request – if empNo is invalid
- 404 Not Found – if employee does not exist



## Endpoint 3: Get Employees by Department

**Endpoint:** GET /employees/by-department?deptNo=<deptNo>&page=<page>

**Description:** Returns a paginated list of employees for a given department.

**Query Parameters:**
- deptNo: Department number (e.g., d001)
- page: Optional page number (default = 1)

**Response Example:**
```json
    [
      {
        "empNo": 10001,
        "firstName": "Alice",
        "lastName": "Smith",
        "hireDate": "2018-06-01"
      },
      {
        "empNo": 10005,
        "firstName": "Bob",
        "lastName": "Johnson",
        "hireDate": "2019-09-15"
      }
    ]
```
**Error Responses:**
- 400 Bad Request – missing or invalid deptNo
- 404 Not Found – if the department does not exist



## Endpoint 4: Promote Employee

**Endpoint:** POST /employees/promote

**Description:** Updates an employee’s title, salary, department, and/or manager status. Validates against history records to ensure consistency.

**Request Body Example:**
```json
    {
      "empNo": 10004,
      "newTitle": "Manager",
      "fromDate": "2025-12-11",
      "salary": 1000278,
      "deptNo": "d001",
      "manager": true
    }
```
**Fields:**
- empNo: Employee number (positive integer)
- newTitle: New job title (cannot be blank)
- fromDate: Effective date (yyyy-MM-dd)
- salary: New salary (greater than 0)
- deptNo: Department number (format: dXXX, e.g., d001)
- manager: Whether the employee becomes a manager

**Response Example:**
```json
    {
      "message": "Promotion successful for empNo 10004"
    }
```
**Error Responses:**
- 400 Bad Request – if validation fails (missing fields, invalid dates, or salary <= 0)



## Notes

- All endpoints use JSON for input and output.
- Pagination defaults to 20 results per page for employee lists.
- Promotion endpoint enforces historical consistency: fromDate must be after the last salary/title/department change.
- For POST /employees/promote, make sure all fields are valid and the JSON body is properly formatted.