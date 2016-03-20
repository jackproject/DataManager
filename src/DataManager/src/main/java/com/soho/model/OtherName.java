package com.soho.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity(name="t_othername")
public class OtherName {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name="othername_id")
    private Integer othername_id;
    
    @Column(name="item_id")
    private Integer item_id;
    
    @Column(name="name")
    private String name;

	public Integer getOthername_id() {
		return othername_id;
	}

	public void setOthername_id(Integer othername_id) {
		this.othername_id = othername_id;
	}

	public Integer getItem_id() {
		return item_id;
	}

	public void setItem_id(Integer item_id) {
		this.item_id = item_id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}


}
