using System;
using System.Collections.Generic;
using ProtoBuf;
[ProtoContract]
public class GeneralizeDto : SerializeMessage {
	[ProtoMember(1)]
	public string userNmae;
	[ProtoMember(2)]
	public long award;
	[ProtoMember(3)]
	public string time;
}
