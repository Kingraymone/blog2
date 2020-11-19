package top.king.mapper;

import top.king.entity.ShareDetail;

import java.util.List;

public interface ShareDetailMapper {
    int insert(ShareDetail record);

    int insertSelective(ShareDetail record);

    void insertShareDetailByDuplicate(ShareDetail shareDetail);

    List<ShareDetail> selectShareDetailByType(ShareDetail shareDetail);

    void updateShareDetail(ShareDetail shareDetail);
}