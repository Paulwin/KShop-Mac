package com.example.paulwinjeba.kshop;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class MyPostActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{

    FirebaseAuth mAuth;
    private DatabaseReference databaseReference;

    private RecyclerView MyPostList;

    Button login,signin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_post);

        databaseReference = FirebaseDatabase.getInstance().getReference().child("Post");
        mAuth = FirebaseAuth.getInstance();

        MyPostList = (RecyclerView) findViewById(R.id.my_post);
        MyPostList.setHasFixedSize(true);
        MyPostList.setLayoutManager(new LinearLayoutManager(this));

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

    }


    @Override
    protected void onStart(){
        super.onStart();
        FirebaseRecyclerAdapter<MyBlog, MyPostActivity.MyBlogViewHolder> my_firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<MyBlog, MyPostActivity.MyBlogViewHolder>(
                MyBlog.class,
                R.layout.my_post,
                MyPostActivity.MyBlogViewHolder.class,
                databaseReference
        ) {
            @Override
            protected void populateViewHolder(MyPostActivity.MyBlogViewHolder viewHolder, MyBlog model, int position) {

                viewHolder.setTitle(model.getTitle());
                viewHolder.setPrice(model.getPrice());
                viewHolder.setImage(getApplicationContext(),model.getImage());
                viewHolder.setCategory(model.getCategory());
                viewHolder.setDescription_1(model.getDescription_1());
                viewHolder.setDescription_2(model.getDescription_2());
            }
        };

        MyPostList.setAdapter(my_firebaseRecyclerAdapter);

    }

    public static class MyBlogViewHolder extends RecyclerView.ViewHolder{

        View mView;
        Button edit,delete;

        public MyBlogViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
            edit = (Button) findViewById(R.id.edit);
        }

        public void setTitle(String Title){
            TextView post_title = (TextView) mView.findViewById(R.id.post_title);
            post_title.setText(Title);
        }

        public void setPrice(String Price){
            TextView post_price = (TextView) mView.findViewById(R.id.post_price);
            post_price.setText(Price);
        }

        public void setCategory(String Category){
            TextView post_category = (TextView) mView.findViewById(R.id.show_category);
            post_category.setText(Category);
        }

        public void setDescription_1(String Description_1){
            TextView post_desc_1 = (TextView) mView.findViewById(R.id.show_description1);
            post_desc_1.setText(Description_1);
        }

        public void setDescription_2(String Description_2){
            TextView post_desc_2 = (TextView) mView.findViewById(R.id.show_description2);
            post_desc_2.setText(Description_2);
        }

        public void setImage(Context ctx, String Image){
            ImageView post_image = (ImageView) mView.findViewById(R.id.post_image);
            Picasso.with(ctx.getApplicationContext()).load(Image).into(post_image);
            //Glide.with(HomeActivity.this).load(Image).dontAnimate().into(post_image);
            //Picasso.with(ctx.getApplicationContext()).load(Image).into(post_image);
        }
    }



    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            //super.onBackPressed();
            finish();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        /*int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);*/
        return true;
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.electronics) {
            // Handle the electronics action
            final Intent electronic = new Intent(MyPostActivity.this,ElectronicsActivity.class);
            startActivity(electronic);
        } else if (id == R.id.clothes) {
            /*final Intent upload = new Intent(ClothesActivity.this,PostActivity.class);
            startActivity(upload);*/

        } else if (id == R.id.bike) {
           /* final Intent upload = new Intent(BikesActivity.this,PostActivity.class);
            startActivity(upload);*/

        } else if (id == R.id.book) {
           /* final Intent upload = new Intent(BooksActivity.this,PostActivity.class);
            startActivity(upload);*/

        } else if (id == R.id.my_profile) {
            /*final Intent upload = new Intent(MiscellaneoussActivity.this,PostActivity.class);
            startActivity(upload);*/

        } else if (id == R.id.upload) {
            if (mAuth.getCurrentUser() != null) {
                // User is logged in
                final Intent upload = new Intent(MyPostActivity.this,PostActivity.class);
                startActivity(upload);
            }
            else
            {
                try {
                    LayoutInflater inflater = getLayoutInflater();
                    View alertLayout;
                    alertLayout = inflater.inflate(R.layout.activity_inflater, null);
                    AlertDialog.Builder alert = new AlertDialog.Builder(MyPostActivity.this);
                    //Set the button id
                    login = (Button) alertLayout.findViewById(R.id.log_in);
                    signin = (Button) alertLayout.findViewById(R.id.sign_in);
                    alert.setTitle("Login or Signin");
                    // this is set the view from XML inside AlertDialog
                    alert.setView(alertLayout);
                    // disallow cancel of AlertDialog on click of back button and outside touch
                    alert.setCancelable(false);

                    login.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent nxtlogin = new Intent(MyPostActivity.this, PostLoginActivity.class);
                            Log.d("login", "testing");
                            startActivity(nxtlogin);
                        }
                    });

                    signin.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Log.d("sign in checking", "tested");
                            final Intent nxtsignin = new Intent(MyPostActivity.this, PostSigninActivity.class);
                            startActivity(nxtsignin);
                        }
                    });
                    alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(getBaseContext(), "Cancelled", Toast.LENGTH_SHORT).show();
                            /*final Intent cancel = new Intent(HomeActivity.this, HomeActivity.class);
                            startActivity(cancel);*/

                        }
                    });
                    AlertDialog dialog = alert.create();
                    dialog.show();
                }
                catch (Exception e)
                {
                    Toast.makeText(getBaseContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
                }

            }
        }
        else if (id == R.id.logout) {

            //End user session
            FirebaseAuth.getInstance().signOut();
            Intent homeagain = new Intent(MyPostActivity.this, FirstpageActivity.class);
            homeagain.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            Toast.makeText(MyPostActivity.this,"Logged Out Successfully",Toast.LENGTH_LONG).show();
            startActivity(homeagain);

        }else if (id == R.id.sett) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
