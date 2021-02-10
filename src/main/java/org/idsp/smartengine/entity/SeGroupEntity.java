package org.idsp.smartengine.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.hswebframework.web.api.crud.entity.GenericEntity;

import javax.persistence.Column;
import javax.persistence.Table;


@Table(name = "se_group")
@Getter
@Setter
public class SeGroupEntity extends GenericEntity<String> {

	@Column(length = 64, nullable = false)
	@Schema(description = "流程分组名称")
	private String name;


	@Column(length = 64, nullable = false)
	@Schema(description = "租户id")
	private String tenantId;


}
