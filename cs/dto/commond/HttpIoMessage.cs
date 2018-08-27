using System;
using System.Collections.Generic;
using ProtoBuf;
[ProtoContract]
public class HttpIoMessage : SerializeMessage {
	[ProtoMember(1)]
	public long uid;
	[ProtoMember(2)]
	public int resultCode;
	[ProtoMember(3)]
	public string msg;
}
