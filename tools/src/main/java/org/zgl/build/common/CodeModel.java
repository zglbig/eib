package org.zgl.build.common;

public class CodeModel {
    private String createPath;
    private String id;
    private Class<?> clazz;

    public CodeModel() {
    }

    public CodeModel(String createPath,String id, Class<?> clazz) {
        this.createPath = createPath;
        this.id = id;
        this.clazz = clazz;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Class<?> getClazz() {
        return clazz;
    }

    public String getCreatePath() {
        return createPath;
    }

    public void setCreatePath(String createPath) {
        this.createPath = createPath;
    }

    public void setClazz(Class<?> clazz) {
        this.clazz = clazz;
    }
}