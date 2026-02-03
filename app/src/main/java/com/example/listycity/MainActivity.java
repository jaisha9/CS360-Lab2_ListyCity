package com.example.listycity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.app.AlertDialog;
import android.content.DialogInterface;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    ListView cityList;
    ArrayAdapter<String> cityAdapter;
    ArrayList<String> dataList;

    Button addButton;
    Button deleteButton;
    int selectedIndex = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        cityList = findViewById(R.id.cityList);
        addButton = findViewById(R.id.addButton);
        deleteButton = findViewById(R.id.deleteButton);

        String[] cities = {"Edmonton", "Vancouver", "Moscow","Sydney", "Berlin", "Vienna", "Beijing", "Tokyo", "Osaka", "New Delhi"};

        dataList = new ArrayList<>();
        dataList.addAll(Arrays.asList(cities));

        cityAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_activated_1,
                dataList
        );

        cityList.setAdapter(cityAdapter);
        cityList.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

        cityList.setOnItemClickListener((parent, view, position, id) -> {
            selectedIndex = position;
            cityList.setItemChecked(position, true);
        });

        addButton.setOnClickListener(v -> showAddCityDialog());

        deleteButton.setOnClickListener(v ->
        {
            if (selectedIndex != -1)
            {
                dataList.remove(selectedIndex);
                selectedIndex = -1;
                cityList.clearChoices();
                cityAdapter.notifyDataSetChanged();
            }
        });
    }

    private void showAddCityDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Add City");

        final EditText input = new EditText(this);
        input.setHint("Enter city name");
        builder.setView(input);

        builder.setPositiveButton("Confirm", (dialog, which) ->
        {
            String newCity = input.getText().toString().trim();
            if (!newCity.isEmpty())
            {
                dataList.add(newCity);
                cityAdapter.notifyDataSetChanged();
            }
        });

        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());

        builder.show();
    }
}
