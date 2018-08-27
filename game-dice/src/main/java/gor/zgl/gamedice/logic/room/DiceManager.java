package gor.zgl.gamedice.logic.room;


import org.zgl.RandomUtils;
import org.zgl.build.excel.read.StaticConfigMessage;
import org.zgl.datable.DiceDataTable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DiceManager {
    /**围点枚举*/
    public enum WeiEnum{
        /**什么都不是*/
        NONE(0),
        /**围点*/
        WEI(1),
        /**不是围点*/
        NOT_WEI(2);
        private int id;
        WeiEnum(int id) {
            this.id = id;
        }
        public int getId() {
            return id;
        }
    }
    private static DiceManager instance;
    /**所有点*/
    private static final List<DiceDataTable> DICE_DATE;
    /**所有的围点*/
    private static final Map<Integer,DiceDataTable> WEI_TOU;
    /**所有不是围的点*/
    private static final Map<Integer,List<DiceDataTable>> NOT_IS_WEI_TOU;
    private static final List<DiceDataTable> NOT_IS_WEI_TOU_LIST;
    static {
        Map<Serializable,Object> o = StaticConfigMessage.getInstance().getMap(DiceDataTable.class);
        WEI_TOU = new HashMap<>();
        NOT_IS_WEI_TOU = new HashMap<>();
        DICE_DATE = new ArrayList<>(o.size());
        NOT_IS_WEI_TOU_LIST = new ArrayList<>();
        for(Object ox : o.values()){
            DiceDataTable dataTable = (DiceDataTable) ox;
            if(dataTable.getWeiTou() == WeiEnum.WEI.id){
                WEI_TOU.put(dataTable.getCount(),dataTable);
            }else if(dataTable.getWeiTou() == WeiEnum.NOT_WEI.id){
                NOT_IS_WEI_TOU_LIST.add(dataTable);
                List<DiceDataTable> l = NOT_IS_WEI_TOU.getOrDefault(dataTable.getCount(),null);
                if(l == null){
                    l = new ArrayList<>();
                }
                l.add(dataTable);
                NOT_IS_WEI_TOU.put(dataTable.getCount(),l);
            }
            DICE_DATE.add(dataTable);

        }
    }
    public static DiceManager getInstance() {
        if(instance == null){
            instance = new DiceManager();
        }
        return instance;
    }
    public List<DiceDataTable> getDiceDate(){
        return DICE_DATE;
    }
    public DiceDataTable getNotWeiByPosition(int position){
        List<DiceDataTable> dataTables = NOT_IS_WEI_TOU.get(position);
        if(dataTables == null || dataTables.size() <= 0){
            throw new RuntimeException("获取不是围骰点数错误");
        }
        if(dataTables.size() == 1){
            return dataTables.get(0);
        }else {
            return dataTables.get(RandomUtils.getRandom(0,dataTables.size()-1));
        }
    }
    public List<DiceDataTable> getWeiByPosition(){
        return new ArrayList<>(WEI_TOU.values());
    }
    public List<DiceDataTable> getNotWeiByPosition(){
        return new ArrayList<>(NOT_IS_WEI_TOU_LIST);
    }
    public Map<Integer, DiceDataTable> getWeiTou() {
        return new HashMap<>(WEI_TOU);
    }

}
