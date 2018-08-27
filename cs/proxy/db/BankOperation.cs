/// <summary>
/// 
/// </summary>
namespace org.zgl.service.client.db{
	[Proxy(gameId = -7)]
	public interface BankOperation : IHttpSyncService { 
		/// <summary>
		/// 
		/// </summary>
		BankDto save(long arg0,long arg1);
		/// <summary>
		/// 
		/// </summary>
		BankDto draw(long arg0,long arg1);
	}
}
