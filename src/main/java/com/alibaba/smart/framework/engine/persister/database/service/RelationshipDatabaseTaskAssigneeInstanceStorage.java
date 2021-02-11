package com.alibaba.smart.framework.engine.persister.database.service;

import com.alibaba.smart.framework.engine.configuration.ProcessEngineConfiguration;
import com.alibaba.smart.framework.engine.extension.annoation.ExtensionBinding;
import com.alibaba.smart.framework.engine.extension.constant.ExtensionConstant;
import com.alibaba.smart.framework.engine.instance.impl.DefaultTaskAssigneeInstance;
import com.alibaba.smart.framework.engine.instance.storage.TaskAssigneeStorage;
import com.alibaba.smart.framework.engine.model.instance.TaskAssigneeInstance;
import com.alibaba.smart.framework.engine.service.param.query.PendingTaskQueryParam;
import org.idsp.smartengine.dao.TaskAssigneeDAO;
import org.idsp.smartengine.entity.TaskAssigneeEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@ExtensionBinding(group = ExtensionConstant.COMMON, bindKey = TaskAssigneeStorage.class)

public class RelationshipDatabaseTaskAssigneeInstanceStorage implements TaskAssigneeStorage {

    @Override
    public List<TaskAssigneeInstance> findList(String taskInstanceId, ProcessEngineConfiguration processEngineConfiguration) {
        TaskAssigneeDAO taskAssigneeDAO= (TaskAssigneeDAO) processEngineConfiguration.getInstanceAccessor().access("taskAssigneeDAO");
        List<TaskAssigneeEntity> taskAssigneeEntityList =  taskAssigneeDAO.findList(Long.valueOf(taskInstanceId));

        List<TaskAssigneeInstance> taskAssigneeInstanceList= null;
        if(null != taskAssigneeEntityList){
            taskAssigneeInstanceList = new ArrayList<TaskAssigneeInstance>(taskAssigneeEntityList.size());
            for (TaskAssigneeEntity taskAssigneeEntity : taskAssigneeEntityList) {
                TaskAssigneeInstance taskAssigneeInstance = buildTaskAssigneeInstance(taskAssigneeEntity);
                taskAssigneeInstanceList.add(taskAssigneeInstance);
            }
        }

        return taskAssigneeInstanceList;
    }

    @Override
    public Map<String, List<TaskAssigneeInstance>> findAssigneeOfInstanceList(List<String> taskInstanceIdList, ProcessEngineConfiguration processEngineConfiguration) {
        return null;
    }

    @Override
    public List<TaskAssigneeInstance> findPendingTaskAssigneeList(PendingTaskQueryParam pendingTaskQueryParam, ProcessEngineConfiguration processEngineConfiguration) {
        return null;
    }

    @Override
    public Long countPendingTaskAssigneeList(PendingTaskQueryParam pendingTaskQueryParam, ProcessEngineConfiguration processEngineConfiguration) {
        return null;
    }

    @Override
    public TaskAssigneeInstance insert(TaskAssigneeInstance taskAssigneeInstance, ProcessEngineConfiguration processEngineConfiguration) {
        return null;
    }

    @Override
    public TaskAssigneeInstance update(String taskAssigneeInstanceId, String assigneeId, ProcessEngineConfiguration processEngineConfiguration) {
        return null;
    }

    @Override
    public TaskAssigneeInstance findOne(String taskAssigneeId, ProcessEngineConfiguration processEngineConfiguration) {
        return null;
    }

    @Override
    public void remove(String taskAssigneeInstanceId, ProcessEngineConfiguration processEngineConfiguration) {

    }

    @Override
    public void removeAll(String taskInstanceId, ProcessEngineConfiguration processEngineConfiguration) {

    }

    private TaskAssigneeInstance buildTaskAssigneeInstance(TaskAssigneeEntity taskAssigneeEntity) {
        TaskAssigneeInstance taskAssigneeInstance = new DefaultTaskAssigneeInstance();

        taskAssigneeInstance.setInstanceId(taskAssigneeEntity.getId().toString());
        taskAssigneeInstance.setProcessInstanceId(taskAssigneeEntity.getProcessInstanceId().toString());
        taskAssigneeInstance.setTaskInstanceId(taskAssigneeEntity.getTaskInstanceId().toString());


        taskAssigneeInstance.setAssigneeId(taskAssigneeEntity.getAssigneeId());
        taskAssigneeInstance.setAssigneeType(taskAssigneeEntity.getAssigneeType());

        taskAssigneeInstance.setStartTime(taskAssigneeEntity.getGmtCreate());
        taskAssigneeInstance.setCompleteTime(taskAssigneeEntity.getGmtModified());
        return taskAssigneeInstance;
    }
}
