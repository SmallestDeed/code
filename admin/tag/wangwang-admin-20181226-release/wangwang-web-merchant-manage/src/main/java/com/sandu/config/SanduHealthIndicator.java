package com.sandu.config;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.boot.actuate.health.Status;
import org.springframework.stereotype.Component;

/**
 * CopyRight (c) 2018 Sandu Technology Inc.
 * <p>
 * sandu-wangwang
 *
 * @author Yoco (yocome@gmail.com)
 * @date 2018/3/7 14:00
 */
@Component
public class SanduHealthIndicator implements HealthIndicator {

    @Override
    public Health health() {

        Status status = new Status("200", "web server is ok");
        return Health.up().withDetail("name", "web-merchant-manage").status(status).build();
    }
}
