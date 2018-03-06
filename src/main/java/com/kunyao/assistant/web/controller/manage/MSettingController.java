package com.kunyao.assistant.web.controller.manage;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kunyao.assistant.core.feature.dbdiff.DbTools;
import com.kunyao.assistant.core.feature.dbdiff.DbTools.Db;
import com.kunyao.assistant.core.feature.dbdiff.model.Config;
import com.kunyao.assistant.core.feature.dbdiff.model.Table;

@Controller
@RequestMapping("/um/setting")
public class MSettingController {

	@RequestMapping(value = "/dbDiff")
	@ResponseBody
	public Map<String, List<Table>> dbDiff(Config config) {
		return DbTools.diff(
				new Db(config.getLocalMysql(), config.getLocalDbName(), config.getLocalUsername(),
						config.getLocalPassword()),
				new Db(config.getRemoteMysql(), config.getRemoteDbName(), config.getRemoteUsername(),
						config.getRemotePassword()),
				config.getIsHidenSameValue());
	}
}
