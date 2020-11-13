package top.king.entity.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class FareRatioDTO implements Serializable {
    private String fundcode;
    private List<String> businessType;
    private List<String> primaryKey;
}