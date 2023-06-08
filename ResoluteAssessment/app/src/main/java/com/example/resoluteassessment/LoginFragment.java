package com.example.resoluteassessment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

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

public class LoginFragment extends Fragment {

    View view;
    FirebaseAuth auth;
    EditText email,pass;
    TextView text;
    Button btn;

    String em,pas;
    public LoginFragment() {
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
        view = inflater.inflate(R.layout.fragment_login, container, false);
        auth = FirebaseAuth.getInstance();
        email = view.findViewById(R.id.login_email);
        pass = view.findViewById(R.id.login_pass);
        btn = view.findViewById(R.id.login_btn);
        text = view.findViewById(R.id.login_register);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                em = email.getText().toString().trim();
                pas = pass.getText().toString().trim();
                if(em.equals("") || pas.equals("")){
                    Toast.makeText(getContext(), "Please fill all entries", Toast.LENGTH_SHORT).show();
                }
                else{
                    auth.signInWithEmailAndPassword(em,pas).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            Toast.makeText(getContext(), "Logged In", Toast.LENGTH_SHORT).show();
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

        text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.addToBackStack(null);
                transaction.replace(R.id.auth_framelayout, new RegisterationFragment());
                transaction.commit();
            }
        });

        return view;
    }
}