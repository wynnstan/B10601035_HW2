package com.example.B10601035_HW2;

import android.content.Context;
import android.database.Cursor;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.B10601035_HW2.data.WaitlistContract;

public class GuestListAdapter extends RecyclerView.Adapter<GuestListAdapter.WaitListViewHolder> {

    private Context context;
    private Cursor cursor;

    public GuestListAdapter(Context context, Cursor cursor) {
        this.context = context;
        this.cursor = cursor;
    }

    @NonNull
    @Override
    public WaitListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.waitlist_item, parent, false);
        return new WaitListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WaitListViewHolder holder, int position) {
        if (!cursor.moveToPosition(position)) return;
        String name = cursor.getString(cursor.getColumnIndex(WaitlistContract.WaitlistEntry.COLUMN_GUEST_NAME));
        int partySize = cursor.getInt(cursor.getColumnIndex(WaitlistContract.WaitlistEntry.COLUMN_PARTY_SIZE));
        long id = cursor.getLong(cursor.getColumnIndex(WaitlistContract.WaitlistEntry._ID));
        GradientDrawable gd = (GradientDrawable) holder.partySizeTextView.getBackground();
        gd.setColor(MainActivity.getShapeColor());
        holder.nameTextView.setText(name);
        holder.partySizeTextView.setText(String.valueOf(partySize));
        holder.itemView.setTag(id);
    }

    @Override
    public int getItemCount() {
        return cursor.getCount();
    }

    public void swapCursor(Cursor newCursor) {
        if (cursor != null) cursor.close();
        cursor = newCursor;
        if (newCursor != null) {
            this.notifyDataSetChanged();
        }
    }

    class WaitListViewHolder extends RecyclerView.ViewHolder {

        TextView nameTextView;
        TextView partySizeTextView;

        public WaitListViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = (TextView) itemView.findViewById(R.id.name_text_view);
            partySizeTextView = (TextView) itemView.findViewById(R.id.party_size_text_view);
        }
    }

}
