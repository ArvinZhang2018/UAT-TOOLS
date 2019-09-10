package com.fsc.generate.utils;

import common.config.tools.config.ConfigTools3;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class ConfigService {

    public Map<String, String> getAllConfig(String prefix) {
        Map<String, String> configs = ConfigTools3.getAllConfig();
        Map<String, String> res = new HashMap<>();
        configs.entrySet().stream()
                .filter(item -> item.getKey().toUpperCase().startsWith(prefix.toUpperCase()))
                .forEach(item -> {
                    res.put(item.getKey().replace(prefix + ".", ""), item.getValue());
                });
        return res;
    }
}
