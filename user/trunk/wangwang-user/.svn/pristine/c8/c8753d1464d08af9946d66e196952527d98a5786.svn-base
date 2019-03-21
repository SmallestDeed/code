package com.sandu.api.user.output;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Setter
@Getter
@ToString
public class RoleGroupVO extends ItemVO implements Serializable {
    Integer type;
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Long)) {
            return false;
        }

        return this.id.equals(obj);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
