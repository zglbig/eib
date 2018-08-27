using System;
using System.Collections.Generic;
namespace org.zgl.build.excel.create{
	public class ExcelErrorCodeModule : DataTableMessage {
		public int Id(){
			return id;
		}
		public static ExcelErrorCodeModule get(int id){
			return StaticConfigMessage.Instance.get<ExcelErrorCodeModule>(typeof(ExcelErrorCodeModule),id);
		}
		public int id;
		public string name;
		public string value;
		public void AfterInit(){}
	}
}
