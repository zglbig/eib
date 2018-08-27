using System;
using System.Collections.Generic;
using ProtoBuf;
[ProtoContract]
public class GameRankingDto : SerializeMessage {
	[ProtoMember(1)]
	public long winGold;
	[ProtoMember(2)]
	public long holdGold;
	[ProtoMember(3)]
	public string userName;
}
