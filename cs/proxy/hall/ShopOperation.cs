/// <summary>
/// 商城操作
/// </summary>
namespace org.zgl.service.client.hall{
	[Proxy(gameId = -10)]
	public interface ShopOperation : ITcpAsyncService { 
		/// <summary>
		/// 购买vip
		/// </summary>
		void pay(int productId);
		/// <summary>
		/// 购买vip ----> <<pay();回调>>
		/// </summary>
		void pay2CallBack(ItemListDto callBackParam);
	}
}
