using System;
using System.Collections.Generic;
namespace org.zgl.datable{
	public class RebateDialDataTable : DataTableMessage {
		public int Id(){
			return id;
		}
		public static RebateDialDataTable get(int id){
			return StaticConfigMessage.Instance.get<RebateDialDataTable>(typeof(RebateDialDataTable),id);
		}
		public int id;
		public int awardId;
		public int num;
		public int probability;
		public void AfterInit(){}
	}
}
