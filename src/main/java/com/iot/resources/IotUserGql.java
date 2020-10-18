package com.iot.resources;

import com.iot.inmemory.Roles;
import com.iot.model.IotUser;
import com.iot.model.User;
import com.iot.util.PBKDF2Encoder;
import graphql.GraphQLException;
import org.eclipse.microprofile.graphql.GraphQLApi;
import org.eclipse.microprofile.graphql.Mutation;
import org.eclipse.microprofile.graphql.Query;
import org.eclipse.microprofile.jwt.JsonWebToken;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;

@GraphQLApi
public class IotUserGql {

    @Inject
    JsonWebToken token;

    @RolesAllowed({ Roles.USER, Roles.ADMIN })
    @Query("allIotUsers")
    public List<IotUser> allIotUsers() {
        return IotUser.listAll();
    }

    @RolesAllowed({ Roles.USER, Roles.ADMIN })
    @Query("allActiveIotUsers")
    public List<IotUser> allActiveIotUsers() {
        return IotUser.list("enabled", true);
    }

    @RolesAllowed({ Roles.USER, Roles.ADMIN })
    @Query("allInactiveIotUsers")
    public List<IotUser> allInactiveIotUsers() {
        return IotUser.list("enabled", true);
    }

    @Inject
    PBKDF2Encoder passwordEncoder;

    @RolesAllowed({ Roles.USER, Roles.ADMIN })
    @Query("iotUsersWithUserId")
    public IotUser iotUsersWithUserId(String userId) {
        return IotUser.find("userId", userId).firstResult();
    }

    @RolesAllowed({ Roles.USER, Roles.ADMIN })
    @Query("iotUsersWithId")
    public IotUser iotUsersWithId(Long id) {
        return IotUser.findById(id);
    }

    @PermitAll
    @Transactional
    @Mutation("saveIotUser")
    public IotUser saveObject(User user) {
        IotUser iotUser = IotUser.find("userId", user.userId).firstResult();
        if (iotUser == null) {
            if (user.password != null) {
                user.password = passwordEncoder.encode(user.password);
            }
            iotUser = new IotUser(user);
            iotUser.persist();
            return iotUser;
        }
        throw new GraphQLException("User id not available");
    }

    @RolesAllowed({ Roles.USER, Roles.ADMIN })
    @Transactional
    @Mutation("updateIotUser")
    public IotUser updateObject(IotUser t) {
        return t.getEntityManager().merge(t);
    }

    @RolesAllowed({ Roles.USER, Roles.ADMIN })
    @Transactional
    @Mutation("deleteIotUser")
    public boolean deleteObject(Long id) {
        return IotUser.deleteById(id);
    }

}
