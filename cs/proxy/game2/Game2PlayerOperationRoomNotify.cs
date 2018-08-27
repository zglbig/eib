/// <summary>
/// 有玩家对房间操作时服务器主动通知客户端
/// </summary>
namespace org.zgl.service.client.game2{
	[Proxy(gameId = -9)]
	public interface Game2PlayerOperationRoomNotify : INotify { 
		/// <summary>
		/// 
		/// </summary>
		void start0();
		/// <summary>
		/// 
		/// </summary>
		void end();
		/// <summary>
		/// 有新的人进入房间并上了位置
		/// </summary>
		void newPlayerUpPosition(Game2PositionPlayerInfoDto arg0);
		/// <summary>
		/// 
		/// </summary>
		void playerBetUpdateRoomWeath(long arg0,long arg1,long arg2,int arg3);
		/// <summary>
		/// 结算同步位置玩家财富，reduceGold：玩家剩余金币，winGold：玩家本局获得金币
		/// </summary>
		void positionPlayerSettleAccounts(long arg0,long arg1);
		/// <summary>
		/// 房间有玩家财富变更同步更新，uid：变更得uid，dto：变更得财富
		/// </summary>
		void playerWeathUpdate(long arg0,ItemListDto arg1);
		/// <summary>
		/// 结算同步财富，reduceGold：玩家剩余金币，winGold：玩家本局获得金币
		/// </summary>
		void playerSettleAccounts(long arg0,long arg1);
		/// <summary>
		/// 结算同步位置玩家财富，reduceGold：玩家剩余金币，winGold：玩家本局获得金币，nowJackpot：当前奖池
		/// </summary>
		void bankerSettleAccounts(long arg0,long arg1,long arg2);
		/// <summary>
		/// 
		/// </summary>
		void positionPlayerExitRoom(long arg0);
		/// <summary>
		/// 主动下庄成功
		/// </summary>
		void bankerDownSuccess();
		/// <summary>
		/// 金币不足被迫下庄
		/// </summary>
		void bankerDownByGlodInsufficient();
		/// <summary>
		/// 
		/// </summary>
		void start1();
		/// <summary>
		/// 通知手牌
		/// </summary>
		void showCards(Game2CardListDto arg0);
		/// <summary>
		/// 结算之后的排行
		/// </summary>
		void ranking(GameRankingListDto arg0);
		/// <summary>
		/// 
		/// </summary>
		void stopBet();
		/// <summary>
		/// 通知换庄了 bankerType：庄家类型 1 系统庄家 player返回null 、2 玩家庄家 playter返回数据
		/// </summary>
		void bankerExchange(int arg0,Game2PositionPlayerInfoDto arg1);
		/// <summary>
		/// 上庄列表的人数
		/// </summary>
		void bankerListCount(int arg0);
		/// <summary>
		/// 达到上庄限制次数自动下庄
		/// </summary>
		void bankerCountLimit();
		/// <summary>
		/// 收到聊天信息
		/// </summary>
		void receiveChatMsg(ChatDto arg0);
		/// <summary>
		/// 踢人下坐
		/// </summary>
		bool kicking(string arg0,long arg1,string arg2);
	}
}
