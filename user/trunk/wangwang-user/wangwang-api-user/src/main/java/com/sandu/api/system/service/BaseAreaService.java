package com.sandu.api.system.service;

public interface BaseAreaService {
    String getAreaName(Integer areaId);

    String selectByAreaCode(String provinceCode);
}
