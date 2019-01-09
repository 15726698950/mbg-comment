package com.zzjeq.common.domain.sys;

import java.io.Serializable;

/**
 * 商家分类划分
 *
 * @author liao
 * @date   2019/01/08
 */
public class TbSystmMerchant implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 商家分类主id
     */
    private Integer merchantId;

    /**
     * 商家分类名称
     */
    private String merchantName;

    /**
     * 商家分类编码
     */
    private String merchantCode;

    /**
     * 商家分类父编码
     */
    private String merchantParentCode;

    /**
     * 逻辑删除
     */
    private String isEnable;

    public Integer getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(Integer merchantId) {
        this.merchantId = merchantId;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName == null ? null : merchantName.trim();
    }

    public String getMerchantCode() {
        return merchantCode;
    }

    public void setMerchantCode(String merchantCode) {
        this.merchantCode = merchantCode == null ? null : merchantCode.trim();
    }

    public String getMerchantParentCode() {
        return merchantParentCode;
    }

    public void setMerchantParentCode(String merchantParentCode) {
        this.merchantParentCode = merchantParentCode == null ? null : merchantParentCode.trim();
    }

    public String getIsEnable() {
        return isEnable;
    }

    public void setIsEnable(String isEnable) {
        this.isEnable = isEnable == null ? null : isEnable.trim();
    }
}