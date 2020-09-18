package com.bcaf.tama.FinalProject.Entity;

public class TripExt extends Trip {

    private Bus bus;
    private Stop sourceStop;
    private Stop destinationStop;



    public TripExt() {

    }

    public TripExt(Trip trip) {
        this.setId(trip.getId());
        this.setBusId(trip.getBusId());
        this.setAgencyId(trip.getAgencyId());
        this.setFare(trip.getFare());
        this.setCreatedDate(trip.getCreatedDate());
        this.setUpdatedDate(trip.getUpdatedDate());
        this.setSourceStopId(trip.getSourceStopId());
        this.setDestStopId(trip.getDestStopId());
        this.setJourneyTime(trip.getJourneyTime());
    }

    public Bus getBus() {
        return bus;
    }

    public void setBus(Bus bus) {
        this.bus = bus;
    }

    public Stop getSourceStop() {
        return sourceStop;
    }

    public void setSourceStop(Stop sourceStop) {
        this.sourceStop = sourceStop;
    }

    public Stop getDestinationStop() {
        return destinationStop;
    }

    public void setDestinationStop(Stop destinationStop) {
        this.destinationStop = destinationStop;
    }
}
