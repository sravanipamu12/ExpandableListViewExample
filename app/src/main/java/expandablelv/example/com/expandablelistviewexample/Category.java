package expandablelv.example.com.expandablelistviewexample;

import java.io.Serializable;
import java.util.ArrayList;

public class Category implements Serializable {
    private String categoryName;
    private boolean isChecked;

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public boolean getChecked() {
        return isChecked;
    }
}
