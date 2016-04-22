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
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((choice == null) ? 0 : choice.hashCode());
		result = prime * result + ((item_id == null) ? 0 : item_id.hashCode());
		result = prime * result + ((pick_id == null) ? 0 : pick_id.hashCode());
		result = prime * result
				+ ((pick_item_id == null) ? 0 : pick_item_id.hashCode());
		result = prime * result
				+ ((pick_value == null) ? 0 : pick_value.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof PickItem)) {
			return false;
		}
		PickItem other = (PickItem) obj;
		if (choice == null) {
			if (other.choice != null) {
				return false;
			}
		} else if (!choice.equals(other.choice)) {
			return false;
		}
		if (item_id == null) {
			if (other.item_id != null) {
				return false;
			}
		} else if (!item_id.equals(other.item_id)) {
			return false;
		}
		if (pick_id == null) {
			if (other.pick_id != null) {
				return false;
			}
		} else if (!pick_id.equals(other.pick_id)) {
			return false;
		}
		if (pick_item_id == null) {
			if (other.pick_item_id != null) {
				return false;
			}
		} else if (!pick_item_id.equals(other.pick_item_id)) {
			return false;
		}
		
		return true;
	}

	@Override
	public String toString() {
		return "PickItem [pick_item_id=" + pick_item_id + ", pick_id="
				+ pick_id + ", item_id=" + item_id + ", choice=" + choice
				+ ", pick_value=" + pick_value + "]";
	}
    
    

}
