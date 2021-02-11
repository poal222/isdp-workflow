package org.idsp.smartengine;

import com.alibaba.smart.framework.engine.SmartEngine;
import com.alibaba.smart.framework.engine.constant.DeploymentStatusConstant;
import com.alibaba.smart.framework.engine.constant.RequestMapSpecialKeyConstant;
import com.alibaba.smart.framework.engine.model.instance.*;
import com.alibaba.smart.framework.engine.service.command.*;
import com.alibaba.smart.framework.engine.service.param.command.CreateDeploymentCommand;
import com.alibaba.smart.framework.engine.service.param.command.UpdateDeploymentCommand;
import com.alibaba.smart.framework.engine.service.query.*;
import com.alibaba.smart.framework.engine.util.IOUtil;
import org.idsp.smartengine.service.SeDeploymentCommandService;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import static org.junit.jupiter.api.Assertions.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@SpringBootTest
class MyRestControllerTest {


    @Autowired
    private SmartEngine smartEngine;


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

	protected DeploymentQueryService deploymentQueryService;
	protected VariableQueryService variableQueryService;
	protected TaskAssigneeQueryService taskAssigneeQueryService;

    //3. 部署流程定义
    @Autowired
    private SeDeploymentCommandService deploymentCommandService;
    @Test
    void testSeDeploymentCommandService() {

        CreateDeploymentCommand createDeploymentCommand = new CreateDeploymentCommand();

        //3. 部署流程定义
        String content = IOUtil.readResourceFileAsUTF8String("smart-engine/multi-instance-test.bpmn20.xml");
        createDeploymentCommand.setProcessDefinitionContent(content);
        //  流程创建者
        createDeploymentCommand.setDeploymentUserId("jarod");
        // 是否激活流程
        createDeploymentCommand.setDeploymentStatus(DeploymentStatusConstant.INACTIVE);

        createDeploymentCommand.setProcessDefinitionDesc("desc");

        createDeploymentCommand.setProcessDefinitionName("name");

        createDeploymentCommand.setProcessDefinitionType("group");

        createDeploymentCommand.setProcessDefinitionCode("code");

//        // 部署流程
//        deploymentCommandService.createDeployment(createDeploymentCommand)
//            .subscribe(deploymentInstance -> {
//                System.out.println(deploymentInstance);
//                System.out.println("sdsdsdsd="+smartEngine.getDeploymentQueryService().findById(deploymentInstance.getInstanceId()));
//            });
//        deploymentCommandService.activateDeploymentInstance("8729514227676237496");
//        deploymentCommandService.inactivateDeploymentInstance("8729514227676237496");
//        deploymentCommandService.deleteDeploymentInstanceLogically("8729514227676237496");
//
//        UpdateDeploymentCommand updateDeploymentCommand = new UpdateDeploymentCommand();
//        updateDeploymentCommand.setDeployInstanceId("8729514227676237496");
//
//
//        updateDeploymentCommand.setDeploymentUserId("wojia");
//        //updateDeploymentCommand.setProcessDefinitionContent(newContent);
//        updateDeploymentCommand.setProcessDefinitionDesc("newDesc");
//        updateDeploymentCommand.setProcessDefinitionName("newName");
//        updateDeploymentCommand.setProcessDefinitionType("newType");
//        deploymentCommandService.updateDeployment(updateDeploymentCommand);

//        DeploymentInstance inactivateDeploymentInstance =   smartEngine.getDeploymentQueryService().findById("8729514227676237496");
//        System.out.println("sdssd=sdsd="+inactivateDeploymentInstance);
                ////		//4.启动流程实例

        ////		//3. 部署流程定义
////		deploymentCommandService = smartEngine.getDeploymentCommandService();
//
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
//
        Map<String, Object> request = new HashMap();
        request.put(RequestMapSpecialKeyConstant.PROCESS_INSTANCE_START_USER_ID,"123");
        request.put(RequestMapSpecialKeyConstant.PROCESS_DEFINITION_TYPE,"group");
        request.put("processVariable","processVariableValue");

		ProcessInstance processInstance = smartEngine.getProcessCommandService().start("multi-instance-user-task", "1.0.2",request);
		System.out.println(processInstance);
        Assert.assertNotNull(processInstance);
        Assert.assertEquals("group",processInstance.getProcessDefinitionType());

        processInstance =   processQueryService.findById(processInstance.getInstanceId());
        Assert.assertEquals("group",processInstance.getProcessDefinitionType());
        Assert.assertEquals("multi-instance-user-task:1.0.2",processInstance.getProcessDefinitionIdAndVersion());
        Assert.assertEquals("multi-instance-user-task",processInstance.getProcessDefinitionId());
        Assert.assertEquals("1.0.2",processInstance.getProcessDefinitionVersion());

        Assert.assertEquals("123",processInstance.getStartUserId());
        Assert.assertEquals(InstanceStatus.running,processInstance.getStatus());

//        Assert.assertNotNull(processInstance.getCompleteTime());

        Assert.assertNotNull(processInstance.getStartTime());
        Assert.assertNull(processInstance.getBizUniqueId());

        processInstance =   processQueryService.findById(processInstance.getInstanceId());

        processCommandService.abort(processInstance.getInstanceId(),"reason");


        processInstance =   processQueryService.findById(processInstance.getInstanceId());
        Assert.assertEquals(InstanceStatus.aborted,processInstance.getStatus());

        Assert.assertEquals("reason",processInstance.getReason());

        List<ExecutionInstance> executionInstanceList = executionQueryService.findActiveExecutionList(processInstance.getInstanceId());
        Assert.assertEquals(0,executionInstanceList.size());
        List<TaskInstance> taskInstanceList =   taskQueryService.findAllPendingTaskList(processInstance.getInstanceId());
        Assert.assertEquals(0,taskInstanceList.size());
    }

    @Test
    void getUser() {

    }
//    @Test
//    void testSeDeploymentCommandService() {
//
//        ////		//4.启动流程实例
//////
////		ProcessInstance processInstance = processCommandService.start("Process_1", "3.0.0");
////		System.out.println(processInstance);
//    }
}