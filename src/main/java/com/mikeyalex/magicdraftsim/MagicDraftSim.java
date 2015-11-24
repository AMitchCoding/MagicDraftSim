package com.mikeyalex.magicdraftsim;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class MagicDraftSim {

    public void CreateRoomFile() throws FileNotFoundException {
        //TODO
        //Mythic vs Rare 1:8
        //No duplicates
        //Foil 1:6 packs replaces a common

        //Create JSONObject from JSON file
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource("DTK.json").getFile());
        StringBuilder json = new StringBuilder("");
        Scanner scanner = new Scanner(file);

        while(scanner.hasNextLine()){
            String line = scanner.nextLine();
            json.append(line).append("\n");
        }
        scanner.close();

        JSONObject setJSON = new JSONObject(json);

        //Load cards to rarity sets
        ArrayList<JSONObject> setMythic = new ArrayList<JSONObject>();
        ArrayList<JSONObject> setRare =  new ArrayList<JSONObject>();
        ArrayList<JSONObject> setUncommon =  new ArrayList<JSONObject>();
        ArrayList<JSONObject> setCommon =  new ArrayList<JSONObject>();
        JSONArray setCards = setJSON.getJSONArray("cards");
        for (int i = 0; i < setCards.length(); i++){
            JSONObject currentCard = setCards.getJSONObject(i);
            String s = currentCard.getString("rarity");
            if (s.equals("Mythic")) {
                setMythic.add(currentCard);
            } else if (s.equals("Rare")) {
                setRare.add(currentCard);
            } else if (s.equals("Uncommon")) {
                setUncommon.add(currentCard);
            } else if (s.equals("Common")) {
                setCommon.add(currentCard);
            }
        }

        //Build boosters
        ArrayList<JSONArray> boosters = new ArrayList<JSONArray>();
        Random random = new Random();
    }
}
