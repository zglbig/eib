package org.zgl.build.excel.create;


import org.zgl.build.desc.DataTable;
import org.zgl.build.excel.read.DataTableMessage;

/**
 * @author
 */
@DataTable
public class ExcelErrorCodeModule implements DataTableMessage,Comparable<ExcelErrorCodeModule> {
    private final int id;
    private final String name;
    private final String value;

    public ExcelErrorCodeModule() {
        this.id = 0;
        this.name = "";
        this.value = "";
    }

    public ExcelErrorCodeModule(int id, String name, String value) {
        this.id = id;
        this.name = name;
        this.value = value;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }

    @Override
    public int id() {
        return id;
    }

    @Override
    public void afterInit() {

    }

    @Override
    public int compareTo(ExcelErrorCodeModule o) {
        return this.id - o.getId();
    }
}