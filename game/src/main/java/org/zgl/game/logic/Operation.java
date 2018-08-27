package org.zgl.game.logic;

import org.zgl.game.logic.player.Player;
import org.zgl.game.logic.room.Room;

/**
 * @author： 猪哥亮
 * @创建时间： 2018/7/26
 * @文件描述：
 */
public abstract class Operation implements Cloneable{
    protected Player player;
    protected Room room;
    public void setPlayer(Player player){
        this.player = player;
    }
    public void setRoom(Room room){
        this.room = room;
    }
    /**
     * 深度拷贝 因为spring 不对多例模式注解的bean的生命周期进行管理
     * 所以所有的接口实现类只能使用单利进行注解管理
     * 而因为Operation抽象类有两个成员变量Player和Room 如果不对其重新赋值将会有并发问题
     * 在这里使用给这两个成员变量赋null值进行深度拷贝
     * @return
     */
    private Operation deepCopy(){
        this.player = null;
        this.room = null;
        return this;
    }

    @Override
    public Operation clone(){
        Operation o = null;
        try {
            Object ob =  super.clone();
            o = ((Operation) ob).deepCopy();
        }catch (CloneNotSupportedException e){
            e.printStackTrace();
        }
        return o;
    }
}
