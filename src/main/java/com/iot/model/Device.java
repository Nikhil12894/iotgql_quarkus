package com.iot.model;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Device extends PanacheEntity {
    @ManyToOne
    @JoinColumn(name = "RoomID", nullable = false)
    public Room room;

    public String deviceName;
    public String deviceIconCss;
    public String pin;// D1,D2,D5,D6,D7
    public String type;// "INPUT","OUTPUT"
    public Boolean isOn;// true to on and false to off

}
