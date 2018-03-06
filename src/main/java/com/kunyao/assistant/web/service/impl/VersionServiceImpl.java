package com.kunyao.assistant.web.service.impl;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.kunyao.assistant.core.exception.ServiceException;
import com.kunyao.assistant.core.generic.GenericDao;
import com.kunyao.assistant.core.generic.GenericServiceImpl;
import com.kunyao.assistant.core.model.Version;
import com.kunyao.assistant.web.dao.VersionMapper;
import com.kunyao.assistant.web.service.VersionService;

@Service
public class VersionServiceImpl extends GenericServiceImpl<Version, Integer> implements VersionService {
	@Resource
	private VersionMapper versionMapper;

	public GenericDao<Version, Integer> getDao() {
		return versionMapper;
	}

	/**
	 * 版本更新
	 * 
	 * @param platform 平台名称、iOS android
	 * @param appName app名称 cross city
	 */
	@Override
	public Map<String, Object> findInfo(String platform, String appName) {
		Map<String, Object> dataMap = new HashMap<>();
		try {
			Version model = new Version();
			model.setPlatform(platform);
			model.setAppName(appName);
			Version version = findOne(model);

			if (platform.equals("android")) {
				dataMap.put("apk_version_code", version.getApkVersionCode());
				dataMap.put("apk_update", Boolean.valueOf(version.getApkUpdate()));
			}
			dataMap.put("hot_version_code", version.getHotVersionCode());
			dataMap.put("url", version.getUrl());
		} catch (ServiceException e) {
			e.printStackTrace();
		}
		return dataMap;
	}
}
