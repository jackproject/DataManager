package com.soho.model;

import javax.persistence.*;

@Entity(name="t_item")
public class Item {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="item_id")
    private Integer item_id;
    
    @Column(name="name")
    private String name;

    @Column(name="type")
    private Integer type;

    @Column(name="order_num")
    private Integer order_num;

    @Column(name="maxlength")
    private Integer maxlength;

    public Item() {
    	
    }

    public Item(ItemParam itemParam) {
    	item_id = itemParam.getItem_id();
    	name = itemParam.getName();
    	order_num = itemParam.getOrder_num();
    	type = findTypeValue(itemParam.getType());
    	maxlength = itemParam.getMaxlength();
    }

	

	private Integer findTypeValue(String strType) {
		Integer type = 0;

        if (strType.equals("数值")) {
            type = 1;

        } else if (strType.equals("序列")) {
            type = 2;

        } else if (strType.equals("日期")) {
            type = 3;
        }

        return type;
	}
    
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

	public Integer getMaxlength() {
		return maxlength;
	}

	public void setMaxlength(Integer maxlength) {
		this.maxlength = maxlength;
	}

	@Override
	public String toString() {
		return "Item [item_id=" + item_id + ", name=" + name + ", type=" + type
				+ ", order_num=" + order_num + ", maxlength=" + maxlength + "]";
	}

    
}


