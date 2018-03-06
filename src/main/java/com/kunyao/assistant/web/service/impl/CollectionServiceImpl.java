package com.kunyao.assistant.web.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.kunyao.assistant.core.dto.GroupCrossInfoDto;
import com.kunyao.assistant.core.enums.CrossEnum;
import com.kunyao.assistant.core.generic.GenericDao;
import com.kunyao.assistant.core.generic.GenericServiceImpl;
import com.kunyao.assistant.core.model.Collection;
import com.kunyao.assistant.core.model.CrossInfo;
import com.kunyao.assistant.web.dao.CollectionMapper;
import com.kunyao.assistant.web.dao.CrossInfoMapper;
import com.kunyao.assistant.web.service.CollectionService;

@Service
public class CollectionServiceImpl extends GenericServiceImpl<Collection, Integer> implements CollectionService {
	@Resource
	private CollectionMapper collectionMapper;
	
	@Resource
	private CrossInfoMapper crossInfoMapper;

	public GenericDao<Collection, Integer> getDao() {
		return collectionMapper;
	}

	/**
	 * 我的收藏
	 */
	@Override
	public List<GroupCrossInfoDto> findList(Integer userId) {
		List<GroupCrossInfoDto> result = new ArrayList<>();
		List<CrossInfo> crossInfos = collectionMapper.findList(userId, CrossEnum.BookStatus.SUBSCRIBE.getValue());
		if (crossInfos == null) {
			return result;
		}

		// 按城市名称进行分组
		String lastCityName = "";
		GroupCrossInfoDto group = null;
		for (CrossInfo crossInfo : crossInfos) {
			String cityName = crossInfo.getCityName();
			if (!cityName.equals(lastCityName)) {
				group = new GroupCrossInfoDto(cityName);
				result.add(group);
			}
			group.getCrossInfos().add(crossInfo);
			lastCityName = cityName;
		}
		return result;
	}

	/**
	 * 收藏金鹰
	 */
	@Override
	public Collection addCollection(Integer userId, Integer crossUserId) {
		Integer crossInfoId = crossInfoMapper.findCrossInfo(crossUserId).getId();
		Collection collection = new Collection(userId, crossInfoId);
		collectionMapper.insert(collection);
		return collection;
	}

	/**
	 * 是否收藏金鹰
	 */
	@Override
	public Boolean isCollection(Integer userId, Integer crossUserId) {
		Integer crossInfoId = crossInfoMapper.findCrossInfo(crossUserId).getId();
		return collectionMapper.findCollection(userId, crossInfoId) != null;
	}

	/**
	 * 取消收藏
	 */
	@Override
	public void removeCollection(Integer userId, Integer crossUserId) {
		Integer crossInfoId = crossInfoMapper.findCrossInfo(crossUserId).getId();
		collectionMapper.removeCollection(userId, crossInfoId);
	}
}
