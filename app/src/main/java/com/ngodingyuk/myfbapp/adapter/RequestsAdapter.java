package com.ngodingyuk.myfbapp.adapter;

import android.app.Activity;
import android.content.Intent;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ngodingyuk.myfbapp.MainActivity;
import com.ngodingyuk.myfbapp.R;
import com.ngodingyuk.myfbapp.model.Requests;

import java.util.List;

public class RequestsAdapter extends RecyclerView.Adapter<RequestsAdapter.MyHolder> {
    private List<Requests> listData;
    private Activity mActivity;

    public RequestsAdapter(List<Requests> listData, Activity mActivity) {
        this.listData = listData;
        this.mActivity = mActivity;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_request, parent, false);
        return new MyHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, final int position) {
        final Requests data = listData.get(position);

        holder.tvName.setText(data.getName());
        holder.tvEmail.setText(data.getEmail());
        holder.tvDescription.setText(data.getDesc());

        holder.ll_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goDetail = new Intent(mActivity, MainActivity.class);
                goDetail.putExtra(MainActivity.DATA, listData.get(position));

                mActivity.startActivity(goDetail);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        private LinearLayout ll_data;
        private TextView tvName, tvEmail, tvDescription;

        public MyHolder(@NonNull View itemView) {
            super(itemView);

            ll_data = itemView.findViewById(R.id.ll_data);
            tvName = itemView.findViewById(R.id.tv_name);
            tvEmail = itemView.findViewById(R.id.tv_email);
            tvDescription = itemView.findViewById(R.id.tv_description);
        }
    }
}
