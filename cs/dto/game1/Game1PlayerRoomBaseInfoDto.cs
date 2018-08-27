using System;
using System.Collections.Generic;
using ProtoBuf;
[ProtoContract]
public class Game1PlayerRoomBaseInfoDto : SerializeMessage {
	[ProtoMember(1)]
	public long uid;
	[ProtoMember(2)]
	public string userName;
	[ProtoMember(3)]
	public long gold;
	[ProtoMember(4)]
	public string headIcon;
	[ProtoMember(5)]
	public int vipLv;
	[ProtoMember(6)]
	public long bottomNum;
	[ProtoMember(7)]
	public bool hasReady;
	[ProtoMember(8)]
	public int postion;
	[ProtoMember(9)]
	public int autoId;
	[ProtoMember(10)]
	public string sex;
	[ProtoMember(11)]
	public List<int> cardId;
	[ProtoMember(12)]
	public long nowBetAll;
	[ProtoMember(13)]
	public int nowChip;
}
