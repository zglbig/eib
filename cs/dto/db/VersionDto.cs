using System;
using System.Collections.Generic;
using ProtoBuf;
[ProtoContract]
public class VersionDto : SerializeMessage {
	[ProtoMember(1)]
	public string gameV;
	[ProtoMember(2)]
	public string gameUrl;
	[ProtoMember(3)]
	public string dataV;
	[ProtoMember(4)]
	public string dataUrl;
	[ProtoMember(5)]
	public string abV;
	[ProtoMember(6)]
	public string abUrl;
	[ProtoMember(7)]
	public string secretKey;
	[ProtoMember(8)]
	public long size;
}
