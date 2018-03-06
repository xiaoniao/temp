package com.kunyao.assistant.web.service.impl;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.kunyao.assistant.core.constant.ResultCode;
import com.kunyao.assistant.core.enums.BaseEnum;
import com.kunyao.assistant.core.enums.OrderEnum;
import com.kunyao.assistant.core.exception.ServiceException;
import com.kunyao.assistant.core.generic.GenericDao;
import com.kunyao.assistant.core.generic.GenericServiceImpl;
import com.kunyao.assistant.core.model.MemberInfo;
import com.kunyao.assistant.core.model.Order;
import com.kunyao.assistant.core.model.Share;
import com.kunyao.assistant.core.utils.StringUtils;
import com.kunyao.assistant.web.dao.MemberInfoMapper;
import com.kunyao.assistant.web.dao.OrderMapper;
import com.kunyao.assistant.web.dao.ShareMapper;
import com.kunyao.assistant.web.service.ShareService;

@Service
public class ShareServiceImpl extends GenericServiceImpl<Share, Integer> implements ShareService {
    @Resource
    private ShareMapper shareMapper;
    
    @Resource
    private OrderMapper orderMapper;
    
    @Resource
    private MemberInfoMapper memberInfoMapper;

    public GenericDao<Share, Integer> getDao() {
        return shareMapper;
    }

	@Override
	public Share sourceInfo(String code) {
		return shareMapper.sourceInfo(code);
	}

	@Override
	public String memberShare(Integer userId) throws ServiceException {
		MemberInfo member = memberInfoMapper.findMemberInfo(userId);
		if (member == null) throw new ServiceException(ResultCode.NO_DATA);
		
		Share share = shareMapper.findShareByShareUserId(userId);
		if (share != null)
			return share.getCode();
		
		Order model = new Order();
		model.setUserId(userId);
		model.setStatus(OrderEnum.Status.FINISH.getValue());
		Map<String, Object> findOrder = orderMapper.findOne(model, null);
		
		Share newShare = new Share();
		newShare.setShareUserId(userId);
		newShare.setIsOrder(findOrder == null ? BaseEnum.Status.BASE_STATUS_DISABLED.getValue() : BaseEnum.Status.BASE_STATUS_ENABLE.getValue());
		String code = StringUtils.getRandomString(4);
		newShare.setCode(code);
		shareMapper.insert(newShare);
		
		return code;
	}
}
