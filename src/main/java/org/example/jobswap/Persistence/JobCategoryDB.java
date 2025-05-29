package org.example.jobswap.Persistence;

import org.example.jobswap.Foundation.DBConnection;
import org.example.jobswap.Model.Department;
import org.example.jobswap.Model.Profile;
import org.example.jobswap.Persistence.Interfaces.JobCategoryDBInterface;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * Class to handle database querys and stored procedures of Jobcategory
 */
public class JobCategoryDB implements JobCategoryDBInterface {

    /**
     * Returns a {@link List} containing {@link String}s of JobCategory names from the database.
     * @return {@link List} of {@link String}s, Job Categories
     */
    public List<String> getCategories()
    {
        String query = "select * from tbl_JobCategory";

        List<String> jobCategories = new ArrayList<>();
        try {
            Connection connection = DBConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                jobCategories.add(resultSet.getString("JobCategory"));
            }
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            System.out.println(e.getCause());
            System.out.println(e.getStackTrace());
        }

        return jobCategories;
    }

    public void addCategory(String categoryName)
    {
        // Not implemented
    }
}
