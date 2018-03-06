package com.kunyao.assistant.web.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.kunyao.assistant.core.entity.PageInfo;
import com.kunyao.assistant.core.exception.ServiceException;
import com.kunyao.assistant.core.generic.GenericDao;
import com.kunyao.assistant.core.generic.GenericServiceImpl;
import com.kunyao.assistant.core.model.Banner;
import com.kunyao.assistant.core.utils.ListUtils;
import com.kunyao.assistant.web.dao.BannerMapper;
import com.kunyao.assistant.web.service.BannerService;

@Service
public class BannerServiceImpl extends GenericServiceImpl<Banner, Integer> implements BannerService {
	
	@Resource
	private BannerMapper bannerMapper;

	public GenericDao<Banner, Integer> getDao() {
		return bannerMapper;
	}

	@Override
	public List<Banner> selectBannerList(Integer currentPage, Integer pagesize, Banner banner) throws ServiceException {
		List<Banner> banners = findList(currentPage, pagesize, banner);
		new ListUtils<Banner>().sort(banners, "status", "desc");
		return banners;
	}

	@Override
	public PageInfo selectBannerListCount(Integer pageSize, Banner banner) {
		Integer allRow = bannerMapper.findCountByCondition(banner);
		PageInfo page = new PageInfo(allRow, pageSize);
		return page;
	}
}
