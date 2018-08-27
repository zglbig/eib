/// <summary>
/// 
/// </summary>
namespace org.zgl.service.client.db{
	[Proxy(gameId = -7)]
	public interface GameVersion : IHttpSyncService { 
		/// <summary>
		/// 
		/// </summary>
		VersionDto versionChech();
	}
}
