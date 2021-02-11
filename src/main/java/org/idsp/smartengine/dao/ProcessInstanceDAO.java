package org.idsp.smartengine.dao;

import com.alibaba.smart.framework.engine.service.param.query.ProcessInstanceQueryParam;
import org.hswebframework.ezorm.rdb.executor.SqlRequests;
import org.hswebframework.ezorm.rdb.executor.SyncSqlExecutor;
import org.hswebframework.ezorm.rdb.executor.wrapper.ResultWrappers;
import org.idsp.smartengine.entity.ProcessInstanceEntity;
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
public class ProcessInstanceDAO  {


//    @Autowired
//    private ReactiveSyncSqlExecutor reactiveSyncSqlExecutor;

//    private DefaultJdbcExecutor defaultJdbcExecutor = new DefaultJdbcExecutor();

//    private  DefaultJdbcExecutor defaultJdbcExecutor = new DefaultJdbcExecutor();
    @Autowired
private SyncSqlExecutor defaultJdbcExecutor;

    private ZoneId zoneId = ZoneId.systemDefault();


    private String getSelectSql = new String("select id, gmt_create, gmt_modified, process_definition_id_and_version,process_definition_type,start_user_id,\n" +
            "        status, parent_process_instance_id,parent_execution_instance_id, reason, biz_unique_id, title,tag,comment" +
            " from se_process_instance");


    public ProcessInstanceEntity findOne(@Param("id") Long id){
        final ProcessInstanceEntity[] processInstanceEntity = {new ProcessInstanceEntity()};

        defaultJdbcExecutor.select(new StringBuilder(getSelectSql).append("  where id=?").toString(),id)
                .forEach(stringObjectMap -> {

                   processInstanceEntity[0] = convertMapToBean(processInstanceEntity[0],stringObjectMap);

                });
        return processInstanceEntity[0];

    }

    public ProcessInstanceEntity findOneForUpdate(@Param("id") Long id){
        final ProcessInstanceEntity[] processInstanceEntity = {new ProcessInstanceEntity()};
        defaultJdbcExecutor.select(new StringBuilder(getSelectSql).append("  where id=? for update").toString(),id)
                .forEach(stringObjectMap -> {

                    convertMapToBean(processInstanceEntity[0],stringObjectMap);

                });
        return processInstanceEntity[0];
    }

    public List<ProcessInstanceEntity> find(ProcessInstanceQueryParam processInstanceQueryParam){

        List<ProcessInstanceEntity> list=new ArrayList<>();
        defaultJdbcExecutor.select(new StringBuilder(getSelectSql).append("  order by gmt_modified desc").toString())
                .forEach(stringObjectMap -> {
                    ProcessInstanceEntity processInstanceEntity= new ProcessInstanceEntity();

                    list.add(convertMapToBean(processInstanceEntity,stringObjectMap));

                });
        return list;

//        select
//                <include refid="baseColumn"/>
//                from  se_process_instance
//                <include refid="condition"/>
//                order by gmt_modified desc
//                <if test="pageOffset != null and pageSize != null">limit #{pageOffset},#{pageSize}</if>
    }

    public Long count(ProcessInstanceQueryParam processInstanceQueryParam){

        Map<String,Object> map = defaultJdbcExecutor.select("select count(*)\n" +
                "        from  se_process_instance", ResultWrappers.singleMap());
        return (Long) map.get("count(*)");

    }


