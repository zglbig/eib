using System;
using System.Collections.Generic;
using ProtoBuf;
[ProtoContract]
public class DiceSettleRankingDto : SerializeMessage {
	[ProtoMember(1)]
	public long uid;
	[ProtoMember(2)]
	public string userName;
	[ProtoMember(3)]
	public long residueGold;
	[ProtoMember(4)]
	public long winGold;
}
