package smadhu.example.com.luklatv;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.youtube.player.YouTubePlayerFragment;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by suman on 8/3/2017.
 */

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyHolder> {
    Activity context;
    //String link;
    ArrayList<MyClass> list;
    MyClass myclass;

    String img_url;
    String url;

    Fragment fragment;
    //static final String developerKey = "AIzaSyAvPdip-zsISAm2W24vQ3maLBB0RcEzWzg";//"AIzaSyCe6tORd9Ch4lx-9Ku5SQ476uS9OtZYsWA";
    ManageClass m;

    public MyAdapter(Fragment frag, Activity context, ArrayList<MyClass> list, ManageClass mc) {
        this.context= context;
        this.list= list;
        fragment= frag;
        m = mc;
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v;
        v= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_view,parent, false);
        MyHolder m= new MyHolder(v);
        return m;

    }

    @Override
    public void onBindViewHolder(MyHolder holder, final int position) {
        myclass= list.get(position);
        img_url = "http://img.youtube.com/vi/" + myclass.link + "/0.jpg"; // this is link which will give u thumnail image of that video

        Picasso.with(context)
                .load(img_url)
               // .placeholder(R.drawable.images)
                .into(holder.imageView);
        holder.textView.setText(myclass.title);

        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                myclass =list.get(position);
                if(m==null) {
                    m = new ManageClass(context, fragment, myclass.link);
                }else {
                    m.setlink(myclass.link);
                    m.play();
                }

            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void playLiveVideo() {
        if(m==null) {
            m = new ManageClass(context, fragment, url);
        }else {
            m.setlink(url);
            m.play();
        }
    }
    public void liveYoutubePlayer(String urll){
        url = urll;
        playLiveVideo();
    }

    class MyHolder extends RecyclerView.ViewHolder
    {

        ImageView imageView;
        TextView textView;
        public MyHolder(View itemView) {
            super(itemView);
            imageView= (ImageView) itemView.findViewById(R.id.img);
            textView = (TextView) itemView.findViewById(R.id.textView);

        }
    }
}
