package com.example.cookingrecipes;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Scanner;

public class AddRecipeActivity extends AppCompatActivity {
    String recipe_title;
    String category;
    String products;
    String description;
    private static String DB_NAME = "cookingRecipes";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_recipe);
    }

    public void addNewRecipe(View view) {
        recipe_title = ((EditText) findViewById(R.id.newrecipe)).getText().toString();
        category = ((EditText) findViewById(R.id.category_recipe)).getText().toString();
        products = ((EditText) findViewById(R.id.newrecipe_products)).getText().toString();
        description = ((EditText) findViewById(R.id.newrecipe_description)).getText().toString();

        boolean isNew = true;
        PrintStream output;
        Scanner scan = new Scanner(getResources().openRawResource(R.raw.categories));
        while (scan.hasNextLine()) {
            String line = scan.nextLine();
            if (line.equals(category)) {
                isNew = false;
            }
        }
        scan.close();

        if(isNew) {
            try {
                Scanner scan2 = new Scanner(openFileInput("added_categories.txt"));
                while (scan2.hasNextLine()) {
                    String line = scan2.nextLine();
                    if (line.equals(category)) {
                        isNew = false;
                    }
                }
                scan2.close();

                if(isNew) {
                    output = new PrintStream(openFileOutput("added_categories.txt", MODE_APPEND));
                    output.println(category);
                    output.close();
                }

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        try {
            SQLiteDatabase db = this.openOrCreateDatabase(DB_NAME, MODE_PRIVATE, null);

            db.execSQL("INSERT INTO recipes (title, category, products, description, favourites) VALUES ('"+recipe_title+"', '"+category+"', '"+products+"', '"+description+"', 0);");

            finish();
        } catch (Exception e) {
            Log.wtf("DB Error: ", e.getMessage());
        }
    }
}