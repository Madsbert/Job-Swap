package org.example.jobswap.Persistence.Interfaces;

import java.util.List;

public interface JobCategoryDBInterface {

    public List<String> getCategories();

    public void addCategory(String categoryName);
}
