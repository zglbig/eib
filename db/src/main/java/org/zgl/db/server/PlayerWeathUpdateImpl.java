package org.zgl.db.server;

import org.springframework.stereotype.Component;
import org.zgl.DateUtils;
import org.zgl.HttpProxyOutboundHandler;
import org.zgl.config.ConfigFactory;
import org.zgl.datable.CommodityDataTable;
import org.zgl.datable.VipDataTable;
import org.zgl.db.dao.entity.*;
import org.zgl.db.dao.mapper.AutosMapper;
import org.zgl.db.dao.mapper.RebateDialMapper;
import org.zgl.db.dao.mapper.TopUpMapper;
import org.zgl.db.logic.Operation;
import org.zgl.db.logic.giftbag.GiftBagListener;
import org.zgl.db.player.PlayerServerModel;
import org.zgl.db.utils.SpringUtils;
import org.zgl.desc.ServerProxy;
import org.zgl.dto.ItemDto;
import org.zgl.dto.ItemListDto;
import org.zgl.dto.clinet.hall.PawnshopDto;
import org.zgl.error.AppErrorCode;
import org.zgl.error.AppGeneralError;
import org.zgl.service.server.commond.PlayerWeathUpdate;
import org.zgl.service.server.commond.WeathUpdate;
import org.zgl.type.ScenesEnum;
import org.zgl.type.ShopIdEnum;

import java.util.ArrayList;
import java.util.List;
/**
 * @author： 猪哥亮
 * @创建时间： 2018/8/6
 * @文件描述：
 */
@Component
@ServerProxy
public class PlayerWeathUpdateImpl extends Operation implements PlayerWeathUpdate {
    @Override
    public ItemListDto intertWeath(long uid, List<ItemDto> items) {
        Player player = PlayerServerModel.getInstance().getPlayerByPlayerId(uid);
        if (player == null) {
            //用户尚未登陆
            new AppGeneralError(10, "用户尚未登陆");
        }
        Weath weath = player.getWeath();
        items = weath.insertWeath(items);
        weath.updateWeath();
        return new ItemListDto(items);
    }

    @Override
    public ItemListDto reduceWeath(long uid, List<ItemDto> items) {
        Player player = PlayerServerModel.getInstance().getPlayerByPlayerId(uid);
        if (player == null) {
            //用户尚未登陆
            new AppGeneralError(10, "用户尚未登陆");
        }
        Weath weath = player.getWeath();
        items = weath.reduceWeath(items);
        weath.updateWeath();
        return new ItemListDto(items);
    }

    @Override
    public ItemDto checkBet(long uid,int scenesId, long count) {
        Player player = PlayerServerModel.getInstance().getPlayerByPlayerId(uid);
        if (player == null) {
            //用户尚未登陆
            new AppGeneralError(AppErrorCode.ACCOUNT_IS_NOT_LOGIN);
        }
        Weath weath = player.getWeath();
        //这里还要判断在那个场下注了多少
        if (!weath.reduceGold(count)) {
            new AppGeneralError(AppErrorCode.GOLD_NOT_INSUFFICIENT_ERR);
        }
        GiftBagListener.listen(player,scenesId);
        weath.updateGold();
        rebateDial(uid,scenesId,count,false);
        return new ItemDto(1, weath.getGold());
    }

