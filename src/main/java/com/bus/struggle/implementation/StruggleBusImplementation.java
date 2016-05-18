package com.bus.struggle.implementation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import com.bus.struggle.config.MongoConfig;
import com.bus.struggle.entity.StruggleBusEntity;
import com.bus.struggle.mapper.OmniMapper;
import com.bus.struggle.model.Aknowledgement;
import com.bus.struggle.model.StruggleBus;

public class StruggleBusImplementation {
	ApplicationContext ctx = new AnnotationConfigApplicationContext(MongoConfig.class);
	MongoOperations mongoOperation = (MongoOperations) ctx.getBean("mongoTemplate");
	OmniMapper omniMapper = new OmniMapper();

	public StruggleBus postStruggle(StruggleBus struggleBus) {
		struggleBus.setUuid(UUID.randomUUID());
		struggleBus.setPostDate(new Date());
		StruggleBusEntity struggleBusEntity = new StruggleBusEntity();
		mongoOperation.save(omniMapper.omniMapper(struggleBus, struggleBusEntity));
		return struggleBus;
	}

	public StruggleBus retrieveStruggle(UUID uuid) {
		StruggleBusEntity struggleEntity = new StruggleBusEntity();
	 	Query findStruggleByUuid = new Query(Criteria.where("uuid").is(uuid));
	 	struggleEntity = mongoOperation.findOne(findStruggleByUuid, StruggleBusEntity.class);
	 	StruggleBus returnStruggle = new StruggleBus();
	 	returnStruggle = (StruggleBus)omniMapper.omniMapper(struggleEntity, returnStruggle);
		return returnStruggle;
	}

	public ArrayList<StruggleBus> getLatestBusses() {
		List<StruggleBusEntity> struggles = new ArrayList<StruggleBusEntity>();
	 	Query findStruggleByUuid = new Query(Criteria.where("uuid").exists(true));
	 	struggles = mongoOperation.find(findStruggleByUuid, StruggleBusEntity.class);
		return sortDates(struggles);
	}

	public ArrayList<StruggleBus> getHotBusses() {
		List<StruggleBusEntity> struggles = new ArrayList<StruggleBusEntity>();
		Date date = new Date(new Date().getTime()-604800000);
	 	Query findStruggleByPastWeek = new Query(Criteria.where("postDate").gte(date));
	 	struggles = mongoOperation.find(findStruggleByPastWeek, StruggleBusEntity.class);
		return sortLikes(struggles);
	}
	
	public ArrayList<StruggleBus> getTopBusses() {
		List<StruggleBusEntity> struggles = new ArrayList<StruggleBusEntity>();
	 	Query findStruggleByPastWeek = new Query(Criteria.where("uuid").exists(true));
	 	struggles = mongoOperation.find(findStruggleByPastWeek, StruggleBusEntity.class);
		return sortLikes(struggles);
	}

	public Aknowledgement addLike(UUID uuid) {
		StruggleBusEntity struggleEntity = new StruggleBusEntity();
	 	Query findStruggleByUuid = new Query(Criteria.where("uuid").is(uuid));
	 	struggleEntity = mongoOperation.findOne(findStruggleByUuid, StruggleBusEntity.class);
	 	long riders = struggleEntity.getRiders().longValue();
	 	mongoOperation.updateFirst(findStruggleByUuid, Update.update("riders", riders+=1), StruggleBusEntity.class);
	 	Aknowledgement aknowledgement = new Aknowledgement();
	 	aknowledgement.setSuccess(true);
	 	aknowledgement.setValue("success");
		return aknowledgement;
	}

	
	private ArrayList<StruggleBus> sortDates(List<StruggleBusEntity> list){
		ArrayList<StruggleBus> struggles = new ArrayList<StruggleBus>();
		Collections.sort(list, new Comparator<StruggleBusEntity>() {
 		  public int compare(StruggleBusEntity o1, StruggleBusEntity o2) {
 		      return o1.getPostDate().compareTo(o2.getPostDate());
 		  }
 		});
		
		for(int i =0; i<50 && i<list.size(); i++){
			StruggleBus struggleBus = new StruggleBus();
			struggleBus = (StruggleBus)omniMapper.omniMapper(list.get(i), struggleBus);
			struggles.add(struggleBus);
		}
		return struggles;
	}
	
	private ArrayList<StruggleBus> sortLikes(List<StruggleBusEntity> list){
		ArrayList<StruggleBus> struggles = new ArrayList<StruggleBus>();
		Collections.sort(list, new Comparator<StruggleBusEntity>() {
 		  public int compare(StruggleBusEntity o1, StruggleBusEntity o2) {
 		      return o1.getRiders().compareTo(o2.getRiders());
 		  }
 		});
		
		for(int i =0; i<50 && i<list.size(); i++){
			StruggleBus struggleBus = new StruggleBus();
			struggleBus = (StruggleBus)omniMapper.omniMapper(list.get(i), struggleBus);
			struggles.add(struggleBus);
		}
		return struggles;
	}
}
