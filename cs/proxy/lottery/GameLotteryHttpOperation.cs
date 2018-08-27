/// <summary>
/// 天天乐操作
/// </summary>
namespace org.zgl.service.server.lottery{
	[Proxy(gameId = -11)]
	public interface GameLotteryHttpOperation : IHttpSyncService { 
		/// <summary>
		/// 关闭天天乐界面的狮虎调用这个方法
		/// </summary>
		bool exit(long uid);
		/// <summary>
		/// 打开天天乐界面的时候调用这个方法
		/// </summary>
		LotteryRoomInfoDto enter(long uid);
	}
}
