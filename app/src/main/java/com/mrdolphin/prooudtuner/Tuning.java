package com.mrdolphin.prooudtuner;


import android.content.Context;

import java.lang.ref.PhantomReference;

/**
 * Created by andry on 24/04/16.
 */
public class Tuning {
    String name;
    Pitch[] pitches;

    public Tuning(String name, Pitch[] pitches) {
        this.name = name;
        this.pitches = pitches;
    }

    public Pitch closestPitch(float freq) {
        Pitch closest = null;
        float dist = Float.MAX_VALUE;
        for (Pitch pitch : pitches) {
            float d = Math.abs(freq - pitch.frequency);
            if (d < dist) {
                closest = pitch;
                dist = d;
            }
        }
        return closest;
    }

    public int closestPitchIndex(float freq) {
        int index = -1;
        float dist = Float.MAX_VALUE;
        for (int i = 0; i < pitches.length; i++) {
            Pitch pitch = pitches[i];
            float d = Math.abs(freq - pitch.frequency);
            if (d < dist) {
                index = i;
                dist = d;
            }
        }
        return index;
    }


}
