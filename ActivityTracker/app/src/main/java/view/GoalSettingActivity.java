package view;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.example.activitytracker.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;
import java.util.List;

public class GoalSettingActivity extends AppCompatActivity {
    private Goaldb goaldb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goal_setting);

        EditText editTextGoal = findViewById(R.id.editTextGoal);
        Button buttonSaveGoal = findViewById(R.id.buttonSaveGoal);

        buttonSaveGoal.setOnClickListener(v -> {
            String goal = editTextGoal.getText().toString();
            saveGoal(goal);
        });

        goaldb = new Goaldb(this);

        TextView textViewGoals = findViewById(R.id.textViewGoals);
        Button buttonShowGoals = findViewById(R.id.buttonViewGoal);

        buttonShowGoals.setOnClickListener(v -> textViewGoals.setText(getGoals()));

        BottomNavigationView bottomNavigation = findViewById(R.id.bottom_navigation);
        Menu menu = bottomNavigation.getMenu();
        MenuItem homeItem = menu.findItem(R.id.nav_home);
        MenuItem recordItem = menu.findItem(R.id.nav_record);
        MenuItem summaryItem = menu.findItem(R.id.nav_summary);
        homeItem.setChecked(true);

        bottomNavigation.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();
                if (itemId == R.id.nav_home) {
                    Intent homeIntent = new Intent(GoalSettingActivity.this, GoalSettingActivity.class);
                    startActivity(homeIntent);
                    return true;
                } else if (itemId == R.id.nav_record) {
                    Intent recordIntent = new Intent(GoalSettingActivity.this, LoggingActivity.class);
                    startActivity(recordIntent);
                    return true;
                } else if (itemId == R.id.nav_summary) {
                    Intent summaryIntent = new Intent(GoalSettingActivity.this, ActivitySummary.class);
                    startActivity(summaryIntent);
                    return true;
                }
                return false;
            }
        });

        Switch switchButton = findViewById(R.id.tglTheme);
        switchButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                }
            }
        });
    }

    private void saveGoal(String goal) {
        SQLiteDatabase db = goaldb.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Goaldb.COLUMN_GOAL, goal);
        db.insert(Goaldb.TABLE_NAME, null, values);
    }

    private String getGoals() {
        SQLiteDatabase db = goaldb.getReadableDatabase();
        Cursor cursor = db.query(
                Goaldb.TABLE_NAME,
                null, null, null, null, null, null
        );

        List<Goal> goalList = new ArrayList<>();
        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndexOrThrow(Goaldb.COLUMN_ID));
            String goals = cursor.getString(cursor.getColumnIndexOrThrow(Goaldb.COLUMN_GOAL));

            goalList.add(new Goal(id, goals));
        }
        cursor.close();

        StringBuilder summary = new StringBuilder();
        for (Goal goal : goalList) {
            summary.append("\nGoal: ")
                    .append(goal.getGoal())
                    .append("\n");
        }
        return summary.toString();
    }


}

