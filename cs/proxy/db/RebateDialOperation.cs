/// <summary>
/// 返利轮盘
/// </summary>
namespace org.zgl.service.client.hall{
	[Proxy(gameId = -7)]
	public interface RebateDialOperation : IHttpSyncService { 
		/// <summary>
		/// 抽奖
		/// </summary>
		RebateDialDto luckyDraw(long arg0);
		/// <summary>
		/// 查看轮盘信息
		/// </summary>
		RebateDialInfoDto dialInfo(long arg0);
	}
}
