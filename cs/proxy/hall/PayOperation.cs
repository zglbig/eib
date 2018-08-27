/// <summary>
/// 充值操作
/// </summary>
namespace org.zgl.service.server.hall{
	[Proxy(gameId = -10)]
	public interface PayOperation : ITcpAsyncService { 
		/// <summary>
		/// 
		/// </summary>
		void aliPay(int productId);
		/// <summary>
		///  ----> <<aliPay();回调>>
		/// </summary>
		void aliPay2CallBack(bool callBackParam);
		/// <summary>
		/// 
		/// </summary>
		void wxPay(int productId);
		/// <summary>
		///  ----> <<wxPay();回调>>
		/// </summary>
		void wxPay2CallBack(bool callBackParam);
	}
}
