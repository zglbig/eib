/// <summary>
/// 签到
/// </summary>
namespace org.zgl.service.client.db{
	[Proxy(gameId = -7)]
	public interface SiginOperation : ITcpAsyncService { 
		/// <summary>
		/// 签到奖励
		/// </summary>
		void sigin(long arg0);
		/// <summary>
		/// 签到奖励 ----> <<sigin();回调>>
		/// </summary>
		void sigin2CallBack(ItemListDto callBackParam);
		/// <summary>
		/// 签到转盘
		/// </summary>
		void dial(long arg0);
		/// <summary>
		/// 签到转盘 ----> <<dial();回调>>
		/// </summary>
		void dial2CallBack(ItemListDto callBackParam);
	}
}
