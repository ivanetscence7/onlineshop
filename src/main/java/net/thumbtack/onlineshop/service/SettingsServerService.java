package net.thumbtack.onlineshop.service;

import net.thumbtack.onlineshop.dto.response.SettingsDtoResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class SettingsServerService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SettingsServerService.class);

    public SettingsDtoResponse getServerSettings(String token) {
        LOGGER.debug("Get server settings by token {}", token);
        return new SettingsDtoResponse();
    }
}
