package org.idsp.smartengine.helper.dispatcher;

import com.alibaba.smart.framework.engine.configuration.TaskAssigneeDispatcher;
import com.alibaba.smart.framework.engine.constant.AssigneeTypeConstant;
import com.alibaba.smart.framework.engine.model.assembly.Activity;
import com.alibaba.smart.framework.engine.model.instance.TaskAssigneeCandidateInstance;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * TaskAssigneeDispatcher:仅用于DataBase模式。必须实现该接口。
 * 在传统工作流里面，需要设置任务的处理者时，则需要这个接口。
 */
public class DefaultTaskAssigneeDispatcher implements TaskAssigneeDispatcher {

	@Override
	public List<TaskAssigneeCandidateInstance> getTaskAssigneeCandidateInstance(Activity activity,
			Map<String, Object> request) {
		List<TaskAssigneeCandidateInstance> taskAssigneeCandidateInstanceList = new ArrayList<TaskAssigneeCandidateInstance>();

		TaskAssigneeCandidateInstance taskAssigneeCandidateInstance = new TaskAssigneeCandidateInstance();
		taskAssigneeCandidateInstance.setAssigneeId("1");
		taskAssigneeCandidateInstance.setAssigneeType(AssigneeTypeConstant.USER);
		taskAssigneeCandidateInstanceList.add(taskAssigneeCandidateInstance);

		TaskAssigneeCandidateInstance taskAssigneeCandidateInstance1 = new TaskAssigneeCandidateInstance();
		taskAssigneeCandidateInstance1.setAssigneeId("3");
		taskAssigneeCandidateInstance1.setAssigneeType(AssigneeTypeConstant.USER);
		taskAssigneeCandidateInstanceList.add(taskAssigneeCandidateInstance1);


		TaskAssigneeCandidateInstance taskAssigneeCandidateInstance2 = new TaskAssigneeCandidateInstance();
		taskAssigneeCandidateInstance2.setAssigneeId("5");
		taskAssigneeCandidateInstance2.setAssigneeType(AssigneeTypeConstant.USER);
		taskAssigneeCandidateInstanceList.add(taskAssigneeCandidateInstance2);


		return taskAssigneeCandidateInstanceList;
	}


}
