using System;
using System.Collections.Generic;
using ProtoBuf;
[ProtoContract]
public class PlayerInfoDto : SerializeMessage {
	[ProtoMember(1)]
	public long uid;
	[ProtoMember(2)]
	public string userName;
	[ProtoMember(3)]
	public string headImgUrl;
	[ProtoMember(4)]
	public long gold;
	[ProtoMember(5)]
	public int vipLv;
	[ProtoMember(6)]
	public long diamond;
	[ProtoMember(7)]
	public long charm;
	[ProtoMember(8)]
	public string sigin;
	[ProtoMember(9)]
	public string contactWay;
}
