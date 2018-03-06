package com.kunyao.assistant.web.service;

import java.util.List;

import com.kunyao.assistant.core.entity.PageInfo;
import com.kunyao.assistant.core.exception.ServiceException;
import com.kunyao.assistant.core.generic.GenericService;
import com.kunyao.assistant.core.model.Banner;

public interface BannerService extends GenericService<Banner, Integer> {
	
	List<Banner> selectBannerList(Integer currentPage, Integer pagesize, Banner banner) throws ServiceException;
	
	PageInfo selectBannerListCount(Integer pageSize, Banner banner);
}