    private void rebateDial(long uid, int scenesId, long gold, boolean isPay) {
        RebateDialMapper mapper = SpringUtils.getBean(RebateDialMapper.class);
        RebateDial dial = mapper.queryRebateDialUid(uid);
        if (dial == null) {
            dial = new RebateDial();
            dial.setUid(uid);
            dial.setGoldAward(0L);
            dial.setTopUpAward(0L);
            dial.setToday(DateUtils.currentDay());
            if (isPay) {
                dial.setDice(0L);
                dial.setToRoom(0L);
                dial.setLottery(0L);
                dial.setTopUp(gold);
            } else {
                dial.setTopUp(0L);
                switch (scenesId) {
                    case 1:
                        dial.setDice(0L);
                        dial.setToRoom(gold);
                        dial.setLottery(0L);
                        break;
                    case 2:
                        dial.setDice(gold);
                        dial.setToRoom(0L);
                        dial.setLottery(0L);
                        break;
                    case 3:
                        dial.setDice(0L);
                        dial.setToRoom(0L);
                        dial.setLottery(gold);
                        break;
                    default:
                        break;
                }
            }
            mapper.insertRebateDial(dial);
        }else {
            if (isPay) {
                dial.setTopUp(dial.getTopUp() + gold);
            } else {
                ScenesEnum scenesEnum = ScenesEnum.getEnum(scenesId);
                switch (scenesEnum) {
                    case THOUSANDS_OF:
                        dial.setToRoom(dial.getToRoom() + gold);
                        break;
                    case DICE:
                        dial.setDice(dial.getDice() + gold);
                        break;
                    case LOTTERY:
                        dial.setLottery(dial.getLottery() + gold);
                        break;
                    default:
                        break;
                }
            }
            mapper.updateRebateDial(dial);
        }
    }

    @Override
    public ItemListDto payWeath(long uid, int productId) {
        Player player = PlayerServerModel.getInstance().getPlayerByPlayerId(uid);
        if (player == null) {
            //用户尚未登陆
            new AppGeneralError(AppErrorCode.ACCOUNT_IS_NOT_LOGIN);
        }
        CommodityDataTable dataTable = CommodityDataTable.get(productId);
        if (dataTable == null) {
            new AppGeneralError(AppErrorCode.NOT_THIS_SHOP, productId);
        }
        ItemListDto dto = null;
        ShopIdEnum shopIdEnum = ShopIdEnum.getType(dataTable.getShopId());
        switch (shopIdEnum) {
            case GOLD:
                dto = gold(player, dataTable);
                updateWeathByOtherScenes(player, dto);
                return dto;
            case DIAMOND:
                dto = diamond(player, dataTable);
                rebateDial(uid,0,productId,true);
                topUp(uid, productId);
                //添加购买日志记录
                return dto;
            case AUTOS:
                dto = auto(player, dataTable);
                return dto;
            case VIP:
                return vip(player,dataTable);
            case GIFT:
                return prop(player,dataTable);
            case PROP:
                return prop(player,dataTable);
            default:
                return null;
        }
    }
    @Override
    public PawnshopDto pawnshop(long uid, int productId, int count) {
        Player p = PlayerServerModel.getInstance().getPlayerByPlayerId(uid);
        CommodityDataTable dataTable = CommodityDataTable.get(productId);
        if (dataTable == null) {
            new AppGeneralError(AppErrorCode.NOT_THIS_SHOP, productId);
        }
        if (!dataTable.getDescribe().equals(2)) {
            new AppGeneralError(AppErrorCode.THIS_SHOP_NOT_PAWNSHOP);
        }
        if (p == null) {
            new AppGeneralError(AppErrorCode.ACCOUNT_IS_NOT_LOGIN);
        }
        Prop prop = p.getProp();
        Weath weath = p.getWeath();
        if (!prop.checkProp(productId, count)) {
            new AppGeneralError(AppErrorCode.GIFT_INSUFFICIENT);
        }
        ItemDto item1 = prop.reduceProp(productId, count);
        long acquireGold = (long) (dataTable.getSelling() * 0.2);
        weath.insertGold(acquireGold);
        weath.updateGold();
        return new PawnshopDto(acquireGold, weath.getGold(), item1.getItemId(), (int) item1.getItemCount());
    }

    /**
     * 需要同步的有金币 vip等级
     *
     * @param
     * @param dto
     */
    public static void updateWeathByOtherScenes(Player p, final ItemListDto dto) {
        final long uid = p.getUid();
        List<String> scenesUir = p.getScenesIds();
        if (scenesUir == null) {
            return;
        }
        scenesUir.forEach(url -> {
            WeathUpdate updateDice = HttpProxyOutboundHandler.createProxy(WeathUpdate.class, url);
            updateDice.updateWeath(uid, dto);
        });
    }

    /**
     * 钻石
     *
     * @return
     */
    private ItemListDto diamond(Player p, CommodityDataTable dataTable) {
        List<ItemDto> items = new ArrayList<>(1);
        items.add(new ItemDto(ShopIdEnum.DIAMOND.id(), dataTable.getCount()));
        return commond(p.getWeath(), items);
    }

