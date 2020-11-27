package top.king.entity.finance;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class NetValue implements Serializable, Cloneable {
    /**
     * 主键
     */
    private Integer uniqueId;
    /**
     * 基金代码
     */
    private String fundcode;
    /**
     * 净值日期
     */
    private Integer cDate;
    /**
     * 单位净值
     */
    private BigDecimal netvalue;
    /**
     * 净值增长率
     */
    private BigDecimal increaseratio;
}