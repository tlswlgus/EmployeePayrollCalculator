package com.example.employeepayrollcalculator;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class SummaryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);

        TextView txtSummary = findViewById(R.id.txtSummary);
        Button btnBack = findViewById(R.id.btnBack);

        String empId = getIntent().getStringExtra("empId");
        String name = getIntent().getStringExtra("name");
        String posCode = getIntent().getStringExtra("posCode");
        String civilStatus = getIntent().getStringExtra("civilStatus");
        int daysWorked = getIntent().getIntExtra("daysWorked", 0);
        double basicPay = getIntent().getDoubleExtra("basicPay", 0);
        double sss = getIntent().getDoubleExtra("sss", 0);
        double tax = getIntent().getDoubleExtra("tax", 0);
        double netPay = getIntent().getDoubleExtra("netPay", 0);

        String result = String.format(
                "Employee ID: %s\nName: %s\nPosition Code: %s\nCivil Status: %s\nNo. of Days Worked: %d\n\n" +
                        "Basic Pay: Php %,.2f\nSSS Contribution: Php %,.2f\nWithholding Tax: Php %,.2f\nNet Pay: Php %,.2f",
                empId, name, posCode, civilStatus, daysWorked, basicPay, sss, tax, netPay
        );
        txtSummary.setText(result);

        btnBack.setOnClickListener(v -> finish());
    }
}
