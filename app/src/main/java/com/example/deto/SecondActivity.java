package com.example.deto;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;

public class SecondActivity extends AppCompatActivity {
            private TabLayout tablayout;
            private ViewPager viewpager;
            private TabItem tab1, tab2, tab3;
            public PageAdapter pagerAdapter;
            DatabaseHelper myDb;
            EditText editname,editsurname,editdate,editnitrit;
            Button button;
            Button button2;
            Button button3;
            Button button4;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        myDb = new DatabaseHelper(this);
        editname = (EditText)findViewById(R.id.text);
        editsurname = (EditText)findViewById(R.id.text2);
        editdate = (EditText)findViewById(R.id.text3);
        editnitrit = (EditText)findViewById(R.id.text4);
        button=(Button)findViewById(R.id.button);
        button2=(Button)findViewById(R.id.button2);
        button3=(Button)findViewById(R.id.button3);
        button4=(Button)findViewById(R.id.button4);
        AddData();
        viewAll();
        UpdateData();
        DeleteData();

    }



        public void DeleteData() {
            button4.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Integer deletedRows = myDb.deleteData(editname.getText().toString());
                            if (deletedRows > 0)
                                Toast.makeText(SecondActivity.this, "Data Deleted", Toast.LENGTH_LONG).show();
                            else
                                Toast.makeText(SecondActivity.this, "Data Not Deleted", Toast.LENGTH_LONG).show();

                        }
                    }
            );
        }


    public void UpdateData(){
        button3.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean isUpdate = myDb.updateData(editname.getText().toString(),editsurname.getText().toString()
                                ,editdate.getText().toString(),editnitrit.getText().toString() );
                        if(isUpdate==true)
                            Toast.makeText(SecondActivity.this, "Data Updated", Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(SecondActivity.this, "Data Not Updated", Toast.LENGTH_LONG).show();

                    }
                }
        );
    }
    public void AddData(){
        button.setOnClickListener(
                new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        boolean isInserted = myDb.insertData(editname.getText().toString(),editsurname.getText().toString()
                                ,editdate.getText().toString(),editnitrit.getText().toString() );
                        if(isInserted==true)
                            Toast.makeText(SecondActivity.this, "Data Inserted", Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(SecondActivity.this, "Data Not Inserted", Toast.LENGTH_LONG).show();

                    }
                }
        );

    }

    public void viewAll(){
        button2.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Cursor res = myDb.getAllData();
                        if(res.getCount() == 0){
                            //show message
                            showMessage("Error","Nothing found");
                            return;
                        }
                        StringBuffer buffer = new StringBuffer();
                        while (res.moveToNext()){
                            buffer.append("Name: "+ res.getString(0) + "\n");
                            buffer.append("Surname: "+ res.getString(1) + "\n");
                            buffer.append("Date: "+ res.getString(2) + "\n");
                            buffer.append("Nitrit value: "+ res.getString(3) + "\n\n");
                        }
                        showMessage("Data",buffer.toString());
                    }
                }
        );
    }

            public void showMessage(String title, String Message){
                  AlertDialog.Builder builder = new AlertDialog.Builder(this);
                  builder.setCancelable(true);
                  builder.setTitle(title);
                  builder.setMessage(Message);
                  builder.show();
    }




    @Override //sætter menuen til at åbne den rigtige fane af alarm, graf eller vedligehold
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {

            case R.id.item2:
                Intent i = new Intent(SecondActivity.this, Alarm.class);
            startActivity(i);
            return true;
            case R.id.item3:
                Intent ii = new Intent(SecondActivity.this, GrafiskOversigt.class);
                startActivity(ii);
                return true;
            case R.id.item4:
                Intent iii = new Intent(SecondActivity.this, KalibreringOgVedligehold.class);
                startActivity(iii);
                return true;
            default:return super.onOptionsItemSelected(item);
        }

    }
}