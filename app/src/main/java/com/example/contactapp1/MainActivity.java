package com.example.contactapp1;

import android.app.AlertDialog;
import android.os.Bundle;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    ListView ListV;
    EditText SearchInput;
    Button AddButton;
    Button SearchButton;
    String[] result=new String[2];
    List<User> arrayList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SearchInput = findViewById(R.id.SearchInput);
        AddButton = findViewById(R.id.AddButton);
        ListV = findViewById(R.id.ListV);
        SearchButton = findViewById(R.id.SearchButton);

        SearchButton.setOnClickListener(v->{
            if(SearchInput.getText().toString().isEmpty()){
                ShowContacts();
                return;
            }
            BaseCont dataBase=new BaseCont(getApplicationContext(),"ContactAppBase.db",null,1);
            arrayList=dataBase.SearchContact(SearchInput.getText().toString());
            CstmAdpt costumAdapter=new CstmAdpt(getApplicationContext(),arrayList);
            ListV.setAdapter(costumAdapter);
        });

        ShowContacts();

        AddButton.setOnClickListener(v->{
            showTwoInputDialog();
        });
        ListV.setOnItemLongClickListener((parent, view, position, id) -> {
            BaseCont dataBase=new BaseCont(getApplicationContext(),"ContactAppBase.db",null,1);
            dataBase.DeleteContact(arrayList.get(position).Name,arrayList.get(position).Tel);
            ShowContacts();
            return true;
        });
    }
    public void showTwoInputDialog() {
        LayoutInflater inflater = LayoutInflater.from(this);
        View dialogView = inflater.inflate(R.layout.dialog_two, null);

        EditText input1 = dialogView.findViewById(R.id.input1);
        EditText input2 = dialogView.findViewById(R.id.input2);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Add New Contact");
        builder.setView(dialogView);

        builder.setPositiveButton("OK", (dialog, which) -> {
            result[0]=input1.getText().toString();
            result[1]=input2.getText().toString();
            if(!result[0].isEmpty() && !result[1].isEmpty()){
                BaseCont dataBase=new BaseCont(getApplicationContext(),"ContactAppBase.db",null,1);
                if(dataBase.CheckContact(result[1])){
                    Toast.makeText(this, "This contact already exists", Toast.LENGTH_SHORT).show();
                    return;
                }
                dataBase.AddContact(result[0],result[1]);
                arrayList=dataBase.GetContacts();
                User user=new User(result[0],result[1]);
                CstmAdpt costumAdapter=new CstmAdpt(getApplicationContext(),arrayList);
                ListV.setAdapter(costumAdapter);
            }else{
                Toast.makeText(this, "Please enter all fields", Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton("Cancel", null);
        builder.create().show();
    }
    public void ShowContacts(){
        BaseCont dataBase=new BaseCont(getApplicationContext(),"ContactAppBase.db",null,1);
        arrayList=dataBase.GetContacts();
        CstmAdpt costumAdapter=new CstmAdpt(getApplicationContext(),arrayList);
        ListV.setAdapter(costumAdapter);
    }
    public boolean DialogDeleteShow(){
        return true;
    }
}