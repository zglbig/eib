using System;
using System.Collections.Generic;
namespace org.zgl.datable{
	public class SignInDataTable : DataTableMessage {
		public int Id(){
			return id;
		}
		public static SignInDataTable get(int id){
			return StaticConfigMessage.Instance.get<SignInDataTable>(typeof(SignInDataTable),id);
		}
		public int id;
		public long gold;
		public int changeCard;
		public void AfterInit(){}
	}
}
