package smadhu.example.com.luklatv;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubePlayerFragment;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout mDrawerLayout;
    public static int a = 0;
    Toolbar toolbar;
    ImageView kantipur, abc, image;
    SwipeRefreshLayout swip;
    ManageClass mc;
    YouTubePlayerFragment fragment;
    RecyclerView recycle;
    ArrayList<MyClass> arr = new ArrayList<>();
    MyAdapter adp;
    Boolean t = true;
    ImageView luklaIm;
    static String luklaLiveVideoUrl = "";
    private static final int REQUEST_READ_PHONE_STATE = 110, REQUEST_ACCESS_FINE_LOCATION = 111, REQUEST_WRITE_STORAGE = 112;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        boolean hasPermissionPhoneState = (ContextCompat.checkSelfPermission(getApplicationContext(),
                Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED);
        if (!hasPermissionPhoneState) {
            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{Manifest.permission.READ_PHONE_STATE},
                    REQUEST_READ_PHONE_STATE);
        }

        boolean hasPermissionLocation = (ContextCompat.checkSelfPermission(getApplicationContext(),
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED);
        if (!hasPermissionLocation) {
            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_ACCESS_FINE_LOCATION);
        }

        boolean hasPermissionWrite = (ContextCompat.checkSelfPermission(getApplicationContext(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED);
        if (!hasPermissionWrite) {
            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    REQUEST_WRITE_STORAGE);
        }

        swip = (SwipeRefreshLayout) findViewById(R.id.swip);
        swip.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (new NetworkConnection(MainActivity.this).isNetworkConnection()) {
                    LiveStreamingVideoBackground l = new LiveStreamingVideoBackground();
                    l.execute();

                   // if(arr.size() == -1 ) {
                       // if(luklaLiveVideoUrl.equals("")) {

                       // }
                    int size = arr.size();
                        if(size == 0 ) {
                            Background b = new Background();
                            b.execute();
                        }
                    //}
                    swip.setRefreshing(false);
                }else{
                    swip.setRefreshing(false);
                    Toast.makeText(MainActivity.this,"please connect internet!!!",Toast.LENGTH_SHORT).show();
                }
            }
        });
        kantipur = (ImageView) findViewById(R.id.kantipur_tv);
        abc = (ImageView) findViewById(R.id.abc_tv);
        image = (ImageView) findViewById(R.id.image_tv);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("            Lukla Tv");
        //toolbar.setLogo(R.drawable.finall);
        setSupportActionBar(toolbar);
        recycle = (RecyclerView) findViewById(R.id.recy1);
        setupNavigationDrawerMenu();
