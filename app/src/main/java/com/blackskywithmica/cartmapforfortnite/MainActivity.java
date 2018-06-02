package com.blackskywithmica.cartmapforfortnite;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Switch;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;


import uk.co.senab.photoview.PhotoViewAttacher;


public class MainActivity extends AppCompatActivity {
    public DrawerLayout mDrawerLayout;
    public NavigationView mNavigationView;
    public ImageView imageView;
    public Switch simpleSwitch;
    public InterstitialAd mInterstitialAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        loadAD();
        navigationView();
        chestSwitch();
        firstRunDialog();

        imageView = (ImageView) findViewById(R.id.imageView);
        PhotoViewAttacher photoView = new PhotoViewAttacher(imageView);
        photoView.update();


    }


    public void chestSwitch() {

        simpleSwitch = (Switch) findViewById(R.id.simpleSwitch);
        simpleSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (simpleSwitch.isChecked()) {
                    imageView.setImageResource(R.drawable.mapwithcarts);
                    PhotoViewAttacher photoView = new PhotoViewAttacher(imageView);
                    photoView.update();
                    if (mInterstitialAd.isLoaded()) {
                        mInterstitialAd.show();

                        AdRequest adRequest = new AdRequest.Builder().build();
                        mInterstitialAd = new InterstitialAd(getApplicationContext());
                        mInterstitialAd.setAdUnitId(getText(R.string.interstitial_ad_unit_id) + "");
                        mInterstitialAd.loadAd(adRequest);
                    }

                } else {
                    imageView.setImageResource(R.drawable.mapwithoutcarts);
                    PhotoViewAttacher photoView = new PhotoViewAttacher(imageView);
                    photoView.update();

                    if (mInterstitialAd.isLoaded()) {
                        mInterstitialAd.show();

                        AdRequest adRequest = new AdRequest.Builder().build();
                        mInterstitialAd = new InterstitialAd(getApplicationContext());
                        mInterstitialAd.setAdUnitId(getText(R.string.interstitial_ad_unit_id) + "");
                        mInterstitialAd.loadAd(adRequest);
                    }

                }

            }
        });

    }


    public void navigationView() {

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        mNavigationView = (NavigationView) findViewById(R.id.shitstuff);


        mNavigationView.setItemIconTintList(null);

        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                mDrawerLayout.closeDrawers();

                String url;
                url = "https://play.google.com/store/apps/developer?id=PentaButtons";

                Boolean link;
                link = true;

                switch (menuItem.getItemId()) {


                    case R.id.rate:
                        url = "https://redirection.lima-city.de/links/instaicon.html";
                        break;
                    case R.id.share:
                        link = false;
                        Intent shareIntent = new Intent(Intent.ACTION_SEND);
                        shareIntent.setType("text/plain");
                        shareIntent.putExtra(Intent.EXTRA_SUBJECT, "✶"+ R.string.app_name +"✶");
                        shareIntent.putExtra(Intent.EXTRA_TEXT, "✶Go and check out the" + " \"" +  getText(R.string.app_name) + "\" " + "✶\n\n " + getText(R.string.link_zur_app));
                        startActivity(Intent.createChooser(shareIntent,  "Share via"));
                        break;




                }
                if (link) {
                    Intent intent = new Intent();
                    intent.setAction(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(url));
                    onPause();
                    startActivity(intent);
                }


                return false;
            }

        });


        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
        ActionBarDrawerToggle mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.app_name,
                R.string.app_name);

        mDrawerLayout.setDrawerListener(mDrawerToggle);

        mDrawerToggle.syncState();

    }


    public void loadAD() {

        AdView mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId(getText(R.string.interstitial_ad_unit_id) + "");
        mInterstitialAd.loadAd(adRequest);
        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
                //Ads loaded
            }

            @Override
            public void onAdClosed() {
                super.onAdClosed();
                //Ads closed
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                super.onAdFailedToLoad(errorCode);
                //Ads couldn't loaded
            }
        });
        mInterstitialAd.loadAd(adRequest);


    }


    public void firstRunDialog() {
        SharedPreferences prefs = getSharedPreferences("werte", 0);
        boolean b = prefs.getBoolean("firstrun", true);

        if (b) {
            AlertDialog.Builder a_builder = new AlertDialog.Builder(this);
            a_builder.setMessage(R.string.firstrun)
                    .setCancelable(true)
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });

            AlertDialog alert = a_builder.create();
            alert.show();
        }


        SharedPreferences sh = getSharedPreferences("werte", 0);
        SharedPreferences.Editor editor = sh.edit();
        editor.putBoolean("firstrun", false);
        editor.commit();
    }

}
