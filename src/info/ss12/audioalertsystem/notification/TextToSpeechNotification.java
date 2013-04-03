package info.ss12.audioalertsystem.notification;

import info.ss12.audioalertsystem.MainActivity;

import java.util.HashMap;

import android.media.AudioManager;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.OnInitListener;
import android.speech.tts.TextToSpeech.OnUtteranceCompletedListener;

public class TextToSpeechNotification extends AbstractNotification implements
		OnInitListener, OnUtteranceCompletedListener
{
	TextToSpeech talker;
	private boolean continueSpeaking = false;
	HashMap<String, String> myHashAlarm = new HashMap<String, String>();

	public TextToSpeechNotification(MainActivity mainActivity)
	{
		talker = new TextToSpeech(mainActivity, this);
	    myHashAlarm.put(TextToSpeech.Engine.KEY_PARAM_STREAM, String.valueOf(AudioManager.STREAM_ALARM));
	    myHashAlarm.put(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, "SOME MESSAGE");
	}

	public void say(String text2say)
	{
		talker.speak(text2say, TextToSpeech.QUEUE_FLUSH, myHashAlarm);
	}

	public void onInit(int status)
	{
		talker.setOnUtteranceCompletedListener(this);
	}

	@Override
	public void onUtteranceCompleted(String utteranceId)
	{
		if(continueSpeaking)
		{
			say("Assistance Needed");
		}
	}

	@Override
	public void startNotify()
	{
		say("Assistance Needed");
		continueSpeaking = true;
	}

	@Override
	public void stopNotify()
	{
		continueSpeaking = false;
	}
	
	public void releaseTalker()
	{
		if(talker != null)
		{
			talker.shutdown();
		}
	}
}
