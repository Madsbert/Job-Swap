package org.example.jobswap.Persistence.Interfaces;

import java.util.List;

public interface JobCategoryDBInterface {

    List<String> getCategories();

    void addCategory(String categoryName);
}
