package com.sam.citycyclerentals;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class RentalAdapter extends RecyclerView.Adapter<RentalAdapter.ViewHolder> {

    private Context context;
    private List<Rental> rentalList;
    private DatabaseHelper dbHelper;

    public RentalAdapter(Context context, List<Rental> rentalList) {
        this.context = context;
        this.rentalList = rentalList;
        this.dbHelper = new DatabaseHelper(context);
    }

    public RentalAdapter(RentalDetailActivity context, List<Rental> ongoingRentals, RentalDetailActivity rentalDetailActivity) {
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_rental, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Rental rental = rentalList.get(position);

        holder.bikeName.setText("Bike ID: " + rental.getBikeId());
        holder.rentalPeriod.setText("From: " + rental.getStartDate() + " To: " + rental.getEndDate());
        holder.paymentMethod.setText("Payment: " + rental.getPaymentMethod());
        holder.totalPrice.setText("Total: $" + rental.getTotalPrice());

        if ("Ongoing".equals(rental.getStatus())) {
            holder.finishRentalButton.setVisibility(View.VISIBLE);
        } else {
            holder.finishRentalButton.setVisibility(View.GONE);
        }

        holder.finishRentalButton.setOnClickListener(view -> {
            boolean updated = dbHelper.updateRentalStatus(rental.getId(), "Completed");
            if (updated) {
                Toast.makeText(context, "Rental Completed!", Toast.LENGTH_SHORT).show();

                rental.setStatus("Completed");
                rentalList.remove(position);
                notifyItemRemoved(position);
            } else {
                Toast.makeText(context, "Error updating rental status.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return rentalList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView bikeName, rentalPeriod, paymentMethod, totalPrice;
        Button finishRentalButton;

        public ViewHolder(View itemView) {
            super(itemView);
            bikeName = itemView.findViewById(R.id.bikeName);
            rentalPeriod = itemView.findViewById(R.id.rentalPeriod);
            paymentMethod = itemView.findViewById(R.id.paymentMethod);
            totalPrice = itemView.findViewById(R.id.totalPrice);
            finishRentalButton = itemView.findViewById(R.id.buttonEndRental);
        }
    }
}