    /**
     * 金币
     *
     * @return
     */
    private ItemListDto gold(Player p, CommodityDataTable dataTable) {
        Weath weath = p.getWeath();
        List<ItemDto> items = new ArrayList<>(1);
        items.add(new ItemDto(ShopIdEnum.GOLD.id(), dataTable.getCount()));
        return commond(weath, items);
    }

    /**
     * 获取数据库的座驾
     * @param p
     * @param dataTable
     * @return
     */
    private ItemListDto auto(Player p, CommodityDataTable dataTable) {
        Weath weath = p.getWeath();
        if (!weath.checkWeath(ShopIdEnum.GOLD.id(), dataTable.getSelling())) {
            new AppGeneralError(AppErrorCode.GOLD_COUNT_INSUFFICIENT);
        }
        weath.updateGold();
        AutosMapper mapper = SpringUtils.getBean(AutosMapper.class);
        Autos autos = new Autos();
        autos.setAutoId(dataTable.id());
        autos.setCreateTime(System.currentTimeMillis());
        autos.setCreateTimeStr(DateUtils.nowTime());
        autos.setUid(p.getUid());
        mapper.insertAutos(autos);
        List<ItemDto> items = new ArrayList<>(1);
        items.add(new ItemDto(dataTable.id(), dataTable.getCount()));
        return new ItemListDto(items);
    }

    /**
     * 道具
     * @return
     */
    private ItemListDto prop(Player p, CommodityDataTable dataTable){
        Weath weath =p.getWeath();
        if(!weath.checkWeath(3,dataTable.getSelling())){
            new AppGeneralError(AppErrorCode.INTEGRAL_INSUFFICIENT);
        }
        Prop prop = p.getProp();
        List<ItemDto> items = new ArrayList<>(1);
        items.add(prop.insertProp(dataTable.id(),dataTable.getCount()));
        prop.updateProp();
        return new ItemListDto(items);
    }
    private ItemListDto vip(Player p, CommodityDataTable dataTable){
        Weath weath =p.getWeath();
        int exp = 0;
        try {
            exp = Integer.parseInt(dataTable.getEffect());
        }catch (NumberFormatException e){
//            new LogAppError("获取不到vip的经验值");
        }
        //vip等级、经验
        int vipLv = weath.getVipLv();
        long vipExp = weath.getVipExp();
        //购买到的经验
        long vipBuyExp = exp * dataTable.getCount();
        //vip升到下一级所需经验
        //vip本次购买之后的总经验（用这个去算能升几级）
        vipExp += vipBuyExp;
        while (true) {
            if(vipLv == 25){
                break;
            }
            VipDataTable vipDataTable = VipDataTable.get(vipLv + 1);
            //升级下一级所需经验减去vip当前经验+本次购买获得的经验如果小于0说明要升级
            long vipTemp = vipDataTable.getExp() - vipExp;
            if (vipTemp < 0) {
                //vip等级+1
                vipLv++;
                vipExp -= vipDataTable.getExp();
                //经验值归0
            }else {
                break;
            }
        }
        //设置vip等级
        weath.setVipExp(vipExp);
        weath.setVipLv(vipLv);
        List<ItemDto> items = new ArrayList<>(2);
        items.add(new ItemDto(ShopIdEnum.VIP_LV.id(),vipLv));
        items.add(new ItemDto(ShopIdEnum.VIP_EXP.id(),vipExp));
        weath.updateWeath();
        return new ItemListDto(items);
    }
    private ItemListDto commond(Weath weath, List<ItemDto> items) {
        items = weath.insertWeath(items);
        weath.updateWeath();
        return new ItemListDto(items);
    }

    private void topUp(long uid, int produceId) {
        TopUp up = new TopUp();
        up.setUid(uid);
        up.setProductId(produceId);
        up.setMoney(produceId);
        up.setCreateTime(System.currentTimeMillis());
        up.setCreateTimeStr(DateUtils.nowTime());
        TopUpMapper mapper = SpringUtils.getBean(TopUpMapper.class);
        mapper.insertTopUp(up);
    }
}
