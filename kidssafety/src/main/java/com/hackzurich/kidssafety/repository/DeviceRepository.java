package com.hackzurich.kidssafety.repository;

import com.hackzurich.kidssafety.model.Device;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface DeviceRepository extends CrudRepository<Device, String>{

    @Override
    Iterable<Device> findAll();

    @Modifying
    @Query("update childSecurityEnabled set childSecurityEnabled = :childSecurityEnabled where id = :id")
    int setChildSecurityEnabled(@Param("childSecurityEnabled") boolean childSecurityEnabled, @Param("id") String id);

    @Modifying
    @Query("update elderlySecurityEnabled set elderlySecurityEnabled = :elderlySecurityEnabled where id = :id")
    int setElderlySecurityEnabled(@Param("elderlySecurityEnabled") boolean elderlySecurityEnabled, @Param("id") String id);

    @Modifying
    @Query("update state set state = :state where id = :id")
    int setPowerEnabled(@Param("state") boolean state, @Param("id") String id);

    @Query("select Device device where id = :id")
    Device getDevice(@Param("id") String id);
}
