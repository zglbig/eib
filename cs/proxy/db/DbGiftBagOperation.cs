/// <summary>
/// 
/// </summary>
namespace org.zgl.service.client.db{
	[Proxy(gameId = -7)]
	public interface DbGiftBagOperation : IHttpSyncService { 
		/// <summary>
		/// 打开成长礼包信息
		/// </summary>
		GiftBagInfoDto open(long arg0);
		/// <summary>
		/// 领取奖励
		/// </summary>
		ItemListDto receiveAward(long arg0);
	}
}
