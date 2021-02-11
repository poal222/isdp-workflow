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
@Table(name = "se_deployment_instance")
public class DeploymentInstanceEntity extends BaseProcessEntity {

    @Column(name="process_definition_id",length = 255, nullable = false)
    @Schema(description = "process_definition_id")
    @ColumnType(javaType = String.class, jdbcType = JDBCType.VARCHAR)
    private String processDefinitionId;

    @Column(name="process_definition_version",length = 255, nullable = false)
    @Schema(description = "process_definition_version")
    @ColumnType(javaType = String.class, jdbcType = JDBCType.VARCHAR)
    private String processDefinitionVersion;

    @Column(name="process_definition_type",length = 255, nullable = false)
    @Schema(description = "process_definition_type")
    @ColumnType(javaType = String.class, jdbcType = JDBCType.VARCHAR)
    private String processDefinitionType;

    @Column(name="process_definition_code",length = 255, nullable = false)
    @Schema(description = "process_definition_code")
    @ColumnType(javaType = String.class, jdbcType = JDBCType.VARCHAR)
    private String processDefinitionCode;

    @Column(name="process_definition_name",length = 255, nullable = false)
    @Schema(description = "process_definition_name")
    @ColumnType(javaType = String.class, jdbcType = JDBCType.VARCHAR)
    private String processDefinitionName;

    @Column(name="process_definition_desc",length = 255, nullable = false)
    @Schema(description = "process_definition_desc")
    @ColumnType(javaType = String.class, jdbcType = JDBCType.VARCHAR)
    private String processDefinitionDesc;

    @Column(name="process_definition_content",length = 255, nullable = false)
    @Schema(description = "process_definition_content")
    @ColumnType(javaType = String.class, jdbcType = JDBCType.VARCHAR)
    private String processDefinitionContent;

    @Column(name="deployment_user_id",length = 255, nullable = false)
    @Schema(description = "deployment_user_id")
    @ColumnType(javaType = String.class, jdbcType = JDBCType.VARCHAR)
    private String deploymentUserId;

    @Column(name="deployment_status",length = 255, nullable = false)
    @Schema(description = "deployment_status")
    @ColumnType(javaType = String.class, jdbcType = JDBCType.VARCHAR)
    private String deploymentStatus;

    @Column(name="logic_status",length = 255, nullable = false)
    @Schema(description = "logic_status")
    @ColumnType(javaType = String.class, jdbcType = JDBCType.VARCHAR)
    private String logicStatus;


}

