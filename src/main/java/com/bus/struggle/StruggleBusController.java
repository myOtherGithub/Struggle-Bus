package com.bus.struggle;

import org.springframework.web.bind.annotation.RestController;

import com.bus.struggle.implementation.StruggleBusImplementation;
import com.bus.struggle.model.Aknowledgement;
import com.bus.struggle.model.StruggleBus;
import java.util.ArrayList;
import java.util.UUID;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RestController
public class StruggleBusController {
	StruggleBusImplementation struggleImpl = new StruggleBusImplementation();

    @RequestMapping("/struggles")
    public String hotstruggles(Model model) {
        return "hotstruggles";
    }
    
    @RequestMapping(value="/postStruggle", method = RequestMethod.POST,
			 produces = MediaType.APPLICATION_JSON_VALUE,consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public StruggleBus postBus(@RequestBody StruggleBus struggleBus) {
		 return struggleImpl.postStruggle(struggleBus);
	};
	
	@RequestMapping(value="/getSpecificBus/{uuid}")
	@ResponseBody
	public ArrayList<StruggleBus> getBus(@PathVariable UUID uuid){
		StruggleBus singleStruggleBus = struggleImpl.retrieveStruggle(uuid);
		ArrayList<StruggleBus> struggle = new ArrayList<StruggleBus>();
		struggle.add(singleStruggleBus);
		return struggle;
	};
    
    @RequestMapping("/getNewBusses")
    @ResponseBody
    public ArrayList<StruggleBus> getLatestBusses() {
        return struggleImpl.getLatestBusses();
    }
    
    @RequestMapping("/getHotBusses")
    @ResponseBody
    public ArrayList<StruggleBus> getHotBusses() {
       return struggleImpl.getHotBusses();
    }
    
    @RequestMapping("/getTopBusses")
    @ResponseBody
    public ArrayList<StruggleBus> getTopBusses() {
       return struggleImpl.getTopBusses();
    }
    
    @RequestMapping(value="/joinStruggle", method = RequestMethod.POST)
   	@ResponseBody
   	public Aknowledgement addLike( @RequestBody StruggleBus struggleBus) {
   		 return struggleImpl.addLike(struggleBus.getUuid());
   	};
}