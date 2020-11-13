package top.king.mapper;

import top.king.entity.ShareDetail;

public interface ShareDetailMapper {
    int insert(ShareDetail record);

    int insertSelective(ShareDetail record);

    void insertShareDetailByDuplicate(ShareDetail shareDetail);
}