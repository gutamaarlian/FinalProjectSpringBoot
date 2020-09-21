package com.bcaf.tama.FinalProject.Controller;


import com.bcaf.tama.FinalProject.Entity.Bus;
import com.bcaf.tama.FinalProject.Util.BusDao;
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
public class BusApiController {

    @Autowired
    private BusDao busDao;


    @PostMapping("/getAllBus")
    public String getAllBus(HttpServletRequest request) throws JsonProcessingException {
        HttpSession session = request.getSession(true);
        String agencyId = (String) session.getAttribute("agencyId");
        List<Bus> listBus = busDao.findAllBusByAgencyId(agencyId);
        if (listBus == null)
            listBus = new LinkedList<>();
        ObjectMapper Obj = new ObjectMapper();
        String rs = Obj.writeValueAsString(listBus);
        return rs;
    }

    @PostMapping("/updateBus")
    public String updateBus(@RequestBody List<Bus> listBus) throws JsonProcessingException {
        for (Bus b : listBus) {
            b.setUpdatedDate(new Timestamp(System.currentTimeMillis()));
            busDao.save(b);
        }
        ObjectMapper Obj = new ObjectMapper();
        String rs = Obj.writeValueAsString(listBus);
        return rs;
    }

    @PostMapping("/addBus")
    public String addBus(@RequestBody List<Bus> listBus,HttpServletRequest request) throws JsonProcessingException {
        HttpSession session = request.getSession(true);
        String agencyId = (String) session.getAttribute("agencyId");
        for (Bus b : listBus) {
            b.setAgencyId(agencyId);
            b.setCreatedDate(new Timestamp(System.currentTimeMillis()));
            busDao.save(b);
        }
        ObjectMapper Obj = new ObjectMapper();
        String rs = Obj.writeValueAsString(listBus);
        return rs;
    }

    @PostMapping("/deleteBus")
    public String deleteBus(@RequestBody List<Bus> listBus) throws JsonProcessingException {
        for (Bus b : listBus) {
            busDao.delete(b);
        }
        ObjectMapper Obj = new ObjectMapper();
        String rs = Obj.writeValueAsString(listBus);
        return rs;
    }


    //----------------------------------------------------------------------------------------//
    //                                API FOR ANGULAR
    //----------------------------------------------------------------------------------------//

    @GetMapping("/v1/buses")
    public String getBusesByAgencyId(@RequestParam String agencyId) throws JsonProcessingException {
        List<Bus> listBus = busDao.findAllBusByAgencyId(agencyId);
        if (listBus == null)
            listBus = new LinkedList<>();
        ObjectMapper Obj = new ObjectMapper();
        String rs = Obj.writeValueAsString(listBus);
        return rs;
    }

    @PostMapping("/v1/buses")
    public String addBusApi(@RequestParam String agencyId, @RequestBody List<Bus> listBus) throws JsonProcessingException {
        for (Bus b : listBus) {
            b.setAgencyId(agencyId);
            b.setCreatedDate(new Timestamp(System.currentTimeMillis()));
            busDao.save(b);
        }
        ObjectMapper Obj = new ObjectMapper();
        String rs = Obj.writeValueAsString(listBus);
        return rs;
    }


    //FOR ANDRO

    @PostMapping("bus/getAllBusByAgencyId")
    public String getAllBusByAgencyId(String agencyId) throws JsonProcessingException {
        List<Bus> listBus = busDao.findAllBusByAgencyId(agencyId);
        if (listBus == null)
            listBus = new LinkedList<>();
        ObjectMapper Obj = new ObjectMapper();
        String rs = Obj.writeValueAsString(listBus);
        return rs;
    }

    @PostMapping("bus/getBusById")
    public String getBusById(String busId) throws JsonProcessingException {
        Bus bus = busDao.findById(busId).get();
        ObjectMapper Obj = new ObjectMapper();
        String rs = Obj.writeValueAsString(bus);
        return rs;
    }
}
