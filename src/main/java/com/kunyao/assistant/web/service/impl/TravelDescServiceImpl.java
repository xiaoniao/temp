package com.kunyao.assistant.web.service.impl;

import com.kunyao.assistant.core.generic.GenericDao;
import com.kunyao.assistant.core.generic.GenericServiceImpl;
import com.kunyao.assistant.core.model.TravelDesc;
import com.kunyao.assistant.web.dao.TravelDescMapper;
import com.kunyao.assistant.web.service.TravelDescService;
import java.lang.Integer;
import java.util.List;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class TravelDescServiceImpl extends GenericServiceImpl<TravelDesc, Integer> implements TravelDescService {
    @Resource
    private TravelDescMapper travelDescMapper;

    public GenericDao<TravelDesc, Integer> getDao() {
        return travelDescMapper;
    }

	@Override
	public List<TravelDesc> queryList() {
		return travelDescMapper.queryList();
	}

}
