using System;
using System.Collections.Generic;
namespace org.zgl.datable{
	public class DiceDataTable : DataTableMessage {
		public int Id(){
			return id;
		}
		public static DiceDataTable get(int id){
			return StaticConfigMessage.Instance.get<DiceDataTable>(typeof(DiceDataTable),id);
		}
		public int id;
		public int probability;
		public int count;
		public int weiTou;
		public DiceCountDto splitCount;
		public void AfterInit(){}
	}
}
