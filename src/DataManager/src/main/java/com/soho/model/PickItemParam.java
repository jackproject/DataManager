package com.soho.model;

import java.util.List;

import javax.persistence.Column;

public class PickItemParam {
	
    private Integer pick_item_id;

    private Integer pick_id;

    private String item_name;
    
    private Integer choice;
    
    private String pick_value;
    
    public PickItemParam() {
    	
    }

    public PickItemParam(PickItem pickItem) {
    	pick_item_id = pickItem.getPick_item_id();
    	pick_id = pickItem.getPick_id();
    	item_name = "";
    	choice = pickItem.getChoice();
    	pick_value = pickItem.getPick_value();
    }

	public Integer getPick_item_id() {
		return pick_item_id;
	}

	public void setPick_item_id(Integer pick_item_id) {
		this.pick_item_id = pick_item_id;
	}

	public Integer getPick_id() {
		return pick_id;
	}

	public void setPick_id(Integer pick_id) {
		this.pick_id = pick_id;
	}

	public String getItem_name() {
		return item_name;
	}

	public void setItem_name(String item_name) {
		this.item_name = item_name;
	}

	public Integer getChoice() {
		return choice;
	}

	public void setChoice(Integer choice) {
		this.choice = choice;
	}

	public String getPick_value() {
		return pick_value;
	}

	public void setPick_value(String pick_value) {
		this.pick_value = pick_value;
	}
    
}
