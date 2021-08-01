package com.yaseen.therevolutionarystep;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInApi;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class LoginActivity extends AppCompatActivity {

    EditText email,password;
    Button signin;
    SignInButton googlesignin;
    TextView signuptextview,fgtpswdtextview;
    ProgressBar progressBar;
    FirebaseAuth auth=FirebaseAuth.getInstance();
    GoogleSignInClient googleSignInClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email=findViewById(R.id.loginemail);
        password=findViewById(R.id.loginpassword);
        signin=findViewById(R.id.signinbutton);
        googlesignin=findViewById(R.id.googlesigninbtn);
        signuptextview=findViewById(R.id.loginsignuptext);
        fgtpswdtextview=findViewById(R.id.forgotpasswordtext);
        progressBar=findViewById(R.id.progressbarlogin);
        progressBar.setVisibility(View.INVISIBLE);

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String userMail=email.getText().toString();
                String userPassword=password.getText().toString();
                signInWithFirebase(userMail,userPassword);

            }
        });

        googlesignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                signInGoogle();

            }
        });

        signuptextview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(LoginActivity.this,SignupActivity.class);
                startActivity(intent);

            }
        });


        fgtpswdtextview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(LoginActivity.this,Forgot_Password.class);
                startActivity(intent);

            }
        });
    }

    public void signInWithFirebase(String userMail,String userPassword)
    {
        progressBar.setVisibility(View.VISIBLE);
        signin.setClickable(false);
        auth.signInWithEmailAndPassword(userMail,userPassword)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful())
                        {
                            Intent intent=new Intent(LoginActivity.this,HomeActivity.class);
                            startActivity(intent);
                            finish();
                            progressBar.setVisibility(View.INVISIBLE);
                            Toast.makeText(LoginActivity.this, "SignIn Successfull",Toast.LENGTH_LONG).show();

                        }else
                        {
                            Toast.makeText(LoginActivity.this, "SignIn Not Successfull",Toast.LENGTH_LONG).show();
                        }

                    }
                });
    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser user=auth.getCurrentUser();
        if (user!=null)
        {
            Intent intent=new Intent(LoginActivity.this,HomeActivity.class);
            startActivity(intent);
            finish();
        }
    }


    private void signInGoogle() {

        GoogleSignInOptions googleSignInOptions=new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail().build();

        googleSignInClient = GoogleSignIn.getClient(this,googleSignInOptions);

        signIn();
    }

    private void signIn() {
        Intent signInIntent=googleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent,1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode==1)
        {
            Task<GoogleSignInAccount> task= GoogleSignIn.getSignedInAccountFromIntent(data);
            firebaseSignInWithGoogle(task);

        }
    }

    private void firebaseSignInWithGoogle(Task<GoogleSignInAccount> task) {

        try {
            GoogleSignInAccount account=task.getResult(ApiException.class);
            Toast.makeText(LoginActivity.this,"Signed In Successfully",Toast.LENGTH_LONG).show();
            Intent intent=new Intent(LoginActivity.this,HomeActivity.class);
            startActivity(intent);
            finish();
            firebaseGoogleAccount(account);
        } catch (ApiException e) {
            e.printStackTrace();
            Toast.makeText(LoginActivity.this,"Signed In is not Successfull",Toast.LENGTH_LONG).show();
        }
    }

    private void firebaseGoogleAccount(GoogleSignInAccount account) {

        AuthCredential authCredential= GoogleAuthProvider.getCredential(account.getIdToken(),null);
        auth.signInWithCredential(authCredential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful())
                {
                    FirebaseUser user=auth.getCurrentUser();
                }

            }
        });

    }
}

