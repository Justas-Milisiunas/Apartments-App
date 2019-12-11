package com.apartmentslt.apartments.models;

public class CancelBooking {
    private int rentPeriodId;
    private int tenantId;

    public CancelBooking(int rentPeriodId, int tenantId) {
        this.rentPeriodId = rentPeriodId;
        this.tenantId = tenantId;
    }

    public void setRentPeriodId(int rentPeriodId) {
        this.rentPeriodId = rentPeriodId;
    }

    public void setTenantId(int tenantId) {
        this.tenantId = tenantId;
    }

    public int getRentPeriodId() {
        return rentPeriodId;
    }

    public int getTenantId() {
        return tenantId;
    }
}
