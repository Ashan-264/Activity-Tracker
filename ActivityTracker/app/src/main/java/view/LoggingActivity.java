package view;// ViewModel.LoggingActivity.java
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.*;


import com.example.activitytracker.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import view.DatabaseHelper;

public class LoggingActivity extends AppCompatActivity {

    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logging);

        databaseHelper = new DatabaseHelper(this);

        EditText editTextName = findViewById(R.id.editTextActivityName);
        EditText editTextDate = findViewById(R.id.editTextActivityDate);
        EditText editTextDuration = findViewById(R.id.editTextActivityDuration);
        EditText editTextType = findViewById(R.id.editTextActivityType);
        Button buttonSave = findViewById(R.id.buttonSaveActivity);
        Button buttonSummaryScreen = findViewById(R.id.buttonActivitySummary);

        buttonSave.setOnClickListener(v -> {
            String name = editTextName.getText().toString();
            String date = editTextDate.getText().toString();
            int duration = Integer.parseInt(editTextDuration.getText().toString());
            String type = editTextType.getText().toString();

            saveActivity(name, date, duration, type);
        });

        buttonSummaryScreen.setOnClickListener(v -> {
            Intent intent = new Intent(this, ActivitySummary.class);
            startActivity(intent);
        });

        BottomNavigationView bottomNavigation = findViewById(R.id.bottom_navigation);
        Menu menu = bottomNavigation.getMenu();
        MenuItem homeItem = menu.findItem(R.id.nav_home);
        MenuItem recordItem = menu.findItem(R.id.nav_record);
        MenuItem summaryItem = menu.findItem(R.id.nav_summary);
        recordItem.setChecked(true);

        bottomNavigation.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();
                if (itemId == R.id.nav_home) {
                    Intent homeIntent = new Intent(LoggingActivity.this, GoalSettingActivity.class);
                    startActivity(homeIntent);
                    return true;
                } else if (itemId == R.id.nav_record) {
                    Intent recordIntent = new Intent(LoggingActivity.this, LoggingActivity.class);
                    startActivity(recordIntent);
                    return true;
                } else if (itemId == R.id.nav_summary) {
                    Intent summaryIntent = new Intent(LoggingActivity.this, ActivitySummary.class);
                    startActivity(summaryIntent);
                    return true;
                }
                return false;
            }
        });
    }

    private void saveActivity(String name, String date, int duration, String type) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_NAME, name);
        values.put(DatabaseHelper.COLUMN_DATE, date);
        values.put(DatabaseHelper.COLUMN_DURATION, duration);
        values.put(DatabaseHelper.COLUMN_TYPE, type);
        db.insert(DatabaseHelper.TABLE_NAME, null, values);
    }
}
