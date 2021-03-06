package com.iot.model;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

import javax.persistence.*;

import com.iot.inmemory.Roles;

import java.util.LinkedHashSet;
import java.util.Set;

@Table(indexes = { @Index(name = "IotUser_idx_unique", columnList = "userId", unique = true),
        @Index(name = "IotUser_idx_name", columnList = "firstName,lastName", unique = false),
        @Index(name = "IotUser_idx_fullname", columnList = "userId,firstName,lastName", unique = false), })
@Entity
public class IotUser extends PanacheEntity {

    @Column(length = 20, unique = true)
    public String userId;

    public String firstName;

    public String lastName;

    public String password;

    public String email;

    public String authToken;

    public boolean enabled;

    @ManyToOne
    @JoinColumn(name = "IotRoleID", nullable = false)
    public IotRole role;

    @OneToMany(mappedBy = "iotUser")
    public Set<ESPConfig> espconfigList = new LinkedHashSet<>();

    public IotUser() {
        super();
    }

    public IotUser(User user) {
        super();
        this.userId = user.userId;
        this.firstName = user.firstName;
        this.lastName = user.lastName;
        this.email = user.email;
        this.authToken = null;
        this.enabled = false;
        this.role = IotRole.find("roleId", Roles.ADMIN).firstResult();
    }

}
