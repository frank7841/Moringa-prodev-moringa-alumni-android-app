package com.prodev.moringaalumni;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.net.URI;

import butterknife.BindView;

public class PostActivity extends AppCompatActivity {
    private StorageReference storageReference;
    private Uri uri=null;
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;
    private FirebaseAuth mAuth;
    private FirebaseUser mCurrentUser;
    private DatabaseReference mDatabaseuser;
    @BindView(R.id.imageBtn) ImageButton imageButton;
    @BindView(R.id.postBtn) Button mPostbtn;
    @BindView(R.id.textTitle) EditText mTextTitle;
    @BindView(R.id.textDesc) EditText mTextDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        storageReference = FirebaseStorage.getInstance().getReference();
        databaseReference = database.getInstance().getReference().child("MoringaPosts");
        mAuth= FirebaseAuth.getInstance();
        mCurrentUser = mAuth.getCurrentUser();
        mDatabaseuser = FirebaseDatabase.getInstance().getReference().child("users").child(mCurrentUser.getUid());
    }
}