package com.example.smartpantrymanager;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class PantryAdapter extends RecyclerView.Adapter<PantryAdapter.PantryViewHolder> {

    private Context context;
    private List<PantryItem> itemList;
    private DatabaseHelper dbHelper;

    public PantryAdapter(Context context, List<PantryItem> itemList, DatabaseHelper dbHelper) {
        this.context = context;
        this.itemList = itemList;
        this.dbHelper = dbHelper;
    }

    @NonNull
    @Override
    public PantryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_pantry, parent, false);
        return new PantryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PantryViewHolder holder, int position) {
        PantryItem item = itemList.get(position);
        holder.tvName.setText(item.getName().toUpperCase());
        holder.tvDetails.setText("Qty: " + item.getQuantity() + " " + item.getUnit() + " | Exp: " + item.getExpiry());

        holder.btnEdit.setOnClickListener(v -> {
            Intent intent = new Intent(context, AddEditActivity.class);
            intent.putExtra("ID", item.getId());
            intent.putExtra("NAME", item.getName());
            intent.putExtra("QTY", item.getQuantity());
            intent.putExtra("UNIT", item.getUnit());
            intent.putExtra("EXPIRY", item.getExpiry());
            context.startActivity(intent);
        });

        holder.btnDelete.setOnClickListener(v -> {
            if (dbHelper.deletePantryItem(item.getId())) {
                itemList.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, itemList.size());
                Toast.makeText(context, "Item deleted", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public static class PantryViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvDetails;
        ImageButton btnEdit, btnDelete;

        public PantryViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvItemName);
            tvDetails = itemView.findViewById(R.id.tvItemDetails);
            btnEdit = itemView.findViewById(R.id.btnEdit);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }
    }
}