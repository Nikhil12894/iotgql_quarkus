package com.iot.resources;

import com.iot.model.ESPConfig;
import org.eclipse.microprofile.graphql.GraphQLApi;
import org.eclipse.microprofile.graphql.Mutation;
import org.eclipse.microprofile.graphql.Query;

import javax.transaction.Transactional;
import java.util.List;

@GraphQLApi
public class ESPConfigGql {

    @Query("allEspConfig")
    public List<ESPConfig> allEspConfig() {
        return ESPConfig.listAll();
    }

    @Query("espConfigForUsr")
    public List<ESPConfig> espConfigForUsr(String usrId) {
        return ESPConfig.list("iotUser.userId",usrId);
    }

    @Query("espConfigForUsrId")
    public List<ESPConfig> espConfigForUsrId(Long id) {
        return ESPConfig.list("iotUser.id",id);
    }

    @Transactional
    @Mutation("saveEspConfig")
    public ESPConfig saveEspConfig(ESPConfig t) {
        t.persist();
        return t;
    }

    @Transactional
    @Mutation("updateEspConfig")
    public ESPConfig updateEspConfig(ESPConfig t) {
        return t.getEntityManager().merge(t);
    }

    @Transactional
    @Mutation("deleteEspConfig")
    public boolean deleteEspConfig(Long id) {
        return ESPConfig.deleteById(id);
    }
}
