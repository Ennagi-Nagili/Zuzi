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

public class TechnologyCompanyAdapter extends RecyclerView.Adapter<TechnologyCompanyAdapter.TechnologyCompanyHolder> {
    private ArrayList<TechnologyCompany> userList;
    private Context context;
    private OnItemClickListener listener;
    private StorageReference reference2;

    public TechnologyCompanyAdapter(ArrayList<TechnologyCompany> userList, Context context) {
        this.userList = userList;
        this.context = context;
    }

    @NonNull
    @Override
    public TechnologyCompanyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.company, parent, false);
        return new TechnologyCompanyHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull TechnologyCompanyHolder holder, int position) {
        TechnologyCompany technologyCompany = userList.get(position);
        holder.setData(technologyCompany);
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    class TechnologyCompanyHolder extends RecyclerView.ViewHolder {
        TextView company, ceo;
        ImageView image;

        public TechnologyCompanyHolder(@NonNull View itemView) {
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

        public void setData(TechnologyCompany technologyCompany) {
            this.company.setText(technologyCompany.getCompany());
            this.ceo.setText(technologyCompany.getCeo());

            reference2 = FirebaseStorage.getInstance().getReference(technologyCompany.getUid() + ".jpg");

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
        void onItemClick(TechnologyCompany technologyCompany, int position);
    }

    public void setOnItemClickListener(TechnologyCompanyAdapter.OnItemClickListener listener) {
        this.listener = listener;
    }
}
