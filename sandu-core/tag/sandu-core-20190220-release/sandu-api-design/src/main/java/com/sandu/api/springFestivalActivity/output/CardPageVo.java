package com.sandu.api.springFestivalActivity.output;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class CardPageVo implements Serializable {
    private Integer totalCard;

    private Boolean hasTicketRemain;

    private Boolean hasBindingMobile;
}
