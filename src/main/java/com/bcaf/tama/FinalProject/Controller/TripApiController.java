package com.bcaf.tama.FinalProject.Controller;


import com.bcaf.tama.FinalProject.Entity.Trip;
import com.bcaf.tama.FinalProject.Entity.TripExt;
import com.bcaf.tama.FinalProject.Util.BusDao;
import com.bcaf.tama.FinalProject.Util.StopDao;
import com.bcaf.tama.FinalProject.Util.TripDao;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class TripApiController {
    @Autowired
    private TripDao tripDao;

    @Autowired
    private BusDao busDao;

    @Autowired
    private StopDao stopDao;

    @PostMapping("/getAllTrip")
    public String getAllTrip(HttpServletRequest request) throws JsonProcessingException {
        HttpSession session = request.getSession(true);
        String agencyId = (String) session.getAttribute("agencyId");
        List<Trip> listTrip = tripDao.findAllTripByAgencyId(agencyId);
        List<TripExt> listTripExts = new LinkedList<>();

        for (Trip t : listTrip) {
            TripExt tripExt = new TripExt(t);
            tripExt.setBus(busDao.findById(t.getBusId()).get());
            tripExt.setSourceStop(stopDao.findById(t.getSourceStopId()).get());
            tripExt.setDestinationStop(stopDao.findById(t.getDestStopId()).get());
            listTripExts.add(tripExt);
        }

        ObjectMapper Obj = new ObjectMapper();
        String rs = Obj.writeValueAsString(listTripExts);
        return rs;
    }

    @PostMapping("/updateTrip")
    public String updateTrip(@RequestBody List<TripExt> listTrip) throws JsonProcessingException {

        for (TripExt t : listTrip) {
            Trip newTrip=tripDao.findById(t.getId()).get();
            newTrip.setFare(t.getFare());
            newTrip.setJourneyTime(t.getJourneyTime());
            newTrip.setBusId(t.getBus().getId());
            newTrip.setSourceStopId(t.getSourceStop().getId());
            newTrip.setDestStopId(t.getDestinationStop().getId());
            newTrip.setUpdatedDate(new Timestamp(System.currentTimeMillis()));
            tripDao.save(newTrip);
        }

        ObjectMapper Obj = new ObjectMapper();
        String rs = Obj.writeValueAsString(listTrip);
        return rs;
    }

    @PostMapping("/addTrip")
    public String addTrip(@RequestBody List<TripExt> listTrip, HttpServletRequest request) throws JsonProcessingException {
        HttpSession session = request.getSession(true);
        String agencyId = (String) session.getAttribute("agencyId");
        for (TripExt t : listTrip) {
            Trip newTrip=new Trip();
            newTrip.setAgencyId(agencyId);
            newTrip.setBusId(t.getBus().getId());
            newTrip.setCreatedDate(new Timestamp(System.currentTimeMillis()));
            newTrip.setDestStopId(t.getDestinationStop().getId());
            newTrip.setSourceStopId(t.getSourceStop().getId());
            newTrip.setFare(t.getFare());
            newTrip.setJourneyTime(t.getJourneyTime());
            tripDao.save(newTrip);
        }
        ObjectMapper Obj = new ObjectMapper();
        String rs = Obj.writeValueAsString(listTrip);
        return rs;
    }

    @PostMapping("/deleteTrip")
    public String deleteTrip(@RequestBody List<TripExt> listTrip) throws JsonProcessingException {
        for (TripExt t : listTrip) {
            tripDao.deleteById(t.getId());
        }
        ObjectMapper Obj = new ObjectMapper();
        String rs = Obj.writeValueAsString(listTrip);
        return rs;
    }


    //----------------------------------------------------------------
    //                          ANGULAR API
    //----------------------------------------------------------------

    @GetMapping("/v1/trips")
    public String getAllTripApi(@RequestParam String agencyId) throws JsonProcessingException {

        List<Trip> listTrip = tripDao.findAllTripByAgencyId(agencyId);
        List<TripExt> listTripExts = new LinkedList<>();

        for (Trip t : listTrip) {
            TripExt tripExt = new TripExt(t);
            tripExt.setBus(busDao.findById(t.getBusId()).get());
            tripExt.setSourceStop(stopDao.findById(t.getSourceStopId()).get());
            tripExt.setDestinationStop(stopDao.findById(t.getDestStopId()).get());
            listTripExts.add(tripExt);
        }

        ObjectMapper Obj = new ObjectMapper();
        String rs = Obj.writeValueAsString(listTripExts);
        return rs;
    }

    @PostMapping("/v1/trips")
    public String addTripApi(@RequestParam String agencyId, @RequestBody List<TripExt> listTrip) throws JsonProcessingException {

        for (TripExt t : listTrip) {
            Trip newTrip=new Trip();
            newTrip.setAgencyId(agencyId);
            newTrip.setBusId(t.getBus().getId());
            newTrip.setCreatedDate(new Timestamp(System.currentTimeMillis()));
            newTrip.setDestStopId(t.getDestinationStop().getId());
            newTrip.setSourceStopId(t.getSourceStop().getId());
            newTrip.setFare(t.getFare());
            newTrip.setJourneyTime(t.getJourneyTime());
            tripDao.save(newTrip);
        }

        ObjectMapper Obj = new ObjectMapper();
        String rs = Obj.writeValueAsString(listTrip);
        return rs;

    }




}
