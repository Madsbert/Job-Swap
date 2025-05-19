package org.example.jobswap.Persistence.Interfaces;

import org.example.jobswap.Model.Department;

import java.util.List;

public interface DepartmentDBInterface {
    List<Department> getDepartments();
    void createDepartment(Department department);
    void deleteDepartment(int departmentID);
    Department getDepartmentFromUserID(int profileID);
}
