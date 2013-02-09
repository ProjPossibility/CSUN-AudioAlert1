package info.ss12.audioalertsystem;

import com.musicg.api.DetectionApi;
import com.musicg.wave.WaveHeader;

public class AlarmAPI extends DetectionApi {
	public AlarmAPI (WaveHeader waveHeader) {
		super(waveHeader);
	}
	
	protected void init() {
		// settings for detecting a fire alarm
		minFrequency = 0.0f;
		maxFrequency = Double.MAX_VALUE;
		
		minIntensity = 1000.0f;
		maxIntensity = 100000.0f;
		
		minStandardDeviation = 0.1f;
		maxStandardDeviation = 1.0f;
		
		highPass = 100;
		lowPass = 100000;
		
		minNumZeroCross = 50;
		maxNumZeroCross = 200;
		
		numRobust = 10;
	}
	
	public boolean isFireAlarm(byte[] audioBytes) {
		return isSpecificSound(audioBytes);
	}
}

