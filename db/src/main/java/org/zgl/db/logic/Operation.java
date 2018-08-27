package org.zgl.db.logic;


/**
 * @author： 猪哥亮
 * @创建时间： 2018/8/20
 * @文件描述：
 */
public abstract class Operation implements Cloneable {
    protected String addr;

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }
    /**
     * 深度拷贝 因为spring 不对多例模式注解的bean的生命周期进行管理
     * 所以所有的接口实现类只能使用单利进行注解管理
     * 而因为Operation抽象类有两个成员变量Player和Room 如果不对其重新赋值将会有并发问题
     * 在这里使用给这两个成员变量赋null值进行深度拷贝
     * @return
     */
    private Operation deepCopy(){
        this.addr = "";
        return this;
    }

    @Override
    public Operation clone(){
        Operation op = null;
        try {
            Object o = super.clone();
            op = ((Operation) o).deepCopy();
        }catch (CloneNotSupportedException e){
            e.printStackTrace();
        }
        return op;
    }
}
