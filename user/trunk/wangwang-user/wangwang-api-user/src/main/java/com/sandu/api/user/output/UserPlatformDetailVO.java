package com.sandu.api.user.output;

import lombok.*;

import java.io.Serializable;
import java.util.List;

@Setter
@Getter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserPlatformDetailVO implements Serializable {
    Integer userId;
    List<Long> platforms;
}
