
package adaptor;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.khao.DetailsActivity;
import com.example.khao.Food;
import com.example.khao2.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DecimalFormat;

import model.Cart;

import static com.example.khao.ui.SigninActivity.mAuth;
import static com.example.khao.ui.dashboard.DashboardFragment.cartTotal;

public class CartAdapter extends FirebaseRecyclerAdapter<Cart, CartAdapter.myviewholder>
{
     Integer itemC, newCount;
     Double  newPrice;
     String finalc;

    public CartAdapter(@NonNull FirebaseRecyclerOptions<Cart> options) {
        super(options);
    }

    @Override
    public void onBindViewHolder(@NonNull myviewholder holder, int position, @NonNull Cart cart ){
        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = auth.getCurrentUser();
        String userID = firebaseUser.getUid();
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference("Cart").child(userID).child(cart.getUid());

         holder.name.setText(cart.getName());
        holder.price.setText(cart.getTotalprice());
//        holder.rating.setText(cart.getRating());
        holder.resturantname.setText(cart.getResturantname());
        holder.itemCount.setText(cart.getItemcount());
        Glide.with(holder.image.getContext()).load(cart.getImageurl()).centerCrop().into(holder.image);
        Boolean Type = Boolean.parseBoolean(cart.getVeg());
        String imageurl = cart.getImageurl();
        String name = cart.getName();
        String rating = cart.getRating();
        String description = cart.getDescription();
        String resturant= cart.getResturantname();
        String price = cart.getPrice();

        if (Type!= true){
            holder.type.setImageResource(R.drawable.nonveg);
        }
        else {
            holder.type.setImageResource(R.drawable.veg);
        }

        holder.add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DecimalFormat decimalFormat = new DecimalFormat("00");
                itemC =   Integer.parseInt(cart.getItemcount());
                newCount = itemC + 1;
                finalc = decimalFormat.format(newCount);
              databaseReference.child("itemcount").setValue(finalc);
               newPrice = Double.parseDouble(price) * newCount;
                Double d = Double.parseDouble(newPrice.toString());
              databaseReference.child("totalprice").setValue(String.format("%.2f",d));


            }
        });

        holder.remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DecimalFormat decimalFormat = new DecimalFormat("00");
                itemC = Integer.parseInt(cart.getItemcount());
                newCount = itemC -1;
                finalc = decimalFormat.format(newCount);
                databaseReference.child("itemcount").setValue(finalc);
                newPrice = Double.parseDouble(price) * newCount;
                Double d = Double.parseDouble(newPrice.toString());
                databaseReference.child("totalprice").setValue(String.format("%.2f",d));
                if (newCount <= 0){
                    databaseReference.removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                         if (task.isSuccessful()){
                             Toast.makeText(holder.itemView.getContext(), cart.getName()+" has been removed from cart", Toast.LENGTH_SHORT).show();
                         }
                        }
                    });
                }

            }
        });

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseReference.child("totalprice").setValue("0.00");
                databaseReference.removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(holder.itemView.getContext(), cart.getName()+" has been removed from cart", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });

    }


    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_model, parent, false);
        return new myviewholder(view);

    }



    class myviewholder extends RecyclerView.ViewHolder
    {

        TextView name, price, rating, resturantname, itemCount,add, remove;
        ImageView image, type, delete;
        public myviewholder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.c_food_name);
            itemCount = itemView.findViewById(R.id.c_item_count);
            add = itemView.findViewById(R.id.c_itemadd);
            remove = itemView.findViewById(R.id.c_itemremove);
            price = itemView.findViewById(R.id.c_price);
            delete = itemView.findViewById(R.id.c_delete);
//            rating = itemView.findViewById(R.id.rating);
            resturantname = itemView.findViewById(R.id.c_resturant);
            image = itemView.findViewById(R.id.c_image);
            type = itemView.findViewById(R.id.c_type);

        }
    }
}
