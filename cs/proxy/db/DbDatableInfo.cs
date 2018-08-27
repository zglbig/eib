/// <summary>
/// 从服务器获取游戏所需要的静态数据
/// </summary>
namespace org.zgl.service.client.db{
	[Proxy(gameId = -7)]
	public interface DbDatableInfo : IHttpSyncService { 
		/// <summary>
		/// 
		/// </summary>
		DatableModelListDto getData();
	}
}
