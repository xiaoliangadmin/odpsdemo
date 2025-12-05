package com.fd.model;

public class DataModel {
    //节点相关信息
    private Long projectId;//工作空间id
    private String projectIdentifier;//工作空间名称
    private String folderName;//业务流程名称
    private String fileName;//任务名称
    private Integer fileType;//任务类型
    private String fileDescription;//任务描述
    private String paraValue;//参数
    private String owner;//任务责任人
    private Boolean stop;//是否暂停调度
    private String rerunMode;//重跑属性
    private Integer autoRerunTimes;//重跑次数
    private Integer rerunIntervalMillis;//重跑间隔时间 毫秒
    private Long startEffectDate;//生效时间
    private Long endEffectDate;//失效时间
    private String cycleType;//调度周期
    private String cronExpress;//调度时间
    private String dependentType;//是否依赖上一周期 //目前没有追究依赖上一周期节点的情况
    private String inputList;//父节点名称,多个用','隔开
    private String resourceGroupIdentifier;//调度资源组
    private String content;//节点内容

    //datax的相关信息 源端
    private String fromStepType;//源端数据源类型
    private String fromDataSource;//源端数据源名称
    private String fromSelectedDatabase;//源端数据库名称
    private String fromTableName;//源端数据表名称（或者为oss文件名+路径）
    private String fromWhere;//源端where条件
    private String fromSplitPk;//源端切分键
    private String fromFileFormat;//源端文本类型
    private String fromFieldDelimiter;//源端列分隔符
    private String fromLineDelimiter;//源端行分隔符
    private String fromEncoding;//源端字符集
    private String fromRejectColumn;//填写不需要同步的字段使用逗号隔开
    //datax的相关信息 目标端
    private String toStepType;//目标端数据源类型
    private String toDataSource;//目标端数据源名称
    private String toSelectedDatabase;//目标端数据库名称(暂时用不到)
    private String toTableName;//目标端数据表名称（或者为oss文件名+路径）
    private String toPartition;//目标端表分区
    private Boolean toTruncate;//目标端插入数据是否清空已有数据
    private String toWriteMode;//目标端是否替换原有文件
    private String toFileFormat;//目标端文本类型
    private String toFieldDelimiter;//目标端列分隔符
    private String toLineDelimiter;//目标端行分隔符
    private String toEncoding;//目标端字符集
    //datax的相关信息 其他
    private String concurrent;//期望最大并发数
    private String throttle;//是否限流
    private String record;//允许错误记录数
    private String diResourceGroupId;//集成资源组

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public String getProjectIdentifier() {
        return projectIdentifier;
    }

    public void setProjectIdentifier(String projectIdentifier) {
        this.projectIdentifier = projectIdentifier;
    }

    public String getFolderName() {
        return folderName;
    }

