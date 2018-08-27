using System;
using System.Collections.Generic;
using ProtoBuf;
[ProtoContract]
public class Game2BetUpdateWeathDto : SerializeMessage {
	[ProtoMember(1)]
	public long reduceGold;
	[ProtoMember(2)]
	public long betGold;
	[ProtoMember(3)]
	public int betPosition;
}
