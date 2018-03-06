package com.kunyao.assistant.web.service;

import com.kunyao.assistant.core.generic.GenericService;
import com.kunyao.assistant.core.model.AccountRecord;

import java.lang.Integer;
import java.util.List;
import java.util.Map;

public interface AccountRecordService extends GenericService<AccountRecord, Integer> {

	Map<String, List<AccountRecord>> queryAccountRecord(Integer userId);

	Integer queryListCount(String type, AccountRecord search);

	List<AccountRecord> queryList(String type, Integer currentPage, Integer pagesize, AccountRecord search);
}
