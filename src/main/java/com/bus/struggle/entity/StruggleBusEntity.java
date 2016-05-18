package com.bus.struggle.entity;

import java.util.Date;
import java.util.UUID;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "struggleBus")
public class StruggleBusEntity {
	
	private UUID uuid;
	
	private String title;
	
	private String struggle;
	
	private Long riders;
	
	private String busColor;
	
	private Date postDate;

	public Date getPostDate() {
		return postDate;
	}

	public void setPostDate(Date postDate) {
		this.postDate = postDate;
	}

	public UUID getUuid() {
		return uuid;
	}

	public void setUuid(UUID uuid) {
		this.uuid = uuid;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getStruggle() {
		return struggle;
	}

	public void setStruggle(String struggle) {
		this.struggle = struggle;
	}

	public Long getRiders() {
		return riders;
	}

	public void setRiders(Long riders) {
		this.riders = riders;
	}

	public String getBusColor() {
		return busColor;
	}

	public void setBusColor(String busColor) {
		this.busColor = busColor;
	}
}
