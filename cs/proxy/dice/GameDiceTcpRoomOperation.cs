/// <summary>
/// 
/// </summary>
namespace org.zgl.service.server.dice{
	[Proxy(gameId = -12)]
	public interface GameDiceTcpRoomOperation : ITcpAsyncService { 
		/// <summary>
		/// 清除下注 返回剩下多少钱，返回乐多少钱
		/// </summary>
		void clearBet();
		/// <summary>
		/// 清除下注 返回剩下多少钱，返回乐多少钱 ----> <<clearBet();回调>>
		/// </summary>
		void clearBet2CallBack(GameDiceBetUpdateWeathDto callBackParam);
		/// <summary>
		/// 当前房间没在位置上所有的人
		/// </summary>
		void nowPlayPlayerList();
		/// <summary>
		/// 当前房间没在位置上所有的人 ----> <<nowPlayPlayerList();回调>>
		/// </summary>
		void nowPlayPlayerList2CallBack(GameDicePositionPlayerInfoListDto callBackParam);
		/// <summary>
		/// 
		/// </summary>
		void history();
		/// <summary>
		///  ----> <<history();回调>>
		/// </summary>
		void history2CallBack(DiceHistoryDto callBackParam);
		/// <summary>
		/// 上位置
		/// </summary>
		void positionUp(int position);
		/// <summary>
		/// 上位置 ----> <<positionUp();回调>>
		/// </summary>
		void positionUp2CallBack(bool callBackParam);
		/// <summary>
		/// 下位置
		/// </summary>
		void positionDown();
		/// <summary>
		/// 下位置 ----> <<positionDown();回调>>
		/// </summary>
		void positionDown2CallBack(bool callBackParam);
		/// <summary>
		/// 踢人下坐
		/// </summary>
		void kicking(long targetUid);
		/// <summary>
		/// 踢人下坐 ----> <<kicking();回调>>
		/// </summary>
		void kicking2CallBack(bool callBackParam);
		/// <summary>
		/// 发送聊天消息 msgType:消息类型1-普通文字 2-表情 ，msg：消息内容
		/// </summary>
		void sendChatMsg(int msgType,string msg);
		/// <summary>
		/// 发送聊天消息 msgType:消息类型1-普通文字 2-表情 ，msg：消息内容 ----> <<sendChatMsg();回调>>
		/// </summary>
		void sendChatMsg2CallBack(bool callBackParam);
		/// <summary>
		/// 下注
		/// </summary>
		void bet(int chip,int position);
		/// <summary>
		/// 下注 ----> <<bet();回调>>
		/// </summary>
		void bet2CallBack(GameDiceBetUpdateWeathDto callBackParam);
	}
}
