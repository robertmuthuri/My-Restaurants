package com.example.myrestaurants.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.accessibility.AccessibilityViewCommand;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myrestaurants.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CreateAccountActivity extends AppCompatActivity implements View.OnClickListener {
    @BindView(R.id.createUserButton) Button mCreateUserButton;
    @BindView(R.id.nameEditText) EditText mNameEditText;
    @BindView(R.id.emailEditText) EditText mEmailEditText;
    @BindView(R.id.passwordEditText) EditText mPasswordEditText;
    @BindView(R.id.confirmPasswordEditText) EditText mConfirmPasswordEditText;
    @BindView(R.id.loginTextView) TextView mLoginTextView;

    public static final String TAG = CreateAccountActivity.class.getSimpleName();

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;

    private ProgressDialog mAuthProgressDialog;

    private String mName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
        ButterKnife.bind(this);

        mLoginTextView.setOnClickListener(this);
        mCreateUserButton.setOnClickListener(this);

        mAuth = FirebaseAuth.getInstance();
        createAuthStateListener();

        createAuthProgressDialog();
    }

    @Override public void onClick (View v) {
        if (v == mLoginTextView) {
            Intent intent = new Intent(CreateAccountActivity.this, LoginActivity.class);
            // Add intent flags to manage the back stack of tasks.
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        }
        if (v == mCreateUserButton) {
            createNewUser();
        }
    }

    private void createNewUser() {
        final String name = mNameEditText.getText().toString().trim();
        final String email = mEmailEditText.getText().toString().trim();
        String password = mPasswordEditText.getText().toString().trim();
        String confirmPassword = mConfirmPasswordEditText.getText().toString().trim();

        mName = mNameEditText.getText().toString().trim();

        boolean validName = isValidName(mName);
        boolean validEmail = isValidEmail(email);
        boolean validPassword = isValidPassword(password, confirmPassword);

        if (!validEmail || !validName || !validPassword) return;
        // show progress dialog after validation
        mAuthProgressDialog.show();

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        mAuthProgressDialog.dismiss();

                        if (task.isSuccessful()) {
                            Log.d(TAG, "Authentication successful");
                            // call the method that set the user's name
                            createFirebaseUserProfile(task.getResult().getUser());
                        } else {
                            Toast.makeText(CreateAccountActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }

    private void createAuthStateListener() {
        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                final FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    Intent intent = new Intent(CreateAccountActivity.this, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();
                }
            }
        };
    }
    // validate email
    private boolean isValidEmail(String email) {
        boolean isGoodEmail = ( email != null && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches());
        if ( ! isGoodEmail) {
            mEmailEditText.setError("Please enter a valid email!");
            return false;
        }
        return true;
    }
    // validate name
    private boolean isValidName(String name) {
        if (name.equals("")) {
            mNameEditText.setError("Please enter your name!");
            return false;
        }
        return true;
    }
    // validate password
    private boolean isValidPassword (String password, String confirmPassword) {
        if (password.length() < 6 ) {
            mPasswordEditText.setError("Please create a password containing at least 6 characters!");
            return false;
        } else if ( !password.equals(confirmPassword)) {
            mPasswordEditText.setError("Passwords do no match!");
            return false;
        } return true;
    }
    // Add progress dialog method
    private void createAuthProgressDialog() {
        mAuthProgressDialog = new ProgressDialog(this);
        mAuthProgressDialog.setTitle("Loading...");
        mAuthProgressDialog.setMessage("Authenticating with Firebase...");
        mAuthProgressDialog.setCancelable(false);
    }
    // Add method to set user's name
    private void  createFirebaseUserProfile(final FirebaseUser user) {
        UserProfileChangeRequest addProfileName = new UserProfileChangeRequest.Builder()
                .setDisplayName(mName)
                .build();

        user.updateProfile(addProfileName)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, user.getDisplayName());
                        }
                    }
                });
    }

    @Override public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthStateListener);
    }
    @Override public void onStop() {
        super.onStop();
        if (mAuthStateListener != null) {
            mAuth.removeAuthStateListener(mAuthStateListener);
        }
    }
}
