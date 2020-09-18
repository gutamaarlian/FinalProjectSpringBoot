package com.bcaf.tama.FinalProject.Controller;

import com.bcaf.tama.FinalProject.Entity.Agency;
import com.bcaf.tama.FinalProject.Entity.User;
import com.bcaf.tama.FinalProject.Request.RegisterRequest;
import com.bcaf.tama.FinalProject.Request.UpdateAgencyRequest;
import com.bcaf.tama.FinalProject.Request.UpdateProfileRequest;
import com.bcaf.tama.FinalProject.Util.AgencyDao;
import com.bcaf.tama.FinalProject.Util.CreateJWT;
import com.bcaf.tama.FinalProject.Util.RoleDao;
import com.bcaf.tama.FinalProject.Util.UserDao;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.Timestamp;

@RestController
@RequestMapping("/api")
public class UserApiController {

    @Autowired
    private UserDao userDao;
    @Autowired
    private RoleDao roleDao;
    @Autowired
    private AgencyDao agencyDao;
    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Bean
    public BCryptPasswordEncoder pass() {
        return new BCryptPasswordEncoder();
    }
    @PostMapping("/createNewAccount")
    public HttpStatus createNewAccount(@RequestBody RegisterRequest registerRequest) {
        User user = new User();
        user.setFirstName(registerRequest.getFirstName());
        user.setLastName(registerRequest.getLastName());
        user.setEmail(registerRequest.getEmail());
        user.setMobileNumber(registerRequest.getContactNumber());
        user.setRoleId(roleDao.findIdByRole("owner").getId());
        user.setCreatedDate(new Timestamp(System.currentTimeMillis()));
        user.setPassword(pass().encode(registerRequest.getPassword()));
        userDao.save(user);

        Agency agency = new Agency();
        agency.setName(registerRequest.getAgencyName());
        agency.setDetails(registerRequest.getAgencyDetail());
        agency.setUserId(user.getId());
        agency.setCreatedDate(new Timestamp(System.currentTimeMillis()));
        agencyDao.save(agency);
        return HttpStatus.OK;
    }


    @PostMapping("/checkEmailUser")
    public String checkEmailUser(@RequestBody RegisterRequest registerRequest) throws JsonProcessingException {
        User user = userDao.findEmailValidation(registerRequest.getEmail());
        if (user == null)
            user = new User();
        ObjectMapper Obj = new ObjectMapper();
        String rs = Obj.writeValueAsString(user);
        return rs;
    }

    @PostMapping("/performLogin")
    public String performLogin(@RequestBody User user) throws JsonProcessingException, InvalidKeySpecException, NoSuchAlgorithmException {
        User userDB = userDao.findEmailValidation(user.getEmail());
        if (user == null)
            user = new User();
        ObjectMapper Obj = new ObjectMapper();
        String rs = Obj.writeValueAsString(user);
        if (user.validatePassword(user.getPassword(), userDB.getPassword())) {
            rs = Obj.writeValueAsString(userDB);
        }
        return rs;
    }

    @PostMapping("/updateUser")
    public String updateUser(@RequestBody UpdateProfileRequest dtoUpdate, HttpServletRequest request ) throws JsonProcessingException {
        HttpSession session = request.getSession(true);
        String userId = (String) session.getAttribute("connectedUser");
        User user = userDao.findById(userId).get();

        user.setFirstName(dtoUpdate.getFirstName());
        user.setLastName(dtoUpdate.getLastName());
        user.setMobileNumber(dtoUpdate.getMobileNumber());
        userDao.save(user);
        ObjectMapper Obj = new ObjectMapper();
        String rs = Obj.writeValueAsString(user);
        return rs;
    }


    @PostMapping("/updatePassword")
    public String updatePass(@RequestBody String password, HttpServletRequest request ) throws JsonProcessingException {
        HttpSession session = request.getSession(true);
        String userId = (String) session.getAttribute("connectedUser");
        User user = userDao.findById(userId).get();
        user.setPassword(bCryptPasswordEncoder.encode(password));
        userDao.save(user);
        ObjectMapper Obj = new ObjectMapper();
        String rs = Obj.writeValueAsString(user);
        return rs;
    }

    @PostMapping("/login")
    public String login(String email, String password) throws JsonProcessingException {
        User user = userDao.findEmailValidation(email);
        Agency agency = agencyDao.findAgencyByUserId(user.getId());
        if(pass().matches(password, user.getPassword())){
            String JWT = new CreateJWT().buildJWT(user, agency.getId());
            ObjectMapper mapper = new ObjectMapper();
            ObjectNode userResponse = mapper.createObjectNode();
            userResponse.put("data", JWT);
            String rs = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(userResponse);
            return rs;
        }else{
            return  "error";
        }
    }

    //---------------------------------------------------------------


    @GetMapping("/v1/user")
    public String getUseraApi(@RequestParam String id) throws JsonProcessingException{

        User user = userDao.findById(id).get();
        ObjectMapper Obj = new ObjectMapper();
        String rs = Obj.writeValueAsString(user);
        return rs;

    }

    @PutMapping("/v1/user")
    public String UpdateUseraApi(@RequestBody User userData) throws JsonProcessingException{
        User user = userDao.findById(userData.getId()).get();
        user.setFirstName(userData.getFirstName());
        user.setLastName(userData.getLastName());
        user.setMobileNumber(userData.getMobileNumber());
        userDao.save(user);
        ObjectMapper Obj = new ObjectMapper();
        String rs = Obj.writeValueAsString(user);
        return rs;

    }

    @PutMapping("/v1/password")
    public String UpdatePasswordApi(@RequestBody User userData) throws JsonProcessingException{
        User user = userDao.findById(userData.getId()).get();
        user.setPassword(bCryptPasswordEncoder.encode(userData.getPassword()));
        userDao.save(user);
        ObjectMapper Obj = new ObjectMapper();
        String rs = Obj.writeValueAsString(user);
        return rs;
    }


    //-------------------------------------------------------------------------------

    //-------------------------------------------------------------------------------

    @PostMapping("/v1/createNewAUser")
    public String createNewUser(@RequestBody User userData) throws JsonProcessingException{
        User user = new User();
        user.setFirstName(userData.getFirstName());
        user.setLastName(userData.getLastName());
        user.setEmail(userData.getEmail());
        user.setMobileNumber(userData.getMobileNumber());
        user.setRoleId(roleDao.findIdByRole("pa").getId());
        user.setCreatedDate(new Timestamp(System.currentTimeMillis()));
        user.setPassword(pass().encode(userData.getPassword()));
        userDao.save(user);
        ObjectMapper Obj = new ObjectMapper();
        String rs = Obj.writeValueAsString(user);
        return rs;
    }

}
