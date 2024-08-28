package view;

import android.widget.CompoundButton;
import android.widget.Switch;

import androidx.appcompat.app.AppCompatDelegate;

public class AppUtils {
    public static void initThemeSwitcher(Switch switchButton) {
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
}
