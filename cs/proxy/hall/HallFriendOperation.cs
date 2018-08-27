/// <summary>
/// 好友
/// </summary>
namespace org.zgl.service.client.hall{
	[Proxy(gameId = -10)]
	public interface HallFriendOperation : ITcpAsyncService { 
		/// <summary>
		/// 
		/// </summary>
		void addFriend(long targetUid);
		/// <summary>
		///  ----> <<addFriend();回调>>
		/// </summary>
		void addFriend2CallBack(bool callBackParam);
		/// <summary>
		/// 
		/// </summary>
		void removeFriend(long targetUid);
		/// <summary>
		///  ----> <<removeFriend();回调>>
		/// </summary>
		void removeFriend2CallBack(bool callBackParam);
		/// <summary>
		/// 同意好友添加
		/// </summary>
		void consent(long targetUid);
		/// <summary>
		/// 同意好友添加 ----> <<consent();回调>>
		/// </summary>
		void consent2CallBack(BasePlayerDto callBackParam);
		/// <summary>
		/// 好友列表
		/// </summary>
		void friendList();
		/// <summary>
		/// 好友列表 ----> <<friendList();回调>>
		/// </summary>
		void friendList2CallBack(FriendListDto callBackParam);
		/// <summary>
		/// 发送好友聊天消息 msgType:消息类型1-普通文字 2-表情 ，msg：消息内容
		/// </summary>
		void friendChat(long targetUid,int msgType,string msg);
		/// <summary>
		/// 发送好友聊天消息 msgType:消息类型1-普通文字 2-表情 ，msg：消息内容 ----> <<friendChat();回调>>
		/// </summary>
		void friendChat2CallBack(bool callBackParam);
	}
}
