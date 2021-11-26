package adaptor;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.PorterDuff;
import android.location.Address;
import android.location.Geocoder;
import android.text.Editable;
import android.text.TextWatcher;
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
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import model.Resturant;

import static com.example.khao.MainActivity.latitude;
import static com.example.khao.MainActivity.longitude;
import static com.example.khao.ui.home.HomeFragment.CurrentLocation;
import static com.example.khao.ui.home.HomeFragment.SubLocation;

public class ResturantAdapter extends FirestoreRecyclerAdapter<Resturant, ResturantAdapter.myviewholder>
{

    public ResturantAdapter(@NonNull FirestoreRecyclerOptions<Resturant> options) {
        super(options);
    }

    @Override
    public void onBindViewHolder(@NonNull myviewholder holder, int position, @NonNull Resturant resturant  ){
        holder.name.setText(resturant.getName());
//        holder.price.setText(food.getMinimumbudget());
//        holder.rating.setText(food.getRating());
        Glide.with(holder.image.getContext()).load(resturant.getImageurl()).into(holder.image);
        ColorMatrix matrix = new ColorMatrix();
        matrix.setSaturation(0);
        ColorMatrixColorFilter filter = new ColorMatrixColorFilter(matrix);

        Boolean status = Boolean.parseBoolean(resturant
                .getOnline());
        String imageurl = resturant.getImageurl();
        String name = resturant.getName();
        String rating = resturant.getRating();

        if (status!= true){
         holder.image.setColorFilter(filter);
         holder.online.setText("Currently Not Taking Orders");
         holder.online.setTextColor(Color.RED);
         holder.onlineLogo.setColorFilter(Color.RED);
        }
        else {
         holder.image.setColorFilter(null);
         holder.onlineLogo.setColorFilter(Color.GREEN);
         holder.online.setText("Online");
         holder.online.setTextColor(Color.GREEN);
        }

        holder.price.setText(resturant.getMinimumbudget()+" For One");
        holder.type.setText(resturant.getType());
        holder.rating.setText(rating);

        Double res_lat = Double.parseDouble(resturant.getLat());
        Double res_lon = Double.parseDouble(resturant.getLon());

        Double distanceVal = Distance(latitude,-longitude,res_lat,- res_lon) ;
        String dvalue = String.format("%.2f", distanceVal);
        dvalue = dvalue.substring(0, dvalue.length() - 1);
        holder.distance.setText(dvalue + "kms");
      
        try {
            Geocoder geo = new Geocoder(holder.itemView.getContext(), Locale.getDefault());
            List<Address> addresses = null;
            try {
                addresses = geo.getFromLocation(res_lat, res_lon, 1);
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (addresses.isEmpty()) {

            } else {

                holder.address.setText(" "+addresses.get(0).getSubLocality());
//                SubLocation.setText(addresses.get(0).getAddressLine(0));
            }
        } catch (Exception e) {
//
        }





        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(holder.itemView.getContext(), CategoryView.class);
                intent.putExtra("Name", name);
                intent.putExtra("Rating", rating);
                intent.putExtra("Image", imageurl);

                holder.itemView.getContext().startActivity(intent);

            }
        });

    }


    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.resturant_view, parent, false);
        return new myviewholder(view);

    }



    class myviewholder extends RecyclerView.ViewHolder
    {

        TextView name, price, rating, type, distance, online, address;
        ImageView image, onlineLogo;
        public myviewholder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.res_name);
            price = itemView.findViewById(R.id.minBudget);
            rating = itemView.findViewById(R.id.res_rating);
            online = itemView.findViewById(R.id.res_online);
            image = itemView.findViewById(R.id.resImage);
            onlineLogo = itemView.findViewById(R.id.online_logo);
            type = itemView.findViewById(R.id.res_type);
            distance = itemView.findViewById(R.id.res_distance);
            address = itemView.findViewById(R.id.res_address);
        }
    }
    private double Distance(double lat1, double lon1, double lat2, double lon2){
        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2dag(dist);
        dist = dist * 60 * 1.1515;
        dist = dist * 1.609344;
        return (dist);
    }
    private double deg2rad(double deg){
        return (deg * Math.PI / 180.0);
    }
    private  double rad2dag(double rad){
        return  (rad * 180.0 / Math.PI);
    }
}
