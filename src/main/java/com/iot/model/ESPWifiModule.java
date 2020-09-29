package com.iot.model;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

import javax.persistence.*;

@Table(indexes = {@Index(name = "ESPWifiModule_idx_name", columnList = "name", unique = true)})
@Entity
public class ESPWifiModule extends PanacheEntity {

    public String name;
    @OneToOne
    @JoinColumn(name = "ESPConfigsID", nullable = true)
    public ESPConfig espConfigs;

    @ManyToOne
    @JoinColumn(name = "userID", nullable = false)
    public IotUser user;

    @OneToOne(mappedBy = "espWifiModule")
    public Room room;
}
