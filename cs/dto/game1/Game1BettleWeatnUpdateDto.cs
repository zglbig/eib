using System;
using System.Collections.Generic;
using ProtoBuf;
[ProtoContract]
public class Game1BettleWeatnUpdateDto : SerializeMessage {
	[ProtoMember(1)]
	public long uid;
	[ProtoMember(2)]
	public long holdGold;
}
