package com.bcaf.tama.FinalProject.Controller;

import com.bcaf.tama.FinalProject.Entity.Agency;
import com.bcaf.tama.FinalProject.Entity.User;
import com.bcaf.tama.FinalProject.Util.AgencyDao;
import com.bcaf.tama.FinalProject.Util.UserDao;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
public class IndexController {


    @Autowired
    AgencyDao agencyDao;

    @Autowired
    UserDao userDao;

    @GetMapping
    @RequestMapping({"/","/index","/dashboard"})
    public String viewDashboard(HttpServletRequest request, HttpServletResponse response, Model model) {
        HttpSession session = request.getSession(true);
        String userId=(String)session.getAttribute("connectedUser");
        System.out.println(userId);
        User user = userDao.findById(userId).get();
        model.addAttribute("user", user);
        return "index";
    }



    @GetMapping
    @RequestMapping({"/buses"})
    public String viewBus(Model model, HttpServletRequest request) {
        HttpSession session = request.getSession(true);
        String userId = (String) session.getAttribute("connectedUser");
        User user = userDao.findById(userId).get();
        model.addAttribute("user", user);
        return "busView";
    }

    @GetMapping
    @RequestMapping({"/trips"})
    public String viewTrip(Model model, HttpServletRequest request) {
        HttpSession session = request.getSession(true);
        String userId = (String) session.getAttribute("connectedUser");
        User user = userDao.findById(userId).get();
        model.addAttribute("user", user);
        return "tripView";
    }


    @GetMapping
    @RequestMapping({"/agency"})
    public String viewAgency(Model model, HttpServletRequest request) {

        HttpSession session = request.getSession(true);
        String agencyId = (String) session.getAttribute("agencyId");
        String userId = (String) session.getAttribute("connectedUser");
        User user = userDao.findById(userId).get();
        model.addAttribute("user", user);
        Agency agency = agencyDao.findById(agencyId).get();
        model.addAttribute("agency", agency);

        return "agencyView";

    }

    @GetMapping
    @RequestMapping({"/profile"})
    public String viewProfile(HttpServletRequest request, Model model) {
        HttpSession session = request.getSession(true);
        String userId = (String) session.getAttribute("connectedUser");
        User user = userDao.findById(userId).get();
        model.addAttribute("user", user);
        return "profileView";
    }


}
