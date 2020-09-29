package com.iot.resources;

import com.iot.inmemory.Roles;
import com.iot.model.IotUser;
import com.iot.util.PBKDF2Encoder;
import graphql.GraphQLException;
import org.eclipse.microprofile.graphql.GraphQLApi;
import org.eclipse.microprofile.graphql.Mutation;
import org.eclipse.microprofile.graphql.Query;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;

@GraphQLApi
@RolesAllowed({Roles.USER, Roles.ADMIN})
public class IotUserGql {

    @Query("allIotUsers")
    public List<IotUser> allIotUsers() {
        return IotUser.listAll();
    }

    @Query("allActiveIotUsers")
    public List<IotUser> allActiveIotUsers() {
        return IotUser.list("enabled", true);
    }

    @Query("allInactiveIotUsers")
    public List<IotUser> allInactiveIotUsers() {
        return IotUser.list("enabled", true);
    }

    @Inject
    PBKDF2Encoder passwordEncoder;

    @Query("iotUsersWithUserId")
    public IotUser iotUsersWithUserId(String userId) {
        return IotUser.find("userId", userId).firstResult();
    }

    @Query("iotUsersWithId")
    public IotUser iotUsersWithId(Long id) {
        return IotUser.findById(id);
    }

    @Transactional
    @Mutation("saveIotUser")
    public IotUser saveObject(IotUser iotUser) {
        IotUser user = IotUser.find("userId", iotUser.userId).firstResult();
        if (user == null) {
            if (iotUser.password != null) {
                iotUser.password = passwordEncoder.encode(iotUser.password);
            }
            iotUser.persist();
            return iotUser;
        }
        throw new GraphQLException("User id not available");
    }

    @Transactional
    @Mutation("updateIotUser")
    public IotUser updateObject(IotUser t) {
        return t.getEntityManager().merge(t);
    }

    @Transactional
    @Mutation("deleteIotUser")
    public boolean deleteObject(Long id) {
        return IotUser.deleteById(id);
    }

}
