package org.zgl.proxy;

public class ParamTypes {
    private Class<?>[] paramTypes;
    private Object[] params;

    public ParamTypes() {
    }

    public ParamTypes(Class<?>[] paramTypes, Object[] params) {
        this.paramTypes = paramTypes;
        this.params = params;
    }

    public Class<?>[] getParamTypes() {
        return paramTypes;
    }

    public void setParamTypes(Class<?>[] paramTypes) {
        this.paramTypes = paramTypes;
    }

    public Object[] getParams() {
        return params;
    }

    public void setParams(Object[] params) {
        this.params = params;
    }
}
