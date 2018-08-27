using System;
using System.Collections.Generic;
using ProtoBuf;
[ProtoContract]
public class DatableModelDto : SerializeMessage {
	[ProtoMember(1)]
	public string objType;
	[ProtoMember(2)]
	public List<string> jsonMsg;
}
