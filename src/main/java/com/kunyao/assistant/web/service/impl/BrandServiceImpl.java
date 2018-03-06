package com.kunyao.assistant.web.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.kunyao.assistant.core.entity.PageInfo;
import com.kunyao.assistant.core.generic.GenericDao;
import com.kunyao.assistant.core.generic.GenericServiceImpl;
import com.kunyao.assistant.core.model.Brand;
import com.kunyao.assistant.core.utils.ListUtils;
import com.kunyao.assistant.web.dao.BrandMapper;
import com.kunyao.assistant.web.service.BrandService;

@Service
public class BrandServiceImpl extends GenericServiceImpl<Brand, Integer> implements BrandService {
    @Resource
    private BrandMapper brandMapper;

    public GenericDao<Brand, Integer> getDao() {
        return brandMapper;
    }

	@Override
	public List<Brand> selectBrandList(Integer currentPage, Integer pagesize, Brand brand) {
		Integer startpos = null;     //当前页
		
		if (currentPage != null && pagesize != null) {
			currentPage = currentPage >= 1 ? currentPage : 1;
			startpos = PageInfo.countOffset(pagesize, currentPage);
		}
		
		List<Brand> brandList = brandMapper.selectBrandList(startpos, pagesize, brand);
		new ListUtils<Brand>().sort(brandList, "status", "desc");
		return brandList;
	}
	
	@Override
	public PageInfo selectBrandListCount(Integer pageSize, Brand brand) {
		int allRow = brandMapper.selectBrandCount(brand);
		PageInfo page = new PageInfo(allRow, pageSize);
		return page;
	}
}
