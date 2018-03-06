package com.kunyao.assistant.web.service;

import com.kunyao.assistant.core.exception.ServiceException;
import com.kunyao.assistant.core.generic.GenericService;
import com.kunyao.assistant.core.model.Share;
import java.lang.Integer;

public interface ShareService extends GenericService<Share, Integer> {

	/** 查询分享来源信息 */
	Share sourceInfo(String code);

	String memberShare(Integer userId) throws ServiceException;
}
