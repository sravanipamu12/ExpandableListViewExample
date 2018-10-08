package expandablelv.example.com.expandablelistviewexample;

import java.io.Serializable;
import java.util.ArrayList;

public class Industries implements Serializable {
    private String industryName;
    private ArrayList<Category> categoryList;

    public void setIndustryName(String industryName) {
        this.industryName = industryName;
    }

    public String getIndustryName() {
        return industryName;
    }

    public void setCategoryList(ArrayList<Category> categoryList) {
        this.categoryList = categoryList;
    }

    public ArrayList<Category> getCategoryList() {
        return categoryList;
    }
}
