package com.example.gtics_lab7_20213852.Repository;

import com.example.gtics_lab7_20213852.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Integer> {
    @Query(nativeQuery = true,value = "select * from users where active is true and authorizedResource=?1")
    List<User> listarUsuarios(Integer recurso);

    @Transactional
    @Modifying
    @Query(nativeQuery = true,value = "update user set name=?1,type=?2,authorizedResource=?3 where userId=?4")
    void actualizarUsuario(String name,String type,Integer idResource,Integer idUsuario);

    @Transactional
    @Modifying
    @Query(nativeQuery = true,value = "update user set active=0")
    void apagadoLogico();
}