package com.iot.resources;

import com.iot.inmemory.Roles;
import com.iot.model.IotUser;
import com.iot.service.TokenService;
import com.iot.util.PBKDF2Encoder;
import io.quarkus.panache.common.Parameters;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/user")
public class AuthenticationREST {

    @Inject
    PBKDF2Encoder passwordEncoder;
    @Inject
    TokenService tokenService;

    @PermitAll
    @POST
    @Path("/login")
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public Response login(AuthRequest authRequest) {
        IotUser u = IotUser.find("userId", authRequest.username).firstResult();
        if (u != null && u.password.equals(passwordEncoder.encode(authRequest.password))) {
            try {
                return Response.ok(new AuthResponse(tokenService.generateToken(u.userId, u.password, u.role.roleId)))
                        .build();
            } catch (Exception e) {
                return Response.status(Response.Status.UNAUTHORIZED).build();
            }
        } else {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
    }

    @PermitAll
    @POST
    @Path("/logout")
    @Produces(MediaType.APPLICATION_JSON)
    public Response logout(AuthRequest authRequest) {
        IotUser u = IotUser
                .find("userId = :userId and password = :password",
                        Parameters.with("userId", authRequest.username).and("password", authRequest.password))
                .firstResult();
        if (u != null && u.password.equals(passwordEncoder.encode(authRequest.password))) {
            try {
                return Response.ok(new AuthResponse(tokenService.generateUserToken(u.userId, u.password))).build();
            } catch (Exception e) {
                return Response.status(Response.Status.UNAUTHORIZED).build();
            }
        } else {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
    }

    @GET
    @Path("roles-allowed")
    @RolesAllowed({ Roles.USER, Roles.ADMIN })
    @Produces(MediaType.TEXT_PLAIN)
    public String helloRolesAllowed() {
        return "hello world";
    }

}
