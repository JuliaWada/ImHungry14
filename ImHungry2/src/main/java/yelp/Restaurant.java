package yelp;

import scraping.Result;

public class Restaurant extends Result {
	
	//restaurant class
	// TODO make everything
	String website = "";
	String address = "";
	String phoneNum = "";
	String pricing = "";
	int minsAway = 0;
	
	public Restaurant(String name, String website, String address, String phoneNum,
			String pricing, int minsAway) {
		this.name = name;
		this.website = website;
		this.address = address;
		this.phoneNum = phoneNum;
		this.pricing = pricing;
		this.minsAway = minsAway;
	}

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhoneNum() {
		return phoneNum;
	}

	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}

	public String getPricing() {
		return pricing;
	}

	public void setPricing(String pricing) {
		this.pricing = pricing;
	}

	public int getMinsAway() {
		return minsAway;
	}

	public void setMinsAway(int minsAway) {
		this.minsAway = minsAway;
	}
}
