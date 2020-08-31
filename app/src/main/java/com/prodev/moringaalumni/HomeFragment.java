package com.prodev.moringaalumni;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.prodev.moringaalumni.adapters.AdapterPosts;
import com.prodev.moringaalumni.models.ModelPost;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    // firebase auth

    FirebaseAuth firebaseAuth;

    RecyclerView recyclerView;
    List<ModelPost> postList;
    AdapterPosts adapterPosts;



    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
      View view =  inflater.inflate(R.layout.fragment_home, container, false);

        //init
        firebaseAuth = FirebaseAuth.getInstance();

        // recycler view and its properties
        recyclerView = view.findViewById(R.id.postsRecyclerview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        //show newest post
        layoutManager.setStackFromEnd(true);
        layoutManager.setReverseLayout(true);
      return view;
    }
    private void checkUserStatus(){
//        get current user
        FirebaseUser user = firebaseAuth.getCurrentUser();
        if (user !=null){
            // user is signed in stay here
            // set email of logged in user
            //mProfileTv.setText(user.getEmail());

        }
        else {
            //user not signed in, go to main activity
            startActivity(new Intent(getActivity(), MainActivity.class));
            getActivity().finish();
        }
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState){
        setHasOptionsMenu(true); //to show menu options
        super.onCreate(savedInstanceState);
    }
    @Override
    public void onCreateOptionsMenu (Menu menu, MenuInflater inflater){
        inflater.inflate(R.menu.menu_main, menu);
        super.onCreateOptionsMenu(menu, inflater);

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();
        if(id == R.id.action_logout){
            firebaseAuth.signOut();
            checkUserStatus();
        }
        if(id == R.id.action_add_post){
            startActivity(new Intent(getActivity(), AddPostActivity.class));
        }

        return super.onOptionsItemSelected(item);
    }
}