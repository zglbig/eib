package org.zgl.build.common;

/**
 * @author
 */
public class PathCnf {
    /**扫描路劲*/
    private String scanPath;
    /**cs生成路劲*/
    private String dtoCsPath;
    private String commandCodePath;
    private String operateCommandRecivePath;
    private String errorCodeExcelPath;
    /**OperateCommandRecivePackage 的包路劲*/
    private String operateCommandRecivePackage;
    /**OperateCommandAbstract 的包路劲*/
    private String operateCommandAbstractPackage;
    private String commandCodePackage;
    /**导表类生成路径*/
    private String datableCsBuildPath;
    public String getScanPath() {
        return scanPath;
    }

    public String getCommandCodePackage() {
        return commandCodePackage;
    }

    public void setCommandCodePackage(String commandCodePackage) {
        this.commandCodePackage = commandCodePackage;
    }

    public void setScanPath(String scanPath) {
        this.scanPath = scanPath;
    }

    public String getDtoCsPath() {
        return dtoCsPath;
    }

    public void setDtoCsPath(String dtoCsPath) {
        this.dtoCsPath = dtoCsPath;
    }

    public String getCommandCodePath() {
        return commandCodePath;
    }

    public void setCommandCodePath(String commandCodePath) {
        this.commandCodePath = commandCodePath;
    }

    public String getOperateCommandRecivePath() {
        return operateCommandRecivePath;
    }

    public void setOperateCommandRecivePath(String operateCommandRecivePath) {
        this.operateCommandRecivePath = operateCommandRecivePath;
    }

    public String getOperateCommandRecivePackage() {
        return operateCommandRecivePackage;
    }

    public void setOperateCommandRecivePackage(String operateCommandRecivePackage) {
        this.operateCommandRecivePackage = operateCommandRecivePackage;
    }

    public String getErrorCodeExcelPath() {
        return errorCodeExcelPath;
    }

    public void setErrorCodeExcelPath(String errorCodeExcelPath) {
        this.errorCodeExcelPath = errorCodeExcelPath;
    }

    public String getOperateCommandAbstractPackage() {
        return operateCommandAbstractPackage;
    }

    public void setOperateCommandAbstractPackage(String operateCommandAbstractPackage) {
        this.operateCommandAbstractPackage = operateCommandAbstractPackage;
    }

    public String getDatableCsBuildPath() {
        return datableCsBuildPath;
    }

    public void setDatableCsBuildPath(String datableCsBuildPath) {
        this.datableCsBuildPath = datableCsBuildPath;
    }
}
