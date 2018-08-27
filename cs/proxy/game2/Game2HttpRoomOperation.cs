/// <summary>
/// 房间http操作
/// </summary>
namespace org.zgl.service.server.game2{
	[Proxy(gameId = -9)]
	public interface Game2HttpRoomOperation : IHttpSyncService { 
		/// <summary>
		/// 进入房间
		/// </summary>
		Game2PlayerRoomDto enterRoom(long arg0,int arg1);
	}
}
