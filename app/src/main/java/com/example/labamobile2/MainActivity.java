package com.example.labamobile2;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import com.example.labamobile2.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Підключення ViewBinding
        ActivityMainBinding  binding = ActivityMainBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());

        // Налаштування AppBarConfiguration для вашого навігаційного меню
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, // Домашня сторінка
                R.id.navigation_dashboard, // Сторінка з автомобілями
                R.id.navigation_notifications // Сторінка з контактами
        ).build();

        // Налаштування NavController для навігації між фрагментами
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);

        // Підключення ActionBar до NavController
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);

        // Підключення нижньої панелі навігації (BottomNavigationView) до NavController
        NavigationUI.setupWithNavController(binding.navView, navController);
    }
}
