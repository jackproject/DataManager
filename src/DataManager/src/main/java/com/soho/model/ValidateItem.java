package com.soho.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity(name="t_validate")
public class ValidateItem {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name="validate_id")
    private Integer validate_id;

    @Column(name="item_id")
    private Integer item_id;

    @Column(name="validate_item")
    private String validate_item;

	public Integer getValidate_id() {
		return validate_id;
	}

	public void setValidate_id(Integer validate_id) {
		this.validate_id = validate_id;
	}

	public Integer getItem_id() {
		return item_id;
	}

	public void setItem_id(Integer item_id) {
		this.item_id = item_id;
	}

	public String getValidate_item() {
		return validate_item;
	}

	public void setValidate_item(String validate_item) {
		this.validate_item = validate_item;
	}

	@Override
	public String toString() {
		return "ValidateItem [validate_id=" + validate_id + ", item_id="
				+ item_id + ", validate_item=" + validate_item + "]";
	}

}
