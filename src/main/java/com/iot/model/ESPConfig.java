package com.iot.model;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

import javax.persistence.*;

@NamedQuery(name = "ESPConfig.fetchByUserId", query = "SELECT e FROM ESPConfig e WHERE e.iotUser.userId =: userId")
@NamedQuery(name = "ESPConfig.fetchByUser", query = "SELECT e FROM ESPConfig e WHERE e.iotUser.id =: id")
@Table(indexes = {@Index(name = "ESPConfig_idx_uique", columnList = "deviceName,IotUserID", unique = true),
        @Index(name = "ESPConfig_idx_deviceName", columnList = "deviceName", unique = false)})
@Entity
public class ESPConfig extends PanacheEntity {

    public String sid;

    public String password;

    public String ip;

    public String deviceName;

    @ManyToOne
    @JoinColumn(name = "IotUserID", nullable = false)
    public IotUser iotUser;
}
