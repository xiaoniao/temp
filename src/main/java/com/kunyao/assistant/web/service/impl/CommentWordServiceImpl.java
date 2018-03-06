package com.kunyao.assistant.web.service.impl;

import com.kunyao.assistant.core.generic.GenericDao;
import com.kunyao.assistant.core.generic.GenericServiceImpl;
import com.kunyao.assistant.core.model.CommentWord;
import com.kunyao.assistant.web.dao.CommentWordMapper;
import com.kunyao.assistant.web.service.CommentWordService;
import java.lang.Integer;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class CommentWordServiceImpl extends GenericServiceImpl<CommentWord, Integer> implements CommentWordService {
    @Resource
    private CommentWordMapper commentWordMapper;

    public GenericDao<CommentWord, Integer> getDao() {
        return commentWordMapper;
    }
}
