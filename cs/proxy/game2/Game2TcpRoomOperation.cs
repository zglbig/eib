/// <summary>
/// 
/// </summary>
namespace org.zgl.service.server.game2{
	[Proxy(gameId = -9)]
	public interface Game2TcpRoomOperation : ITcpAsyncService { 
		/// <summary>
		/// 
		/// </summary>
		void bankerUp();
		/// <summary>
		///  ----> <<bankerUp();回调>>
		/// </summary>
		void bankerUp2CallBack(int callBackParam);
		/// <summary>
		/// 
		/// </summary>
		void bankerDown();
		/// <summary>
		///  ----> <<bankerDown();回调>>
		/// </summary>
		void bankerDown2CallBack(bool callBackParam);
		/// <summary>
		/// 离开房间
		/// </summary>
		void exitRoom();
		/// <summary>
		/// 离开房间 ----> <<exitRoom();回调>>
		/// </summary>
		void exitRoom2CallBack(bool callBackParam);
		/// <summary>
		/// 下注 ：chip 下注筹码对应的索引值 ,返回自己剩余的金币
		/// </summary>
		void bet(int arg0,int arg1);
		/// <summary>
		/// 下注 ：chip 下注筹码对应的索引值 ,返回自己剩余的金币 ----> <<bet();回调>>
		/// </summary>
		void bet2CallBack(Game2BetUpdateWeathDto callBackParam);
		/// <summary>
		/// 
		/// </summary>
		void jackpot();
		/// <summary>
		///  ----> <<jackpot();回调>>
		/// </summary>
		void jackpot2CallBack(Game2JackpotListDto callBackParam);
		/// <summary>
		/// 
		/// </summary>
		void positionDown();
		/// <summary>
		///  ----> <<positionDown();回调>>
		/// </summary>
		void positionDown2CallBack(bool callBackParam);
		/// <summary>
		/// 
		/// </summary>
		void history();
		/// <summary>
		///  ----> <<history();回调>>
		/// </summary>
		void history2CallBack(Game2HistoryListDto callBackParam);
		/// <summary>
		/// 
		/// </summary>
		void bankerList();
		/// <summary>
		///  ----> <<bankerList();回调>>
		/// </summary>
		void bankerList2CallBack(Game2PositionPlayerInfoListDto callBackParam);
		/// <summary>
		/// 
		/// </summary>
		void positionUp(int arg0);
		/// <summary>
		///  ----> <<positionUp();回调>>
		/// </summary>
		void positionUp2CallBack(bool callBackParam);
		/// <summary>
		/// 发送聊天消息 msgType:消息类型1-普通文字 2-表情 ，msg：消息内容
		/// </summary>
		void sendChatMsg(int arg0,string arg1);
		/// <summary>
		/// 发送聊天消息 msgType:消息类型1-普通文字 2-表情 ，msg：消息内容 ----> <<sendChatMsg();回调>>
		/// </summary>
		void sendChatMsg2CallBack(bool callBackParam);
		/// <summary>
		/// 
		/// </summary>
		void playPlayerList();
		/// <summary>
		///  ----> <<playPlayerList();回调>>
		/// </summary>
		void playPlayerList2CallBack(Game2PositionPlayerInfoListDto callBackParam);
		/// <summary>
		/// 踢人下坐
		/// </summary>
		void kicking(long arg0);
		/// <summary>
		/// 踢人下坐 ----> <<kicking();回调>>
		/// </summary>
		void kicking2CallBack(bool callBackParam);
	}
}
