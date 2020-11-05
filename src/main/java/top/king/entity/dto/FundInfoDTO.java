package top.king.entity.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class FundInfoDTO implements Serializable {
    List<String> primaryKey;
}
