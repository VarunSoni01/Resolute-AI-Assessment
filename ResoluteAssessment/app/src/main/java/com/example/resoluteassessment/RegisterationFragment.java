package com.example.resoluteassessment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.resoluteassessment.dashboard.DashboardActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterationFragment extends Fragment {
    View view;
    FirebaseAuth auth;
    EditText name,email,pass,conpass;
    TextView text;
    Button btn;

    String nam,em,pas,conpas;
    public RegisterationFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_registeration, container, false);
        auth = FirebaseAuth.getInstance();
        name = view.findViewById(R.id.reg_name);
        email = view.findViewById(R.id.reg_email);
        pass = view.findViewById(R.id.reg_pass);
        conpass = view.findViewById(R.id.reg_conpass);
        btn = view.findViewById(R.id.reg_btn);
        text = view.findViewById(R.id.reg_login);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nam = name.getText().toString().trim();
                em = email.getText().toString().trim();
                pas = pass.getText().toString().trim();
                conpas = conpass.getText().toString().trim();
                if(em.equals("") || pas.equals("") || nam.equals("") || conpass.equals("")){
                    Toast.makeText(getContext(), "Please fill all entries", Toast.LENGTH_SHORT).show();
                } else if (!pas.equals(conpas)) {
                    Toast.makeText(getContext(), "Password is not matched", Toast.LENGTH_SHORT).show();
                }
                else{
                    auth.createUserWithEmailAndPassword(em,pas).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            Toast.makeText(getContext(), "Successfull", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getActivity(), DashboardActivity.class);
                            startActivity(intent);
                            getActivity().finish();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getContext(), ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });

        return view;
    }
}