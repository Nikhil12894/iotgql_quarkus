package com.iot.model;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;

@Table(indexes = {@Index(name = "IotRole_idx", columnList = "roleId", unique = true)})
@Entity
public class IotRole extends PanacheEntity {

    public String roleId;

    public String name;

    public Boolean canDoAction;

}
