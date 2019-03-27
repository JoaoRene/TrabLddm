package com.example.trablddm;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.PhoneNumberUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;


public class MainActivity extends AppCompatActivity {

    Pessoa contato;
    EditText name, phone, email;
    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Button addContact = findViewById(R.id.addContact);
        addContact.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                addContact_onClick(v);
            }
        });

        final Button sendEmail = findViewById(R.id.sendEmail);
        sendEmail.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                sendEmail_onClick(v);
            }
        });

        final Button sendWpp = findViewById(R.id.sendWpp);
        sendWpp.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                sendWpp_onClick(v);
            }
        });


        CallbackManager callbackManager = CallbackManager.Factory.create();

        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        // App code
                    }

                    @Override
                    public void onCancel() {
                        // App code
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        // App code   
                    }
                });

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        CallbackManager callbackManager = null;
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }
    

    private void addContact_onClick(View v) {

        //create intent
        try{
            Intent intent = new Intent(ContactsContract.Intents.Insert.ACTION);

            //getting data
            name = findViewById(R.id.idName);
            email = findViewById(R.id.idEmail);
            phone = findViewById(R.id.idPhone);

            contato = new Pessoa(name.getText().toString(), email.getText().toString(), phone.getText().toString());

            //definindo intents
            intent
                    .setType(ContactsContract.RawContacts.CONTENT_TYPE)
                    .putExtra(ContactsContract.Intents.Insert.NAME, contato.getName())
                    .putExtra(ContactsContract.Intents.Insert.EMAIL, contato.getEmail())
                    .putExtra(ContactsContract.Intents.Insert.PHONE, contato.getPhone())
                    .putExtra(ContactsContract.Intents.Insert.PHONE_TYPE, ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE);

            startActivity(intent);
        }catch (Exception e){
            AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
            alertDialog.setTitle("Error");
            alertDialog.setMessage("Something went wrong!");
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
            alertDialog.show();
        }
    }

    private void sendEmail_onClick(View v) {
        try {
            Intent mailer = new Intent(Intent.ACTION_SENDTO);

            name = findViewById(R.id.idName);
            email = findViewById(R.id.idEmail);
            phone = findViewById(R.id.idPhone);

            String themail = "mailto:" + email.getText().toString() +
                    "?cc" +
                    "&subject=" + Uri.encode("You were added!") +
                    "&body=" + Uri.encode("Congratulations, you have a new friend!");
            mailer
                    .setType("text/html")
                    .setData(Uri.parse(themail))
            ;

            startActivity(mailer);
        }catch (Exception e) {
            AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
            alertDialog.setTitle("Error");
            alertDialog.setMessage("Something went wrong");
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
            alertDialog.show();
        }

    }

    private void sendWpp_onClick(View v) {
        try {
            Intent wapper = new Intent(Intent.ACTION_SEND);
            wapper
                    .setAction(Intent.ACTION_SEND)
                    .putExtra(Intent.EXTRA_TEXT, "Congratulations, you have a new friend!")
                    .setType("text/plain")
                    .setPackage("com.whatsapp");

            startActivity(wapper);
        }catch (Exception e) {
            AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
            alertDialog.setTitle("Error");
            alertDialog.setMessage("Something went wrong!");
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
            alertDialog.show();
        }
    }
}
