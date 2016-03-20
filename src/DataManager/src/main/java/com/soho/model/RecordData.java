package com.soho.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name="t_data")
public class RecordData {
    @Id
    @Column(name="data_id")
    private Integer data_id;

    @Column(name="item_id")
    private Integer item_id;

    @Column(name="content")
    private String content_item;

	public Integer getData_id() {
		return data_id;
	}

	public void setData_id(Integer data_id) {
		this.data_id = data_id;
	}

	public Integer getItem_id() {
		return item_id;
	}

	public void setItem_id(Integer item_id) {
		this.item_id = item_id;
	}

	public String getContent_item() {
		return content_item;
	}

	public void setContent_item(String content_item) {
		this.content_item = content_item;
	}

    
}
