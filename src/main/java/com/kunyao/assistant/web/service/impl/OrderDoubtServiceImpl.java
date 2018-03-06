package com.kunyao.assistant.web.service.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.kunyao.assistant.core.constant.ResultCode;
import com.kunyao.assistant.core.enums.OrderEnum;
import com.kunyao.assistant.core.exception.ServiceException;
import com.kunyao.assistant.core.generic.GenericDao;
import com.kunyao.assistant.core.generic.GenericServiceImpl;
import com.kunyao.assistant.core.model.OrderDoubt;
import com.kunyao.assistant.core.utils.DateUtils;
import com.kunyao.assistant.web.dao.OrderDoubtMapper;
import com.kunyao.assistant.web.service.OrderDoubtService;

@Service
public class OrderDoubtServiceImpl extends GenericServiceImpl<OrderDoubt, Integer> implements OrderDoubtService {

	    @Resource
	    private OrderDoubtMapper orderDoubtMapper;
	    
	   
	    
	    public GenericDao<OrderDoubt, Integer> getDao() {
	        return orderDoubtMapper;
	    }

		@Override
		public List<OrderDoubt> findDoubtBytime(OrderDoubt orderDoubt) {
			
			Calendar calendar = new GregorianCalendar();
			calendar.setTime(DateUtils.parseYMDDate(orderDoubt.getStayDate()));
			calendar.add(Calendar.DATE, 1); 		// 把日期往后增加一天.整数往后推,负数往前移动
			Date torrowDate = calendar.getTime(); 	// 这个时间就是日期往后推一天的结果
			
			
			return orderDoubtMapper.findDoubtBytime(orderDoubt.getOrderId(), orderDoubt.getStatus(), orderDoubt.getStayDate(), DateUtils.parseYMDTime(torrowDate));
		}

		@Override
		public int placeOrder(Integer orderId, String date) {
			int doubtId = 0;
			Calendar calendar = new GregorianCalendar();
			calendar.setTime(DateUtils.parseYMDDate(date));
			calendar.add(Calendar.DATE, 1); 		// 把日期往后增加一天.整数往后推,负数往前移动
			Date torrowDate = calendar.getTime(); 	// 这个时间就是日期往后推一天的结果
			
			OrderDoubt orderDoubt = new OrderDoubt();
			orderDoubt.setOrderId(orderId);
			orderDoubt.setStayDate(DateUtils.parseYMDDate(date));
			orderDoubt.setEndDate(torrowDate);
			
			List<OrderDoubt> orderDoubtList = orderDoubtMapper.findDoubtBytime(orderId, 0, date, DateUtils.parseYMDTime(torrowDate));
			if (orderDoubtList != null && orderDoubtList.size() > 0){
				doubtId = orderDoubtList.get(0).getId();
			}
			return doubtId;
		}

		@Override
		public void createDoubt(Integer userId, Integer orderId, String date) throws ServiceException {
			OrderDoubt doubt = findOne(new OrderDoubt(userId, orderId, DateUtils.parseYMDDate(date), OrderEnum.Doubt.INIT.getValue(), null));
			
			if (doubt != null) {	// 有相同待处理存疑账单，则告知已存在，防止重复提交
				throw new ServiceException(ResultCode.DOUBT_EXIST);
			}
			
			insert(new OrderDoubt(userId, orderId, DateUtils.parseYMDDate(date), OrderEnum.Doubt.INIT.getValue(), new Date()));
		}
}
