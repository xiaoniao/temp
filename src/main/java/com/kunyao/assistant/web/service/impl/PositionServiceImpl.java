package com.kunyao.assistant.web.service.impl;

import com.kunyao.assistant.core.generic.GenericDao;
import com.kunyao.assistant.core.generic.GenericServiceImpl;
import com.kunyao.assistant.core.model.Position;
import com.kunyao.assistant.web.dao.PositionMapper;
import com.kunyao.assistant.web.service.PositionService;
import java.lang.Integer;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class PositionServiceImpl extends GenericServiceImpl<Position, Integer> implements PositionService {
    @Resource
    private PositionMapper positionMapper;

    public GenericDao<Position, Integer> getDao() {
        return positionMapper;
    }
}
