package adaptor;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.khao.CategoryView;
import com.example.khao.DetailsActivity;
import com.example.khao.Food;
import com.example.khao2.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import java.util.List;

import model.popularFood;

public class CategoryAdapter extends FirebaseRecyclerAdapter<Food, CategoryAdapter.myviewholder>
{

    public CategoryAdapter(@NonNull FirebaseRecyclerOptions<Food> options) {
        super(options);
    }

    @Override
    public void onBindViewHolder(@NonNull myviewholder holder, int position, @NonNull Food food ){
        holder.name.setText(food.getName());
//

              Glide.with(holder.image.getContext()).load(food.getImageurl()).into(holder.image);


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(holder.itemView.getContext(), CategoryView.class);
                intent.putExtra("Category", food.getName());

                holder.itemView.getContext().startActivity(intent);
            }
        });

    }


    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_layout, parent, false);
        return new myviewholder(view);

    }



    class myviewholder extends RecyclerView.ViewHolder
    {

        TextView name;
        ImageView image;
        public myviewholder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.cat_name);
            image = itemView.findViewById(R.id.cat_img);

        }
    }
}
