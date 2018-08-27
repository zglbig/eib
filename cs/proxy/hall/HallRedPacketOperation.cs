/// <summary>
/// d大厅红包
/// </summary>
namespace org.zgl.service.server.hall{
	[Proxy(gameId = -10)]
	public interface HallRedPacketOperation : ITcpAsyncService { 
		/// <summary>
		/// 领取红包
		/// </summary>
		void recieve(long id);
		/// <summary>
		/// 领取红包 ----> <<recieve();回调>>
		/// </summary>
		void recieve2CallBack(ItemDto callBackParam);
		/// <summary>
		/// 发大厅红包 返回自己当前剩余金币 redType：红包类型 1 普通红包 2 运气红包，count：发多少个红包 desc：说明
		/// </summary>
		void redEnvelope(short redType,int count,string desc);
		/// <summary>
		/// 发大厅红包 返回自己当前剩余金币 redType：红包类型 1 普通红包 2 运气红包，count：发多少个红包 desc：说明 ----> <<redEnvelope();回调>>
		/// </summary>
		void redEnvelope2CallBack(ItemDto callBackParam);
		/// <summary>
		/// 打开单个红包信息
		/// </summary>
		void openOneRed(long id);
		/// <summary>
		/// 打开单个红包信息 ----> <<openOneRed();回调>>
		/// </summary>
		void openOneRed2CallBack(HallRedEnvelopePlayerListDto callBackParam);
		/// <summary>
		/// 打开大厅红包列表
		/// </summary>
		void openHallList();
		/// <summary>
		/// 打开大厅红包列表 ----> <<openHallList();回调>>
		/// </summary>
		void openHallList2CallBack(HallRedPacketListDto callBackParam);
	}
}
