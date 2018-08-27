/// <summary>
/// 天天乐操作
/// </summary>
namespace org.zgl.service.server.lottery{
	[Proxy(gameId = -11)]
	public interface GameLotteryTcpOperation : ITcpAsyncService { 
		/// <summary>
		/// 下注 position:下注位置,chipCount:下注注数
		/// </summary>
		void bet(int position,int chipCount);
		/// <summary>
		/// 下注 position:下注位置,chipCount:下注注数 ----> <<bet();回调>>
		/// </summary>
		void bet2CallBack(LotteryBetDto callBackParam);
	}
}
