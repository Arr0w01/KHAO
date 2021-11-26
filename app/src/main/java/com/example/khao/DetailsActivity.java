package com.example.khao;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.khao.ui.dashboard.DashboardFragment;
import com.example.khao2.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;

import model.Cart;

import static com.example.khao.MainActivity.pinCode;

public class DetailsActivity extends AppCompatActivity {
     ImageView detail_img,back, bookmark,type,addCart, removeCart;
     TextView rating, name,resturant,description, price, itemNumber, pin, discount;
     Button checkoutButton;
     String Name, Image, Resturant, Price;
     String saveCurrentTIme, saveCurrentDate, PINCODE;
     Double discountPrice;
     Boolean firstimage = true, Type;
     FirebaseDatabase firebaseDatabase;
     DatabaseReference databaseReference;
     Cart cart;
     String userID, uid;
     Double tp;
     FirebaseAuth auth;
     FirebaseUser user;
     Calendar calForDate;
     SimpleDateFormat currentDate, currentTime;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

//        try {
             calForDate = Calendar.getInstance();
           currentDate = new SimpleDateFormat("MMM dd, yyyy");
           currentTime = new SimpleDateFormat("HH:mm:ss a");

            firebaseDatabase = FirebaseDatabase.getInstance();
//        userID = userMain.getUid();
            auth = FirebaseAuth.getInstance();
            user = auth.getCurrentUser();
            userID = user.getUid();
            cart = new Cart();


            bookmark = findViewById(R.id.bookmark);
            detail_img = findViewById(R.id.detail_img);
            rating = findViewById(R.id.detail_rating);
            back = findViewById(R.id.back);
            name = findViewById(R.id.detail_foodname);
            resturant = findViewById(R.id.detail_resturant);
            type = findViewById(R.id.type);
            description = findViewById(R.id.description);
            price = findViewById(R.id.detail_price);
            pin = findViewById(R.id.pincode);
            if (pinCode != null) {
                PINCODE = pinCode;
                pin.setText(PINCODE);
            }
            discount = findViewById(R.id.discount);

            pin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
//                        editPincode();
                    } catch (Exception e) {
                        Toast.makeText(DetailsActivity.this, "" + e, Toast.LENGTH_SHORT).show();
                    }
                }
            });

            back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });

            bookmark.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String Name = name.getText().toString();

                    if ((bookmark != null) && (firstimage)) {
                        Toast.makeText(DetailsActivity.this, Name + " Added To Bookmark", Toast.LENGTH_SHORT).show();
                        bookmark.setImageResource(R.drawable.ic_baseline_bookmark_24);
                        firstimage = false;
                    } else {
                        if (bookmark != null) {
                            bookmark.setImageResource(R.drawable.ic_baseline_bookmark_border_24);
                            Toast.makeText(DetailsActivity.this, Name + " Removed From Bookmark", Toast.LENGTH_SHORT).show();
                            firstimage = true;
                        }
                    }


                }
            });

            Intent intent = getIntent();
            String Rating = intent.getStringExtra("Rating");
            Name = intent.getStringExtra("Name");
            Resturant = intent.getStringExtra("Resturant");
            Image = intent.getStringExtra("Image");
            String Description = intent.getStringExtra("Description");
            Price = intent.getStringExtra("Price");
            if (PINCODE != null) {
                if (PINCODE.equals("700104")) {
                    discountPrice = Double.parseDouble(Price) - ((Double.parseDouble(Price) / 100) * 10);
                    discount.setText("Discounted " + Price + " - " + ((Double.parseDouble(Price) / 100) * 10));
                } else if (PINCODE.equals("700103")) {
                    discountPrice = Double.parseDouble(Price) - ((Double.parseDouble(Price) / 100) * 5);
                    discount.setText("Discounted " + Price + " - " + ((Double.parseDouble(Price) / 100) * 5));
                } else {
                    discountPrice = Double.parseDouble(Price);
                    discount.setText("No Discount Available in this Pincode");
                }
            }


            if (intent.getBooleanExtra("Type", false)) {
                Type = true;
            } else {
                Type = false;
            }
            rating.setText(Rating);
            name.setText(Name);
            resturant.setText(Resturant);
//        detail_img.setImageResource(Image);

            Glide.with(getBaseContext()).load(Image).into(detail_img);


            description.setText(Description);
            price.setText(discountPrice.toString());

            if (Type != false) {
                type.setImageResource(R.drawable.veg);
            } else if (Type != true) {
                type.setImageResource(R.drawable.nonveg);
            }


