using System;
using System.Collections.Generic;
using ProtoBuf;
[ProtoContract]
public class ChatDto : SerializeMessage {
	[ProtoMember(1)]
	public long uid;
	[ProtoMember(2)]
	public int msgType;
	[ProtoMember(3)]
	public string username;
	[ProtoMember(4)]
	public string headIcon;
	[ProtoMember(5)]
	public int vipLv;
	[ProtoMember(6)]
	public string msg;
	[ProtoMember(7)]
	public string sex;
	[ProtoMember(8)]
	public string sendTime;
}
