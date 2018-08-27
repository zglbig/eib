/// <summary>
/// 房间http操作
/// </summary>
namespace org.zgl.service.server.game1{
	[Proxy(gameId = -8)]
	public interface Game1HttpRoomOperation : IHttpSyncService { 
		/// <summary>
		/// 进入房间
		/// </summary>
		Game1PlayerRoomDto enterRoom(long uid,int scenesId);
	}
}
