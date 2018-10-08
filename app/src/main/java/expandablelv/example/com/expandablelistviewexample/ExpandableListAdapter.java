package expandablelv.example.com.expandablelistviewexample;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

public class ExpandableListAdapter extends BaseExpandableListAdapter {

    private Context _context;
    private ArrayList<Industries> industriesList; // header titles
    private ICheckBoxListner iCheckBoxListner;


    public ExpandableListAdapter(Context context, ArrayList<Industries> industriesList, ICheckBoxListner listner) {
        this._context = context;
        this.industriesList = industriesList;
        iCheckBoxListner = listner;

    }

    @Override
    public Object getChild(int groupPosition, int childPosititon) {
        return this.industriesList.get(groupPosition).getCategoryList().get(childPosititon).getCategoryName();
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(final int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {

        final String childText = (String) getChild(groupPosition, childPosition);

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_item, null);
        }

        TextView txtListChild = (TextView) convertView
                .findViewById(R.id.lblListItem);
        CheckBox cbItem = (CheckBox) convertView
                .findViewById(R.id.cbItem);
        txtListChild.setText(childText);
        cbItem.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isCheked) {
                iCheckBoxListner.onCheckBoxClicked(groupPosition, childPosition, isCheked);
            }
        });
        if (industriesList.get(groupPosition).getCategoryList().get(childPosition).getChecked()) {
            cbItem.setChecked(true);
        } else {
            cbItem.setChecked(false);
        }
        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return this.industriesList.get(groupPosition).getCategoryList().size();

    }

    @Override
    public Object getGroup(int groupPosition) {
        return this.industriesList.get(groupPosition).getIndustryName();
    }

    @Override
    public int getGroupCount() {
        return this.industriesList.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        String headerTitle = (String) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_group, null);
        }

        TextView lblListHeader = (TextView) convertView
                .findViewById(R.id.lblListHeader);
        lblListHeader.setTypeface(null, Typeface.BOLD);
        lblListHeader.setText(headerTitle);

        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
