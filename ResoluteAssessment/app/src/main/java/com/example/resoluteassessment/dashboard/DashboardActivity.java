package com.example.resoluteassessment.dashboard;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.resoluteassessment.AuthenticationActivity;
import com.example.resoluteassessment.R;
import com.example.resoluteassessment.camera.CameraActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class DashboardActivity extends AppCompatActivity {
    Dash_Adapter adapter;
    List<Dash_Model> list;
    RecyclerView recyclerView;
    Button scan,logout;
    String text;
    FirebaseFirestore firestore;
    FirebaseAuth auth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        text = getIntent().getStringExtra("text");
        firestore = FirebaseFirestore.getInstance();

        if (text != null) {
            Toast.makeText(this, "varun - " + text, Toast.LENGTH_SHORT).show();
            showDialogWithAnimation(text);
            addtofirestore(text);
        }


        initiallize();
        scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashboardActivity.this, CameraActivity.class);
                startActivity(intent);
                finish();

            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                auth.signOut();
                Intent intent = new Intent(DashboardActivity.this, AuthenticationActivity.class);
                startActivity(intent);
                finish();

            }
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        data();


    }

    private void addtofirestore(String txt) {
        Date currentDate = new Date();

        // Define the desired date and time format
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());

        // Format the date and time as a string
        String formattedDateTime = dateFormat.format(currentDate);

        firestore.collection("Data").document(formattedDateTime).set(new Dash_Model(txt)).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(DashboardActivity.this, "Success", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(DashboardActivity.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initiallize() {
        recyclerView = findViewById(R.id.dash_rv);
        auth = FirebaseAuth.getInstance();
        list = new ArrayList<>();
        scan = findViewById(R.id.dash_bt);
        logout = findViewById(R.id.dash_logout);
        adapter = new Dash_Adapter(DashboardActivity.this, list);
    }

    private void data() {
        firestore.collection("Data").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error != null) {
                    Toast.makeText(DashboardActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
                    return;
                }
                for (DocumentChange dc : value.getDocumentChanges()) {
                    if (dc.getType() == DocumentChange.Type.ADDED) {
                        list.add(new Dash_Model(dc.getDocument().getString("data")));
                    }
                    adapter.notifyDataSetChanged();
                }
            }
        });

    }

    private void showDialogWithAnimation(String txt) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Saved Successfully")
                .setMessage(txt)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Positive button click
                        dialog.dismiss();
                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

}