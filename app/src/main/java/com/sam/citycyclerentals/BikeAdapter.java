package com.sam.citycyclerentals;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class BikeAdapter extends RecyclerView.Adapter<BikeAdapter.BikeViewHolder> {

    private Context context;
    private List<Bike> bikeList;
    private OnBikeClickListener listener;

    public interface OnBikeClickListener {
        void onBikeClick(Bike bike);
    }

    public BikeAdapter(Context context, List<Bike> bikeList, OnBikeClickListener listener) {
        this.context = context;
        this.bikeList = bikeList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public BikeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_bike, parent, false);
        return new BikeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BikeViewHolder holder, int position) {
        Bike bike = bikeList.get(position);
        holder.bind(bike);
    }

    @Override
    public int getItemCount() {
        return bikeList.size();
    }

    public class BikeViewHolder extends RecyclerView.ViewHolder {

        private TextView bikeName, bikePrice,BikeId;
        private ImageView bikeImage;
        private Button buttonRentNow;

        public BikeViewHolder(@NonNull View itemView) {
            super(itemView);
            bikeName = itemView.findViewById(R.id.bikeName);
            bikePrice = itemView.findViewById(R.id.bikePrice);
            bikeImage = itemView.findViewById(R.id.bikeImage);
            buttonRentNow = itemView.findViewById(R.id.rentButton);
        }

        public void bind(final Bike bike) {
            bikeName.setText(bike.getBikeName());
            bikePrice.setText("Price: $" + bike.getBikePrice());
            bikeImage.setImageResource(bike.getBikeImage());

            buttonRentNow.setOnClickListener(v -> {
                Intent intent = new Intent(context, RentActivity.class);
                intent.putExtra("bike_id", bike.getId());
                intent.putExtra("bike_name", bike.getBikeName());
                intent.putExtra("bike_price", bike.getBikePrice());
                intent.putExtra("bike_image", bike.getBikeImage());

                intent.putExtra("start_date", "2025-03-12");
                intent.putExtra("end_date", "2025-03-14");
                intent.putExtra("rental_duration", 2);

                context.startActivity(intent);
            });
        }
    }
}
