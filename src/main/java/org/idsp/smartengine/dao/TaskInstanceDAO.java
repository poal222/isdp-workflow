package org.idsp.smartengine.dao;

import com.alibaba.smart.framework.engine.service.param.query.TaskInstanceQueryByAssigneeParam;
import com.alibaba.smart.framework.engine.service.param.query.TaskInstanceQueryParam;
import org.hswebframework.ezorm.rdb.executor.SqlRequests;
import org.hswebframework.ezorm.rdb.executor.SyncSqlExecutor;
import org.idsp.smartengine.entity.TaskInstanceEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class TaskInstanceDAO implements ITaskInstanceDAO {

    private ZoneId zoneId = ZoneId.systemDefault();


    private String getSelectSql = new String("select         id, gmt_create, gmt_modified,process_instance_id, process_definition_id_and_version,process_definition_type,\n" +
            "        activity_instance_id, process_definition_activity_id, execution_instance_id,claim_user_id,priority,tag,claim_time,complete_time,status,comment,extension,title\n" +
            " from se_task_instance");


    @Autowired
    private SyncSqlExecutor defaultJdbcExecutor;


    @Override
    public List<TaskInstanceEntity> findTaskByAssignee(TaskInstanceQueryByAssigneeParam taskInstanceQueryByAssigneeParam) {

        List<TaskInstanceEntity> list = new ArrayList<>();
        StringBuilder finlistSql = new StringBuilder(getSelectSql);

        finlistSql.append(" where 1=1 ");


        if (taskInstanceQueryByAssigneeParam.getProcessDefinitionType() != null) {
            finlistSql.append(" and process_definition_type=" + taskInstanceQueryByAssigneeParam.getProcessDefinitionType());
        }
        if (taskInstanceQueryByAssigneeParam.getProcessInstanceIdList() != null) {
            finlistSql.append(" and process_instance_id in (");
            for (Long string : taskInstanceQueryByAssigneeParam.getProcessInstanceIdList()) {
                finlistSql.append("" + string + ",");
            }
            finlistSql.append(" )");
        }

        if (taskInstanceQueryByAssigneeParam.getStatus() != null) {
            finlistSql.append(" and status='" + taskInstanceQueryByAssigneeParam.getStatus() + "'");
        }

        if (taskInstanceQueryByAssigneeParam.getAssigneeUserId() != null) {
            finlistSql.append(" and claim_user_id='" + taskInstanceQueryByAssigneeParam.getAssigneeUserId() + "'");
        }

//         <choose>
//            <when test="assigneeUserId != null and assigneeGroupIdList != null">
//                and (      (assignee.assignee_id =  #{assigneeUserId} and assignee.assignee_type = 'user') or (assignee.assignee_id in
//                        <foreach item="item" index="index" separator="," open="(" close=")"  collection="assigneeGroupIdList">
//                #{item}
//                </foreach>
//                and assignee.assignee_type = 'group')    )
//            </when>
//            <when test="assigneeUserId != null">
//                and assignee.assignee_id =  #{assigneeUserId} and assignee.assignee_type = 'user'
//                </when>
//
//            <when test="assigneeGroupIdList != null">
//                and assignee.assignee_id in
//                <foreach item="item" index="index" separator="," open="(" close=")"  collection="assigneeGroupIdList">
//                    #{item}
//                </foreach>
//                and assignee.assignee_type = 'group'
//                </when>
//
//  <choose>
//            <when test="assigneeUserId != null and assigneeGroupIdList != null">
//                and (      (assignee.assignee_id =  #{assigneeUserId} and assignee.assignee_type = 'user') or (assignee.assignee_id in
//                        <foreach item="item" index="index" separator="," open="(" close=")"  collection="assigneeGroupIdList">
//                #{item}
//                </foreach>
//                and assignee.assignee_type = 'group')    )
//            </when>
//            <when test="assigneeUserId != null">
//                and assignee.assignee_id =  #{assigneeUserId} and assignee.assignee_type = 'user'
//                </when>
//
//            <when test="assigneeGroupIdList != null">
//                and assignee.assignee_id in
//                <foreach item="item" index="index" separator="," open="(" close=")"  collection="assigneeGroupIdList">
//                    #{item}
//                </foreach>
//                and assignee.assignee_type = 'group'
//                </when>
//


        defaultJdbcExecutor.select(finlistSql.toString())
                .forEach(stringObjectMap -> {
                    TaskInstanceEntity taskInstanceEntity = new TaskInstanceEntity();
                   list.add(convertMapToBean(taskInstanceEntity,stringObjectMap));

                });
        return list;
    }

    @Override
    public Integer countTaskByAssignee(TaskInstanceQueryByAssigneeParam taskInstanceQueryByAssigneeParam) {
        return null;
    }

    @Override
    public List<TaskInstanceEntity> findTaskList(TaskInstanceQueryParam taskInstanceQueryParam) {

        List<TaskInstanceEntity> list = new ArrayList<>();
        StringBuilder finlistSql = new StringBuilder(getSelectSql);
        finlistSql.append(" where 1=1 ");

        if (taskInstanceQueryParam.getStatus() != null) {
            finlistSql.append(" and status='" + taskInstanceQueryParam.getStatus() + "'");
        }
        if (taskInstanceQueryParam.getProcessDefinitionType() != null) {
            finlistSql.append(" and process_definition_type='" + taskInstanceQueryParam.getProcessDefinitionType() + "'");
        }


        if (taskInstanceQueryParam.getTag() != null) {
            finlistSql.append(" and tag='" + taskInstanceQueryParam.getTag() + "'");
        }
        if (taskInstanceQueryParam.getActivityInstanceId() != null) {
            finlistSql.append(" and activity_instance_id=" + taskInstanceQueryParam.getActivityInstanceId());
        }
        if (taskInstanceQueryParam.getProcessDefinitionActivityId() != null) {
            finlistSql.append(" and process_definition_activity_id=" + taskInstanceQueryParam.getProcessDefinitionActivityId());
        }
        if (taskInstanceQueryParam.getProcessInstanceIdList() != null) {
            finlistSql.append(" and process_instance_id in (");
            for (String string : taskInstanceQueryParam.getProcessInstanceIdList()) {

                finlistSql.append("" + string + ",");
            }
            finlistSql.append(" 0)");
        }

        defaultJdbcExecutor.select(finlistSql.toString())
                .forEach(stringObjectMap -> {
                    TaskInstanceEntity taskInstanceEntity = new TaskInstanceEntity();


                    list.add(convertMapToBean(taskInstanceEntity,stringObjectMap));

                });
        return list;
    }

    @Override
    public Integer count(TaskInstanceQueryParam taskInstanceQueryParam) {
        return null;
    }

    @Override
    public List<TaskInstanceEntity> findTaskByProcessInstanceIdAndStatus(Long processInstanceId, String status) {
        List<TaskInstanceEntity> list = new ArrayList<>();
        StringBuilder finlistSql = new StringBuilder(getSelectSql);
        finlistSql.append(" where 1=1 ");
        if (processInstanceId != null) {
            finlistSql.append(" and process_instance_id=" + processInstanceId);
        }
        if (status != null) {
            finlistSql.append(" and status='" + status + "'");
        }
        defaultJdbcExecutor.select(finlistSql.toString())
                .forEach(stringObjectMap -> {
                    TaskInstanceEntity taskInstanceEntity = new TaskInstanceEntity();
                    list.add(convertMapToBean(taskInstanceEntity,stringObjectMap));

                });
        return list;
    }

    @Override
    public TaskInstanceEntity findOne(Long id) {
        List<TaskInstanceEntity> list = new ArrayList<>();
        StringBuilder finlistSql = new StringBuilder(getSelectSql);
        if (id != null) {
            finlistSql.append(" where id=" + id);
        }

        defaultJdbcExecutor.select(finlistSql.toString())
                .forEach(stringObjectMap -> {
                    TaskInstanceEntity taskInstanceEntity = new TaskInstanceEntity();

                    list.add(convertMapToBean(taskInstanceEntity,stringObjectMap));

                });
        return list.get(0);
    }

    @Override
    public void insert(TaskInstanceEntity taskInstanceEntity) {
        String insertsql = "insert into se_task_instance (id, gmt_create, gmt_modified,process_instance_id, process_definition_id_and_version,process_definition_type," +
                "  activity_instance_id, process_definition_activity_id, execution_instance_id,claim_user_id,priority,tag,claim_time,complete_time,status,comment,extension,title)"
                + "values (?, now(), now(), ?::bigint,?,?, ?::bigint,?,?::bigint, ?,?::int,?, ?::date,?::date,?, ?,?,?)";
        defaultJdbcExecutor.update(SqlRequests.of(insertsql,
                taskInstanceEntity.getId(),
                taskInstanceEntity.getProcessInstanceId(),
                taskInstanceEntity.getProcessDefinitionIdAndVersion(),
                taskInstanceEntity.getProcessDefinitionType(),

                taskInstanceEntity.getActivityInstanceId(),
                taskInstanceEntity.getProcessDefinitionActivityId(),
                taskInstanceEntity.getExecutionInstanceId(),
                taskInstanceEntity.getClaimUserId(),
                taskInstanceEntity.getPriority(),
                taskInstanceEntity.getTag(),
                taskInstanceEntity.getClaimTime(),
                taskInstanceEntity.getCompleteTime(),
                taskInstanceEntity.getStatus(),
                taskInstanceEntity.getComment(),
                taskInstanceEntity.getExtension(),
                taskInstanceEntity.getTitle()
                )


        );
    }

    @Override
    public int update(TaskInstanceEntity taskInstanceEntity) {
        StringBuilder updataSql = new StringBuilder("update se_task_instance set gmt_modified=now()");
        if (taskInstanceEntity.getClaimUserId() != null) {
            updataSql.append(" ,claim_user_id = '" + taskInstanceEntity.getClaimUserId() + "'");
        }
        if (taskInstanceEntity.getPriority() != null) {
            updataSql.append(" ,priority = '" + taskInstanceEntity.getPriority() + "'");
        }
        if (taskInstanceEntity.getClaimTime() != null) {
            updataSql.append(" ,claim_time = '" + taskInstanceEntity.getClaimTime() + "'");
        }
        if (taskInstanceEntity.getCompleteTime() != null) {
            updataSql.append(" ,complete_time = '" + taskInstanceEntity.getCompleteTime() + "'");
        }
        if (taskInstanceEntity.getStatus() != null) {
            updataSql.append(" ,status = '" + taskInstanceEntity.getStatus() + "'");
        }
        if (taskInstanceEntity.getTag() != null) {
            updataSql.append(" , tag = '" + taskInstanceEntity.getTag() + "'");
        }
        if (taskInstanceEntity.getComment() != null) {
            updataSql.append(" , comment = '" + taskInstanceEntity.getComment() + "'");
        }
        if (taskInstanceEntity.getExtension() != null) {
            updataSql.append(" , extension = '" + taskInstanceEntity.getExtension() + "'");
        }
        if (taskInstanceEntity.getTitle() != null) {
            updataSql.append(" , title = '" + taskInstanceEntity.getTitle() + "'");
        }
        updataSql.append(" where id=? ");
        return defaultJdbcExecutor.update(SqlRequests.of(updataSql.toString(), taskInstanceEntity.getId()));
    }

    @Override
    public int updateFromStatus(TaskInstanceEntity taskInstanceEntity, String fromStatus) {
        StringBuilder updataSql = new StringBuilder("update se_task_instance set gmt_modified=now()");
        if (taskInstanceEntity.getClaimUserId() != null) {
            updataSql.append(" ,claim_user_id = '" + taskInstanceEntity.getClaimUserId() + "'");
        }
        if (taskInstanceEntity.getPriority() != null) {
            updataSql.append(" ,priority = '" + taskInstanceEntity.getPriority() + "'");
        }
        if (taskInstanceEntity.getClaimTime() != null) {
            updataSql.append(" ,claim_time = '" + taskInstanceEntity.getClaimTime() + "'");
        }
        if (taskInstanceEntity.getCompleteTime() != null) {
            updataSql.append(" ,complete_time = '" + taskInstanceEntity.getCompleteTime() + "'");
        }
        if (taskInstanceEntity.getStatus() != null) {
            updataSql.append(" ,status = '" + taskInstanceEntity.getStatus() + "'");
        }
        if (taskInstanceEntity.getTag() != null) {
            updataSql.append(" , tag = '" + taskInstanceEntity.getTag() + "'");
        }
        if (taskInstanceEntity.getComment() != null) {
            updataSql.append(" , comment = '" + taskInstanceEntity.getComment() + "'");
        }
        if (taskInstanceEntity.getExtension() != null) {
            updataSql.append(" , extension = '" + taskInstanceEntity.getExtension() + "'");
        }
        if (taskInstanceEntity.getTitle() != null) {
            updataSql.append(" , title = '" + taskInstanceEntity.getTitle() + "'");
        }
        updataSql.append(" where id=? and status = ?");
        return defaultJdbcExecutor.update(SqlRequests.of(updataSql.toString(), taskInstanceEntity.getId(), fromStatus));

    }

    @Override
    public void delete(Long id) {
        defaultJdbcExecutor.update(SqlRequests.of(" delete from se_task_instance where id=? ", id));
    }

    private TaskInstanceEntity convertMapToBean(TaskInstanceEntity taskInstanceEntity, Map<String, Object> stringObjectMap) {

        LocalDate localDate = (LocalDate) stringObjectMap.get("gmt_create");

        ZonedDateTime zdt = localDate.atStartOfDay(zoneId);
        taskInstanceEntity.setGmtCreate(Date.from(zdt.toInstant()));

        LocalDate localDate1 = (LocalDate) stringObjectMap.get("gmt_modified");

        ZonedDateTime zdt1 = localDate1.atStartOfDay(zoneId);
        taskInstanceEntity.setGmtCreate(Date.from(zdt1.toInstant()));


        taskInstanceEntity.setId((Long) stringObjectMap.get("id"));

        taskInstanceEntity.setProcessInstanceId(String.valueOf(stringObjectMap.get("process_instance_id")));
        taskInstanceEntity.setProcessDefinitionIdAndVersion((String) stringObjectMap.get("process_definition_id_and_version"));
        taskInstanceEntity.setProcessDefinitionType((String) stringObjectMap.get("process_definition_type"));

        taskInstanceEntity.setActivityInstanceId(String.valueOf(stringObjectMap.get("activity_instance_id")));
        taskInstanceEntity.setProcessDefinitionActivityId((String) stringObjectMap.get("process_definition_activity_id"));
        taskInstanceEntity.setExecutionInstanceId(String.valueOf(stringObjectMap.get("execution_instance_id")));
        taskInstanceEntity.setClaimUserId((String) stringObjectMap.get("claim_user_id"));
        taskInstanceEntity.setPriority((Integer) stringObjectMap.get("priority"));
        taskInstanceEntity.setTag((String) stringObjectMap.get("tag"));
        taskInstanceEntity.setClaimTime((Date) stringObjectMap.get("claim_time"));
        taskInstanceEntity.setCompleteTime((Date) stringObjectMap.get("complete_time"));
        taskInstanceEntity.setStatus((String) stringObjectMap.get("status"));
        taskInstanceEntity.setComment((String) stringObjectMap.get("comment"));
        taskInstanceEntity.setExtension((String) stringObjectMap.get("extension"));
        taskInstanceEntity.setTitle((String) stringObjectMap.get("title"));
        return taskInstanceEntity;

    }
}


