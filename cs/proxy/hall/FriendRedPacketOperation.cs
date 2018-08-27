/// <summary>
/// 
/// </summary>
namespace org.zgl.service.server.hall{
	[Proxy(gameId = -10)]
	public interface FriendRedPacketOperation : ITcpAsyncService { 
		/// <summary>
		/// 发好友红包
		/// </summary>
		void giveRedEnvelopes(long targetUid,long gold);
		/// <summary>
		/// 发好友红包 ----> <<giveRedEnvelopes();回调>>
		/// </summary>
		void giveRedEnvelopes2CallBack(RedPacketDto callBackParam);
		/// <summary>
		/// 领好友红包
		/// </summary>
		void bonus(int id);
		/// <summary>
		/// 领好友红包 ----> <<bonus();回调>>
		/// </summary>
		void bonus2CallBack(RedPacketDto callBackParam);
	}
}
