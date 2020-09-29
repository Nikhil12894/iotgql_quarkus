package com.iot.model;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@NamedQuery(name = "UIMenu.fetchByRoleId", query = "SELECT e FROM UIMenu e WHERE e.role.roleId =: roleId")
@Entity
@Table(indexes = {@Index(name = "UIMenu_idx", columnList = "menueId,IotRoleID", unique = true)})
public class UIMenu extends PanacheEntity implements Comparable<UIMenu> {

    public String menueId;

    public String pageID;

    public String hrefClass;

    public String customClass;

    public String smallIcon;

    public String bigIcon;

    public Boolean isActive = true;

    public String parentID;

    public String iconTltKey;

    public String friendlyNameTltKey;

    public String url;

    public String helpTextLBL;

    public String textColor = "#ffff";

    public String hideKeyPath;

    public String landingMenuID;// This ill be null for other item - Only Top level menu have id on landing
    // which menu to be opened by default

    public String menuIdentifierText;// Menu_Contacts : Team Center> Contacts

    public Boolean showBreadcrum;

    public Boolean isCustomMenu;

    public Boolean isWalkthruConfigured;

    public String menuType;

    public String customIcon;

    public Boolean showDropIcon;

    public String videoUrl;

    public Integer menueOrder;

    @ManyToOne
    public UIMenu parent;

    @OneToMany(mappedBy = "parent")
    public Collection<UIMenu> children = new ArrayList<>(4);

    @ManyToOne
    @JoinColumn(name = "IotRoleID", nullable = false)
    public IotRole role;

    public List<String> getchildrenIds() {
        ArrayList<String> menueIds = new ArrayList<>();
        if (!this.children.isEmpty()) {
            this.children.forEach(child -> menueIds.add(child.menueId));
        }
        return menueIds;
    }

    @Override
    public int compareTo(UIMenu o) {
        return o.menueOrder < this.menueOrder ? 1 : 0;
    }

}
