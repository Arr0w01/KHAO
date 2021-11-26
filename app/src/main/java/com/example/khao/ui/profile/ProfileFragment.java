package com.example.khao.ui.profile;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.telecom.TelecomManager;
import android.telephony.TelephonyManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.khao.MainActivity;
import com.example.khao.UserDao;
import com.example.khao.ui.SigninActivity;
import com.example.khao2.R;
import com.example.khao.ui.home.HomeViewModel;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.ktx.Firebase;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import model.User;

import static android.content.ContentValues.TAG;
import static com.example.khao.ui.SigninActivity.mAuth;
import static com.example.khao.ui.SigninActivity.mGoogleSignInClient;
import static com.example.khao.ui.SigninActivity.user;

//
public class ProfileFragment extends Fragment {
    private ProfileViewModel profileViewModel;
    CardView signout;

    TextView userN, email, EditNum,mobileNum;
    ImageView profilePhoto,editName;
    public static String imageUrl;
    private static int numberCount;
    private static String mobileNumber;
    private String verificationId;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        profileViewModel =
                new ViewModelProvider(this).get(ProfileViewModel.class);
        View root = inflater.inflate(R.layout.fragment_profile, container, false);

//        Find view By IDs -
        userN = root.findViewById(R.id.userName);
        email = root.findViewById(R.id.email);
        profilePhoto = root.findViewById(R.id.profile_img);
        EditNum = root.findViewById(R.id.editNumber);
        mobileNum = root.findViewById(R.id.mobileNum);
        editName = root.findViewById(R.id.editName);
//        End Find view by Id
        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser usera = auth.getCurrentUser();
        userN.setText(usera.getDisplayName());
        if (usera.getPhoneNumber() != null && !usera.getPhoneNumber().isEmpty()){
            EditNum.setText("EDIT");
            mobileNum.setText(user.getPhoneNumber());
        }
        else {
            EditNum.setText("Add Number");
        }

        editName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editNAME();
            }
        });


//        email.setText();
        imageUrl = usera.getPhotoUrl().toString();
        email.setText(usera.getEmail());
        Glide.with(getContext()).load(imageUrl).circleCrop().into(profilePhoto);

        EditNum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SetNumber();
            }
        });


        signout = root.findViewById(R.id.logout);
        signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signOut();


            }
        });


        return root;
    }

    private void editNAME() {
//        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(getContext(), R.style.BottomSheetDialog);
//        bottomSheetDialog.setContentView(R.layout.edit_name);
//         EditText getName;
//         Button setName;
//         ProgressBar updatenamePB;
//
//         getName = bottomSheetDialog.findViewById(R.id.typeName);
//         setName = bottomSheetDialog.findViewById(R.id.updateNameBtn);
//         updatenamePB = bottomSheetDialog.findViewById(R.id.updateNamePB);
//
//         setName.setOnClickListener(new View.OnClickListener() {
//             @Override
//             public void onClick(View v) {
//                 updatenamePB.setVisibility(View.VISIBLE);
//                 setName.setVisibility(View.INVISIBLE);
//                 FirebaseAuth auth = FirebaseAuth.getInstance();
//                 FirebaseUser firebaseUser = auth.getCurrentUser();
//                 String newName = getName.getText().toString();
//                 UserProfileChangeRequest profileChangeRequest = new UserProfileChangeRequest.Builder().
//                         setDisplayName(newName).build();
//                 firebaseUser.updateProfile(profileChangeRequest).addOnCompleteListener(new OnCompleteListener<Void>() {
//                     @Override
//                     public void onComplete(@NonNull Task<Void> task) {
//                         if (task.isSuccessful()){
//                             Toast.makeText(getContext(), "Name Updated Successfully!", Toast.LENGTH_SHORT).show();
//                             userN.setText(user.getDisplayName());
//                             firebaseUser.reload();
//                             userN.setText(firebaseUser.getDisplayName());
//                             bottomSheetDialog.cancel();
//                         }else {
//                             Toast.makeText(getContext(), ""+task.getException(), Toast.LENGTH_LONG).show();
//                         }
//                     }
//                 });
//             }
//         });


//        bottomSheetDialog.show();
//        bottomSheetDialog.getWindow().findViewById(R.id.bgmain).setBackgroundResource(android.R.color.transparent);

    }

    private void signOut() {
        mAuth.signOut();

        mGoogleSignInClient.signOut().addOnCompleteListener(getActivity(),
                new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Intent login = new Intent(getContext(), SigninActivity.class);
                        login.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(login);
                        getActivity().finish();

                    }
                });
