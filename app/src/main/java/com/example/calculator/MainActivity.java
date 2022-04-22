package com.example.calculator;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private EditText result;
    private EditText newNumber;
    private TextView displayOperation;
//to store operands and type of operation
    private Double operand1=null;
    private String pendingOperation="=";
    private static final String STATE_PENDING_OPERATION="pendingOperation";
    private static final String STATE_OPERAND1="operand1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        result=(EditText) findViewById(R.id.result);
        newNumber=(EditText) findViewById(R.id.newnumber);
        displayOperation=(TextView) findViewById(R.id.operation);

        Button button0=(Button) findViewById(R.id.button0);
        Button button1=(Button) findViewById(R.id.button1);
        Button button2=(Button) findViewById(R.id.button2);
        Button button3=(Button) findViewById(R.id.button3);
        Button button4=(Button) findViewById(R.id.button4);
        Button button5=(Button) findViewById(R.id.button5);
        Button button6=(Button) findViewById(R.id.button6);
        Button button7=(Button) findViewById(R.id.button7);
        Button button8=(Button) findViewById(R.id.button8);
        Button button9=(Button) findViewById(R.id.button9);
        Button buttonDot=(Button) findViewById(R.id.dot);

        Button buttonDivide=(Button) findViewById(R.id.divide);
        Button buttonMultiply=(Button) findViewById(R.id.multiply);
        Button buttonSubtract=(Button) findViewById(R.id.subtract);
        Button buttonAdd=(Button) findViewById(R.id.add);
        Button buttonEqual=(Button) findViewById(R.id.equals);

        View.OnClickListener Listener =new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Button b=(Button) v;
                newNumber.append(b.getText().toString());
            }
        };
        button0.setOnClickListener(Listener);
        button1.setOnClickListener(Listener);
        button2.setOnClickListener(Listener);
        button3.setOnClickListener(Listener);
        button4.setOnClickListener(Listener);
        button5.setOnClickListener(Listener);
        button6.setOnClickListener(Listener);
        button7.setOnClickListener(Listener);
        button8.setOnClickListener(Listener);
        button9.setOnClickListener(Listener);
        buttonDot.setOnClickListener(Listener);

        View.OnClickListener Listener2=new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Button b=(Button) v;
                String op=b.getText().toString();
                String value=newNumber.getText().toString();
                try {
                    Double DoubleValue=Double.valueOf(value);
                    pendingOperation(DoubleValue,op);
                }catch(NumberFormatException e){
                    newNumber.setText("");
                }
                pendingOperation=op;
                displayOperation.setText(pendingOperation);
            }
        };

        buttonEqual.setOnClickListener(Listener2);
        buttonDivide.setOnClickListener(Listener2);
        buttonMultiply.setOnClickListener(Listener2);
        buttonAdd.setOnClickListener(Listener2);
        buttonSubtract.setOnClickListener(Listener2);
        Button buttonNeg=(Button)findViewById(R.id.buttonNeg);
        buttonNeg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String value=newNumber.getText().toString();
                if(value.length()==0){
                    newNumber.setText("-");
                }
                else{
                    try{
                        Double doubleValue=Double.valueOf(value);
                        doubleValue*=-1;
                        newNumber.setText(doubleValue.toString());
                    }
                    catch (NumberFormatException e){
                        //if newNumber is "-" or "."  clear it
                        newNumber.setText("");
                    }
                }
            }
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState){
        outState.putString(STATE_PENDING_OPERATION,pendingOperation);
        if(operand1!=null){
            outState.putDouble(STATE_OPERAND1,operand1);
        }
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onRestoreInstanceState(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onRestoreInstanceState(savedInstanceState);
        pendingOperation=savedInstanceState.getString(STATE_PENDING_OPERATION);
        operand1=savedInstanceState.getDouble(STATE_OPERAND1);
        displayOperation.setText(pendingOperation);
        if(savedInstanceState.containsKey(STATE_OPERAND1))
        {
            operand1=Double.valueOf(savedInstanceState.get(STATE_OPERAND1).toString());
        }
    }

    private void pendingOperation(Double value, String operation){
        if(null==operand1){
            operand1=value;
        }
        else{
            if(pendingOperation.equals("=")){
                pendingOperation=operation;
            }
            switch(pendingOperation){
                case "=":operand1= value;
                         break;
                case "/":if(value ==0){
                         operand1=0.0;
                        }
                        else{
                            operand1/= value;
                        }
                        break;
                case "*":operand1*= value;
                        break;
                case "+":operand1+= value;
                        break;
                case "-":operand1-= value;
                        break;
            }
        }
        result.setText(operand1.toString());
        newNumber.setText("");
    }
}