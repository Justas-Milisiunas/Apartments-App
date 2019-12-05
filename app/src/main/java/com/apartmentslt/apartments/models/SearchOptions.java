package com.apartmentslt.apartments.models;

import java.io.Serializable;

public class SearchOptions implements Serializable {
    private int ownerId;
    private int tenantId;

    public SearchOptions() {
    }

    public SearchOptions(int ownerId, int tenantId) {
        this.ownerId = ownerId;
        this.tenantId = tenantId;
    }

    public void setOwnerId(int ownerId) {
        this.ownerId = ownerId;
    }

    public void setTenantId(int tenantId) {
        this.tenantId = tenantId;
    }

    public int getOwnerId() {
        return ownerId;
    }

    public int getTenantId() {
        return tenantId;
    }
}
