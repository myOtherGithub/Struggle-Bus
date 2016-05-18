package com.bus.struggle.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;

import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;

@Configuration
public class MongoConfig extends AbstractMongoConfiguration {

	@Override
	public String getDatabaseName() {
		return "struggles";
	}
	
	@Override
	@Bean
	public Mongo mongo() throws Exception {
		MongoClientOptions mco = new MongoClientOptions.Builder()
			    .connectionsPerHost(100)
			    .threadsAllowedToBlockForConnectionMultiplier(10)
			    .build();
		return new MongoClient("mongodb://system:legateaunestpasunemensonage18@ds023902.mlab.com:23902/heroku_1fd736j5", mco);
		
	}
}