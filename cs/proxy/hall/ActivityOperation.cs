/// <summary>
/// 
/// </summary>
namespace org.zgl.service.server.hall{
	[Proxy(gameId = -10)]
	public interface ActivityOperation : ITcpAsyncService { 
		/// <summary>
		/// 
		/// </summary>
		void pay(int productId);
		/// <summary>
		///  ----> <<pay();回调>>
		/// </summary>
		void pay2CallBack();
	}
}
