package org.idsp.smartengine.dao;

import org.hswebframework.ezorm.rdb.executor.SqlRequests;
import org.hswebframework.ezorm.rdb.executor.SyncSqlExecutor;
import org.idsp.smartengine.entity.ExecutionInstanceEntity;
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
public class ExecutionInstanceDAO {

    private ZoneId zoneId = ZoneId.systemDefault();


    private String getSelectSql = new String("select id, gmt_create, gmt_modified, " +
            "        process_instance_id,process_definition_id_and_version,process_definition_activity_id,activity_instance_id,active" +
            " from se_execution_instance");

    @Autowired
    private SyncSqlExecutor defaultJdbcExecutor;
    public List<ExecutionInstanceEntity> findActiveExecution(Long processInstanceId){
        List<ExecutionInstanceEntity> list=new ArrayList<>();
        defaultJdbcExecutor.select(new StringBuilder(getSelectSql).append("  where process_instance_id=? and active = true ").toString(),processInstanceId)
                .forEach(stringObjectMap -> {
                    ExecutionInstanceEntity executionInstanceEntity= new ExecutionInstanceEntity();
                    list.add(convertMapToBean(executionInstanceEntity,stringObjectMap));

                });
        return list;

    }

    public List<ExecutionInstanceEntity> findAllExecutionList(Long processInstanceId){
        List<ExecutionInstanceEntity> list=new ArrayList<>();
        defaultJdbcExecutor.select(new StringBuilder(getSelectSql).append("  where process_instance_id=?  order by gmt_modified desc").toString(),processInstanceId)
                .forEach(stringObjectMap -> {
                    ExecutionInstanceEntity executionInstanceEntity= new ExecutionInstanceEntity();

                    list.add(convertMapToBean(executionInstanceEntity,stringObjectMap));

                });
        return list;
    }

    public List<ExecutionInstanceEntity> findByActivityInstanceId(@Param("processInstanceId") Long processInstanceId, @Param("activityInstanceId") Long activityInstanceId){

        List<ExecutionInstanceEntity> list=new ArrayList<>();
        defaultJdbcExecutor.select(new StringBuilder(getSelectSql).append("  where process_instance_id=?  and activity_instance_id = ? order by gmt_modified desc").toString(),processInstanceId,activityInstanceId)
                .forEach(stringObjectMap -> {
                    ExecutionInstanceEntity executionInstanceEntity= new ExecutionInstanceEntity();

                    list.add(convertMapToBean(executionInstanceEntity,stringObjectMap));

                });
        return list;
    }


    public ExecutionInstanceEntity findOne(@Param("id") Long id){
        List<ExecutionInstanceEntity> list=new ArrayList<>();
        defaultJdbcExecutor.select(new StringBuilder(getSelectSql).append("  where id=?  ").toString(),id)
                .forEach(stringObjectMap -> {
                    ExecutionInstanceEntity executionInstanceEntity= new ExecutionInstanceEntity();



                    list.add(convertMapToBean(executionInstanceEntity,stringObjectMap));

                });
        return list.get(0);
    }


    public void insert(  ExecutionInstanceEntity executionInstanceEntity ){
        String insertsql ="insert into se_execution_instance (id, gmt_create, gmt_modified, " +
                "process_instance_id,process_definition_id_and_version,process_definition_activity_id,activity_instance_id,active)"
                +"values (?, now(), now(), ?::bigint,?,?,?::bigint, ?)";
        defaultJdbcExecutor.update(SqlRequests.of(insertsql,
                executionInstanceEntity.getId(),
                executionInstanceEntity.getProcessInstanceId(),
                executionInstanceEntity.getProcessDefinitionIdAndVersion(),
                executionInstanceEntity.getProcessDefinitionActivityId(),
                executionInstanceEntity.getActivityInstanceId(),
                executionInstanceEntity.isActive())

               );
    }

    public int update(ExecutionInstanceEntity executionInstanceEntity){

         StringBuilder insertsql =new StringBuilder("update se_execution_instance set " +
                "gmt_modified=now() ");
//        if(executionInstanceEntity.isActive() ){
            insertsql.append(" ,active="+executionInstanceEntity.isActive());
//        }
        if(executionInstanceEntity.getActivityInstanceId() !=null ){
            insertsql.append(" ,activity_instance_id='"+executionInstanceEntity.getActivityInstanceId()+"'");
        }
        if(executionInstanceEntity.getProcessInstanceId() !=null ){
            insertsql.append(" ,process_instance_id='"+executionInstanceEntity.getProcessInstanceId()+"'");
        }
        if(executionInstanceEntity.getProcessDefinitionActivityId() !=null ){
            insertsql.append(" ,process_definition_activity_id='"+executionInstanceEntity.getProcessDefinitionActivityId()+"'");
        }
        if(executionInstanceEntity.getProcessDefinitionIdAndVersion() !=null ){
            insertsql.append(" ,process_definition_id_and_version='"+executionInstanceEntity.getProcessDefinitionIdAndVersion()+"'");
        }

        insertsql.append(" where id=? ");
        return defaultJdbcExecutor.update(SqlRequests.of(insertsql.toString(),executionInstanceEntity.getId()));
    }

    public void delete(@Param("id") Long id){
        defaultJdbcExecutor.execute(SqlRequests.of(" delete from se_execution_instance where id=? ",id));
    }


    private ExecutionInstanceEntity convertMapToBean(ExecutionInstanceEntity executionInstanceEntity,Map<String,Object> stringObjectMap){
        LocalDate localDate =  (LocalDate)stringObjectMap.get("gmt_create");

        ZonedDateTime zdt = localDate.atStartOfDay(zoneId);
        executionInstanceEntity.setGmtCreate(Date.from(zdt.toInstant()));

        LocalDate localDate1 =  (LocalDate)stringObjectMap.get("gmt_modified");

        ZonedDateTime zdt1 = localDate1.atStartOfDay(zoneId);
        executionInstanceEntity.setGmtCreate(Date.from(zdt1.toInstant()));


        executionInstanceEntity.setId((Long) stringObjectMap.get("id"));

        executionInstanceEntity.setProcessDefinitionIdAndVersion((String) stringObjectMap.get("process_definition_id_and_version"));

        executionInstanceEntity.setProcessInstanceId(String.valueOf(stringObjectMap.get("process_instance_id")));
        executionInstanceEntity.setProcessDefinitionActivityId((String) stringObjectMap.get("process_definition_activity_id"));

        Long acid = (Long) stringObjectMap.get("activity_instance_id");
        executionInstanceEntity.setActivityInstanceId(String.valueOf(acid));
        executionInstanceEntity.setActive((Boolean) stringObjectMap.get("active"));
        return executionInstanceEntity;

    }
}
