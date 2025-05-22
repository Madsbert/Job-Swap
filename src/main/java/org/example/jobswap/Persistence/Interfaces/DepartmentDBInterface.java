package org.example.jobswap.Persistence.Interfaces;

import org.example.jobswap.Model.Department;

import java.util.List;

public interface DepartmentDBInterface {
    static List<Department> getDepartments() {
        return null;
    }

    static void createDepartment(Department department) {

    }

    static void deleteDepartment(int departmentID) {

    }

    static Department getDepartmentFromUserID(int profileID) {
        return null;
    }
}
