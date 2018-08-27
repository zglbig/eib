using System;
using System.Collections.Generic;
using ProtoBuf;
[ProtoContract]
public class LotteryBetDto : SerializeMessage {
	[ProtoMember(1)]
	public long reduceGold;
	[ProtoMember(2)]
	public long roomAllGold;
	[ProtoMember(3)]
	public long betGold;
	[ProtoMember(4)]
	public int betPosition;
}
