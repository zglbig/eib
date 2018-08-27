using System;
using System.Collections.Generic;
using ProtoBuf;
[ProtoContract]
public class Game1BetAllResponseDto : SerializeMessage {
	[ProtoMember(1)]
	public long holdGold;
	[ProtoMember(2)]
	public long betGold;
	[ProtoMember(3)]
	public long roomGld;
	[ProtoMember(4)]
	public long nextOperationUid;
}
