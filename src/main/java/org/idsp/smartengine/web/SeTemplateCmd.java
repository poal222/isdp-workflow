package org.idsp.smartengine.web;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.hswebframework.web.crud.service.ReactiveCrudService;
import org.hswebframework.web.crud.web.reactive.ReactiveServiceCrudController;
import org.idsp.smartengine.entity.SeGroupEntity;
import org.idsp.smartengine.entity.SeTemplateEntity;
import org.idsp.smartengine.service.SeGroupService;
import org.idsp.smartengine.service.SeTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Function 流程分组
 */


@RestController
@RequestMapping("/isdp/se/SeTemplate")
@Tag(name = "流程表单管理",description = "对流程表单进行管理，方便对页面表单的配置")
public class SeTemplateCmd implements ReactiveServiceCrudController<SeTemplateEntity,String> {

	@Autowired
	private SeTemplateService seTemplateService;


	@Override
	public ReactiveCrudService getService() {
		return this.seTemplateService;
	}
}
