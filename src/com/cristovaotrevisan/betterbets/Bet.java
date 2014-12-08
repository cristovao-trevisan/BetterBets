package com.cristovaotrevisan.betterbets;

import java.sql.Timestamp;


public class Bet {
	private String daredUserID;
	private Timestamp startDate;
	private Timestamp endDate;
	private String description;
	private String prize;
	private String userUrl;
	private String daredUserUrl;
	
	public Bet(){}
	
	public Bet (String daredUserID, Timestamp startDate, Timestamp endDate, String description, String prize, String userUrl, String daredUserUrl){
		this.daredUserID = daredUserID;
		this.startDate = startDate;
		this.endDate = endDate;
		this.description = description;
		this.prize = prize;
		this.userUrl = userUrl;
		this.daredUserUrl = daredUserUrl;
	}
	
	public String getDaredUserID() {
		return daredUserID;
	}
	public void setDaredUserID(String daredUserID) {
		this.daredUserID = daredUserID;
	}
	public Timestamp getStartDate() {
		return startDate;
	}
	public void setStartDate(Timestamp startDate) {
		this.startDate = startDate;
	}
	public Timestamp getEndDate() {
		return endDate;
	}
	public void setEndDate(Timestamp endDate) {
		this.endDate = endDate;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getPrize() {
		return prize;
	}
	public void setPrize(String prize) {
		this.prize = prize;
	}
	public String getUserUrl() {
		return userUrl;
	}
	public void setUserUrl(String userUrl) {
		this.userUrl = userUrl;
	}
	public String getDaredUserUrl() {
		return daredUserUrl;
	}
	public void setDaredUserUrl(String daredUserUrl) {
		this.daredUserUrl = daredUserUrl;
	}
	
	public int userVideoLikes(){
//		"http://gdata.youtube.com/feeds/api/videos/J71IDnSj81g?v=2&alt=jsonc";
			
		return 0;
	}
}
