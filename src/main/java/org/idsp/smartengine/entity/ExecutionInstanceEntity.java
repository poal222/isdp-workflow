package org.idsp.smartengine.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.hswebframework.ezorm.rdb.mapping.annotation.ColumnType;

import javax.persistence.Column;
import javax.persistence.Table;
import java.sql.JDBCType;


@Data
@Table(name = "se_execution_instance")
public class ExecutionInstanceEntity  extends BaseProcessEntity {


    @Column(name="process_instance_id",length = 255, nullable = false)
    @Schema(description = "process_instance_id")
    @ColumnType(javaType = String.class, jdbcType = JDBCType.VARCHAR)
    private String processInstanceId;

    @Column(name="process_definition_id_and_version",length = 255, nullable = false)
    @Schema(description = "process_definition_id_and_version")
    @ColumnType(javaType = String.class, jdbcType = JDBCType.VARCHAR)
    private String processDefinitionIdAndVersion;

    @Column(name="process_definition_activity_id",length = 255, nullable = false)
    @Schema(description = "process_definition_activity_id")
    @ColumnType(javaType = String.class, jdbcType = JDBCType.VARCHAR)
    private String processDefinitionActivityId;

    @Column(name="activity_instance_id",length = 255, nullable = false)
    @Schema(description = "activity_instance_id")
    @ColumnType(javaType = String.class, jdbcType = JDBCType.VARCHAR)
    private String activityInstanceId;

    @Column(name="active",length = 255, nullable = false)
    @Schema(description = "active")
    @ColumnType(javaType = String.class, jdbcType = JDBCType.VARCHAR)
    private String incomeTransitionId;

    @Column(name="title",length = 255, nullable = false)
    @Schema(description = "title")
    @ColumnType(javaType = String.class, jdbcType = JDBCType.VARCHAR)
    private String incomeActivityInstanceId;

    @Column(name="title",length = 255, nullable = false)
    @Schema(description = "title")
    @ColumnType(javaType = Boolean.class, jdbcType = JDBCType.BOOLEAN)
    private boolean active;
}

