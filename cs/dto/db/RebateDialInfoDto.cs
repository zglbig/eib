using System;
using System.Collections.Generic;
using ProtoBuf;
[ProtoContract]
public class RebateDialInfoDto : SerializeMessage {
	[ProtoMember(1)]
	public int canGetCount;
	[ProtoMember(2)]
	public int getCounted;
	[ProtoMember(3)]
	public long topUpNum;
	[ProtoMember(4)]
	public long ahRoonBetNum;
	[ProtoMember(5)]
	public long toRoomBetNum;
	[ProtoMember(6)]
	public long diceRoomNum;
	[ProtoMember(7)]
	public long betAllCount;
}
