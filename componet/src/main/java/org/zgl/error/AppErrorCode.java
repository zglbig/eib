package org.zgl.error;

import org.zgl.build.desc.ExcelInversionToAnn;
import org.zgl.build.desc.ExcelValue;

@ExcelInversionToAnn
public final class AppErrorCode {
    /**
     * 说明 1-10000段是所有服务器异常
     *      10001 - 20000段是db服务器异常
     *      20001 - 30000段是经典千王异常
     *      30001 - 40000 段是骰子场异常
     *      40001 - 50000段是万人场异常
     *      50001 - 60000段是天天乐异常
     *      60001 - 70000段是商城，家族等服的异常
     */
    @ExcelValue(value = "没有这这个操作")
    public final static int SERVER_NOT_THIS_OPERATION = 101;
    @ExcelValue(value = "操作成功")
    public final static int SUCCESS = 200;
    @ExcelValue(value = "服务器异常")
    public final static int SERVER_ERROR = 404;
    @ExcelValue(value = "服务器异常")
    public final static int GOLD_NOT_INSUFFICIENT_ERR = 405;
    @ExcelValue(value = "当前房间没有该账号")
    public final static int THIS_ROOM_IS_NOT_PLAYER = 406;
    @ExcelValue(value = "当前房间服务器正在维护请先去别得场玩玩吧")
    public final static int THIS_GAME_IS_UPDATE_ERR = 407;
    @ExcelValue(value = "注册链接的uid参数异常")
    public final static int REGISTER_UID_ERR = 408;
    @ExcelValue(value = "消息参数错误{0}")
    public final static int PARAMETER_ERR = 409;
    @ExcelValue(value = "连接的签名不正确")
    public final static int SIGIN_ERR = 410;
    @ExcelValue(value = "当前房间正在维护更新，请到别的房间逛逛把！")
    public final static int NOW_SERVER_UPDATE = 411;
    @ExcelValue(value = "服务器尚未启动完成！")
    public final static int SERVER_NOT_INIT_SUCCESS = 412;
    @ExcelValue(value = "不是房间操作！")
    public final static int NOT_ROOM_OPERATION = 413;
    @ExcelValue(value = "数据解析异常！")
    public final static int DATA_ANALYSIS_ERR = 414;
    @ExcelValue(value = "数据异常！")
    public final static int DATA_ERR = 415;
    /**-----------------------------1-10000段是所有服务器异常--------------------*/
    @ExcelValue(value = "账号在其他地方登陆")
    public final static int ACCOUNT_IS_ONLINE_ERR = 10001;
    @ExcelValue(value = "账号尚未注册")
    public final static int ACCOUNT_IS_NOT_REGIST = 10002;
    @ExcelValue(value = "账号尚未登陆")
    public final static int ACCOUNT_IS_NOT_LOGIN = 10003;
    @ExcelValue(value = "没有{0}这个id对应的商品！")
    public final static int NOT_THIS_SHOP = 10004;
    @ExcelValue(value = "今天已经签到过了！")
    public final static int TODAY_IS_SIGIN = 10005;
    @ExcelValue(value = "今天的签到轮盘次数已经用完！")
    public final static int SIGIN_DIAL_COUNT_IS_NULL = 10006;
    @ExcelValue(value = "没有uid为{0}的这个用户！")
    public final static int NOT_THIS_USER = 10007;
    @ExcelValue(value = "您的vip等级每对方高！")
    public final static int VIP_LV_INSUFFICIENT= 10008;
    @ExcelValue(value = "赠送的礼物不存在！")
    public final static int GIFT_NOT_ERROR= 10009;
    @ExcelValue(value = "该玩家不是您的好友无法删除！")
    public final static int PLAYER_NOT_IS_FRIEND = 10009;
    @ExcelValue(value = "该玩家不是您的好友不能和他进行聊天！")
    public final static int PLAYER_NOT_IS_FRIEND_CAN_NOT_CHAT = 10010;
    @ExcelValue(value = "礼物不足不能当！")
    public final static int GIFT_INSUFFICIENT= 10011;
    @ExcelValue(value = "该商品为非卖品，不能当！")
    public final static int THIS_SHOP_NOT_PAWNSHOP = 10012;
    @ExcelValue(value = "您没在银行存钱，不能取钱！")
    public final static int BANK_NOT_GOLD = 10013;
    @ExcelValue(value = "金币不足，您在银行里只存了{0},而您要取{1}！")
    public final static int BANK_GOLD_INSUFFICIENT = 10014;
    @ExcelValue(value = "没有uid为{0}对应的活动数据！")
    public final static int NOT_ACTIVITY_DATA = 10015;
    @ExcelValue(value = "没有uid为{0}对应的玩家！")
    public final static int NOT_PLAYER = 10016;
    @ExcelValue(value = "一个玩家不能被多个人邀请")
    public final static int DO_NOT_EACH_OHTER_INVITION = 10017;
    @ExcelValue(value = "两个玩家之间不能相互邀请")
    public final static int NOT_EACH_OHTER_INVITION = 10018;
    @ExcelValue(value = "您的返利轮盘还没有可以抽奖得次数，先去完成任务吧！")
    public final static int REBATE_DIAL_NOT_AWARD = 10019;
    @ExcelValue(value = "该该红包已经被领完，试试别的吧！")
    public final static int RED_PACKET_RECEIVE_OVER = 10020;
    @ExcelValue(value = "今天的成长礼包任务还没完成哟！")
    public final static int TODAY_GIFT_BAG_NOT_DONE = 10021;
    @ExcelValue(value = "当天奖励已经领取")
    public final static int AWARD_GET_ERR = 10022;
    @ExcelValue(value = "您的积分不足")
    public final static int INTEGRAL_INSUFFICIENT = 10023;
    /**-----------------------------20001 - 30000段是经典千王异常--------------------*/
    @ExcelValue(value = "金币不足{0}不能在{1}做准备")
    public final static int GOLD_NOT_INSUFFICIENT = 20001;
    @ExcelValue(value = "房间已经开局，不能准备")
    public final static int ROOM_IS_START_CAN_NOT_READY = 20002;
    @ExcelValue(value = "您还还没有准备")
    public final static int PLAYER_NOT_READY = 20003;
    @ExcelValue(value = "已经有玩家全压，您不能选择下注，只能选择全压或者弃牌")
    public final static int HAS_PLAYER_BET_ALL = 20004;
    @ExcelValue(value = "选择下注的筹码位置不正确")
    public final static int BET_CHIP_POSITION_ERROR = 20005;
    @ExcelValue(value = "下注金额不足")
    public final static int BRT_GOLD_NOT_INSUFFICIENT = 20006;
    @ExcelValue(value = "超时下注")
    public final static int BET_TIME_OUT = 20007;
    @ExcelValue(value = "还没轮到您操作")
    public final static int NOT_IS_PLAYER_OPERATION = 20008;
    @ExcelValue(value = "您已经胜出，不能再弃牌了")
    public final static int LAST_ONE_PLAYER_CAN_NOT_GIVE_UP = 20009;
    @ExcelValue(value = "上个玩家选择没有看牌全压，所以您也不能看牌")
    public final static int BET_ALL_NOT_LOOCK_CARD = 20010;
    @ExcelValue(value = "房间没有uid为：{0}对应的玩家")
    public final static int NOT_THIS_UID_PLAYER = 20011;
    @ExcelValue(value = "玩家：{0}已经输掉，不能再和他比牌了")
    public final static int PLAYER_IS_LOSE = 20012;
    @ExcelValue(value = "只有最后两个人的时候才能全压哟，现在房间还有{0}个人！")
    public final static int PLAYER_EXCEED_BET_LIMIT = 20013;
    @ExcelValue(value = "当前选的下注筹码位置比房间当前筹码位置小，无法下注！")
    public final static int CHIP_POS_ERR = 20014;
    @ExcelValue(value = "房间已经开局，不能把人踢出房间！")
    public final static int ROOM_IS_START_NOT_KICKING_PLAYER = 20015;
    @ExcelValue(value = "对方vip等级比您高，不能将他踢出房间！")
    public final static int TARGET_VIP_HIGTH = 20016;
    @ExcelValue(value = "您的踢人卡不足，不能将对方提出房间！")
    public final static int KICKING_CARD_INSUFFICIENT = 20017;
    /**-----------------------------30001 - 40000 段是骰子场异常--------------------*/
    @ExcelValue(value = "房间已经在结算，不能清除下注！")
    public final static int DICE_SETTLE_ACCOUNTS_NOT_CLEAR_BET = 30001;
    @ExcelValue(value = "您的踢人卡不足！")
    public final static int KICKING_INSUFFICIENT = 30002;
    @ExcelValue(value = "对方不在位置上！")
    public final static int PLAYER_NOT_IN_POSITION = 30003;
    /**-----------------------------40001 - 50000段是万人场异常--------------------*/
    @ExcelValue(value = "当前选的下注筹码位置比房间当前筹码位置小，无法下注！")
    public final static int BANKER_CAN_NOT_BET = 40001;
    @ExcelValue(value = "本局投注达到上限！")
    public final static int BET_LIMIT = 40002;
    @ExcelValue(value = "金币不足{0}不能上庄！")
    public final static int GOLD_NOT = 40003;
    @ExcelValue(value = "当前已经在上庄列表！")
    public final static int NOW_IN_BANKER_LIST = 40004;
    @ExcelValue(value = "当前已经有位置了")
    public final static int TO_ROOM_HASH_POSITION = 40005;
    @ExcelValue(value = "位置已经被占满了，再等等吧！")
    public final static int POSITION_NOT = 40006;
    @ExcelValue(value = "当前没在位置上，无法下坐！")
    public final static int PLAYER_NOT_POSITION = 40007;
    @ExcelValue(value = "您不是庄家无法做下庄操作，无法下坐！")
    public final static int PLAYER_NOT_IS_BANKER_NOR_DOWN = 40008;

    /**-----------------------------50001 - 60000段是天天乐异常--------------------*/
    @ExcelValue(value = "当前下注位置超出下注限制！")
    public final static int BET_UNNECESSARY  = 50001;

    /**-----------------------------60001 - 70000段是商城，家族等服的异常--------------------*/
    @ExcelValue(value = "钻石不足！")
    public final static int DIAMOND_COUNT_INSUFFICIENT = 60001;
    @ExcelValue(value = "金币不足！")
    public final static int GOLD_COUNT_INSUFFICIENT = 60002;
    @ExcelValue(value = "对方已经时您的好友，不需要重新添加！")
    public final static int THIS_PLAYER_IS_YOU_FRIEND = 60003;
    @ExcelValue(value = "对方不在线！")
    public final static int OTHER_NOT_ONLINE = 60004;
    @ExcelValue(value = "红包已经过期！")
    public final static int RED_PACKET_TIME_OUT = 60005;
}