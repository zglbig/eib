using System;
using System.Collections.Generic;
using ProtoBuf;
[ProtoContract]
public class RankingBaseDto : SerializeMessage {
	[ProtoMember(1)]
	public long uid;
	[ProtoMember(2)]
	public string userName;
	[ProtoMember(3)]
	public string headImgUrl;
	[ProtoMember(4)]
	public int vipLv;
	[ProtoMember(5)]
	public long showWeath;
	[ProtoMember(6)]
	public int useAutoId;
}
