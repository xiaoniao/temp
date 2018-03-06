package com.kunyao.assistant.web.service.impl;

import com.kunyao.assistant.core.generic.GenericDao;
import com.kunyao.assistant.core.generic.GenericServiceImpl;
import com.kunyao.assistant.core.model.Department;
import com.kunyao.assistant.web.dao.DepartmentMapper;
import com.kunyao.assistant.web.service.DepartmentService;
import java.lang.Integer;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class DepartmentServiceImpl extends GenericServiceImpl<Department, Integer> implements DepartmentService {
    @Resource
    private DepartmentMapper departmentMapper;

    public GenericDao<Department, Integer> getDao() {
        return departmentMapper;
    }
}
