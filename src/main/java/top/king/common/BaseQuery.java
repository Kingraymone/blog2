package top.king.common;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class BaseQuery<T> implements Serializable {
    private Integer pageNum = 1;
    private Integer pageSize = 10;
    T data;

    public BaseQuery(T data) {
        this.data = data;
    }
}
