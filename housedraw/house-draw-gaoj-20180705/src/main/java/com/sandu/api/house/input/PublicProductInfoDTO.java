package com.sandu.api.house.input;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PublicProductInfoDTO implements Serializable {

    private static final long serialVersionUID = 1935375482119244387L;
    private Long fileId;
    private String uniqueId;
}