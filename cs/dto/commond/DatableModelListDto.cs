using System;
using System.Collections.Generic;
using ProtoBuf;
[ProtoContract]
public class DatableModelListDto : SerializeMessage {
	[ProtoMember(1)]
	public List<DatableModelDto> msgList;
}
