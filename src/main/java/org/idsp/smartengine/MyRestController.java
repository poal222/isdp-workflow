package org.idsp.smartengine;

import com.alibaba.smart.framework.engine.SmartEngine;
import com.alibaba.smart.framework.engine.configuration.scanner.AnnotationScanner;
import com.alibaba.smart.framework.engine.constant.RequestMapSpecialKeyConstant;
import com.alibaba.smart.framework.engine.model.instance.ExecutionInstance;
import com.alibaba.smart.framework.engine.model.instance.InstanceStatus;
import com.alibaba.smart.framework.engine.model.instance.ProcessInstance;
import com.alibaba.smart.framework.engine.model.instance.TaskInstance;
import com.alibaba.smart.framework.engine.service.command.*;
import com.alibaba.smart.framework.engine.service.param.query.ProcessInstanceQueryParam;
import com.alibaba.smart.framework.engine.service.query.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/users")
public class MyRestController {

	//3. 部署流程定义
	protected RepositoryCommandService repositoryCommandService;
	//2.获得常用服务
	protected ProcessCommandService processCommandService;
	protected ProcessQueryService processQueryService;

	protected ExecutionQueryService executionQueryService;
	protected ExecutionCommandService executionCommandService;
	protected RepositoryQueryService repositoryQueryService;
	protected TaskCommandService taskCommandService;
	protected ActivityQueryService activityQueryService;
	protected TaskQueryService taskQueryService;

	protected DeploymentCommandService deploymentCommandService;
	protected DeploymentQueryService deploymentQueryService;
	protected VariableQueryService variableQueryService;
	protected TaskAssigneeQueryService taskAssigneeQueryService;

	private AnnotationScanner annotationScanner;

	@Autowired
	private SmartEngine smartEngine;

	@GetMapping("/{user}")
	public Mono<String> getUser(@PathVariable Long user) {
		//3. 部署流程定义
		deploymentCommandService = smartEngine.getDeploymentCommandService();

		repositoryCommandService = smartEngine.getRepositoryCommandService();
		processCommandService = smartEngine.getProcessCommandService();
		executionCommandService = smartEngine.getExecutionCommandService();
		taskCommandService = smartEngine.getTaskCommandService();

		deploymentQueryService = smartEngine.getDeploymentQueryService();
		repositoryQueryService = smartEngine.getRepositoryQueryService();
		processQueryService = smartEngine.getProcessQueryService();
		executionQueryService = smartEngine.getExecutionQueryService();
		activityQueryService = smartEngine.getActivityQueryService();
		taskQueryService = smartEngine.getTaskQueryService();

		variableQueryService = smartEngine.getVariableQueryService();
		taskAssigneeQueryService = smartEngine.getTaskAssigneeQueryService();


		//4.启动流程实例

		ProcessInstance processInstance = processCommandService.start("Process_1", "3.0.0");
		System.out.println(processInstance);

		List<TaskInstance> submitTaskInstanceList = taskQueryService
				.findAllPendingTaskList(processInstance.getInstanceId());
		System.out.println(submitTaskInstanceList.size());
		TaskInstance first = submitTaskInstanceList.get(0);
		TaskInstance second = submitTaskInstanceList.get(1);

		String firstProcessDefinitionActivityId = first.getProcessDefinitionActivityId();
		String secondProcessDefinitionActivityId = second.getProcessDefinitionActivityId();

		//		Assert.assertTrue( ("processPayment".equals(firstProcessDefinitionActivityId) &&   "processDelivery".equals(secondProcessDefinitionActivityId)) ||
		//				("processPayment".equals(secondProcessDefinitionActivityId) &&   "processDelivery".equals(firstProcessDefinitionActivityId)));


		//5.流程流转:构造提交申请参数
		Map<String, Object> submitFormRequest = new HashMap<String, Object>();
		Map<String, Object> request = new HashMap();
		request.put(RequestMapSpecialKeyConstant.PROCESS_INSTANCE_START_USER_ID, "123");
		request.put(RequestMapSpecialKeyConstant.PROCESS_DEFINITION_TYPE, "group");
		request.put("processVariable", "processVariableValue");

		ProcessInstanceQueryParam processInstanceQueryParam = new ProcessInstanceQueryParam();
		processInstanceQueryParam.setStartUserId("123");
		//完成支付或者发货
		TaskInstance firstTaskInstance = submitTaskInstanceList.get(0);
		taskCommandService.complete(firstTaskInstance.getInstanceId(), submitFormRequest);

		submitTaskInstanceList = taskQueryService.findAllPendingTaskList(processInstance.getInstanceId());
		System.out.println(submitTaskInstanceList.size());

		TaskInstance secondTaskInstance = submitTaskInstanceList.get(0);
		taskCommandService.complete(secondTaskInstance.getInstanceId(), submitFormRequest);


		ProcessInstance finalProcessInstance = processQueryService.findById(processInstance.getInstanceId());
		List<ExecutionInstance> executionInstanceList = executionQueryService
				.findActiveExecutionList(processInstance.getInstanceId());

		submitTaskInstanceList = taskQueryService.findAllPendingTaskList(processInstance.getInstanceId());
		System.out.println(submitTaskInstanceList.size());
		TaskInstance thirdTaskInstance = submitTaskInstanceList.get(0);

		taskCommandService.complete(thirdTaskInstance.getInstanceId(), submitFormRequest);


		//10.由于流程测试已经关闭,需要断言没有需要处理的人,状态关闭.
		finalProcessInstance = processQueryService.findById(processInstance.getInstanceId());
//		System.out.println(InstanceStatus.completed);
		System.out.println("getStatus===="+finalProcessInstance.getStatus());


		return Mono.just("sdsd");
	}


}