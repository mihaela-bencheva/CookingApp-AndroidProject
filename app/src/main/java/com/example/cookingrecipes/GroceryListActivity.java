package com.example.cookingrecipes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class GroceryListActivity extends AppCompatActivity {
    EditText quantity;
    EditText price;
    double sum = 0;
    EditText groceryText;
    TextView productsSum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grocery_list);

        SharedPreferences sharedPref = GroceryListActivity.this.getSharedPreferences("grocery", Context.MODE_PRIVATE);
        String tmpText = sharedPref.getString("groceryText", "");
        sum = sharedPref.getFloat("sum", 0);
        quantity = (EditText) findViewById(R.id.quantity);
        price = (EditText) findViewById(R.id.price);
        groceryText = (EditText) findViewById(R.id.products);
        productsSum = (TextView) findViewById(R.id.sumProducts);
        sum = Math.round(sum * 100.0) / 100.0;
        productsSum.setText(String.valueOf(sum) + "$");
        groceryText.setText(tmpText);

    }

    public void addToSum(View view) {
        sum += Double.parseDouble(quantity.getText().toString()) * Double.parseDouble(price.getText().toString());
        sum = Math.round(sum * 100.0) / 100.0;
        productsSum.setText(String.valueOf(sum) + "$");

        SharedPreferences sharedPref = GroceryListActivity.this.getSharedPreferences("grocery", Context.MODE_PRIVATE);
        SharedPreferences.Editor prefEditor = sharedPref.edit();
        prefEditor.putString("groceryText", groceryText.getText().toString());
        prefEditor.putFloat("sum", (float) sum);
        prefEditor.commit();
    }

    public void deleteSum(View view) {
        sum = 0.0;
        productsSum.setText(String.valueOf(sum) + "$");

        SharedPreferences sharedPref = GroceryListActivity.this.getSharedPreferences("grocery", Context.MODE_PRIVATE);
        SharedPreferences.Editor prefEditor = sharedPref.edit();
        prefEditor.putFloat("sum", (float) sum);
        prefEditor.commit();
    }
}