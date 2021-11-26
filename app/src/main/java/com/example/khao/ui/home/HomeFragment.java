package com.example.khao.ui.home;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.khao.Food;
import com.example.khao2.R;
import com.example.khao2.SearchActivity;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import adaptor.Adapter;
import adaptor.CategoryAdapter;
import adaptor.ResturantAdapter;
import model.Resturant;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    RecyclerView asian_RV, resturantRv, popularRV;
    FirestoreRecyclerAdapter resAdapter;
    Adapter adapter;
    CategoryAdapter adapter1;
    Button retry;
    ImageView search;
   public static ResturantAdapter adapter3;
    public static TextView CurrentLocation, SubLocation;
    private static RelativeLayout noNetwork, network;
    public static ConnectivityManager comMg;
    public static NetworkInfo netInfo;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);

//         Id Assign
            noNetwork = root.findViewById(R.id.noInternet_layout);
            network = root.findViewById(R.id.internet_layout);
            CurrentLocation = root.findViewById(R.id.currLocation);
            SubLocation = root.findViewById(R.id.fullLoc);
            resturantRv = root.findViewById(R.id.resturantRV);
            retry = root.findViewById(R.id.retry_button);
            search = root.findViewById(R.id.home_search);


//            Search Button
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), SearchActivity.class);
                startActivity(intent);
            }
        });

//            Features Food Layout
            try {


                asian_RV = (RecyclerView) root.findViewById(R.id.asian_recycler);
                asian_RV.setLayoutManager(new LinearLayoutManager(getActivity()));
                FirebaseRecyclerOptions<Food> options =
                        new FirebaseRecyclerOptions.Builder<Food>()
                                .setQuery(FirebaseDatabase.getInstance().getReference().child("Food"), Food.class)
                                .build();




                adapter = new Adapter(options);
                asian_RV.setAdapter(adapter);
                asian_RV.setNestedScrollingEnabled(false);


            } catch (Exception e){
                Toast.makeText(getContext(),""+ e, Toast.LENGTH_LONG).show();
            }catch (Error error){
                Toast.makeText(getContext(), ""+error, Toast.LENGTH_SHORT).show();
            }


//            Category view

            popularRV = root.findViewById(R.id.popular_recycler);
            popularRV.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL,false));
            FirebaseRecyclerOptions<Food> options1 =
                    new FirebaseRecyclerOptions.Builder<Food>()
                            .setQuery(FirebaseDatabase.getInstance().getReference().child("Category"), Food.class)
                            .build();
            adapter1 = new CategoryAdapter(options1);
            popularRV.setAdapter(adapter1);

//            Restaurants View

            FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
            Query query = firebaseFirestore.collection("resturants");
            FirestoreRecyclerOptions<Resturant> resOptions = new FirestoreRecyclerOptions.Builder<Resturant>().
                    setQuery(query, Resturant.class).build();


            adapter3 = new ResturantAdapter(resOptions);
            resturantRv.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
            resturantRv.setAdapter(adapter3);
            resturantRv.setNestedScrollingEnabled(false);


//            No Internet

          retry.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  checkInternet();
              }
          });
        return root;


    }



    @Override
    public void onStart() {
        super.onStart();
            adapter.startListening();
            adapter1.startListening();
            adapter3.startListening();
        checkInternet();
    }

    @Override
    public void onResume() {
        super.onResume();
        checkInternet();
    }

    @Override
    public void onStop(){
        super.onStop();
        adapter.stopListening();
        adapter1.stopListening();
        adapter3.stopListening();
    }

    private void checkInternet(){

         comMg = (ConnectivityManager)getActivity().getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
         netInfo = comMg.getActiveNetworkInfo();

        if (netInfo != null &&  netInfo.isConnectedOrConnecting()){

            noNetwork.setVisibility(View.GONE);
            network.setVisibility(View.VISIBLE);
        }else {
            noNetwork.setVisibility(View.VISIBLE);
            network.setVisibility(View.GONE);
        }


    }
}