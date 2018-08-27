using System;
using System.Collections.Generic;
namespace org.zgl.error{
	public class AppErrorDatable : DataTableMessage {
		public int Id(){
			return id;
		}
		public static AppErrorDatable get(int id){
			return StaticConfigMessage.Instance.get<AppErrorDatable>(typeof(AppErrorDatable),id);
		}
		public int id;
		public string name;
		public string value;
		public void AfterInit(){}
	}
}
