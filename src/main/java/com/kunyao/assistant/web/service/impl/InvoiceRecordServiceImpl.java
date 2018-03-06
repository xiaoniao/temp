package com.kunyao.assistant.web.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.kunyao.assistant.core.entity.PageInfo;
import com.kunyao.assistant.core.enums.InvoiceEnum;
import com.kunyao.assistant.core.exception.ServiceException;
import com.kunyao.assistant.core.generic.GenericDao;
import com.kunyao.assistant.core.generic.GenericServiceImpl;
import com.kunyao.assistant.core.model.CommonInvoice;
import com.kunyao.assistant.core.model.InvoiceRecord;
import com.kunyao.assistant.core.model.Order;
import com.kunyao.assistant.core.model.Recharge;
import com.kunyao.assistant.web.dao.CommonInvoiceMapper;
import com.kunyao.assistant.web.dao.InvoiceRecordMapper;
import com.kunyao.assistant.web.dao.OrderMapper;
import com.kunyao.assistant.web.dao.RechargeMapper;
import com.kunyao.assistant.web.service.InvoiceRecordService;

@Service
public class InvoiceRecordServiceImpl extends GenericServiceImpl<InvoiceRecord, Integer> implements InvoiceRecordService {
	@Resource
	private InvoiceRecordMapper invoiceRecordMapper;

	@Resource
	private OrderMapper orderMapper;

	@Resource
	private RechargeMapper rechargeMapper;

	@Resource
	private CommonInvoiceMapper commonInvoiceMapper;

	public GenericDao<InvoiceRecord, Integer> getDao() {
		return invoiceRecordMapper;
	}

	@Override
	public List<InvoiceRecord> list(Integer userId) {
		return invoiceRecordMapper.list(userId);
	}
	
	@Override
	public Integer listPageCount() {
		return invoiceRecordMapper.listPageCount();
	}

	@Override
	public List<InvoiceRecord> listPage(Integer currentPage, Integer pagesize) {
		List<InvoiceRecord> result = null;
		Integer startpos = null;     //当前页
		if (currentPage != null && pagesize != null) {
			currentPage = currentPage >= 1 ? currentPage : 1;
			startpos = PageInfo.countOffset(pagesize, currentPage);
		}
		result = invoiceRecordMapper.listPage(startpos, pagesize);
		if (result != null) {
			for (InvoiceRecord invoiceRecord : result) {
				invoiceRecord.dto();
			}
		}
		return result;
	}

	@Override
	public InvoiceRecord info(Integer id) {
		return invoiceRecordMapper.info(id);
	}

	@Override
	public void addOrderInvoice(Integer orderId) throws ServiceException {
		InvoiceRecord invoiceRecord = null;
		Order order = orderMapper.findOrderInfo(orderId);
		if (order.getIsNeedInvoice().intValue() == 0) {
			return;
		}
		CommonInvoice commonInvoice = commonInvoiceMapper.findByTitleOrCompanyName(order.getUserId(), order.getInvoiceTitle());
		if (commonInvoice.getType().intValue() == InvoiceEnum.Type.NORMAL.getValue()) {
			// 普通发票
			invoiceRecord = new InvoiceRecord(commonInvoice.getTitle(), order.getInvoicePeople(), order.getInvoiceMobile()
					, order.getInvoiceAddress(), order.getUserId(), "" + orderId);//order|
			insert(invoiceRecord);
		} else {
			// 增值税发票
			invoiceRecord = new InvoiceRecord(commonInvoice.getCompanyName(), commonInvoice.getIdentification(), commonInvoice.getCode()
					, commonInvoice.getAddress(), commonInvoice.getMobile(), commonInvoice.getBankName(), commonInvoice.getBankCard()
					, order.getInvoicePeople(), order.getInvoiceMobile(), order.getInvoiceAddress(), order.getUserId(), "" + orderId);//order|
			insert(invoiceRecord);
		}
	}

	@Override
	public void addRechargeInvoice(Integer rechargeId) throws ServiceException {
		InvoiceRecord invoiceRecord = null;
		Recharge recharge = rechargeMapper.findRechargeInfoById(rechargeId);
		if (recharge.getIsNeedInvoice().intValue() == 0) {
			return;
		}
		CommonInvoice commonInvoice = commonInvoiceMapper.findByTitleOrCompanyName(recharge.getUserId(), recharge.getInvoiceTitle());
		if (commonInvoice.getType().intValue() == InvoiceEnum.Type.NORMAL.getValue()) {
			invoiceRecord = new InvoiceRecord(commonInvoice.getTitle(), recharge.getInvoicePeople()
					, recharge.getInvoiceMobile(), recharge.getInvoiceAddress(), recharge.getUserId(), "" + rechargeId); //recharge|
			insert(invoiceRecord);
		} else {
			invoiceRecord = new InvoiceRecord(commonInvoice.getCompanyName(), commonInvoice.getIdentification()
					, commonInvoice.getCode(), commonInvoice.getAddress(), commonInvoice.getMobile()
					, commonInvoice.getBankName(), commonInvoice.getBankCard(), recharge.getInvoicePeople()
					, recharge.getInvoiceMobile(), recharge.getInvoiceAddress(), recharge.getUserId(), "" + rechargeId); //recharge|
			insert(invoiceRecord);
		}
	}
}
