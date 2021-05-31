package com.example.cookingrecipes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class FavouritesActivity extends AppCompatActivity {
    List<Recipes> allRecipes;
    List<String> favouriteRecipesNames;
    private static String DB_NAME = "cookingRecipes";
    ListView favouriteRecipes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourites);

        favouriteRecipesNames = new ArrayList<>();
        allRecipes = GetAllFavouriteRecipes();
        favouriteRecipes = (ListView) findViewById(R.id.favouriteRecipe);
        for (int i = 0; i < allRecipes.size(); i++) {
            if (allRecipes.get(i).favourites == 1) {
                favouriteRecipesNames.add(allRecipes.get(i).title);
            }
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                new ArrayList<String>(favouriteRecipesNames)
        );
        favouriteRecipes.setAdapter(adapter);

        favouriteRecipes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long id) {
                String recipeClicked = adapterView.getItemAtPosition(i).toString();

                Intent intent = new Intent(FavouritesActivity.this, RecipeActivity.class);
                intent.putExtra("selected_recipe", recipeClicked);
                startActivity(intent);
            }
        });
    }

    public List<Recipes> GetAllFavouriteRecipes() {
        List<Recipes> recipes = new ArrayList<>();

        try {
            SQLiteDatabase db = this.openOrCreateDatabase(DB_NAME, MODE_PRIVATE, null);

            Cursor cr = db.rawQuery("SELECT title, favourites FROM recipes", null);

            if (cr.moveToFirst()) {
                do {
                    Recipes recipe = new Recipes();

                    recipe.title = cr.getString(cr.getColumnIndex("title"));
                    recipe.favourites = cr.getInt(cr.getColumnIndex("favourites"));

                    recipes.add(recipe);
                } while (cr.moveToNext());
                cr.close();
            }
        } catch (Exception e) {

        }
        return recipes;
    }
}