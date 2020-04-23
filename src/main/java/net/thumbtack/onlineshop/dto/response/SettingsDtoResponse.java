package net.thumbtack.onlineshop.dto.response;

import net.thumbtack.onlineshop.config.AppProperties;
import org.springframework.beans.factory.annotation.Autowired;

public class SettingsDtoResponse {

    private int maxNameLength;
    private int minPasswordLength;

    public SettingsDtoResponse() {
        this.maxNameLength = AppProperties.getMaxNameLength();
        this.minPasswordLength = AppProperties.getMinPasswordLength();
    }

    public int getMaxNameLength() {
        return maxNameLength;
    }

    public int getMinPasswordLength() {
        return minPasswordLength;
    }
}

