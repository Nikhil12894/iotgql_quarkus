package com.iot.model;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

import javax.persistence.Entity;

@Entity
public class Privilege extends PanacheEntity {

    public String name;

//    @ManyToMany(mappedBy = "privileges", fetch = FetchType.EAGER)
//    public Collection<IotRole> roles;

}
