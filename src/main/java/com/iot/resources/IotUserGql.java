package com.iot.resources;

import com.iot.model.IotUser;
import io.quarkus.panache.common.Parameters;
import io.smallrye.jwt.build.Jwt;
import org.eclipse.microprofile.graphql.GraphQLApi;
import org.eclipse.microprofile.graphql.Mutation;
import org.eclipse.microprofile.graphql.Query;
import org.eclipse.microprofile.jwt.Claims;

import javax.transaction.Transactional;
import java.util.List;

@GraphQLApi
public class IotUserGql {
    @Query("allIotUsers")
    public List<IotUser> allIotUsers() {
        return IotUser.listAll();
    }

    @Query("allActiveIotUsers")
    public List<IotUser> allActiveIotUsers() {
        return IotUser.list("enabled",true);
    }
    @Query("allInactiveIotUsers")
    public List<IotUser> allInactiveIotUsers() {
        return IotUser.list("enabled",true);
    }

    @Query("iotUsersWithUserId")
    public List<IotUser> iotUsersWithUserId(String userId) {
        return IotUser.find("userId", userId).firstResult();
    }

    @Query("iotUsersWithId")
    public List<IotUser> iotUsersWithId(Long id) {
        return IotUser.findById(id);
    }

    public static void main(String[] args) {
        String token =
                Jwt.issuer("iotgql")
                        .upn("test")
                        .upn("password")
                        .claim(Claims.birthdate.name(), "2001-07-13")
                        .sign();
        System.out.println(token);
    }
    @Query("login")
    public IotUser login(String userId, String password) {
        return IotUser.find("userId = :userId and password = :password",
                Parameters.with("userId",userId).and("password", password)).firstResult();
    }

    @Mutation("logout")
    public IotUser logout(String userToken) {
//        return IotUser.find("userId = :userId and password = :password",
//                Parameters.with("userId",user.userID).and("password", user.password)).firstResult();
        return null;
    }

    @Transactional
    @Mutation("saveIotUser")
    public IotUser saveObject(IotUser iotUser) {
        iotUser.persist();
        return iotUser;
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
