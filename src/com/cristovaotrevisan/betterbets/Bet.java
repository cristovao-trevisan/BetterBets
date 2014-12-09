package com.cristovaotrevisan.betterbets;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

import android.os.Parcel;
import android.os.Parcelable;
public class Bet implements Parcelable {
	private String daredUserID;
	private String daredUserName;
	private Timestamp startDate;
	private Timestamp endDate;
	private String description;
	private String prize;
	private String userUrl;
	private String daredUserUrl;
	private int userLikes;
	private int daredUserLikes;
	public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Bet createFromParcel(Parcel in ) {
            return new Bet( in );
        }

        public Bet[] newArray(int size) {
            return new Bet[size];
        }
    };
	
	public Bet(){}
	
	public Bet (String daredUserID, String daredUserName, Timestamp startDate, Timestamp endDate, String description, String prize, String userUrl, String daredUserUrl, int userLikes, int daredUserLikes){
		this.daredUserID = daredUserID;
		this.daredUserName = daredUserName;
		this.startDate = startDate;
		this.endDate = endDate;
		this.description = description;
		this.prize = prize;
		this.userUrl = userUrl;
		this.daredUserUrl = daredUserUrl;
		this.userLikes = userLikes;
		this.daredUserLikes = daredUserLikes;
	}
	
	public Bet(Parcel in){
		readFromParcel(in);
	}
	
	@Override
	public String toString(){
		return "[" +daredUserID+", "+daredUserName+", "+startDate.toString()+", "+endDate.toString()
				+", "+description+", "+prize+", "+userUrl+", "+daredUserUrl+", "+userLikes+", "+daredUserLikes+ "]";
	}
	
	public String getDaredUserID() {
		return daredUserID;
	}
	public void setDaredUserID(String daredUserID) {
		this.daredUserID = daredUserID;
	}
	
	public String getDaredUserName() {
		return daredUserName;
	}
	public void setDaredUserName(String daredUserName) {
		this.daredUserName = daredUserName;
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
	public int getDaredUserLikes() {
		return daredUserLikes;
	}
	public void setDaredUserLikes(int daredUserLikes) {
		this.daredUserLikes = daredUserLikes;
	}
	public int getUserLikes() {
		return userLikes;
	}

	public void setUserLikes(int userLikes) {
		this.userLikes = userLikes;
	}

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(daredUserID);
		dest.writeString(daredUserName);
		dest.writeString(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(startDate));
		dest.writeString(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(endDate));
		dest.writeString(description);
		dest.writeString(prize);
		dest.writeString(userUrl);
		dest.writeString(daredUserUrl);
		dest.writeInt(userLikes);
		dest.writeInt(daredUserLikes);
	}
	
	private void readFromParcel(Parcel in ) {
		this.daredUserID = in.readString();
		this.daredUserName = in.readString();
		this.startDate = MainActivity.convertStringToTimestamp(in.readString());
		this.endDate = MainActivity.convertStringToTimestamp(in.readString());
		this.description = in.readString();
		this.prize = in.readString();
		this.userUrl = in.readString();
		this.daredUserUrl = in.readString();
		this.userLikes = in.readInt();
		this.daredUserLikes = in.readInt();
	}
}
