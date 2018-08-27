/// <summary>
/// 
/// </summary>
namespace org.zgl.service.server.hall{
	[Proxy(gameId = -10)]
	public interface GiftOperation : ITcpAsyncService { 
		/// <summary>
		/// 当铺 返回剩余礼物和当前金币
		/// </summary>
		void pawnshop(int id,int count);
		/// <summary>
		/// 当铺 返回剩余礼物和当前金币 ----> <<pawnshop();回调>>
		/// </summary>
		void pawnshop2CallBack(PawnshopDto callBackParam);
		/// <summary>
		/// 赠送礼物 返回自己当前财富
		/// </summary>
		void giveGifts(long targetUid,int giftId,int count);
		/// <summary>
		/// 赠送礼物 返回自己当前财富 ----> <<giveGifts();回调>>
		/// </summary>
		void giveGifts2CallBack(long callBackParam);
		/// <summary>
		/// 感谢赠送的礼物
		/// </summary>
		void thank(long targetUid);
		/// <summary>
		/// 感谢赠送的礼物 ----> <<thank();回调>>
		/// </summary>
		void thank2CallBack(long callBackParam);
	}
}
