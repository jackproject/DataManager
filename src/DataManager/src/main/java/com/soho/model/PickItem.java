package com.soho.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity(name="t_pick_item")
public class PickItem {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="pick_item_id")
    private Integer pick_item_id;

    @Column(name="pick_id")
    private Integer pick_id;

    @Column(name="item_id")
    private Integer item_id;

    @Column(name="choice")
    private Integer choice;
    
    @Column(name="pick_value")
    private String pick_value;

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

	public Integer getItem_id() {
		return item_id;
	}

	public void setItem_id(Integer item_id) {
		this.item_id = item_id;
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

	@Override
	public String toString() {
		return "PickItem [pick_item_id=" + pick_item_id + ", pick_id="
				+ pick_id + ", item_id=" + item_id + ", choice=" + choice
				+ ", pick_value=" + pick_value + "]";
	}
    
    

}
