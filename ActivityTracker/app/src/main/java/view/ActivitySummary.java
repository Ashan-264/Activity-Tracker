package view;// SummaryActivity.java
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.activitytracker.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;
import java.util.List;

import view.Activity;

public class ActivitySummary extends AppCompatActivity {

    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);

        databaseHelper = new DatabaseHelper(this);

        TextView textViewSummary = findViewById(R.id.textViewSummary);
        Button buttonShowSummary = findViewById(R.id.buttonShowSummary);
        Button buttonLoggingActivity = findViewById(R.id.buttonLoggingActivity);

        buttonShowSummary.setOnClickListener(v -> textViewSummary.setText(getSummary()));

        buttonLoggingActivity.setOnClickListener(v -> {
            Intent intent = new Intent(this, LoggingActivity.class);
            startActivity(intent);
        });

        BottomNavigationView bottomNavigation = findViewById(R.id.bottom_navigation);
        Menu menu = bottomNavigation.getMenu();
        MenuItem homeItem = menu.findItem(R.id.nav_home);
        MenuItem recordItem = menu.findItem(R.id.nav_record);
        MenuItem summaryItem = menu.findItem(R.id.nav_summary);
        summaryItem.setChecked(true);


        bottomNavigation.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();
                if (itemId == R.id.nav_home) {
                    Intent homeIntent = new Intent(ActivitySummary.this, GoalSettingActivity.class);
                    startActivity(homeIntent);
                    return true;
                } else if (itemId == R.id.nav_record) {
                    Intent recordIntent = new Intent(ActivitySummary.this, LoggingActivity.class);
                    startActivity(recordIntent);
                    return true;
                } else if (itemId == R.id.nav_summary) {
                    Intent summaryIntent = new Intent(ActivitySummary.this, ActivitySummary.class);
                    startActivity(summaryIntent);
                    return true;
                }
                return false;
            }
        });


    }

    private String getSummary() {
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        Cursor cursor = db.query(
                DatabaseHelper.TABLE_NAME,
                null, null, null, null, null, null
        );

        List<Activity> activities = new ArrayList<>();
        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_ID));
            String name = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_NAME));
            String date = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_DATE));
            int duration = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_DURATION));
            String type = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_TYPE));

            activities.add(new Activity(id, name, date, duration, type));
        }
        cursor.close();

        StringBuilder summary = new StringBuilder();
        for (Activity activity : activities) {
            summary.append("\nDate : ")
                    .append(activity.getDate())
                    .append("\nActivity Name: ")
                    .append(activity.getName())
                    .append("\nActivity Type:  ")
                    .append(activity.getType())
                    .append("\nDuration (min): ")
                    .append(activity.getDuration())
                    .append("mins\n");
        }
        return summary.toString();
    }
}