//         Checkout position

            addCart = findViewById(R.id.add_cart);
            removeCart = findViewById(R.id.remove_cart);
            itemNumber = findViewById(R.id.item_number);
            checkoutButton = findViewById(R.id.checkoutButton);


//  Count + item
            addCart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Integer itemCount = Integer.parseInt(itemNumber.getText().toString());
                    Integer p = Integer.parseInt(Price);
                    Integer add = itemCount + 1;
                    Integer currentPrice = p * add;

                    DecimalFormat decimalFormat = new DecimalFormat("00");
                    String Add = decimalFormat.format(add);

                    itemNumber.setText(Add);
                    price.setText(currentPrice.toString());
                }
            });
// Count - items
            removeCart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Integer itemCount = Integer.parseInt(itemNumber.getText().toString());
                    Integer p = Integer.parseInt(Price);
                    Integer remove = itemCount - 1;
                    DecimalFormat decimalFormat = new DecimalFormat("00");
                    String Remove = decimalFormat.format(remove);
                    Integer currentPrice = p * remove;
                    if (itemCount > 0) {
                        itemNumber.setText(Remove);
                        price.setText(currentPrice.toString());
                    }

                }
            });

//        Add To Cart

            checkoutButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Integer i = Integer.parseInt(itemNumber.getText().toString());
                    if (i > 0) {
                        AddCart();

                    } else {
                        Toast.makeText(DetailsActivity.this, "Please Add Atleast 1 item", Toast.LENGTH_SHORT).show();
                    }
                }
            });
//        }catch (Exception e){}

    }
    private void  AddCart(){
        saveCurrentDate = currentDate.format(calForDate.getTime());
        saveCurrentTIme = currentTime.format(calForDate.getTime());
        Double d = Double.parseDouble(Price);
         tp = Double.parseDouble(price.getText().toString());
        cart.setItemcount(itemNumber.getText().toString());
        cart.setTotalprice(String.format("%.2f",tp));
        uid = Name+ saveCurrentDate+saveCurrentTIme;
        cart.setUid(uid);

        cart.setName(Name);
        cart.setImageurl(Image);
        cart.setPrice(String.format("%.2f", d));

        cart.setResturantname(Resturant);
        cart.setVeg(Type.toString());

        databaseReference = firebaseDatabase.getReference("Cart").child(userID).child(uid);
        if (cart != null) {
            databaseReference.setValue(cart)

                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(DetailsActivity.this, "Item Has Been Added To Cart", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(DetailsActivity.this, "failed to add Cart", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }else {
            Toast.makeText(this, "data not fetched", Toast.LENGTH_SHORT).show();
        }
    }

//    private void editPincode() {
//        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(DetailsActivity.this, R.style.BottomSheetDialog);
//        bottomSheetDialog.setContentView(R.layout.edit_name);
//        EditText getName;
//        Button setName;
//        ProgressBar updatenamePB;
//
//        getName = bottomSheetDialog.findViewById(R.id.typeName);
//        setName = bottomSheetDialog.findViewById(R.id.updateNameBtn);
//        updatenamePB = bottomSheetDialog.findViewById(R.id.updateNamePB);
//
//        setName.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                updatenamePB.setVisibility(View.VISIBLE);
//                setName.setVisibility(View.INVISIBLE);
//
//                String newName = getName.getText().toString();
//                PINCODE = newName;
//                pin.addTextChangedListener(new TextWatcher() {
//                    @Override
//                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//                    }
//
//                    @Override
//                    public void onTextChanged(CharSequence s, int start, int before, int count) {
//                        updatenamePB.setVisibility(View.GONE);
//                        bottomSheetDialog.cancel();
//                        discount();
//                    }
//
//                    @Override
//                    public void afterTextChanged(Editable s) {
//
//                    }
//                });
//                pin.setText(PINCODE);
//
//
//            }
//        });
//
//
//        bottomSheetDialog.show();
//        bottomSheetDialog.getWindow().findViewById(R.id.bgmain).setBackgroundResource(android.R.color.transparent);
//
//    }

    private void discount(){
        if (PINCODE.equals("700104")){
            discountPrice = Double.parseDouble(Price) -( (Double.parseDouble(Price)/100)*10) ;
            discount.setText("Discounted "+Price +" - " +((Double.parseDouble(Price)/100)*10));
        }
        else if (PINCODE.equals("700103")){
            discountPrice = Double.parseDouble(Price) -( (Double.parseDouble(Price)/100)*5) ;
            discount.setText("Discounted "+Price +" - " +((Double.parseDouble(Price)/100)*5));
        }else {
            discountPrice = Double.parseDouble(Price);
            discount.setText("No Discount Available in this Pincode");
        }
       price.setText(discountPrice.toString());
    }
}
