package Audio;

import java.util.*;
import java.util.Map.*;

import javax.print.attribute.standard.MediaSize.*;

import acm.util.*;

public class AudioManager {

	private static String previousStage = "";
	public static String otherNameStage = "";
	private static SoundClip tmpSound,prev;
	private static boolean start = true;
	private static int indexSound = -1;
	private static HashMap<String,SoundClip> ambientSound = new HashMap<String,SoundClip>();
	private static HashMap<String,SoundClip> sound = new HashMap<String,SoundClip>();
	
	public static void addAmbientSound(String stage,String url){
		try{
			tmpSound = new SoundClip(url);
			tmpSound.setVolume(1.0);
			ambientSound.put(stage, tmpSound);
		}catch(Exception e){e.printStackTrace();}
	}
	
	public static void update(String stage){
		if(!previousStage.equals(stage))indexSound = -1;
		ArrayList<SoundClip> sounds = getAmbientSoundByStage(stage);
		if(sounds.size() != 0){
			if(indexSound != -1){
				if(sounds.get(indexSound).getFrameIndex() >= sounds.get(indexSound).getFrameCount()){
					prev.stop();
					indexSound++;
					if(indexSound>=sounds.size())indexSound=0;
					sounds.get(indexSound).play();
				}
			}else{
				if(!start){
					prev.stop();
				}
				sounds.get(0).play();
				prev = sounds.get(0);
				indexSound = 0;
				previousStage = stage;
			}
		}
		start = false;
	}
	
	public static void addSound(String name,String url){
		try{
			tmpSound = new SoundClip(url);
			tmpSound.setVolume(1.0);
			sound.put(name, tmpSound);
		}catch(Exception e){e.printStackTrace();}
	}
	
	public static SoundClip getSound(String name){
		return sound.get(name);
	}
	
	public static void playSound(String name){
		getSound(name).play();
	}
	
	public static void setVolumeSound(double vol){
		for(Entry<String, SoundClip> s : sound.entrySet())s.getValue().setVolume(vol);
	}
	
	public static void setVolumeAmbientSound(double vol){
		for(Entry<String, SoundClip> s : ambientSound.entrySet())s.getValue().setVolume(vol);
	}
	
	public static ArrayList<SoundClip> getAmbientSoundByStage(String stage){
		ArrayList<SoundClip> sounds = new ArrayList<SoundClip>();
		for(Entry<String,SoundClip> s : ambientSound.entrySet()){
			if(s.getKey().equals(stage)){
				sounds.add(s.getValue());
			}
		}
		return sounds;
	}
	
	public static void stopSound(String name){
		getSound(name).stop();
	}
	
}
