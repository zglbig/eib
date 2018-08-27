/// <summary>
/// 
/// </summary>
namespace org.zgl.service.server.dice{
	[Proxy(gameId = -12)]
	public interface GameDiceHttpRoomOperation : IHttpSyncService { 
		/// <summary>
		/// 
		/// </summary>
		DiceRoomInfiDto enter(long uid);
	}
}
