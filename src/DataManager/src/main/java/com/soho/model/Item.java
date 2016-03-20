package com.soho.model;

import javax.persistence.*;

@Entity(name="t_item")
public class Item {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name="item_id")
    private Integer item_id;
    
    @Column(name="name")
    private String name;

    @Column(name="type")
    private Integer type;

    @Column(name="order_num")
    private Integer order_num;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getOrder_num() {
		return order_num;
	}

	public void setOrder_num(Integer order_num) {
		this.order_num = order_num;
	}

	public Integer getItem_id() {
		return item_id;
	}

	public void setItem_id(Integer item_id) {
		this.item_id = item_id;
	}

	@Override
	public String toString() {
		return "Item [item_id=" + item_id + ", name=" + name + ", type=" + type
				+ ", order_num=" + order_num + "]";
	}

    
}


