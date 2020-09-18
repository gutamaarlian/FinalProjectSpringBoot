package com.bcaf.tama.FinalProject.Controller;

import com.bcaf.tama.FinalProject.Entity.Stop;
import com.bcaf.tama.FinalProject.Util.StopDao;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.LinkedList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class StopApiController {

    @Autowired
    private StopDao stopDao;

    @PostMapping("/getAllStop")
    public String getAllStop(HttpServletRequest request) throws JsonProcessingException {
        List<Stop> listStop = stopDao.findAll();
        if (listStop == null)
            listStop = new LinkedList<>();
        ObjectMapper Obj = new ObjectMapper();
        String rs = Obj.writeValueAsString(listStop);
        return rs;
    }
}
