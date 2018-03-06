package com.kunyao.assistant.web.service.impl;

import com.kunyao.assistant.core.generic.GenericDao;
import com.kunyao.assistant.core.generic.GenericServiceImpl;
import com.kunyao.assistant.core.model.OrderRefund;
import com.kunyao.assistant.web.dao.OrderRefundMapper;
import com.kunyao.assistant.web.service.OrderRefundService;
import java.lang.Integer;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class OrderRefundServiceImpl extends GenericServiceImpl<OrderRefund, Integer> implements OrderRefundService {
    @Resource
    private OrderRefundMapper orderRefundMapper;

    public GenericDao<OrderRefund, Integer> getDao() {
        return orderRefundMapper;
    }
}
