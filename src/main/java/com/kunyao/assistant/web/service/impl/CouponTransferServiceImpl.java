package com.kunyao.assistant.web.service.impl;

import com.kunyao.assistant.core.generic.GenericDao;
import com.kunyao.assistant.core.generic.GenericServiceImpl;
import com.kunyao.assistant.core.model.CouponTransfer;
import com.kunyao.assistant.web.dao.CouponTransferMapper;
import com.kunyao.assistant.web.service.CouponTransferService;
import java.lang.Integer;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class CouponTransferServiceImpl extends GenericServiceImpl<CouponTransfer, Integer> implements CouponTransferService {
    @Resource
    private CouponTransferMapper couponTransferMapper;

    public GenericDao<CouponTransfer, Integer> getDao() {
        return couponTransferMapper;
    }
}
