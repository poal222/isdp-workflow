package org.idsp.smartengine.helper;

import com.alibaba.smart.framework.engine.SmartEngine;
import com.alibaba.smart.framework.engine.configuration.MultiInstanceCounter;
import com.alibaba.smart.framework.engine.constant.AdHocConstant;
import com.alibaba.smart.framework.engine.service.param.query.TaskInstanceQueryParam;
import com.alibaba.smart.framework.engine.service.query.TaskQueryService;

import java.util.ArrayList;
import java.util.List;

/**
 */
public class DefaultMultiInstanceCounter implements MultiInstanceCounter {


	@Override
	public Integer countPassedTaskInstanceNumber(String processInstanceId, String activityInstanceId,
			SmartEngine smartEngine) {
		TaskQueryService taskQueryService = smartEngine.getTaskQueryService();

		TaskInstanceQueryParam taskInstanceQueryParam = new TaskInstanceQueryParam();
		List<String> processInstanceIdList = new ArrayList<String>(2);
		processInstanceIdList.add(processInstanceId);
		taskInstanceQueryParam.setProcessInstanceIdList(processInstanceIdList);
		taskInstanceQueryParam.setActivityInstanceId(activityInstanceId);

		taskInstanceQueryParam.setTag(AdHocConstant.AGREE);
		Integer count = taskQueryService.count(taskInstanceQueryParam).intValue();
		return count;

	}

	@Override
	public Integer countRejectedTaskInstanceNumber(String processInstanceId, String activityInstanceId,
			SmartEngine smartEngine) {
		TaskQueryService taskQueryService = smartEngine.getTaskQueryService();

		TaskInstanceQueryParam taskInstanceQueryParam = new TaskInstanceQueryParam();
		List<String> processInstanceIdList = new ArrayList<String>(2);
		processInstanceIdList.add(processInstanceId);
		taskInstanceQueryParam.setProcessInstanceIdList(processInstanceIdList);
		taskInstanceQueryParam.setActivityInstanceId(activityInstanceId);
		taskInstanceQueryParam.setTag(AdHocConstant.DISAGREE);
		Integer count = taskQueryService.count(taskInstanceQueryParam).intValue();
		return count;

	}

}