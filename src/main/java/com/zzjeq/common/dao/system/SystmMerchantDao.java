package com.zzjeq.common.dao.system;

import com.zzjeq.common.domain.sys.TbSystmMerchant;

public interface SystmMerchantDao {
    int deleteByPrimaryKey(Integer merchantId);

    int insert(TbSystmMerchant record);

    int insertSelective(TbSystmMerchant record);

    TbSystmMerchant selectByPrimaryKey(Integer merchantId);

    int updateByPrimaryKeySelective(TbSystmMerchant record);

    int updateByPrimaryKey(TbSystmMerchant record);
}