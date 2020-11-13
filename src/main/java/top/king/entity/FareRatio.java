package top.king.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class FareRatio implements Serializable, Cloneable {
    /**
     * 主键
     */
    private Integer uniqueId;
    /**
     * 基金代码
     */
    private String fundcode;
    /**
     * 业务类型;根据业务类型来判断申购0还是赎回1
     */
    private String businessType;
    /**
     * 最低申购金额
     */
    private Integer minBalance;
    /**
     * 最高申购金额
     */
    private Integer maxBalance;
    /**
     * 最低持有天数
     */
    private Integer minHoldday;
    /**
     * 最高持有天数
     */
    private Integer maxHoldday;
    /**
     * 费率
     */
    private Double ratio;
}