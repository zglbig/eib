using System;
using System.Collections.Generic;
using ProtoBuf;
[ProtoContract]
public class HallRedEnvelopePlayerDto : SerializeMessage {
	[ProtoMember(1)]
	public string userName;
	[ProtoMember(2)]
	public string headImgUrl;
	[ProtoMember(3)]
	public long editTime;
	[ProtoMember(4)]
	public long recieveGold;
}
