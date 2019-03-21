package com.sandu.api.springFestivalActivity.output;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class FilmTicketVo implements Serializable {
    /**
     * 影票码
     */
    private String ticketCode;
}
