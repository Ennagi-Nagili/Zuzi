package com.dani.zuzi;

import android.content.Context;
import android.graphics.ImageDecoder;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
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
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.IOException;
import java.util.ArrayList;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CommentHolder> {
    private ArrayList<Comment> productList;
    private Context context;
    private StorageReference reference2;

    public CommentAdapter(ArrayList<Comment> productList, Context context) {
        this.productList = productList;
        this.context = context;
    }

    @NonNull
    @Override
    public CommentHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.comment, parent, false);
        return new CommentHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentHolder holder, int position) {
        Comment comment = productList.get(position);
        holder.setData(comment);
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    class CommentHolder extends RecyclerView.ViewHolder {
        TextView name;
        TextView comment;
        ImageView image;

        public CommentHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            comment = itemView.findViewById(R.id.comment_text);
            image = itemView.findViewById(R.id.comment_image);
        }

        public void setData(@NonNull Comment comment) {
            this.name.setText(comment.getName());
            this.comment.setText(comment.getComment());

            reference2 = FirebaseStorage.getInstance().getReference(comment.getUid() + ".jpg");

            System.out.println(comment.getUid());

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
}