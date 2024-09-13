package com.healinghaven.bigmomma.entity;

import com.fasterxml.jackson.databind.deser.std.DateDeserializers;
import com.healinghaven.bigmomma.utils.DateUtil;
import lombok.Data;

import java.util.Objects;

@Data
public abstract class Entity {
    protected int id;
    protected String dateAdded;

    @Override
    public boolean equals(Object object) {
        if (this.getClass() != object.getClass()) {
            return false;
        }
        Entity entity = (Entity) object;
        return this.id == entity.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, DateUtil.getEpochDate(dateAdded));
    }
}
