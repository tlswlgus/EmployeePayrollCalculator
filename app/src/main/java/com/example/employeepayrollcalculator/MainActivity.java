package com.example.employeepayrollcalculator;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    Spinner spinnerEmpId, spinnerPosition;
    Spinner spinnerDaysWorked;

    TextView txtEmpName;
    RadioGroup rgStatus;
    Button btnCompute, btnClear;

    HashMap<String, String> employeeNames = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        spinnerEmpId = findViewById(R.id.spinnerEmpId);
        spinnerPosition = findViewById(R.id.spinnerPosition);
        txtEmpName = findViewById(R.id.txtEmpName);
        rgStatus = findViewById(R.id.rgStatus);
        spinnerDaysWorked = findViewById(R.id.spinnerDaysWorked);
        btnCompute = findViewById(R.id.btnCompute);
        btnClear = findViewById(R.id.btnClear);

        // Employee list
        String[] empIds = {"H2H2401", "H2H2402", "H2H2403", "H2H2404", "H2H2405"};
        String[] empNames = {"Ian", "Stella", "Jiwoo", "A-na", "Carmen"};
        for (int i = 0; i < empIds.length; i++) {
            employeeNames.put(empIds[i], empNames[i]);
        }

        ArrayAdapter<String> empAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, empIds);
        empAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerEmpId.setAdapter(empAdapter);

        spinnerEmpId.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                String selectedId = empIds[position];
                txtEmpName.setText(employeeNames.get(selectedId));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) { }
        });

        Integer[] days = new Integer[30];
        for (int i = 0; i < 30; i++) {
            days[i] = i + 1;
        }
        ArrayAdapter<Integer> daysAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, days);
        daysAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDaysWorked.setAdapter(daysAdapter);


        // Position list
        String[] positions = {"A", "B", "C"};
        ArrayAdapter<String> posAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, positions);
        posAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerPosition.setAdapter(posAdapter);

        btnCompute.setOnClickListener(v -> computePayroll());
        btnClear.setOnClickListener(v -> clearFields());
    }

    private void computePayroll() {
        String empId = spinnerEmpId.getSelectedItem().toString();
        String name = txtEmpName.getText().toString();
        String posCode = spinnerPosition.getSelectedItem().toString();
        int selectedId = rgStatus.getCheckedRadioButtonId();
        RadioButton rb = findViewById(selectedId);
        if (rb == null) {
            Toast.makeText(this, "Select Civil Status", Toast.LENGTH_SHORT).show();
            return;
        }
        String civilStatus = rb.getText().toString();
        int daysWorked = (int) spinnerDaysWorked.getSelectedItem();

        double rate = 0, taxRate = 0, sssRate = 0;

        // Rate/day by position
        switch (posCode) {
            case "A": rate = 500; break;
            case "B": rate = 400; break;
            case "C": rate = 300; break;
        }

        // Tax rate by civil status
        if (civilStatus.equals("Single")) taxRate = 0.10;
        else taxRate = 0.05;

        double basicPay = rate * daysWorked;

        // SSS rate by basic pay range
        if (basicPay >= 10000) sssRate = 0.07;
        else if (basicPay >= 5000) sssRate = 0.05;
        else if (basicPay >= 1000) sssRate = 0.03;
        else sssRate = 0.01;

        double sss = basicPay * sssRate;
        double tax = basicPay * taxRate;
        double netPay = basicPay - (sss + tax);

        Intent intent = new Intent(MainActivity.this, SummaryActivity.class);
        intent.putExtra("empId", empId);
        intent.putExtra("name", name);
        intent.putExtra("posCode", posCode);
        intent.putExtra("civilStatus", civilStatus);
        intent.putExtra("daysWorked", daysWorked);
        intent.putExtra("basicPay", basicPay);
        intent.putExtra("sss", sss);
        intent.putExtra("tax", tax);
        intent.putExtra("netPay", netPay);
        startActivity(intent);
    }

    private void clearFields() {
        spinnerEmpId.setSelection(0);
        spinnerPosition.setSelection(0);
        rgStatus.clearCheck();
        txtEmpName.setText("");
        spinnerDaysWorked.setSelection(0);
    }
}
