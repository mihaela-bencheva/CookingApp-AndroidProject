package com.example.cookingrecipes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Scanner;

public class UpdateRecipe extends AppCompatActivity {

    String recipe_title;
    String category;
    String products;
    String description;

    TextView recipe;
    EditText recipe_category;
    EditText recipe_products;
    EditText recipe_description;

    private static String DB_NAME = "cookingRecipes";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_recipe);

        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            recipe_title = extras.getString("update_recipe_title");
            category = extras.getString("update_recipe_category");
            products = extras.getString("update_recipe_products");
            description = extras.getString("update_recipe_description");

            recipe = (TextView) findViewById(R.id.current_recipe);
            recipe.setText(recipe_title);

            recipe_category = (EditText) findViewById(R.id.category_current_recipe);
            recipe_category.setText(category);

            recipe_products = (EditText) findViewById(R.id.current_recipe_products);
            recipe_products.setText(products);

            recipe_description = (EditText) findViewById(R.id.current_recipe_description);
            recipe_description.setText(description);
        }
    }

    public void updateRecipe(View view) {
        String categoryEdit = recipe_category.getText().toString();

        boolean isNew = true;
        PrintStream output;
        Scanner scan = new Scanner(getResources().openRawResource(R.raw.categories));
        while (scan.hasNextLine()) {
            String line = scan.nextLine();
            if (line.equals(categoryEdit)) {
                isNew = false;
            }
        }
        scan.close();

        if(isNew) {
            try {
                Scanner scan2 = new Scanner(openFileInput("added_categories.txt"));
                while (scan2.hasNextLine()) {
                    String line = scan2.nextLine();
                    if (line.equals(categoryEdit)) {
                        isNew = false;
                    }
                }
                scan2.close();

                if(isNew) {
                    output = new PrintStream(openFileOutput("added_categories.txt", MODE_APPEND));
                    output.println(categoryEdit);
                    output.close();
                }

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        try {
            SQLiteDatabase db = this.openOrCreateDatabase(DB_NAME, MODE_PRIVATE, null);

            ContentValues values = new ContentValues();
            values.put("category", recipe_category.getText().toString());
            values.put("products", recipe_products.getText().toString());
            values.put("description", recipe_description.getText().toString());

            db.update("recipes", values, "title=?", new String[] {recipe_title});

            finish();
        } catch (Exception e) {
            Log.wtf("DB Error: ", e.getMessage());
        }
    }
}