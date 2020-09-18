package com.bcaf.tama.FinalProject.Service;

import com.bcaf.tama.FinalProject.Entity.Role;
import com.bcaf.tama.FinalProject.Entity.User;
import com.bcaf.tama.FinalProject.Entity.UserExt;
import com.bcaf.tama.FinalProject.Util.RoleDao;
import com.bcaf.tama.FinalProject.Util.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AccountStatusUserDetailsChecker;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userRepository;
    @Autowired
    private RoleDao roleDao;

    private final AccountStatusUserDetailsChecker detailsChecker = new AccountStatusUserDetailsChecker();

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        System.out.println("user impl");
        User user = userRepository.findEmailValidation(s);
        List<Role> r = roleDao.findNameByRole(user.getRoleId());
        UserExt ut=new UserExt(user,r);
        if (user != null){
            detailsChecker.check(ut);
        }
        return ut;
    }
}