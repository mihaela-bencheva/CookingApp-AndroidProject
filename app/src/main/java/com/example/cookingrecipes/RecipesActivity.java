package com.example.cookingrecipes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class RecipesActivity extends AppCompatActivity {
    List<String> recipesNames;
    private static String DB_NAME = "cookingRecipes";
    String category_name;

    ListView recipes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipes);

        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            category_name = extras.getString("selected_category");
            recipesNames = GetRecipesByCategory(category_name);

            ((TextView) findViewById(R.id.recipes_title)).setText(category_name);
        } else {
            recipesNames = GetAllRecipesNames();
        }

        recipes = (ListView) findViewById(R.id.recipe);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                new ArrayList<String>(recipesNames)
        );
        recipes.setAdapter(adapter);

        recipes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long id) {
                String recipeClicked = adapterView.getItemAtPosition(i).toString();

                Intent intent = new Intent(RecipesActivity.this, RecipeActivity.class);
                intent.putExtra("selected_recipe", recipeClicked);
                startActivity(intent);
            }
        });
    }

    public void addNewRecipe(View view) {
        Intent intent = new Intent(this, AddRecipeActivity.class);
        if (category_name != null) {
            intent.putExtra("hint_category", category_name);
        }

        startActivity(intent);
    }

    public List<String> GetAllRecipesNames() {
        List<String> recipes = new ArrayList<>();

        try {
            SQLiteDatabase db = this.openOrCreateDatabase(DB_NAME, MODE_PRIVATE, null);

            Cursor cr = db.rawQuery(
                    "SELECT title FROM recipes", null
            );

            if (cr.moveToFirst()) {
                do {
                    String recipeName;
                    recipeName = cr.getString(cr.getColumnIndex("title"));

                    recipes.add(recipeName);
                } while (cr.moveToNext());
                cr.close();
            }
        } catch (Exception e) {
            //Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }

        return recipes;
    }

    private List<String> GetRecipesByCategory(String category) {
        List<String> recipes = new ArrayList<>();

        try {
            SQLiteDatabase db = this.openOrCreateDatabase(DB_NAME, MODE_PRIVATE, null);

            Cursor cr = db.rawQuery(
                    "SELECT title FROM recipes WHERE category= '" + category + "'", null
            );

            if (cr.moveToFirst()) {
                do {
                    String recipeName;
                    recipeName = cr.getString(cr.getColumnIndex("title"));

                    recipes.add(recipeName);
                } while (cr.moveToNext());
                cr.close();
            }
        } catch (Exception e) {
            //Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }

        return recipes;
    }

    public void openHomePageClick(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}