package com.example.cookingrecipes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private static String DB_NAME = "cookingRecipes";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences sharedPref = MainActivity.this.getSharedPreferences("database2", Context.MODE_PRIVATE);
        String tmpText = sharedPref.getString("flag", "");
        if (tmpText.equals(null) || tmpText.equals("")) {
            initDataBase();
        }
    }

    public void openCategoriesClick(View view) {
        Intent intent = new Intent(this, CategoriesActivity.class);
        startActivity(intent);
    }

    public void openRecipesClick(View view) {
        Intent intent = new Intent(this, RecipesActivity.class);
        startActivity(intent);
    }

    public void createNewGroceryListClick(View view) {
        Intent intent = new Intent(this, GroceryListActivity.class);
        startActivity(intent);
    }

    public void openFavouritesClick(View view) {
        Intent intent = new Intent(this, FavouritesActivity.class);
        startActivity(intent);
    }

    public void initDataBase() {
        try {
            SQLiteDatabase db = this.openOrCreateDatabase(DB_NAME, MODE_PRIVATE, null);
            db.execSQL("CREATE TABLE IF NOT EXISTS recipes (\n" +
                    " title varchar(32) NOT NULL DEFAULT '',\n" +
                    " category  varchar(32)  NOT NULL DEFAULT '',\n" +
                    " products varchar(500)     NOT NULL DEFAULT '',\n" +
                    " description varchar(1000)     NOT NULL DEFAULT '',\n" +
                    " favourites integer     NOT NULL DEFAULT 0\n" +
                    ");");

            db.execSQL("INSERT INTO recipes (title, category, products, description, favourites) VALUES (\"Chicken Soup\", \"soup\", \"chicken \nwater \nsalt\", \"Mix everything and boil for 30 minutes\", 0);");
            db.execSQL("INSERT INTO recipes (title, category, products, description, favourites) VALUES (\"Shkembe Soup\", \"soup\",\"meat \nwater \nsalt\", \"Mix everything and boil for 30 minutes\", 0);");
            db.execSQL("INSERT INTO recipes (title, category, products, description, favourites) VALUES (\"Cesaer Salad\", \"salad\",\"chicken \ntomatoes \ncucumbers\", \"Cut everything and mix\", 1);");
            db.execSQL("INSERT INTO recipes (title, category, products, description, favourites) VALUES (\"Chicken Steak\", \"chicken delicacies\", \"chicken steak \nbarbecue \nsalt\", \"Put in the oven and bake for 20 minutes\", 1);");

            SharedPreferences sharedPref = MainActivity.this.getSharedPreferences("database2", Context.MODE_PRIVATE);
            SharedPreferences.Editor prefEditor = sharedPref.edit();
            prefEditor.putString("flag", "instance");
            prefEditor.commit();
        } catch (Exception e) {
            Log.e("DB Error: ", e.getMessage());
        }
    }
}