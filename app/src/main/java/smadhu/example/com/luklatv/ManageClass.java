package smadhu.example.com.luklatv;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
/**
 * Created by suman on 8/3/2017.
 */

public class ManageClass extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener {
    Fragment fragment;
    String VideoId;
    static final String developerKey="AIzaSyCl6oVQxU3sGNHcjvFz6UOVnGMDikyhxmk";
    MainActivity mainActivity;
    Activity activity;
    boolean p = false;
    boolean isPlaying = false;


    public ManageClass(Activity activity,Fragment fragment, String VideoId) {
        this.fragment= fragment;
        this.activity= activity;
        mainActivity= (MainActivity) activity;
        this.VideoId= VideoId;
        p = true;
        mainActivity.fragment.initialize(developerKey,this);

    }
    YouTubePlayer.Provider pro;
    YouTubePlayer youTubeP;
    boolean bb;
    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {

        if(!b)
        {
            pro = provider;
            youTubeP = youTubePlayer;
            bb = b;   //false
            youTubePlayer.loadVideo(VideoId);

        }
        youTubePlayer.play();
        isPlaying = youTubePlayer.isPlaying();    //false
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
        if(youTubeInitializationResult.isUserRecoverableError()){
            String s = String.valueOf(youTubeInitializationResult.getErrorDialog(this,1));
            Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();
        }else {
            Toast.makeText(getApplicationContext(),"Error Initialization YoutubePlayer",Toast.LENGTH_LONG).show();
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == 1){
            getYouTubePlayerProvider().initialize(developerKey,this);
        }
    }

    private YouTubePlayer.Provider getYouTubePlayerProvider() {
        return (YouTubePlayerView)findViewById(R.id.youtube);
    }

    public void setlink(String myclass){
        VideoId = myclass;
    }

    public void play() {
       onInitializationSuccess(pro,youTubeP,false);

    }

    public boolean isPlayingVideo(){
        return isPlaying;
    }
}
