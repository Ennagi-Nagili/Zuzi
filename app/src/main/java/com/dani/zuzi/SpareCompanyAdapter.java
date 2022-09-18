package com.dani.zuzi;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.dani.zuzi.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.lang.ref.Reference;
import java.util.ArrayList;

public class SpareCompanyAdapter extends RecyclerView.Adapter<SpareCompanyAdapter.SpareCompanyHolder> {
    private ArrayList<SpareCompany> userList;
    private Context context;
    private OnItemClickListener listener;
    private StorageReference reference2;

    public SpareCompanyAdapter(ArrayList<SpareCompany> userList, Context context) {
        this.userList = userList;
        this.context = context;
    }

    @NonNull
    @Override
    public SpareCompanyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.company, parent, false);
        return new SpareCompanyHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull SpareCompanyHolder holder, int position) {
        SpareCompany spareCompany = userList.get(position);
        holder.setData(spareCompany);
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    class SpareCompanyHolder extends RecyclerView.ViewHolder {
        TextView company, ceo;
        ImageView image;

        public SpareCompanyHolder(@NonNull View itemView) {
            super(itemView);
            company = itemView.findViewById(R.id.company_name);
            ceo = itemView.findViewById(R.id.company_ceo);
            image = itemView.findViewById(R.id.company_image);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();

                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(userList.get(position), position);
                    }
                }
            });
        }

        public void setData(SpareCompany spareCompany) {
            this.company.setText(spareCompany.getCompany());
            this.ceo.setText(spareCompany.getCeo());

            reference2 = FirebaseStorage.getInstance().getReference(spareCompany.getUid() + ".jpg");

            reference2.getDownloadUrl()
                    .addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            if (uri != null) {
                                Glide.with(context)
                                        .load(uri)
                                        .into(image);
                            }

                            else {
                                image.setImageResource(R.drawable.ic_profile);
                            }
                        }
                    });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(SpareCompany spareCompany, int position);
    }

    public void setOnItemClickListener(SpareCompanyAdapter.OnItemClickListener listener) {
        this.listener = listener;
    }
}
