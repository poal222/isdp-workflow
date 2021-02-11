package org.idsp.smartengine.service;

import com.alibaba.smart.framework.engine.SmartEngine;
import com.alibaba.smart.framework.engine.model.instance.DeploymentInstance;
import com.alibaba.smart.framework.engine.service.command.DeploymentCommandService;
import com.alibaba.smart.framework.engine.service.param.command.CreateDeploymentCommand;
import com.alibaba.smart.framework.engine.service.param.command.UpdateDeploymentCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class SeDeploymentCommandService implements ISeDeploymentCommandService {


    @Autowired
    private SmartEngine smartEngine;

    public DeploymentCommandService getDeploymentCommandService() {
        return smartEngine.getDeploymentCommandService();
    }

    //部署流程定义
    protected DeploymentCommandService deploymentCommandService;



    @Override
    public Mono<DeploymentInstance> createDeployment(CreateDeploymentCommand createDeploymentCommand) {


        return Mono.just(getDeploymentCommandService().createDeployment(createDeploymentCommand));
    }

    @Override
    public Mono<DeploymentInstance> updateDeployment(UpdateDeploymentCommand updateDeploymentCommand) {
        return Mono.just(getDeploymentCommandService().updateDeployment(updateDeploymentCommand));
    }

    @Override
    public void inactivateDeploymentInstance(String deploymentInstanceId) {
        getDeploymentCommandService().inactivateDeploymentInstance(deploymentInstanceId);

    }

    @Override
    public void activateDeploymentInstance(String deploymentInstanceId) {
        getDeploymentCommandService().activateDeploymentInstance(deploymentInstanceId);
    }

    @Override
    public void deleteDeploymentInstanceLogically(String deploymentInstanceId) {
        getDeploymentCommandService().deleteDeploymentInstanceLogically(deploymentInstanceId);
    }
}