//
    }

    private void SetNumber() {
        ConstraintLayout otpLayout;
        Button getOtp, Verify;
        EditText subNumber, subOTP;
        ImageView tick;
        ProgressBar otpProgress, verifyProgress;


//        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(getContext(), R.style.BottomSheetDialog);
//        bottomSheetDialog.setContentView(R.layout.set_number);
//
//        otpLayout = bottomSheetDialog.findViewById(R.id.otpLayout);
//        getOtp = bottomSheetDialog.findViewById(R.id.getOtp);
//        Verify = bottomSheetDialog.findViewById(R.id.verifyBtn);
//        subNumber = bottomSheetDialog.findViewById(R.id.submitNum);
//        subOTP = bottomSheetDialog.findViewById(R.id.submitOTP);
//        tick = bottomSheetDialog.findViewById(R.id.tick);
//        otpProgress = bottomSheetDialog.findViewById(R.id.getotpPB);
//        verifyProgress = bottomSheetDialog.findViewById(R.id.verifyotpPB);
//
//        subNumber.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//                 numberCount =  subNumber.getText().length();
//                 if (numberCount == 10){
//                     getOtp.setEnabled(true);
//                     getOtp.setBackgroundColor(Color.WHITE);
//                     getOtp.setTextColor(Color.BLACK);
//                     tick.setVisibility(View.VISIBLE);
//                 }else {
//                     getOtp.setEnabled(false);
//                     getOtp.setBackgroundColor(getResources().getColor(R.color.darkGrey));
//                     getOtp.setTextColor(Color.WHITE);
//                     tick.setVisibility(View.GONE);
//                }
//            }
//        });
//        getOtp.setEnabled(false);
//
//        getOtp.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mobileNumber = subNumber.getText().toString();
//                otpProgress.setVisibility(View.VISIBLE);
//                getOtp.setVisibility(View.INVISIBLE);
//
//                PhoneAuthProvider.getInstance().verifyPhoneNumber("+91"+ mobileNumber,60,TimeUnit.SECONDS,
//                        getActivity(),
//                        new PhoneAuthProvider.OnVerificationStateChangedCallbacks(){
//
//                            @Override
//                            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                                Toast.makeText(getContext(), "Verified!", Toast.LENGTH_SHORT).show();
//                            }
//
//                            @Override
//                            public void onVerificationFailed(@NonNull FirebaseException e) {
//                                Toast.makeText(getContext(), ""+e.getMessage(), Toast.LENGTH_LONG).show();
//                            }
//
//                            @Override
//                            public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
//                                super.onCodeSent(s, forceResendingToken);
//                                otpLayout.setVisibility(View.VISIBLE);
//                                otpProgress.setVisibility(View.GONE);
//                                verificationId = s;
//                            }
//                        });
//
//                Verify.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        verifyProgress.setVisibility(View.VISIBLE);
//                        Verify.setVisibility(View.INVISIBLE);
//                        String code = subOTP.getText().toString();
//                        if (verificationId !=null){
//
//                            getOtp.setVisibility(View.INVISIBLE);
//                            subNumber.setVisibility(View.INVISIBLE);
//                            PhoneAuthCredential phoneAuthCredential = PhoneAuthProvider.getCredential(
//                                    verificationId,
//                                    code
//                            );
//                            FirebaseAuth auth = FirebaseAuth.getInstance();
//                            FirebaseUser firebaseUser = auth.getCurrentUser();
//                            firebaseUser.updatePhoneNumber(phoneAuthCredential).addOnCompleteListener(new OnCompleteListener<Void>() {
//                                @Override
//                                public void onComplete(@NonNull Task<Void> task) {
//                                    if (task.isSuccessful()){
//                                        Toast.makeText(getContext(), "Success", Toast.LENGTH_SHORT).show();
//                                        verifyProgress.setVisibility(View.GONE);
//                                        firebaseUser.reload();
//                                        mobileNum.setText(firebaseUser.getPhoneNumber());
//                                        bottomSheetDialog.cancel();
//                                    }else {
//
//                                        Toast.makeText(getContext(), ""+task.getException(), Toast.LENGTH_LONG).show();
//                                        verifyProgress.setVisibility(View.GONE);
//                                    }
//                                }
//                            });
//
//
//                                                PhoneAuthCredential pc = PhoneAuthProvider.getCredential("+91"+mobileNumber,code);
//                                                firebaseUser.updatePhoneNumber(pc);
//
//                        }
//                    }
//                });
//            }
//        });
//
//        bottomSheetDialog.show();
//        bottomSheetDialog.getWindow().findViewById(R.id.bsMain).setBackgroundResource(android.R.color.transparent);

    }

}