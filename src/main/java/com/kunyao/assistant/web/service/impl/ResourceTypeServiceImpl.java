package com.kunyao.assistant.web.service.impl;

import com.kunyao.assistant.core.generic.GenericDao;
import com.kunyao.assistant.core.generic.GenericServiceImpl;
import com.kunyao.assistant.core.model.ResourceType;
import com.kunyao.assistant.web.dao.ResourceTypeMapper;
import com.kunyao.assistant.web.service.ResourceTypeService;
import java.lang.Integer;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class ResourceTypeServiceImpl extends GenericServiceImpl<ResourceType, Integer> implements ResourceTypeService {
    @Resource
    private ResourceTypeMapper resourceTypeMapper;

    public GenericDao<ResourceType, Integer> getDao() {
        return resourceTypeMapper;
    }
}
