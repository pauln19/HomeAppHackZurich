package com.hackzurich.kidssafety.repository;

import com.hackzurich.kidssafety.model.Device;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface DeviceRepository extends CrudRepository<Device, String> {

    @Override
    Iterable<Device> findAll();

    @Query("select d from Device d where id = :id")
    Device getDevice(@Param("id") String id);
}
