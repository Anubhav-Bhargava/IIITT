package com.example.loop.iiitt;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResolvingResultCallbacks;
import com.google.android.gms.common.api.Status;

public class MainActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {

    SignInButton signInButton;
    ImageButton peoplechatimageButton;

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Toast.makeText(this,"Connection Failed. Please check your Internet Connection!",Toast.LENGTH_SHORT).show();
    }

    GoogleApiClient mGoogleApiClient;
    private static final int RC_SIGN_IN=9001;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        peoplechatimageButton=(ImageButton)findViewById(R.id.people);
        peoplechatimageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(),ChatRoom.class);
                startActivity(intent);
            }
        });

        // Configure Google Sign In
       /* GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                //.requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();*/
        mGoogleApiClient=new GoogleApiClient.Builder(this)
                .enableAutoManage(this,this)
                .addApi(Auth.GOOGLE_SIGN_IN_API)
                .build();


      //  mGoogleApiClient = new GoogleSignInOptions.Builder(this).enableAutoManage(this, this).addApi(Auth.GOOGLE_SIGN_IN_API, new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build()).build();
        final boolean loggedin_flag=false;
        signInButton=(SignInButton)findViewById(R.id.sign_in_button);
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(loggedin_flag==false)
                    signIn();
                else{
                    signOut();

                }
            }
        });


    }
    private void signOut() {
        Auth.GoogleSignInApi.signOut(this.mGoogleApiClient).setResultCallback(new ResolvingResultCallbacks<Status>(this,0) {
            @Override
            public void onSuccess(@NonNull Status status) {
                Toast.makeText(getApplicationContext(),"Signed Out",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onUnresolvableFailure(@NonNull Status status) {

            }
        });
    }

    private void signIn() {
        startActivityForResult(Auth.GoogleSignInApi.getSignInIntent(this.mGoogleApiClient), 9001);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 9001) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess()) {
                //this.statusTextView.setText("Hello " + result.getSignInAccount().getDisplayName());
                GoogleSignInAccount account=result.getSignInAccount();
                Toast.makeText(this,"Hello " + account.getDisplayName()+"given name"+account.getGivenName()+"email"+account.getEmail(),Toast.LENGTH_SHORT).show();
            }
        }
    }




    public void onClick(View view) {
        Intent implicitIntent = null;
        switch (view.getId()) {
            case R.id.place:
                implicitIntent = new Intent("android.intent.action.VIEW", Uri.parse("https://www.google.co.in/maps/place/National+Institute+Of+Technology,+Tiruchirappalli,+Tamil+Nadu+620015/@10.76109,78.8117403,17z/data=!3m1!4b1!4m5!3m4!1s0x3baa8d47758e1ae1:0xb3e16389eeab05a!8m2!3d10.76109!4d78.813929"));
                break;
            case R.id.dining:
                implicitIntent = new Intent("android.intent.action.VIEW", Uri.parse("https://www.google.co.in/maps/search/restaurants+nit+trichy/@10.7614165,78.8132328,15z/data=!3m1!4b1"));
                break;
            case R.id.events:
                Toast.makeText(this, "NO Events to Show", Toast.LENGTH_SHORT).show();
                break;
           /* case R.id.people:
                 implicitIntent=new Intent(this,ChatRoom.class);
                break;*/
            case R.id.gallery:
                Toast.makeText(this, "Gallery Selected", Toast.LENGTH_SHORT).show();
                break;
            case R.id.timetable:
                Toast.makeText(this, "Time Table", Toast.LENGTH_SHORT).show();
                break;

        }
        if (implicitIntent != null) {
            startActivity(implicitIntent);
        }
    }
}
