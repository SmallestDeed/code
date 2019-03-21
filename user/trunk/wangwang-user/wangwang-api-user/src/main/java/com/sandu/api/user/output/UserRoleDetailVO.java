package com.sandu.api.user.output;

import lombok.*;

import java.io.Serializable;
import java.util.List;

@Setter
@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class UserRoleDetailVO implements Serializable {
    Integer id;
    String label;
    Integer parentId;

    List<UserRoleDetailVO> children;
}
