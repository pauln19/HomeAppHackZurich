package com.hackzurich.kidssafety.repository;

import com.hackzurich.kidssafety.model.Device;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface DeviceRepository extends CrudRepository<Device, String>{

    @Override
    Iterable<Device> findAll();

    @Transactional
    @Modifying
    @Query("update Device d set d.childSecurityEnabled = :childSecurityEnabled where d.id = :id")
    void setChildSecurityEnabled(@Param("childSecurityEnabled") boolean childSecurityEnabled, @Param("id") String id);

    @Transactional
    @Modifying
    @Query("update Device d set d.elderlySecurityEnabled = :elderlySecurityEnabled where d.id = :id")
    void setElderlySecurityEnabled(@Param("elderlySecurityEnabled") boolean elderlySecurityEnabled, @Param("id") String id);

    @Modifying
    @Transactional
    @Query("update Device d set d.powerEnabled = :powerEnabled where d.id = :id")
    void setPowerEnabled(@Param("powerEnabled") boolean powerEnabled, @Param("id") String id);

    @Query("select d from Device d where id = :id")
    Device getDevice(@Param("id") String id);
}
