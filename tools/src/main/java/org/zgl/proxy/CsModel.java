package org.zgl.proxy;

import java.util.List;

public class CsModel {
    /**所有需要的包*/
    private List<String> packages;
    private String interfaceName;
    private String implementsInterfaceName;
    private String classDesc;
    //方法名 参数名 参数类型 返回类型
    private List<CsMethodModel> methodModels;
    public CsModel() {
    }

    public List<String> getPackages() {
        return packages;
    }

    public void setPackages(List<String> packages) {
        this.packages = packages;
    }

    public String getClassDesc() {
        return classDesc;
    }

    public void setClassDesc(String classDesc) {
        this.classDesc = classDesc;
    }

    public String getInterfaceName() {
        return interfaceName;
    }

    public void setInterfaceName(String interfaceName) {
        this.interfaceName = interfaceName;
    }

    public String getImplementsInterfaceName() {
        return implementsInterfaceName;
    }

    public void setImplementsInterfaceName(String implementsInterfaceName) {
        this.implementsInterfaceName = implementsInterfaceName;
    }

    public List<CsMethodModel> getMethodModels() {
        return methodModels;
    }

    public void setMethodModels(List<CsMethodModel> methodModels) {
        this.methodModels = methodModels;
    }
}