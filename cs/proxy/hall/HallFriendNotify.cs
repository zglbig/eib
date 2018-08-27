/// <summary>
/// 好友操作通知
/// </summary>
namespace org.zgl.service.client.hall{
	[Proxy(gameId = -10)]
	public interface HallFriendNotify : INotify { 
		/// <summary>
		/// 收到好友聊天信息
		/// </summary>
		void receiveChatMsg(long sendUid,ChatDto msg);
		/// <summary>
		/// 有人請求添加为好友 uid：請求人的uid，userName：請求人的用户名
		/// </summary>
		void hasFriendRequest(long uid,string userName);
		/// <summary>
		/// 同意还好友請求
		/// </summary>
		void friendConsent(BasePlayerDto dto);
	}
}
