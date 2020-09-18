package com.bcaf.tama.FinalProject.Util;

import com.bcaf.tama.FinalProject.Entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleDao extends JpaRepository<Role,String> {

    @Query(nativeQuery = true,value="SELECT r.* FROM tb_role r WHERE r.role like %:role% ")
    Role findIdByRole(@Param("role") String role);

    @Query(nativeQuery = true,value="SELECT r.* FROM tb_role r WHERE r.id like %:id% ")
    List<Role> findNameByRole(@Param("id") String id);
}