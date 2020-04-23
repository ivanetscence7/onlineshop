package net.thumbtack.onlineshop.controller;

import net.thumbtack.onlineshop.config.AppProperties;
import net.thumbtack.onlineshop.dto.response.SettingsDtoResponse;
import net.thumbtack.onlineshop.service.SettingsServerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;

@RestController
@RequestMapping("/api")
public class SettingsController {
    private SettingsServerService settingsService;

    @Autowired
    public SettingsController(SettingsServerService settingsService) {
        this.settingsService = settingsService;
    }

    @GetMapping(value = "/settings", produces = MediaType.APPLICATION_JSON_VALUE)
    public SettingsDtoResponse getServerSettings(@CookieValue(value = AppProperties.COOKIE_NAME, required = false) Cookie cookie) {
        return settingsService.getServerSettings(cookie.getValue());
    }
}
