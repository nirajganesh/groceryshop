package com.example.groceryshop;

import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.groceryshop.Adapter.CategorieslistAdapter;
import com.example.groceryshop.Adapter.DrawerAdapter;
import com.example.groceryshop.Adapter.TopCategoriesAdapter;
import com.example.groceryshop.Modal.DrawerItems;
import com.example.groceryshop.Modal.Categorieslistdata;
import com.example.groceryshop.Modal.SimpleItem;
import com.example.groceryshop.Modal.TopProductdata;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.smarteist.autoimageslider.SliderLayout;
import com.smarteist.autoimageslider.SliderView;
import com.yarolegovich.slidingrootnav.SlidingRootNav;
import com.yarolegovich.slidingrootnav.SlidingRootNavBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;

import es.dmoral.toasty.Toasty;

public class MainActivity extends AppCompatActivity implements DrawerAdapter.OnItemSelectedListener {
    private static final int POS_DASHBOARD = 0;
    private static final int POS_ACCOUNT = 1;
    private static final int POS_MESSAGES = 2;
    private static final int POS_CART = 3;
    private static final int POS_POLICY=4;
    private static final int POS_LOGOUT = 5;
    private String[] screenTitles;
    private Drawable[] screenIcons;
    RecyclerView recyclerView,recyclerView1;
    SliderLayout sliderLayout;
    CategorieslistAdapter adapter;
    TopCategoriesAdapter topCategoriesAdapter;
    Context context=MainActivity.this;
    ArrayList<Categorieslistdata> jsondataArrayList=new ArrayList<>();
    ArrayList<TopProductdata> topProductdata=new ArrayList<>();
    String url1="https://nirajganeshphp.000webhostapp.com/categoriesdata.php";
    String url="https://nirajganeshphp.000webhostapp.com/productdata.php";
    StringRequest request;
    StringRequest request1;
    private SlidingRootNav slidingRootNav;
    BottomNavigationView bottomNavigationView;
    SwipeRefreshLayout swipeRefreshLayout;
    ProgressBar progressBar,progressBar1;
    TextView categoriestext,topproducttext;
    Animation animation;
    ShimmerFrameLayout shimmerFrameLayout,shimmerFrameLayout1;
    CoordinatorLayout coordinatorLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        coordinatorLayout=findViewById(R.id.coordinatorid);
        bottomNavigationView=findViewById(R.id.navigationmenuid);
        swipeRefreshLayout=findViewById(R.id.swipeid1);
        progressBar=findViewById(R.id.progressBarid);
        progressBar1=findViewById(R.id.progressBarid1);
        categoriestext=findViewById(R.id.allcategoriestext);
        topproducttext=findViewById(R.id.topproducttext);
        shimmerFrameLayout = findViewById(R.id.shimmerLayout);
        shimmerFrameLayout.startShimmer();

        shimmerFrameLayout1 = findViewById(R.id.shimmerLayout1);
        shimmerFrameLayout1.startShimmer();
        animation= AnimationUtils.loadAnimation(context,R.anim.right_animation);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if(item.getItemId()==R.id.cartid)
                {
                    Intent intent=new Intent(MainActivity.this,CardActivity.class);
                    startActivity(intent);

                }
                if(item.getItemId()==R.id.profileid)
                {
                    Intent intent=new Intent(MainActivity.this,SignupActivity.class);
                    startActivity(intent);
                }
                if(item.getItemId()==R.id.orderid1)
                {

                    Intent intent=new Intent(MainActivity.this,MyOrderActivity.class);
                    startActivity(intent);
                }

