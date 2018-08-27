/// <summary>
/// 天天乐服务器主动通知通知
/// </summary>
namespace org.zgl.service.client.lottery{
	[Proxy(gameId = -11)]
	public interface GameLotteryOperationRoomNotify : INotify { 
		/// <summary>
		/// 开奖结果
		/// </summary>
		void result(LotteryHistoryDto dto);
		/// <summary>
		/// 房间开始
		/// </summary>
		void start();
		/// <summary>
		/// 下注通知 nowBetPlayerNumber:当前下注人数,nowLotteryAllGold:当前房间下注总金额
		/// </summary>
		void betNotify(int nowBetPlayerNumber,long nowLotteryAllGold);
		/// <summary>
		/// 房间有玩家财富变更同步更新，uid：变更得uid，dto：变更得财富
		/// </summary>
		void playerWeathUpdate(long uid,ItemListDto dto);
		/// <summary>
		/// 停止下注
		/// </summary>
		void stopBet();
		/// <summary>
		/// 结算通知 bonus:中奖金额
		/// </summary>
		void settleAccount(long bonus);
	}
}