    public void setFolderName(String folderName) {
        this.folderName = folderName;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Integer getFileType() {
        return fileType;
    }

    public void setFileType(Integer fileType) {
        this.fileType = fileType;
    }

    public String getFileDescription() {
        return fileDescription;
    }

    public void setFileDescription(String fileDescription) {
        this.fileDescription = fileDescription;
    }

    public String getParaValue() {
        return paraValue;
    }

    public void setParaValue(String paraValue) {
        this.paraValue = paraValue;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public Boolean getStop() {
        return stop;
    }

    public void setStop(Boolean stop) {
        this.stop = stop;
    }

    public String getRerunMode() {
        return rerunMode;
    }

    public void setRerunMode(String rerunMode) {
        this.rerunMode = rerunMode;
    }

    public Integer getAutoRerunTimes() {
        return autoRerunTimes;
    }

    public void setAutoRerunTimes(Integer autoRerunTimes) {
        this.autoRerunTimes = autoRerunTimes;
    }

    public Integer getRerunIntervalMillis() {
        return rerunIntervalMillis;
    }

    public void setRerunIntervalMillis(Integer rerunIntervalMillis) {
        this.rerunIntervalMillis = rerunIntervalMillis;
    }

    public Long getStartEffectDate() {
        return startEffectDate;
    }

    public void setStartEffectDate(Long startEffectDate) {
        this.startEffectDate = startEffectDate;
    }

    public Long getEndEffectDate() {
        return endEffectDate;
    }

    public void setEndEffectDate(Long endEffectDate) {
        this.endEffectDate = endEffectDate;
    }

    public String getCycleType() {
        return cycleType;
    }

    public void setCycleType(String cycleType) {
        this.cycleType = cycleType;
    }

    public String getCronExpress() {
        return cronExpress;
    }

    public void setCronExpress(String cronExpress) {
        this.cronExpress = cronExpress;
    }

    public String getDependentType() {
        return dependentType;
    }

    public void setDependentType(String dependentType) {
        this.dependentType = dependentType;
    }

    public String getInputList() {
        return inputList;
    }

    public void setInputList(String inputList) {
        this.inputList = inputList;
    }

    public String getResourceGroupIdentifier() {
        return resourceGroupIdentifier;
    }

    public void setResourceGroupIdentifier(String resourceGroupIdentifier) {
        this.resourceGroupIdentifier = resourceGroupIdentifier;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getFromStepType() {
        return fromStepType;
    }

    public void setFromStepType(String fromStepType) {
        this.fromStepType = fromStepType;
    }

    public String getFromDataSource() {
        return fromDataSource;
    }

    public void setFromDataSource(String fromDataSource) {
        this.fromDataSource = fromDataSource;
    }

    public String getFromSelectedDatabase() {
        return fromSelectedDatabase;
    }

    public void setFromSelectedDatabase(String fromSelectedDatabase) {
        this.fromSelectedDatabase = fromSelectedDatabase;
    }

    public String getFromTableName() {
        return fromTableName;
    }

    public void setFromTableName(String fromTableName) {
        this.fromTableName = fromTableName;
    }

    public String getFromWhere() {
        return fromWhere;
    }

    public void setFromWhere(String fromWhere) {
        this.fromWhere = fromWhere;
    }

    public String getFromSplitPk() {
        return fromSplitPk;
    }

    public void setFromSplitPk(String fromSplitPk) {
        this.fromSplitPk = fromSplitPk;
    }

    public String getFromFileFormat() {
        return fromFileFormat;
    }

    public void setFromFileFormat(String fromFileFormat) {
        this.fromFileFormat = fromFileFormat;
    }

    public String getFromFieldDelimiter() {
        return fromFieldDelimiter;
    }

    public void setFromFieldDelimiter(String fromFieldDelimiter) {
        this.fromFieldDelimiter = fromFieldDelimiter;
    }

    public String getFromLineDelimiter() {
        return fromLineDelimiter;
    }

    public void setFromLineDelimiter(String fromLineDelimiter) {
        this.fromLineDelimiter = fromLineDelimiter;
    }

    public String getFromEncoding() {
        return fromEncoding;
    }

    public void setFromEncoding(String fromEncoding) {
        this.fromEncoding = fromEncoding;
    }

    public String getToStepType() {
        return toStepType;
    }

    public void setToStepType(String toStepType) {
        this.toStepType = toStepType;
    }

    public String getToDataSource() {
        return toDataSource;
    }

    public void setToDataSource(String toDataSource) {
        this.toDataSource = toDataSource;
    }

    public String getToSelectedDatabase() {
        return toSelectedDatabase;
    }

    public void setToSelectedDatabase(String toSelectedDatabase) {
        this.toSelectedDatabase = toSelectedDatabase;
    }

    public String getToTableName() {
        return toTableName;
    }

    public void setToTableName(String toTableName) {
        this.toTableName = toTableName;
    }

    public String getToPartition() {
        return toPartition;
    }

    public void setToPartition(String toPartition) {
        this.toPartition = toPartition;
    }

    public Boolean getToTruncate() {
        return toTruncate;
    }

    public void setToTruncate(Boolean toTruncate) {
        this.toTruncate = toTruncate;
    }

    public String getToWriteMode() {
        return toWriteMode;
    }

    public void setToWriteMode(String toWriteMode) {
        this.toWriteMode = toWriteMode;
    }

    public String getToFileFormat() {
        return toFileFormat;
    }

    public void setToFileFormat(String toFileFormat) {
        this.toFileFormat = toFileFormat;
    }

    public String getToFieldDelimiter() {
        return toFieldDelimiter;
    }

    public void setToFieldDelimiter(String toFieldDelimiter) {
        this.toFieldDelimiter = toFieldDelimiter;
    }

    public String getToLineDelimiter() {
        return toLineDelimiter;
    }

    public void setToLineDelimiter(String toLineDelimiter) {
        this.toLineDelimiter = toLineDelimiter;
    }

    public String getToEncoding() {
        return toEncoding;
    }

    public void setToEncoding(String toEncoding) {
        this.toEncoding = toEncoding;
    }

    public String getConcurrent() {
        return concurrent;
    }

    public void setConcurrent(String concurrent) {
        this.concurrent = concurrent;
    }

    public String getThrottle() {
        return throttle;
    }

    public void setThrottle(String throttle) {
        this.throttle = throttle;
    }

    public String getRecord() {
        return record;
    }

    public void setRecord(String record) {
        this.record = record;
    }

    public String getDiResourceGroupId() {
        return diResourceGroupId;
    }

    public void setDiResourceGroupId(String diResourceGroupId) {
        this.diResourceGroupId = diResourceGroupId;
    }
    public String getFromRejectColumn() {
        return fromRejectColumn;
    }

    public void setFromRejectColumn(String fromRejectColumn) {
        this.fromRejectColumn = fromRejectColumn;
    }
}
