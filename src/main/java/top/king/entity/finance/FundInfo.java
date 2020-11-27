package top.king.entity.finance;

import lombok.Data;

import java.io.Serializable;

@Data
public class FundInfo implements Serializable,Cloneable {
    /**
     * 主键
     */
    private Integer uniqueId;
    /**
     * 基金名称
     */
    private String fundname;
    /**
     * 基金代码
     */
    private String fundcode;
    /**
     * 投资范围
     */
    private String investRange;
    /**
     * 成立日期
     */
    private Integer setupDate;
    /**
     * 基金经理
     */
    private String director;
    /**
     * 类型
     */
    private String fundType;
    /**
     * 管理人
     */
    private String manger;
    /**
     * 资产规模
     */
    private String asset;
    /**
     * 购买起价
     */
    private String purchase;
    /**
     * 买入确认日
     */
    private String bDate;
    /**
     * 卖出确认日
     */
    private String sDate;
    /**
     * 管理费率
     */
    private String manageRatio;
    /**
     * 托管费率
     */
    private String trusteeRatio;
    /**
     * 销售服务费率
     */
    private String serviceRatio;
    /**
     * 申购费
     */
    private String purchaseFare;
    /**
     * 赎回费
     */
    private String redeemFare;
}