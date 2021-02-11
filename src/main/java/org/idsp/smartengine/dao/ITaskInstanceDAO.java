package org.idsp.smartengine.dao;

import com.alibaba.smart.framework.engine.service.param.query.TaskInstanceQueryByAssigneeParam;
import com.alibaba.smart.framework.engine.service.param.query.TaskInstanceQueryParam;
import org.idsp.smartengine.entity.TaskInstanceEntity;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ITaskInstanceDAO {
    List<TaskInstanceEntity> findTaskByAssignee(TaskInstanceQueryByAssigneeParam taskInstanceQueryByAssigneeParam);

    Integer countTaskByAssignee(TaskInstanceQueryByAssigneeParam taskInstanceQueryByAssigneeParam);

    List<TaskInstanceEntity> findTaskList(TaskInstanceQueryParam taskInstanceQueryParam);

    Integer count(TaskInstanceQueryParam taskInstanceQueryParam);

    List<TaskInstanceEntity> findTaskByProcessInstanceIdAndStatus(Long processInstanceId,String status);


    TaskInstanceEntity findOne(@Param("id") Long id);


    void insert(  TaskInstanceEntity taskInstanceEntity );

    int update(@Param("taskInstanceEntity") TaskInstanceEntity taskInstanceEntity);

    int updateFromStatus(@Param("taskInstanceEntity") TaskInstanceEntity taskInstanceEntity,@Param("fromStatus") String fromStatus);

    void delete(@Param("id") Long id);
}
