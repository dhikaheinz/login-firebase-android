package co.kyozen.belajarfirebase;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {

    private EditText edtEmail, edtPassword;
    private Button btnRegister;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initView();
        registerUser();
    }

    private void initView() {
        edtEmail = findViewById(R.id.edt_email_register);
        edtPassword = findViewById(R.id.edt_password_register);
        btnRegister = findViewById(R.id.btn_sign_up);
        auth = FirebaseAuth.getInstance();
    }

    private void registerUser(){
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailUser = edtEmail.getText().toString().trim();
                String passwordUser = edtPassword.getText().toString().trim();

                if (emailUser.isEmpty()){
                    edtEmail.setError("Email Tidak Boleh Kosong");
                }else if (!Patterns.EMAIL_ADDRESS.matcher(emailUser).matches()){
                    edtEmail.setError("Email Tidak Valid");
                }else if (passwordUser.isEmpty()){
                    edtPassword.setError("Password Tidak Boleh Kosong");
                }else if (passwordUser.length() < 6){
                    edtPassword.setError("Password Kurang Dari 6 Karakter");
                }else {
                    auth.createUserWithEmailAndPassword(emailUser,passwordUser)
                            .addOnCompleteListener(RegisterActivity.this,
                                    new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(Task<AuthResult> task) {
                                    if (!task.isSuccessful()){
                                        Toast.makeText(RegisterActivity.this, "Register Gagal Karena"+task.getException().getMessage(), Toast.LENGTH_LONG).show();
                                    }else {
                                        startActivity(new Intent(RegisterActivity.this, AdminLoginActivity.class));
                                    }
                                }
                            });
                }
            }
        });
    }
}
