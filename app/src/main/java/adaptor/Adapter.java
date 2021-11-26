package adaptor;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.khao.DetailsActivity;
import com.example.khao.Food;
import com.example.khao2.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import model.food;


public class Adapter extends FirebaseRecyclerAdapter<Food, Adapter.myviewholder>
{

    public Adapter(@NonNull FirebaseRecyclerOptions<Food> options) {
        super(options);
    }

    @Override
    public void onBindViewHolder(@NonNull myviewholder holder, int position, @NonNull Food food ){
    holder.name.setText(food.getName());
    holder.price.setText(food.getPrice());
    holder.rating.setText(food.getRating());
    holder.resturantname.setText(food.getResturantname());
    Glide.with(holder.image.getContext()).load(food.getImageurl()).into(holder.image);
    Boolean Type = Boolean.parseBoolean(food.getVeg());
    String imageurl = food.getImageurl();
    String name = food.getName();
    String rating = food.getRating();
    String description = food.getDescription();
    String resturant= food.getResturantname();
    String price = food.getPrice();

    if (Type!= true){
        holder.type.setImageResource(R.drawable.nonveg);
    }
    else {
        holder.type.setImageResource(R.drawable.veg);
    }



    holder.itemView.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
           Intent intent = new Intent(holder.itemView.getContext(), DetailsActivity.class);
           intent.putExtra("Type",Type);
           intent.putExtra("Name", name);
           intent.putExtra("Rating", rating);
           intent.putExtra("Description", description);
           intent.putExtra("Image", imageurl);
           intent.putExtra("Resturant", resturant);
           intent.putExtra("Price", price);

           holder.itemView.getContext().startActivity(intent);

        }
    });

    }


    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.asian_food, parent, false);
        return new myviewholder(view);

    }



    class myviewholder extends RecyclerView.ViewHolder
{

    TextView name, price, rating, resturantname;
    ImageView image, type;
    public myviewholder(@NonNull View itemView) {
        super(itemView);
        name = itemView.findViewById(R.id.foodname);
        price = itemView.findViewById(R.id.foodprice);
        rating = itemView.findViewById(R.id.rating);
        resturantname = itemView.findViewById(R.id.resturant_name);
        image = itemView.findViewById(R.id.foodimg);
        type = itemView.findViewById(R.id.asian_type);

    }
}
}