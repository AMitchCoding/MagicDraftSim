package com.mikeyalex.magicdraftsim;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class MagicDraftSim {

    private ArrayList<ArrayList<JSONObject>> boosters_old = new ArrayList<ArrayList<JSONObject>>();
    private ArrayList<JSONObject> boosters = new ArrayList<JSONObject>();

    public void CreateRoomFile() throws IOException {
        //Create JSONObject from JSON file
        ClassLoader classLoader = getClass().getClassLoader();
        File setFile = new File(classLoader.getResource("DTK.json").getFile());
        JSONObject room = new JSONObject();
        JSONArray players = new JSONArray();
        JSONObject player = new JSONObject();
        player.put("name", "Alex");
        player.put("drafted-cards", new JSONArray());
        player.put("mainboard", new JSONArray());
        player.put("sideboard", new JSONArray());
        players.put(player);
        room.put("players", players);

        String json = new String(Files.readAllBytes(Paths.get(setFile.toURI())), StandardCharsets.UTF_8);

        JSONObject setJSON = new JSONObject(json);

        //Load cards to rarity sets
        ArrayList<JSONObject> setMythic = new ArrayList<JSONObject>();
        ArrayList<JSONObject> setRare =  new ArrayList<JSONObject>();
        ArrayList<JSONObject> setUncommon =  new ArrayList<JSONObject>();
        ArrayList<JSONObject> setCommon =  new ArrayList<JSONObject>();
        ArrayList<JSONObject> setLands =  new ArrayList<JSONObject>();
        JSONArray setCards = setJSON.getJSONArray("cards");
        for (int i = 0; i < setCards.length(); i++){
            JSONObject currentCard = setCards.getJSONObject(i);
            String s = currentCard.getString("rarity");
            if (s.equals("Mythic Rare")) {
                setMythic.add(currentCard);
            } else if (s.equals("Rare")) {
                setRare.add(currentCard);
            } else if (s.equals("Uncommon")) {
                setUncommon.add(currentCard);
            } else if (s.equals("Common")) {
                setCommon.add(currentCard);
            } else if (s.equals("Basic Land")) {
                setLands.add(currentCard);
            }
        }


        Date now = new Date();
        Random random = new Random(now.getTime());

        //initialize i with number of players
        for(int i = 0; i < 3; i++){
            boosters.add(new JSONObject());
            ArrayList<JSONObject> cards = new ArrayList<JSONObject>();

            //Add commons
            for(int c = 0; c < 10; c++) {
                GetCard(setCommon, random, cards);
            }
            //Add uncommons
            for(int u = 0; u < 3; u++) {
                GetCard(setUncommon, random, cards);
            }
            //Add rare
            if(random.nextInt(8)<7)
                GetCard(setRare, random, cards);
            //Add mythic
            else
                GetCard(setMythic, random, cards);
            //Add land
            GetCard(setLands, random, cards);

            if(random.nextInt(7) > 5){
                JSONObject foilCard = cards.get(random.nextInt(cards.size()));
                foilCard.put("foil", true);
                if(cards.get(0).has("foil"))
                    cards.remove(1);
                else
                    cards.remove(0);
                cards.add(foilCard);
            }
            boosters.get(i).put("cards", cards);

        }
        room.put("boosters", boosters);

        File roomFile = new File(System.getProperty("java.io.tmpdir") + "/room1.json");
        roomFile.createNewFile();
        Writer writer = new FileWriter(roomFile);
        room.write(writer);


    }

    public void GetCard(ArrayList<JSONObject> set, Random random, ArrayList<JSONObject> cards){
        JSONObject currentCard;
        boolean cardAdded = false;

        currentCard = set.get(random.nextInt(set.size()));
        while(!cardAdded) {
            if (!cards.contains((currentCard))) {
                cards.add(currentCard);
                cardAdded = true;
            }
            else
                currentCard = set.get(random.nextInt(set.size()));
        }
    }

}
