using System;
using System.Collections.Generic;
namespace org.zgl.datable{
	public class VipDataTable : DataTableMessage {
		public int Id(){
			return id;
		}
		public static VipDataTable get(int id){
			return StaticConfigMessage.Instance.get<VipDataTable>(typeof(VipDataTable),id);
		}
		public int id;
		public string vipIcon;
		public int singIn;
		public int moneyTreeLv;
		public int friendCount;
		public bool kicking;
		public int auto;
		public bool giveGift;
		public bool privateRoom;
		public int price;
		public long exp;
		public int moneyTreeMultiple;
		public void AfterInit(){}
	}
}
