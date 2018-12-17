package com.client.bnb.myapplication;

import android.content.Context;
import android.speech.tts.TextToSpeech;
import android.widget.Toast;

/**
 * Created by Dmytro on 21.10.2018.
 */

public class TTS {


    public static void SpeakText(TextToSpeech textToSpeech, Context context, String text){
        String toSpeak = text;
        Toast.makeText(context, toSpeak,Toast.LENGTH_SHORT).show();
        textToSpeech.speak(toSpeak, TextToSpeech.QUEUE_ADD, null);
    }

}
