package com.example.cookingrecipes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class RecipeActivity extends AppCompatActivity {
    String recipe_title;
    Recipes recipe;
    TextView recipe_name;
    TextView category;
    TextView products;
    TextView description;
    Button addToFav;
    private static String DB_NAME = "cookingRecipes";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            recipe_title = extras.getString("selected_recipe");

            getRecipeByTitle();

            recipe_name = (TextView) findViewById(R.id.recipe_title);
            category = (TextView) findViewById(R.id.recipe_category);
            products = (TextView) findViewById(R.id.recipe_products);
            description = (TextView) findViewById(R.id.recipe_description);
            addToFav = (Button) findViewById(R.id.addToFav);

            recipe_name.setText(recipe.title);
            category.setText(recipe.category);
            products.setText(recipe.products);
            description.setText(recipe.description);


            if (recipe.favourites == 1) {
                addToFav.setText("Remove From Favourites");
            } else {
                addToFav.setText("Add To Favourites");
            }
        }
    }

    public void getRecipeByTitle() {
        try {
            SQLiteDatabase db = this.openOrCreateDatabase(DB_NAME, MODE_PRIVATE, null);

            Cursor cr = db.rawQuery(
                    "SELECT * FROM recipes WHERE title = '" + recipe_title + "'", null
            );

            if (cr.moveToFirst()) {
                do {
                    recipe = new Recipes();
                    recipe.title = cr.getString(cr.getColumnIndex("title"));
                    recipe.category = cr.getString(cr.getColumnIndex("category"));
                    recipe.products = cr.getString(cr.getColumnIndex("products"));
                    recipe.description = cr.getString(cr.getColumnIndex("description"));
                    recipe.favourites = cr.getInt(cr.getColumnIndex("favourites"));
                } while (cr.moveToNext());
                cr.close();
            }
        } catch (Exception e) {

        }
    }

    public void deleteRecipe(View view) {
        try {
            SQLiteDatabase db = this.openOrCreateDatabase(DB_NAME, MODE_PRIVATE, null);

            db.delete("recipes", "title =?", new String[] {recipe.title});
            finish();
        } catch (Exception e) {
            Log.wtf("DB Error: ", e.getMessage());
        }
    }

    public void editRecipe(View view) {
        Intent intent = new Intent(this, UpdateRecipe.class);
        intent.putExtra("update_recipe_title", recipe.title);
        intent.putExtra("update_recipe_category", recipe.category);
        intent.putExtra("update_recipe_products", recipe.products);
        intent.putExtra("update_recipe_description", recipe.description);
        startActivity(intent);
    }

    public void addRecipeToFavourite(View view) {
        if (recipe.favourites == 0) {
            recipe.favourites = 1;
            addToFav.setText("Remove From Favourites");
        } else {
            recipe.favourites = 0;
            addToFav.setText("Add To Favourites");
        }

        try {
            SQLiteDatabase db = this.openOrCreateDatabase(DB_NAME, MODE_PRIVATE, null);

            ContentValues values = new ContentValues();
            values.put("favourites", recipe.favourites);

            db.update("recipes", values, "title=?", new String[] {recipe.title});
        } catch (Exception e) {
            Log.wtf("DB Error: ", e.getMessage());
        }
    }

    public void addProductsToGroceryList(View view) {
        SharedPreferences sharedPref = RecipeActivity.this.getSharedPreferences("grocery", Context.MODE_PRIVATE);
        SharedPreferences.Editor prefEditor = sharedPref.edit();
        prefEditor.putString("groceryText", recipe.products);
        prefEditor.commit();
    }
}