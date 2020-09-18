package com.bcaf.tama.FinalProject.Util;


import com.bcaf.tama.FinalProject.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao extends JpaRepository<User,String> {

    @Query(nativeQuery = true,value="SELECT u.* FROM tb_user u WHERE u.email =:email ")
    User findEmailValidation(@Param("email") String email);
}