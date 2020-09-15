package top.king.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;

public abstract class BaseService implements Serializable {
    protected Logger bLogger = LoggerFactory.getLogger(this.getClass());
}
