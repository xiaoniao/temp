package com.kunyao.assistant.web.controller.member;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kunyao.assistant.core.dto.GroupCrossInfoDto;
import com.kunyao.assistant.core.dto.Result;
import com.kunyao.assistant.core.dto.ResultFactory;
import com.kunyao.assistant.core.model.Collection;
import com.kunyao.assistant.web.service.CollectionService;

@Controller
@RequestMapping(value = "/mc/collection")
@ResponseBody
public class CollectionController {

	@Resource
	private CollectionService collectionService;

	/**
	 * 收藏金鹰列表
	 */
	@RequestMapping(value = "/list")
	public Result collectionList(Integer userId) {
		List<GroupCrossInfoDto> list = collectionService.findList(userId);
		return ResultFactory.createJsonSuccess(list);
	}

	/**
	 * 是否收藏金鹰
	 */
	@RequestMapping(value = "/isCollection")
	public Result isCollection(Integer userId, Integer crossUserId) {
		return ResultFactory.createJsonSuccess(collectionService.isCollection(userId, crossUserId));
	}
	
	/**
	 * 收藏金鹰
	 */
	@RequestMapping(value = "/addCollection")
	@ResponseBody
	public Result addCollection(Integer userId, Integer crossUserId) {
		Collection collection = collectionService.addCollection(userId, crossUserId);
		return ResultFactory.createJsonSuccess(collection);
	}
	
	/**
	 * 取消收藏金鹰
	 */
	@RequestMapping(value = "/removeCollection")
	public Result removeCollection(Integer userId, Integer crossUserId) {
		try {
			collectionService.removeCollection(userId, crossUserId);
		} catch (Exception e) {
			return ResultFactory.createJsonSuccess(false);
		}
		return ResultFactory.createJsonSuccess(true);
	}
}
