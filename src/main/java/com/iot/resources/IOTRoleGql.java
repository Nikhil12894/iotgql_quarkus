package com.iot.resources;

import com.iot.model.IotRole;
import org.eclipse.microprofile.graphql.GraphQLApi;
import org.eclipse.microprofile.graphql.Mutation;
import org.eclipse.microprofile.graphql.Query;

import javax.annotation.security.RolesAllowed;
import javax.transaction.Transactional;
import java.util.List;

@GraphQLApi
@RolesAllowed({ "ADMIN" })
public class IOTRoleGql {
    @Query("allIotRole")
    public List<IotRole> allIotUsers() {
        return IotRole.listAll();
    }

    @Query("roleWithRoleId")
    public List<IotRole> roleWithRoleId(String roleId) {
        return IotRole.find("roleId", roleId).firstResult();
    }

    @Query("roleWithId")
    public List<IotRole> roleWithId(Long id) {
        return IotRole.findById(id);
    }

    @Transactional
    @Mutation("createRole")
    public IotRole createRole(IotRole iotRole) {
        iotRole.persist();
        return iotRole;
    }

    @Transactional
    @Mutation("deleteRole")
    public boolean deleteRole(Long id) {
        return IotRole.deleteById(id);
    }
}
