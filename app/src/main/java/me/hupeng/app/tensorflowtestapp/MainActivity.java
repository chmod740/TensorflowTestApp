package me.hupeng.app.tensorflowtestapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private TensorflowRunner tensorflowRunner;
    private Button btnCalc;
    private EditText etInput;
    private TextView tvOutput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnCalc = findViewById(R.id.btn_calc);
        etInput = findViewById(R.id.et_input);
        tvOutput = findViewById(R.id.tv_output);
        tensorflowRunner = new TensorflowRunner(this, new TensorflowRunner.TensorflowRunnerListener() {
            @Override
            public void callback(float data) {
                tvOutput.setText("" + data);
            }
        });

        btnCalc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    float input = Float.parseFloat(etInput.getText().toString());
                    tensorflowRunner.add(input);

                }catch (Exception e){
                    Toast.makeText(MainActivity.this, "ERROR", Toast.LENGTH_LONG).show();
                    tvOutput.setText("");
                }
            }
        });
    }
}
