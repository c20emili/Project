package com.example.appproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class about extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menu_item_about) {
            return true;
        }
        if (id == R.id.menu_item_huvudsida) {
            finish();
            Intent main = new Intent(about.this, MainActivity.class);
            startActivity(main);
            return true;
        }
        if (id == R.id.menu_item_3) {
            finish();
            return true;
        }
        if (id == R.id.menu_item_4) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}