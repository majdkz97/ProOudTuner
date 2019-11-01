package com.mrdolphin.prooudtuner;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Point;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v7.app.AppCompatActivity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TunerActivity extends AppCompatActivity {


    private static final String TAG = TunerActivity.class.getCanonicalName();

    //  public static final String STATE_NEEDLE_POS = "needle_pos";
    public static final String STATE_PITCH_INDEX = "pitch_index";
    public static final String STATE_LAST_FREQ = "last_freq";
    private static final int PERMISSION_REQUEST_RECORD_AUDIO = 443;


  //  private Tuning mTuning;
    private AudioProcessor mAudioProcessor;
    private ExecutorService mExecutor = Executors.newSingleThreadExecutor();
    // private NeedleView mNeedleView;
    private TuningView mTuningView;
    private TextView mFrequencyView;
    private TextView majdFrequencyView;
    private ImageView majdImage;
    private ImageView majdImage2;
    int play = 0;




    private boolean mProcessing = false;

    private int mPitchIndex;
    private float mLastFreq;
    float per;
    int perc;
    int wid;
    float [] fre = new float[40];

   // AudioPlayer audioPlayer=new AudioPlayer();
    @Override
    protected void onStart() {
        super.onStart();
        if(Utils.checkPermission(this, Manifest.permission.RECORD_AUDIO)) {
            startAudioProcessing();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mProcessing) {
            if(mAudioProcessor!=null)
            mAudioProcessor.stop();
            mProcessing = false;
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void requestPermissions() {
        if (!Utils.checkPermission(this, Manifest.permission.RECORD_AUDIO)) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.RECORD_AUDIO)) {

                DialogUtils.showPermissionDialog(this, getString(R.string.permission_record_audio), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ActivityCompat.requestPermissions(TunerActivity.this,
                                new String[]{Manifest.permission.RECORD_AUDIO},
                                PERMISSION_REQUEST_RECORD_AUDIO);
                    }
                });

            } else {

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.RECORD_AUDIO},
                        PERMISSION_REQUEST_RECORD_AUDIO);
            }
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_RECORD_AUDIO: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    startAudioProcessing();
                }
                break;
            }

        }
    }

    private void startAudioProcessing() {
        if (mProcessing)
            return;

        play=0;
        mAudioProcessor = new AudioProcessor();
        mAudioProcessor.init();
        mAudioProcessor.setPitchDetectionListener(new AudioProcessor.PitchDetectionListener() {
            @Override
            public void onPitchDetected(final float freq, double avgIntensity) {

                //    final int index = mTuning.closestPitchIndex(freq);
                //   final Pitch pitch = mTuning.pitches[index];
                //  double interval = 1200 * Utils.log2(freq / pitch.frequency); // interval in cents
                //  final float needlePos = (float) (interval / 100);
                //  final boolean goodPitch = Math.abs(interval) < 5.0;
                runOnUiThread(new Runnable() {
                                  @SuppressLint("DefaultLocale")
                                  @Override
                                  public void run() {

                                      //  mTuningView.setSelectedIndex(index, true);
                                      //    mNeedleView.setTickLabel(0.0F, String.format("%.02fHz", pitch.frequency));
                                      //    mNeedleView.animateTip(needlePos);
                                      //    majdFrequencyView.setText(String.format("%.02fHz", freq));
                                      //  majdImage.scrollTo(((int)freq)-250,0);
                                      int freqq = Math.round(freq);


                                      float dif;
                                      float absT;
                                      float perT;
                                      try
                                      {

                                      }
                                      catch (Exception e)
                                      {

                                      }

                                      //  if(freq>=fre[3] && freq < fre[4])
                                      //{

                                      try {


                                      if (true) {
                                          mFrequencyView.setText("تردد الهرتز الحالي: "+String.format("%.02fHz", freq));
                               //           Toast.makeText(getApplicationContext(),"نسخة تجريبية",Toast.LENGTH_SHORT).show();

                                          for (int i = 0; i < 37; i++) {
                                              if (freq >= fre[i] && freq < fre[i + 1]) {
                                                  dif = Math.abs(freq - fre[i]);
                                                  absT = Math.abs(fre[i] - fre[i + 1]);
                                                  perT = dif / absT;
                                                  perT = perT * (wid / 3.0f);
                                                  //  majdImage.scrollTo(Math.round(wid/3.0f)*i+Math.round(perT)-Math.round(wid/2.0f),0);

                                                  majdImage.setX(-1.0f * ((wid / 3.0f) * i + (perT) - (wid / 2.0f)));
                                                  if (Math.abs(freq - fre[0]) < 1f) {

                                                      majdImage2.setImageResource(R.drawable.true1);
                                                      majdFrequencyView.setText("العلامة الموسيقية: سي 1");

                                                      if (play == 0)
                                                      {
                                                          //audioPlayer.play(getApplicationContext(),R.raw.tr);
                                                          play=0;
                                                      }

                                                      majdFrequencyView.setTextColor(getResources().getColor(R.color.Wrong));
                                                  }
                                                  else if (Math.abs(freq - fre[1]) < 1f) {
                                                      majdImage2.setImageResource(R.drawable.true1);

                                                      majdFrequencyView.setText("العلامة الموسيقية: دو 2");
                                                      majdFrequencyView.setTextColor(getResources().getColor(R.color.Wrong));

                                                      if (play == 0)
                                                      {
                                                          //audioPlayer.play(getApplicationContext(),R.raw.tr);

                                                          play=0;
                                                      }

                                                  }
                                                  else if (Math.abs(freq - fre[2]) < 1f) {
                                                      majdImage2.setImageResource(R.drawable.true1);

                                                      majdFrequencyView.setText("العلامة الموسيقية: ري(بيمول) 2");
                                                      majdFrequencyView.setTextColor(getResources().getColor(R.color.Wrong));
                                                      if (play == 0)
                                                      {
                                                          //audioPlayer.play(getApplicationContext(),R.raw.tr);
                                                          play=0;
                                                      }      }
                                                  else if (Math.abs(freq - fre[3]) < 1f) {
                                                      majdImage2.setImageResource(R.drawable.true1);

                                                      majdFrequencyView.setText("العلامة الموسيقية: ري2 ");
                                                      majdFrequencyView.setTextColor(getResources().getColor(R.color.Wrong));
                                                      if (play == 0)
                                                      {
                                                          //audioPlayer.play(getApplicationContext(),R.raw.tr);

                                                          play=0;
                                                      }    }
                                                  else if (Math.abs(freq - fre[4]) < 1f) {
                                                      majdImage2.setImageResource(R.drawable.true1);

                                                      majdFrequencyView.setText("العلامة الموسيقية: مي(بيمول) 2");
                                                      majdFrequencyView.setTextColor(getResources().getColor(R.color.Wrong));
                                                      if (play == 0)
                                                      {

                                                          //audioPlayer.play(getApplicationContext(),R.raw.tr);

                                                          play=0;
                                                      }  }
                                                  else if (Math.abs(freq - fre[5]) < 1f) {
                                                      majdImage2.setImageResource(R.drawable.true1);

                                                      majdFrequencyView.setText("العلامة الموسيقية: مي 2");
                                                      majdFrequencyView.setTextColor(getResources().getColor(R.color.Wrong));
                                                      if (play == 0)
                                                      {

                                                          //audioPlayer.play(getApplicationContext(),R.raw.tr);

                                                          play=0;
                                                      }  }
                                                  else if (Math.abs(freq - fre[6]) < 1f) {
                                                      majdImage2.setImageResource(R.drawable.true1);

                                                      majdFrequencyView.setText("الوتر السادس: فا 2");
                                                      majdFrequencyView.setTextColor(getResources().getColor(R.color.True));
                                                      if (play == 0)
                                                      {
                                                          //audioPlayer.play(getApplicationContext(),R.raw.f2);

                                                          play=0;
                                                      }   }
                                                  else if (Math.abs(freq - fre[7]) < 1f) {
                                                      majdImage2.setImageResource(R.drawable.true1);

                                                      majdFrequencyView.setText("العلامة الموسيقية: صول(بيمول) 2");
                                                      majdFrequencyView.setTextColor(getResources().getColor(R.color.Wrong));
                                                      if (play == 0)
                                                      {
                                                          //audioPlayer.play(getApplicationContext(),R.raw.tr);

                                                          play=0;
                                                      }   }
                                                  else if (Math.abs(freq - fre[8]) < 1f) {
                                                      majdImage2.setImageResource(R.drawable.true1);

                                                      majdFrequencyView.setText("العلامة الموسيقية: صول 2");
                                                      majdFrequencyView.setTextColor(getResources().getColor(R.color.Wrong));
                                                      if (play == 0)
                                                      {
                                                          //audioPlayer.play(getApplicationContext(),R.raw.tr);

                                                          play=0;
                                                      }   }
                                                  else if (Math.abs(freq - fre[9]) < 1.2f) {
                                                      majdFrequencyView.setTextColor(getResources().getColor(R.color.Wrong));
                                                      majdImage2.setImageResource(R.drawable.true1);
                                                      majdFrequencyView.setText("العلامة الموسيقية: لا(بيمول) 2");

                                                      if (play == 0)
                                                      {
                                                          //audioPlayer.play(getApplicationContext(),R.raw.tr);

                                                          play=0;
                                                      }    }
                                                  else if (Math.abs(freq - fre[10]) < 1.3f) {
                                                      majdFrequencyView.setText( "الوتر الخامس: لا 2");
                                                      majdImage2.setImageResource(R.drawable.true1);
                                                      majdFrequencyView.setTextColor(getResources().getColor(R.color.True));

                                                      if (play == 0)
                                                      {
                                                          //audioPlayer.play(getApplicationContext(),R.raw.a2);

                                                          play=0;
                                                      }      }
                                                  else if (Math.abs(freq - fre[11]) < 1.3f) {
                                                      majdFrequencyView.setText("العلامة الموسيقية: سي(بيمول) 2");
                                                      majdImage2.setImageResource(R.drawable.true1);
                                                      majdFrequencyView.setTextColor(getResources().getColor(R.color.Wrong));
                                                      if (play == 0)
                                                      {
                                                          //audioPlayer.play(getApplicationContext(),R.raw.tr);

                                                          play=0;
                                                      }   }
                                                  else if (Math.abs(freq - fre[12]) < 1.3f) {
                                                      majdFrequencyView.setText("العلامة الموسيقية: سي 2");
                                                      majdImage2.setImageResource(R.drawable.true1);

                                                      if (play == 0)
                                                      {
                                                          //audioPlayer.play(getApplicationContext(),R.raw.tr);

                                                          play=0;
                                                      }    }
                                                  else if (Math.abs(freq - fre[13]) < 1.3f) {
                                                      majdFrequencyView.setText("العلامة الموسيقية: دو 3");
                                                      majdImage2.setImageResource(R.drawable.true1);
                                                      majdFrequencyView.setTextColor(getResources().getColor(R.color.Wrong));
                                                      if (play == 0)
                                                      {
                                                          //audioPlayer.play(getApplicationContext(),R.raw.tr);

                                                          play=0;
                                                      }   }
                                                  else if (Math.abs(freq - fre[14]) < 1.3f) {
                                                      majdFrequencyView.setText("العلامة الموسيقية: ري(بيمول) 3");
                                                      majdImage2.setImageResource(R.drawable.true1);
                                                      majdFrequencyView.setTextColor(getResources().getColor(R.color.Wrong));
                                                      if (play == 0)
                                                      {
                                                          //audioPlayer.play(getApplicationContext(),R.raw.tr);

                                                          play=0;
                                                      }   }
                                                  else if (Math.abs(freq - fre[15]) < 1.6f) {
                                                      majdFrequencyView.setText("الوتر الرابع: ري 3");
                                                      majdImage2.setImageResource(R.drawable.true1);
                                                      majdFrequencyView.setTextColor(getResources().getColor(R.color.True));


                                                      if (play == 0)
                                                      {
                                                          //audioPlayer.play(getApplicationContext(),R.raw.d3);

                                                          play=0;
                                                      }  }
                                                  else if (Math.abs(freq - fre[16]) < 1.6f) {
                                                      majdFrequencyView.setText("العلامة الموسيقية: مي(بيمول) 3");
                                                      majdImage2.setImageResource(R.drawable.true1);
                                                      majdFrequencyView.setTextColor(getResources().getColor(R.color.Wrong));
                                                      if (play == 0)
                                                      {
                                                          //audioPlayer.play(getApplicationContext(),R.raw.tr);

                                                          play=0;
                                                      }   }
                                                  else if (Math.abs(freq - fre[17]) < 1.6f) {
                                                      majdFrequencyView.setText("العلامة الموسيقية: مي 3");
                                                      majdImage2.setImageResource(R.drawable.true1);
                                                      majdFrequencyView.setTextColor(getResources().getColor(R.color.Wrong));
                                                      if (play == 0)
                                                      {
                                                          //audioPlayer.play(getApplicationContext(),R.raw.tr);

                                                          play=0;
                                                      }   }
                                                  else if (Math.abs(freq - fre[18]) < 1.6f) {
                                                      majdFrequencyView.setText("العلامة الموسيقية: فا 3");

                                                      majdImage2.setImageResource(R.drawable.true1);
                                                      majdFrequencyView.setTextColor(getResources().getColor(R.color.Wrong));
                                                      if (play == 0)
                                                      {
                                                          //audioPlayer.play(getApplicationContext(),R.raw.tr);

                                                          play=0;
                                                      }   }
                                                  else if (Math.abs(freq - fre[19]) < 1.6f) {
                                                      majdFrequencyView.setText("العلامة الموسيقية: صول(بيمول) 3");
                                                      majdImage2.setImageResource(R.drawable.true1);
                                                      majdFrequencyView.setTextColor(getResources().getColor(R.color.Wrong));
                                                      if (play == 0)
                                                      {
                                                          //audioPlayer.play(getApplicationContext(),R.raw.tr);

                                                          play=0;
                                                      }  }

                                                  else if (Math.abs(freq - fre[20]) < 2f) {
                                                      majdFrequencyView.setText("الوتر الثالث: صول 3");
                                                      majdImage2.setImageResource(R.drawable.true1);
                                                      majdFrequencyView.setTextColor(getResources().getColor(R.color.True));

                                                      if (play == 0)
                                                      {
                                                          //audioPlayer.play(getApplicationContext(),R.raw.g3);

                                                          play=0;
                                                      }  }
                                                  else if (Math.abs(freq - fre[21]) < 2f) {
                                                      majdFrequencyView.setText("العلامة الموسيقية: لا(بيمول) 3");
                                                      majdImage2.setImageResource(R.drawable.true1);
                                                      majdFrequencyView.setTextColor(getResources().getColor(R.color.Wrong));
                                                      if (play == 0)
                                                      {
                                                          //audioPlayer.play(getApplicationContext(),R.raw.tr);

                                                          play=0;
                                                      } }
                                                  else if (Math.abs(freq - fre[22]) < 2f) {
                                                      majdFrequencyView.setText("العلامة الموسيقية: لا 3");
                                                      majdImage2.setImageResource(R.drawable.true1);
                                                      majdFrequencyView.setTextColor(getResources().getColor(R.color.Wrong));
                                                      if (play == 0)
                                                      {
                                                          //audioPlayer.play(getApplicationContext(),R.raw.tr);

                                                          play=0;
                                                      } }
                                                  else if (Math.abs(freq - fre[23]) < 2f) {
                                                      majdFrequencyView.setText("العلامة الموسيقية: سي(بيمول) 3");
                                                      majdImage2.setImageResource(R.drawable.true1);
                                                      majdFrequencyView.setTextColor(getResources().getColor(R.color.Wrong));
                                                      if (play == 0)
                                                      {
                                                          //audioPlayer.play(getApplicationContext(),R.raw.tr);

                                                          play=0;
                                                      }  }
                                                  else if (Math.abs(freq - fre[24]) < 2f) {
                                                      majdFrequencyView.setText("العلامة الموسيقية: سي3");
                                                      majdImage2.setImageResource(R.drawable.true1);
                                                      majdFrequencyView.setTextColor(getResources().getColor(R.color.Wrong));
                                                      if (play == 0)
                                                      {
                                                          //audioPlayer.play(getApplicationContext(),R.raw.tr);

                                                          play=0;
                                                      }    }
                                                  else if (Math.abs(freq - fre[25]) < 2.2f) {
                                                      majdFrequencyView.setText("الوتر الثاني: دو 4");
                                                      majdImage2.setImageResource(R.drawable.true1);
                                                      majdFrequencyView.setTextColor(getResources().getColor(R.color.True));

                                                      if (play == 0)
                                                      {
                                                          //audioPlayer.play(getApplicationContext(),R.raw.c4);

                                                          play=0;
                                                      }   }

                                                  else if (Math.abs(freq - fre[26]) < 2.2f) {
                                                      majdFrequencyView.setText("العلامة الموسيقية: ري(بيمول) 4");
                                                      majdImage2.setImageResource(R.drawable.true1);
                                                      majdFrequencyView.setTextColor(getResources().getColor(R.color.Wrong));
                                                      if (play == 0)
                                                      {
                                                          //audioPlayer.play(getApplicationContext(),R.raw.tr);

                                                          play=0;
                                                      }  }

                                                  else if (Math.abs(freq - fre[27]) < 2.2f) {
                                                      majdFrequencyView.setText("العلامة الموسيقية: ري4");
                                                      majdImage2.setImageResource(R.drawable.true1);
                                                      majdFrequencyView.setTextColor(getResources().getColor(R.color.Wrong));
                                                      if (play == 0)
                                                      {
                                                          //audioPlayer.play(getApplicationContext(),R.raw.tr);

                                                          play=0;
                                                      }  }
                                                  else if (Math.abs(freq - fre[28]) < 2.2f) {
                                                      majdFrequencyView.setText("العلامة الموسيقية: مي(بيمول) 4");
                                                      majdImage2.setImageResource(R.drawable.true1);
                                                      majdFrequencyView.setTextColor(getResources().getColor(R.color.Wrong));
                                                      if (play == 0)
                                                      {
                                                          //audioPlayer.play(getApplicationContext(),R.raw.tr);

                                                          play=0;
                                                      }  }
                                                  else if (Math.abs(freq - fre[29]) < 2.2f) {
                                                      majdFrequencyView.setText("العلامة الموسيقية: مي 4");
                                                      majdImage2.setImageResource(R.drawable.true1);
                                                      majdFrequencyView.setTextColor(getResources().getColor(R.color.Wrong));
                                                      if (play == 0)
                                                      {
                                                          //audioPlayer.play(getApplicationContext(),R.raw.tr);

                                                          play=0;
                                                      }  }

                                                  else if (Math.abs(freq - fre[30]) < 2.5f) {
                                                      majdFrequencyView.setText("الوتر الأول: فا 4");
                                                      majdImage2.setImageResource(R.drawable.true1);
                                                      majdFrequencyView.setTextColor(getResources().getColor(R.color.True));

                                                      if (play == 0)
                                                      {
                                                          //audioPlayer.play(getApplicationContext(),R.raw.f4);

                                                          play=0;
                                                      }   }
                                                  else if (Math.abs(freq - fre[31]) < 2.5f) {
                                                      majdFrequencyView.setText("العلامة الموسيقية: صول(بيمول) 4");
                                                      majdImage2.setImageResource(R.drawable.true1);
                                                      majdFrequencyView.setTextColor(getResources().getColor(R.color.Wrong));
                                                      if (play == 0)
                                                      {
                                                          //audioPlayer.play(getApplicationContext(),R.raw.tr);

                                                          play=0;
                                                      }   }
                                                  else if (Math.abs(freq - fre[32]) < 2.5f) {
                                                      majdFrequencyView.setText("العلامة الموسيقية: صول 4");
                                                      majdImage2.setImageResource(R.drawable.true1);
                                                      majdFrequencyView.setTextColor(getResources().getColor(R.color.Wrong));
                                                      if (play == 0)
                                                      {
                                                          //audioPlayer.play(getApplicationContext(),R.raw.tr);

                                                          play=0;
                                                      }  }
                                                  else if (Math.abs(freq - fre[33]) < 2.5f) {
                                                      majdFrequencyView.setText("العلامة الموسيقية: لا(بيمول) 4");
                                                      majdImage2.setImageResource(R.drawable.true1);
                                                      majdFrequencyView.setTextColor(getResources().getColor(R.color.Wrong));
                                                      if (play == 0)
                                                      {
                                                          //audioPlayer.play(getApplicationContext(),R.raw.tr);

                                                          play=0;
                                                      }     }
                                                  else if (Math.abs(freq - fre[34]) < 2.5f) {
                                                      majdFrequencyView.setText("العلامة الموسيقية: لا 4");
                                                      majdImage2.setImageResource(R.drawable.true1);
                                                      majdFrequencyView.setTextColor(getResources().getColor(R.color.Wrong));
                                                      if (play == 0)
                                                      {
                                                          //audioPlayer.play(getApplicationContext(),R.raw.tr);

                                                          play=0;
                                                      }   }
                                                  else if (Math.abs(freq - fre[35]) < 2.5f) {
                                                      majdFrequencyView.setText("العلامة الموسيقية: سي(بيمول) 4");
                                                      majdImage2.setImageResource(R.drawable.true1);
                                                      majdFrequencyView.setTextColor(getResources().getColor(R.color.Wrong));
                                                      if (play == 0)
                                                      {

                                                          //audioPlayer.play(getApplicationContext(),R.raw.tr);
                                                          play=0;
                                                      }  }
                                                  else if (Math.abs(freq - fre[36]) < 2.5f) {
                                                      majdFrequencyView.setText("العلامة الموسيقية: سي 5");
                                                      majdImage2.setImageResource(R.drawable.true1);
                                                      majdFrequencyView.setTextColor(getResources().getColor(R.color.Wrong));
                                                      if (play == 0)
                                                      {
                                                          //audioPlayer.play(getApplicationContext(),R.raw.tr);

                                                          play=0;
                                                      }   }
                                                  else {
                                                      play = 0;
                                                      majdFrequencyView.setText("العلامة الموسيقية: لايوجد تطابق");
                                                      majdImage2.setImageResource(R.drawable.false1);
                                                      majdFrequencyView.setTextColor(getResources().getColor(R.color.Wrong));
                                                  }
                                                  break;
                                              }

                                          }

                        /*
                            dif = Math.abs(freqq-fre[2]);
                            absT = Math.abs(fre[2]-fre[3]);
                            perT = dif/absT;
                            perT = perT * wid/3;
                            majdImage.scrollTo((wid/3)*2+(int)perT-(wid/2),0);
*/
                                          //   }
                                          // else
                                          //   majdImage.scrollTo(wid+wid/3,0);


                                          // final View goodPitchView = findViewById(R.id.good_pitch_view);
                                          //  if (goodPitchView != null) {
                                          //    if (goodPitch) {
                                          //        if (goodPitchView.getVisibility() != View.VISIBLE) {
                                          //             Utils.reveal(goodPitchView);
                                          //        }
                                          //     } else if (goodPitchView.getVisibility() == View.VISIBLE) {
                                          //          Utils.hide(goodPitchView);
                                          //       }
                                          //   }
                                      }

                                      }
                                      catch (Exception e){}

                                  }
                              }
                );

                //
                //
                //  mPitchIndex = index;
                mLastFreq = freq;

            }
        });
        mProcessing = true;
        mExecutor.execute(mAudioProcessor);
    }



    @Override
    protected void onPause() {
        super.onPause();
    }
    private AdView mAdView;

    @SuppressLint("DefaultLocale")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //Utils.setupActivityTheme(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        //mTuning = Tuning.getTuning(this, Preferences.getString(this, getString(R.string.pref_tuning_key), getString(R.string.standard_tuning_val)));

        MobileAds.initialize(this,"ca-app-pub-6000403156585351~1180991630");

        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        Display display = getWindowManager().getDefaultDisplay();
        DisplayMetrics outdisplay = new DisplayMetrics();
        display.getMetrics(outdisplay);

        float density = getResources().getDisplayMetrics().density;
        float dbHieght = outdisplay.heightPixels/density;
        float dbWidth = outdisplay.widthPixels/density;

        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int hieght = size.y;
        wid = width;

        int height = size.y;


        mFrequencyView = (TextView) findViewById(R.id.frequency_view);
         mFrequencyView.setText(String.format("تردد الهرتز الحالي: لايوجد صوت"));
        majdFrequencyView = (TextView) findViewById(R.id.textView);
         majdFrequencyView.setText(String.format("العلامة الموسيقية: لايوجد صوت"));
        majdFrequencyView.setTextColor(getResources().getColor(R.color.Wrong));
        majdImage = (ImageView) findViewById(R.id.imageView);
        majdImage2 = (ImageView) findViewById(R.id.imageView2);

        ViewGroup.LayoutParams params=majdImage.getLayoutParams();
        ViewGroup.LayoutParams params2=majdImage2.getLayoutParams();

        params2.width = (int)((width/11.0f));
        params2.height = (int)((height/6.0f))+40;


        perc = (int)((width/3.0f)*40.0f);
        params.width=perc;
        params.height=(int)((height/6.0f));
        majdImage.setPadding(0,30,0,0);
        majdImage.setLayoutParams(params);
        majdImage2.setLayoutParams(params2);

        per = Math.min(411.42856f,dbWidth)/Math.max(411.42856f,dbWidth);
        //majdImage.scrollTo((int) (per*19),0);
       // Toast.makeText(this,height+ " ... "+ wid,Toast.LENGTH_LONG).show();
        fre[0]=61.74f ; // B1
        fre[1]=65.41f ; // C2
        fre[2]=	69.30f;  // C2#
        fre[3]=73.42f ; // D2
        fre[4]=77.78f ; // D2#
        fre[5]=82.41f ; // E2
        fre[6]=87.31f ;// F2
        fre[7]=92.50f ;// F2#
        fre[8]=98.00f ;// G2
        fre[9]=103.83f ;// G2#
        fre[10]=110.00f ;//A2
        fre[11]= 116.54f;//A2#
        fre[12]=123.47f ; // B2
        fre[13]=130.81f ; // C3
        fre[14]=138.59f;  // C3#
        fre[15]=146.83f ; // D3
        fre[16]=155.56f ; // D3#
        fre[17]=164.81f ; // E3
        fre[18]=174.61f ;// F3
        fre[19]=185.00f ;// F3#
        fre[20]=196.00f ;// G3
        fre[21]=207.65f ;// G3#
        fre[22]=220.00f ;//A3
        fre[23]=233.08f;//A3#
        fre[24]=246.94f ; // B3
        fre[25]=261.63f ; // C4
        fre[26]=277.18f;  // C4#
        fre[27]=293.66f ; // D4
        fre[28]=311.13f ; // D4#
        fre[29]=329.63f ; // E4
        fre[30]=349.23f ;// F4
        fre[31]=369.99f ;// F4#
        fre[32]=392.00f ;// G4
        fre[33]=415.30f ;// G4#
        fre[34]=440.00f ;//A4
        fre[35]= 466.16f;//A4#
        fre[36]= 493.88f;//B4
        fre[37]= 523.25f;//C5













        requestPermissions();

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        //  outState.putFloat(STATE_NEEDLE_POS, mNeedleView.getTipPos());
        outState.putInt(STATE_PITCH_INDEX, mPitchIndex);
        outState.putFloat(STATE_LAST_FREQ, mLastFreq);
        super.onSaveInstanceState(outState);
    }

    @SuppressLint("DefaultLocale")
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        //  mNeedleView.setTipPos(savedInstanceState.getFloat(STATE_NEEDLE_POS));
        int pitchIndex = savedInstanceState.getInt(STATE_PITCH_INDEX);
        //  mNeedleView.setTickLabel(0.0F, String.format("%.02fHz", mTuning.pitches[pitchIndex].frequency));
        //  mTuningView.setSelectedIndex(pitchIndex);
        mFrequencyView.setText(String.format("%.02fHz", savedInstanceState.getFloat(STATE_LAST_FREQ)));
        majdFrequencyView.setText(String.format("%.02fHz", savedInstanceState.getFloat(STATE_LAST_FREQ)));

    }




    public void buClicked(View view) {
        Button button=(Button) findViewById(R.id.button);
        Intent intent=new Intent(Intent.ACTION_VIEW);

        intent.setData(Uri.parse("https://www.youtube.com/channel/UCQG56gQQA0JXR-XK0XkqyPA"));
        view.getContext().startActivity(intent);



    }

    public void buWhats(View view) {
        Button whatsBut=(Button) findViewById(R.id.textView4);

        ((ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE)).setText("+962772315499");
        Toast.makeText(this, " تم النسخ ",Toast.LENGTH_LONG).show();



    }
}


