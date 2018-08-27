using System;
using System.Collections.Generic;
namespace org.zgl.datable{
	public class SiginDialDataTable : DataTableMessage {
		public int Id(){
			return id;
		}
		public static SiginDialDataTable get(int id){
			return StaticConfigMessage.Instance.get<SiginDialDataTable>(typeof(SiginDialDataTable),id);
		}
		public int id;
		public int awardId;
		public int count;
		public int probability;
		public int awardType;
		public void AfterInit(){}
	}
}
