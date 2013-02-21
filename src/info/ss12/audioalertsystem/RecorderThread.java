/*
 * Copyright (C) 2012 Jacquet Wong
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 * musicg api in Google Code: http://code.google.com/p/musicg/
 * Android Application in Google Play: https://play.google.com/store/apps/details?id=com.whistleapp
 * 
 */

package info.ss12.audioalertsystem;

import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;

/**
 * The RecorderThread class. Used to process audio frequencies
 */
public class RecorderThread extends Thread
{

	/**
	 * Upon creation, an AudioRecord object initializes its associated audio
	 * buffer that it will fill with the new audio data. The size of this
	 * buffer, specified during the construction, determines how long an
	 * AudioRecord can record before "over-running" data that has not been read
	 * yet. Data should be read from the audio hardware in chunks of sizes
	 * inferior to the total recording buffer size.
	 */
	private AudioRecord audioRecord;
	/** Toggle for recording */
	private boolean isRecording;
	/** Audio format for channel configuration */
	private int channelConfiguration = AudioFormat.CHANNEL_CONFIGURATION_MONO;
	/** Audio encoding ENCODING_PCM_16BIT */
	private int audioEncoding = AudioFormat.ENCODING_PCM_16BIT;
	/** The sample rate */
	private int sampleRate = 44100;
	/** The frame byte size */
	private int frameByteSize = 2048; // for 1024 fft size (16bit sample size)
	/** The frame byte size buffer */
	byte[] buffer;

	/**
	 * RecorderThread constructor. Initializes audio configurations
	 */
	public RecorderThread()
	{
		int recBufSize = AudioRecord.getMinBufferSize(sampleRate,
				channelConfiguration, audioEncoding); // need to be larger than
														// size of a frame
		audioRecord = new AudioRecord(MediaRecorder.AudioSource.MIC,
				sampleRate, channelConfiguration, audioEncoding, recBufSize);
		buffer = new byte[frameByteSize];
	}

	/**
	 * Return the AudioRecord
	 * @return the AudioRecord
	 */
	public AudioRecord getAudioRecord()
	{
		return audioRecord;
	}

	/**
	 * Return the isRecording
	 * @return isRecording
	 */
	public boolean isRecording()
	{
		return this.isAlive() && isRecording;
	}

	/**
	 * Start the recording thread
	 */
	public void startRecording()
	{
		try
		{
			audioRecord.startRecording();
			isRecording = true;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * Stop the recording thread
	 */
	public void stopRecording()
	{
		try
		{
			audioRecord.stop();
			isRecording = false;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * Return the frame bytes
	 * @return the buffer
	 */
	public byte[] getFrameBytes()
	{
		audioRecord.read(buffer, 0, frameByteSize);

		// analyze sound
		int totalAbsValue = 0;
		short sample = 0;
		float averageAbsValue = 0.0f;

		for (int i = 0; i < frameByteSize; i += 2)
		{
			sample = (short) ((buffer[i]) | buffer[i + 1] << 8);
			totalAbsValue += Math.abs(sample);
		}
		averageAbsValue = totalAbsValue / frameByteSize / 2;

		// System.out.println(averageAbsValue);

		// no input
		if (averageAbsValue < 30)
		{
			return null;
		}

		return buffer;
	}

	/**
	 * Method for starting thread
	 */
	public void run()
	{
		startRecording();
	}
}