package com.kunyao.assistant.web.service;

import com.kunyao.assistant.core.generic.GenericService;
import com.kunyao.assistant.core.model.Version;
import java.lang.Integer;
import java.util.Map;

public interface VersionService extends GenericService<Version, Integer> {

	Map<String, Object> findInfo(String platform, String appName);
}
