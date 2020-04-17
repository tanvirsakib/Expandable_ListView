package com.example.expandablelistview;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ExpandableListView exapandableList;

    List<String> listDataHeader;
    HashMap<String,List<String>> listDataChild;
    int lastExpandedPosition = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        exapandableList = findViewById(R.id.expandAbleListView);
        prepareData();

        CustomAdapter customAdapter = new CustomAdapter(this,listDataHeader,listDataChild);
        exapandableList.setAdapter(customAdapter);

        exapandableList.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                String groupName = listDataHeader.get(groupPosition);
                Toast.makeText(getApplicationContext(),"Group: "+groupName,Toast.LENGTH_SHORT).show();
                return false;
            }
        });

        exapandableList.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {
            @Override
            public void onGroupCollapse(int groupPosition) {
                String groupName = listDataHeader.get(groupPosition);
                Toast.makeText(getApplicationContext(),groupName+" is collapsed",Toast.LENGTH_SHORT).show();
            }
        });

        exapandableList.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                String childName = listDataChild.get(listDataHeader.get(groupPosition)).get(childPosition);
                Toast.makeText(getApplicationContext(),childName,Toast.LENGTH_SHORT).show();
                return false;
            }
        });

        exapandableList.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
                if (lastExpandedPosition != -1 && lastExpandedPosition!= groupPosition){
                    exapandableList.collapseGroup(lastExpandedPosition);
                }
                lastExpandedPosition= groupPosition;
            }
        });



    }

    public void prepareData(){

        String[] header = getResources().getStringArray(R.array.list_header);
        String[] child = getResources().getStringArray(R.array.list_child);

        listDataHeader = new ArrayList<>();
        listDataChild = new HashMap<>();

        for (int i=0; i<header.length; i++){
            listDataHeader.add(header[i]);

            List<String> childlist = new ArrayList<>();
            childlist.add(child[i]);

            listDataChild.put(listDataHeader.get(i),childlist);
        }

    }
}
