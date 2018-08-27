using System;
using System.Collections.Generic;
using ProtoBuf;
[ProtoContract]
public class PawnshopDto : SerializeMessage {
	[ProtoMember(1)]
	public long acquireGold;
	[ProtoMember(2)]
	public long holdGold;
	[ProtoMember(3)]
	public int productId;
	[ProtoMember(4)]
	public int productHoldCount;
}
