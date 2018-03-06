package com.kunyao.assistant.web.service.impl;

import com.kunyao.assistant.core.generic.GenericDao;
import com.kunyao.assistant.core.generic.GenericServiceImpl;
import com.kunyao.assistant.core.model.OrderExchange;
import com.kunyao.assistant.web.dao.OrderExchangeMapper;
import com.kunyao.assistant.web.service.OrderExchangeService;
import java.lang.Integer;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class OrderExchangeServiceImpl extends GenericServiceImpl<OrderExchange, Integer> implements OrderExchangeService {
    @Resource
    private OrderExchangeMapper orderExchangeMapper;

    public GenericDao<OrderExchange, Integer> getDao() {
        return orderExchangeMapper;
    }
}
