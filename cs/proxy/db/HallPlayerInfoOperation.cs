/// <summary>
/// 玩家信息操作
/// </summary>
namespace org.zgl.service.client.hall{
	[Proxy(gameId = -7)]
	public interface HallPlayerInfoOperation : IHttpSyncService { 
		/// <summary>
		/// 查看礼物
		/// </summary>
		GiftDto playerGiftInfo(long arg0);
		/// <summary>
		/// 查看个人信息
		/// </summary>
		PlayerInfoDto playerInfo(long arg0);
		/// <summary>
		/// 查看道具
		/// </summary>
		PropDto playerPropInfo(long arg0);
	}
}
