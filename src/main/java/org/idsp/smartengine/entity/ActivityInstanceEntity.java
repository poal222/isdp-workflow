package org.idsp.smartengine.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hswebframework.ezorm.rdb.mapping.annotation.ColumnType;

import javax.persistence.Column;
import javax.persistence.Table;
import java.sql.JDBCType;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Table(name = "se_execution_instance")
public class ActivityInstanceEntity extends BaseProcessEntity {

    @Column(name="process_instance_id",length = 255, nullable = false)
    @Schema(description = "process_instance_id")
    @ColumnType(javaType = String.class, jdbcType = JDBCType.VARCHAR)
    private Long processInstanceId;

    @Column(name="process_definition_id_and_version",length = 255, nullable = false)
    @Schema(description = "process_definition_id_and_version")
    @ColumnType(javaType = String.class, jdbcType = JDBCType.VARCHAR)
    private String processDefinitionActivityId;

    @Column(name="process_definition_activity_id",length = 255, nullable = false)
    @Schema(description = "process_definition_activity_id")
    @ColumnType(javaType = String.class, jdbcType = JDBCType.VARCHAR)
    private String processDefinitionIdAndVersion;
}