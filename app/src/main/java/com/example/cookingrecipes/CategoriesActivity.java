package com.example.cookingrecipes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CategoriesActivity extends AppCompatActivity {
    List<String> categories;
    ListView category;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);

        categories = new ArrayList<>();
        readFileData();
        category = (ListView) findViewById(R.id.category);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                new ArrayList<String>(categories)
        );
        category.setAdapter(adapter);

        category.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long id) {
                String categoryClicked = adapterView.getItemAtPosition(i).toString();

                Intent intent = new Intent(CategoriesActivity.this, RecipesActivity.class);
                intent.putExtra("selected_category", categoryClicked);
                startActivity(intent);
            }
        });
    }

    private void readFileData() {
        Scanner scan = new Scanner(getResources().openRawResource(R.raw.categories));
        readFileHelper(scan);
        scan.close();

        try {
            Scanner scan2 = new Scanner(openFileInput("added_categories.txt"));
            readFileHelper(scan2);
            scan2.close();
        } catch (Exception e) {
        }
    }

    private void readFileHelper(Scanner scan) {
        while (scan.hasNextLine()) {
            String line = scan.nextLine();
            categories.add(line);
        }
    }
}