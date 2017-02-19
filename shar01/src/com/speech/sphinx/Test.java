package com.speech.sphinx;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;

import edu.cmu.sphinx.api.Configuration;
import edu.cmu.sphinx.api.LiveSpeechRecognizer;
import edu.cmu.sphinx.api.SpeechResult;

public class Test {

	public Test() {

		ex01();
		//ex02();
	}

	private void ex01() {

		Configuration configuration = new Configuration();
		 
        // Set path to the acoustic model.
        configuration
        .setAcousticModelPath("resource:/edu/cmu/sphinx/models/en-us/en-us");
        configuration
        .setDictionaryPath("resource:/edu/cmu/sphinx/models/en-us/cmudict-en-us.dict");
        configuration
        .setLanguageModelPath("resource:/edu/cmu/sphinx/models/en-us/en-us.lm.bin");

		String work = null;
		Process p;
 
        try {
			
        //Recognizer object, Pass the Configuration object
        LiveSpeechRecognizer recognize = new LiveSpeechRecognizer(configuration);
 
        //Start Recognition Process (The bool parameter clears the previous cache if true)
        recognize.startRecognition(true);
 
        //Creating SpeechResult object
        SpeechResult result;
 
        //Check if recognizer recognized the speech
        while ((result = recognize.getResult()) != null) {
 
            //Get the recognized speech
            String command = result.getHypothesis();
            //Match recognized speech with our commands
            if(result.getHypothesis().matches("(?i).*time.*")){
	    		TextToSpeechConvertor ttsc = new TextToSpeechConvertor();
	    		Date date = new Date();
	 			SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
	 			System.out.println("Date => "+sdf.format(date));
	 			ttsc.speak(sdf.format(date));
	    	 }
            else if (command.equalsIgnoreCase("notepad")) {
            	//work = "cmd /C dir";
            	work="notepad.exe";
            } 
            //In case command recognized is none of the above hence work might be null
            if(work != null) {
                //Execute the command
                p = Runtime.getRuntime().exec(work);
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(p.getInputStream()));
				String line = null;
				while ((line = in.readLine()) != null) {
				                System.out.println(line);
				}
            }
        }
        } catch (Exception e) {
        	System.out.println("Erreur-"+e.getMessage());
        }
	}

	public static void main(String[] args) {

		new Test();
	}

}
