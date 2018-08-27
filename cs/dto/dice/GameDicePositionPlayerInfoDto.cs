using System;
using System.Collections.Generic;
using ProtoBuf;
[ProtoContract]
public class GameDicePositionPlayerInfoDto : SerializeMessage {
	[ProtoMember(1)]
	public long uid;
	[ProtoMember(2)]
	public string userName;
	[ProtoMember(3)]
	public string headUrl;
	[ProtoMember(4)]
	public int vipLv;
	[ProtoMember(5)]
	public long gold;
	[ProtoMember(6)]
	public int position;
	[ProtoMember(7)]
	public int useAutoId;
}
