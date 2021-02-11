package org.idsp.smartengine.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.hswebframework.ezorm.rdb.mapping.annotation.ColumnType;
import org.hswebframework.ezorm.rdb.mapping.annotation.DefaultValue;
import org.hswebframework.web.api.crud.entity.GenericEntity;
import org.hswebframework.web.crud.generator.Generators;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.JDBCType;
import java.util.Date;


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

	@Column(name = "gmt_create")
	@Id
	@Schema(description = "gmt_create")
	@ColumnType(javaType = Date.class, jdbcType = JDBCType.DATE)
	@DefaultValue(generator = Generators.CURRENT_TIME)//逻辑默认值
	private Date gmtCreate;

	@Column(name = "gmt_modified")
	@ColumnType(javaType = Date.class, jdbcType = JDBCType.DATE)
	@Id
	@Schema(description = "gmt_modified")
	@DefaultValue(generator = Generators.CURRENT_TIME)//逻辑默认值
	private Date gmtModified;


}
