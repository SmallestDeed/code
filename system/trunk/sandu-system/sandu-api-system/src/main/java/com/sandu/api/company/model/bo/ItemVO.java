package com.sandu.api.company.model.bo;

import lombok.*;

import java.io.Serializable;

@Setter
@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ItemVO implements Serializable {
    Long id;
    String name;
}
