package com.mrdolphin.prooudtuner;

import android.content.Context;
import android.media.MediaPlayer;

import java.io.IOException;

public class Audio {

    MediaPlayer mp;
    Context context;

    public Audio(Context ct) {
        this.context = ct;
    }

    public void playClick() {
        mp = MediaPlayer.create(context, R.raw.tr);
        try {
            mp.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
        mp.start();
    }
}