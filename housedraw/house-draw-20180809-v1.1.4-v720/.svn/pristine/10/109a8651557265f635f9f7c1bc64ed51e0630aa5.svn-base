package com.sandu.common.constant.kechuang;

/**
 * 状态：0、待解析(defaults)；1、解析中；2、解析成功；3、已完善
 */
public enum VolumeRoomConstant {
    DEFAULTS(0), PARSING(1), PARSING_SUCCESS(2), PERFECTED(3);

    Integer status;

    VolumeRoomConstant(Integer status) {
        this.status = status;
    }

    public Integer getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return this.getStatus().toString();
    }
}