                return true;
            }
        });
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent=new Intent(MainActivity.this,MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        finish();
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }, 2000);
            }
        });

        sliderLayout = findViewById(R.id.imageSlider);
        sliderLayout.setIndicatorAnimation(SliderLayout.Animations.FILL); //set indicator animation by using SliderLayout.Animations. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
        sliderLayout.setScrollTimeInSec(5); //set scroll delay in seconds :
        sliderLayout.setAnimation(animation);

        recyclerView=findViewById(R.id.recyclerviewid);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        recyclerView1=findViewById(R.id.recyclerviewid1);
        StaggeredGridLayoutManager linearLayoutManager1=new StaggeredGridLayoutManager(3,RecyclerView.VERTICAL);
     //   recyclerView1.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView1.setLayoutManager(linearLayoutManager1);
        request=new StringRequest(url1, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response!=null)
                {
                    shimmerFrameLayout.stopShimmer();
                    shimmerFrameLayout.setVisibility(View.GONE);
                  //  progressBar.setVisibility(View.INVISIBLE);
                    categoriestext.setVisibility(View.VISIBLE);
                    try {
                        JSONObject jsonObject=new JSONObject(response);
                        JSONArray jsonArray=jsonObject.getJSONArray("store");
                        for(int i=0;i<jsonArray.length();i++)
                        {
                            JSONObject object=jsonArray.getJSONObject(i);
                            Categorieslistdata jsondata=new Categorieslistdata();
                            jsondata.setAuthor(object.getString("categoriesname"));
                            jsondata.setDownload_url(object.getString("categoriesimage"));
                            jsondata.setId(object.getString("categoriesid"));
                            jsondataArrayList.add(jsondata);
                            adapter=new CategorieslistAdapter(context,jsondataArrayList);
                            recyclerView.setAdapter(adapter);
                      //      progressBar.setVisibility(View.INVISIBLE);

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });



        RequestQueue queue= Volley.newRequestQueue(this);
        queue.add(request);


        request1=new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response!=null)
                {
                    shimmerFrameLayout1.stopShimmer();
                    shimmerFrameLayout1.setVisibility(View.GONE);
                   //   progressBar1.setVisibility(View.INVISIBLE);
                      topproducttext.setVisibility(View.VISIBLE);
                    try {
                        JSONObject jsonObject=new JSONObject(response);
                        JSONArray jsonArray=jsonObject.getJSONArray("store");
                        for(int i=0;i<jsonArray.length();i++)
                        {
                            JSONObject object=jsonArray.getJSONObject(i);
                            TopProductdata productdata=new TopProductdata();
                            productdata.setProductname(object.getString("productname"));
                            productdata.setProductimage(object.getString("productimage"));
                            productdata.setProductprice(object.getString("productprice"));
                            productdata.setProductid(object.getString("productid"));
                            topProductdata.add(productdata);
                        //    Toast.makeText(context,productdata.getProductprice().toString(),Toast.LENGTH_LONG).show();
                            topCategoriesAdapter=new TopCategoriesAdapter(context,topProductdata);
                            recyclerView1.setAdapter(topCategoriesAdapter);
                            //      progressBar.setVisibility(View.INVISIBLE);

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        RequestQueue queue1= Volley.newRequestQueue(this);
        queue1.add(request1);

        setSliderViews();
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.toolbar_bar);

        slidingRootNav = new SlidingRootNavBuilder(this)
                .withToolbarMenuToggle(toolbar)
                .withMenuOpened(false)
                .withMenuLayout(R.layout.menu_left_drawer)
                .inject();

        screenIcons = loadScreenIcons();
        screenTitles = loadScreenTitles();
        DrawerAdapter adapter = new DrawerAdapter(Arrays.asList(
                createItemFor(POS_DASHBOARD).setChecked(true),
                createItemFor(POS_ACCOUNT),
                createItemFor(POS_MESSAGES),
                createItemFor(POS_CART),
                createItemFor(POS_POLICY),
                //      new SpaceItems(48),
                createItemFor(POS_LOGOUT)));
        adapter.setListener(this);

        RecyclerView list = findViewById(R.id.list);
        list.setNestedScrollingEnabled(false);
        list.setLayoutManager(new LinearLayoutManager(this));
        list.setAdapter(adapter);
        adapter.setSelected(POS_DASHBOARD);
    }

    @Override
    public void onItemSelected(int position) {
        if (position == POS_LOGOUT) {
            finish();
        }
        if(position == POS_ACCOUNT)
        {
            Intent intent=new Intent(getApplicationContext(),ProfileActivity.class);
            startActivity(intent);
        }
        if(position == POS_CART)
        {
            Intent intent=new Intent(getApplicationContext(), CardActivity.class);
            startActivity(intent);
        }
        if(position == POS_MESSAGES)
        {
            Intent intent=new Intent(getApplicationContext(), WishlistActivity.class);
            startActivity(intent);
        }

        slidingRootNav.closeMenu();
        Fragment selectedScreen = CenteredTextFragment.createFor(screenTitles[position]);
        showFragment(selectedScreen);
    }
    private void showFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, fragment)
                .commit();
    }

    private DrawerItems createItemFor(int position) {
        return new SimpleItem(screenIcons[position], screenTitles[position])
                .withIconTint(color(R.color.textColorSecondary))
                .withTextTint(color(R.color.textColorPrimary))
                .withSelectedIconTint(color(R.color.colorAccent))
                .withSelectedTextTint(color(R.color.colorAccent));
    }

    private String[] loadScreenTitles() {
        return getResources().getStringArray(R.array.ld_activityScreenTitles);
    }

    private Drawable[] loadScreenIcons() {
        TypedArray ta = getResources().obtainTypedArray(R.array.ld_activityScreenIcons);
        Drawable[] icons = new Drawable[ta.length()];
        for (int i = 0; i < ta.length(); i++) {
            int id = ta.getResourceId(i, 0);
            if (id != 0) {
                icons[i] = ContextCompat.getDrawable(this, id);
            }
        }
        ta.recycle();
        return icons;
    }

    @ColorInt
    private int color(@ColorRes int res) {
        return ContextCompat.getColor(this, res);
    }

  @Override
    public boolean onCreateOptionsMenu(Menu menu) {
       getMenuInflater().inflate(R.menu.toolbar_menu,menu);
       MenuItem item = menu.findItem(R.id.wishlist);
        return true;
   }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.searchid)
        {
            Intent intent=new Intent(MainActivity.this,SearchActivity.class);
            startActivity(intent);
        }
        if(item.getItemId()==R.id.wishlist)
        {
            Intent intent=new Intent(MainActivity.this,WishlistActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    private void setSliderViews() {

        for (int i = 0; i <= 3; i++) {
            SliderView sliderView = new SliderView(this);
            switch (i) {
                case 0:
                    sliderView.setImageUrl("https://firebasestorage.googleapis.com/v0/b/tournament-65d1d.appspot.com/o/ProfileImage%2Fpexels-ponyo-sakana-4194610.jpg?alt=media&token=bde59a2b-487b-41ce-8f7b-5478e94f94af");
                    break;
                case 1:
                    sliderView.setImageUrl("https://firebasestorage.googleapis.com/v0/b/tournament-65d1d.appspot.com/o/ProfileImage%2Fpexels-magda-ehlers-1300972.jpg?alt=media&token=80f00c8e-b010-4cd3-a148-649d5e4aa0f9");
                    break;
                case 2:
                    sliderView.setImageUrl("https://firebasestorage.googleapis.com/v0/b/tournament-65d1d.appspot.com/o/ProfileImage%2Fpexels-abhinav-goswami-291528.jpg?alt=media&token=9a739bda-6a73-40f3-baa2-67a9949a90fa");
                    break;
                case 3:
                    sliderView.setImageUrl("https://firebasestorage.googleapis.com/v0/b/tournament-65d1d.appspot.com/o/ProfileImage%2Fpexels-artem-beliaikin-452737.jpg?alt=media&token=f1c8bc92-07c4-4cd5-870b-b96f640de748");
                    break;
            }

            sliderView.setImageScaleType(ImageView.ScaleType.CENTER_CROP);
        //    sliderView.setDescription("setDescription " + (i + 1));
         //   final int finalI = i;
        //    sliderView.setOnSliderClickListener(new SliderView.OnSliderClickListener() {
       //         @Override
      //          public void onSliderClick(SliderView sliderView) {
         //           Toast.makeText(MainActivity.this, "This is slider " + (finalI + 1), Toast.LENGTH_SHORT).show();
        //        }
       //     });

            //at last add this view in your layout :
            sliderLayout.addSliderView(sliderView);
        }
    }
}