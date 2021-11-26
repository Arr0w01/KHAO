package com.example.khao.ui.dashboard;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.khao.Food;
import com.example.khao.MainActivity;
import com.example.khao2.R;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONObject;

import java.util.Map;

import adaptor.Adapter;
import adaptor.CartAdapter;
import model.Cart;

import static com.example.khao.ui.SigninActivity.mAuth;

public class DashboardFragment extends Fragment {

    private static final String TAG = MainActivity.class.getSimpleName();
    private DashboardViewModel dashboardViewModel;
    RecyclerView cartView;
    CartAdapter cartAdapter;
    public static TextView cartTotal;
    String userID;
    DatabaseReference dr;
    Button buy;
    FirebaseUser firebaseUser;
//    private FirestoreRecyclerAdapter<Cart, ProductViewHolder> adapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dashboardViewModel =
                new ViewModelProvider(this).get(DashboardViewModel.class);
        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);
        cartView = root.findViewById(R.id.cartRV);
        buy = root.findViewById(R.id.c_checkout);

        cartView.setLayoutManager(new LinearLayoutManager(getContext()));
        FirebaseAuth auth = FirebaseAuth.getInstance();
             firebaseUser = auth.getCurrentUser();
             userID = firebaseUser.getUid();

            FirebaseRecyclerOptions<Cart> options =
                    new FirebaseRecyclerOptions.Builder<Cart>()
                            .setQuery(FirebaseDatabase.getInstance().getReference("Cart").child(userID), Cart.class)
                            .build();

            cartAdapter = new CartAdapter(options);
            cartView.setAdapter(cartAdapter);


        cartTotal = root.findViewById(R.id.cartTotal);
        dr = FirebaseDatabase.getInstance().getReference().child("Cart").child(userID);
        dr.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Double sum = 0.00;
                for (DataSnapshot ds : snapshot.getChildren()){
                    Map<String, Object> map = (Map<String, Object>) ds.getValue();
                    Object price = map.get("totalprice");
                    Double value = Double.parseDouble(String.valueOf(price));
                    sum += value;
                    if (sum != 0.00) {
                        cartTotal.setText(String.format("%.2f", sum));
                        buy.setBackgroundColor(getResources().getColor(R.color.green));
                        buy.setEnabled(true);
                    }else {
                        cartTotal.setText("0.00");
                        buy.setEnabled(false);
                        buy.setBackgroundColor(Color.GRAY);
                    }


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             payment(cartTotal.getText().toString());
            }
        });


       Checkout.preload(getActivity().getApplicationContext());

        return root;

    }

    @Override
    public void onStart() {
        super.onStart();
        cartAdapter.startListening();
    }

    @Override
    public void onStop(){
        super.onStop();
        cartAdapter.stopListening();
    }
private void payment(String amount){
//        Checkout checkout = new Checkout();
//        checkout.setImage(R.drawable.money);
//        checkout.setKeyID("<rzp_test_DWHMaR8R32CI7x>");
//       final Activity activity = getActivity();
//
//    /**
//     * Pass your payment options to the Razorpay Checkout as a JSONObject
//     */
//    try {
//        JSONObject options = new JSONObject();
//
//        options.put("name", "Merchant Name");
//        options.put("description", "Reference No. #123456");
//        options.put("image", "https://s3.amazonaws.com/rzp-mobile/images/rzp.png");
//        options.put("order_id", "order_DBJOWzybf0sJbb");//from response of step 3.
//        options.put("theme.color", "#3399cc");
//        options.put("currency", "INR");
//        options.put("amount", "50000");//pass amount in currency subunits
//        options.put("prefill.email", "gaurav.kumar@example.com");
//        options.put("prefill.contact","9988776655");
//        JSONObject retryObj = new JSONObject();
//        retryObj.put("enabled", true);
//        retryObj.put("max_count", 4);
//        options.put("retry", retryObj);
//
//        assert activity != null;
//        checkout.open(activity, options);
//
//    } catch(Exception e) {
//        Log.e(TAG, "Error in starting Razorpay Checkout", e);
//    }

    final Activity activity = getActivity();

    Checkout checkout = new Checkout();
    checkout.setKeyID("rzp_test_DWHMaR8R32CI7x");
//    checkout.setImage(R.drawable.icon);

    double finalAmount = Float.parseFloat(amount)*100;

    try {
        JSONObject options = new JSONObject();
        options.put("name", "KHAO");
        options.put("description", "Reference No. #123456");
        options.put("image", "https://s3.amazonaws.com/rzp-mobile/images/rzp.png");
        // options.put("order_id", "order_DBJOWzybf0sJbb");//from response of step 3.
        options.put("theme.color", "#3399cc");
        options.put("currency", "INR");
        options.put("amount", finalAmount+"");//300 X 100
        options.put("prefill.email", firebaseUser.getEmail() );
        options.put("prefill.contact",firebaseUser.getPhoneNumber());

        checkout.open(activity, options);

    } catch(Exception e) {
        Log.e("TAG", "Error in starting Razorpay Checkout", e);
    }

}



}