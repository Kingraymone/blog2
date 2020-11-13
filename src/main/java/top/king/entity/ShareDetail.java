package top.king.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class ShareDetail implements Serializable, Cloneable {
    /**
     * 主键
     */
    private Integer id;
    /**
     * 基金代码
     */
    private String fundcode;
    /**
     * 确认日期
     */
    private Integer dDate;
    /**
     * 用户
     */
    private String username;
    /**
     * 业务类型
     */
    private String businessType;
    /**
     * 确认份额
     */
    private Double shares;
    /**
     * 申请日期
     */
    private Integer dRequest;
    /**
     * 申购费
     */
    private Double purchase;
    /**
     * 赎回费
     */
    private Double redeem;
    /**
     * 申购金额
     */
    private Double pBalance;
    /**
     * 赎回份额
     */
    private Double rFare;
    /**
     * 确认金额
     */
    private Double balances;
}