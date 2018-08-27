/// <summary>
/// 
/// </summary>
namespace org.zgl.service.server.hall{
	[Proxy(gameId = -10)]
	public interface GeneralizeOperation : ITcpAsyncService { 
		/// <summary>
		/// 邀请
		/// </summary>
		void invite(long targetUid);
		/// <summary>
		/// 邀请 ----> <<invite();回调>>
		/// </summary>
		void invite2CallBack(GeneralizeAwardDto callBackParam);
		/// <summary>
		/// 打开推广列表
		/// </summary>
		void openGeneralizeList();
		/// <summary>
		/// 打开推广列表 ----> <<openGeneralizeList();回调>>
		/// </summary>
		void openGeneralizeList2CallBack(GeneralizeListDto callBackParam);
		/// <summary>
		/// 领取奖励
		/// </summary>
		void receiveAward();
		/// <summary>
		/// 领取奖励 ----> <<receiveAward();回调>>
		/// </summary>
		void receiveAward2CallBack(GoldBaseDto callBackParam);
	}
}
