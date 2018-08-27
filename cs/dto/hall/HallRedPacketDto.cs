using System;
using System.Collections.Generic;
using ProtoBuf;
[ProtoContract]
public class HallRedPacketDto : SerializeMessage {
	[ProtoMember(1)]
	public long id;
	[ProtoMember(2)]
	public string userName;
	[ProtoMember(3)]
	public string headImgUrl;
	[ProtoMember(4)]
	public string desc;
	[ProtoMember(5)]
	public bool isFinish;
	[ProtoMember(6)]
	public short redPacketType;
	[ProtoMember(7)]
	public long createTime;
}
