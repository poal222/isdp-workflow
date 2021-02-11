package org.idsp.smartengine.service;


import com.alibaba.smart.framework.engine.model.instance.DeploymentInstance;
import com.alibaba.smart.framework.engine.service.param.command.CreateDeploymentCommand;
import com.alibaba.smart.framework.engine.service.param.command.UpdateDeploymentCommand;
import reactor.core.publisher.Mono;

/**
 * @author wanggang
 * @Function <p>
 *     定义流程管理的对外接口服务
 *     对se引擎进行进一步的封装
 *     对外统一提供rest服务
 * </p>
 */
public interface ISeDeploymentCommandService{

//    流程部署服务
    /**
     * 创建流程实例,解析流程定义并部署到本地内存中.
     *
     * @param createDeploymentCommand
     * @return
     */
    Mono<DeploymentInstance> createDeployment(CreateDeploymentCommand createDeploymentCommand) ;

    /**
     *
     * 更新部署实例,但是不涉及修改部署实例的部署状态(比如说 active,inactive)
     * 另外,在更新时, 如果部署实例的部署状态是 active, 则解析流程定义并部署到本地内存中.如果部署实例的部署状态是 inactive, 则<bold>不会解析流程定义<bold/>.
     * @param updateDeploymentCommand
     * @return
     */
    Mono<DeploymentInstance> updateDeployment(UpdateDeploymentCommand updateDeploymentCommand) ;

    /**
     * 将DeploymentInstance的状态置为 inactive, 关联的流程定义的仍然在内存里面.
     * @param deploymentInstanceId
     */
    void inactivateDeploymentInstance(String deploymentInstanceId);

    /**
     * 将DeploymentInstance的状态置为 active, 将关联的流程定义的重新加载到内存里面.
     * @param deploymentInstanceId
     */
    void activateDeploymentInstance(String deploymentInstanceId);

    /**
     * 逻辑删除DeploymentInstance
     * @param deploymentInstanceId
     */
    void deleteDeploymentInstanceLogically(String deploymentInstanceId);

//    流程实例服务
}
