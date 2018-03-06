package com.kunyao.assistant.core.model;

import java.util.List;

import com.kunyao.assistant.core.annotation.ModelMeta;
import com.kunyao.assistant.core.annotation.ModelMeta.ModelFieldType;
import com.kunyao.assistant.core.generic.GenericModel;

public class Province implements GenericModel{
	
	@ModelMeta(fieldType = ModelFieldType.SERIALVERSION)
	private static final long serialVersionUID = 1302071873486665596L;
	
	@ModelMeta(fieldType = ModelFieldType.ID)
	private Integer id;

    /**
     * 名称 */
    private String name;

    /**
     * 状态 0未开通 1已开通 */
    private Integer status;
    
    
    /***********************************V0****************************/
    @ModelMeta(fieldType = ModelFieldType.VO)
    private List<City> cityList;
    
    
	@Override
	public String getTablePrefixName() {
		return "t";
	}

	
	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public Integer getStatus() {
		return status;
	}


	public void setStatus(Integer status) {
		this.status = status;
	}


	public List<City> getCityList() {
		return cityList;
	}


	public void setCityList(List<City> cityList) {
		this.cityList = cityList;
	}

}
