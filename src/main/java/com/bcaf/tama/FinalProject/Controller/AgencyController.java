package com.bcaf.tama.FinalProject.Controller;


import com.bcaf.tama.FinalProject.Entity.Agency;
import com.bcaf.tama.FinalProject.Entity.Bus;
import com.bcaf.tama.FinalProject.Request.UpdateAgencyRequest;
import com.bcaf.tama.FinalProject.Util.AgencyDao;
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
public class AgencyController {

    @Autowired
    AgencyDao agencyDao;

    @PostMapping("/getAgency")
    public String getAgency(HttpServletRequest request) throws JsonProcessingException {
        HttpSession session = request.getSession(true);
        String agencyId     = (String) session.getAttribute("agencyId");
        Agency agency       = agencyDao.findById(agencyId).get();
        ObjectMapper Obj    = new ObjectMapper();
        String rs           = Obj.writeValueAsString(agency);
        return rs;
    }




    @PostMapping("/updateAgency")
    public String updateAgency(@RequestBody UpdateAgencyRequest dtoUpdate, HttpServletRequest request ) throws JsonProcessingException {
        HttpSession session = request.getSession(true);
        String agencyId = (String) session.getAttribute("agencyId");
        Agency agency = agencyDao.findById(agencyId).get();
        agency.setName(dtoUpdate.getName());
        agency.setDetails(dtoUpdate.getDetails());
        agencyDao.save(agency);
        ObjectMapper Obj = new ObjectMapper();
        String rs = Obj.writeValueAsString(agency);
        return rs;
    }

    //-------------------------------------------------------------------------//


    @GetMapping("/v1/agency") //for angular
    public String getAgencyById(@RequestParam String id) throws JsonProcessingException {
        String agencyId     = id;
        Agency agency       = agencyDao.findById(agencyId).get();
        ObjectMapper Obj    = new ObjectMapper();
        String rs           = Obj.writeValueAsString(agency);
        return rs;
    }

    @PutMapping("/v1/agency")
    public String updateAgencyApi(@RequestBody Agency agencyDto ) throws JsonProcessingException {
        String agencyId     = agencyDto.getId();
        Agency agency       = agencyDao.findById(agencyId).get();
        agency.setName(agencyDto.getName());
        agency.setDetails(agencyDto.getDetails());
        agencyDao.save(agency);
        ObjectMapper Obj    = new ObjectMapper();
        String rs           = Obj.writeValueAsString(agency);
        return rs;
    }






    //FOR ANDRO
    @PostMapping("agency/createAgency")
    public String createAgency(@RequestBody Agency agencyInp) throws JsonProcessingException {

        Agency agency = new Agency();
        agency.setName(agencyInp.getName());
        agency.setDetails(agencyInp.getDetails());
        agency.setUserId(agencyInp.getUserId());
        agency.setCreatedDate(new Timestamp(System.currentTimeMillis()));
        agencyDao.save(agency);
        ObjectMapper Obj = new ObjectMapper();
        String rs = Obj.writeValueAsString(agency);
        return rs;
    }

    @PostMapping("agency/getAgencyById")
    public String getAgencyByIdAndro(String agencyId) throws JsonProcessingException {
        Agency agency = agencyDao.findById(agencyId).get();
        ObjectMapper Obj = new ObjectMapper();
        String rs = Obj.writeValueAsString(agency);
        return rs;
    }
}
