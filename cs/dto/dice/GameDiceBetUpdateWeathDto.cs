using System;
using System.Collections.Generic;
using ProtoBuf;
[ProtoContract]
public class GameDiceBetUpdateWeathDto : SerializeMessage {
	[ProtoMember(1)]
	public long residueGold;
	[ProtoMember(2)]
	public long betGold;
	[ProtoMember(3)]
	public int betPosition;
}
