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

public class HealthCompanyAdapter extends RecyclerView.Adapter<HealthCompanyAdapter.HealthCompanyHolder> {
    private ArrayList<HealthCompany> userList;
    private Context context;
    private OnItemClickListener listener;
    private StorageReference reference2;

    public HealthCompanyAdapter(ArrayList<HealthCompany> userList, Context context) {
        this.userList = userList;
        this.context = context;
    }

    @NonNull
    @Override
    public HealthCompanyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.company, parent, false);
        return new HealthCompanyHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull HealthCompanyHolder holder, int position) {
        HealthCompany healthCompany = userList.get(position);
        holder.setData(healthCompany);
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    class HealthCompanyHolder extends RecyclerView.ViewHolder {
        TextView company, ceo;
        ImageView image;

        public HealthCompanyHolder(@NonNull View itemView) {
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

        public void setData(HealthCompany healthCompany) {
            this.company.setText(healthCompany.getCompany());
            this.ceo.setText(healthCompany.getCeo());

            reference2 = FirebaseStorage.getInstance().getReference(healthCompany.getUid() + ".jpg");

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
        void onItemClick(HealthCompany healthCompany, int position);
    }

    public void setOnItemClickListener(HealthCompanyAdapter.OnItemClickListener listener) {
        this.listener = listener;
    }
}
