package info.ss12.audioalertsystem;

import com.musicg.wave.WaveHeader;

public class AlarmApi extends AlternateDetectionApi
{

	public AlarmApi(WaveHeader waveHeader)
	{
		super(waveHeader);
	}

	protected void init()
	{
		// settings for detecting a alarm
		minFrequency = 600.0f;
		maxFrequency = Double.MAX_VALUE;

		minIntensity = 100.0f;
		maxIntensity = 100000.0f;

		minStandardDeviation = 0.1f;
		maxStandardDeviation = 1.0f;

		highPass = 100;
		lowPass = 10000;

		minNumZeroCross = 50;
		maxNumZeroCross = 200;

		numRobust = 10;
	}

	public boolean isAlarm(byte[] audioBytes)
	{
		return isSpecificSound(audioBytes);
	}

}
