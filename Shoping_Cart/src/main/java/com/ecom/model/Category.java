package com.ecom.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class Category {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private int id;
	
private String name;
	
private String imageName;
	
private Boolean isActive;



public int getId() {
	return id;
}

public void setId(int id) {
	this.id = id;
}

public String getImageName() {
	return imageName;
}

public void setImageName(String imageName) {
	this.imageName = imageName;
}

public String getName() {
	return name;
}

public void setName(String name) {
	this.name = name;
}

public Boolean getIsActive() {
	return isActive;
}

public void setIsActive(Boolean isActive) {
	this.isActive = isActive;
}



	}

