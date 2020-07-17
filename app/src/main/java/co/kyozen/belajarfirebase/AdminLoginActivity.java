package co.kyozen.belajarfirebase;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.os.Bundle;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class AdminLoginActivity extends AppCompatActivity{

    private EditText edtEmail, edtPassword;
    private Button btnSignin, btnRegister;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);

        initView();
        userLogin();
    }

    private void userLogin() {
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminLoginActivity.this, RegisterActivity.class));
            }
        });

        btnSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String emailUser = edtEmail.getText().toString().trim();
                final String passUser = edtPassword.getText().toString().trim();

                if (emailUser.isEmpty()){
                    edtEmail.setError("Email TIdak Boleh Kosong");
                }else if (!Patterns.EMAIL_ADDRESS.matcher(emailUser).matches()){
                    edtEmail.setError("Email Tidak Valid");
                }else if (passUser.isEmpty()){
                    edtPassword.setError("Password Tidak Boleh Kosong");
                }else if (passUser.length() < 6){
                    edtPassword.setError("Katakter Kurang dari 6");
                }else {
                    auth.signInWithEmailAndPassword(emailUser,passUser)
                            .addOnCompleteListener(AdminLoginActivity.this,
                                    new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (!task.isSuccessful()){
                                        Toast.makeText(AdminLoginActivity.this, "Login Error Karena"+task.getException().getMessage(), Toast.LENGTH_LONG).show();
                                    }else {
                                        Bundle bundle = new Bundle();
                                        bundle.putString("email", emailUser);
                                        bundle.putString("pass", passUser);
                                        startActivity(new Intent(AdminLoginActivity.this, MainActivity.class).putExtra("emailpass", bundle));
                                        finish();
                                    }
                                }
                            });

                }
            }
        });
    }

    private void initView() {
        edtEmail = findViewById(R.id.tv_email);
        edtPassword = findViewById(R.id.tv_pass);
        btnSignin = findViewById(R.id.btn_sign_in);
        btnRegister = findViewById(R.id.btn_sign_up);
        auth = FirebaseAuth.getInstance();
    }
}
