package org.idsp.smartengine.dao;

import org.hswebframework.ezorm.rdb.executor.reactive.ReactiveSyncSqlExecutor;
import org.idsp.smartengine.entity.TaskAssigneeEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Repository
public class TaskAssigneeDAO {




    @Autowired
    private ReactiveSyncSqlExecutor reactiveSyncSqlExecutor;

    private String getSelectSql="select id, gmt_create, gmt_modified,process_instance_id,task_instance_id,assignee_id,assignee_type from se_task_assignee_instance ";

    /**
     * 查询任务信息
     * @param taskInstanceId
     * @return
     */
    public List<TaskAssigneeEntity> findList(Long  taskInstanceId){
        String sql = getSelectSql
                +" where task_instance_id=?";
        List<TaskAssigneeEntity> list = new ArrayList<>();
        ZoneId zoneId = ZoneId.systemDefault();
        reactiveSyncSqlExecutor.select(sql,taskInstanceId)
                .forEach(stringObjectMap -> {
                    TaskAssigneeEntity taskAssigneeEntity = new TaskAssigneeEntity();
                    taskAssigneeEntity.setId((Long) stringObjectMap.get("id"));
                    taskAssigneeEntity.setAssigneeId((String) stringObjectMap.get("assignee_id"));
                    taskAssigneeEntity.setAssigneeType((String) stringObjectMap.get("assignee_type"));
                    taskAssigneeEntity.setProcessInstanceId((Long) stringObjectMap.get("process_instance_id"));
                    taskAssigneeEntity.setTaskInstanceId((Long) stringObjectMap.get("task_instance_id"));
                    LocalDate localDate =  (LocalDate)stringObjectMap.get("gmt_create");

                    ZonedDateTime zdt = localDate.atStartOfDay(zoneId);
                    taskAssigneeEntity.setGmtCreate(Date.from(zdt.toInstant()));

                    LocalDate localDate1 =  (LocalDate)stringObjectMap.get("gmt_modified");

                    ZonedDateTime zdt1 = localDate1.atStartOfDay(zoneId);
                    taskAssigneeEntity.setGmtCreate(Date.from(zdt1.toInstant()));
                    list.add(taskAssigneeEntity);
                });
        return list;
    }
}
