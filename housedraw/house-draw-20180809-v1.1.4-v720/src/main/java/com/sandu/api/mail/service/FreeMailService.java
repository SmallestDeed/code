package com.sandu.api.mail.service;

import java.util.List;

public interface FreeMailService {
    void push(List<String> targets, String subject, String body);
}
