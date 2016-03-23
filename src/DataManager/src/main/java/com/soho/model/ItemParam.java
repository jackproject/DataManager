package com.soho.model;

/**
 * @author Administrator
 * 用于接收前端传入的数据
 */
public class ItemParam {

    private Integer item_id;

    private String name;
    
    private String other_name;

    private String type;

    private String validate;
    
    private Integer order_num;

    public ItemParam() {
    }
    
    public ItemParam(Item item) {
    	item_id = item.getItem_id();
    	name = item.getName();
    	order_num = item.getOrder_num();
    	setType(findTypeText(item.getType()));
    }

	private String findTypeText(Integer param) {
        String strType = "字符串";

        if (param == 1) {
            strType = "数值";

        } else if (param == 2) {
            strType = "序列";

        } else if (param == 3) {
            strType = "日期";
        }

        return strType;
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

	public String getOther_name() {
		return other_name;
	}

	public void setOther_name(String other_name) {
		this.other_name = other_name;
	}


	public String getValidate() {
		return validate;
	}

	public void setValidate(String validate) {
		this.validate = validate;
	}

	public Integer getOrder_num() {
		return order_num;
	}

	public void setOrder_num(Integer order_num) {
		this.order_num = order_num;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

    
    
}
