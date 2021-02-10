package org.idsp.smartengine.web;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.hswebframework.web.crud.service.ReactiveCrudService;
import org.hswebframework.web.crud.web.reactive.ReactiveServiceCrudController;
import org.idsp.smartengine.entity.SeGroupEntity;
import org.idsp.smartengine.service.SeGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Function 流程分组
 */


@RestController
@RequestMapping("/isdp/se/SeGroup")
@Tag(name = "流程分组",description = "对流程进行分组，方便管理")
public class SeGroup implements ReactiveServiceCrudController<SeGroupEntity,String> {

	@Autowired
	private SeGroupService seGroupService;


	@Override
	public ReactiveCrudService getService() {
		return this.seGroupService;
//		EasyormProperties
	}
}
