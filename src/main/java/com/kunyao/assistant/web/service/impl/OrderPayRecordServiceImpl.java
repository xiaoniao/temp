package com.kunyao.assistant.web.service.impl;

import com.kunyao.assistant.core.generic.GenericDao;
import com.kunyao.assistant.core.generic.GenericServiceImpl;
import com.kunyao.assistant.core.model.OrderPayRecord;
import com.kunyao.assistant.web.dao.OrderPayRecordMapper;
import com.kunyao.assistant.web.service.OrderPayRecordService;
import java.lang.Integer;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class OrderPayRecordServiceImpl extends GenericServiceImpl<OrderPayRecord, Integer> implements OrderPayRecordService {
    @Resource
    private OrderPayRecordMapper orderPayRecordMapper;

    public GenericDao<OrderPayRecord, Integer> getDao() {
        return orderPayRecordMapper;
    }
}
