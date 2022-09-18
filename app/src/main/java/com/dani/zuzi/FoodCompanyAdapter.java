package com.dani.zuzi;

import android.content.Context;
import android.content.SharedPreferences;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class FoodCompanyAdapter extends RecyclerView.Adapter<FoodCompanyAdapter.FoodCompanyHolder> {
    private ArrayList<FoodCompany> userList;
    private Context context;
    private OnItemClickListener listener;
    private ArrayList<StorageReference> refList;
    private CollectionReference reference = FirebaseFirestore.getInstance().collection("Users");
    private DocumentReference ref;
    private StorageReference reference2;

    public FoodCompanyAdapter(ArrayList<FoodCompany> userList, Context context, ArrayList<StorageReference> refList) {
        this.userList = userList;
        this.context = context;
        this.refList = refList;
    }

    @NonNull
    @Override
    public FoodCompanyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.company, parent, false);
        return new FoodCompanyHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull FoodCompanyHolder holder, int position) {
        FoodCompany foodCompany = userList.get(position);
        holder.setData(foodCompany);
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    class FoodCompanyHolder extends RecyclerView.ViewHolder {
        TextView company, ceo;
        ImageView image;

        public FoodCompanyHolder(@NonNull View itemView) {
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

        public void setData(FoodCompany foodCompany) {
            this.company.setText(foodCompany.getCompany());
            this.ceo.setText(foodCompany.getCeo());

           reference2 = FirebaseStorage.getInstance().getReference(foodCompany.getUid() + ".jpg");

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
        void onItemClick(FoodCompany foodCompany, int position);
    }

    public void setOnItemClickListener(FoodCompanyAdapter.OnItemClickListener listener) {
        this.listener = listener;
    }
}
