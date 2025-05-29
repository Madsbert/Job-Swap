package org.example.jobswap.Persistence;

import org.example.jobswap.Foundation.DBConnection;
import org.example.jobswap.Model.Department;
import org.example.jobswap.Persistence.Interfaces.DepartmentDBInterface;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * Class to handle database querys and stored procedures of {@link Department}
 */
public class DepartmentDB implements DepartmentDBInterface {

    /**
     * Returns a {@link List} containing all {@link Department} in the Database.
     * @return {@link List} of {@link Department} Objects.
     */
    public List<Department> getDepartments()
    {
        String query = "select * from tbl_department";


        List<Department> departments = new ArrayList<>();
        try {
            Connection connection = DBConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String departmentName = resultSet.getString("DepartmentName");
                int departmentID = resultSet.getInt("DepartmentID");
                String departmentCity = resultSet.getString("City");
                if (departmentName != null && departmentID > 0 && departmentCity != null) {
                    departments.add(new Department(departmentID, departmentName, departmentCity));
                }
            }
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            System.out.println(e.getCause());
            System.out.println(e.getStackTrace());
        }

        return departments;
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