//aa
       if (new NetworkConnection(this).isNetworkConnection()) {
            LiveStreamingVideoBackground l = new LiveStreamingVideoBackground();
            l.execute();
            Background b = new Background();
            b.execute();
        }
        recycle.setLayoutManager(new LinearLayoutManager(MainActivity.this, LinearLayout.HORIZONTAL, false));
        fragment = (YouTubePlayerFragment) getFragmentManager().findFragmentById(R.id.youtube);



        kantipur.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                LiveTvUrls l = new LiveTvUrls();
                String uul = l.getKantipur();
                if (new NetworkConnection(MainActivity.this).isNetworkConnection()) {
                    if (t) {
                        t = false;
                        mc = new ManageClass(MainActivity.this, fragment, uul);
                    } else {
                       // if(mc.isPlayingVideo() == true) {
                            adp.liveYoutubePlayer(uul);
                       // }
                    }
                }else {
                    Toast.makeText(MainActivity.this,"Internet connecction failed!!!",Toast.LENGTH_SHORT).show();
                }
            }
        });
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LiveTvUrls l = new LiveTvUrls();
                String uul = l.getImage();
                if (new NetworkConnection(MainActivity.this).isNetworkConnection()) {
                    if (t) {
                        t = false;
                        mc = new ManageClass(MainActivity.this, fragment, uul);
                    } else {
                       // if(mc.isPlayingVideo() == true) {
                            adp.liveYoutubePlayer(uul);
                       // }
                    }
                }else {
                    Toast.makeText(MainActivity.this,"Internet connecction failed!!!",Toast.LENGTH_SHORT).show();
                }
                //  adp.liveYoutubePlayer(new LiveTvUrls().getImage());
            }
        });
        abc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //adp.liveYoutubePlayer(new LiveTvUrls().getAbc());
                LiveTvUrls l = new LiveTvUrls();
                String uul = l.getAbc();
               /* if (t) {
                    t = false;
                    mc = new ManageClass(MainActivity.this, fragment, uul);
                } else {
                    adp.liveYoutubePlayer(uul);
                }*/
                if (new NetworkConnection(MainActivity.this).isNetworkConnection()) {
                    if (t) {
                        t = false;
                        mc = new ManageClass(MainActivity.this, fragment, uul);
                    } else {
                       // if(mc.isPlayingVideo() == true) {
                            adp.liveYoutubePlayer(uul);
                        //}
                    }
                }else {
                    Toast.makeText(MainActivity.this,"Internet connecction failed!!!",Toast.LENGTH_SHORT).show();
                }
            }
        });

        luklaIm = (ImageView) findViewById(R.id.luklaIm);
        luklaIm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, WebLukla.class);
                i.putExtra("LuklaTv", "http://news.luklatv.com.au/category/general-news/");
                startActivity(i);
            }
        });

    }

    private class LiveStreamingVideoBackground  extends AsyncTask<Object, Object, String> {
        //String url = "https://www.googleapis.com/youtube/v3/search?part=snippet&channelId=UCYp-1O_nOWuJZxePg6YEqIw&eventType=live&type=video&key=AIzaSyCl6oVQxU3sGNHcjvFz6UOVnGMDikyhxmk";
        String url;
        @Override
        protected void onPreExecute() {
            url = new LiveTvUrls().getLuklaLive();
        }
        @Override
        protected String doInBackground(Object... params) {
            YoutubeHttpConnection youtubeHttpConnection = new YoutubeHttpConnection();
            String jsonString = youtubeHttpConnection.getService(url);
            try {
                JSONObject jsonObject = new JSONObject(jsonString);
                JSONArray jsonArray = jsonObject.getJSONArray("items");
                for(int i=0;i<jsonArray.length();i++){
                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                    JSONObject snippet = jsonObject1.getJSONObject("id");
                    String videoId = snippet.getString("videoId");
                    luklaLiveVideoUrl = videoId;
                }
            } catch (Exception e) {
                Toast.makeText(MainActivity.this,"error",Toast.LENGTH_SHORT).show();

            }
            return luklaLiveVideoUrl.toString();
        }

        @Override
        protected void onPostExecute(String s) {
            if (t && !luklaLiveVideoUrl.equals("") ) {
                t = false;
                mc = new ManageClass(MainActivity.this, fragment, luklaLiveVideoUrl);
            }else {
                if(luklaLiveVideoUrl.equals("")){
                    Toast.makeText(getApplicationContext(),"now! live is not available",Toast.LENGTH_LONG).show();
                }else {
                    adp.liveYoutubePlayer(luklaLiveVideoUrl);
                }
            }
        }
    }

    private class Background extends AsyncTask<Object, Object, String> {
        String result = "";

        @Override
        protected String doInBackground(Object... voids) {

            String url = "https://www.googleapis.com/youtube/v3/playlistItems?part=snippet&playlistId=PLrNQucDMRhKBYs4S9n-sfqvRERBUQwYQx&maxResults=50&key=AIzaSyCl6oVQxU3sGNHcjvFz6UOVnGMDikyhxmk";

            YoutubeHttpConnection youtubeHttpConnection = new YoutubeHttpConnection();
            String jsonString = youtubeHttpConnection.getService(url);

            try {
                JSONObject jsonObject = new JSONObject(jsonString);
                JSONArray jsonArray = jsonObject.getJSONArray("items");
                //jsonString = String.valueOf(jsonArray);
                //for (int i = 0; i < jsonArray.length(); i++) {
                for(int i=jsonArray.length()-1;i>=0;i--){
                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                    JSONObject snippet = jsonObject1.getJSONObject("snippet");

                    //getting title
                    String title = snippet.getString("title");

                    //getting default thumbnail
                    JSONObject thumbnails = snippet.getJSONObject("thumbnails");
                    JSONObject defaultImg = thumbnails.getJSONObject("default");
                    String imgurl = defaultImg.getString("url");

                    //getting video id
                    JSONObject resourceId = snippet.getJSONObject("resourceId");
                    String videoId = resourceId.getString("videoId");

                    arr.add(new MyClass(videoId, title));

                    result = result + title + "\n" + imgurl + "\n" + videoId + "\n\n";
                }

            } catch (Exception e) {
                result = e.getMessage();
            }
            //}
            return result.toString();
        }
        @Override
        protected void onPostExecute(String aVoid) {
            if (t && luklaLiveVideoUrl.equals("")) {
                if(!result.equals("") || !result.equals(null)) {
                    t = false;
                    mc = new ManageClass(MainActivity.this, fragment, arr.get(0).link);
                }else {
                    Toast.makeText(getApplicationContext(),result,Toast.LENGTH_LONG).show();
                }
            }
            if(result.equals(null) || result.equals("")){
                Toast.makeText(getApplicationContext(),result,Toast.LENGTH_LONG).show();
            }else {
                adp = new MyAdapter(fragment, MainActivity.this, arr, mc);
                recycle.setAdapter(adp);
            }
        }

    }

    private void setupNavigationDrawerMenu() {

        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation_view);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(this,
                mDrawerLayout,
                toolbar,
                R.string.drawer_open,
                R.string.drawer_close);
        mDrawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        NavigationView nv = (NavigationView) findViewById(R.id.navigation_view);
        Menu m = nv.getMenu();
        item.setCheckable(true);
        switch (item.getItemId()) {
            case R.id.lukla_ns:
                boolean b = !m.findItem(R.id.news1).isVisible();
                m.findItem(R.id.news).setVisible(b);
                m.findItem(R.id.news1).setVisible(b);
                m.findItem(R.id.news3).setVisible(b);
                break;

            case R.id.news:
                Intent i = new Intent(MainActivity.this, WebLukla.class);
                i.putExtra("LuklaTv", "http://news.luklatv.com.au/category/general-news/");
                startActivity(i);
                break;
            case R.id.news1:
                Intent ii = new Intent(MainActivity.this, WebLukla.class);
                ii.putExtra("LuklaTv", "http://news.luklatv.com.au/category/general-news/");
                startActivity(ii);
                break;

            case R.id.news3:
                Intent iii = new Intent(MainActivity.this, WebLukla.class);
                iii.putExtra("LuklaTv", "http://news.luklatv.com.au/category/world-news/");
                startActivity(iii);
                break;
            case R.id.contact_us:
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(MainActivity.this);
                View mView = getLayoutInflater().inflate(R.layout.contact_us, null);
                Button mLogin = (Button) mView.findViewById(R.id.btncall);
                mBuilder.setView(mView);
                final AlertDialog dialog = mBuilder.create();
                dialog.show();
                mLogin.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String s = "+610298631500";
                        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", s, null));
                        startActivity(intent);

                    }
                });
                break;

            case R.id.item_location:
                double latitude = -33.783987;
                double longitude = 150.9511447;
                String uriBegin = "geo:" + latitude + "," + longitude;
                String query = latitude + "," + longitude;
                String encodedQuery = Uri.encode(query);
                String uriString = uriBegin + "?q=" + encodedQuery + "&z=16";
                Uri uri = Uri.parse(uriString);
                Intent intent3 = new Intent(android.content.Intent.ACTION_VIEW, uri);
                startActivity(intent3);
                break;

            case R.id.item_share:
                Intent share = new Intent(Intent.ACTION_SEND);
                share.setType("text/plain");
                share.putExtra(Intent.EXTRA_TEXT, "my new app");
                String appLink = "http://play.google.com/store/hsfohfods";
                share.putExtra(Intent.EXTRA_TEXT, "Try my new app:" + appLink);
                startActivity(Intent.createChooser(share, "Share Via"));
                break;

            case R.id.item_facebook:
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("https://www.facebook.com/LuklaTV/"));
                startActivity(intent);
                break;

            case R.id.about_uss:
                Intent intent1 = new Intent(MainActivity.this, About_us.class);
                startActivity(intent1);
                break;

        }

        return true;
    }

    private void closeDrawer() {
        mDrawerLayout.closeDrawer(GravityCompat.START);
    }

    private void showDrawer() {
        mDrawerLayout.openDrawer(GravityCompat.START);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_items, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.about_us) {
            AlertDialog.Builder mBuilder = new AlertDialog.Builder(MainActivity.this);
            View mView = getLayoutInflater().inflate(R.layout.about_us, null);
            TextView textt = (TextView) mView.findViewById(R.id.txxx);
            mBuilder.setView(mView);
            final AlertDialog dialog = mBuilder.create();
            dialog.show();
            textt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse("http://news.luklatv.com.au/"));
                    startActivity(intent);
                }
            });
            return true;


        } else if (id == R.id.exit) {
            AlertDialog.Builder alertbox = new AlertDialog.Builder(MainActivity.this);
            alertbox.setTitle("Are you suru want to close the app??");
            alertbox.setCancelable(false);
            alertbox.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            });
            alertbox.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            alertbox.show();
        }
        return true;
    }

    @Override
    public void onBackPressed() {

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START))
            drawer.closeDrawer(GravityCompat.START);
        else {
            a++;
            if (a >= 2)
                super.onBackPressed();
            else
                Toast.makeText(MainActivity.this, " Press Back Again to Exit.", Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_READ_PHONE_STATE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(MainActivity.this, "Permission granted.", Toast.LENGTH_SHORT).show();
                    //reload my activity with permission granted or use the features what required the permission
                    finish();
                    startActivity(getIntent());
                } else {
                    //Toast.makeText(MainActivity.this, "The app was not allowed to get your phone state. Hence, it cannot function properly. Please consider granting it this permission", Toast.LENGTH_LONG).show();
                }
            }
            case REQUEST_ACCESS_FINE_LOCATION: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(MainActivity.this, "Permission granted.", Toast.LENGTH_SHORT).show();
                    //reload my activity with permission granted or use the features what required the permission
                    finish();
                    startActivity(getIntent());
                } else {
                    //Toast.makeText(MainActivity.this, "The app was not allowed to get your location. Hence, it cannot function properly. Please consider granting it this permission", Toast.LENGTH_LONG).show();
                }
            }

            case REQUEST_WRITE_STORAGE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(MainActivity.this, "Permission granted.", Toast.LENGTH_SHORT).show();
                    //reload my activity with permission granted or use the features what required the permission
                    finish();
                    startActivity(getIntent());
                } else {
                  //  Toast.makeText(MainActivity.this, "The app was not allowed to write to your storage. Hence, it cannot function properly. Please consider granting it this permission", Toast.LENGTH_LONG).show();
                }
            }
        }

    }

}