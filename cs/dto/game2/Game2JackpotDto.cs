using System;
using System.Collections.Generic;
using ProtoBuf;
[ProtoContract]
public class Game2JackpotDto : SerializeMessage {
	[ProtoMember(1)]
	public string userName;
	[ProtoMember(2)]
	public long winGold;
	[ProtoMember(3)]
	public int awardType;
}
