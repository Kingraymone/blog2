package top.king.entity.finance;

import lombok.Data;

import java.io.Serializable;

@Data
public class StaticShare implements Serializable, Cloneable {
    /**
     * 主键
     */
    private Integer id;
    /**
     * 基金代码
     */
    private String fundcode;
    /**
     * 用户
     */
    private String username;
    /**
     * 当前份额
     */
    private Double shares;
    /**
     * 累计收益
     */
    private Double profit;
    /**
     * 持仓金额
     */
    private Double balances;
    /**
     * 持仓成本
     */
    private Double netvalue;
}