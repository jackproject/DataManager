package com.soho.model;

import java.io.Serializable;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name="t_data")
public class RecordData implements Serializable {
    @Id
    @Column(name="data_id")
    private Integer data_id;

    @Id
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

	@Override
	public String toString() {
		return "RecordData [data_id=" + data_id + ", item_id=" + item_id
				+ ", content_item=" + content_item + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((content_item == null) ? 0 : content_item.hashCode());
		result = prime * result + ((data_id == null) ? 0 : data_id.hashCode());
		result = prime * result + ((item_id == null) ? 0 : item_id.hashCode());
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
		if (!(obj instanceof RecordData)) {
			return false;
		}
		RecordData other = (RecordData) obj;
		if (content_item == null) {
			if (other.content_item != null) {
				return false;
			}
		} else if (!content_item.equals(other.content_item)) {
			return false;
		}
		if (data_id == null) {
			if (other.data_id != null) {
				return false;
			}
		} else if (!data_id.equals(other.data_id)) {
			return false;
		}
		if (item_id == null) {
			if (other.item_id != null) {
				return false;
			}
		} else if (!item_id.equals(other.item_id)) {
			return false;
		}
		return true;
	}

    
}
