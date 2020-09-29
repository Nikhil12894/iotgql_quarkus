package com.iot.model;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Table(indexes = {@Index(name = "Room_roomIdx_user", columnList = "roomId,userID", unique = true),
        @Index(name = "Room_roomIdx", columnList = "roomId", unique = true)

})
@Entity
public class Room extends PanacheEntity {

    public String name;
    @Column(nullable = false)
    public String roomId;
    @OneToOne
    @JoinColumn(name = "ESPWifiModuleID", nullable = false)
    public ESPWifiModule espWifiModule;
    @ManyToOne
    @JoinColumn(name = "userID", nullable = false)
    public IotUser user;

    @OneToMany(mappedBy = "room")
    public List<Device> deviceArray = new ArrayList<>(4);

}
