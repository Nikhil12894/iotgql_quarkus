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

    // https://www.baeldung.com/role-and-privilege-for-spring-security-registration
//    @ManyToMany(fetch = FetchType.EAGER)
//    @JoinTable(name = "roles_privileges", joinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "privilege_id", referencedColumnName = "id"))
//    public Collection<Privilege> privileges;

}
