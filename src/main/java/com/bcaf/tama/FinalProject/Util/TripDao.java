package com.bcaf.tama.FinalProject.Util;

import com.bcaf.tama.FinalProject.Entity.Bus;
import com.bcaf.tama.FinalProject.Entity.Trip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TripDao extends JpaRepository<Trip, String> {
    @Query(nativeQuery = true,value="SELECT tb.* FROM tb_trip tb WHERE tb.agency_id =:id ")
    List<Trip> findAllTripByAgencyId(@Param("id") String id);
}
