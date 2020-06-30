package com.ngodingyuk.myfbapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.ngodingyuk.myfbapp.model.Requests;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "LOG";
    public static final String DATA = "DATA_ITEM";

    private DatabaseReference database;

    private Requests data;

    private EditText etName, etEmail, etDesc;
    private ProgressDialog loading;
    private Button btnSave, btnCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        database = FirebaseDatabase.getInstance().getReference();

        data = getIntent().getParcelableExtra(DATA);

        etName = findViewById(R.id.et_name);
        etEmail = findViewById(R.id.et_email);
        etDesc = findViewById(R.id.et_description);
        btnSave = findViewById(R.id.btn_save);
        btnCancel = findViewById(R.id.btn_cancel);

        getData();

        if (data == null){
            btnSave.setText("Save");
            btnCancel.setText("Cancel");
        }else {
            btnSave.setText("Update");
            btnCancel.setText("Delete");
        }

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = etName.getText().toString();
                String email = etEmail.getText().toString();
                String description = etDesc.getText().toString();

                if (btnSave.getText().equals("Save")){
                    if (name.equals("")){
                        etName.setError("Silahkan masukan nama");
                        etName.requestFocus();
                    }else if(email.equals("")){
                        etEmail.setError("Silahkan masukan email");
                        etEmail.requestFocus();
                    }else if(description.equals("")){
                        etDesc.setError("Silahkan masukan deskripsi");
                        etDesc.requestFocus();
                    }else{
                        loading = ProgressDialog.show(
                                MainActivity.this,
                                null,
                                "Please Wait",
                                true,
                                false
                        );

                        submitUser(new Requests(
                                name.toLowerCase(),
                                email.toLowerCase(),
                                description.toLowerCase()));
                    }
                }else{
                    if (name.equals("")){
                        etName.setError("Silahkan masukan nama");
                        etName.requestFocus();
                    }else if(email.equals("")){
                        etEmail.setError("Silahkan masukan email");
                        etEmail.requestFocus();
                    }else if(description.equals("")){
                        etDesc.setError("Silahkan masukan deskripsi");
                        etDesc.requestFocus();
                    }else{
                        loading = ProgressDialog.show(
                                MainActivity.this,
                                null,
                                "Please Wait",
                                true,
                                false
                        );

                        editUser(new Requests(
                                name.toLowerCase(),
                                email.toLowerCase(),
                                description.toLowerCase()), data.getId());
                    }
                }

            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (btnCancel.getText().equals("Cancel")){
                    finish();
                }else{
                    loading = ProgressDialog.show(
                            MainActivity.this,
                            null,
                            "Please Wait",
                            true,
                            false
                    );

                    deleteUser(data.getId());
                }

            }
        });
    }

    private void submitUser(Requests requests){
        database.child("Requests")
                .push()
                .setValue(requests)
                .addOnSuccessListener(this, new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        loading.dismiss();

                        etName.setText("");
                        etEmail.setText("");
                        etDesc.setText("");

                        Toast.makeText(MainActivity.this, "Data berhasil di tambahkan", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });
    }

    private void editUser(Requests requests, String id){
        database.child("Requests")
                .child(id)
                .setValue(requests)
                .addOnSuccessListener(this, new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        loading.dismiss();

                        etName.setText("");
                        etEmail.setText("");
                        etDesc.setText("");

                        Toast.makeText(MainActivity.this, "Data berhasil di ubah", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });
    }

    private void deleteUser(String id){
        database.child("Requests")
                .child(id)
                .removeValue()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        loading.dismiss();
                        Toast.makeText(MainActivity.this, "Data berhasil di hapus", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });
    }

    private void getData(){
        if (data != null){
            String name = data.getName();
            String email = data.getEmail();
            String desc = data.getDesc();

            etName.setText(name);
            etEmail.setText(email);
            etDesc.setText(desc);
        }else {
            etName.setText("");
            etEmail.setText("");
            etDesc.setText("");
        }

    }

}