package expandablelv.example.com.expandablelistviewexample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity implements ICheckBoxListner

{
    ExpandableListView expListView;
    ArrayList<Industries> listDataHeader = new ArrayList<Industries>();
    ;
    ArrayList<Industries> selectedListDataHeader = new ArrayList<>();
    ArrayList<String> industryList = new ArrayList<>();
    ArrayList<String> categoryList = new ArrayList<>();

    ExpandableListAdapter listAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        expListView = (ExpandableListView) findViewById(R.id.lvExp);
        Button btnClick = (Button) findViewById(R.id.btnClick);
        btnClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                industryList.clear();
                categoryList.clear();
                selectedListDataHeader.clear();
                getSelectedList();
                convertListToStringList();
                Log.d("industry details-->", industryList.toString());
                Log.d("category details-->", categoryList.toString());
            }
        });
        try {
            JSONArray obj = new JSONArray(loadJSONFromAsset());
            prepareListData(obj);
            Log.d("Details-->", obj.toString());

            listAdapter = new ExpandableListAdapter(this, listDataHeader, this);

            // setting list adapter
            expListView.setAdapter(listAdapter);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public String loadJSONFromAsset() {
        String json = null;
        try {
            InputStream is = MainActivity.this.getAssets().open("industries.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    private void prepareListData(JSONArray jsonArray) {


        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                Industries industries = new Industries();
                industries.setIndustryName(jsonArray.getJSONObject(i).getString("industryName"));
                JSONArray jsonCategoryList = jsonArray.getJSONObject(i).getJSONArray("Categories");
                ArrayList<Category> catList = new ArrayList<>();
                for (int j = 0; j < jsonCategoryList.length(); j++) {
                    Category category = new Category();
                    category.setCategoryName(jsonCategoryList.getJSONObject(j).getString("industryName"));
                    catList.add(category);
                    category.setChecked(false);
                }
                industries.setCategoryList(catList);
                listDataHeader.add(industries);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    public void onCheckBoxClicked(int groupPosition, int childPosition, boolean isChecked) {
        listDataHeader.get(groupPosition).getCategoryList().get(childPosition).setChecked(isChecked);
        listAdapter.notifyDataSetChanged();
    }

    public void convertListToStringList() {
        for (Industries industries : selectedListDataHeader) {

            //  Log.d("category size->", ""+industries.getCategoryList().size());

            if (industries.getCategoryList().size() > 0) {
                for (Category category : industries.getCategoryList()) {
                    if (category.getCategoryName() != null) {
                        //  Log.d("category name", category.getCategoryName());
                        categoryList.add(category.getCategoryName());
                    }
                }
                // Log.d("industry name ->", ""+industries.getIndustryName());
                industryList.add(industries.getIndustryName());
            }
        }
    }

    public void getSelectedList() {
        for (Industries industry :
                listDataHeader) {
            Industries selectedIndustry = new Industries();

            selectedIndustry.setIndustryName(industry.getIndustryName());

            ArrayList<Category> categoryArrayList = industry.getCategoryList();
            ArrayList<Category> selectedCategoryArrayList = new ArrayList<>();
            for (Category category :
                    categoryArrayList) {

                if (category.getChecked()) {
                    Category selectedCategory = new Category();
                    selectedCategory.setCategoryName(category.getCategoryName());
                    selectedCategoryArrayList.add(selectedCategory);
                }

            }
            selectedIndustry.setCategoryList(selectedCategoryArrayList);
            selectedListDataHeader.add(selectedIndustry);
        }
    }
}
