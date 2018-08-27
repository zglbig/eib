using System;
using System.Collections.Generic;
namespace org.zgl.datable{
	public class CommodityDataTable : DataTableMessage {
		public int Id(){
			return id;
		}
		public static CommodityDataTable get(int id){
			return StaticConfigMessage.Instance.get<CommodityDataTable>(typeof(CommodityDataTable),id);
		}
		public int id;
		public int shopId;
		public string name;
		public string describe;
		public long selling;
		public string effect;
		public int consumeType;
		public int count;
		public int vipLimitLv;
		public int integral;
		public void AfterInit(){}
	}
}
