package org.idsp.smartengine.dao;

import com.alibaba.smart.framework.engine.service.param.query.DeploymentInstanceQueryParam;
import org.hswebframework.ezorm.rdb.executor.SqlRequests;
import org.hswebframework.ezorm.rdb.executor.SyncSqlExecutor;
import org.idsp.smartengine.entity.DeploymentInstanceEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class DeploymentInstanceDAO {


    private ZoneId zoneId = ZoneId.systemDefault();


    private String getSelectSql = new String("select id, gmt_create, gmt_modified,process_definition_id,process_definition_version,\n" +
            "        process_definition_type,process_definition_code,process_definition_name,process_definition_desc,process_definition_content,\n" +
            "        deployment_user_id,deployment_status,logic_status" +
            " from se_deployment_instance");
    @Autowired
    private SyncSqlExecutor defaultJdbcExecutor;
    public DeploymentInstanceEntity findOne(@Param("id") Long id){

        List<DeploymentInstanceEntity> list=new ArrayList<>();
        defaultJdbcExecutor.select(new StringBuilder(getSelectSql).append("  where id=?  ").toString(),id)
                .forEach(stringObjectMap -> {
                    DeploymentInstanceEntity activityInstanceEntity= new DeploymentInstanceEntity();

                    list.add(convertMapToBean(activityInstanceEntity,stringObjectMap));

                });
        return list.get(0);


    }

    public List<DeploymentInstanceEntity> findByPage(DeploymentInstanceQueryParam param){

        List<DeploymentInstanceEntity> list=new ArrayList<>();


        StringBuilder finlistSql =new StringBuilder(getSelectSql);
        finlistSql.append(" where 1=1 ");
        if(param.getId() != null){
            finlistSql.append(" and id = "+param.getId());
        }
        if(param.getProcessDefinitionVersion() != null){
            finlistSql.append(" and process_definition_version = '"+param.getProcessDefinitionVersion()+"'");
        }
        if(param.getProcessDefinitionType() != null){
            finlistSql.append(" and process_definition_type = '"+param.getProcessDefinitionType()+"'");
        }
        if(param.getProcessDefinitionCode() != null){
            finlistSql.append(" and process_definition_code ='"+param.getProcessDefinitionCode()+"'");
        }
        if(param.getProcessDefinitionName() != null){
            finlistSql.append(" and process_definition_name = '"+param.getProcessDefinitionName()+"'");
        }
        if(param.getDeploymentUserId() != null){
            finlistSql.append(" and deployment_user_id ='"+param.getDeploymentUserId()+"'");
        }
        if(param.getDeploymentStatus() != null){
            finlistSql.append(" and deployment_status = '"+param.getDeploymentStatus()+"'");
        }
        if(param.getLogicStatus() != null){
            finlistSql.append(" and logic_status = '"+param.getLogicStatus()+"'");
        }
        // TODO: 2/10/21   分页没写
//         <if test="pageOffset != null and pageSize != null">limit #{pageOffset},#{pageSize}</if>

        defaultJdbcExecutor.select(finlistSql.toString())
                .forEach(stringObjectMap -> {
                    DeploymentInstanceEntity activityInstanceEntity= new DeploymentInstanceEntity();




                    list.add(convertMapToBean(activityInstanceEntity,stringObjectMap));

                });
        return list;
    }

    public void insert(DeploymentInstanceEntity deploymentInstanceEntity){

        System.out.println(deploymentInstanceEntity);
        String insertsql ="insert into se_deployment_instance (id, gmt_create, gmt_modified," +
                "process_definition_id,process_definition_version," +
                "process_definition_type,process_definition_code,process_definition_name," +
                "process_definition_desc,process_definition_content," +
                "deployment_user_id,deployment_status,logic_status)"
                +" values (?, now(), now(), " +
                "?,?," +
                "?,?,?," +
                "?,?," +
                "?,?,?)";
        defaultJdbcExecutor.update(SqlRequests.of(insertsql,
                deploymentInstanceEntity.getId(),

                deploymentInstanceEntity.getProcessDefinitionId(),
                deploymentInstanceEntity.getProcessDefinitionVersion(),

                deploymentInstanceEntity.getProcessDefinitionType(),
                deploymentInstanceEntity.getProcessDefinitionCode(),
                deploymentInstanceEntity.getProcessDefinitionName(),

                deploymentInstanceEntity.getProcessDefinitionDesc(),
                deploymentInstanceEntity.getProcessDefinitionContent(),

                deploymentInstanceEntity.getDeploymentUserId(),
                deploymentInstanceEntity.getDeploymentStatus(),
                deploymentInstanceEntity.getLogicStatus())


        );
    }

    public int delete(@Param("id") Long id){
        return defaultJdbcExecutor.update(SqlRequests.of(" delete from se_deployment_instance where id=? ",id));
    }

    public int update(DeploymentInstanceEntity deploymentInstanceEntity){

        StringBuilder updatesql =new StringBuilder("update se_deployment_instance set " +
                "gmt_modified=now() ");

        if(deploymentInstanceEntity.getProcessDefinitionVersion() != null){
            updatesql.append(" , process_definition_version = '"+deploymentInstanceEntity.getProcessDefinitionVersion()+"'");
        }
        if(deploymentInstanceEntity.getProcessDefinitionType() != null){
            updatesql.append(" , process_definition_type = '"+deploymentInstanceEntity.getProcessDefinitionType()+"'");
        }
        if(deploymentInstanceEntity.getProcessDefinitionCode() != null){
            updatesql.append(" , process_definition_code ='"+deploymentInstanceEntity.getProcessDefinitionCode()+"'");
        }
        if(deploymentInstanceEntity.getProcessDefinitionName() != null){
            updatesql.append(" , process_definition_name = '"+deploymentInstanceEntity.getProcessDefinitionName()+"'");
        }
        if(deploymentInstanceEntity.getProcessDefinitionName() != null){
            updatesql.append(" , process_definition_desc = '"+deploymentInstanceEntity.getProcessDefinitionName()+"'");
        }
        if(deploymentInstanceEntity.getDeploymentUserId() != null){
            updatesql.append(" , deployment_user_id ='"+deploymentInstanceEntity.getDeploymentUserId()+"'");
        }
        if(deploymentInstanceEntity.getDeploymentStatus() != null){
            updatesql.append(" , deployment_status = '"+deploymentInstanceEntity.getDeploymentStatus()+"'");
        }
        if(deploymentInstanceEntity.getLogicStatus() != null){
            updatesql.append(" , logic_status = '"+deploymentInstanceEntity.getLogicStatus()+"'");
        }


        updatesql.append(" where id=? ");

        return defaultJdbcExecutor.update(SqlRequests.of(updatesql.toString(),deploymentInstanceEntity.getId()));

    }


    public int count(DeploymentInstanceQueryParam param){
        return 10;
                //defaultJdbcExecutor.select(" select count(1) from  se_deployment_instance  ", ResultWrappers.singleMap()));
//        select count(1) from  se_deployment_instance  <include returnfid="condition"/> <if test="pageOffsetset != null and pageSize != null">limit #{pageOffset},#{pageSize}</if>
    }
    private DeploymentInstanceEntity convertMapToBean(DeploymentInstanceEntity activityInstanceEntity, Map<String,Object> stringObjectMap){

        LocalDate localDate =  (LocalDate)stringObjectMap.get("gmt_create");

        ZonedDateTime zdt = localDate.atStartOfDay(zoneId);
        activityInstanceEntity.setGmtCreate(Date.from(zdt.toInstant()));

        LocalDate localDate1 =  (LocalDate)stringObjectMap.get("gmt_modified");

        ZonedDateTime zdt1 = localDate1.atStartOfDay(zoneId);
        activityInstanceEntity.setGmtCreate(Date.from(zdt1.toInstant()));


        activityInstanceEntity.setId((Long) stringObjectMap.get("id"));

        activityInstanceEntity.setProcessDefinitionId((String) stringObjectMap.get("process_definition_id"));

        activityInstanceEntity.setProcessDefinitionVersion((String) stringObjectMap.get("process_definition_version"));

        activityInstanceEntity.setProcessDefinitionType((String) stringObjectMap.get("process_definition_type"));
        activityInstanceEntity.setProcessDefinitionCode((String) stringObjectMap.get("process_definition_code"));
        activityInstanceEntity.setProcessDefinitionName((String) stringObjectMap.get("process_definition_name"));
        activityInstanceEntity.setProcessDefinitionContent((String) stringObjectMap.get("process_definition_content"));
        activityInstanceEntity.setProcessDefinitionDesc((String) stringObjectMap.get("process_definition_desc"));

        activityInstanceEntity.setDeploymentUserId((String) stringObjectMap.get("deployment_user_id"));
        activityInstanceEntity.setDeploymentStatus((String) stringObjectMap.get("deployment_status"));
        activityInstanceEntity.setLogicStatus((String) stringObjectMap.get("logic_status"));
        return activityInstanceEntity;

    }
}
