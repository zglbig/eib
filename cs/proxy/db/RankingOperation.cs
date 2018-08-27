/// <summary>
/// 
/// </summary>
namespace org.zgl.service.client.db{
	[Proxy(gameId = -7)]
	public interface RankingOperation : IHttpSyncService { 
		/// <summary>
		/// 
		/// </summary>
		RankingListDto charmRanking();
		/// <summary>
		/// 
		/// </summary>
		RankingListDto goldRanking();
	}
}
