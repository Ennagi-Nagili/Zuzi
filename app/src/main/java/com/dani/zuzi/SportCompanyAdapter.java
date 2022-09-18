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

import java.util.ArrayList;

public class SportCompanyAdapter extends RecyclerView.Adapter<SportCompanyAdapter.SportCompanyHolder> {
    private ArrayList<SportCompany> userList;
    private Context context;
    private OnItemClickListener listener;
    private StorageReference reference2;

    public SportCompanyAdapter(ArrayList<SportCompany> userList, Context context) {
        this.userList = userList;
        this.context = context;
    }

    @NonNull
    @Override
    public SportCompanyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.company, parent, false);
        return new SportCompanyHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull SportCompanyHolder holder, int position) {
        SportCompany sportCompany = userList.get(position);
        holder.setData(sportCompany);
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    class SportCompanyHolder extends RecyclerView.ViewHolder {
        TextView company, ceo;
        ImageView image;

        public SportCompanyHolder(@NonNull View itemView) {
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

        public void setData(SportCompany sportCompany) {
            this.company.setText(sportCompany.getCompany());
            this.ceo.setText(sportCompany.getCeo());

            reference2 = FirebaseStorage.getInstance().getReference(sportCompany.getUid() + ".jpg");

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
        void onItemClick(SportCompany sportCompany, int position);
    }

    public void setOnItemClickListener(SportCompanyAdapter.OnItemClickListener listener) {
        this.listener = listener;
    }
}
