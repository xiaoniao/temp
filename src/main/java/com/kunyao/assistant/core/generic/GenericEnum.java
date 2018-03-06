package com.kunyao.assistant.core.generic;

/**
 * 所有自定义枚举类型实现该接口
 * 
 * @author GeNing
 * @since 2016.4.15
 **/
public interface GenericEnum {

    /**
     * value: 为保存在数据库中的值
     */
    public int getValue();

    /**
     * text : 为前端显示值
     */
    public String getText();

}
