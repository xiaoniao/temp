package com.kunyao.assistant.web.service.impl;

import com.kunyao.assistant.core.generic.GenericDao;
import com.kunyao.assistant.core.generic.GenericServiceImpl;
import com.kunyao.assistant.core.model.CostItem;
import com.kunyao.assistant.web.dao.CostItemMapper;
import com.kunyao.assistant.web.service.CostItemService;
import java.lang.Integer;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class CostItemServiceImpl extends GenericServiceImpl<CostItem, Integer> implements CostItemService {
    @Resource
    private CostItemMapper costItemMapper;

    public GenericDao<CostItem, Integer> getDao() {
        return costItemMapper;
    }
}
