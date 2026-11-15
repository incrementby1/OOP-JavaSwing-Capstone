package org.foodDelivery.model;

import java.util.Objects;
import java.util.UUID;

public abstract class BaseEntity {
    private final UUID id;
    protected BaseEntity(UUID id) { this.id = Objects.requireNonNull(id, "ID cannot be null"); }
    public UUID getId() { return id; }

    //    For hashmap lookup
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    //    In case two separately identical instances but different memory addresses
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BaseEntity that = (BaseEntity) o;
        return Objects.equals(id, that.id);
    }
}
