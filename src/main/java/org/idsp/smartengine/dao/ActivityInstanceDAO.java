package org.idsp.smartengine.dao;

import org.hswebframework.ezorm.rdb.executor.SqlRequests;
import org.hswebframework.ezorm.rdb.executor.SyncSqlExecutor;
import org.idsp.smartengine.entity.ActivityInstanceEntity;
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
public class ActivityInstanceDAO {

    private ZoneId zoneId = ZoneId.systemDefault();


    private String getSelectSql = new String("select id, gmt_create, gmt_modified, " +
            "        process_instance_id,process_definition_id_and_version,process_definition_activity_id" +
            " from se_activity_instance");


    @Autowired
    private SyncSqlExecutor defaultJdbcExecutor;

    public List<ActivityInstanceEntity> findAllActivity(Long processInstanceId){

        List<ActivityInstanceEntity> list=new ArrayList<>();
        defaultJdbcExecutor.select(new StringBuilder(getSelectSql).append("  where process_instance_id=?  ").toString(),processInstanceId)
                .forEach(stringObjectMap -> {
                    ActivityInstanceEntity activityInstanceEntity= new ActivityInstanceEntity();

                    list.add(convertMapToBean(activityInstanceEntity,stringObjectMap));

                });
        return list;
    }

    public ActivityInstanceEntity findOne(@Param("id") Long id){
        List<ActivityInstanceEntity> list=new ArrayList<>();
        defaultJdbcExecutor.select(new StringBuilder(getSelectSql).append("  where id=?  ").toString(),id)
                .forEach(stringObjectMap -> {
                    ActivityInstanceEntity activityInstanceEntity= new ActivityInstanceEntity();

                    list.add(convertMapToBean(activityInstanceEntity,stringObjectMap));

                });
        return list.get(0);
    }


    public void insert(ActivityInstanceEntity activityInstanceEntity ){
        String insertsql ="insert into se_activity_instance (id, gmt_create, gmt_modified, " +
                "process_instance_id,process_definition_id_and_version,process_definition_activity_id)"
                +"values (?, now(), now(), ?,?,?)";
        defaultJdbcExecutor.update(SqlRequests.of(insertsql,
                activityInstanceEntity.getId(),
                activityInstanceEntity.getProcessInstanceId(),
                activityInstanceEntity.getProcessDefinitionIdAndVersion(),
                activityInstanceEntity.getProcessDefinitionActivityId())


        );
    }

    public Long delete(@Param("id") Long id){
       return Long.valueOf(defaultJdbcExecutor.update(SqlRequests.of(" delete from se_activity_instance where id=? ",id)));
    }
    private ActivityInstanceEntity convertMapToBean(ActivityInstanceEntity activityInstanceEntity, Map<String,Object> stringObjectMap){

        LocalDate localDate =  (LocalDate)stringObjectMap.get("gmt_create");

        ZonedDateTime zdt = localDate.atStartOfDay(zoneId);
        activityInstanceEntity.setGmtCreate(Date.from(zdt.toInstant()));

        LocalDate localDate1 =  (LocalDate)stringObjectMap.get("gmt_modified");

        ZonedDateTime zdt1 = localDate1.atStartOfDay(zoneId);
        activityInstanceEntity.setGmtCreate(Date.from(zdt1.toInstant()));


        activityInstanceEntity.setId((Long) stringObjectMap.get("id"));

        activityInstanceEntity.setProcessDefinitionIdAndVersion((String) stringObjectMap.get("process_definition_id_and_version"));

        activityInstanceEntity.setProcessInstanceId((Long) stringObjectMap.get("process_instance_id"));
        activityInstanceEntity.setProcessDefinitionActivityId((String) stringObjectMap.get("process_definition_activity_id"));

        return activityInstanceEntity;

    }
}
