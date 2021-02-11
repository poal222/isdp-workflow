package org.idsp.smartengine.entity;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.hswebframework.ezorm.rdb.mapping.annotation.ColumnType;

import javax.persistence.Column;
import javax.persistence.Table;
import java.sql.JDBCType;

@Data
@Table(name = "se_process_instance")
public class ProcessInstanceEntity extends BaseProcessEntity{


    @Column(name="process_definition_id_and_version",length = 255, nullable = true)
    @Schema(description = "process_definition_id_and_version")
    @ColumnType(javaType = String.class, jdbcType = JDBCType.VARCHAR)
    private String processDefinitionIdAndVersion;

    @Column(name="start_user_id",length = 255, nullable = true)
    @Schema(description = "start_user_id")
    @ColumnType(javaType = String.class, jdbcType = JDBCType.VARCHAR)
    private String startUserId;

    @Column(name="parent_process_instance_id",length = 20, nullable = true)
    @Schema(description = "parent_process_instance_id")
    @ColumnType(javaType = Long.class, jdbcType = JDBCType.BIGINT)
    private Long parentProcessInstanceId;

    @Column(name="parent_execution_instance_id",length = 20, nullable = true)
    @Schema(description = "parent_execution_instance_id")
    @ColumnType(javaType = Long.class, jdbcType = JDBCType.BIGINT)
    private Long parentExecutionInstanceId;

    @Column(name="status",length = 255, nullable = true)
    @Schema(description = "status")
    @ColumnType(javaType = String.class, jdbcType = JDBCType.VARCHAR)
    private String status;

    @Column(name="process_definition_type",length = 255, nullable = true)
    @Schema(description = "process_definition_type")
    @ColumnType(javaType = String.class, jdbcType = JDBCType.VARCHAR)
    private String processDefinitionType;

    @Column(name="biz_unique_id",length = 255, nullable = true)
    @Schema(description = "biz_unique_id")
    @ColumnType(javaType = String.class, jdbcType = JDBCType.VARCHAR)
    private String bizUniqueId;

    @Column(name="reason",length = 255, nullable = true)
    @Schema(description = "reason")
    @ColumnType(javaType = String.class, jdbcType = JDBCType.VARCHAR)
    private String reason;

    @Column(name="title",length = 255, nullable = true)
    @Schema(description = "title")
    @ColumnType(javaType = String.class, jdbcType = JDBCType.VARCHAR)
    private String title;

    @Column(name="tag",length = 255, nullable = true)
    @Schema(description = "tag")
    @ColumnType(javaType = String.class, jdbcType = JDBCType.VARCHAR)
    private String tag;

    @Column(name="comment",length = 255, nullable = true)
    @Schema(description = "comment")
    @ColumnType(javaType = String.class, jdbcType = JDBCType.VARCHAR)
    private String comment;
}
