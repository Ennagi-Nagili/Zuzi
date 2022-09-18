package com.dani.zuzi;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.ImageDecoder;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.dani.zuzi.R;

import java.io.IOException;

public class Home extends AppCompatActivity {
    private DrawerLayout drawer;
    private NavigationView navigation;
    private BottomNavigationView bottom;
    private ProfileFragment profileFragment;
    private MainFragment mainFragment;
    private SearchFragment searchFragment;
    private FirebaseFirestore firestore;
    private DocumentReference reference;
    private TextView nav_name, nav_email;
    private ProgressBar nav;
    private Button add_image;
    private FirebaseAuth auth;
    private FirebaseUser user;
    private int denied = 0, granted = 1;
    private Bitmap selectedImage;
    private android.net.Uri imageUri;
    private String Uri;
    private ImageView profile_image;
    private Button logout;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private Button info;

    private StorageReference reference1, reference2, reference3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        drawer = findViewById(R.id.drawer);
        navigation = findViewById(R.id.navigation);
        bottom = findViewById(R.id.bottom_view);
        profileFragment = new ProfileFragment();
        mainFragment = new MainFragment();
        searchFragment = new SearchFragment();
        firestore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        reference = firestore.collection("Users").document(user.getUid());
        nav_name = navigation.getHeaderView(0).findViewById(R.id.nav_name);
        nav_email = navigation.getHeaderView(0).findViewById(R.id.nav_email);
        nav = navigation.getHeaderView(0).findViewById(R.id.fame);
        add_image = navigation.getHeaderView(0).findViewById(R.id.add_image);
        profile_image = navigation.getHeaderView(0).findViewById(R.id.profile_image);
        reference1 = FirebaseStorage.getInstance().getReference();
        reference2 = reference1.child(user.getUid() + ".jpg");
        reference3 = FirebaseStorage.getInstance().getReference(user.getUid() + ".jpg");
        logout = navigation.getHeaderView(0).findViewById(R.id.logout);
        preferences = getSharedPreferences("com.dani.Zuzi", MODE_PRIVATE);
        editor = preferences.edit();
        info = navigation.getHeaderView(0).findViewById(R.id.info);

        info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reference.get()
                        .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                            @Override
                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                if (documentSnapshot.exists()) {
                                    if (documentSnapshot.getData().get("type").equals("company")) {
                                        Intent intent = new Intent(Home.this, Info.class);
                                        startActivity(intent);
                                    }

                                    else if (documentSnapshot.getData().get("type").equals("person")) {
                                        Intent intent = new Intent(Home.this, People_Info.class);
                                        startActivity(intent);
                                    }
                                }
                            }
                        });
            }
        });

        nav.setProgress(75);

        setFragment(profileFragment);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alert = new AlertDialog.Builder(Home.this, R.style.Theme_Zuzi);
                alert.setTitle("Zuzi");
                alert.setTitle("Çıxmaq istədiyinizdən əminsiniz?");

                alert.setPositiveButton("Bəli", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        auth.signOut();
                        editor.putString("email", null);
                        editor.putString("password", null);
                        editor.apply();
                        Intent intent = new Intent(Home.this, Login.class);
                        finish();
                        startActivity(intent);
                    }
                });

                alert.setNegativeButton("Xeyr", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });

                alert.show();
            }
        });

        bottom.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.bottom_home:
                        setFragment(mainFragment);
                        return true;

                    case R.id.bottom_profile:
                        setFragment(profileFragment);
                        return true;

                    case R.id.bottom_users:
                        setFragment(searchFragment);
                        return true;

                    default:
                        return false;
                }
            }
        });

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                drawer.open();
            }
        }, 3000);

        reference.get()
                .addOnSuccessListener(this, new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()) {
                            nav_name.setText("İstifadəçi adı: " +documentSnapshot.getData().get("name").toString());
                            nav_email.setText("Mail: " +documentSnapshot.getData().get("email").toString());
                        }
                    }
                });

        add_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                select();
            }
        });

        setter();
    }

    public void select() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, denied);
        }

        else {
            Intent select = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(select, granted);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == denied) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent select = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(select, granted);
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == granted) {
            if (resultCode == RESULT_OK && data != null) {
                imageUri = data.getData();

                try {
                    if (Build.VERSION.SDK_INT >= 28) {
                        ImageDecoder.Source imageSource = ImageDecoder.createSource(this.getContentResolver(), imageUri);
                        selectedImage = ImageDecoder.decodeBitmap(imageSource);
                        profile_image.setImageBitmap(selectedImage);
                        save();
                    }

                    else {
                        selectedImage = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
                        profile_image.setImageBitmap(selectedImage);
                        save();
                    }
                }

                catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    public void save() {
        reference2.putFile(imageUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Toast.makeText(Home.this, "Saved", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(Home.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame, fragment);
        transaction.commit();
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
    }

    public void setter() {
        reference3.getDownloadUrl()
                .addOnSuccessListener(new OnSuccessListener<android.net.Uri>() {
                    @Override
                    public void onSuccess(android.net.Uri uri) {
                        Glide.with(Home.this)
                                .load(uri)
                                .into(profile_image);
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                //Toast.makeText(Home.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}