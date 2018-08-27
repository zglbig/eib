/// <summary>
/// 
/// </summary>
namespace org.zgl.service.client.dice{
	[Proxy(gameId = -12)]
	public interface GameDicePlayerOperationRoomNotify : INotify { 
		/// <summary>
		/// 
		/// </summary>
		void end();
		/// <summary>
		/// 
		/// </summary>
		void start();
		/// <summary>
		/// 结算是财富更新
		/// </summary>
		void settleAccountsWeathUpdate(long betGold,long winGold,long residueGold,int betPosition,float rate);
		/// <summary>
		/// 结算是财富更新
		/// </summary>
		void positionPlayerWeathUpdate(long uid,long exchangeGold,long residueGold);
		/// <summary>
		/// 结算排行
		/// </summary>
		void settleAccountRanking(int count1,int count2,DiceSettleRankingListDto dto);
		/// <summary>
		/// 收到聊天信息
		/// </summary>
		void receiveChatMsg(ChatDto msg);
		/// <summary>
		/// 有人上位置
		/// </summary>
		void playerPositionUp(GameDicePositionPlayerInfoDto dto);
		/// <summary>
		/// 房间有玩家财富变更同步更新，uid：变更得uid，dto：变更得财富
		/// </summary>
		void playerWeathUpdate(long uid,ItemListDto dto);
		/// <summary>
		/// 有玩家清除下注
		/// </summary>
		void playerClearBet(long uid,long residueGold,long roomAllGold,int nowBetCount);
		/// <summary>
		/// 踢人下坐,selfUserName：踢人的用户名，targetUserName：被踢的人的用户名，targetUid：被踢的人的uid
		/// </summary>
		void kicking(string selfUserName,string targetUserName,long targetUid);
		/// <summary>
		/// 
		/// </summary>
		void stopBet();
		/// <summary>
		/// 
		/// </summary>
		void playerSettle(DiceSettleRankingDto d);
		/// <summary>
		/// 有人下位置
		/// </summary>
		void playerPositionDown(long uid);
	}
}
