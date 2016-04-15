package com.soho.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity(name="t_pick")
public class Pick {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="pick_id")
    private Integer pick_id;
    
    @Column(name="pick_name")
    private String pick_name;

	public Integer getPick_id() {
		return pick_id;
	}

	public void setPick_id(Integer pick_id) {
		this.pick_id = pick_id;
	}

	public String getPick_name() {
		return pick_name;
	}

	public void setPick_name(String pick_name) {
		this.pick_name = pick_name;
	}

	@Override
	public String toString() {
		return "Pick [pick_id=" + pick_id + ", pick_name=" + pick_name + "]";
	}

}
