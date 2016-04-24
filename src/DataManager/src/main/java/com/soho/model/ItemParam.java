package com.soho.model;

import java.util.ArrayList;
import java.util.List;


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

    private Integer maxlength;
    
    public ItemParam() {
    }
    
    public ItemParam(Item item) {
    	item_id = item.getItem_id();
    	name = item.getName();
    	order_num = item.getOrder_num();
    	maxlength = item.getMaxlength();
    	setType(findTypeText(item.getType()));
    }

	private String findTypeText(Integer param) {
        String strType = "字符串";

        if (param == ItemType.TYPE_NUMBER) {
            strType = "数值";

        } else if (param == ItemType.TYPE_SYSTEM) {
            strType = "系统项";

        } else if (param == ItemType.TYPE_DATE) {
            strType = "日期";
        }

        return strType;
	}

	// 将 分开的别名列表转换成字符串
	public void modOtherNameByList(List<OtherName> list) {
		other_name = "";
		
		if (list.size() <= 0) {
			return ;
		}
		
		String separator = ", ";
		
		StringBuilder sb = new StringBuilder();  
		
		for (int i = 0; i < list.size(); i++) {
			
			OtherName otherName = list.get(i);

		    sb.append(otherName.getName());  
		    sb.append(separator);  
		}

		other_name = sb.toString().substring(0,
				sb.toString().length() - separator.length());  
	}

	public void modValidateByList(List<ValidateItem> list) {
		validate = "";
		
		if (list.size() <= 0) {
			return ;
		}
		
		
		String separator = ", ";
		
		StringBuilder sb = new StringBuilder();  
		
		for (int i = 0; i < list.size(); i++) {
			
			ValidateItem validateItem = list.get(i);

		    sb.append(validateItem.getValidate_item());  
		    sb.append(separator);  
		}

		
		validate = sb.toString().substring(0,
				sb.toString().length() - separator.length());  
	}

	// 获取别名列表
	public List<OtherName> findListOtherName()
	{
		List<OtherName> list = new ArrayList();

		if (other_name.equals("")) {
			return list;
		}
		
		String[] arr = other_name.split("[,，]");
		for (String s : arr) {
			
			OtherName item = new OtherName();
			
			item.setName(s.trim());
			item.setItem_id(item_id);
			
			list.add(item);
		}
		
		return list;
	}
	
	// 获取验证项列表
	public List<ValidateItem> findListValidateItem()
	{
		List<ValidateItem> list = new ArrayList();
		
		if (validate.equals("")) {
			return list;
		}

		String[] arr = validate.split("[,，]");
		for (String s : arr) {
			
			ValidateItem item = new ValidateItem();
			
			item.setValidate_item(s.trim());
			item.setItem_id(item_id);
			
			list.add(item);
		}
		
		return list;
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

	public Integer getMaxlength() {
		return maxlength;
	}

	public void setMaxlength(Integer maxlength) {
		this.maxlength = maxlength;
	}

    
    
}