    public void insert(  ProcessInstanceEntity processInstanceEntity ){
        String insertsql ="insert into se_process_instance (id, gmt_create, gmt_modified, process_definition_id_and_version,process_definition_type," +
                "start_user_id,status, parent_process_instance_id,parent_execution_instance_id, reason, biz_unique_id, title,tag,comment)"
        +"values (?, now(), now(), ?,?,?,?, ?::bigint,?::bigint, " +
                "?, ?, ?, ?,?)";
        defaultJdbcExecutor.update(SqlRequests.of(insertsql,
                processInstanceEntity.getId(),
                processInstanceEntity.getProcessDefinitionIdAndVersion(),
                processInstanceEntity.getProcessDefinitionType(),
                processInstanceEntity.getStartUserId(),
                processInstanceEntity.getStatus(),
                (Long)processInstanceEntity.getParentProcessInstanceId(),
                (Long)processInstanceEntity.getParentExecutionInstanceId(),
                processInstanceEntity.getReason(),
                processInstanceEntity.getBizUniqueId(),
                processInstanceEntity.getTitle(),
                processInstanceEntity.getTag(),
                processInstanceEntity.getComment()));

    }

    public int update(ProcessInstanceEntity processInstanceEntity){
                StringBuilder insertsql =new StringBuilder("update se_process_instance set " +
                "gmt_modified=now() ");

        if(processInstanceEntity.getStatus()!=null ){
            insertsql.append(" ,status='"+processInstanceEntity.getStatus()+"'");
        }
        if(processInstanceEntity.getTag() !=null ){
            insertsql.append(" ,tag='"+processInstanceEntity.getTag()+"'");
        }
        if(processInstanceEntity.getReason() !=null ){
            insertsql.append(" ,reason='"+processInstanceEntity.getReason()+"'");
        }
        if(processInstanceEntity.getParentProcessInstanceId() !=null ){
            insertsql.append(" ,parent_process_instance_id="+processInstanceEntity.getParentProcessInstanceId()+"");
        }
        if(processInstanceEntity.getComment() !=null ){
            insertsql.append(" ,comment='"+processInstanceEntity.getComment()+"'");
        }


        insertsql.append(" where id=? ");
        return defaultJdbcExecutor.update(SqlRequests.of(insertsql.toString(),processInstanceEntity.getId()));
    }

    public void delete(@Param("id") Long id){
        defaultJdbcExecutor.execute(SqlRequests.of(" delete from se_process_instance where id=? ",id));
    }

    public void tryLock(Long id){
        defaultJdbcExecutor.execute(SqlRequests.of(new StringBuilder(getSelectSql).append("  where id=? for update").toString(),id));
    }

    private ProcessInstanceEntity convertMapToBean(ProcessInstanceEntity processInstanceEntity,Map<String,Object> stringObjectMap){

        LocalDate localDate =  (LocalDate)stringObjectMap.get("gmt_create");

        ZonedDateTime zdt = localDate.atStartOfDay(zoneId);
        processInstanceEntity.setGmtCreate(Date.from(zdt.toInstant()));

        LocalDate localDate1 =  (LocalDate)stringObjectMap.get("gmt_modified");

        ZonedDateTime zdt1 = localDate1.atStartOfDay(zoneId);
        processInstanceEntity.setGmtCreate(Date.from(zdt1.toInstant()));


        processInstanceEntity.setId((Long) stringObjectMap.get("id"));

        processInstanceEntity.setProcessDefinitionIdAndVersion((String) stringObjectMap.get("process_definition_id_and_version"));
        processInstanceEntity.setProcessDefinitionType((String) stringObjectMap.get("process_definition_type"));
        processInstanceEntity.setStartUserId((String) stringObjectMap.get("start_user_id"));

        processInstanceEntity.setStatus((String) stringObjectMap.get("status"));
        processInstanceEntity.setParentProcessInstanceId((Long) stringObjectMap.get("parent_process_instance_id"));
        processInstanceEntity.setParentExecutionInstanceId((Long) stringObjectMap.get("parent_execution_instance_id"));
        processInstanceEntity.setReason((String) stringObjectMap.get("reason"));

        processInstanceEntity.setBizUniqueId((String) stringObjectMap.get("biz_unique_id"));
        processInstanceEntity.setTitle((String) stringObjectMap.get("title"));
        processInstanceEntity.setTag((String) stringObjectMap.get("tag"));
        processInstanceEntity.setComment((String) stringObjectMap.get("comment"));
        return processInstanceEntity;

    }

}
