package books.microServices.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="PUBLISHERS")
public class Publisher {

	private String name;
	private String address;
	private String phoneNumber;
	private String webSiteUrl;
	
	
	
	public Publisher() {
		
	}

	@Id
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getWebSiteUrl() {
		return webSiteUrl;
	}

	public void setWebSiteUrl(String webSiteUrl) {
		this.webSiteUrl = webSiteUrl;
	}
	
	@Override
	public String toString() {
		return "Publisher [name=" + name + ", address=" + address + ", phoneNumber=" + phoneNumber + ", webSiteUrl="
				+ webSiteUrl + "]";
	}


	
}
