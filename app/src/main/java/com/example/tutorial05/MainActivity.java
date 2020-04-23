package com.example.tutorial05;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    DatabaseHelper myDb;
    EditText editId,editName,editAddress,editAge,editPosition;
    Button buttonAdd,buttonView,buttonDelete,buttonUpdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myDb = new DatabaseHelper(this);
        editId = findViewById(R.id.editText1);
        editName = findViewById(R.id.editText2);
        editAddress = findViewById(R.id.editText3);
        editAge = findViewById(R.id.editText4);
        editPosition = findViewById(R.id.editText5);
        buttonAdd = findViewById(R.id.buttonCreate);
        buttonView = findViewById(R.id.buttonRead);
        buttonUpdate = findViewById(R.id.buttonUpdate);
        buttonDelete = findViewById(R.id.buttonDelete);

        addData();
        viewAll();
        updateData();
        deleteData();

        Cursor res = myDb.getAllData();
        if(res.getCount() == 0){
            showMessage("Error","Nothing to display"); //check any data in present in the database
            return;
        }
        StringBuffer buffer = new StringBuffer();
        while (res.moveToNext()){
            buffer.append("ID :"+ res.getString(0)+"\n");
            buffer.append("Name :"+ res.getString(1)+"\n");
            buffer.append("Address :"+ res.getString(2)+"\n");
            buffer.append("Age :"+ res.getString(3)+"\n");
            buffer.append("Position :"+ res.getString(4)+"\n\n");
        }
        showMessage("Employee Details",buffer.toString());
    }

    public void addData(){
        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isInserted = myDb.insertData(editId.getText().toString(),
                        editName.getText().toString(),
                        editAddress.getText().toString(),
                        Integer.parseInt(editAge.getText().toString()),
                        editPosition.getText().toString());
                if(isInserted){
                    Toast.makeText(MainActivity.this,"Data Inserted",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(MainActivity.this,"Employee ID Exists",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void viewAll(){
        buttonView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor res = myDb.getAllData();
                if(res.getCount() == 0){
                    showMessage("Error","Nothing to display"); //check any data in present in the database
                    return;
                }
                StringBuffer buffer = new StringBuffer();
                while (res.moveToNext()){
                    buffer.append("ID :"+ res.getString(0)+"\n");
                    buffer.append("Name :"+ res.getString(1)+"\n");
                    buffer.append("Address :"+ res.getString(2)+"\n");
                    buffer.append("Age :"+ res.getString(3)+"\n");
                    buffer.append("Position :"+ res.getString(4)+"\n\n");
                }
                showMessage("Employee Details",buffer.toString());
            }
        });
    }

    public void showMessage(String title, String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();

    }

    public void updateData(){
        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isUpdated = myDb.updateData(editId.getText().toString(),
                        editName.getText().toString(),
                        editAddress.getText().toString(),
                        Integer.parseInt(editAge.getText().toString()),
                        editPosition.getText().toString());
                if(isUpdated){
                    Toast.makeText(MainActivity.this,"Data Updated",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(MainActivity.this,"Data Not Updated",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void deleteData(){
        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer deletedRows = myDb.deleteData(editId.getText().toString());
                if(deletedRows > 0){
                    Toast.makeText(MainActivity.this,"Data Deleted",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(MainActivity.this,"Data Not Deleted",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
