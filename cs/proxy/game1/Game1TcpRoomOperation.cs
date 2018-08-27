/// <summary>
/// 
/// </summary>
namespace org.zgl.service.server.game1{
	[Proxy(gameId = -8)]
	public interface Game1TcpRoomOperation : ITcpAsyncService { 
		/// <summary>
		/// 准备
		/// </summary>
		void ready();
		/// <summary>
		/// 准备 ----> <<ready();回调>>
		/// </summary>
		void ready2CallBack(bool callBackParam);
		/// <summary>
		/// 比牌
		/// </summary>
		void compareCard(long targetUid);
		/// <summary>
		/// 比牌 ----> <<compareCard();回调>>
		/// </summary>
		void compareCard2CallBack(bool callBackParam);
		/// <summary>
		/// 踢人出房间
		/// </summary>
		void kicking(long targetUid);
		/// <summary>
		/// 踢人出房间 ----> <<kicking();回调>>
		/// </summary>
		void kicking2CallBack(bool callBackParam);
		/// <summary>
		/// 离开房间
		/// </summary>
		void exitRoom();
		/// <summary>
		/// 离开房间 ----> <<exitRoom();回调>>
		/// </summary>
		void exitRoom2CallBack(bool callBackParam);
		/// <summary>
		/// 全压
		/// </summary>
		void betAll();
		/// <summary>
		/// 全压 ----> <<betAll();回调>>
		/// </summary>
		void betAll2CallBack(Game1BetAllResponseDto callBackParam);
		/// <summary>
		/// 弃牌
		/// </summary>
		void giveUpCard();
		/// <summary>
		/// 弃牌 ----> <<giveUpCard();回调>>
		/// </summary>
		void giveUpCard2CallBack(bool callBackParam);
		/// <summary>
		/// 看牌
		/// </summary>
		void lookCard();
		/// <summary>
		/// 看牌 ----> <<lookCard();回调>>
		/// </summary>
		void lookCard2CallBack(CardsDto callBackParam);
		/// <summary>
		/// 下注 ：chip 下注筹码对应的索引值 ,返回自己剩余的金币
		/// </summary>
		void bet(int chip);
		/// <summary>
		/// 下注 ：chip 下注筹码对应的索引值 ,返回自己剩余的金币 ----> <<bet();回调>>
		/// </summary>
		void bet2CallBack(long callBackParam);
		/// <summary>
		/// 发送聊天消息 msgType:消息类型1-普通文字 2-表情 ，msg：消息内容
		/// </summary>
		void sendChatMsg(int msgType,string msg);
		/// <summary>
		/// 发送聊天消息 msgType:消息类型1-普通文字 2-表情 ，msg：消息内容 ----> <<sendChatMsg();回调>>
		/// </summary>
		void sendChatMsg2CallBack(bool callBackParam);
		/// <summary>
		/// 加注 ：chipPosition 下注筹码对应的索引值 ,返回自己剩余的金币
		/// </summary>
		void betAdd(int chipPosition);
		/// <summary>
		/// 加注 ：chipPosition 下注筹码对应的索引值 ,返回自己剩余的金币 ----> <<betAdd();回调>>
		/// </summary>
		void betAdd2CallBack(long callBackParam);
		/// <summary>
		/// 换房间
		/// </summary>
		void exchangeRoom();
		/// <summary>
		/// 换房间 ----> <<exchangeRoom();回调>>
		/// </summary>
		void exchangeRoom2CallBack(Game1PlayerRoomDto callBackParam);
	}
}
