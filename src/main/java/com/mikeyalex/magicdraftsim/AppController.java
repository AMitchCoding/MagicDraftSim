package com.mikeyalex.magicdraftsim;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.mikeyalex.magicdraftsim.MagicDraftSim;

@Controller
@RequestMapping("/")
public class AppController {

    @Autowired
    MagicDraftSim magicDraftSim = new MagicDraftSim();

    @RequestMapping(value = {"/", "/main"}, method = RequestMethod.GET)
    public String main(){ return "main"; }

    @RequestMapping(value = {"/create"}, method = RequestMethod.GET)
    public String newRoom(){
        return "create";
    }

    @RequestMapping(value = {"/create"}, method = RequestMethod.POST)
    public String createRoom(){
        magicDraftSim.CreateRoomFile();
        return "room";
    }
}