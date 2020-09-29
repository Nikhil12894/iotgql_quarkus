package com.iot.service;

import com.iot.inmemory.Roles;
import com.iot.util.TokenUtils;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.jwt.Claims;
import org.jboss.logmanager.Logger;
import org.jose4j.jwt.JwtClaims;

import javax.enterprise.context.RequestScoped;
import java.util.Arrays;
import java.util.UUID;

@RequestScoped
public class TokenService {

    public final static Logger LOGGER = Logger.getLogger(TokenService.class.getSimpleName());
    @ConfigProperty(name = "com.ard333.quarkusjwt.jwt.duration")
    public Long duration;
    @ConfigProperty(name = "mp.jwt.verify.issuer")
    public String issuer;

    public String generateUserToken(String email, String username) {
        return generateToken(email, username, Roles.USER);
    }

    public String generateServiceToken(String serviceId, String serviceName) {
        return generateToken(serviceId, serviceName, Roles.SERVICE);
    }

    public String generateToken(String subject, String name, String... roles) {
        try {
            JwtClaims jwtClaims = new JwtClaims();
            jwtClaims.setIssuer(this.issuer); // change to your company
            jwtClaims.setJwtId(UUID.randomUUID().toString());
            jwtClaims.setSubject(subject);
            jwtClaims.setClaim(Claims.upn.name(), subject);
            jwtClaims.setClaim(Claims.preferred_username.name(), name); //add more
            jwtClaims.setClaim(Claims.groups.name(), Arrays.asList(roles));
            jwtClaims.setAudience("using-jwt");
            jwtClaims.setExpirationTimeMinutesInTheFuture(this.duration); // TODO specify how long do you need


            String token = TokenUtils.generateTokenString(jwtClaims);
            LOGGER.info("TOKEN generated: " + token);
            return token;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
