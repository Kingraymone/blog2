package top.king.mapper;

import top.king.entity.ShareDetail;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface ShareDetailMapper {
    int insert(ShareDetail record);

    int insertSelective(ShareDetail record);

    void insertShareDetailByDuplicate(ShareDetail shareDetail);

    List<ShareDetail> selectShareDetailByType(ShareDetail shareDetail);

    /**
     * 查询当前未处理的申购费
     *
     * @param fundcode
     * @return java.math.BigDecimal
     * @date 2020/11/20
     */
    BigDecimal selectPurchaseFare(String fundcode);

    /**
     * 查询历史收益信息
     *
     * @param fundcode
     * @return java.util.Map
     * @date 2020/11/20
     */
    Map selectHistoryProfit(String fundcode);
    void updateShareDetail(ShareDetail shareDetail);
}