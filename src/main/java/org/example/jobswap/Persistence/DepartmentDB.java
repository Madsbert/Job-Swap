package org.example.jobswap.Persistence;

import org.example.jobswap.Model.Department;
import org.example.jobswap.Persistence.Interfaces.DepartmentDBInterface;

import java.util.List;

public class DepartmentDB implements DepartmentDBInterface {

    public List<Department> getDepartments()
    {
        return null;
    }

    public void createDepartment(Department department)
    {
        // Not implemented
    }

    public void deleteDepartment(int departmentID)
    {
        // Not implemented
    }

    public Department getDepartmentFromUserID(int profileID)
    {
        return null;
    }
}
