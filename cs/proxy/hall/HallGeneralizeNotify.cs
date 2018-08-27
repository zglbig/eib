/// <summary>
/// 
/// </summary>
namespace org.zgl.service.client.hall{
	[Proxy(gameId = -10)]
	public interface HallGeneralizeNotify : INotify { 
		/// <summary>
		/// 如果玩家在线 通知推广人有玩家填了你的邀请码
		/// </summary>
		void notifyInvite(GeneralizeAwardDto dto);
	}
}
