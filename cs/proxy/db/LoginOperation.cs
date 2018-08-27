/// <summary>
/// 登陆代理接口
/// </summary>
namespace org.zgl.service.client.db{
	[Proxy(gameId = -7)]
	public interface LoginOperation : IHttpSyncService { 
		/// <summary>
		/// 登陆
		/// </summary>
		LoginDto login(string arg0,string arg1);
		/// <summary>
		/// 注册
		/// </summary>
		LoginDto regist(string arg0,string arg1,string arg2,string arg3,string arg4,string arg5);
	}
}
